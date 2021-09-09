# Spring是如何保证同一事务获取同一个Connection的

[CSDN]: https://fangshixiang.blog.csdn.net/article/details/91538445

前言
关于Spring的事务，它是Spring Framework中极其重要的一块。前面用了大量的篇幅从应用层面、原理层面进行了比较全方位的一个讲解。但是因为它过于重要，所以本文继续做补充内容：Spring事务的同步机制（后面还有Spring事务的监听机制）

Spring事务同步机制？我估摸很多小伙伴从来没听过还有这么一说法，毕竟它在平时开发中你可能很少遇到（如果你没怎么考虑过系统性能和吞吐量的话）。

让我记录本文的源动力是忆起两年前自己在开发、调试过程中遇到这样一个诡异异常：

java.sql.SQLException: Connection has already been closed
1
但是，它不是必现的，重点：它不是必现的。
而一旦出现，任何涉及需要使用数据库连接的接口都有可能报这个错（已经影响正常work了），重启也解决不了问题的根本。

关于非必现问题，我曾经表达了一个观点：程序中的“软病（非必现问题）”是相对很难解决的，因为定位难度高，毕竟只要问题一旦定位了，从来不差解决方案

这个异常的字面意思非常简单：数据库连接池连接被关闭了。
可能大多数人（我当然也不例外）看到此异常都会fuck一句：what？我的连接都是交给Spring去管理了，自己从来不会手动close，怎么回事？难道Spring有bug？
敢于质疑“权威”一直以来都是件好事，但是有句话这么说：你对人家还不了解的情况下不要轻易说人家程序有bug。

可能大多数人对于Spring的事务，只知道怎么使用，比如加个注解啥的，但是底层原理并不清楚，因此定位此问题就会变得非常的困难了~
由于我之前有研究过Spring事务的同步机制这块，所以忆起这件事之后就迅速定位了问题所在：这和Spring事务的同步机制有关，并不是Spring事务的bug。

Spring事务极简介绍
关于Spring事务，我推荐小伙伴看看上面的【相关阅读】，能让你对Spring事务管理有个整体的掌握。但是由于过了有段时间了，此处做个非常简单的介绍：

Spring有声明式事务和编程式事务：
声明式事务只需要提供@Transactional的注解，然后事务的开启和提交/回滚、资源的清理就都由spring来管控，我们只需要关注业务代码即可；
编程式事务则需要使用spring提供的模板，如TransactionTemplate，或者直接使用底层的PlatformTransactionManager手动控制提交、回滚。

声明式事务的最大优点就是对代码的侵入性小，只需要在方法上加@Transactional的注解就可以实现事务；
编程式事务的最大优点就是事务的管控粒度较细，可以实现代码块级别的事务。

前提介绍
Spring把JDBC 的 Connection或者Hibernate的Session等访问数据库的链接（会话）都统一称为资源，显然我们知道Connection这种是线程不安全的，同一时刻是不能被多个线程共享的。

简单的说：同一时刻我们每个线程持有的Connection应该是独立的，且都是互不干扰和互不相同的

但是Spring管理的Service、Dao等他们都是无状态的单例Bean，怎么破？，如何保证单例Bean里面使用的Connection都能够独立呢？
Spring引入了一个类：事务同步管理类org.springframework.transaction.support.TransactionSynchronizationManager来解决这个问题。它的做法是内部使用了很多的ThreadLocal为不同的事务线程提供了独立的资源副本，并同时维护这些事务的配置属性和运行状态信息 （比如强大的事务嵌套、传播属性和这个强相关）。

这个同步管理器TransactionSynchronizationManager是掌管这一切的大脑，它管理的TransactionSynchronization是开放给调用者一个非常重要的扩展点，下面会有详细介绍~

TransactionSynchronizationManager 将 Dao、Service 类中影响线程安全的所有 “ 状态 ” 都统一抽取到该类中，并用 ThreadLocal 进行封装，这样一来， Dao （基于模板类或资源获取工具类创建的 Dao ）和 Service （采用 Spring 事务管理机制）就不用自己来保存一些事务状态了，从而就变成了线程安全的单例对象了，优秀~

DataSourceUtils
这里有必要提前介绍Spring提供给我们的这个工具类。

