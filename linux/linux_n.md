
# �޸�root����:
	su 
	sudo passwd

# �鿴����ļ������
	sysctl -a | grep fs.file-max
	ulimit -n
	netstat -natp
	
# linux�ļ�������µ����������⡣
	1��limit -a    
	��ǰsession��Чulimit -n 2048
	2���޸�linuxϵͳ������
	vi /etc/security/limits.conf
	*����soft����nofile����65536
	*����hard����nofile����65536

#���ǿ���ͨ��vi�༭�����鿴�ļ���format��ʽ��
	- 1.������vi������ļ�
	[root@localhost test]# vim test.sh  
	- 2.��vi����ģʽ��ʹ�� :set ff ���� ���Կ������ļ��ĸ�ʽΪ fileformat=dos
	- 3.�޸��ļ�formatΪunix
	ʹ��vi/vim�޸��ļ�format ���:set ff=unix ���ߣ�:set fileformat=unix
	Ȼ��:wq�����˳��Ϳ�����

# centos �������⣨�������������Ա��޸ģ���
	vim /etc/sysconfig/network-scripts/ifcfg-eth0
	vim /etc/resolv.conf
	service network restart

# scp -r xxx root@xx.xx:/user/......

# uname 

# mysqlȱ������ ��
	find  / -name mysql -print
    ln -fs /usr/local/mysql/bin/mysqldump /usr/bin  

# ����ʱ�䣺
	1��yum install ntp 
	2��ntpdate ntp.api.bz  
	ʱ����/etc/share/zoneinfo/Shanghai  /etc/localtime

# �����Զ����ݣ�
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
	��Ӷ�ʱ����  40 23 * * * /usr/local/mysql_sh/mysqlback.sh


lrzsz�ļ����䡣

# centos��ʼ�����磺
https://blog.csdn.net/qq_32801733/article/details/81434165

# ubuntu��װjdk:
	ж�أ�https://www.cnblogs.com/happyflyingpig/p/8068020.html
	1��rpm -qa | grep jdk
	2��rpm -e --nodeps +�汾��
	��װ��
	1��sudo vim /etc/profile
	#set java env
	export JAVA_HOME=/usr/local/java/jdk
	export JRE_HOME=${JAVA_HOME}/jre    
	export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib    
	export PATH=${JAVA_HOME}/bin:$PATH 
	2��source /etc/profile 

# ����ǽ
	ִ��firewall-cmd --permanent --zone=public --add-port=3306/tcp����ʾFirewallD is not running������ͼ��ʾ��
	centos���֡�FirewallD is not running����ô��
	ͨ��systemctl status firewalld�鿴firewalld״̬�����ֵ�ǰ��dead״̬��������ǽδ����
	centos���֡�FirewallD is not running����ô��
	ͨ��systemctl start firewalld��������ǽ��û���κ���ʾ�������ɹ���
	centos���֡�FirewallD is not running����ô��
	�ٴ�ͨ��systemctl status firewalld�鿴firewalld״̬����ʾrunning���ѿ����ˡ�
	centos���֡�FirewallD is not running����ô��
	5
	���Ҫ�رշ���ǽ���ã�����ͨ��systemctl stop firewalld����ָ�����رոù��ܡ�
	centos���֡�FirewallD is not running����ô��
	6
	�ٴ�ִ��ִ��firewall-cmd --permanent --zone=public --add-port=3306/tcp����ʾsuccess����ʾ���óɹ��������Ϳ��Լ�������������ˡ�

# head -n 100 �鿴100��
# tail -n 100 ĩβ100��
# tail -n +100 �ӵ�һ���п�ʼ
	1���ӵ�3000�п�ʼ����ʾ1000�С�����ʾ3000~3999��
	cat filename | tail -n +3000 | head -n 1000
	2����ʾ1000�е�3000��
	cat filename| head -n 3000 | tail -n +1000  
	3����sed����
	 sed -n '5,10p' filename ������Ϳ���ֻ�鿴�ļ��ĵ�5�е���10�С