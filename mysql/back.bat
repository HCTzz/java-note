rem auther:zj
rem date:2018-12-03
rem ******MySQL backup start********
@echo off
forfiles /p "D:\mysql_back\MySQL BackUp" /m backup_*.sql -d -20 /c "cmd /c del /f @path"
set "Ymd=%date:~0,4%%date:~5,2%%date:~8,2%0%time:~1,1%%time:~3,2%%time:~6,2%"
"D:\njzjServe\mysql-5.6.40\bin\mysqldump" --opt --single-transaction=TRUE --user=zj --password=123456 --host=127.0.0.1 --protocol=tcp --port=3306 --default-character-set=utf8 --single-transaction=TRUE --routines --events "yumi_website" > "D:\mysql_back\MySQL BackUp\backup_%Ymd%.sql"
@echo on
rem ******MySQL backup end********