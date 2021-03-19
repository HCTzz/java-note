# Synchronized 

《Java并发编程的艺术》

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\10a3c08b9aec4888b9f2962a7f2922c8~tplv-k3u1fbpfcp-zoom-1.image)

锁升级想必网上有太多文章说过了，这里提到当轻量级锁 CAS 失败，则**当前线程会尝试使用自旋来获取锁**。

其实起初我也是这样认为的，毕竟都是这样说的，而且也很有道理。

因为重量级锁会阻塞线程，所以如果加锁的代码执行的非常快，那么稍微自旋一会儿其他线程就不需要锁了，就可以直接 CAS 成功了，因此不用阻塞了线程然后再唤醒。

但是我看了源码之后发现并不是这样的，这段代码在 synchronizer.cpp 中。

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5ecd39feab17434480b46fd2cb0c3102~tplv-k3u1fbpfcp-zoom-1.image)

所以 CAS 失败了之后，**并没有什么自旋操作**，如果 CAS 成功就直接 return 了，如果失败会执行下面的锁膨胀方法。

我去锁膨胀的代码`ObjectSynchronizer::inflate`翻了翻，也没看到自旋操作。

所以从源码来看**轻量级锁 CAS 失败并不会自旋而是直接膨胀成重量级锁**。

不过为了优化性能，自旋操作在 Synchronized 中确实却有。

那是在已经升级成**重量级锁**之后，线程如果没有争抢到锁，会进行一段自旋等待锁的释放。

咱们还是看源码说话，单单注释其实就已经说得很清楚了：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\e05caca75d2f48359fbae7a39626f8c2~tplv-k3u1fbpfcp-zoom-1.image)

毕竟阻塞线程入队再唤醒开销还是有点大的。

我们再来看看 `TrySpin` 的操作，这里面有**自适应自旋**，其实从实际函数名就 `TrySpin_VaryDuration` 就可以反映出自旋是变化的。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\f3c6fea3c2154f36b235ef8628bb028c~tplv-k3u1fbpfcp-zoom-1.image)

至此，有关 Synchronized 自旋问题就完结了，重量级锁竞争失败会有自旋操作，轻量级锁没有这个动作（至少 1.8 源码是这样的），如果有人反驳你，请把这篇文章甩给他哈哈。

不过都说到这儿了，索性我就继续讲讲 Synchronized 吧，毕竟这玩意出镜率还是挺高的。

这篇文章关于 Synchronized 的深度到哪个程度呢？

之后如有面试官问你看过啥源码？

**看完这篇文章，你可以回答：我看过 JVM 的源码**。

当然源码有点多的，**我把 Synchronized 相关的所有操作都过了一遍**，还是有点难度的。

不过之前看过我的源码分析的读者就会知道，**我都会画个流程图来整理**的，所以即使代码看不懂，流程还是可以搞清楚的！

好，发车！

## 从重量级锁开始说起

Synchronized 在1.6 之前只是重量级锁。

因为会有线程的阻塞和唤醒，这个操作是借助操作系统的系统调用来实现的，常见的 Linux 下就是利用 pthread 的 mutex 来实现的。

我截图了调用线程阻塞的源码，可以看到确实是利用了 mutex。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\c3ef0d6fd2a04559a1b5d816db7c1b9c~tplv-k3u1fbpfcp-zoom-1.image)

而涉及到系统调用就会有上下文的切换，即用户态和内核态的切换，我们知道这种切换的开销还是挺大的。

**所以称为重量级锁**，也因为这样才会有上面提到的自适应自旋操作，因为不希望走到这一步呀！

> 我们来看看重量级锁的实现原理

Synchronized 关键字可以修饰代码块，实例方法和静态方法，**本质上都是作用于对象上**。

代码块作用于括号里面的对象，实例方法是当前的实例对象即 this ，而静态方法就是当前的类。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\16c753a7af11406eaa67d5e5a33ea53a~tplv-k3u1fbpfcp-zoom-1.image)

这里有个概念叫**临界区**。

我们知道，之所以会有竞争是因为有共享资源的存在，多个线程都想要得到那个共享资源，所以就划分了一个区域，操作共享资源资源的代码就在区域内。

