## tomcat线程池策略

- 场景1：接受一个请求，此时tomcat启动的线程数还没有达到corePoolSize(`tomcat里头叫minSpareThreads`)，tomcat会启动一个线程来处理该请求；
- 场景2：接受一个请求，此时tomcat启动的线程数已经达到了corePoolSize，tomcat把该请求放入队列(`offer`)，如果放入队列成功，则返回，放入队列不成功，则尝试增加工作线程，在当前线程个数<maxThreads的时候，可以继续增加线程来处理，超过maxThreads的时候，则继续往等待队列里头放，等待队列放不进去，则抛出RejectedExecutionException；

值得注意的是，使用LinkedBlockingQueue的话，默认是使用Integer.MAX_VALUE，即无界队列(这种情况下如果没有配置队列的capacity的话，队列始终不会满，那么始终无法进入开启新线程到达maxThreads个数的地步，则此时配置maxThreads其实是没有意义的)。

而TaskQueue的队列capacity为maxQueueSize，默认也是Integer.MAX_VALUE。但是，**其重写offer方法，当其线程池大小小于maximumPoolSize的时候，返回false，即在一定程度改写了队列满的逻辑**，修复了使用LinkedBlockingQueue默认的capacity为Integer.MAX_VALUE的时候，maxThreads失效的"bug"。从而可以继续增长线程到maxThreads，超过之后，继续放入队列。

> TaskQueue的offer操作

```
@Override
    public boolean offer(Runnable o) {
      //we can't do any checks
        if (parent==null) return super.offer(o);
        //we are maxed out on threads, simply queue the object
        if (parent.getPoolSize() == parent.getMaximumPoolSize()) return super.offer(o);
        //we have idle threads, just add it to the queue
        if (parent.getSubmittedCount()<(parent.getPoolSize())) return super.offer(o);
        //if we have less threads than maximum force creation of a new thread
        if (parent.getPoolSize()<parent.getMaximumPoolSize()) return false;
        //if we reached here, we need to add it to the queue
        return super.offer(o);
    }
```

### StandardThreadExecutor

```
/**
     * Start the component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {

        taskqueue = new TaskQueue(maxQueueSize);
        TaskThreadFactory tf = new TaskThreadFactory(namePrefix,daemon,getThreadPriority());
        executor = new ThreadPoolExecutor(getMinSpareThreads(), getMaxThreads(), maxIdleTime, TimeUnit.MILLISECONDS,taskqueue, tf);
        executor.setThreadRenewalDelay(threadRenewalDelay);
        if (prestartminSpareThreads) {
            executor.prestartAllCoreThreads();
        }
        taskqueue.setParent(executor);

        setState(LifecycleState.STARTING);
    }
```

值得注意的是，tomcat的线程池使用了自己扩展的taskQueue，而不是Executors工厂方法里头用的LinkedBlockingQueue。(`主要是修改了offer的逻辑`)

这里的maxQueueSize默认为

```
/**
     * The maximum number of elements that can queue up before we reject them
     */
    protected int maxQueueSize = Integer.MAX_VALUE;
```

### org/apache/tomcat/util/threads/ThreadPoolExecutor

```
/**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the <tt>Executor</tt> implementation.
     * If no threads are available, it will be added to the work queue.
     * If the work queue is full, the system will wait for the specified
     * time and it throw a RejectedExecutionException if the queue is still
     * full after that.
     *
     * @param command the runnable task
     * @param timeout A timeout for the completion of the task
     * @param unit The timeout time unit
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution - the queue is full
     * @throws NullPointerException if command or unit is null
     */
    public void execute(Runnable command, long timeout, TimeUnit unit) {
        submittedCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException rx) {
            if (super.getQueue() instanceof TaskQueue) {
                final TaskQueue queue = (TaskQueue)super.getQueue();
                try {
                    if (!queue.force(command, timeout, unit)) {
                        submittedCount.decrementAndGet();
                        throw new RejectedExecutionException("Queue capacity is full.");
                    }
                } catch (InterruptedException x) {
                    submittedCount.decrementAndGet();
                    throw new RejectedExecutionException(x);
                }
            } else {
                submittedCount.decrementAndGet();
                throw rx;
            }

        }
    }
```