有些场景比如我们使用MyBatis的时候，某些场景下，可能无法使用 Spring 提供的模板类来达到效果，而是需要直接操作源生API Connection。

那如何拿到这个链接Connection呢？？？（主意此处打大前提：必须保证和当前MaBatis线程使用的是同一个链接，这样才接受本事务控制嘛，否则就脱缰了~）

这个时候DataSourceUtils这个工具类就闪亮登场了，它提供了这个能力：

```java
public abstract class DataSourceUtils {
	...
	public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException { ... }
	...
	
    // 把definition和connection进行一些准备工作~
    public static Integer prepareConnectionForTransaction(Connection con, @Nullable TransactionDefinition definition) throws SQLException { ...}

    // Reset the given Connection after a transaction,
    // con.setTransactionIsolation(previousIsolationLevel);和con.setReadOnly(false);等等
    public static void resetConnectionAfterTransaction(Connection con, @Nullable Integer previousIsolationLevel) { ... }

    // 该JDBC Connection 是否是当前事务内的链接~
    public static boolean isConnectionTransactional(Connection con, @Nullable DataSource dataSource) { ... }

    // Statement 给他设置超时时间  不传timeout表示不超时
    public static void applyTransactionTimeout(Statement stmt, @Nullable DataSource dataSource) throws SQLException { ... }
    public static void applyTimeout(Statement stmt, @Nullable DataSource dataSource, int timeout) throws SQLException { ... }

    // 此处可能是归还给连接池，也有可能是close~（和连接池参数有关）
    public static void releaseConnection(@Nullable Connection con, @Nullable DataSource dataSource) { ... }
    public static void doReleaseConnection(@Nullable Connection con, @Nullable DataSource dataSource) throws SQLException { ... }

    // 这个是真close
    public static void doCloseConnection(Connection con, @Nullable DataSource dataSource) throws SQLException { ... }

    // 如果链接是代理，会拿到最底层的connection
    public static Connection getTargetConnection(Connection con) { ... }
}
```
getConnection()这个方法就是从TransactionSynchronizationManager里拿到一个现成的Connection（若没有现成的会用DataSource创建一个链接然后放进去~~~），所以这个工具类还是蛮好用的。

其实Spring不仅为JDBC提供了这个工具类，还为Hibernate、JPA、JDO等都提供了类似的工具类。
org.springframework.orm.hibernate.SessionFactoryUtils.getSession()
org.springframework.orm.jpa.EntityManagerFactoryUtils.getTransactionalEntityManager()
org.springframework.orm.jdo.PersistenceManagerFactoryUtils.getPersistenceManager()

问题场景一模拟
为了更好解释和说明，此处我模拟出这样的一个场景。

// 此处生路而关于DataSource、PlatformTransactionManager事务管理器等的配置

```java
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public Object hello(Integer id) {
        // 向数据库插入一条记录
        String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
        jdbcTemplate.update(sql);

        // 做其余的事情  可能抛出异常
        System.out.println(1 / 0);
        return "service hello";
    }
}
```

如上Demo，这样子的因为有事务，所以最终这个插入都是不会成功的。(这个应该不用解释了吧，初级工程师应该必备的“技能”~)

```java
@Transactional
@Override
public Object hello(Integer id) {
    // 向数据库插入一条记录
    String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
    jdbcTemplate.update(sql);
}
```

稍微改造一下，按照上面这么写，我相信想都不用想。count永远是返回1的~~这应该也是我们面向过程编程时候的经典案例：前面insert一条记录，下面是可以立马去查询出来的

下面我把它改造如下：


```java
@Transactional
@Override
public Object hello(Integer id) {
    // 向数据库插入一条记录
    String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
    jdbcTemplate.update(sql);
	// 生产环境一般会把些操作交给线程池，此处我只是模拟一下效果而已~
    new Thread(() -> {
        String query = "select count(1) from user where id = " + id;
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        log.info(count.toString());
    }).start();
    // 把问题放大
    try {
        TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    return "service hello";
}
```

经过这番改造，这样我们的count的值永远是0（是不是不符合预期了？）。

小技巧：此处为了演示我使用sleep方式把问题放大了，否则可能有时候好使、有时候不好使 把问题放大是debug调试的一个基本技巧~