可以理解为想要进入到这个区域就必须持有锁，不然就无法进入，这个区域叫临界区。

> 当用 Synchronized 修饰代码块时

此时编译得到的字节码会有 monitorenter 和 monitorexit 指令，我习惯按照临界区来理解，enter 就是要进入临界区了，exit 就是要退出临界区了，与之对应的就是获得锁和解锁。

实际上这两个指令还是和修饰代码块的那个对象相关的，也就是上文代码中的`lockObject`。

**每个对象都有一个 monitor 对象于之关联**，执行 monitorenter 指令的线程就是试图去获取 monitor 的所有权，抢到了就是成功获取锁了。

这个 monitor 下文会详细分析，我们先看下生成的字节码是怎样的。

图片上方是 lockObject 方法编译得到的字节码，下面就是 lockObject 方法，这样对着看比较容易理解。

![img](data:image/svg+xml;utf8,<?xml version="1.0"?><svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="800" height="600"></svg>)

从截图来看，执行 System.out 之前执行了 monitorenter 执行，这里执行争锁动作，拿到锁即可进入临界区。

调用完之后有个 monitorexit 指令，表示释放锁，要出临界区了。

图中我还标了一个 monitorexit 指令时，**因为有异常的情况也需要解锁**，不然就死锁了。

从生成的字节码我们也可以得知，为什么 synchronized 不需要手动解锁？

是有人在替我们负重前行啊！**编译器生成的字节码都帮咱们做好了，异常的情况也考虑到了**。

> 当用 synchronized 修饰方法时

修饰方法生成的字节码和修饰代码块的不太一样，但本质上是一样。

此时字节码中没有 monitorenter 和 monitorexit 指令，不过在**当前方法的访问标记**上做了手脚。

我这里用的是 idea 的插件来看字节码，所以展示的字面结果不太一样，不过 flag 标记是一样的：0x0021 ，是 ACC_PUBLIC 和 ACC_SYNCHRONIZED 的结合。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\1f8afccaa481414fb8401c159d2335ef~tplv-k3u1fbpfcp-zoom-1.image)

原理就是修饰方法的时候在 flag 上标记 ACC_SYNCHRONIZED，在运行时常量池中通过 ACC_SYNCHRONIZED 标志来区分，这样 JVM 就知道这个方法是被 synchronized 标记的，于是在进入方法的时候就会进行执行争锁的操作，一样只有拿到锁才能继续执行。

然后不论是正常退出还是异常退出，都会进行解锁的操作，所以本质还是一样的。

这里还有个隐式的锁对象就是我上面提到的，修饰实例方法就是 this，修饰类方法就是当前类(关于这点是有坑的，我写的[这篇文章](https://mp.weixin.qq.com/s/aM87lQD9Qi6uKUa-MG0wTg)分析过)。

我还记得有个面试题，好像是面字节跳动时候问的，**面试官问 synchronized  修饰方法和代码块的时候字节码层面有什么区别？**。

怎么说？不知不觉距离字节跳动又更近了呢。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\79c443c032a44995858a8dc5161a2929~tplv-k3u1fbpfcp-zoom-1.image)

> 我们再来继续深入 synchronized

从上文我们已经知道 synchronized 是作用于对象身上的，但是没细说，我们接下来剖析一波。

在 Java 中，对象结构分为对象头、实例数据和对齐填充。

而对象头又分为：MarkWord 、 klass pointer、数组长度(只有数组才有)，我们的重点是锁，所以关注点只放在 MarkWord 上。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\295166d9c3e5405f8278afc672c00b97~tplv-k3u1fbpfcp-zoom-1.image)

我再画一下 64 位时 MarkWord 在不同状态下的内存布局（里面的 monitor 打错了，但是我不准备改，留个印记哈哈）。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\b7bd3c75d7cf4c94ac0f9fea987fe3b1~tplv-k3u1fbpfcp-zoom-1.image)

MarkWord 结构之所以搞得这么复杂，是因为需要**节省内存**，让同一个内存区域在不同阶段有不同的用处。

记住这个图啊，各种锁操作都和这个 MarkWord 有很强的联系。

