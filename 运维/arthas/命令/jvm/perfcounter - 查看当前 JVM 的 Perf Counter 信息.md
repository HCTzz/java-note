<center><font size="4"><b>查看当前 JVM 的 Perf Counter 信息</b></font></center>

# perfcounter

提示

查看当前 JVM 的 Perf Counter 信息



### [#](https://arthas.aliyun.com/doc/perfcounter.html#使用参考)使用参考



```text
$ perfcounter
 java.ci.totalTime（JIT编译用时）               2325637411
 java.cls.loadedClasses                       3403
 java.cls.sharedLoadedClasses                 0
 java.cls.sharedUnloadedClasses               0
 java.cls.unloadedClasses                     0
 java.property.java.version                   11.0.4
 java.property.java.vm.info                   mixed mode
 java.property.java.vm.name                   OpenJDK 64-Bit Server VM
...
```

可以用`-d`参数打印更多信息：



```text
$ perfcounter -d
 Name                                   Variability   Units        Value
---------------------------------------------------------------------------------
 java.ci.totalTime                      Monotonic     Ticks        3242526906
 java.cls.loadedClasses                 Monotonic     Events       3404
 java.cls.sharedLoadedClasses           Monotonic     Events       0
 java.cls.sharedUnloadedClasses         Monotonic     Events       0
 java.cls.unloadedClasses               Monotonic     Events       0
```

### [#](https://arthas.aliyun.com/doc/perfcounter.html#jdk9-以上的应用)jdk9 以上的应用

如果没有打印出信息，应用在启动时，加下面的参数：



```text
--add-opens java.base/jdk.internal.perf=ALL-UNNAMED --add-exports java.base/jdk.internal.perf=ALL-UN
```