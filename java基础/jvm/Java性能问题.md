





# top

  top命令是我们最常用的Linux命令之一，它可以实时的显示当前正在执行的进程的CPU使用率，
  内存使用率等系统信息。
	top -Hp pid 可以查看线程的系统资源使用情况。

	# PID  进程id
	# USER 进程所有者的用户名     
	# PR   优先级
	# NI   nice值。负值表示高优先级，正值表示低优先级 
	# VIRT virtual memory usage 虚拟内存
		1、进程“需要的”虚拟内存大小，包括进程使用的库、代码、数据等
		2、假如进程申请100m的内存，但实际只使用了10m，那么它会增长100m，而不是实际的使用量
	# RES  resident memory usage 常驻内存
		1、进程当前使用的内存大小，但不包括swap out
		2、包含其他进程的共享
		3、如果申请100m的内存，实际使用10m，它只增长10m，与VIRT相反
		4、关于库占用内存的情况，它只统计加载的库文件所占内存大小
	# SHR  shared memory 共享内存
		1、除了自身进程的共享内存，也包括其他进程的共享内存
		2、虽然进程只使用了几个共享库的函数，但它包含了整个共享库的大小
		3、计算某个进程所占的物理内存大小公式：RES – SHR
		4、swap out后，它将会降下来
	# S      进程状态。（D=不可中断的睡眠状态，R=运行，S=睡眠，T=跟踪/停止，Z=僵尸进程）
	# %CPU   上次更新到现在的CPU时间占用百分比
	# %MEM   进程使用的物理内存百分比   
	# TIME+  进程使用的CPU时间总计，单位1/100秒
	# COMMAND 命令名/命令行



# jps

命令格式：

```
jps[options][hostid]
```

```
1. -q  只输出LVMID，省略主类的名称
2. -m	输出虚拟机进程启动时传递到主类main()函数的参数。
3. -l	输出主类的全名，如果进程执行的是jar包，输出jar路径
4. -v	输出虚拟机进程启动时的JVM参数
```




# pidstat
  	pidstat 是 Sysstat 中的一个组件，也是一款功能强大的性能监测工具，top 和 vmstat 两个命令都是监测进程的	内存、CPU 以及 I/O 使用情况， 而 pidstat 命令可以检测到线程级别的。pidstat命令线程切换字段说明如下：
  	UID ：被监控任务的真实用户ID。
  	TGID ：线程组ID。
  	TID：线程ID。
  	cswch/s：主动切换上下文次数，这里是因为资源阻塞而切换线程，比如锁等待等情况。
  	nvcswch/s：被动切换上下文次数，这里指CPU调度切换了线程。
  	pidstat -p 87093 -w 1 10

- -u：默认的参数，显示各个进程的cpu使用统计

- -r：显示各个进程的内存使用统计

- -d：显示各个进程的IO使用情况

- -p：指定进程号

- -w：显示每个进程的上下文切换情况

- -t：显示选择任务的线程的统计信息外的额外信息

- -T { TASK | CHILD | ALL }

  这个选项指定了pidstat监控的。TASK表示报告独立的task，CHILD关键字表示报告进程下所有线程统计信息。ALL表示报告独立的task和task下面的所有线程。

  注意：task和子线程的全局的统计信息和pidstat选项无关。这些统计信息不会对应到当前的统计间隔，这些统计信息只有在子线程kill或者完成的时候才会被收集。

- -V：版本号

- -h：在一行上显示了所有活动，这样其他程序可以容易解析。

- -I：在SMP环境，表示任务的CPU使用率/内核数量

- -l：显示命令名和所有参数

# jstack 

命令格式

```
jstack[option]vmid
```

**jstack  threadID | grep tid(十六进制threadId) -A60 打印前六十行**

jstack（Stack Trace for Java）命令用于生成虚拟机当前时刻的线程快照（一般称为 threaddump或者javacore文件）。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈 的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循 环、请求外部资源导致的长时间等待等都是导致线程长时间停顿的常见原因。线程出现停顿 的时候通过jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做些 什么事情，或者等待着什么资源。

	-F 当正常输出的请求不被响应时，强制输出线程堆栈。
	-l 除堆栈外，显示关于锁的附加信息。
	-m 如果调用到本地方法的话可以显示C/C++的堆栈。

