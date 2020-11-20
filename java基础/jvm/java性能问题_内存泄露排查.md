### 一个Java内存泄漏的排查案例

某个业务系统在一段时间突然变慢，我们怀疑是因为出现内存泄露问题导致的，于是踏上排查之路。

#### 2.1 确定频繁Full GC现象

首先通过“虚拟机进程状况工具：jps”找出正在运行的虚拟机进程，最主要是找出这个进程在本地虚拟机的唯一ID（LVMID，Local Virtual Machine Identifier），因为在后面的排查过程中都是需要这个LVMID来确定要监控的是哪一个虚拟机进程。
同时，对于本地虚拟机进程来说，LVMID与操作系统的进程ID（PID，Process Identifier）是一致的，使用Windows的任务管理器或Unix的ps命令也可以查询到虚拟机进程的LVMID。
jps命令格式为：
`jps [ options ] [ hostid ]`
使用命令如下：
使用jps：`jps -l`
使用ps：`ps aux | grep tomat`

找到你需要监控的ID（假设为20954），再利用“虚拟机统计信息监视工具：jstat”监视虚拟机各种运行状态信息。
jstat命令格式为：
`jstat [ option vmid [interval[s|ms] [count]] ]`
使用命令如下：
`jstat -gcutil 20954 1000`
意思是每1000毫秒查询一次，一直查。gcutil的意思是已使用空间站总空间的百分比。
结果如下图：

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\164263a7ef98afcd) jstat执行结果

查询结果表明：这台服务器的新生代Eden区（E，表示Eden）使用了28.30%（最后）的空间，两个Survivor区（S0、S1，表示Survivor0、Survivor1）分别是0和8.93%，老年代（O，表示Old）使用了87.33%。程序运行以来共发生Minor GC（YGC，表示Young GC）101次，总耗时1.961秒，发生Full GC（FGC，表示Full GC）7次，Full GC总耗时3.022秒，总的耗时（GCT，表示GC Time）为4.983秒。

#### 2.2 找出导致频繁Full GC的原因

分析方法通常有两种：
1）把堆dump下来再用MAT等工具进行分析，但dump堆要花较长的时间，并且文件巨大，再从服务器上拖回本地导入工具，这个过程有些折腾，不到万不得已最好别这么干。
2）更轻量级的在线分析，使用“Java内存影像工具：jmap”生成堆转储快照（一般称为headdump或dump文件）。
jmap命令格式：
`jmap [ option ] vmid`
使用命令如下：
`jmap -histo:live 20954`
查看存活的对象情况，如下图所示：

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\164263a7ef7d7418) 存活对象

按照一位IT友的说法，数据不正常，十有八九就是泄露的。在我这个图上对象还是挺正常的。

我在网上找了一位博友的不正常数据，如下：

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\164263a7ef8ccb83) image.png

可以看出HashTable中的元素有5000多万，占用内存大约1.5G的样子。这肯定不正常。

#### 2.3 定位到代码

定位带代码，有很多种方法，比如前面提到的通过MAT查看Histogram即可找出是哪块代码。——我以前是使用这个方法。 也可以使用BTrace，我没有使用过。

**举例：**



一台生产环境机器每次运行几天之后就会莫名其妙的宕机，分析日志之后发现在tomcat刚启动的时候内存占用比较少，但是运行个几天之后内存占用越来越大，通过jmap命令可以查询到一些大对象引用没有被及时GC，这里就要求解决内存泄露的问题。



Java的内存泄露多半是因为对象存在无效的引用，对象得不到释放，如果发现Java应用程序占用的内存出现了泄露的迹象，那么我们一般采用下面的步骤分析：

1. 用工具生成java应用程序的heap dump（如jmap）

2. 使用Java heap分析工具（如MAT），找出内存占用超出预期的嫌疑对象

3. 根据情况，分析嫌疑对象和其他对象的引用关系。

4. 分析程序的源代码，找出嫌疑对象数量过多的原因。



以下一步步的按照项目实例来操作，去解决内存泄露的问题。

1. 登录linux服务器，获取tomcat的pid，命令：

1. ps -ef|grep java 

![img](https://img-blog.csdn.net/20160907153739493)





2. 利用jmap初步分析内存映射，命令：

1. jmap -histo:live 3514 | head -7 

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907162814112)

第2行是我们业务系统的对象，通过这个对象的引用可以初步分析出到底是哪里出现了引用未被垃圾回收收集，通知开发人员优化相关代码。



3. 如果上面一步还无法定位到关键信息，那么需要拿到heap dump，生成离线文件，做进一步分析，命令：



1. jmap -dump:live,format=b,file=heap.hprof 3514 

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907162941232)



4. 拿到heap dump文件，利用eclipse插件MAT来分析heap profile。

a. 安装MAT插件

b. 在eclipse里切换到Memory Analysis视图

c. 用MAT打开heap profile文件。

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907163118749)

[Memory Analyzer插件下载](http://www.eclipse.org/mat/downloads.php)

直接看到下面Action窗口，有4种Action来分析heap profile，介绍其中最常用的2种:

\- **Histogram**：这个使用的最多，跟上面的jmap -histo 命令类似，只是在MAT里面可以用GUI来展示应用系统各个类产生的实例。

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907163309052)

Shllow Heap排序后发现 Cms_Organization 这个类占用的内存比较多（没有得到及时GC），查看引用：

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907163424065)

