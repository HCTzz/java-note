### 一、基本概念

简单说就是对象在spring容器（IOC容器）中的生命周期，也可以理解为对象在spring容器中的创建方式。

![img](image/20141016225232936)

#### 1、singleton（单一实例）

​		此取值时表明容器中创建时只存在一个实例，所有引用此bean都是单一实例。如同每个国家都有一个总统，国家的所有人共用此总统，而这个国家就是一个spring容器，总统就是spring创建的类的bean，国家中的人就是其它调用者，总统是一个表明其在spring中的scope为singleton，也就是单例模型。

此外，singleton类型的bean定义从容器启动到第一次被请求而实例化开始，只要容器不销毁或退出，该类型的bean的单一实例就会一直存活，典型单例模式，如同servlet在web容器中的生命周期。

#### 2、prototype

​		spring容器在进行输出prototype的bean对象时，会每次都重新生成一个新的对象给请求方，虽然这种类型的对象的实例化以及属性设置等工作都是由容器负责的，但是只要准备完毕，并且对象实例返回给请求方之后，容器就不在拥有当前对象的引用，请求方需要自己负责当前对象后继生命周期的管理工作，包括该对象的销毁。也就是说，容器每次返回请求方该对象的一个新的实例之后，就由这个对象“自生自灭”，最典型的体现就是spring与struts2进行整合时，要把action的scope改为prototype。

如同分苹果，将苹果的bean的scope属性声明为prototype，在每个人领取苹果的时候，我们都是发一个新的苹果给他，发完之后，别人爱怎么吃就怎么吃，爱什么时候吃什么时候吃，但是注意吃完要把苹果核扔到垃圾箱！对于那些不能共享使用的对象类型，应该将其定义的scope设为prototype。

#### 3、request

​		再次说明 request，session和global session类型只实用于 web程序，通常是和XmlWebApplicationContext共同使用。

<bean id ="requestPrecessor" class="...RequestPrecessor"  scope="request" />

Spring容器，即XmlWebApplicationContext 会为每个HTTP请求创建一个全新的RequestPrecessor对象，当请求结束后，该对象的生命周期即告结束，如同java web中request的生命周期。当同时有100个HTTP请求进来的时候，容器会分别针对这10个请求创建10个全新的RequestPrecessor实例，且他们相互之间互不干扰，简单来讲，request可以看做prototype的一种特例，除了场景更加具体之外，语意上差不多。

#### 4、session

​		对于web应用来说，放到session中最普遍的就是用户的登录信息，对于这种放到session中的信息，我们可以使用如下形式的制定scope为session：

<bean id ="userPreferences" class="...UserPreferences"  scope="session" />

Spring容器会为每个独立的session创建属于自己的全新的UserPreferences实例，比request scope的bean会存活更长的时间，其他的方面没区别，如果java web中session的生命周期。



#### 5、global session

<bean id ="userPreferences" class="...UserPreferences"  scope="globalsession" />

global session只有应用在基于porlet的web应用程序中才有意义，它映射到porlet的global范围的session，如果普通的servlet的web 应用中使用了这个scope，容器会把它作为普通的session的scope对待。

### 二、示例（使用scope动态更新bean）

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aaf7bd95b5cd46b38b5686b5a75bab46~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp)

#### 1、实现思路

​		了解spring的朋友，应该知道spring的单例bean是缓存在singletonObjects这个map里面，所以可以通过变更singletonObjects来实现bean的刷新。我们可以通过调用removeSingleton和addSingleton这两个方法来实现，但是这种实现方式的缺点就是会改变bean的生命周期，会导致原来的一些增强功能失效，比如AOP。但spring作为一个极其优秀的框架，他提供了让我们自己管理bean的扩展点。这个扩展点就是通过指定scope，来达到自己管理bean的效果

#### 2、实现步骤

##### 		2.1、自定义scope

```java
public class RefreshBeanScope implements Scope {

    private final Map<String,Object> beanMap = new ConcurrentHashMap<>(256);

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(beanMap.containsKey(name)){
            return beanMap.get(name);
        }

        Object bean = objectFactory.getObject();
        beanMap.put(name,bean);
        return bean;
    }

    @Override
    public Object remove(String name) {
        return beanMap.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
```

##### 		2.2、自定义scope注册

```java
public class RefreshBeanScopeDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            beanFactory.registerScope(SCOPE_NAME,new RefreshBeanScope());
    }
}

```

##### 		3、自定义scope注解（可选）

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Scope("refreshBean")
@Documented
public @interface RefreshBeanScope {

    /**
     * @see Scope#proxyMode()
     * @return proxy mode
     */
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
}
```

##### 		4、编写自定义scope bean刷新逻辑

```java
@RequiredArgsConstructor
public class RefreshBeanScopeHolder implements ApplicationContextAware {
    
    private final DefaultListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;
    
    
    public List<String> refreshBean(){
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        List<String> refreshBeanDefinitionNames = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if(SCOPE_NAME.equals(beanDefinition.getScope())){
                beanFactory.destroyScopedBean(beanDefinitionName);
                beanFactory.getBean(beanDefinitionName);
                refreshBeanDefinitionNames.add(beanDefinitionName);
                applicationContext.publishEvent(new RefreshBeanEvent(beanDefinitionName));
            }
        }

        return Collections.unmodifiableList(refreshBeanDefinitionNames);
        
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

```