这个现象就是一个非常严重的问题，它可能会出现：刚插入的数据竟然查不到的诡异现象，这个在我们现阶段平时工作中也会较为频繁的遇到，若对这块不了解，它会对的业务逻辑、对mysql binlog的顺序有依赖的相关逻辑全都将会受到影响

解决方案
在互联网环境编程中，我们经常为了提高吞吐量、程序性能，会使用到异步的方式进行优化、消峰等等。因此连接池、线程池被得到了大量的应用。我们知道异步的提供的好处不言而喻，能够尽最大可能的提升硬件的利用率和能力，但它带来的缺点只有一个：提升系统的复杂性，很多时候需要深入的了解它才能运用自如，毕竟任何方案都是一把双刃剑，没有完美的~

比如一个业务处理中，发短信、发微信通知、记录操作日志等等这些非主干需求，我们一般都希望交给线程池去处理而不要干扰主要业务流程，所以我觉得现在多线程方式处理任务的概率已经越来越高了~
既然如此，我觉得出现上面我模拟的这种现象的可能性还是蛮高的，所以希望小伙伴们能引起重视一些。

定位到问题的原因是解决问题的关键，这里我先给出直接的解决方案，再做理论分析。
我们的诉求是：我们的异步线程的执行时，必须确保记录已经持久化到数据库了才ok。因此可以这么来做，一招制敌：


```java
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public Object hello(Integer id) {
        // 向数据库插入一条记录
        String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
        jdbcTemplate.update(sql);
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            // 在事务提交之后执行的代码块（方法）  此处使用TransactionSynchronizationAdapter，其实在Spring5后直接使用接口也很方便了~
            @Override
            public void afterCommit() {
                new Thread(() -> {
                    String query = "select count(1) from user where id = " + id;
                    Integer count = jdbcTemplate.queryForObject(query, Integer.class);
                    log.info(count.toString());
                }).start();
            }
        });
            // 把问题放大
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "service hello";
    }
}
```


我们使用TransactionSynchronizationManager注册一个TransactionSynchronization然后在afterCommit里执行我们的后续代码，这样就能100%确保我们的后续逻辑是在当前事务被commit后才执行的，完美的问题解决

它还有个方法afterCompletion()有类似的效果，至于它和afterCommit()有什么区别，我觉得稍微有点技术敏感性的小伙伴都能知晓的~

TransactionSynchronizationManager
对它简单的解释为：使用TreadLocal记录事务的一些属性，用于应用扩展同步器的使用，在事务的开启，挂起，提交等各个点上回调应用的逻辑

// @since 02.06.2003  它是个抽象类，但是没有任何子类  因为它所有的方法都是静态的
public abstract class TransactionSynchronizationManager {

	// ======保存着一大堆的ThreadLocal 这里就是它的核心存储======
	
	//  应用代码随事务的声明周期绑定的对象  比如：DataSourceTransactionManager有这么做：
	//TransactionSynchronizationManager.bindResource(obtainDataSource(), txObject.getConnectionHolder());
	// TransactionSynchronizationManager.bindResource(obtainDataSource(), suspendedResources);
	// 简单理解为当前线程的数据存储中心~~~~
	private static final ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
	
	// 使用的同步器，用于应用扩展
	// TransactionSynchronization同步器是最为重要的一个扩展点~~~ 这里是个set 所以每个线程都可以注册N多个同步器
	private static final ThreadLocal<Set<TransactionSynchronization>> synchronizations = new NamedThreadLocal<>("Transaction synchronizations");
	
	// 事务的名称  
	private static final ThreadLocal<String> currentTransactionName = new NamedThreadLocal<>("Current transaction name");
	// 事务是否是只读  
	private static final ThreadLocal<Boolean> currentTransactionReadOnly = new NamedThreadLocal<>("Current transaction read-only status");
	// 事务的隔离级别
	private static final ThreadLocal<Integer> currentTransactionIsolationLevel = new NamedThreadLocal<>("Current transaction isolation level");
	// 事务是否开启   actual：真实的
	private static final ThreadLocal<Boolean> actualTransactionActive = new NamedThreadLocal<>("Actual transaction active");
	
	// 返回的是个只读视图
	public static Map<Object, Object> getResourceMap() {
		Map<Object, Object> map = resources.get();
		return (map != null ? Collections.unmodifiableMap(map) : Collections.emptyMap());
	}
	
