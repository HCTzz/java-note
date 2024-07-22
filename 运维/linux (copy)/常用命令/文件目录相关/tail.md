tail 命令用于显示文件的尾部内容。在默认情况下，tail 命令显示文件的尾部 10 行内容。如果给定的文件不止一个，则在显示的每个文件前面加一个文件名标题。如果没有指定文件或者文件名为“-”，则读取标准输入。

**参数**：

```shell
-c,   --bytes=K	输出最后 K 个字节
-f,   --follow[={name|descriptor}]	当文件增长时，输出后续添加的数据
-F	和 --follow=name --retry 一样
-n   --lines=K	输出最后 K 行，而非默认的最后 10 行
--max-unchanged-stats=N	与 --follow=name 合用，
--pid=PID	与 -f 合用，表示在进程 ID、PID 死掉之后结束。
-q, --quiet, --silent	从不输出给出文件名的首部
--retry	即使 tail 开始时就不能访问，或者在 tail 运行后不能访问，也仍然不停地尝试打开文件，-- 只与 -f 合用时有用。
-s, --sleep-interval=N	与 -f 合用，表示在每次反复的间隔休眠 N 秒
-v, --verbose	总是输出给出文件名的首部
--help	显示帮助信息后退出
--version	输出版本信息后退出
```

-f与-F的区别：

-f当文件inode值变化时是无感知的，也就是不会展示后续新增的内容。-F有感知。