示例：

```
C：\Users\IcyFenix＞jstack-l 3500
2010-11-19 23：11：26
Full thread dump Java HotSpot（TM）64-Bit Server VM（17.1-b03 mixed mode）：
"[ThreadPool Manager]-Idle Thread"daemon prio=6 tid=0x0000000039dd4000 nid=0xf50 in Object.wait（）[0x000000003c96f000]
java.lang.Thread.State：WAITING（on object monitor）
at java.lang.Object.wait（Native Method）
-waiting on＜0x0000000016bdcc60＞（a org.eclipse.equinox.internal.util.impl.tpt.threadpool.Executor）
at java.lang.Object.wait（Object.java：485）
at org.eclipse.equinox.internal.util.impl.tpt.threadpool.Executor.run（Executor.java：106）
-locked＜0x0000000016bdcc60＞（a org.eclipse.equinox.internal.util.impl.tpt.threadpool.Executor）
Locked ownable synchronizers：
-None
```

在JDK 1.5中，java.lang.Thread类新增了一个getAllStackTraces（）方法用于获取虚拟机 中所有线程的StackTraceElement对象。使用这个方法可以通过简单的几行代码就完成jstack的 大部分功能，在实际项目中不妨调用这个方法做个管理员页面，可以随时使用浏览器来查看 线程堆栈，

```html
＜%@page import="java.util.Map"%＞
＜html＞
＜head＞
＜title＞服务器线程信息＜/title＞
＜/head＞
＜body＞
＜pre＞
＜%
for（Map.Entry＜Thread,StackTraceElement[]＞stackTrace：Thread.
getAllStackTraces（）.entrySet（））{
Thread thread=（Thread）stackTrace.getKey（）；
StackTraceElement[]stack=（StackTraceElement[]）stackTrace.getValue（）；
if（thread.equals（Thread.currentThread（）））{
continue；
}
out.print（"\n线程："+thread.getName（）+"\n"）；
for（StackTraceElement element：stack）{
out.print（"\t"+element+"\n"）；
}
}
%＞
＜/pre＞
＜/body＞
＜/html＞
```

# HSDIS

JIT生成代码反汇编

在Java虚拟机规范中，详细描述了虚拟机指令集中每条指令的执行过程、执行前后对操 作数栈、局部变量表的影响等细节。这些细节描述与Sun的早期虚拟机（Sun Classic VM）高 度吻合，但随着技术的发展，高性能虚拟机真正的细节实现方式已经渐渐与虚拟机规范所描 述的内容产生了越来越大的差距，虚拟机规范中的描述逐渐成了虚拟机实现的“概念模 型”——即实现只能保证规范描述等效。基于这个原因，我们分析程序的执行语义问题（虚 拟机做了什么）时，在字节码层面上分析完全可行，但分析程序的执行行为问题（虚拟机是 怎样做的、性能如何）时，在字节码层面上分析就没有什么意义了，需要通过其他方式解 决。 分析程序如何执行，通过软件调试工具（GDB、Windbg等）来断点调试是最常见的手 段，但是这样的调试方式在Java虚拟机中会遇到很大困难，因为大量执行代码是通过JIT编译 器动态生成到CodeBuffer中的，没有很简单的手段来处理这种混合模式的调试（不过相信虚 拟机开发团队内部肯定是有内部工具的）。因此，不得不通过一些特别的手段来解决问题， 基于这种背景，本节的主角——HSDIS插件就正式登场了。 HSDIS是一个Sun官方推荐的HotSpot虚拟机JIT编译代码的反汇编插件，它包含在HotSpot 虚拟机的源码之中，但没有提供编译后的程序。在Project Kenai的网站[1]也可以下载到单独的 源码。它的作用是让HotSpot的-XX：+PrintAssembly指令调用它来把动态生成的本地代码还 原为汇编代码输出，同时还生成了大量非常有价值的注释，这样我们就可以通过输出的代码 来分析问题。读者可以根据自己的操作系统和CPU类型从Project Kenai的网站上下载编译好 的插件，直接放到JDK_HOME/jre/bin/client和JDK_HOME/jre/bin/server目录中即可。如果没 有找到所需操作系统（譬如Windows的就没有）的成品，那就得自己使用源码编译一下[2]。 还需要注意的是，如果读者使用的是Debug或者FastDebug版的HotSpot，那可以直接通 过-XX：+PrintAssembly指令使用插件；如果使用的是Product版的HotSpot，那还要额外加入 一个-XX：+UnlockDiagnosticVMOptions参数。笔者以代码清单4-6中的简单测试代码为例演 示一下这个插件的使用。