	public static boolean hasResource(Object key) { ... }
	public static Object getResource(Object key) { ... }
	
	// actualKey：确定的key  拆包后的
	@Nullable
	private static Object doGetResource(Object actualKey) {
		Map<Object, Object> map = resources.get();
		if (map == null) {
			return null;
		}
		Object value = map.get(actualKey);
		// Transparently remove ResourceHolder that was marked as void...
		// 如果ResourceHolder 被标记为了void空白了。此处直接从map里移除掉对应的key 
		// ~~~~~~~并且返回null~~~~~~~~~~~
		if (value instanceof ResourceHolder && ((ResourceHolder) value).isVoid()) {
			map.remove(actualKey);
			// Remove entire ThreadLocal if empty...
			if (map.isEmpty()) {
				resources.remove();
			}
			value = null;
		}
		return value;
	}
	
	// 逻辑很简单，就是和当前线程绑定一个Map，并且处理ResourceHolder 如果isVoid就抛错
	public static void bindResource(Object key, Object value) throws IllegalStateException {
		Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
		Assert.notNull(value, "Value must not be null");
		Map<Object, Object> map = resources.get();
		// set ThreadLocal Map if none found
		if (map == null) {
			map = new HashMap<>();
			resources.set(map);
		}
		Object oldValue = map.put(actualKey, value);
		// Transparently suppress a ResourceHolder that was marked as void...
		if (oldValue instanceof ResourceHolder && ((ResourceHolder) oldValue).isVoid()) {
			oldValue = null;
		}
		if (oldValue != null) {
			throw new IllegalStateException("Already value [" + oldValue + "] for key [" +
					actualKey + "] bound to thread [" + Thread.currentThread().getName() + "]");
		}
	}
	
	public static Object unbindResource(Object key) throws IllegalStateException { ... }
	public static Object unbindResourceIfPossible(Object key) { ... }


	// 同步器是否是激活状态~~~  若是激活状态就可以执行同步器里的相关回调方法了
	public static boolean isSynchronizationActive() {
		return (synchronizations.get() != null);
	}
	
	// 如果事务已经开启了，就不能再初始化同步器了  而是直接注册
	public static void initSynchronization() throws IllegalStateException {
		if (isSynchronizationActive()) {
			throw new IllegalStateException("Cannot activate transaction synchronization - already active");
		}
		logger.trace("Initializing transaction synchronization");
		synchronizations.set(new LinkedHashSet<>());
	}
	
	// 注册同步器TransactionSynchronization   这个非常重要 下面有详细介绍这个接口
	// 注册的时候要求当前线程的事务已经是激活状态的  而不是随便就可以调用的哦~~~
	public static void registerSynchronization(TransactionSynchronization synchronization) throws IllegalStateException {
		Assert.notNull(synchronization, "TransactionSynchronization must not be null");
		if (!isSynchronizationActive()) {
			throw new IllegalStateException("Transaction synchronization is not active");
		}
		synchronizations.get().add(synchronization);
	}


	    // 返回的是只读视图  并且，并且支持AnnotationAwareOrderComparator.sort(sortedSynchs); 这样排序~~
	    public static List<TransactionSynchronization> getSynchronizations() throws IllegalStateException { ... }
	    public static void clearSynchronization() throws IllegalStateException { ... }
	
	    ... // 省略name等其余几个属性的get/set方法  因为没有任何逻辑
	    // 这个方法列出来，应该下面会解释
	    public static void setActualTransactionActive(boolean active) {
	        actualTransactionActive.set(active ? Boolean.TRUE : null);
	    }
	
	    // 清楚所有和当前线程相关的（注意：此处只是clear清除，和当前线程的绑定而已~~~）
	    public static void clear() {
	        synchronizations.remove();
	        currentTransactionName.remove();
	        currentTransactionReadOnly.remove();
	        currentTransactionIsolationLevel.remove();
	        actualTransactionActive.remove();
	    }
	}


这里把setActualTransactionActive单独拿出来看一下，以加深对事务执行过程的了解。
在AbstractPlatformTransactionManager.getTransaction()的时候会调用此方法如下：

