1����¼���ļ��𡢴����������

2�������ļ�

�����ļ�Log4J�����ļ��Ļ�����ʽ���£�


#���ø�Loggerlog4j.rootLogger = [ level ] ,appenderName1 ,appenderName2 ,��
#������־��Ϣ���Ŀ�ĵ�
Appenderlog4j.appender.appenderName? = ?fully.qualified.name.of.appender.class?����log4j.appender.appenderName.option1? = ?value1?������?����
log4j.appender.appenderName.optionN? = ?valueN?

#������־��Ϣ�ĸ�ʽ�����֣�
log4j.appender.appenderName.layout = fully.qualified.name.of.layout.class?����
log4j.appender.appenderName.layout.option1 = value1?��?����
log4j.appender.appenderName.layout.optionN = valueN?


��־����

FATAL      0  
ERROR      3  
WARN       4  
INFO       6  
DEBUG      7 

Appender Ϊ��־���Ŀ�ĵأ�Log4j�ṩ��appender�����¼��֣�

org.apache.log4j.ConsoleAppender������̨����
org.apache.log4j.FileAppender���ļ�����
org.apache.log4j.DailyRollingFileAppender��ÿ�����һ����־�ļ�����
org.apache.log4j.RollingFileAppender���ļ���С����ָ���ߴ��ʱ�����һ���µ��ļ�����
org.apache.log4j.WriterAppender������־��Ϣ������ʽ���͵�����ָ���ĵط���
