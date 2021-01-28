查看版本号：
1、uname -r
2、cat /etc/redhat-release

基础概念：
1、仓库（Repository）是集中存放镜像文件的场所
2、仓库和仓库注册服务器是有区别的。每个镜像有不同的标签（tag）。

安装：
centos6
1、yum install -y epel-release
2、yum install -y docker-io 
No package docker-io available  
yum install https://get.docker.com/rpm/1.7.1/centos-6/RPMS/x86_64/docker-engine-1.7.1-1.el6.x86_64.rpm
3、配置文件/etc/sysconfig/docker
4、启动服务service docker satrt
5、docker version
centos7
docs.docker.com

hello word
1、阿里云镜像加速地址  https://mtuohg4w.mirror.aliyuncs.com
2、other_args="--registry-mirror=https://mtuohg4w.mirror.aliyuncs.com"
3、ps ef|grep docker 
4、docker run hello-world 寻找镜像 -》 下载镜像 -》 生成实例 -》 运行

docker 基本原理：
docker client-server结构的系统，
docker守护进程运行在主机上，然后通过Socket连接从客户端访问，守护进程从客户端接受命令并管理运行在主机上的容器。
容器是一个运行时环境。
为什么socker比vm快：
1、docker有着比虚拟机更少的抽象层，
2、socker利用的是宿主机的内核不需要guest os.

docker常用命令
一、帮助命令
	1、docker version
	2、docker info 
	3、docker --help
二、镜像命令
	1、docker images   
		-a 列出本地所有镜像，包含中间映像层。
		-q 查询镜像的ID
		-digests 摘要
		--no-trunc 镜像完整信息
	2、docker search [option] 镜像名
		-s 点赞数 docker search -s 30 tomcat
		--no-trunc 
		--automated 自动构建的	
	3、docker pull 拉取镜像
		1、docker pull 镜像名[:tag]
	4、docker rimi 删除镜像
		1、docker rmi -f 镜像ID
		2、docker rmi -f 镜像名1:TAG 镜像名2:TAG
		3、docker rmi -f $(docker images -qa) 删除全部
		
三、容器命令：

```sh
1、新建并启动容器 docker run [options] image[command]][args...]
    1、options
        --name="新容器的名字"：为容器指定一个名称
        --restart 自动重启策略：
        	no:表示不自动重启容器，默认。
        	on-failure[:max-retries]:表示在退出状态为非0时才会重启（非正常退出），可选参数：最大重试次				数，可以设置最大重启次数
        	always:表示始终重启容器。
        	unless-stopped:表示始终重启容器，当docker守护进程启动时，如果容器已经停止运行，则不会重启。
        -d：后台运行容器，并返回容器ID，也即启动守护石容器
        -i：以交互模式运行容器，通常与-t同时使用
        -t: 为容器重新分配一个伪输入终端
        -P：随机端口映射
        -p：指定端口映射
        ip:hostport:containerport
        ip::containerport
        hostport:containerport
        containerport
2、docker ps 查看运行中的容器	
3、容器停止退出
	1、docker exit  停止并退出
	2、ctrl + p + q  
	3、docker attach 重新进入
4、启动容器
	1、docker start 容器ID/容器名
5、重启容器
	1、docker restart 容器ID/容器名
	2、
6、停止容器
	1、docker stop 容器id/容器名
	2、docker kill 容器ID/容器名
7、删除已停止的容器
	1、docker rm 容器ID/容器名
	2、docker rm -f $(docker ps -a -q) / docker ps -a -q | xargs docker rm 删除全部
8、重要命令
	1、启动守护试容器  docker run -d 容器名
	2、查看容器日志 docker logs -f -t --tail 容器id  f:跟随最新的日志打印  t：加入时间戳 --tail:显示最后多少条
	3、查看容器内运行的进程 docker top
	4、查看容器内部细节 docker inspect 容器ID/容器名
	5、进入正在运行的容器并以命令方式交互：
		1、docker attach 容器ID
		2、docker exec 容器ID  针对容器执行命令 docker exec -t 容器ID ls -l /tmp
			sudo docker exec -i -t nginx-ubuntu-container /bin/bash
	6、容器内的数据copy到主机上  docker cp 容器:路径 主机/路径
```

