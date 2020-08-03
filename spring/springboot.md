nohup java -jar xxxx.jar >/dev/null 2>&1&
yaml语法：
	1、k: v
	2、Map 数据 ： 
		k: 
		  p1: v1
		  p2: v2
		行内写法 k: {p1: v1,p2: v2}
    3、List,Set
		k:
		 - p1: v1
		 - p2: v2
		 
将配置文件中配置映射到这个组件中
1、@ConfigurationProperties(prefix="xxx")	支持JSR303 数据验证 [eg(@Email)]	 
2、@value 字面量/${key}/#{SpEL} spring 表达式    
3、@PropertySource 加载指定的配置文件
4、@ImportSource   导入spring的配置文件

配置文件占位符
1、随机数 random
2、使用之前配置的值，没有则取默认。${name:xxx}

Profile 多环境配置文件 spring.profiles.active=dev
1、多Profile文件 
	application-{profile}.properties
2、yml文件
	--- 文档块
3、命令行方式：--spring.profiles.active=dev
4、虚拟机参数：-Dspring.profiles.active


二、拓展springmvc
@Configuration
@EnableMvc（全面接管） 使用后会使springboot自动配置MVC失效
	
	
国际化：(message)
	1、编写国际化配置文件。	login_zh_CN.properties login_en_US.properties
	2、配置类MessageSourceAutoConfiguration
	3、编写页面
	4、Locale(区域信息)  localeResolve 区域信息解析器
	
修改内嵌servlet容器：编写一个EmbeddedServletContainerFactory
1、修改和server相关的配置文件（serverProperties）
2、编写一个EmbeddedServletContainerCustomizer:嵌入式容器的定制器。

注册Servlet Filter Listener
1、servletRegistrationBean
2、FilterRegistrattionBean
3、ServletListenerRegistrtionBean

使用其他的servlet容器
1、JETTY
2、Undertow
按照依赖决定使用哪个servlet容器（POM.xml）

嵌入式servlet启动原理“
1、springboot 应用启动run
2、refreshContext(context) springboot 刷新IOC容器【创建IOC容器对象，并初始化容器】
3、refresh(context);
4、onRefresh()
	1、invokeBeanFactoryPostProcessors 加载所有的BEAN DEFINITION（bean定义）
5、webIOC容器会创建嵌入式的servlet容器
6、createEmbeddedServletContainer

使用外部容器
1、
2、
3、