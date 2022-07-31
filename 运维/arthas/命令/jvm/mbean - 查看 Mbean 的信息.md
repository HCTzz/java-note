<center><font size="4"><b>查看 Mbean 的信息</b></font></center>

# mbean  

查询注册到JMX中的mbean信息：

eg: 

​	1、java.lang:type=Memory

​	2、java.lang:type=ClassLoading

查看 Mbean 的信息

这个命令可以便捷的查看或监控 Mbean 的属性信息。

### [#](https://arthas.aliyun.com/doc/mbean.html#参数说明)参数说明

|            参数名称 | 参数说明                                             |
| ------------------: | :--------------------------------------------------- |
|      *name-pattern* | 名称表达式匹配                                       |
| *attribute-pattern* | 属性名表达式匹配                                     |
|                 [m] | 查看元信息                                           |
|                [i:] | 刷新属性值的时间间隔 (ms)                            |
|                [n:] | 刷新属性值的次数                                     |
|                 [E] | 开启正则表达式匹配，默认为通配符匹配。仅对属性名有效 |

### [#](https://arthas.aliyun.com/doc/mbean.html#使用参考)使用参考

列出所有 Mbean 的名称：



```bash
mbean
```

查看 Mbean 的元信息：



```bash
mbean -m java.lang:type=Threading
```

查看 mbean 属性信息：



```bash
mbean java.lang:type=Threading
```

mbean 的 name 支持通配符匹配：



```bash
mbean java.lang:type=Th*
```

注意

注意：ObjectName 的匹配规则与正常的通配符存在差异，详细参见：[javax.management.ObjectName在新窗口打开](https://docs.oracle.com/javase/8/docs/api/javax/management/ObjectName.html?is-external=true)

通配符匹配特定的属性字段：



```bash
mbean java.lang:type=Threading *Count
```

使用`-E`命令切换为正则匹配：



```bash
mbean -E java.lang:type=Threading PeakThreadCount|ThreadCount|DaemonThreadCount
```

使用`-i`命令实时监控：



```bash
mbean -i 1000 java.lang:type=Threading *Count
```