四、Docker镜像
		1、用来打包软件运行环境和基于运行环境开发的软件
		2、unionFs 联合文件系统 分层的轻量级并且高性能的文件系统，支持对文件系统的修改作为一次提交来一层层的叠加。
		3、Docker镜像加载原理
		4、分层的镜像   
		5、为什么采用分层的镜像：共享资源
		镜像的操作：
			1、docker commit -m=修改信息  -a=作者 容器ID 容器名:TAG
			
五、容器数据卷：持久化	+ 数据共享		
		1、卷就是目录或文件，存在与一个或多个容器中，由docker挂在到容器，但不属于联合文件系统。
	卷的设计目的就是数据的持久化，完全独立于容器的生存周期，因此docker不会再容器删除时删除其挂在的数据卷。
		特点：	
			1、数据卷可在容器之间共享或重用数据
			2、卷重的更改可以直接生效
			3、数据卷的更改不会包含在镜像的更新中
			4、数据卷的生命周期一直持续到没有容器使用过它为止。
	数据卷添加：
		1、直接命令添加 
			1、docker run -it -v /宿主机绝对路径目录:/容器内目录:option【ro只读】  镜像名
			2、验证 docker inspect 	
		2、dockerFile添加
			1、根目录下新建mydocker文件夹并进入
			2、可在Docker中使用volume指令来给镜像添加一个或多个数据卷
			3、file构建
				#volume test
				FROM centos
				VOLUME ["/dataVolumeContainer1","dataVolumeContainer2"]
				CMD echo "finished, ---- success"
				CMD /bin/bash
			4、build后生成镜像
				docker bulid -f /dockerfile路径 -t 镜像名 .
			5、run容器
		3、--volumes-from 数据卷共享 docker run -it --name 容器名  --volumes-from 容器名 容器镜像名 
	DockerFile解析：scratch（源镜像类似于java  Object）
		1、dockerfile是用来构建Docker镜像的构建文件，是由一系列命令和参数构成的脚本。
		2、	保留关键字
			centos 6.8 DockerFile
				FROM scratch
				MAINTAINER The CentOS Project <cloud-ops@centos.org>
				ADD c68-docker.tar.xz /
				LABEL name="CentOS Base Image" \
					vendor="CentOS" \
					license="GPLv2" \
					build-date="2016-06-02"

				# Default command
​				CMD ["/bin/bash"]						
​			

			1、FROM 基础镜像，当前镜像基于哪个镜像。
			2、MAINTAINER 作者加作者的邮箱
			3、RUN 容器运行的时候需要运行的命令
			4、EXPOSE 暴露该镜像对外端口
			5、WORKDIR 登陆以后的目录
			6、ENV 构建过程设置环境变量
			7、ADD  拷贝 + 解压缩    将其他的文件加入到镜像中
			8、COPY 拷贝
			9、VOLUMES 数据卷
			10、CMD 指定启动时需要执行的命令.(只有最后一个生效)
			11、ENTRYPOINT  多个都会生效，追加方式（不会被启动容器时的指令覆盖）
			12、ONBUILD 当构件一个被继承的DOCKERFILE时运行命令	
		3、docker history 镜像ID   查看容器历史

自定义tomcat:
	1、创建tomcat文件夹
	2、建立 c.txt文件（只是为了演示copy关键字）
	

	3、将jdk和tomcat安装的压缩包拷贝进上一部目录	
	4、建立Dockerfile文件
		FORM centos
		MAINTAINER   HHF<ctt9342@gmail.com>
		COPY x.txt /usr/local/c.txt
		ADD jdk.tar.gz /usr/local/
		ADD tomcat.tar.gz /usr/local/
		RUN yum -y install vim
		ENV MAINPATH /usr/local/
		WORKDIR $MYPATH
		ENV JAVA_HOME /usr/local/jdk
		ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
		ENV CATALINA_HOME /usr/local/tomcat
		ENV CATALINA_BASE /usr/local/tomcat
		ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
		EXPOSE 8080
		#ENTRYPOINT ["/usr/local/tomcat/bin/startup.sh"]
		#CMD ["/usr/local/tomcat/bin/catalina.sh","run"]
		CMD /usr/local/tomcat/bin/startup.sh && tail -F /usr/local/tocmat/bin/logs/catalina.out 
	5、构建过程设置环境变量
		docker bulid -f /dockerfile路径 -t 镜像名
	6、run 启动
	7、验证
	8、发布服务

