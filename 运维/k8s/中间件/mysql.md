镜像版本：mysql:5.7.35

环境变量：MYSQL_ROOT_PASSWORD  123456

数据存储：/var/lib/mysql

配置文件：/etc/mysql/conf.d   特定建：my.cnf



配置文件内容：

```properties
[client]
default-character-set=utf8mb4
 
[mysql]
default-character-set=utf8mb4
[mysqld]
init_connect='SET collation_connection = utf8mb4_general_ci'
init_connect='SET NAMES utf8mb4'
character-set-server=utf8mb4
collation-server=utf8mb4_general_ci
skip-character-set-client-handshake
skip-name-resolve
explicit_defaults_for_timestamp=ON
```