```java
public class Bar{
int a=1；
static int b=2；
public int sum(int c){
return a+b+c；
}
public static void main（String[]args）{
new Bar().sum(3)；
}
}

```

编译这段代码，并使用以下命令执行。

```
java-XX：+PrintAssembly-Xcomp-XX：CompileCommand=dontinline，*Bar.sum-XX：Compi leCommand=compileonly，*Bar.sum test.Bar
```

其中，参数-Xcomp是让虚拟机以编译模式执行代码，这样代码可以“偷懒”，不需要执行 足够次数来预热就能触发JIT编译[3]。两个-XX：CompileCommand意思是让编译器不要内联 sum（）并且只编译sum（），-XX：+PrintAssembly就是输出反汇编内容。如果一切顺利的 话，那么屏幕上会出现类似下面代码清单4-7所示的内容

```
[Disassembling for mach='i386']
[Entry Point]
[Constants]
#{method}'sum''（I）I'in'test/Bar'
#this：ecx='test/Bar'
#parm0：edx=int
#[sp+0x20]（sp of caller）
……
0x01cac407：cmp 0x4（%ecx），%eax
0x01cac40a：jne 0x01c6b050；{runtime_call}
[Verified Entry Point]
0x01cac410：mov%eax，-0x8000（%esp）
0x01cac417：push%ebp
0x01cac418：sub$0x18，%esp；*aload_0 ；-test.Bar：sum@0（line 8）
；block B0[0，10]
0x01cac41b：mov 0x8（%ecx），%eax；*getfield a ；-test.Bar：sum@1（line 8）
0x01cac41e：mov$0x3d2fad8，%esi；{oop（a
'java/lang/Class'='test/Bar'）}
0x01cac423：mov 0x68（%esi），%esi；*getstatic b ；-test.Bar：sum@4（line 8）
0x01cac426：add%esi，%eax
0x01cac428：add%edx，%eax
0x01cac42a：add$0x18，%esp
0x01cac42d：pop%ebp
0x01cac42e：test%eax，0x2b0100；{poll_return}
0x01cac434：ret
```

上段代码并不多，下面一句句进行说明。

```
1. mov%eax，-0x8000（%esp）：检查栈溢。
2. push%ebp：保存上一栈帧基址。
3. sub$0x18，%esp：给新帧分配空间。
4. mov 0x8（%ecx），%eax：取实例变量a，这里0x8（%ecx）就是ecx+0x8的意思，前 面“[Constants]”节中提示了“this：ecx='test/Bar'”，即ecx寄存器中放的就是this对象的地址。偏 移0x8是越过this对象的对象头，之后就是实例变量a的内存位置。这次是访问“Java堆”中的数 据。
5. mov$0x3d2fad8，%esi：取test.Bar在方法区的指针。
6. mov 0x68（%esi），%esi：取类变量b，这次是访问“方法区”中的数据。
7. add%esi，%eax和add%edx，%eax：做两次加法，求a+b+c的值，前面的代码把a放在 eax中，把b放在esi中，而c在[Constants]中提示了，“parm0：edx=int”，说明c在edx中。
8. add$0x18，%esp：撤销栈帧。
9. pop%ebp：恢复上一栈帧
10. test%eax，0x2b0100：轮询方法返回处的SafePoint。
11. ret：方法返回。
```

[1]Project Kenai：http://kenai.com/projects/base-hsdis。 

[2]HLLVM圈子中有已编译好的：http://hllvm.group.iteye.com/。 

[3]-Xcomp在较新的HotSpot中被移除了，如果读者的虚拟机无法使用这个参数，请加个循环 预热代码，触发JIT编译。

# jstat 

