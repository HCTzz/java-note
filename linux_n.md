�޸�root����:

su 
sudo passwd


���ǿ���ͨ��vi�༭�����鿴�ļ���format��ʽ���������£�
1.������vi������ļ�
[root@localhost test]# vim test.sh  
2.��vi����ģʽ��ʹ�� :set ff ����
���Կ������ļ��ĸ�ʽΪ fileformat=dos
3.�޸��ļ�formatΪunix
ʹ��vi/vim�޸��ļ�format ���:set ff=unix ���ߣ�:set fileformat=unix
Ȼ��:wq�����˳��Ϳ�����



2��centos �������⣨�������������Ա��޸ģ���
vim /etc/sysconfig/network-scripts/ifcfg-eth0
vim /etc/resolv.conf
service network restart


3��scp -r xxx root@xx.xx:/user/......

4��uname 

5��mysqlȱ������ ��find  / -name mysql -print
                   ln -fs /usr/local/mysql/bin/mysqldump /usr/bin  

6������ʱ�䣺1��yum install ntp 2��ntpdate ntp.api.bz  ʱ����/etc/share/zoneinfo/Shanghai

7�������Զ����ݣ�
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

crontab -e ��Ӷ�ʱ����  40 23 * * * /usr/local/mysql_sh/mysqlback.sh


lrzsz�ļ����䡣



centos��ʼ�����磺
https://blog.csdn.net/qq_32801733/article/details/81434165


ubuntu��װjdk:
ж�أ�https://www.cnblogs.com/happyflyingpig/p/8068020.html
1��sudo vim /etc/profile
#set java env
export JAVA_HOME=/usr/lib/jdk/jdk1.8.0_201
export JRE_HOME=${JAVA_HOME}/jre    
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib    
export PATH=${JAVA_HOME}/bin:$PATH 
2��source /etc/profile 