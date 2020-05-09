#排查问题的工具清单
1. Linux命令类
    - tail    
       tail -300f shopbase.log #倒数300行并进入实时监听文件写入模式
    - grep
        ```java
           grep forest f.txt     #文件查找
           grep forest f.txt cpf.txt #多文件查找
           grep 'log' /home/admin -r -n #目录下查找所有符合关键字的文件
           cat f.txt | grep -i shopbase    
           grep 'shopbase' /home/admin -r -n --include *.{vm,java} #指定文件后缀
           grep 'shopbase' /home/admin -r -n --exclude *.{vm,java} #反匹配
           seq 10 | grep 5 -A 3    #上匹配
           seq 10 | grep 5 -B 3    #下匹配
           seq 10 | grep 5 -C 3    #上下匹配，平时用这个就妥了
           cat f.txt | grep -c 'SHOPBASE'
        ```
   - awk
   
        1  、基础命令
       ```java
       awk '{print $4,$6}' f.txt
       awk '{print NR,$0}' f.txt cpf.txt    
       awk '{print FNR,$0}' f.txt cpf.txt
       awk '{print FNR,FILENAME,$0}' f.txt cpf.txt
       awk '{print FILENAME,"NR="NR,"FNR="FNR,"$"NF"="$NF}' f.txt cpf.txt
       echo 1:2:3:4 | awk -F: '{print $1,$2,$3,$4}'
       ```
        2  、匹配
        ```java
        awk '/ldb/ {print}' f.txt   #匹配ldb
        awk '!/ldb/ {print}' f.txt  #不匹配ldb
        awk '/ldb/ && /LISTEN/ {print}' f.txt   #匹配ldb和LISTEN
        awk '$5 ~ /ldb/ {print}' f.txt #第五列匹配ldb
        ```
        3 、 内建变量
        ```java
        NR:NR表示从awk开始执行后，按照记录分隔符读取的数据次数，默认的记录分隔符为换行符，因此默认的就是读取的数据行数，NR可以理解为Number of Record的缩写。
        
        FNR:在awk处理多个输入文件的时候，在处理完第一个文件后，NR并不会从1开始，而是继续累加，因此就出现了FNR，每当处理一个新文件的时候，FNR就从1开始计数，FNR可以理解为File Number of Record。
        
        NF: NF表示目前的记录被分割的字段的数目，NF可以理解为Number of Field。
        ```
    - find
        ```java
        sudo -u admin find /home/admin /tmp /usr -name *.log(多个目录去找)
        find . -iname *.txt(大小写都匹配)
        find . -type d(当前目录下的所有子目录)
        find /usr -type l(当前目录下所有的符号链接)
        find /usr -type l -name "z*" -ls(符号链接的详细信息 eg:inode,目录)
        find /home/admin -size +250000k(超过250000k的文件，当然+改成-就是小于了)
        find /home/admin f -perm 777 -exec ls -l {} ; (按照权限查询文件)
        find /home/admin -atime -1  1天内访问过的文件
        find /home/admin -ctime -1  1天内状态改变过的文件    
        find /home/admin -mtime -1  1天内修改过的文件
        find /home/admin -amin -1  1分钟内访问过的文件
        find /home/admin -cmin -1  1分钟内状态改变过的文件    
        find /home/admin -mmin -1  1分钟内修改过的文件
        ```
     - pgm
     批量查询vm-shopbase满足条件的日志
        ```java
        批量查询vm-shopbase满足条件的日志
        ```
     - tsar   
     tsar是咱公司自己的采集工具。很好用, 将历史收集到的数据持久化在磁盘上，所以我们快速来查询历史的系统数据。当然实时的应用情况也是可以查询的啦。大部分机器上都有安装。   
     tsar可以查看最近一天的各项指标  
     tsar --live 可以查看实时指标，默认五秒一刷  
     tsar -d 20161218 指定查看某天的数据，貌似最多只能看四个月的数据  
     tsar --mem  
     tsar --load  
     tsar --cpu  
     当然这个也可以和-d参数配合来查询某天的单个指标的情况  
     - top  
     top除了看一些基本信息之外，剩下的就是配合来查询vm的各种问题了  
         ```java
           ps -ef | grep java
           top -H -p pid
         ``` 
       获得线程10进制转16进制后jstack去抓看这个线程到底在干啥
   
     - 其他  
         ```java
         netstat -nat|awk  '{print $6}'|sort|uniq -c|sort -rn 
         查看当前连接，注意close_wait偏高的情况，比如如下  
         ```
      
     #java
      -  jps  
          ```java
           sudo -u admin /opt/taobao/java/bin/jps -mlvV
          ```
      - jstack  
        1、普通用法:
        ```java
        sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jstack 2815
        ```
        2、native+java栈:  
        ```java
        sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jstack 2815
        ```  

      - jinfo  
      可看系统启动的参数，如下
          ```java
           sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jinfo -flags 2815
          ```
      - jmap  
        1、查看堆的情况  
        ```java
        sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jmap -heap 2815
        ```
        2、dump
        ```java
        1、sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jmap -dump:live,format=b,file=/tmp/heap2.bin 2815
        2、sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jmap -dump:format=b,file=/tmp/heap3.bin 2815
        ``` 
        3、看看堆都被谁占了? 再配合zprofiler和btrace，排查问题简直是如虎添翼
        ```java
        sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jmap -histo 2815 | head -10
        ```
      - jstat  
      jstat参数众多，但是使用一个就够了  
          ```java
           sudo -u admin /opt/taobao/install/ajdk-8_1_1_fp1-b52/bin/jstat -gcutil 2815 1000 
          ```    
      - jdb  
      时至今日，jdb也是经常使用的。 
      jdb可以用来预发debug,假设你预发的java_home是/opt/taobao/java/，远程调试端口是8000.那么  
          ```java
           sudo -u admin /opt/taobao/java/bin/jdb -attach 8000
          ```   
      -   