jstat（JVM Statistics Monitoring Tool）是用于监视虚拟机各种运行状态信息的命令行工 具。它可以显示本地或者远程[1]虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数 据，在没有GUI图形界面，只提供了纯文本控制台环境的服务器上，它将是运行期定位虚拟 机性能问题的首选工具。

t命令格式

```
jstat[option vmid[interval[s|ms][count]]]
```

如果是本地虚拟机进程，VMID与 LVMID是一致的，如果是远程虚拟机进程，那VMID的格式应当是：

```
[protocol：][//]lvmid[@hostname[：port]/servername]
```

参数interval和count代表查询间隔和次数，如果省略这两个参数，说明只查询一次。假设 需要每250毫秒查询一次进程2764垃圾收集状况，一共查询20次，那命令应当是：

```
jstat-gc 2764 250 20
```

选项option代表着用户希望查询的虚拟机信息，主要分为3类：类装载、垃圾收集、运行 期编译状况:

	-class  监视类装载，卸载数量、总空间以及类装载所耗费的时间
	-gc 	监视Java堆状况，包括eden区，两个survicor区、老年代、永久代等的容量、已用空间，GC时间合计等信息
	-gccapacity		监视内容与GC基本相同、但输出主要关注java堆各个区域使用到的最大最小空间
	-gcutil		监视内容与GC基本相同、但输出主要关注已使用占总空间的百分比
	-gccause	与gcutil功能一样，但是会额外输出导致上一次GC产生的原因
	-gcnew		监视新生代GC情况
	-gcnewcapacity	监视内容与-gcnew基本相同，输出主要关注使用到的最大最小空间
	-gcold		监视老年代GC状况
	-gcoldcapacity	监视内容与-gcold基本相同，输出主要关注使用到的最大最小空间
	-gcpermcapacity 输出永久代使用到的最大最小空间
	-compiler 	输出JIT编译过的方法，耗时等信息。
	-printcompilation 输出已被JIT编译的方法

  它可以检测java程序运行的实时情况，包括堆内存信息和垃圾回收信息，我们常常用来查看程序垃圾回收情况。
  常用的命令是jstat -gc pid。信息字段说明如下：

![image-20210129110921699](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\image-20210129110921699.png)

```
S0C：年轻代中 To Survivor 的容量（单位 KB）；
S1C：年轻代中 From Survivor 的容量（单位 KB）；
S0U：年轻代中 To Survivor 目前已使用空间（单位 KB）；
S1U：年轻代中 From Survivor 目前已使用空间（单位 KB）；
EC：年轻代中 Eden 的容量（单位 KB）；
EU：年轻代中 Eden 目前已使用空间（单位 KB）；
OC：老年代的容量（单位 KB）；
OU：老年代目前已使用空间（单位 KB）；
MC：元空间的容量（单位 KB）；
MU：元空间目前已使用空间（单位 KB）；
YGC：从应用程序启动到采样时年轻代中 gc 次数；
YGCT：从应用程序启动到采样时年轻代中 gc 所用时间 (s)；
FGC：从应用程序启动到采样时 老年代（Full Gc）gc 次数；
FGCT：从应用程序启动到采样时 老年代代（Full Gc）gc 所用时间 (s)；
GCT：从应用程序启动到采样时 gc 用的总时间 (s)。
```

```
D：\Develop\Java\jdk1.6.0_21\bin＞jstat-gcutil 2764
S0 S1 E O P YGC YGCT FGC FGCT GCT
0.00 0.00 6.20 41.42 47.20 16 0.105 3 0.472 0.577
```

查询结果表明：这台服务器的新生代Eden区（E，表示Eden）使用了6.2%的空间，两个 Survivor区（S0、S1，表示Survivor0、Survivor1）里面都是空的，老年代（O，表示Old）和 永久代（P，表示Permanent）则分别使用了41.42%和47.20%的空间。程序运行以来共发生 Minor GC（YGC，表示Young GC）16次，总耗时0.105秒，发生Full GC（FGC，表示Full GC）3次，Full GC总耗时（FGCT，表示Full GC Time）为0.472秒，所有GC总耗时（GCT， 表示GC Time）为0.577秒。

# jmap	

命令格式：

```
jmap[option]vmid
jmap -histo:live
```

