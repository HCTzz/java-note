一、sed命令介绍
sed全称是：Stream EDitor（流编辑器）
Linux sed 命令是利用脚本来处理文本文件，sed 可依照脚本的指令来处理、编辑文本文件。Sed 主要用来自动编辑一个或多个文件、简化对文件的反复操作、编写转换程序等。

二、sed 的运行模式
当处理数据时，Sed 从输入源一次读入一行，并将它保存到所谓的模式空间pattern space中。所有 Sed 的变换都发生在模式空间。变换都是由命令行上或外部 Sed 脚本文件提供的单字母命令来描述的。大多数 Sed 命令都可以由一个地址或一个地址范围作为前导来限制它们的作用范围。

选项：

```
命令   功能
 a\  在当前行后添加一行或多行。多行时除最后一行外，每行末尾需用“\”续行
 c\  用此符号后的新文本替换当前行中的文本。多行时除最后一行外，每行末尾需用"\"续行
 i\  在当前行之前插入文本。多行时除最后一行外，每行末尾需用"\"续行
 d   删除行
 h   把模式空间里的内容复制到暂存缓冲区
 H   把模式空间里的内容追加到暂存缓冲区
 g   把暂存缓冲区里的内容复制到模式空间，覆盖原有的内容
 G   把暂存缓冲区的内容追加到模式空间里，追加在原有内容的后面
 l   列出非打印字符
 p   打印行
 n   读入下一输入行，并从下一条命令而不是第一条命令开始对其的处理
 q   结束或退出sed
 r   从文件中读取输入行
 !   对所选行以外的所有行应用命令
 s   用一个字符串替换另一个
 g   在行内进行全局替换
 w   将所选的行写入文件
 x   交换暂存缓冲区与模式空间的内容
 y   将字符替换为另一字符（不能对正则表达式使用y命令）
 
 常用命令参数：
 p==print 
 d：delete
 =：打印匹配行的行号
 -n 取消默认的完整输出，只要需要的  
 -e 允许多项编辑
 -i 修改文件内容
 -r 不需要转义
```

```markdown
-- 测试文件内容
hhh     pts/1        192.168.25.1     Sat Jun 30 22:04   still logged in   
reboot   system boot  2.6.32-358.el6.i Sat Jun 30 22:04 - 22:43  (00:38)    
omc     pts/0        192.168.25.1     Sat Jun 30 20:16 - down   (00:00)    
reboot   system boot  2.6.32-358.el6.i Sat Jun 30 19:38 - 20:16  (00:37)    
root     pts/1        192.168.25.1     Sat Jun 30 12:20 - down   (00:55)    
root     pts/0        192.168.25.1     Sat Jun 30 11:53 - down   (01:22)    
reboot   system boot  2.6.32-358.el6.i Sat Jun 30 11:52 - 13:15  (01:23)    
root     pts/0        192.168.25.1     Sat Jun 30 05:40 - down   (02:51)    
reboot   system boot  2.6.32-358.el6.i Sat Jun 30 05:38 - 08:32  (02:54)    
root     pts/0        192.168.25.1     Fri Jun 29 21:01 - down   (06:21)    

wtmp begins Tue Jun  9 03:57:56 2015
```

只打印第三行

```shell
sed -n '3p' test.log
```

只查看文件的第3行到第9行

```shell
sed -n '3,9p' test.log
```

过滤特定字符串,显示正行内容

```shell
sed -n '/root/p' test.log
```

显示包含"hhh"的行到包含"omc"的行之间的行

```shell
sed -n '/hhh/,/omc/p' test.log
```

打印1-5行，并显示行号

```shell
sed -n -e '1,5p' -e '=' test.log
```

仅仅显示匹配字符串的行号

```shell
ed -n '=;/root/p' test.log
```

打印最后一行

```shell
sed -n '$p' test.log
```

在文件第一行添加happy,文件结尾添加new year

```shell
sed -e '1i happy' -e '$a new year' test.log       【界面显示】
sed -i -e '1i happy' -e '$a new year' test.log    【真实写入文件】
```

在文件第一行和第四行的每行下面添加hahaha

```shell
sed -i '1,4i hahaha' test.log
```

& 符号在sed命令中代表上次匹配的结果

```shell
sed 's/world/hello_&/g' test.log
```

删除第3到第9行,只是不显示而已

```shell
sed  '3,9d' test.log
```

删除包含"hhh"的行到包含"omc"的行之间的行

```shell
sed '/hhh/,/omc/d' test.log
```

删除包含"omc"的行到第十行的内容

```shell
sed '/omc/,10d' test.log
```

显示中5到10行里匹配root，把行内所有的root替换为FTL，并打印到屏幕上

```shell
sed '5,10 s/root/FTL/g' test.log         【仅显示用】
sed -i sed '5,10 s/root/FTL/g' test.log  【-i 会真正替换信息】
```

删除空白行

```shell
sed '/^$/d' file
```

删除文件的第2行到末尾所有行

```shell
sed '2,$d' file
```