注意看这里改写了jdk线程池默认的Rejected规则，即catch住了RejectedExecutionException。正常jdk的规则是core线程数＋临时线程数 >maxSize的时候，就抛出RejectedExecutionException。这里catch住的话，继续往taskQueue里头放。

```
public boolean force(Runnable o, long timeout, TimeUnit unit) throws InterruptedException {
        if ( parent==null || parent.isShutdown() ) throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
        return super.offer(o,timeout,unit); //forces the item onto the queue, to be used if the task is rejected
    }
```

注意的是这里调用的super.offer(o,timeout,unit)，即LinkedBlockingQueue，只有当列满的时候，返回false，才会抛出重新抛出RejectedExecutionException。(`这里改变了jdk的ThreadPoolExecutor的RejectedExecutionException抛出的逻辑，也就是超出了maxThreads不会抛出RejectedExecutionException，而是继续往队列丢任务，而taskQueue本身是无界的，因此可以默认几乎不会抛出RejectedExecutionException`)

## JDK线程池策略

> 1. 每次提交任务时，如果线程数还没达到coreSize就创建新线程并绑定该任务。所以第coreSize次提交任务后线程总数必达到coreSize，不会重用之前的空闲线程。
> 2. 线程数达到coreSize后，新增的任务就放到工作队列里，而线程池里的线程则努力的使用take()从工作队列里拉活来干。
> 3. 如果队列是个有界队列，又如果线程池里的线程不能及时将任务取走，工作队列可能会满掉，插入任务就会失败，此时线程池就会紧急的再创建新的临时线程来补救。
> 4. 临时线程使用poll(keepAliveTime，timeUnit)来从工作队列拉活，如果时候到了仍然两手空空没拉到活，表明它太闲了，就会被解雇掉。
> 5. 如果core线程数＋临时线程数 >maxSize，则不能再创建新的临时线程了，转头执行RejectExecutionHanlder。默认的AbortPolicy抛RejectedExecutionException异常，其他选择包括静默放弃当前任务(Discard)，放弃工作队列里最老的任务(DisacardOldest)，或由主线程来直接执行(CallerRuns).

> 源码

```
/**
     * Executes the given task sometime in the future.  The task
     * may execute in a new thread or in an existing pooled thread.
     *
     * If the task cannot be submitted for execution, either because this
     * executor has been shutdown or because its capacity has been reached,
     * the task is handled by the current {@code RejectedExecutionHandler}.
     *
     * @param command the task to execute
     * @throws RejectedExecutionException at discretion of
     *         {@code RejectedExecutionHandler}, if the task
     *         cannot be accepted for execution
     * @throws NullPointerException if {@code command} is null
     */
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }
```

## 小结

tomcat的线程池与jdk的使用无界LinkedBlockingQueue主要有如下两点区别：

- jdk的ThreadPoolExecutor的线程池增长策略是：如果队列是个有界队列，又如果线程池里的线程不能及时将任务取走，工作队列可能会满掉，插入任务就会失败，此时线程池就会紧急的再创建新的临时线程来补救。而tomcat的ThreadPoolExecutor使用的taskQueue，是无界的LinkedBlockingQueue，但是通过taskQueue的offer方法覆盖了LinkedBlockingQueue的offer方法，改写了规则，使得它也走jdk的ThreadPoolExecutor的有界队列的线程增长策略。
- jdk的ThreadPoolExecutor的线程池，当core线程数＋临时线程数 > maxSize，则不能再创建新的临时线程了，转头执行RejectExecutionHanlder。而tomcat的ThreadPoolExecutor则改写了这个规则，即catch住了RejectExecutionHanlder，继续往队列里头放，直到队列满了才抛出RejectExecutionHanlder。而默认taskQueue是无界的。

疑问：既然taskQueue是无界的，那么在哪里控制tomcat服务器的接收请求限制，如何自我保护。另外acceptCount与maxConnections到底是什么关系。

## doc

- [Tomcat性能优化](http://itindex.net/detail/52878-tomcat-性能-优化)
- [Java ThreadPool的正确打开方式](http://calvin1978.blogcn.com/articles/java-threadpool.html)
- [Tomcat的Connector组件](http://www.wtoutiao.com/p/129wXxu.html)
- [一次Tomcat hang住问题排查手记](https://www.zybuluo.com/zhanjindong/note/25710)