从图中可以看到，在重量级锁时，对象头的锁标记位为 10，并且会有一个指针指向这个 monitor 对象，所以锁对象和 monitor 两者就是这样关联的。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\cc6339d4b0e34a90a1e481677fee1fa2~tplv-k3u1fbpfcp-zoom-1.image)

而这个 monitor 在 HotSpot 中是 c++ 实现的，叫 ObjectMonitor，它是管程的实现，也有叫监视器的。

它长这样，重点字段我都注释了含义，还专门截了个头文件的注释：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\2f12a3b6221746b6b541f4af86f2f983~tplv-k3u1fbpfcp-zoom-1.image)

暂时记忆一下，等下源码和这几个字段关联很大。

> synchronized 底层原理

先来一张图，结合上面 monitor 的注释，先看看，看不懂没关系，有个大致流转的印象即可：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\37f62fec9e514c61b13f216fe5f41c3d~tplv-k3u1fbpfcp-zoom-1.image)

好，我们继续。

前面我们提到了 monitorenter 这个指令，这个指令会执行下面的代码：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\e2986cb074d5486390f0f21e8ebe6235~tplv-k3u1fbpfcp-zoom-1.image)

我们现在分析的是重量级锁，所以不关心偏向的代码，而 slow_enter 方法文章一开始的截图就是了，最终会执行到 `ObjectMonitor::enter` 这个方法中。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\2a3590cfe5b942a7b2e6fe59d0ccde9b~tplv-k3u1fbpfcp-zoom-1.image)

可以看到重点就是通过 CAS 把 ObjectMonitor 中的 _owner 设置为当前线程，**设置成功就表示获取锁成功**。

然后通过 recursions 的自增来表示重入。

如果 CAS 失败的话，会执行下面的一个循环：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\976d543050004ca3955922e3077d4098~tplv-k3u1fbpfcp-zoom-1.image)

EnterI 的代码其实上面也已经截图了，这里再来一次，我把重要的入队操作加上，并且删除了一些不重要的代码：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\30f602d6cecc43b4baa2489f6ebf8074~tplv-k3u1fbpfcp-zoom-1.image)

先再尝试一下获取锁，不行的话就自适应自旋，还不行就包装成 ObjectWaiter 对象加入到 _cxq 这个单向链表之中，挣扎一下还是没抢到锁的话，那么就要阻塞了，所以下面还有个阻塞的方法。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\4d7df6b78ee14352a3cae6d8b4445f95~tplv-k3u1fbpfcp-zoom-1.image)

可以看到不论哪个分支都会执行 `Self->_ParkEvent->park()`，这个就是上文提到的调用 `pthread_mutex_lock`。

至此争抢锁的流程已经很清晰了，我再画个图来理一理。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\b41e7a05245f48ba800a0381614a347f~tplv-k3u1fbpfcp-zoom-1.image)

> 接下来再看看解锁的方法

`ObjectMonitor::exit` 就是解锁时会调用的方法。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\25f7255e12b7402f805346b47570b159~tplv-k3u1fbpfcp-zoom-1.image)

可重入锁就是根据 _recursions 来判断的，重入一次 _recursions++，解锁一次 _recursions--，如果减到 0 说明需要释放锁了。

然后**此时解锁的线程还会唤醒之前等待的线程**，这里有好几种模式，我们来看看。

如果 `QMode == 2 && _cxq != NULL`的时候：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\16034279-4f529baa66e6c558.png)

如果`QMode == 3 && _cxq != NULL`的时候，我就截取了一部分代码：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\b3b8220a004946f9a9b5da473de134ba~tplv-k3u1fbpfcp-zoom-1.image)

如果 `QMode == 4 && _cxq != NULL`的时候：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\a1b2ba58c9f14ad6a987502c1594b73c~tplv-k3u1fbpfcp-zoom-1.image)

如果 QMode 不是 2 的话，最终会执行：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\52eeb22819f44cdf908131cc7f9cf500~tplv-k3u1fbpfcp-zoom-1.image)

至此，解锁的流程就完毕了！我再画一波流程图：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\7e6dd86190e240fbbac42294375584de~tplv-k3u1fbpfcp-zoom-1.image)

> 接下来再看看调用 wait 的方法

