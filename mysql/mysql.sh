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
