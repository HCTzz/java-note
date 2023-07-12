```shell
// 删除镜像 按条件（标签为none的）
docker rmi -f $(docker images |grep "<none>" | awk "{print \$3}")

sudo docker -f rm $(sudo docker ps -a -q)
```

```
处理docker日志占用磁盘空间大的问题
第一步
vi /etc/docker/daemon.json
 
第二步
{
  "log-driver":"json-file",
  "log-opts": {"max-size":"500m", "max-file":"3"}
}
 
第三步
systemctl daemon-reload
systemctl restart docker
```

```shell
执行批量修改tag
docker images | grep 10.20.157.15:5000 | awk '{print " "$1":"$2" " }' | awk -F "/" '{print "docker tag "$1"/"$2"  10.12.82.11:5000/"$2 }' | sh

docker save 

执行批量推送镜像
docker images | grep 10.12.82.11:5000 | awk '{print " docker push "$1":"$2" " }' | sh


删除版本为空的

```
