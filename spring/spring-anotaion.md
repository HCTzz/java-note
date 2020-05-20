1、@Configuration
2、@Bean
3、@CompentScan 自定义扫描规则
4、typeFilter自定义过滤规则
5、@Scope
6、@Layz-bean
7、@Conditional    ---> Condition(interface)
8、@Import  
	注册BEAN方式 ：
	1、ImportSelector(interface)   
	2、ImportBeanDefinitionRegistrar(interface)手动注册bean  
	3、FactoryBean
	生命周期：bean创建  --> 初始化 -->  销毁
	1、@Bean(initMethod="",destoryMethod="")
	2、initlizingBean(初始化)、DisposableBean(销毁)(interface)
	3、JSR250：@PostConstructor @PreDestory
	4、@BeanPostProcessor bean后置处理器
		BeanPostProcessor在spring中的应用：
		1、ApllicationContextAware  -> 获取IOC容器
		2、BeanValidationPostProcessor	-> 数据校验
		...
9、@Value  属性赋值操作    springboot  @ConfigurationProperties(prefix = "spring.redis")
	1、基本数据 @Value("张三")
	2、spEl表达式:#{20-2}
	3、${},配置文件中的值
		1、@PropertySource 导入配置文件 @PropertySource("classpath:xxx.properties")
		2、@Value("${key}")
		
		
		
事件：
	1、ApringApplicationRunListener
	2、ApplicationContextInitializer
	3、ApplicationRunner
	4、CommandLineRunner
		