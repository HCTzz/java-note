# [MySQL - binlog日志简介及设置](https://www.cnblogs.com/hongdada/p/10983768.html)

## 基本概念

binlog是Mysql sever层维护的一种二进制日志，与innodb引擎中的redo/undo log是完全不同的日志；其主要是用来记录对mysql数据更新或潜在发生更新的SQL语句，记录了所有的DDL和DML(除了数据查询语句)语句，并以`事务`的形式保存在磁盘中，还包含语句所执行的消耗的时间，MySQL的二进制日志是事务安全型的。

一般来说开启二进制日志大概会有1%的性能损耗(参见MySQL官方中文手册 5.1.24版)。

作用主要有：

- 复制：MySQL Replication在Master端开启binlog，Master把它的二进制日志传递给slaves并回放来达到master-slave数据一致的目的
- 数据恢复：通过mysqlbinlog工具恢复数据
- 增量备份

二进制日志包括两类文件：二进制日志索引文件（文件名后缀为.index）用于记录所有的二进制文件，二进制日志文件（文件名后缀为.00000*）记录数据库所有的DDL和DML(除了数据查询语句)语句事件。

## 日志管理

### 开启binlog

修改配置文件 `my.cnf`

配置 `log-bin` 和 `log-bin-index` 的值，如果没有则自行加上去。

```
Copylog-bin=mysql-bin
log-bin-index=mysql-bin.index
```

这里的 `log-bin` 是指以后生成各 Binlog 文件的前缀，比如上述使用`master-bin`，那么文件就将会是`master-bin.000001`、`master-bin.000002` 等。

`log-bin-index` 则指 binlog index 文件的名称，这里我们设置为`master-bin.index`，可以不配置。

### 命令查看配置

binlog开启后，可以在配置文件中查看其位置信息，也可以在myslq命令行中查看：

```
Copymysql> show variables like '%log_bin%';
+---------------------------------+---------------------------------------------+
| Variable_name                   | Value                                       |
+---------------------------------+---------------------------------------------+
| log_bin                         | ON                                          |
| log_bin_basename                | D:\Program Files\MySQL\data\mysql-bin       |
| log_bin_index                   | D:\Program Files\MySQL\data\mysql-bin.index |
| log_bin_trust_function_creators | OFF                                         |
| log_bin_use_v1_row_events       | OFF                                         |
| sql_log_bin                     | ON                                          |
+---------------------------------+---------------------------------------------+
6 rows in set (0.07 sec)
```

### 查看binlog文件列表

```text
Copymysql>  show binary logs;
+------------------+-----------+-----------+
| Log_name         | File_size | Encrypted |
+------------------+-----------+-----------+
| mysql-bin.000001 |       202 | No        |
| mysql-bin.000002 |      2062 | No        |
+------------------+-----------+-----------+
2 rows in set (0.07 sec)
```

binlog文件开启binlog后，会在数据目录（默认）生产host-bin.n（具体binlog信息）文件及host-bin.index索引文件（记录binlog文件列表）。当binlog日志写满(binlog大小max_binlog_size，默认1G),或者数据库重启才会生产新文件，但是也可通过手工进行切换让其重新生成新的文件（flush logs）；另外，如果正使用大的事务，由于一个事务不能横跨两个文件，因此也可能在binlog文件未满的情况下刷新文件。

### 查看日志状态

```
Copymysql> show master status;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000002 |     2062 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.08 sec)
```

显示正在写入的二进制文件，及当前position

### 刷新日志

```
Copymysql> flush logs;
Query OK, 0 rows affected (0.12 sec)

mysql>  show binary logs;
+------------------+-----------+-----------+
| Log_name         | File_size | Encrypted |
+------------------+-----------+-----------+
| mysql-bin.000001 |       202 | No        |
| mysql-bin.000002 |      2109 | No        |
| mysql-bin.000003 |       155 | No        |
+------------------+-----------+-----------+
3 rows in set (0.07 sec)
```

自此刻开始产生一个新编号的binlog日志文件

每当mysqld服务重启时，会自动执行此命令，刷新binlog日志；在mysqldump备份数据时加 -F 选项也会刷新binlog日志；

### 重置(清空)所有binlog日志

```
Copymysql> reset master;
```

## 常用命令

### mysqlbinlog查看日志

```
CopyD:\Program Files\MySQL
$ bin\mysqlbinlog data\mysql-bin.000002
```

在MySQL5.5以下版本使用mysqlbinlog命令时如果报错，就加上 “--no-defaults”选项

- mysqlbinlog是mysql官方提供的一个binlog查看工具，
  - 也可使用–read-from-remote-server从远程服务器读取二进制日志，
  - 还可使用--start-position --stop-position、--start-time= --stop-time精确解析binlog日志

内容：

```
CopyBINLOG '
K3L4XBMBAAAARQAAAHEGAAAAAJoCAAAAAAEACmxvbmdodWJhbmcABXRoZW1lAAUDDwUREQWWAAgA
AAABAQACASGhIgQL
K3L4XB4BAAAAPQAAAK4GAAAAAJoCAAAAAAEAAgAF/wA0AQAABGFhYWEAAAAAAMBYQFz4citc+HIr
sXjMIA==
'/*!*/;
# at 1710
#190606  9:53:47 server id 1  end_log_pos 1741 CRC32 0xddb08f33         Xid = 216
COMMIT/*!*/;
# at 1741
#190606  9:53:47 server id 1  end_log_pos 1820 CRC32 0x166b4128         Anonymous_GTID  last_committed=5        sequence_number=6       rbr_only=yes    original_committed_timestamp=1559786027387679   immediate_commit_timestamp=15597860273
        transaction_length=321
/*!50718 SET TRANSACTION ISOLATION LEVEL READ COMMITTED*//*!*/;
# original_commit_timestamp=1559786027387679 (2019-06-06 09:53:47.387679 ?D1ú±ê×?ê±??)
# immediate_commit_timestamp=1559786027387679 (2019-06-06 09:53:47.387679 ?D1ú±ê×?ê±??)
/*!80001 SET @@session.original_commit_timestamp=1559786027387679*//*!*/;
/*!80014 SET @@session.original_server_version=80016*//*!*/;
/*!80014 SET @@session.immediate_server_version=80016*//*!*/;
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 1820
#190606  9:53:47 server id 1  end_log_pos 1901 CRC32 0x47def222         Query   thread_id=10    exec_time=0     error_code=0
SET TIMESTAMP=1559786027/*!*/;
BEGIN
/*!*/;
# at 1901
#190606  9:53:47 server id 1  end_log_pos 1970 CRC32 0x5a235198         Table_map: `longhubang`.`theme` mapped to number 666
# at 1970
#190606  9:53:47 server id 1  end_log_pos 2031 CRC32 0x62dc1928         Write_rows: table id 666 flags: STMT_END_F
```

### show binlog events查看binlog日志

```
CopyA.查询第一个(最早)的binlog日志：
  mysql> show binlog events; 

B.指定查询 mysql-bin.000021 这个文件：
  mysql> show binlog events in 'mysql-bin.000021';

C.指定查询 mysql-bin.000021 这个文件，从pos点:8224开始查起：
  mysql> show binlog events in 'mysql-bin.000021' from 8224;

D.指定查询 mysql-bin.000021 这个文件，从pos点:8224开始查起，查询10条
  mysql> show binlog events in 'mysql-bin.000021' from 8224 limit 10;

E.指定查询 mysql-bin.000021 这个文件，从pos点:8224开始查起，偏移2行，查询10条
  mysql> show binlog events in 'mysql-bin.000021' from 8224 limit 2,10;
```

内容：

