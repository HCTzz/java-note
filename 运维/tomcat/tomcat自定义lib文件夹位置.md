# tomcat指定lib文件夹

将第三方包放置在指定位置，不用每次更新都打包Lib

```
${catalina.home}/conf/catalina.properties
例子：
common.loader = ${catalina.home}/lib/ext/*.jar
```



![img](E:\learn\git\repository\笔记\java-note\运维\tomcat\img\20181108142035867.png)