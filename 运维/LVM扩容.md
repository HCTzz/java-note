yum install -y lvm2

fdisk -l 查看磁盘详情

fdisk /dev/sda 分区

lsblk查看磁盘

pvcreate /dev/sda1

xfs_growfs  /dev/vgsys/lvsys 