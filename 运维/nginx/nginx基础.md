https:/w3techs.com
 
��װ��������Ҫ��һЩ�����Լ�vim�༭��
yum -y install gcc gcc-c++ autoconf pcre-devel make automake
yum -y install wget httpd-tools vim  

�鿴һ��yum�Ƿ��Ѿ�����
yum list | grep nginx

����һ��yumԴ
vim /etc/yum.repos.d/nginx.repos
������Ĵ��븴�ƽ�ȥ �������ȵ��i��
[nginx]
name=nginx repo
baseurl=http://nginx.org/packages/OS/OSRELEASE/$basearch/ [os��Ϊ��ǰϵͳ����centos/7]
gpgcheck=0
enabled=1
��ʼ��װ
yum install nginx
���汾
nginx -v


��ⰲװ·��
rpm -ql nginx
�����ļ�
/etc/nginx/nginx.conf
#�����û���Ĭ�ϼ���nginx�����Բ���������
user  nginx;
#Nginx���̣�һ������Ϊ��CPU����һ��
worker_processes  1;   
#������־���Ŀ¼
error_log  /var/log/nginx/error.log warn;
#����pid���λ��
pid        /var/run/nginx.pid;
events {
    worker_connections  1024; # ������̨���̵���󲢷���
}
http {
    include       /etc/nginx/mime.types;   #�ļ���չ��������ӳ���
    default_type  application/octet-stream;  #Ĭ���ļ�����
    #������־ģʽ
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  /var/log/nginx/access.log  main;   #nginx������־���λ��
    sendfile        on;   #������Ч����ģʽ
    #tcp_nopush     on;    #�������籨�Ķε�����
    keepalive_timeout  65;  #�������ӵ�ʱ�䣬Ҳ�г�ʱʱ��
    #gzip  on;  #����gzipѹ��
    include /etc/nginx/conf.d/*.conf; #��������������λ�ú��ļ�
��
default.conf ���ļ�  conf.dĿ¼��
server {
    listen       80;   #���ü����˿�
    server_name  localhost;  //��������
    #charset koi8-r;     
    #access_log  /var/log/nginx/host.access.log  main;
    location / {
        root   /usr/share/nginx/html;     #����Ĭ������Ŀ¼
        index  index.html index.htm;    #Ĭ�Ϸ����ļ�
    }
    #error_page  404              /404.html;   # ����404ҳ��
    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;   #����״̬�����ʾҳ�棬���ú���Ҫ����
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
    # proxy the PHP scripts to Apache listening on 127.0.0.1:80
    #
    #location ~ \.php$ {
    #    proxy_pass   http://127.0.0.1;
    #}
    # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
    #
    #location ~ \.php$ {
    #    root           html;
    #    fastcgi_pass   127.0.0.1:9000;
    #    fastcgi_index  index.php;
    #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
    #    include        fastcgi_params;
    #}
    # deny access to .htaccess files, if Apache's document root
    # concurs with nginx's one
    #
    #location ~ /\.ht {
    #    deny  all;
    #}
}

����nginx
1��nginx
2��systemctl start nginx.service  linux��������
��ѯ�������
ps aux | grep nginx

ֹͣnginx
1������ֹͣ�������۽����Ƿ��ڹ�������ֱ��ֹͣ���̡�
nginx  -s stop 
2������ֹͣ������Ҫ������ɵ�ǰ��������ֹͣ����
nginx -s quit
3��killall ����ɱ������
killall nginx
4��systemctl ֹͣ
systemctl stop nginx.service
����Nginx����
systemctl restart nginx.service
�������������ļ�
nginx -s reload
�鿴�˿ں�
���˿ںű�ռ��ʱnetstat -tlnp����鿴�˿ںŵ�ռ�������

Gzip��� �����վ�Ƿ�֧����ҳѹ��







