## Java内存模型（JMM）

JMM定义了Java 虚拟机(JVM)在计算机内存(RAM)中的工作方式。JVM是整个计算机虚拟模型，所以JMM是隶属于JVM的。从抽象的角度来看，JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory），本地内存中存储了该线程以读/写共享变量的副本。本地内存是JMM的一个抽象概念，并不真实存在。它涵盖了缓存、写缓冲区、寄存器以及其他的硬件和编译器优化。

![img](image/4222138-96ca2a788ec29dc2.png)

###  JVM对Java内存模型的实现

- 在JVM内部，Java内存模型把内存分成了两部分：线程栈区和堆区
   JVM中运行的每个线程都拥有自己的线程栈，线程栈包含了当前线程执行的方法调用相关信息，我们也把它称作调用栈。随着代码的不断执行，调用栈会不断变化。

  ![img](https:////upload-images.jianshu.io/upload_images/4222138-4c8b73be388ed0f0.png?imageMogr2/auto-orient/strip|imageView2/2/w/553/format/webp)

所有原始类型(boolean,byte,short,char,int,long,float,double)的局部变量都直接保存在线程栈当中，对于它们的值各个线程之间都是独立的。对于原始类型的局部变量，一个线程可以传递一个副本给另一个线程，当它们之间是无法共享的。
 堆区包含了Java应用创建的所有对象信息，不管对象是哪个线程创建的，其中的对象包括原始类型的封装类（如Byte、Integer、Long等等）。不管对象是属于一个成员变量还是方法中的局部变量，它都会被存储在堆区。
 一个局部变量如果是原始类型，那么它会被完全存储到栈区。 一个局部变量也有可能是一个对象的引用，这种情况下，这个本地引用会被存储到栈中，但是对象本身仍然存储在堆区。
 对于一个对象的成员方法，这些方法中包含局部变量，仍需要存储在栈区，即使它们所属的对象在堆区。 对于一个对象的成员变量，不管它是原始类型还是包装类型，都会被存储到堆区。Static类型的变量以及类本身相关信息都会随着类本身存储在堆区。



![img](https:////upload-images.jianshu.io/upload_images/4222138-1cc1cd7e5e09232c.png?imageMogr2/auto-orient/strip|imageView2/2/w/486/format/webp)

##  Java内存模型带来的问题

### 可见性问题

CPU中运行的线程从主存中拷贝共享对象obj到它的CPU缓存，把对象obj的count变量改为2。但这个变更对运行在右边CPU中的线程不可见，因为这个更改还没有flush到主存中：要解决共享对象可见性这个问题，我们可以使用java volatile关键字或者是加锁



![img](https:////upload-images.jianshu.io/upload_images/4222138-58dbd966b4f80fab.png?imageMogr2/auto-orient/strip|imageView2/2/w/520/format/webp)

### 竞争现象

线程A和线程B共享一个对象obj。假设线程A从主存读取Obj.count变量到自己的CPU缓存，同时，线程B也读取了Obj.count变量到它的CPU缓存，并且这两个线程都对Obj.count做了加1操作。此时，Obj.count加1操作被执行了两次，不过都在不同的CPU缓存中。如果这两个加1操作是串行执行的，那么Obj.count变量便会在原始值上加2，最终主存中的Obj.count的值会是3。然而下图中两个加1操作是并行的，不管是线程A还是线程B先flush计算结果到主存，最终主存中的Obj.count只会增加1次变成2，尽管一共有两次加1操作。 要解决上面的问题我们可以使用java synchronized代码块。



![img](https:////upload-images.jianshu.io/upload_images/4222138-0ad9904ab8a34470.png?imageMogr2/auto-orient/strip|imageView2/2/w/542/format/webp)

##  Java内存模型中的重排序

- 在执行程序时，为了提高性能，编译器和处理器常常会对指令做重排序。

### 重排序类型

![img](https:////upload-images.jianshu.io/upload_images/4222138-0531c2c33ca2f3d2.png?imageMogr2/auto-orient/strip|imageView2/2/w/1025/format/webp)

- 1）编译器优化的重排序。编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
- 2）指令级并行的重排序。现代处理器采用了指令级并行技术（Instruction-LevelParallelism，ILP）来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
- 3）内存系统的重排序。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行。

###  重排序与依赖性

- 数据依赖性
   如果两个操作访问同一个变量，且这两个操作中有一个为写操作，此时这两个操作之间就存在数据依赖性。数据依赖分为下列3种类型，这3种情况，只要重排序两个操作的执行顺序，程序的执行结果就会被改变。

  ![img](https:////upload-images.jianshu.io/upload_images/4222138-2c95d88191f5637b.png?imageMogr2/auto-orient/strip|imageView2/2/w/554/format/webp)

- 控制依赖性
   flag变量是个标记，用来标识变量a是否已被写入，在use方法中比变量i依赖if (flag)的判断，这里就叫控制依赖，如果发生了重排序，结果就不对了。

  ![img](https:////upload-images.jianshu.io/upload_images/4222138-459ed3ae17c6d8c2.png?imageMogr2/auto-orient/strip|imageView2/2/w/262/format/webp)

- as-if-serial
   不管如何重排序，都必须保证代码在单线程下的运行正确，连单线程下都无法正确，更不用讨论多线程并发的情况，所以就提出了一个as-if-serial的概念。
   as-if-serial语义的意思是：不管怎么重排序（编译器和处理器为了提高并行度），（单线程）程序的执行结果不能被改变。编译器、runtime和处理器都必须遵守as-if-serial语义。为了遵守as-if-serial语义，编译器和处理器不会对存在数据依赖关系的操作做重排序，因为这种重排序会改变执行结果。（强调一下，这里所说的数据依赖性仅针对单个处理器中执行的指令序列和单个线程中执行的操作，不同处理器之间和不同线程之间的数据依赖性不被编译器和处理器考虑。）但是，如果操作之间不存在数据依赖关系，这些操作依然可能被编译器和处理器重排序。

  ![img](https:////upload-images.jianshu.io/upload_images/4222138-6683667d7b51efe5.png?imageMogr2/auto-orient/strip|imageView2/2/w/224/format/webp)

  1和3之间存在数据依赖关系，同时2和3之间也存在数据依赖关系。因此在最终执行的指令序列中，3不能被重排序到1和2的前面（3排到1和2的前面，程序的结果将会被改变）。但1和2之间没有数据依赖关系，编译器和处理器可以重排序1和2之间的执行顺序。
   asif-serial语义使单线程下无需担心重排序的干扰，也无需担心内存可见性问题。

### 并发下重排序带来的问题

![img](https:////upload-images.jianshu.io/upload_images/4222138-28aebe405b128275.png?imageMogr2/auto-orient/strip|imageView2/2/w/554/format/webp)



这里假设有两个线程A和B，A首先执行init ()方法，随后B线程接着执行use ()方法。线程B在执行操作4时，能否看到线程A在操作1对共享变量a的写入呢？答案是：不一定能看到。
 由于操作1和操作2没有数据依赖关系，编译器和处理器可以对这两个操作重排序；同样，操作3和操作4没有数据依赖关系，编译器和处理器也可以对这两个操作重排序。让我们先来看看，当操作1和操作2重排序时，可能会产生什么效果？操作1和操作2做了重排序。程序执行时，线程A首先写标记变量flag，随后线程B读这个变量。由于条件判断为真，线程B将读取变量a。此时，变量a还没有被线程A写入，这时就会发生错误！
 当操作3和操作4重排序时会产生什么效果？
 在程序中，操作3和操作4存在控制依赖关系。当代码中存在控制依赖性时，会影响指令序列执行的并行度。为此，编译器和处理器会采用猜测（Speculation）执行来克服控制相关性对并行度的影响。以处理器的猜测执行为例，执行线程B的处理器可以提前读取并计算a*a，然后把计算结果临时保存到一个名为重排序缓冲（Reorder Buffer，ROB）的硬件缓存中。当操作3的条件判断为真时，就把该计算结果写入变量i中。猜测执行实质上对操作3和4做了重排序，问题在于这时候，a的值还没被线程A赋值。在单线程程序中，对存在控制依赖的操作重排序，不会改变执行结果（这也是as-if-serial语义允许对存在控制依赖的操作做重排序的原因）；但在多线程程序中，对存在控制依赖的操作重排序，可能会改变程序的执行结果。

### 解决在并发下的问题

#### 1）内存屏障——禁止重排序

![img](https:////upload-images.jianshu.io/upload_images/4222138-3564f1d018da4129.png?imageMogr2/auto-orient/strip|imageView2/2/w/1075/format/webp)



Java编译器在生成指令序列的适当位置会插入内存屏障指令来禁止特定类型的处理器重排序，从而让程序按我们预想的流程去执行。
 1、保证特定操作的执行顺序。
 2、影响某些数据（或则是某条指令的执行结果）的内存可见性。

编译器和CPU能够重排序指令，保证最终相同的结果，尝试优化性能。插入一条Memory Barrier会告诉编译器和CPU：不管什么指令都不能和这条Memory Barrier指令重排序。
 Memory Barrier所做的另外一件事是强制刷出各种CPU cache，如一个Write-Barrier（写入屏障）将刷出所有在Barrier之前写入 cache 的数据，因此，任何CPU上的线程都能读取到这些数据的最新版本。
 JMM把内存屏障指令分为4类，解释表格，StoreLoad Barriers是一个“全能型”的屏障，它同时具有其他3个屏障的效果。现代的多处理器大多支持该屏障（其他类型的屏障不一定被所有处理器支持）。

#### 2）临界区（synchronized？）

![img](https:////upload-images.jianshu.io/upload_images/4222138-5e8f527ffd82d7ef.png?imageMogr2/auto-orient/strip|imageView2/2/w/519/format/webp)



临界区内的代码可以重排序（但JMM不允许临界区内的代码“逸出”到临界区之外，那样会破坏监视器的语义）。JMM会在退出临界区和进入临界区这两个关键时间点做一些特别处理，虽然线程A在临界区内做了重排序，但由于监视器互斥执行的特性，这里的线程B根本无法“观察”到线程A在临界区内的重排序。这种重排序既提高了执行效率，又没有改变程序的执行结果。

## Happens-Before

用happens-before的概念来阐述操作之间的内存可见性。在JMM中，如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须要存在happens-before关系 。

两个操作之间具有happens-before关系，并不意味着前一个操作必须要在后一个操作之前执行！happens-before仅仅要求前一个操作（执行的结果）对后一个操作可见，且前一个操作按顺序排在第二个操作之前（the first is visible to and ordered before the second） 。

1）如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，而且第一个操作的执行顺序排在第二个操作之前。(对程序员来说)

2）两个操作之间存在happens-before关系，并不意味着Java平台的具体实现必须要按照happens-before关系指定的顺序来执行。如果重排序之后的执行结果，与按happens-before关系来执行的结果一致，那么这种重排序是允许的(对编译器和处理器 来说)

在Java 规范提案中为让大家理解内存可见性的这个概念，提出了happens-before的概念来阐述操作之间的内存可见性。对应Java程序员来说，理解happens-before是理解JMM的关键。JMM这么做的原因是：程序员对于这两个操作是否真的被重排序并不关心，程序员关心的是程序执行时的语义不能被改变（即执行结果不能被改变）。因此，happens-before关系本质上和as-if-serial语义是一回事。as-if-serial语义保证单线程内程序的执行结果不被改变，happens-before关系保证正确同步的多线程程序的执行结果不被改变。

![img](https:////upload-images.jianshu.io/upload_images/4222138-8aeb6d12568cac67.png?imageMogr2/auto-orient/strip|imageView2/2/w/677/format/webp)

- Happens-Before规则-无需任何同步手段就可以保证的
   1）程序顺序规则：一个线程中的每个操作，happens-before于该线程中的任意后续操作。
   2）监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁。
   3）volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
   4）传递性：如果A happens-before B，且B happens-before C，那么A happens-before C。
   5）start()规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作。
   6）join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回。
   7 ）线程中断规则:对线程interrupt方法的调用happens-before于被中断线程的代码检测到中断事件的发生。

# 实现原理

- 内存语义：可以简单理解为 volatile，synchronize，atomic，lock 之类的在 JVM 中的内存方面实现原则

## volatile的内存语义

volatile变量自身具有下列特性：

- 可见性。对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入。
- 原子性：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。

volatile写的内存语义如下：当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值刷新到主内存。



![img](https:////upload-images.jianshu.io/upload_images/4222138-5b7339e9829f084f.png?imageMogr2/auto-orient/strip|imageView2/2/w/523/format/webp)

volatile读的内存语义如下：当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效。线程接下来将从主内存中读取共享变量。



![img](https:////upload-images.jianshu.io/upload_images/4222138-ab1319326e4b4ea1.png?imageMogr2/auto-orient/strip|imageView2/2/w/651/format/webp)

volatile重排序规则：



![img](https:////upload-images.jianshu.io/upload_images/4222138-293ce3182f23da52.png?imageMogr2/auto-orient/strip|imageView2/2/w/1027/format/webp)

volatile内存语义的实现——JMM对volatile的内存屏障插入策略：
 在每个volatile写操作的前面插入一个StoreStore屏障。在每个volatile写操作的后面插入一个StoreLoad屏障。
 在每个volatile读操作的后面插入一个LoadLoad屏障。在每个volatile读操作的后面插入一个LoadStore屏障。



![img](https:////upload-images.jianshu.io/upload_images/4222138-b0b75a3272253281.png?imageMogr2/auto-orient/strip|imageView2/2/w/516/format/webp)



![img](https:////upload-images.jianshu.io/upload_images/4222138-c8a7025ca8c1ee3f.png?imageMogr2/auto-orient/strip|imageView2/2/w/528/format/webp)

### volatile的实现原理

有volatile变量修饰的共享变量进行写操作的时候会使用CPU提供的Lock前缀指令：

- 将当前处理器缓存行的数据写回到系统内存
- 这个写回内存的操作会使在其他CPU里缓存了该内存地址的数据无效。

## 锁的内存语义

当线程释放锁时，JMM会把该线程对应的本地内存中的共享变量刷新到主内存中。。
 当线程获取锁时，JMM会把该线程对应的本地内存置为无效。从而使得被监视器保护的临界区代码必须从主内存中读取共享变量。



![img](https:////upload-images.jianshu.io/upload_images/4222138-aa5c342e5a31b159.png?imageMogr2/auto-orient/strip|imageView2/2/w/417/format/webp)



![img](https:////upload-images.jianshu.io/upload_images/4222138-fd26a9ca30cd56f3.png?imageMogr2/auto-orient/strip|imageView2/2/w/486/format/webp)

### synchronized的实现原理

使用monitorenter和monitorexit指令实现的：

- monitorenter指令是在编译后插入到同步代码块的开始位置，而monitorexit是插入到方法结束处和异常处
- 每个monitorenter必须有对应的monitorexit与之配对
- 任何对象都有一个monitor与之关联，当且一个monitor被持有后，它将处于锁定状态

锁的存放位置：



![img](https:////upload-images.jianshu.io/upload_images/4222138-ff64e9ed97012f2a.png?imageMogr2/auto-orient/strip|imageView2/2/w/1076/format/webp)

### 了解各种锁

锁一共有4种状态，级别从低到高依次是：无锁状态、偏向锁状态、轻量级锁状态和重量级锁状态。

偏向锁：大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得，为了让线程获得锁的代价更低而引入了偏向锁。无竞争时不需要进行CAS操作来加锁和解锁。

轻量级锁：无竞争时通过CAS操作来加锁和解锁。（自旋锁——是一种锁的机制，不是状态）

重量级锁：真正的加锁操作

![img](https:////upload-images.jianshu.io/upload_images/4222138-df7012f6129827d3.png?imageMogr2/auto-orient/strip|imageView2/2/w/856/format/webp)

## final的内存语义

编译器和处理器要遵守两个重排序规则：

- 在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。
- 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序。

final域为引用类型：

- 增加了如下规则：在构造函数内对一个final引用的对象的成员域的写入，与随后在构造函数外把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。

final语义在处理器中的实现：

- 会要求编译器在final域的写之后，构造函数return之前插入一个StoreStore障屏。
- 读final域的重排序规则要求编译器在读final域的操作前面插入一个LoadLoad屏障

