一、ThreadLocal:
	1、threadlocal(线程本地变量) 在使用线程池的时候，可能会出现内存溢出的问题。
	2、解决线程内部参数传递(数据库连接、Session管理).
	
	
二、volatile:	
	1、使用原则：
		（1）、对变量的写操作不依赖于当前值。
		（2）、该变量没有包含在具有其他变量的不变式中。
	2、适用场景：
		（1）、：状态标志：
			也许实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或请求停机。
			```java
			volatile boolean shutdownRequested;  
  
			public void shutdown() {   
				shutdownRequested = true;   
			}  
			  
			public void doWork() {   
				while (!shutdownRequested) {   
					// do stuff  
				}  
			}
			```
		（2）、：一次性安全发布
			在缺乏同步的情况下，可能会遇到某个对象引用的更新值（由另一个线程写入）和该对象状态的旧值同时存在。
			这就是造成著名的双重检查锁定（double-checked-locking）问题的根源，其中对象引用在没有同步的情况下进行读操作，
			产生的问题是您可能会看到一个更新的引用，但是仍然会通过该引用看到不完全构造的对象。如下面介绍的单例模式。
			```java
			private volatile static Singleton instace;     
    
			public static Singleton getInstance(){     
				//第一次null检查       
				if(instance == null){              
					synchronized(Singleton.class) {    //1       
						//第二次null检查         
						if(instance == null){          //2    
							instance = new Singleton();//3    
						}    
					}             
				}    
				return instance;   
			}
			
			```
		（3）、：独立观察
			安全使用 volatile 的另一种简单模式是：定期 “发布” 观察结果供程序内部使用。【例如】假设有一种环境传感器能够感觉环境温度。
			一个后台线程可能会每隔几秒读取一次该传感器，并更新包含当前文档的 volatile 变量。然后，其他线程可以读取这个变量，从而随时能够看到最新的温度值。
			使用该模式的另一种应用程序就是收集程序的统计信息。
			例:如下代码展示了身份验证机制如何记忆最近一次登录的用户的名字。将反复使用lastUser 引用来发布值，以供程序的其他部分使用。（主要利用了volatile的可见性）
			```java
			public class UserManager {  
				public volatile String lastUser; //发布的信息  
			  
				public boolean authenticate(String user, String password) {  
					boolean valid = passwordIsValid(user, password);  
					if (valid) {  
						User u = new User();  
						activeUsers.add(u);  
						lastUser = user;  
					}  
					return valid;  
				}  
			}
			```

		（4）、："volatile bean" 模式
			volatile bean 模式的基本原理是：很多框架为易变数据的持有者（例如 HttpSession）提供了容器，但是放入这些容器中的对象必须是线程安全的。
			在 volatile bean 模式中，JavaBean 的所有数据成员都是 volatile 类型的，并且 getter 和 setter 方法必须非常普通——即不包含约束！
			```java
			public class Person {  
				private volatile String firstName;  
				private volatile String lastName;  
				private volatile int age;  
			  
				public String getFirstName() { return firstName; }  
				public String getLastName() { return lastName; }  
				public int getAge() { return age; }  
			  
				public void setFirstName(String firstName) {   
					this.firstName = firstName;  
				}  
			  
				public void setLastName(String lastName) {   
					this.lastName = lastName;  
				}  
			  
				public void setAge(int age) {   
					this.age = age;  
				}  
			}
			```
			
		（5）、：开销较低的“读－写锁”策略
			如果读操作远远超过写操作，您可以结合使用内部锁和 volatile 变量来减少公共代码路径的开销。
			如下显示的线程安全的计数器，使用 synchronized 确保增量操作是原子的，并使用 volatile 保证当前结果的可见性。
			如果更新不频繁的话，该方法可实现更好的性能，因为读路径的开销仅仅涉及 volatile 读操作，这通常要优于一个无竞争的锁获取的开销。
			使用锁进行所有变化的操作，使用 volatile 进行只读操作。其中，锁一次只允许一个线程访问值，volatile 允许多个线程执行读操作。
			```java
			public class CheesyCounter {  
				// Employs the cheap read-write lock trick  
				// All mutative operations MUST be done with the 'this' lock held  
				@GuardedBy("this") private volatile int value;  
			  
				//读操作，没有synchronized，提高性能  
				public int getValue() {   
					return value;   
				}   
			  
				//写操作，必须synchronized。因为x++不是原子操作  
				public synchronized int increment() {  
					return value++;  
				}  
			}
			```
		