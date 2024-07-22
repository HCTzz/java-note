# Linux查找占用磁盘IO读写很高的进程方法

**```通过 iostat -x 1 10 查看相关磁盘使用信息```**

```shell
# 如果没有 iostat 命令，那么使用 yum install sysstat 进行安装
# iostat -x 1 10
```

![Linux 查找占用磁盘IO读写很高的进程方法Linux 查找占用磁盘IO读写很高的进程方法](E:\learn\git\repository\笔记\java-note\java基础\jvm\排查实例\img\20200209001.png)

由上图可知，vdb磁盘的 %util【IO】几乎都在100%，原因是频繁的读取数据造成的。

**其他字段说明**

```
Device：设备名称
tps：每秒的IO读、写请求数量，多个逻辑请求可以组合成对设备的单个I/O请求。
Blk_read/s (kB_read/s, MB_read/s)：从设备读取的数据量，以每秒若干块(千字节、兆字节)表示。块相当于扇区，因此块大小为512字节。
Blk_wrtn/s (kB_wrtn/s, MB_wrtn/s)：写入设备的数据量，以每秒若干块(千字节、兆字节)表示。块相当于扇区，因此块大小为512字节。
Blk_read (kB_read, MB_read)：读取块的总数(千字节、兆字节)。
Blk_wrtn (kB_wrtn, MB_wrtn)：写入块的总数(千字节，兆字节)。

rrqm/s：每秒合并到设备的读请求数。即delta(rmerge)/s
wrqm/s：每秒合并到设备的写入请求数。即delta(wmerge)/s
r/s：每秒完成的读I/O设备次数。即delta(rio)/s
w/s：每秒完成的写I/0设备次数。即delta(wio)/s
rsec/s (rkB/s, rMB/s)：每秒读取设备的扇区数(千字节、兆字节)。每扇区大小为512字节
wsec/s (wkB/s, wMB/s)：每秒写入设备的扇区数(千字节、兆字节)。每扇区大小为512字节

avgrq-sz：平均每次设备I/O操作的数据量(扇区为单位)。即delta(rsec+wsec)/delta(rio+wio)
avgqu-sz：平均每次发送给设备的I/O队列长度。
await：平均每次IO请求等待时间。(包括等待队列时间和处理时间，毫秒为单位)
r_await：平均每次IO读请求等待时间。(包括等待队列时间和处理时间，毫秒为单位)
w_await：平均每次IO写请求等待时间。(包括等待队列时间和处理时间，毫秒为单位)
svctm：平均每次设备I/O操作的处理时间(毫秒)。警告！不要再相信这个字段值，这个字段将在将来的sysstat版本中删除。
%util：一秒中有百分之多少的时间用于I/O操作，或者说一秒中有多少时间I/O队列是非空的。当该值接近100%时，设备饱和发生。
```

**找到 IO 占用高的进程**

**通过 iotop [命令](https://www.linuxcool.com/)**

如果没有该命令，请通过 yum install iotop 进行安装。

```shell
# iotop -oP
```

![Linux 查找占用磁盘IO读写很高的进程方法Linux 查找占用磁盘IO读写很高的进程方法](.\img\20200209002.png)

通过这个命令可以看见比较详细信息，如：进程号，磁盘读取量，磁盘写入量，IO百分比，涉及到的命令是什么「两个都是 grep 命令造成的IO读取量大」。

**通过 pidstat 命令**

```shell
# 命令的含义：展示I/O统计，每秒更新一次
# pidstat -d 1
```

![Linux 查找占用磁盘IO读写很高的进程方法Linux 查找占用磁盘IO读写很高的进程方法](.\img\20200209003.png)

可见其中 grep 命令占用了大量的读IO，之后可根据 PID 查看相关进程信息。