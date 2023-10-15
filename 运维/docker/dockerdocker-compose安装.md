### 安装docker



### 安装docker-compose

运行以下命令以下载 Docker Compose 的当前稳定版本：

```shell
sudo curl -L "https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
```

将可执行权限应用于二进制文件

```shell
sudo chmod +x /usr/local/bin/docker-compose
```

4 创建软链

```shell
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

5 测试是否安装成功

```shell
docker-compose --version
```