没啥花头，就是将当前线程加入到 _waitSet 这个双向链表中，然后再执行 `ObjectMonitor::exit` 方法来释放锁。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\edb27a1b00fb469a9abc7eeb3b037e7f~tplv-k3u1fbpfcp-zoom-1.image)

> 接下来再看看调用 notify 的方法

也没啥花头，就是从 _waitSet 头部拿节点，然后根据策略选择是放在 cxq 还是 EntryList 的头部或者尾部，并且进行唤醒。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\91f01a9e9871498b9cc57a09872ea0fc~tplv-k3u1fbpfcp-zoom-1.image)

至于 notifyAll 我就不分析了，一样的，无非就是做了个循环，全部唤醒。

至此 synchronized 的几个操作都齐活了，出去可以说自己深入研究过  synchronized 了。

现在再来看下这个图，应该心里很有数了。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\46cc96455cbb4ab1817840384cd6aec6~tplv-k3u1fbpfcp-zoom-1.image)

> 为什么会有_cxq 和 _EntryList 两个列表来放线程？

因为会有多个线程会同时竞争锁，所以搞了个 _cxq 这个单向链表基于 CAS 来 hold 住这些并发，然后另外搞一个 _EntryList 这个双向链表，来在每次唤醒的时候搬迁一些线程节点，降低 _cxq 的尾部竞争。

> 引入自旋

synchronized 的原理大致应该都清晰了，我们也知道了底层会用到系统调用，会有较大的开销，那思考一下该如何优化？

从小标题就已经知道了，方案就是自旋，文章开头就已经说了，这里再提一提。

自旋其实就是空转 CPU，执行一些无意义的指令，**目的就是不让出 CPU 等待锁的释放**。

正常情况下锁获取失败就应该阻塞入队，但是有时候可能刚一阻塞，别的线程就释放锁了，然后再唤醒刚刚阻塞的线程，这就没必要了。

所以在线程竞争不是很激烈的时候，稍微自旋一会儿，指不定不需要阻塞线程就能直接获取锁，这样就避免了不必要的开销，提高了锁的性能。

但是**自旋的次数又是一个难点**，在竞争很激烈的情况，自旋就是在浪费 CPU，因为结果肯定是自旋一会让之后阻塞。

所以 Java 引入的是自适应自旋，根据上次自旋次数，来动态调整自旋的次数，**这就叫结合历史经验做事**。

**注意这是重量级锁的步骤，别忘了文章开头说的~**。

> 至此，synchronized 重量级锁的原理应该就很清晰了吧? 小结一下

synchronized 底层是利用 monitor 对象，CAS 和 mutex 互斥锁来实现的，内部会有等待队列(cxq 和 EntryList)和条件等待队列(waitSet)来存放相应阻塞的线程。

未竞争到锁的线程存储到等待队列中，获得锁的线程调用 wait 后便存放在条件等待队列中，解锁和 notify 都会唤醒相应队列中的等待线程来争抢锁。

然后由于阻塞和唤醒依赖于底层的操作系统实现，系统调用存在用户态与内核态之间的切换，所以有较高的开销，因此称之为重量级锁。

所以又引入了自适应自旋机制，来提高锁的性能。

## 现在要引入轻量级锁了

我们再思考一下，是否有这样的场景：**多个线程都是在不同的时间段来请求同一把锁**，此时根本就用不需要阻塞线程，连 monitor 对象都不需要，所以就引入了轻量级锁这个概念，避免了系统调用，减少了开销。

在锁竞争不激烈的情况下，这种场景还是很常见的，可能是常态，所以轻量级锁的引入很有必要。

在介绍轻量级锁的原理之前，再看看之前 MarkWord 图。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\dc5d4f0f4507482e9ef86d5d24949809~tplv-k3u1fbpfcp-zoom-1.image)

轻量级锁操作的就是对象头的 MarkWord 。

如果判断当前处于无锁状态，会在当前线程栈的当前栈帧中划出一块叫 LockRecord 的区域，然后把锁对象的 MarkWord 拷贝一份到 LockRecord 中称之为 dhw(就是那个set_displaced_header 方法执行的)里。

然后通过 CAS 把锁对象头指向这个 LockRecord 。

轻量级锁的加锁过程：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\817af07b09304b15b6f2b2b8bedc9e64~tplv-k3u1fbpfcp-zoom-1.image)