# 数据卷

--privileged=true	解决docker启动数据卷权限问题

```
查看所有数据卷
docker volume ls
查看数据卷详情
docker volume inspect vuid
删除数据卷
docker volume rm vuid
docker volume prune

引用
--volume-from
1、创建数据卷容器
docker run -itd -v /usr/share/nginx/html --name mydata ubuntu
2、引用容器数据卷
docker run -itd --volume-from mydata -p 80:80 --name nginx1 nginx
docker run -itd --volume-from mydata -p 90:80 --name nginx2 nginx
nginx1和nginx2都挂载了同一个数据卷到/usr/share/nginx/html目录下，三个容器中任意修改了该目录下的文件，其他两个都能看到变化。
docker inspect 查看三个数据卷描述都是一致的。
```

# 备份

![img](E:\learn\git\repository\笔记\java-note\运维\docker\img\1814649-20190923175408282-216079499.png)



要求：备份nfs数据卷里的data目录里的test-nfs.txt文件到宿主机/data/backup目录下

**语法：docker run --volumes -from 【数据卷名称】 -v /【宿主机路径】/:/【容器路径】 【镜像】**

说明：首先我们需要使用nfs数据卷新开一个容器并挂载，同时我们还需要把本地的/data/backup目录挂载到该容器的/backup下，这样在容器中/backup目录里面新建的文件，我们就可以直接在/data/backup/目录中看到了。 然后再把/data/目录下面的文件打包到成data.tar文件放到/backup目录下面

 

1：查看nfs数据卷容器是否启动

[root@host1 ~]# docker ps

CONTAINER ID IMAGE COMMAND CREATED STATUS PORTS NAMES

45c637895e7a centos "bash" 2 days ago Up 2 days nfs

 

2：执行备份nfs数据卷的data目录

[root@host1 ~]# docker run --volumes-from nfs -v /data/backup/:/backup centos tar cvf /backup/data.tar data

data/

data/test-nfs.txt

 

3：查看是否备份成功

[root@host1 ~]# cd /data/backup/

[root@host1 backup]# tar xvf data.tar

data/

data/test-nfs.txt

[root@host1 backup]# ls

data data.tar

[root@host1 backup]# cd data

[root@host1 data]# ls

test-nfs.txt

 

二、恢复数据

说明：先新建一个nfs2数据卷容器，再建一个新的容器并挂载nfs2数据卷容器，然后再把tar包解包，完成恢复数据操作！

1： 新建nfs2数据卷容器

[root@host1 data]# docker run -itd -v /data/ --name nfs2 centos bash

7b09a70be336b60126c3dbc747a9689ce1bb224ef93f5c2e257dbd19771f3c18

[root@host1 data]# docker ps

CONTAINER ID IMAGE COMMAND CREATED STATUS PORTS NAMES

7b09a70be336 centos "bash" 4 seconds ago Up 3 seconds nfs2

45c637895e7a centos "bash" 2 days ago Up 2 days nfs

 

2：恢复数据到nfs2容器

[root@host1 data]# docker run --volumes-from nfs2 -v /data/backup/:/backup centos tar xf /backup/data.tar

3：查看是否恢复成功

[root@host1 data]# docker exec -it 7b09a7 bash

[root@7b09a70be336 ~]# ls /data/ 

test-nfs.txt

# 容器连接

```
--link
```

# 容器编排

docker-compose安装

```
1、获取权限
sudo chmod +x /usr/local/bin/docker-compose
2、创建连接
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
3、测试安装
docker-compose --version
```

[更多操作](https://docs.docker.com/compose/install/)

