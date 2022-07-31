<center><font size="4"><b>下载堆栈信息</b></font></center>

# heapdump

提示

dump java heap, 类似 jmap 命令的 heap dump 功能。

### [#](https://arthas.aliyun.com/doc/heapdump.html#使用参考)使用参考

#### [#](https://arthas.aliyun.com/doc/heapdump.html#dump-到指定文件)dump 到指定文件



```bash
[arthas@58205]$ heapdump /tmp/dump.hprof
Dumping heap to /tmp/dump.hprof...
Heap dump file created
```

#### [#](https://arthas.aliyun.com/doc/heapdump.html#只-dump-live-对象)只 dump live 对象



```bash
[arthas@58205]$ heapdump --live /tmp/dump.hprof
Dumping heap to /tmp/dump.hprof...
Heap dump file created
```

### [#](https://arthas.aliyun.com/doc/heapdump.html#dump-到临时文件)dump 到临时文件



```bash
[arthas@58205]$ heapdump
Dumping heap to /var/folders/my/wy7c9w9j5732xbkcyt1mb4g40000gp/T/heapdump2019-09-03-16-385121018449645518991.hprof...
Heap dump file created
```