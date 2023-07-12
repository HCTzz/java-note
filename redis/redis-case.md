### 1、redis-check-aof --fix修复持久化的 AOF文件

服务器可能在程序正在对 AOF 文件进行写入时停机， 如果停机造成了 AOF 文件出错（corrupt）， 那么 Redis 在重启时会拒绝载入这个 AOF 文件， 从而确保数据的一致性不会被破坏。

这时候可以使用可以先使用 Redis 附带的 `redis-check-aof` 程序，对原来的 AOF 文件进行修复，进而再启动redis

```
redis-check-aof --fix AOF文件
```

### 2、运行时配置更改参数

```
CONFIG SET parameter value

CONFIG SET SAVE “900 1 300 10”
```

