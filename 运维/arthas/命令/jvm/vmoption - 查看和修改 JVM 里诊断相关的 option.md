<center><font size="4"><b>查看和修改 JVM 里诊断相关的 option</b></font></center>

# vmoption

提示

查看，更新 VM 诊断相关的参数

### [#](https://arthas.aliyun.com/doc/vmoption.html#使用参考)使用参考

#### [#](https://arthas.aliyun.com/doc/vmoption.html#查看所有的-option)查看所有的 option

```bash
[arthas@56963]$ vmoption
 KEY                    VALUE                   ORIGIN                 WRITEABLE
---------------------------------------------------------------------------------------------
 HeapDumpBeforeFullGC   false                   DEFAULT                true
 HeapDumpAfterFullGC    false                   DEFAULT                true
 HeapDumpOnOutOfMemory  false                   DEFAULT                true
 Error
 HeapDumpPath                                   DEFAULT                true
 CMSAbortablePrecleanW  100                     DEFAULT                true
 aitMillis
 CMSWaitDuration        2000                    DEFAULT                true
 CMSTriggerInterval     -1                      DEFAULT                true
 PrintGC                false                   DEFAULT                true
 PrintGCDetails         true                    MANAGEMENT             true
 PrintGCDateStamps      false                   DEFAULT                true
 PrintGCTimeStamps      false                   DEFAULT                true
 PrintGCID              false                   DEFAULT                true
 PrintClassHistogramBe  false                   DEFAULT                true
 foreFullGC
 PrintClassHistogramAf  false                   DEFAULT                true
 terFullGC
 PrintClassHistogram    false                   DEFAULT                true
 MinHeapFreeRatio       0                       DEFAULT                true
 MaxHeapFreeRatio       100                     DEFAULT                true
 PrintConcurrentLocks   false                   DEFAULT                true
```

#### [#](https://arthas.aliyun.com/doc/vmoption.html#查看指定的-option)查看指定的 option

```bash
$ vmoption PrintGC
 KEY                 VALUE                ORIGIN              WRITEABLE
---------------------------------------------------------------------------------
 PrintGC             false                MANAGEMENT          true
```

#### [#](https://arthas.aliyun.com/doc/vmoption.html#更新指定的-option)更新指定的 option

```bash
$ vmoption PrintGC true
Successfully updated the vm option.
 NAME     BEFORE-VALUE  AFTER-VALUE
------------------------------------
 PrintGC  false         true
```



```bash
$ vmoption PrintGCDetails true
Successfully updated the vm option.
 NAME            BEFORE-VALUE  AFTER-VALUE
-------------------------------------------
 PrintGCDetails  false         true
```