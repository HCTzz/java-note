CentOS7 安装指定版本MySQL

首先到MySQL官网repo站点
http://repo.mysql.com/
找到 [FILE] mysql-community-release-el7.rpm  
回到CentOS系统中，使用rpm安装源
rpm -ivh http://repo.mysql.com/mysql-community-release-el7.rpm
yum clean all
yum makecache
yum repolist all |grep mysql 

mysql-connectors-community/x86_64 MySQL Connectors Community           启用: 108
mysql-connectors-community-source MySQL Connectors Community - Sour 禁用
mysql-tools-community/x86_64          MySQL Tools Community                      启用: 90
mysql-tools-community-source           MySQL Tools Community - Source       禁用
mysql55-community/x86_64               MySQL 5.5 Community Server             禁用
mysql55-community-source                MySQL 5.5 Community Server - Sour  禁用
mysql56-community/x86_64               MySQL 5.6 Community Server             启用: 463
mysql56-community-source                MySQL 5.6 Community Server - Sour  禁用
mysql57-community-dmr/x86_64　　MySQL 5.7 Community Server Develo 禁用
mysql57-community-dmr-source        MySQL 5.7 Community Server Develo 禁用

可以看到5.6是启用的，也就是此时yum 默认安装的是MySQL5.6 版本，

同样，在 /etc/yum.repos.d/mysql-community.repo 文件中可以修改，安装不同的版本

# Enable to use MySQL 5.6
[mysql56-community]
name=MySQL 5.6 Community Server
baseurl=http://repo.mysql.com/yum/mysql-5.6-community/el/7/$basearch/
enabled=1
gpgcheck=1
gpgkey=file:/etc/pki/rpm-gpg/RPM-GPG-KEY-mysql

字段的含义：
baseurl=：指定repo源
enabled=：有两个选项0和1,1表示启用，0表示关闭，在mysql-community.repo 文件中只能有一个enabled=1的源
gpgcheck=：是否开启校验
gpgkey=：校验文件路径

安装指定版本(5.6)MySQL，即可。
yum install mysql-community-server.x86_64 -y


二、数据库设置
1、systemctl start  mysqld.service  systemctl status mysqld.service
2、grep "password" /var/log/mysqld.log
3、mysql -uroot -p
4、set password for root@localhost = password('123');
5、 SHOW VARIABLES LIKE 'validate_password%'; 查看密码设置规则
6、yum -y remove mysql57-community-release-el7-10.noarch

7、systemctl enable（disabled） mysqld 开机启动
8、vi /etc/my.cnf #添加 [mysqld] character_set_server=utf8 init_connect='SET NAMES utf8'

update user set host='%' where user='root';
update user set password=PASSWORD('这里输入你的新密码要用单引号') where user='root';
flush privileges