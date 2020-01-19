LockSupport.park();阻塞当前线程
LockSupport.unpark();取消阻塞；
Thread.sleep();阻塞当前线程（可由Thread.interrupted()中断）
Thread.interrupted();会重置当前线程的状态；


//设置线程池数   CompletableFuture
System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");