jmap（Memory Map for Java）命令用于生成堆转储快照（一般称为heapdump或dump文 件）。如果不使用jmap命令，要想获取Java堆转储快照，还有一些比较“暴力”的手段：譬如 在第2章中用过的-XX：+HeapDumpOnOutOfMemoryError参数，可以让虚拟机在OOM异常出 现之后自动生成dump文件，通过-XX：+HeapDumpOnCtrlBreak参数则可以使用[Ctrl]+[Break] 键让虚拟机生成dump文件，又或者在Linux系统下通过Kill-3命令发送进程退出信号“吓唬”一 下虚拟机，也能拿到dump文件。 jmap的作用并不仅仅是为了获取dump文件，它还可以查询finalize执行队列、Java堆和永 久代的详细信息，如空间使用率、当前用的是哪种收集器等。 和jinfo命令一样，jmap有不少功能在Windows平台下都是受限的，除了生成dump文件的dump选项和用于查看每个类的实例、空间占用统计的-histo选项在所有操作系统都提供之 外，其余选项都只能在Linux/Solaris下使用。

jmap[option]vmid

要注意的是在使用CMS GC 情况下，jmap -heap的执行有可能会导致JAVA 进程挂起

    -dump 生成java堆转储快照，格式为：-dump:[live,]format=b,file=<filename>,其中live子参数说明是否只dump出存活的对象。
    -finalizerinfo 显示在F-Queue中等待Finalize方法的对象，只在Linux/Solaris平台下有用。
    -heap 显示java堆详细信息，如使用哪种回收器、参数配置、分代状况等，只在Linux/Solaris平台下有用。
    -histo 显示堆中对象统计信息，包括类、实例数量、合计容量。
    -permstat 以ClassLoader为统计口径显示永久代内存状态。只在Linux/Solaris平台下有用。
    -F 当虚拟机进程对-dump 选项没有响应时，可使用这个选项强制生成dump快照，只在Linux/Solaris平台下有用。
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${目录}，表示当JVM发生OOM时，自动生成DUMP文件。
    jmap也是JDK工具命令，他可以查看堆内存的初始化信息以及堆内存的使用情况，还可以生成dump文件来进行详细分析。
    查看堆内存情况命令jmap -heap pid。
    jmap -dump:format=b,file=outfile 3024可以将3024进程的内存heap输出出来到outfile文件里;

示例

```
C：\Users\IcyFenix＞jmap-dump：format=b,file=eclipse.bin 3500
Dumping heap to C：\Users\IcyFenix\eclipse.bin……
Heap dump file created
```

# jhat

虚拟机堆转储快照分析工具

Sun JDK提供jhat（JVM Heap Analysis Tool）命令与jmap搭配使用，来分析jmap生成的堆 转储快照。jhat内置了一个微型的HTTP/HTML服务器，生成dump文件的分析结果后，可以在 浏览器中查看。不过实事求是地说，在实际工作中，除非笔者手上真的没有别的工具可用， 否则一般都不会去直接使用jhat命令来分析dump文件，主要原因有二：一是一般不会在部署 应用程序的服务器上直接分析dump文件，即使可以这样做，也会尽量将dump文件复制到其 他机器[1]上进行分析，因为分析工作是一个耗时而且消耗硬件资源的过程，既然都要在其他 机器进行，就没有必要受到命令行工具的限制了；另一个原因是jhat的分析功能相对来说比 较简陋，后文将会介绍到的VisualVM，以及专业用于分析dump文件的Eclipse Memory Analyzer、IBM HeapAnalyzer [2]等工具，都能实现比jhat更强大更专业的分析功能。代码清单4- 3演示了使用jhat分析4.2.4节中采用jmap生成的Eclipse IDE的内存快照文件。

```
C：\Users\IcyFenix＞jhat eclipse.bin
Reading from eclipse.bin……
Dump file created Fri Nov 19 22：07：21 CST 2010
Snapshot read,resolving……
Resolving 1225951 objects……
Chasing references,expect 245 dots……
Eliminating duplicate references……
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.
```

屏幕显示“Server is ready.”的提示后，用户在浏览器中键入http://localhost：7000/就可以 看到分析结果