TransactionSynchronizationManager.setActualTransactionActive(status.hasTransaction()); // 相当于表示事务为开启了
1
并且该类的handleExistingTransaction、prepareTransactionStatus等等方法都会此标记有调用，也就是说它会参与到事务的声明周期里面去

备注：以上方法他们统一的判断条件有：TransactionStatus.isNewTransaction()是新事务的时候才会调用这个方进行标记

另外此类它的suspend暂停的时候会直接的这么调用：

```java
TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
TransactionSynchronizationManager.setActualTransactionActive(false);
```

resume恢复的时候：

```java
TransactionSynchronizationManager.setCurrentTransactionReadOnly(resourcesHolder.readOnly);
TransactionSynchronizationManager.setActualTransactionActive(resourcesHolder.wasActive);

```

大体上可以得出这样的一个处理步骤：

开启新的事务时初始化。第一次开启事务分为：real首次 或 已存在事务但是REQUIRES_NEW
在事务的嵌套过程中，TransactionSynchronizationManager属性不断更新最终清除。即外层事务挂起；事务提交，这两个点需要更新TransactionSynchronizationManager属性。
这里面有个内部类AbstractPlatformTransactionManager.SuspendedResourcesHolder它是负责事务挂起时候，保存事物属性的对象，用于恢复外层事务。当恢复外层事务时，根据SuspendedResourcesHolder对象，调用底层事务框架恢复事务属性，并恢复TransactionSynchronizationManager
DefaultTransactionStatus
它实现了TransactionStatus接口。
这个是整个事务框架最重要的状态对象，它贯穿于事务拦截器，spring抽象框架和底层具体事务实现框架之间。

它的重要任务是在新建，挂起，提交事务的过程中保存对应事务的属性。在AbstractPlatformTransactionManager中，每个事物流程都会new创建这个对象

TransactionSynchronizationUtils
这个工具类比较简单，主要是处理TransactionSynchronizationManager和执行TransactionSynchronization它对应的方法们，略~

TransactionSynchronization：事务同步器
这个类非常的重要，它是我们程序员对事务同步的扩展点：用于事务同步回调的接口，AbstractPlatformTransactionManager支持它。

注意：自定义的同步器可以通过实现Ordered接口来自己定制化顺序，若没实现接口就按照添加的顺序执行~



```java
// @since 02.06.2003  实现了java.io.Flushable接口
public interface TransactionSynchronization extends Flushable {
    int STATUS_COMMITTED = 0;
    int STATUS_ROLLED_BACK = 1;
    int STATUS_UNKNOWN = 2;

    // 事务赞提suspend的时候调用此方法
    // 实现这个方法的目的一般是释放掉绑定的resources 
    // TransactionSynchronizationManager#unbindResource
    default void suspend() {
    }
    // 事务恢复时候调用
    // TransactionSynchronizationManager#bindResource
    default void resume() {
    }

    // 将基础会话刷新到数据存储区（如果适用） 比如Hibernate/Jpa的session
    @Override
    default void flush() {
    }

    // 在事务提交之前促发。在AbstractPlatformTransactionManager.processCommit方法里 commit之前触发
    // 事务提交之前，比如flushing SQL statements to the database
    // 请注意：若此处发生了异常，会导致回滚~
    default void beforeCommit(boolean readOnly) {
    }
    // 在beforeCommit之后，在commit/rollback之前执行
    // 它和beforeCommit还有个非常大的区别是：即使beforeCommit抛出异常了  这个也会执行
    default void beforeCompletion() {
    }

    // 这个就非常重要了，它是事务提交（注意事务已经成功提交，数据库已经持久化完成这条数据了）后执行  注意此处是成功提交而没有异常
    // javadoc说了：此处一般可以发短信或者email等操作~~因为事务已经成功提交了

    // =====但是但是但是：======
    // 事务虽然已经提交，但事务资源（链接connection）可能仍然是活动的和可访问的。
    // 因此，此时触发的任何数据访问代码仍将“参与”原始事务 允许执行一些清理（不再执行提交操作！）
    // 除非它明确声明它需要在单独的事务中运行。
    default void afterCommit() {
    }

    // 和上面的区别在于：即使抛出异常回滚了  它也会执行的。它的notice同上
    default void afterCompletion(int status) {
    }
}
```
我们自定义一个同步器TransactionSynchronization使用得最多的是afterCommit和afterCompletion这两个方法，但是上面的note一定一定要注意，下面我用“人的语言”尝试翻译如下：

