LockSupport.park();������ǰ�߳�
LockSupport.unpark();ȡ��������
Thread.sleep();������ǰ�̣߳�����Thread.interrupted()�жϣ�
Thread.interrupted();�����õ�ǰ�̵߳�״̬��


//�����̳߳���   CompletableFuture
System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");

## LinkedBlockingQueue
��CΪ��ʱ˵����TAKE�����Ѿ������������������������⣬�ж�C==0���ȡtake��֪ͨtake�߳�ȥ��ȡ
if (c == 0)
            signalNotEmpty();