# mat内存工具

  MAT(Memory Analyzer Tool)工具是eclipse的一个插件(MAT也可以单独使用)，它分析大内存的dump文件时，
  可以非常直观的看到各个对象在堆空间中所占用的内存大小、类实例数量、对象引用关系、利用OQL对象查询，
  以及可以很方便的找出对象GC Roots的相关信息。

# jinfo 
命令格式：

```
jinfo[option]pid
```

实时地查看和调整虚拟机各项参数。使用jps 命令的-v参数可以查看虚拟机启动时显式指定的参数列表，但如果想知道未被显式指定的参 数的系统默认值，除了去找资料外，就只能使用jinfo的-flag选项进行查询了（如果只限于 JDK 1.6或以上版本的话，使用java-XX：+PrintFlagsFinal查看参数默认值也是一个很好的选 择），jinfo还可以使用-sysprops选项把虚拟机进程的System.getProperties（）的内容打印出 来。这个命令在JDK 1.5时期已经随着Linux版的JDK发布，当时只提供了信息查询的功 能，JDK 1.6之后，jinfo在Windows和Linux平台都有提供，并且加入了运行期修改参数的能 力，可以使用-flag[+|-]name或者-flag name=value修改一部分运行期可写的虚拟机参数值。 JDK 1.6中，jinfo对于Windows平台功能仍然有较大限制，只提供了最基本的-flag选项。

```
C：\＞jinfo-flag CMSInitiatingOccupancyFraction 1444
-XX：CMSInitiatingOccupancyFraction=85
```

# VisualVM

多合一故障处理工具  (深入理解java虚拟机4.3.2)

​	VisualVM（All-in-One Java Troubleshooting Tool）是到目前为止随JDK发布的功能最强大 的运行监视和故障处理程序，并且可以预见在未来一段时间内都是官方主力发展的虚拟机故 障处理工具。官方在VisualVM的软件说明中写上了“All-in-One”的描述字样，预示着它除了 运行监视、故障处理外，还提供了很多其他方面的功能。如性能分析 （Profiling），VisualVM的性能分析功能甚至比起JProfiler、YourKit等专业且收费的Profiling 工具都不会逊色多少，而且VisualVM的还有一个很大的优点：不需要被监视的程序基于特殊 Agent运行，因此它对应用程序的实际性能的影响很小，使得它可以直接应用在生产环境 中。这个优点是JProfiler、YourKit等工具无法与之媲美的。

VisualVM基于NetBeans平台开发，因此它一开始就具备了插件扩展功能的特性，通过插 件扩展支持，VisualVM可以做到：

- 显示虚拟机进程以及进程的配置、环境信息（jps、jinfo）。
- 监视应用程序的CPU、GC、堆、方法区以及线程的信息（jstat、jstack）。
- dump以及分析堆转储快照（jmap、jhat）。
- 方法级的程序运行性能分析，找出被调用最多、运行时间最长的方法。
- 离线程序快照：收集程序的运行时配置、线程dump、内存dump等信息建立一个快照， 可以将快照发送开发者处进行Bug反馈。

# jcmd

  相比 jstat 功能更为全面的工具，可用于获取目标 Java 进程的性能统计、JFR、内存使用、垃圾收集、线程堆栈、JVM 运行时间等信息。  

  

# JCONSOLE

![连接 Jconsole](E:\learn\git\repository\笔记\java-note\java基础\jvm\img\1JConsole连接.png)

如果需要使用 JConsole 连接远程进程，可以在远程 Java 程序启动时加上下面这些参数:

```shell
-Djava.rmi.server.hostname=外网访问 ip 地址 
-Dcom.sun.management.jmxremote.port=60001   //监控的端口号
-Dcom.sun.management.jmxremote.authenticate=false   //关闭认证
-Dcom.sun.management.jmxremote.ssl=false
```

在使用 JConsole 连接时，远程进程地址如下：

```
外网访问 ip 地址:60001 
```

# 实例：

### CPU占满
```
1、top -Hp 32805 查看Java线程情况
2、执行 printf '%x' 32826 获取16进制的线程id，用于dump信息查询。
3、jstack 32805 |grep -A 20 id 来查看下详细的dump信息
```