事务虽然已经提交，但是我的连接可能还是活动的（比如使用了连接池链接是不会关闭的)
若你的回调中刚好又使用到了这个链接，它会参与到原始的事务里面去
这个时候你参与到了原始事务，但是它并不会给你commit提交。（所以你在这里做的update、insert等默认都将不好使）
回收资源（链接）的时候，因为你使用的就是原始事务的资源，所以Spring事务还会给你回收掉，从而就可能导致你的程序出错
声明一下：这段白话文"翻译"是我自主的行为，目前还没有得到任何官方、第三方的描述和认可，我是第一个，旨在希望对小伙伴理解这块有所帮助，若有不对的地方请帮忙留言指正，不甚感激~

依旧为了加强理解，看看源码处是怎么个逻辑：		

```java
public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager, Serializable {
	...
	private void processCommit(DefaultTransactionStatus status) throws TransactionException {
		...
		try {
			prepareForCommit(status);
			triggerBeforeCommit(status);
			triggerBeforeCompletion(status);
			...
			doCommit(status);
		// 事务正常提交后  当然triggerAfterCompletion方法上面回滚里有而有个执行 此处不贴出了
		try {
			triggerAfterCommit(status);
		} finally {
			triggerAfterCompletion(status, TransactionSynchronization.STATUS_COMMITTED);
		}
	} finally {
		cleanupAfterCompletion(status);
	}
}
...
// 清楚、回收事务相关的资源~~~  并且恢复底层事务（若需要~）
private void cleanupAfterCompletion(DefaultTransactionStatus status) {
	status.setCompleted();
	if (status.isNewSynchronization()) {
		TransactionSynchronizationManager.clear();
	}
	if (status.isNewTransaction()) {
		doCleanupAfterCompletion(status.getTransaction());
	}
	if (status.getSuspendedResources() != null) {
		if (status.isDebug()) {
			logger.debug("Resuming suspended transaction after completion of inner transaction");
		}
		Object transaction = (status.hasTransaction() ? status.getTransaction() : null);
		resume(transaction, (SuspendedResourcesHolder) status.getSuspendedResources());
	}
}
```
从这个代码结构里可以看到，即使triggerAfterCommit和triggerAfterCompletion全部都执行了（哪怕是抛错了），最终它一定会做的是：cleanupAfterCompletion(status);这一步会回收资源。

那这种情况怎么避免被它回收呢？其实上面JavaDoc也说了：首先是可能，其次Spring建议使用一个新事务处理来避免这种可能性发生

至于什么是新事务？比如上面的new了一个线程，那都别说新事务了，都开新线程，所以肯定是不存在此问题了的。
Spring这里指的是若你还在同一个线程里，同步进行处理的时候，建议新启一个新事务（使用PROPAGATION_REQUIRES_NEW吧~）

Spring是如何保证事务获取同一个Connection的
相信这个问题，有了上面的理论支撑，此处不用再大花篇幅了。~以JdbcTemplate为例一笔带过。

JdbcTemplate执行SQL的方法主要分为update和query方法，他俩底层最终都是依赖于execute方法去执行（包括存储函数、储存过程），所以只需要看看execute是怎么获取connection链接的？

```java
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {
	...
	public <T> T execute(StatementCallback<T> action) throws DataAccessException {
		...
		// dataSource就是此JdbcTemplate所关联的数据源，这个在config配置文件里早就配置好了
		// 显然，这里获取的连接就是事务相关的，和当前想成绑定的connection
		Connection con = DataSourceUtils.getConnection(obtainDataSource());
		...
		finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
	}
	...
}
```

TransactionSynchronizationManager内部用ThreadLocal<Map<Object, Object>>对象存储资源，key为DataSource、value为connection对应的ConnectionHolder对象。

以上，就是它保证统一的核心原因，其它持久化框架处理方法都类似~

TransactionSynchronization的实现类们
首先就是TransactionSynchronizationAdapter，从明白中就能看出它仅仅是个Adapter适配器而已，并不做实事。但是这个适配器它额外帮我们实现了Ordered接口，所以子类们不用再显示实现了，这样非常利于我们书写匿名内部类去实现它，这一点还是很暖心的~~

