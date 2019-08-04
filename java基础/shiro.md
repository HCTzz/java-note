一、核心：

	1、Authentication：身份认证/登录，验证用户是不是拥有相应的身份；
	2、Authorization：授权，即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情
	3、Session Manager：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；
	4、Cryptography：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储；
	5、Web Support：Web 支持，可以非常容易的集成到 Web 环境；
	6、Caching：缓存
	7、Concurrency：shiro 支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去
	8、Run As：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问；
	9、Remember Me：记住我，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了。
	
核心类：
	1、Subject：主体
	2、SecurityManager：安全管理器。即所有与安全有关的操作都会与 SecurityManager 交互；且它管理着所有 Subject；可以看出它是 Shiro 的核心，它负责与后边介绍的其他组件进行交互，如果学习过 SpringMVC，你可以把它看成 DispatcherServlet 前端控制器；
	3、Realm：域，Shiro 从从 Realm 获取安全数据（如用户、角色、权限），就是说 SecurityManager要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；也需要从 Realm 得到用户相应的角色/权限进行验证用户是否能进行操作；可以把 Realm 看成 DataSource，即安全数据源。
	4、SessionManager：如果写过 Servlet 就应该知道 Session 的概念，Session 呢需要有人去管理它的生命周期，这个组件就是 SessionManager；而 Shiro 并不仅仅可以用在 Web 环境，也可以用在如普通的 JavaSE 环境、EJB 等环境；所有呢，Shiro 就抽象了一个自己的 Session来管理主体与应用之间交互的数据；这样的话，比如我们在 Web 环境用，刚开始是一台Web 服务器；接着又上了台 EJB 服务器；这时想把两台服务器的会话数据放到一个地方，这个时候就可以实现自己的分布式会话（如把数据放到 Memcached 服务器）；
	5、SessionDAO：DAO 大家都用过，数据访问对象，用于会话的 CRUD，比如我们想把 Session保存到数据库，那么可以实现自己的 SessionDAO，通过如 JDBC 写到数据库；比如想把Session 放到 Memcached 中，可以实现自己的 Memcached SessionDAO；另外 SessionDAO中可以使用 Cache 进行缓存，以提高性能；
	6、CacheManager：缓存控制器，来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少去改变，放到缓存中后可以提高访问的性能
	7、Cryptography：密码模块，Shiro 提高了一些常见的加密组件用于如密码加密/解密的。