```
Copymysql> show binlog events  in 'mysql-bin.000002' from 1710 limit 10;
+------------------+------+----------------+-----------+-------------+--------------------------------------+
| Log_name         | Pos  | Event_type     | Server_id | End_log_pos | Info                                 |
+------------------+------+----------------+-----------+-------------+--------------------------------------+
| mysql-bin.000002 | 1710 | Xid            |         1 |        1741 | COMMIT /* xid=216 */                 |
| mysql-bin.000002 | 1741 | Anonymous_Gtid |         1 |        1820 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS' |
| mysql-bin.000002 | 1820 | Query          |         1 |        1901 | BEGIN                                |
| mysql-bin.000002 | 1901 | Table_map      |         1 |        1970 | table_id: 666 (longhubang.theme)     |
| mysql-bin.000002 | 1970 | Write_rows     |         1 |        2031 | table_id: 666 flags: STMT_END_F      |
| mysql-bin.000002 | 2031 | Xid            |         1 |        2062 | COMMIT /* xid=223 */                 |
| mysql-bin.000002 | 2062 | Rotate         |         1 |        2109 | mysql-bin.000003;pos=4               |
+------------------+------+----------------+-----------+-------------+--------------------------------------+
7 rows in set (0.14 sec)
```

## 数据恢复

### 完全备份

```
CopyD:\Program Files\MySQL
$ bin\mysqldump  -h127.0.0.1 -p3306 -uroot -phongda$123456 -lF -B longhubang >D:\data\backup\longhubang.dump
mysqldump: [Warning] Using a password on the command line interface can be insecure.
```

注意要创建好D:\data\backup文件夹。

这里使用了`-lF`，注意必须大写F,当备份工作刚开始时系统会刷新log日志，产生新的binlog日志来记录备份之后的数据库“增删改”操作。

查看一下：

```
Copymysql> show binary logs;
+------------------+-----------+-----------+
| Log_name         | File_size | Encrypted |
+------------------+-----------+-----------+
| mysql-bin.000001 |       202 | No        |
| mysql-bin.000002 |      2109 | No        |
| mysql-bin.000003 |       374 | No        |
| mysql-bin.000004 |       155 | No        |
+------------------+-----------+-----------+
4 rows in set (0.10 sec)
```

也就是说， mysql-bin.000004 是用来记录**完全备份命令时间**之后对数据库的所有“增删改”操作。

Linux数据备份命令：

```
Copy/usr/local/mysql/bin/mysqldump -uroot -p123456 -lF --log-error=/root/myDump.err -B zyyshop > /root/BAK.zyyshop.sql
```

### 数据恢复

经过一段时间，数据库出现问题，需要恢复

```
Copy mysql> flush logs;
```

此时执行一次刷新日志索引操作，重新开始新的binlog日志记录文件，理论说 mysql-bin.000004 这个文件不会再有后续写入了(便于我们分析原因及查找pos点)，以后所有数据库操作都会写入到下一个日志文件；

查看binlog日志：

```
Copymysql> show binlog events in 'mysql-bin.000004';
```

最后一段日志内容：

```
Copy| mysql-bin.000004 | 3976 | Xid            |         1 |        4007 | COMMIT /* xid=2375 */                    |
| mysql-bin.000004 | 4007 | Anonymous_Gtid |         1 |        4086 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS'     |
| mysql-bin.000004 | 4086 | Query          |         1 |        4167 | BEGIN                                    |
| mysql-bin.000004 | 4167 | Table_map      |         1 |        4236 | table_id: 666 (longhubang.theme)         |
| mysql-bin.000004 | 4236 | Delete_rows    |         1 |        4505 | table_id: 666 flags: STMT_END_F          |
| mysql-bin.000004 | 4505 | Xid            |         1 |        4536 | COMMIT /* xid=2393 */                    |
| mysql-bin.000004 | 4536 | Anonymous_Gtid |         1 |        4613 | SET @@SESSION.GTID_NEXT= 'ANONYMOUS'     |
| mysql-bin.000004 | 4613 | Query          |         1 |        4736 | drop database  longhubang /* xid=2411 */ |
| mysql-bin.000004 | 4736 | Rotate         |         1 |        4783 | mysql-bin.000005;pos=4                   |
+------------------+------+----------------+-----------+-------------+------------------------------------------+
70 rows in set (0.21 sec)
```