```java
public abstract class TransactionSynchronizationAdapter implements TransactionSynchronization, Ordered {
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
	... // 省略空实现们~~~
}

其余实
```

现均为内部类实现，比如DataSourceUtils.ConnectionSynchronization、SimpleTransactionScope.CleanupSynchronization。还有后面会碰到的一个相对重要的的内部类实现：ApplicationListenerMethodTransactionalAdapter.TransactionSynchronizationEventAdapter，它和事务监听机制有关~

**问题场景二模拟**
场景一借助TransactionSynchronizationManager解决了“先插入再异步异步线程查询不到”的问题，也就是著名的：Spring如何在数据库事务提交成功后进行异步操作问题~~

case1最多就是丢失部分信息记录，影响甚微（毕竟非常重要的步骤并不建议使用这种异步方式去实现和处理~）。
case2也就是本case最坏情况最终会导致Spring准备好的所有的connection都被close，从而以后再次请求的话拿到的都是已关闭的连接，最终可能导致整个服务的不可用，可谓非常严重。本case主要是为了模拟出上面Spring官方Note的说明，使用时需要注意的点~

其实如果你在afteCommit里面如果不直接直接使用connection链接，是不会出现链接被关闭问题的。因为现在的高级框架都很好的处理了这个问题

下面我模拟此场景的代码如下：

    @Slf4j
    @Service
    public class HelloServiceImpl implements HelloService {
        @Autowired
        private JdbcTemplate jdbcTemplate;
    
        @Transactional
        @Override
        public Object hello(Integer id) {
            // 向数据库插入一条记录
            String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
            jdbcTemplate.update(sql);
    
             TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                // 在事务提交之后执行的代码块（方法）  此处使用TransactionSynchronizationAdapter，其实在Spring5后直接使用接口也很方便了~
                @Override
                public void afterCommit() {
                    String sql = "insert into user (id,name,age) values (" + (id + 1) + ",'fsx',21)";
                    int update = jdbcTemplate.update(sql);
                    log.info(update + "");
                }
            });
            return "service hello";
        }
    }

预期结果：本以为第二个insert是插入不进去的（不是报错，而是持久化不了），但是最终结果是：两条记录都插入成功了。

what a fuck，有点打我脸，挺疼。，与我之前掌握的理论相悖了，与Spring的javadoc里讲述的也相悖了（其实与Spring的并没有相悖，毕竟人家说的是“可能”，可见话不能说太满的重要性，哈哈）。这勾起了我的深入探索，究竟咋回事呢？？？

下面我把我的研究结果直接描述如下：

afterCommit()内的connection也提交成功的原因分析
按照AbstractPlatformTransactionManager事务的源码执行处：

	public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager, Serializable {
		...
		private void processCommit(DefaultTransactionStatus status) throws TransactionException {
			...
			try {
				prepareForCommit(status);
				triggerBeforeCommit(status);
				triggerBeforeCompletion(status);
				...
				doCommit(status);
			// 事务正常提交后  当然triggerAfterCompletion方法上面回滚里有而有个执行 此处不贴出了
			try {
				triggerAfterCommit(status);
			} finally {
				triggerAfterCompletion(status, TransactionSynchronization.STATUS_COMMITTED);
			}
		} finally {
			cleanupAfterCompletion(status);
		}
	}
	...
	    // 清除、回收事务相关的资源~~~  并且恢复底层事务（若需要~）
	    private void cleanupAfterCompletion(DefaultTransactionStatus status) {
	        status.setCompleted();
	        if (status.isNewSynchronization()) {
	            TransactionSynchronizationManager.clear();
	        }
	        if (status.isNewTransaction()) {
	            doCleanupAfterCompletion(status.getTransaction());
	        }
	        if (status.getSuspendedResources() != null) {
	            if (status.isDebug()) {
	                logger.debug("Resuming suspended transaction after completion of inner transaction");
	            }
	            Object transaction = (status.hasTransaction() ? status.getTransaction() : null);
	            resume(transaction, (SuspendedResourcesHolder) status.getSuspendedResources());
	        }
	    }
	}
