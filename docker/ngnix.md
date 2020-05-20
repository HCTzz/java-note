docker run --name nginx -d -p 80:80  -v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/nginx/log:/var/log/nginx -v /data/nginx/html:/usr/share/nginx/html nginx:1.8


--requirepass "123456"