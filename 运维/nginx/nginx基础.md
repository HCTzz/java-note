https:/w3techs.com
 
安装服务器需要的一些服务以及vim编辑器
yum -y install gcc gcc-c++ autoconf pcre-devel make automake
yum -y install wget httpd-tools vim  

查看一下yum是否已经存在
yum list | grep nginx

生成一个yum源
vim /etc/yum.repos.d/nginx.repos
把下面的代码复制进去 【首先先点击i】
[nginx]
name=nginx repo
baseurl=http://nginx.org/packages/OS/OSRELEASE/$basearch/ [os改为当前系统名称centos/7]
gpgcheck=0
enabled=1
开始安装
yum install nginx
检测版本
nginx -v


检测安装路径
rpm -ql nginx
配置文件
/etc/nginx/nginx.conf
#运行用户，默认即是nginx，可以不进行设置
user  nginx;
#Nginx进程，一般设置为和CPU核数一样
worker_processes  1;   
#错误日志存放目录
error_log  /var/log/nginx/error.log warn;
#进程pid存放位置
pid        /var/run/nginx.pid;
events {
    worker_connections  1024; # 单个后台进程的最大并发数
}
http {
    include       /etc/nginx/mime.types;   #文件扩展名与类型映射表
    default_type  application/octet-stream;  #默认文件类型
    #设置日志模式
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  /var/log/nginx/access.log  main;   #nginx访问日志存放位置
    sendfile        on;   #开启高效传输模式
    #tcp_nopush     on;    #减少网络报文段的数量
    keepalive_timeout  65;  #保持连接的时间，也叫超时时间
    #gzip  on;  #开启gzip压缩
    include /etc/nginx/conf.d/*.conf; #包含的子配置项位置和文件
｝
default.conf 子文件  conf.d目录下
server {
    listen       80;   #配置监听端口
    server_name  localhost;  //配置域名
    #charset koi8-r;     
    #access_log  /var/log/nginx/host.access.log  main;
    location / {
        root   /usr/share/nginx/html;     #服务默认启动目录
        index  index.html index.htm;    #默认访问文件
    }
    #error_page  404              /404.html;   # 配置404页面
    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;   #错误状态码的显示页面，配置后需要重启
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

启动nginx
1、nginx
2、systemctl start nginx.service  linux命令启动
查询启动情况
ps aux | grep nginx

停止nginx
1、立即停止服务【无论进程是否在工作，都直接停止进程】
nginx  -s stop 
2、从容停止服务【需要进程完成当前工作后再停止。】
nginx -s quit
3、killall 方法杀死进程
killall nginx
4、systemctl 停止
systemctl stop nginx.service
重启Nginx服务
systemctl restart nginx.service
重新载入配置文件
nginx -s reload
查看端口号
当端口号被占用时netstat -tlnp命令查看端口号的占用情况。

Gzip检测 检测网站是否支持网页压缩







