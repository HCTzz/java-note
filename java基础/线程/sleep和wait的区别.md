### sleep()和wait()的区别

Java 中的多线程是一种抢占式的机制而不是分时机制。线程主要有以下几种状态：可运行，运行，阻塞，死亡。抢占式机制指的是有多个线程处于可运行状态，但是只有一个线程在运行。当有多个线程访问共享数据的时候，就需要对线程进行同步。线程中的几个主要方法的比较：

​    **Thread 类的方法： sleep(),yield() 等**

​    **Object 的方法： wait() 和 notify() 等**

​    每个对象都有一个机锁来控制同步访问 。 Synchronized 关键字可以和对象的机锁交互，来实现线程的同步。

​    **由于 sleep() 方法是 Thread 类的方法，因此它不能改变对象的机锁。 所以当在一个 Synchronized 方法中调用 sleep （）时，线程虽然休眠了，但是对象的机锁没有被释放，其他线程仍然无法访问这个对象。而 wait()方法则会在线程休眠的同时释放掉 机锁 ，其他线程可以访问该对象 。**

​    **yield() 方法是停止当前线程，让同等优先权的线程运行。如果没有同等优先权的线程，那么 yield() 方法将不会起作用。**

- 一个线程结束的标志是： run() 方法结束。
- 一个机锁被释放的标志是： synchronized 块或方法结束。

​    Wait() 方法和 notify() 方法：当一个线程执行到 wait() 方法时( 线程休眠且释放机锁 )，它就进入到一个和 该对象 相关的等待池中，同时失去了对象的机锁。当它被一个 notify()方法唤醒时 ，等待池中的线程就被放到了锁池中 。该线程从锁池中获得机锁，然后回到 wait()前的中断现场 。join() 方法使当 前线程停下来等待 ，直至另 一个调用join方法的线程 终止。值得注意的是：线程的在被激活后不一定马上就运行，而是进入到 可运行线程的队列中 。

```
共同点：他们都是在多线程的环境下，都可以在程序的调用处阻塞指定的毫秒数，并返回。
不同点：Thread.sleep(long)可以不在synchronized的块下调用，而且使用Thread.sleep()不会丢失当前线程对任何对象的同步锁(monitor);object.wait(long)必须在synchronized的块下来使用，调用了之后失去对object的monitor, 这样做的好处是它不影响其它的线程对object进行操作。
```

```java
/** 
  * 在这里为什么要使用queue.wait()，而不是Thread.sleep(), 是因为暂时放弃queue的对象锁，可以让允许其
  * 它的线程执行一些同步操作。
  * 使用queue.wait(long)的前提条件是sched()动作执行的时间很短，否则如果很长，那么queue.wait()不能够   * 按时醒来
  **/
private void mainLoop() {
    while (true) {
    ....
    synchronized(queue) {
    .....
    if (!taskFired) // Task hasn't yet fired; wait
        queue.wait(executionTime - currentTime);
    }
}
private void sched(TimerTask task, long time, long period) {
     synchronized(queue) {
       ...
       queue.add(task);
     }
}
```

前面讲了wait/notify机制，Thread还有一个sleep()静态方法，它也能使线程暂停一段时间。sleep与wait的不同点是：sleep并不释放锁，并且sleep的暂停和wait暂停是不一样的。obj.wait会使线程进入obj对象的等待集合中并等待唤醒。但是wait()和sleep()都可以通过interrupt()方法打断线程的暂停状态，从而使线程立刻抛出InterruptedException。如果线程A希望立即结束线程B，则可以对线程B对应的Thread实例调用interrupt方法。如果此刻线程B正在wait/sleep/join，则线程B会立刻抛出InterruptedException，在catch() {} 中直接return即可安全地结束线程。**需要注意的是，InterruptedException是线程自己从内部抛出的，并不是interrupt()方法抛出的。对某一线程调用interrupt()时，如果该线程正在执行普通的代码，那么该线程根本就不会抛出InterruptedException。但是，一旦该线程进入到wait()/sleep()/join()后，就会立刻抛出InterruptedException。**

**sleep()、suspend()、resume()方法不推荐使用，推荐使用wait()、notify()、notifyAll()。**

sleep()方法是使线程停止一段时间的方法。在sleep 时间间隔期满后，线程不一定立即恢复执行。这是因为在那个时刻，其它线程可能正在运行而且没有被调度为放弃执行，除非

- “醒来”的线程具有更高的优先级。
- 正在运行的线程因为其它原因而阻塞。

wait()是线程交互时，如果线程对一个同步对象x 发出一个wait()调用，该线程会暂停执行，被调对象进入等待状态，直到被唤醒或等待时间到。当调用wait()后，线程会释放掉它所占有的“锁标志”，从而使线程所在对象中的其它synchronized数据可被别的线程使用。waite()和notify()因为会对对象的“锁标志”进行操作，所以它们必须在synchronized函数或synchronized　block中进行调用。如果在non-synchronized函数或non-synchronized　block中进行调用，虽然能编译通过，但在运行时会发生IllegalMonitorStateException的异常。

```java
//分析这段程序，并解释一下，着重讲讲synchronized、wait(),notify 谢谢！
/**
  *要分析这个程序,首先要理解notify()和wait(),为什么在前几天记录线程的时候没有纪录这两个方法呢,因为这两个   *方法本来就不属于Thread类,而是属于最底层的object基础类的,也就是说不光是Thread，每个对象都有notify和   *wait的功能，为什么？因为他们是用来操纵锁的,而每个对象都有锁,锁是每个对象的基础,既然锁是基础的,那么操纵   *锁的方法当然也是最基础了
  **/
class ThreadA{
public static void main(String[] args) {
  ThreadB b=new ThreadB();
  b.start();
  System.out.println("b is start....");
  synchronized(b){//括号里的b是什么意思,起什么作用?
   try{
	System.out.println("Waiting for b to complete...");
	b.wait();//这一句是什么意思，究竟让谁wait?
    System.out.println("Completed.Now back to main thread");
   }catch (InterruptedException e){}
  }
  System.out.println("Total is :"+b.total);
  }
}
class ThreadB extends Thread{
    int total;
	public void run(){
  		synchronized(this){
   		System.out.println("ThreadB is running..");
   		for (int i=0;i<100;i++ ){
    		total +=i;
    		System.out.println("total is "+total);
   		}
   		notify();
	}
}
```

**等待和通知,也就是wait()和notify了**

```java
按照Think in Java中的解释:"wait()允许我们将线程置入“睡眠”状态，同时又“积极”地等待条件发生改变.而且只有在一个notify()或notifyAll()发生变化的时候，线程才会被唤醒，并检查条件是否有变."
"wait()允许我们将线程置入“睡眠”状态",也就是说,wait也是让当前线程阻塞的,这一点和sleep或者suspend是相同的.那和sleep,suspend有什么区别呢?区别在于"(wait)同时又“积极”地等待条件发生改变",这一点很关键,sleep和suspend无法做到.因为我们有时候需要通过同步（synchronized）的帮助来防止线程之间的冲突，而一旦使用同步,就要锁定对象，也就是获取对象锁,其它要使用该对象锁的线程都只能排队等着,等到同步方法或者同步块里的程序全部运行完才有机会.在同步方法和同步块中,无论sleep()还是suspend()都不可能自己被调用的时候解除锁定,他们都霸占着正在使用的对象锁不放.而wait却可以,它可以让同步方法或者同步块暂时放弃对象锁,而将它暂时让给其它需要对象锁的人(这里应该是程序块,或线程)用,这意味着可在执行wait()期间调用线程对象中的其他同步方法!在其它情况下(sleep啊,suspend啊),这是不可能的.但是注意我前面说的,只是暂时放弃对象锁,暂时给其它线程使用,我wait所在的线程还是要把这个对象锁收回来的呀.wait什么?就是wait别人用完了还给我啊！
好,那怎么把对象锁收回来呢?

1. 限定借出去的时间.在wait()中设置参数,比如wait(1000),以毫秒为单位,就表明我只借出去1秒中,一秒钟之后,我自动收回.
2. 让借出去的人通知我,他用完了,要还给我了.这时,我马上就收回来.哎,假如我设了1小时之后收回,别人只用了半小时就完了,那怎么办呢?靠!当然用完了就收回了,还管我设的是多长时间啊.

那么别人怎么通知我呢?相信大家都可以想到了,notify(),这就是最后一句话"而且只有在一个notify()或notifyAll()发生变化的时候，线程才会被唤醒"的意思了.因此,我们可将一个wait()和notify()置入任何同步方法或同步块内部，无论在那个类里是否准备进行涉及线程的处理。而且实际上,我们也只能在同步方法或者同步块里面调用wait()和notify().这个时候我们来解释上面的程序,简直是易如反掌了.synchronized(b){...}；的意思是定义一个同步块,使用b作为资源锁。b.wait();的意思是临时释放锁，并阻塞当前线程,好让其他使用同一把锁的线程有机会执行,在这里要用同一把锁的就是b线程本身.这个线程在执行到一定地方后用notify()通知wait的线程,锁已经用完,待notify()所在的同步块运行完之后,wait所在的线程就可以继续执行.
```

另外注意一下sleep 和yield 的区别：

- sleep()使当前线程进入停滞状态，所以执行sleep()的线程在指定的时间内肯定不会执行；yield()只是使当前线程重新回到可执行状态，所以执行yield()的线程有可能在进入到可执行状态后马上又被执行。
- sleep()可使优先级低的线程得到执行的机会，当然也可以让同优先级和高优先级的线程有执行的机会；yield()只能使同优先级的线程有执行的机会。
