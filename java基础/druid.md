Druid有一个专门的线程定时负责销毁多余的空闲连接、失效连接、泄露连接。这个线程就DestroyConnectionThread。timeBetweenEvictionRunsMillis参数可以配置销毁任务执行的时间间隔。

timeBetweenEvictionRunsMillis: 300000

keepAliveBetweenTimeMillis: 180000



keep-alive-between-tme-millis: 180000



time-between-eviction-runs-millis: 300000