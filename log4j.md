1、记录器的级别、存放器、布局

2、配置文件

配置文件Log4J配置文件的基本格式如下：


#配置根Loggerlog4j.rootLogger = [ level ] ,appenderName1 ,appenderName2 ,…
#配置日志信息输出目的地
Appenderlog4j.appender.appenderName? = ?fully.qualified.name.of.appender.class?　　log4j.appender.appenderName.option1? = ?value1?　　…?　　
log4j.appender.appenderName.optionN? = ?valueN?

#配置日志信息的格式（布局）
log4j.appender.appenderName.layout = fully.qualified.name.of.layout.class?　　
log4j.appender.appenderName.layout.option1 = value1?…?　　
log4j.appender.appenderName.layout.optionN = valueN?


日志级别：

FATAL      0  
ERROR      3  
WARN       4  
INFO       6  
DEBUG      7 

Appender 为日志输出目的地，Log4j提供的appender有以下几种：

org.apache.log4j.ConsoleAppender（控制台），
org.apache.log4j.FileAppender（文件），
org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件），
org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件），
org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
