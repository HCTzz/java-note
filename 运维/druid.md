**minEvictableIdleTimeMillis**：最小空闲时间，默认30分钟，如果连接池中非运行中的连接数大于minIdle，并且那部分连接的非运行时间大于minEvictableIdleTimeMillis，则连接池会将那部分连接设置成Idle状态并关闭；也就是说如果一条连接30分钟都没有使用到，并且这种连接的数量超过了minIdle，则这些连接就会被关闭了。

**maxEvictableIdleTimeMillis**：最大空闲时间，默认7小时，如果minIdle设置得比较大，连接池中的空闲连接数一直没有超过minIdle，这时那些空闲连接是不是一直不用关闭？当然不是，如果连接太久没用，数据库也会把它关闭，这时如果连接池不把这条连接关闭，系统就会拿到一条已经被数据库关闭的连接。为了避免这种情况，Druid会判断池中的连接如果非运行时间大于maxEvictableIdleTimeMillis，也会强行把它关闭，而不用判断空闲连接数是否小于minIdle；

















/data/nginxWebUI/conf.d                      /home/nginxWebUI/conf.d



/data/nginxWebUI/log                             /home/nginxWebUI/log



/data/nginxWebUI/nginx.conf               /home/nginxWebUI/nginx.conf

 