分析引用栈，找到无效引用，打开源码：

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907163540520)

有问题的源码如下：



```java
1. **public** **class** RefreshCmsOrganizationStruts **implements** Runnable{ 
2.  
3.   **private** **final** **static** Logger logger = Logger.getLogger(RefreshCmsOrganizationStruts.**class**); 
4.    
5.   **private** List<Cms_Organization> organizations; 
6.  
7.   **private** OrganizationDao organizationDao = (OrganizationDao) WebContentBean 
8. ​      .getInstance().getBean("organizationDao"); 
9.   **public** RefreshCmsOrganizationStruts(List<Cms_Organization> organizations) { 
10. ​    **this**.organizations = organizations; 
11.   } 
12.  
13.   **public** **void** run() { 
14. ​    Iterator<Cms_Organization> iter = organizations.iterator(); 
15. ​    Cms_Organization organization = **null**; 
16. ​    **while** (iter.hasNext()) { 
17. ​      organization = iter.next(); 
18. ​      **synchronized** (organization) { 
19. ​        **try** { 
20. ​          organizationDao.refreshCmsOrganizationStrutsInfo(organization.getOrgaId()); 
21. ​          organizationDao.refreshCmsOrganizationResourceInfo(organization.getOrgaId()); 
22. ​          organizationDao.sleep(); 
23. ​        } **catch** (Exception e) { 
24. ​          logger.debug("RefreshCmsOrganizationStruts organization = " + organization.getOrgaId(), e); 
25. ​        } 
26. ​      } 
27. ​    } 
28.   } 
29.  
30. } 
```

**分析源码，定时任务定时调用，每次调用生成10个线程处理，而它又使用了非线程安全的List对象，导致List对象无法被GC收集，所以这里将List替换为CopyOnWriteArrayList 。**



\- **Dominator Tree**：这个使用的也比较多，显示大对象的占用率。

![img](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\20160907164212086)

同样的打开源码：



```java
1. **public** **class** CategoryCacheJob **extends** QuartzJobBean **implements** StatefulJob { 
2.    
3.   **private** **static** **final** Logger LOGGER = Logger.getLogger(CategoryCacheJob.**class**); 
4.    
5.   **public** **static** Map<String,List<Cms_Category>> cacheMap = **new** java.util.HashMap<String,List<Cms_Category>>(); 
6.  
7.   @Override 
8.   **protected** **void** executeInternal(JobExecutionContext ctx) **throws** JobExecutionException { 
9. ​    **try** { 
10. ​      //LOGGER.info("======= 缓存编目树开始 ======="); 
11. ​      MongoBaseDao mongoBaseDao = (MongoBaseDao) BeanLocator.getInstance().getBean("mongoBaseDao"); 
12. ​      MongoOperations mongoOperations = mongoBaseDao.getMongoOperations(); 
13. ​       
14. ​      /* 
15. ​      LOGGER.info("1.缓存基础教育编目树"); 
16. ​      Query query = Query.query(Criteria.where("isDel").is("0").and("categoryType").is("F")); 
17. ​      query.sort().on("orderNo", Order.ASCENDING); 
18. ​      List<Cms_Category> list = mongoOperations.find(query, Cms_Category.class); 
19. ​      String key = query.toString().replaceAll("\\{|\\}|\\p{Cntrl}|\\p{Space}", ""); 
20. ​      key += "_CategoryCacheJob"; 
21. ​      cacheMap.put(key, list); 
22. ​      */ 
23. ​       
24. ​      //LOGGER.info("2.缓存职业教育编目树"); 
25. ​      Query query2 = Query.query(Criteria.where("isDel").is("0").and("categoryType").in("JMP","JHP")); 
26. ​      query2.sort().on("orderNo", Order.ASCENDING); 
27. ​      List<Cms_Category> list2 = mongoOperations.find(query2, Cms_Category.**class**); 
28. ​      String key2 = query2.toString().replaceAll("\\{|\\}|\\p{Cntrl}|\\p{Space}", ""); 
29. ​      key2 += "_CategoryCacheJob"; 
30. ​      cacheMap.put(key2, list2); 
31. ​       
32. ​      //LOGGER.info("3.缓存专题教育编目树"); 
33. ​      Query query3 = Query.query(Criteria.where("isDel").is("0").and("categoryType").is("JS")); 
34. ​      query3.sort().on("orderNo", Order.ASCENDING); 
35. ​      List<Cms_Category> list3 = mongoOperations.find(query3, Cms_Category.**class**); 
36. ​      String key3 = query3.toString().replaceAll("\\{|\\}|\\p{Cntrl}|\\p{Space}", ""); 
37. ​      key3 += "_CategoryCacheJob"; 
38. ​      cacheMap.put(key3, list3); 
39. ​       
40. ​      //LOGGER.info("======= 缓存编目树结束 ======="); 
41. ​    } **catch**(Exception ex) { 
42. ​      LOGGER.error(ex.getMessage(), ex); 
43. ​      LOGGER.info("======= 缓存编目树出错 ======="); 
44. ​    } 
45.   } 
46.  
47. } 
```

这里的HashMap也有问题：居然使用定时任务，在容器启动之后定时将数据放到Map里面做缓存？这里修改这部分代码，替换为使用memcached缓存即可。

内存泄漏的原因分析，总结出来只有一条：**存在无效的引用！**良好的编码规范以及合理使用设计模式有助于解决此类问题。