可以明确的看到执行到triggerAfterCommit/triggerAfterCompletion的时候doCommit是执行完成了的，也就是说这个时候事务肯定是已经提交成功了（此时去数据库里查看此记录也确定已经持久化）。

所以我猜测：后续该connection是不可能再执行connection.commit()方法了的，因为同一个事务只可能被提交一次。从上面理论知道：即使我们在afterCommit()里执行，Spring也保证了我拿到的链接还是当前线程所属事务的Connection
因此我继续猜测：connection的自动提交功能可能是在这期间被恢复了，从而导致了这条SQL语句它的自动提交成功。

关于Connection的自动提交机制，以及事务对它的“影响干预”，请参与上面的推荐博文了解，有详细的表述

来到finally里cleanupAfterCompletion方法里有这么一句：

		// 这里最终都会被执行~~~
		// doCleanupAfterCompletion方法在本抽象类是一个空的protected方法
		// 子类可以根据自己的需要，自己去实现事务提交完成后的操作
		if (status.isNewTransaction()) {
			doCleanupAfterCompletion(status.getTransaction());
		}
我们大都使用的是子类DataSourceTransactionManager，本例也一样使用的是它。因此可以看看它对doCleanupAfterCompletion此方法的实现：

```java
public class DataSourceTransactionManager extends AbstractPlatformTransactionManager
		implements ResourceTransactionManager, InitializingBean {
	...
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
		// 释放资源~~ Remove the connection holder from the thread, if exposed.
		if (txObject.isNewConnectionHolder()) {
			TransactionSynchronizationManager.unbindResource(obtainDataSource());
		}
		// Reset connection.
		Connection con = txObject.getConnectionHolder().getConnection();
		try {
			// 这里是关键，在事后会恢复链接的自动提交本能，也就是常用的恢复现场机制嘛~~
			// 显然这个和isMustRestoreAutoCommit属性的值有关，true就会恢复~~~
			if (txObject.isMustRestoreAutoCommit()) {
				con.setAutoCommit(true);
			}
			DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
		}
		...
		txObject.getConnectionHolder().clear();
	}
	...
}
```


从上注释可知，现在问题的关键的就是DataSourceTransactionObject对象isMustRestoreAutoCommit的属性值了，若它是true，那就完全符合我的猜想。

DataSourceTransactionObject
关于DataSourceTransactionObject，它是一个DataSourceTransactionManager的一个私有内部静态类。



	private static class DataSourceTransactionObject extends JdbcTransactionObjectSupport {
		// 来自父类
		@Nullable
		private ConnectionHolder connectionHolder;
		@Nullable
		private Integer previousIsolationLevel;
		private boolean savepointAllowed = false;
	
		// 来自本类
		private boolean newConnectionHolder;
		private boolean mustRestoreAutoCommit; // 决定是否要恢复自动提交  默认情况下是false的
		...
		public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
			this.mustRestoreAutoCommit = mustRestoreAutoCommit;
		}
		public boolean isMustRestoreAutoCommit() {
			return this.mustRestoreAutoCommit;
		}
	}
这个内部类很简单，就是聚合了一些属性值，此处我们只关注mustRestoreAutoCommit这个属性值是否被设置为true了，若被设置过，就符合我的预期和猜想了。

通过代码跟踪，发现DataSourceTransactionManager在doBegin的时候调用了setMustRestoreAutoCommit方法如下：

```java
@Override
protected void doBegin(Object transaction, TransactionDefinition definition) {
	...
	if (con.getAutoCommit()) {
		txObject.setMustRestoreAutoCommit(true); // 此处设置值为true
		if (logger.isDebugEnabled()) {
			logger.debug("Switching JDBC Connection [" + con + "] to manual commit");
		}
		con.setAutoCommit(false);
	}
	
}
```
此处代码也就是当开启事务（doBegin）的时候的关键代码，它对DataSourceTransactionObject打入标记，表示最终需要事务它返还给链接自动提交的能力。

综上所述：上述案例Demo最终成功插入了两条数据的结果是完全正确，且我的猜想都解释通了。

备注：case2我本想构造的是在afterCommit()里使用connection而最终被错误关闭的情况case，目前来看若使用的是DataSourceTransactionManager这个事务管理器的话，是不用担心这种情况发生的，最终你的SQL都会被成功提交，也不会出现被误close掉的问题~

