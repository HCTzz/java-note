修改root密码:

su 
sudo passwd


我们可以通过vi编辑器来查看文件的format格式。步骤如下：
1.首先用vi命令打开文件
[root@localhost test]# vim test.sh  
2.在vi命令模式中使用 :set ff 命令
可以看到改文件的格式为 fileformat=dos
3.修改文件format为unix
使用vi/vim修改文件format 命令：:set ff=unix 或者：:set fileformat=unix
然后:wq保存退出就可以了



2、centos 网络问题（与正常服务器对比修改）：
vim /etc/sysconfig/network-scripts/ifcfg-eth0
vim /etc/resolv.conf
service network restart


3、scp -r xxx root@xx.xx:/user/......

4、uname 

5、mysql缺少命令 ：find  / -name mysql -print
                   ln -fs /usr/local/mysql/bin/mysqldump /usr/bin  

6、设置时间：1、yum install ntp 2、ntpdate ntp.api.bz  时区：/etc/share/zoneinfo/Shanghai

7、设置自动备份：
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

crontab -e 添加定时任务  40 23 * * * /usr/local/mysql_sh/mysqlback.sh


lrzsz文件传输。



centos初始化网络：
https://blog.csdn.net/qq_32801733/article/details/81434165


ubuntu安装jdk:
卸载：https://www.cnblogs.com/happyflyingpig/p/8068020.html
1、sudo vim /etc/profile
#set java env
export JAVA_HOME=/usr/lib/jdk/jdk1.8.0_201
export JRE_HOME=${JAVA_HOME}/jre    
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib    
export PATH=${JAVA_HOME}/bin:$PATH 
2、source /etc/profile 