通过分析，造成数据库破坏的pos点区间是介于4613--4736 之间，只要恢复到4613前就可。

先进行完全备份恢复：

```
CopyD:\Program Files\MySQL
$ bin\mysql  -h127.0.0.1 -p3306 -uroot -phongda$123456 -v <D:\data\backup\longhubang.dump
```

binlog日志恢复：

```
CopyD:\Program Files\MySQL
$ bin\mysqlbinlog    --stop-position=4613    data\mysql-bin.000004 | bin\mysql  -h127.0.0.1 -p3306 -uroot -phongda$123456  longhubang
mysql: [Warning] Using a password on the command line interface can be insecure.
```

增量数据恢复语法格式：

```
Copymysqlbinlog mysql-bin.0000xx | mysql -u用户名 -p密码 数据库名

常用选项：
  --start-position=953                   起始pos点
  --stop-position=1437                   结束pos点
  --start-datetime="2013-11-29 13:18:54" 起始时间点
  --stop-datetime="2013-11-29 13:21:53"  结束时间点
  --database=zyyshop                     指定只恢复zyyshop数据库(一台主机上往往有多个数据库，只限本地log日志)
    
不常用选项：    
  -u --user=name              Connect to the remote server as username.连接到远程主机的用户名
  -p --password[=name]        Password to connect to remote server.连接到远程主机的密码
  -h --host=name              Get the binlog from server.从远程主机上获取binlog日志
  --read-from-remote-server   Read binary logs from a MySQL server.从某个MySQL服务器上读取binlog日志
```

小结：实际是将读出的binlog日志内容，通过管道符传递给mysql命令。这些命令、文件尽量写成绝对路径；

上面的binlog恢复语句也可以拆分：

```
CopyD:\Program Files\MySQL
$ bin\mysqlbinlog   --stop-position=4613    data\mysql-bin.000004 > D:\data\backup\004.sql

D:\Program Files\MySQL
$ bin\mysql -h127.0.0.1 -p3306 -uroot -phongda$123456 longhubang
mysql: [Warning] Using a password on the command line interface can be insecure.
.......
 
mysql> source D:\data\backup\004.sql
```

所谓恢复，就是让mysql将保存在binlog日志中指定段落区间的sql语句逐个重新执行一次而已。

## 主从复制

复制是mysql最重要的功能之一，mysql集群的高可用、负载均衡和读写分离都是基于复制来实现的；从5.6开始复制有两种实现方式，基于binlog和基于GTID（全局事务标示符）；本文接下来将介绍基于binlog的一主一从复制；

其复制的基本过程如下：

1. Master将数据改变记录到二进制日志(binary log)中
2. Slave上面的IO进程连接上Master，并请求从指定日志文件的指定位置（或者从最开始的日志）之后的日志内容
3. Master接收到来自Slave的IO进程的请求后，负责复制的IO进程会根据请求信息读取日志指定位置之后的日志信息，返回给Slave的IO进程。返回信息中除了日志所包含的信息之外，还包括本次返回的信息已经到Master端的bin-log文件的名称以及bin-log的位置
4. Slave的IO进程接收到信息后，将接收到的日志内容依次添加到Slave端的relay-log文件的最末端，并将读取到的Master端的 bin-log的，文件名和位置记录到master-info文件中，以便在下一次读取的时候能够清楚的告诉Master从某个bin-log的哪个位置开始往后的日志内容
5. Slave的Sql进程检测到relay-log中新增加了内容后，会马上解析relay-log的内容成为在Master端真实执行时候的那些可执行的内容，并在自身执行