如果当前是有锁状态，并且是当前线程持有的，则将 null 放到 dhw 中，这是重入锁的逻辑。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\f405d519298642faac3a566d9b777417~tplv-k3u1fbpfcp-zoom-1.image)

我们再看下轻量级锁解锁的逻辑：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\7c70aff4d2a846e38b935b2ba4d8a3f4~tplv-k3u1fbpfcp-zoom-1.image)

逻辑还是很简单的，就是要把当前栈帧中 LockRecord 存储的 markword （dhw）通过 CAS 换回到对象头中。

如果获取到的 dhw 是 null 说明此时是重入的，所以直接返回即可，否则就是利用 CAS 换，如果 CAS 失败说明此时有竞争，那么就膨胀！

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\69666de2f4cb4d0ebde6740524d7c700~tplv-k3u1fbpfcp-zoom-1.image)

> 关于这个轻量级加锁我再多说几句。

每次加锁肯定是在一个方法调用中，而方法调用就是有栈帧入栈，如果是轻量级锁重入的话那么此时入栈的栈帧里面的 dhw 就是 null，否则就是锁对象的 markword。

这样在解锁的时候就能通过 dhw 的值来判断此时是否是重入的。

## 现在要引入偏向锁

我们再思考一下，是否有这样的场景：一开始一直只有一个线程持有这个锁，也不会有其他线程来竞争，此时频繁的 CAS 是没有必要的，CAS 也是有开销的。

所以 JVM 研究者们就搞了个偏向锁，就是偏向一个线程，那么这个线程就可以直接获得锁。

我们再看看这个图，偏向锁在第二行。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\69e47ed192354f0e9465050986e1c749~tplv-k3u1fbpfcp-zoom-1.image)

原理也不难，如果当前锁对象支持偏向锁，那么就会通过 CAS 操作：将当前线程的地址(也当做唯一ID)记录到 markword 中，并且将标记字段的最后三位设置为 101。

之后有线程请求这把锁，只需要判断 markword 最后三位是否为 101，是否指向的是当前线程的地址。

还有一个可能很多文章会漏的点，就是还需要判断 epoch 值是否和锁对象的**类**中的 epoch 值相同。

如果都满足，那么说明当前线程持有该偏向锁，就可以直接返回。

> 这 epoch 干啥用的？

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\79f00e95db79474ca6fcd7798985fe2c~tplv-k3u1fbpfcp-zoom-1.image)

可以理解为是第几代偏向锁。

偏向锁在有竞争的时候是要执行撤销操作的，其实就是要升级成轻量级锁。

而当一类对象撤销的次数过多，比如有个 Yes 类的对象作为偏向锁，经常被撤销，次数到了一定阈值(XX:BiasedLockingBulkRebiasThreshold，默认为 20 )就会把当代的偏向锁废弃，把类的 epoch 加一。

所以当类对象和锁对象的 epoch 值不等的时候，当前线程可以将该锁重偏向至自己，因为前一代偏向锁已经废弃了。

不过**为保证正在执行的持有锁的线程不能因为这个而丢失了锁**，偏向锁撤销需要所有线程处于安全点，然后遍历所有线程的 Java 栈，**找出该类已加锁的实例**，并且将它们标记字段中的 epoch 值加 1。

当撤销次数超过另一个阈值(XX:BiasedLockingBulkRevokeThreshold，默认值为 40)，则废弃此类的偏向功能，也就是说这个类都无法偏向了。

至此整个 Synchronized 的流程应该都比较清楚了。

我是反着来讲锁升级的过程的，因为事实上是先有的重量级锁，然后根据实际分析优化得到的偏向锁和轻量级锁。

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\0d66b60863594e12940b5842b0e389cb~tplv-k3u1fbpfcp-zoom-1.image)

包括期间的一些细节应该也较为清楚了，我觉得对于 Synchronized 了解到这份上差不多了。

我再搞了张 openjdk wiki 上的图，看看是不是很清晰了：

![img](E:\learn\git\repository\笔记\java-note\java基础\并发\img\06d852da73be406996628d42d04e2f2e~tplv-k3u1fbpfcp-zoom-1.image)


