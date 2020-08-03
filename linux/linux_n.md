
# 修改root密码:
	su 
	sudo passwd

# 查看最大文件句柄数
	sysctl -a | grep fs.file-max
	ulimit -n
	netstat -natp
	
# linux文件句柄导致的吞吐量问题。
	1、limit -a    
	当前session生效ulimit -n 2048
	2、修改linux系统参数。
	vi /etc/security/limits.conf
	*　　soft　　nofile　　65536
	*　　hard　　nofile　　65536

#我们可以通过vi编辑器来查看文件的format格式。
	- 1.首先用vi命令打开文件
	[root@localhost test]# vim test.sh  
	- 2.在vi命令模式中使用 :set ff 命令 可以看到改文件的格式为 fileformat=dos
	- 3.修改文件format为unix
	使用vi/vim修改文件format 命令：:set ff=unix 或者：:set fileformat=unix
	然后:wq保存退出就可以了

# centos 网络问题（与正常服务器对比修改）：
	vim /etc/sysconfig/network-scripts/ifcfg-eth0
	vim /etc/resolv.conf
	service network restart

# scp -r xxx root@xx.xx:/user/......

# uname 

# mysql缺少命令 ：
	find  / -name mysql -print
    ln -fs /usr/local/mysql/bin/mysqldump /usr/bin  

# 设置时间：
	1、yum install ntp 
	2、ntpdate ntp.api.bz  
	时区：/etc/share/zoneinfo/Shanghai  /etc/localtime

# 设置自动备份：
	#!/bin/bash
	userName="root"
	password="123456"
	dataBase="zeln"
 
	DATE=`date '+%Y%m%d'`
	BACKUP_DIR="/home/mysql_back/"
	seven_day_ago=`date +%Y%m%d --date '7 days ago'`
	echo $seven_day_ago
	if [ ! -d $BACKUP_DIR$DATE ] ;
	then
		mkdir -p $BACKUP_DIR$DATE
	fi
	mysqldump --user=$userName --password=$password --host="localhost" $dataBase | gzip > $BACKUP_DIR$DATE/$dataBase$DATE.sql.gz
	if [ -d $BACKUP_DIR$seven_day_ago ] ;        
	then
		rm -rf $BACKUP_DIR$seven_day_ago
	fi
	
# crontab -e 
	添加定时任务  40 23 * * * /usr/local/mysql_sh/mysqlback.sh


lrzsz文件传输。

# centos初始化网络：
https://blog.csdn.net/qq_32801733/article/details/81434165

# ubuntu安装jdk:
	卸载：https://www.cnblogs.com/happyflyingpig/p/8068020.html
	1、rpm -qa | grep jdk
	2、rpm -e --nodeps +版本号
	安装：
	1、sudo vim /etc/profile
	#set java env
	export JAVA_HOME=/usr/local/java/jdk
	export JRE_HOME=${JAVA_HOME}/jre    
	export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib    
	export PATH=${JAVA_HOME}/bin:$PATH 
	2、source /etc/profile 

# 防火墙
	执行firewall-cmd --permanent --zone=public --add-port=3306/tcp，提示FirewallD is not running，如下图所示。
	centos出现“FirewallD is not running”怎么办
	通过systemctl status firewalld查看firewalld状态，发现当前是dead状态，即防火墙未开启
	centos出现“FirewallD is not running”怎么办
	通过systemctl start firewalld开启防火墙，没有任何提示即开启成功。
	centos出现“FirewallD is not running”怎么办
	再次通过systemctl status firewalld查看firewalld状态，显示running即已开启了。
	centos出现“FirewallD is not running”怎么办
	5
	如果要关闭防火墙设置，可能通过systemctl stop firewalld这条指令来关闭该功能。
	centos出现“FirewallD is not running”怎么办
	6
	再次执行执行firewall-cmd --permanent --zone=public --add-port=3306/tcp，提示success，表示设置成功，这样就可以继续后面的设置了。

# head -n 100 查看100行
# tail -n 100 末尾100行
# tail -n +100 从第一百行开始
	1、从第3000行开始，显示1000行。即显示3000~3999行
	cat filename | tail -n +3000 | head -n 1000
	2、显示1000行到3000行
	cat filename| head -n 3000 | tail -n +1000  
	3、用sed命令
	 sed -n '5,10p' filename 这样你就可以只查看文件的第5行到第10行。