### 内存泄露
模拟内存泄漏借助了ThreadLocal对象来完成，ThreadLocal是一个线程私有变量，可以绑定到线程上， 在整个线程的生命周期都会存在，但是由于ThreadLocal的特殊性，ThreadLocal是基于ThreadLocalMap实现的， ThreadLocalMap的Entry继承WeakReference，而Entry的Key是WeakReference的封装，换句话说Key就是弱引用， 弱引用在下次GC之后就会被回收，如果ThreadLocal在set之后不进行后续的操作，因为GC会把Key清除掉，但是Value由于线程还在存活， 所以Value一直不会被回收，最后就会发生内存泄漏。 我们给启动加上堆内存大小限制，同时设置内存溢出的时候输出堆栈快照并输出日志。 

**java -jar -Xms500m -Xmx500m -XX:+HeapDumpOnOutOfMemoryError  XX:HeapDumpPath=/tmp/heapdump.hprof -XX:+PrintGCTimeStamps -XX:+PrintGCDetails - Xloggc:/tmp/heaplog.log xxx.jar**
启动成功后我们循环执行100次,**for i in {1..500}; do curl localhost:8080/memory/leak**
我们用**jstat -gc pid** 命令来看看程序的GC情况。
MAT工具来分析下堆 Dump 文件
	

```
/**
​ *模拟内存泄漏
​ */
@GetMapping(value = "/memory/leak")
public String leak() {
​	System.out.println("模拟内存泄漏");
​	ThreadLocal<Byte[]> localVariable = new ThreadLocal<Byte[]>();
​	localVariable.set(new Byte[4096 * 1024]);// 为线程添加变量
​	return "ok";
}
```

### 死锁

死锁会导致耗尽线程资源，占用内存，表现就是内存占用升高，CPU不一定会飙升(看场景决定)，
如果是直接new线程，会导致JVM内存被耗尽，报无法创建线程的错误，这也是体现了使用线程池的好处。

