
1、META-INFO -> service -> javax.servlet.ServletContainerInitializer文件中配置容器启动需要实例化并执行的类
对应spring-web中为 SpringServletContainerInitializer

@HandlesTypes({WebApplicationInitializer.class}) //容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口等）传递过来,对应下面的webAppInitializerClasses
public class SpringServletContainerInitializer implements ServletContainerInitializer {

	public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
		
	｝
｝