通过ps -ef|grep java命令找出 Java 进程 pid，执行jstack pid 即可出现java线程堆栈信息， 这里发现了5个死锁，我们只列出其中一个，很明显线程pool-1-thread-2锁住了0x00000000f8387d88等待0x00000000f8387d98锁， 线程pool-1-thread-1锁住了0x00000000f8387d98等待锁0x00000000f8387d88,这就产生了死锁。

		ExecutorService service = new ThreadPoolExecutor(4, 10,
	        0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024),
	        Executors.defaultThreadFactory(),
	        new ThreadPoolExecutor.AbortPolicy());
		   /**
			 * 模拟死锁
			 */
			@GetMapping("/cpu/test")
			public String testCPU() throws InterruptedException {
				System.out.println("请求cpu");
				Object lock1 = new Object();
				Object lock2 = new Object();
				service.submit(new DeadLockThread(lock1, lock2), "deadLookThread-" + new Random().nextInt());
				service.submit(new DeadLockThread(lock2, lock1), "deadLookThread-" + new Random().nextInt());
				return "ok";
			}
	
		public class DeadLockThread implements Runnable {
			private Object lock1;
			private Object lock2;
	
			public DeadLockThread1(Object lock1, Object lock2) {
				this.lock1 = lock1;
				this.lock2 = lock2;
			}
	
			@Override
			public void run() {
				synchronized (lock2) {
					System.out.println(Thread.currentThread().getName()+"get lock2 and wait lock1");
					try {
						TimeUnit.MILLISECONDS.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (lock1) {
						System.out.println(Thread.currentThread().getName()+"get lock1 and lock2 ");
					}
				}
			}
		}
### 线程频繁切换

上下文切换会导致将大量CPU时间浪费在寄存器、内核栈以及虚拟内存的保存和恢复上，导致系统整体性能下降。
当你发现系统的性能出现明显的下降时候，需要考虑是否发生了大量的线程上下文切换。

```
@GetMapping(value = "/thread/swap")
public String theadSwap(int num) {
	System.out.println("模拟线程切换");
	for (int i = 0; i < num; i++) {
		new Thread(new ThreadSwap1(new AtomicInteger(0)),"thread-swap"+i).start();
	}
	return "ok";
}
public class ThreadSwap1 implements Runnable {
	private AtomicInteger integer;

​	public ThreadSwap1(AtomicInteger integer) {
​		this.integer = integer;
​	}

​	@Override
​	public void run() {
​		while (true) {
​			integer.addAndGet(1);
​			Thread.yield(); //让出CPU资源
​		}
​	}
}
```

这里我创建多个线程去执行基础的原子+1操作，然后让出 CPU 资源，理论上 CPU 就会去调度别的线程，
我们请求接口创建100个线程看看效果如何，curl localhost:8080/thread/swap?num=100。接口请求成功后，
我们执行 vmstat 1 10，表示每1秒打印一次，打印10次，线程切换采集结果如下：

```
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
101  0 128000 878384    908 468684    0    0     0     0 4071 8110498 14 86  0  0  0
100  0 128000 878384    908 468684    0    0     0     0 4065 8312463 15 85  0  0  0
100  0 128000 878384    908 468684    0    0     0     0 4107 8207718 14 87  0  0  0
100  0 128000 878384    908 468684    0    0     0     0 4083 8410174 14 86  0  0  0
100  0 128000 878384    908 468684    0    0     0     0 4083 8264377 14 86  0  0  0
100  0 128000 878384    908 468688    0    0     0   108 4182 8346826 14 86  0  0  0
```


这里我们关注4个指标，r,cs,us,sy。

r=100,说明等待的进程数量是100，线程有阻塞。

cs=800多万，说明每秒上下文切换了800多万次，这个数字相当大了。

us=14，说明用户态占用了14%的CPU时间片去处理逻辑。

sy=86，说明内核态占用了86%的CPU，这里明显就是做上下文切换工作了。

我们通过top命令以及top -Hp pid查看进程和线程CPU情况，发现Java线程CPU占满了，但是线程CPU使用情况很平均，
没有某一个线程把CPU吃满的情况。

```java
 PID  USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND
 87093 root      20   0 4194788 299056  13252 S 399.7 16.1  65:34.67 java
 
 PID USER      PR  NI    VIRT    RES    SHR S %CPU %MEM     TIME+ COMMAND
 87189 root      20   0 4194788 299056  13252 R  4.7 16.1   0:41.11 java
 87129 root      20   0 4194788 299056  13252 R  4.3 16.1   0:41.14 java
 87130 root      20   0 4194788 299056  13252 R  4.3 16.1   0:40.51 java
 87133 root      20   0 4194788 299056  13252 R  4.3 16.1   0:40.59 java
 87134 root      20   0 4194788 299056  13252 R  4.3 16.1   0:40.95 java
```

结合上面用户态CPU只使用了14%，内核态CPU占用了86%，可以基本判断是Java程序线程上下文切换导致性能问题。
我们使用pidstat命令来看看Java进程内部的线程切换数据，执行pidstat -p 87093 -w 1 10,采集数据如下：

```java
11:04:30 PM   UID       TGID       TID   cswch/s nvcswch/s  Command
11:04:30 PM     0         -     87128      0.00     16.07  |__java
11:04:30 PM     0         -     87129      0.00     15.60  |__java
11:04:30 PM     0         -     87130      0.00     15.54  |__java
11:04:30 PM     0         -     87131      0.00     15.60  |__java
11:04:30 PM     0         -     87132      0.00     15.43  |__java
11:04:30 PM     0         -     87133      0.00     16.02  |__java
11:04:30 PM     0         -     87134      0.00     15.66  |__java
11:04:30 PM     0         -     87135      0.00     15.23  |__java
11:04:30 PM     0         -     87136      0.00     15.33  |__java
11:04:30 PM     0         -     87137      0.00     16.04  |__java
```
根据上面采集的信息，我们知道Java的线程每秒切换15次左右，正常情况下，应该是个位数或者小数。
结合这些信息我们可以断定Java线程开启过多，导致频繁上下文切换，从而影响了整体性能。

cswch/S：自愿上下文切换 

进程无法获取所需资源导致的上下文切换。比如I/O，内存等系统资源不足时就会发生自愿上下文切换。

nvcswch/S：非自愿上下文切换

由于进程时间片时间已到等原因，被系统强制调度，进而发生的上下文切换。