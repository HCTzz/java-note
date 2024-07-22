tcpdump：网络抓包工具

ps -ef | grep 程序名

最后一部分显示了当前运行中的进程的详细列表，有些列跟ps命令的输出类似。
? PID：进程的ID。
? USER：进程属主的名字。
? PR：进程的优先级。
? NI：进程的谦让度值。
? VIRT：进程占用的虚拟内存总量。
? RES：进程占用的物理内存总量。
? SHR：进程和其他进程共享的内存总量。
? S：进程的状态（D代表可中断的休眠状态，R代表在运行状态，S代表休眠状态，T代表
跟踪状态或停止状态，Z代表僵化状态）。
? %CPU：进程使用的CPU时间比例。
? %MEM：进程使用的内存占可用内存的比例。
72 第 4 章 更多的 bash shell 命令
? TIME+：自进程启动到目前为止的CPU时间总量。
? COMMAND：进程所对应的命令行名称，也就是启动的程序名

1 HUP 挂起
2 INT 中断
3 QUIT 结束运行
9 KILL 无条件终止
11 SEGV 段错误
15 TERM 尽可能终止
17 STOP 无条件停止运行，但不终止
18 TSTP 停止或暂停，但继续在后台运行
19 CONT 在STOP或TSTP之后恢复执行

df -h    du是用户级程序，不考虑Meta Data（系统为自身分配的一些磁盘块）.应用程序打开的文件句柄没有关闭的话，会造成df命令显示的剩余磁盘空间少。而du则不会。
		 lsof | grep /tmp/ 	输出的结果中，注意某些含有“(deleted)”字样的记录，它们中的一部分就是罪魁祸首，将它们kill掉即可
		 (如果可以重启这些进程所对应的服务的话，也有可能解决问题）。
du 
? -c：显示所有已列出文件总的大小。
? -h：按用户易读的格式输出大小，即用K替代千字节，用M替代兆字节，用G替代吉字节。
? -s：显示每个输出参数的总计

sort 
-b --ignore-leading-blanks 排序时忽略起始的空白
-C --check=quiet 不排序，如果数据无序也不要报告
-c --check 不排序，但检查输入数据是不是已排序；未排序的话，报告
-d --dictionary-order 仅考虑空白和字母，不考虑特殊字符
-f --ignore-case 默认情况下，会将大写字母排在前面；这个参数会忽略大小写
-g --general-number-sort 按通用数值来排序（跟-n不同，把值当浮点数来排序，支持科学
计数法表示的值）
-i --ignore-nonprinting 在排序时忽略不可打印字符
-k --key=POS1[,POS2] 排序从POS1位置开始；如果指定了POS2的话，到POS2位置结
束
-M --month-sort 用三字符月份名按月份排序
-m --merge 将两个已排序数据文件合并
-n --numeric-sort 按字符串数值来排序（并不转换为浮点数）
-o --output=file 将排序结果写出到指定的文件中
-R --random-sort 按随机生成的散列表的键值排序
 --random-source=FILE 指定-R参数用到的随机字节的源文件
-r --reverse 反序排序（升序变成降序）
-S --buffer-size=SIZE 指定使用的内存大小
-s --stable 禁用最后重排序比较
-T --temporary-directory=DIR 指定一个位置来存储临时工作文件
-t --field-separator=SEP 指定一个用来区分键位置的字符
-u --unique 和-c参数一起使用时，检查严格排序；不和-c参数一起用时，仅
输出第一例相似的两行
-z --zero-terminated 用NULL字符作为行尾，而不是用换行符

-k和-t参数在对按字段分隔的数据进行排序时非常有用，例如/etc/passwd文件。可以用-t
参数来指定字段分隔符，然后用-k参数来指定排序的字段。举个例子，要对前面提到的密码文件
/etc/passwd根据用户ID进行数值排序，可以这么做：
$ sort -t ':' -k 3 -n /etc/passwd
root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
adm:x:3:4:adm:/var/adm:/sbin/nologin
lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
sync:x:5:0:sync:/sbin:/bin/sync
shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown
halt:x:7:0:halt:/sbin:/sbin/halt
mail:x:8:12:mail:/var/spool/mail:/sbin/nologin
news:x:9:13:news:/etc/news:
uucp:x:10:14:uucp:/var/spool/uucp:/sbin/nologin
operator:x:11:0:operator:/root:/sbin/nologin 
games:x:12:100:games:/usr/games:/sbin/nologin
gopher:x:13:30:gopher:/var/gopher:/sbin/nologin
ftp:x:14:50:FTP User:/var/ftp:/sbin/nologin


现在数据已经按第三个字段——用户ID的数值排序。
-n参数在排序数值时非常有用，比如du命令的输出。
$ du -sh * | sort -nr
1008k mrtg-2.9.29.tar.gz
972k bldg1
888k fbs2.pdf
760k Printtest
680k rsync-2.6.6.tar.gz
660k code
516k fig1001.tiff
496k test
496k php-common-4.0.4pl1-6mdk.i586.rpm
448k MesaGLUT-6.5.1.tar.gz
400k plp
注意，-r参数将结果按降序输出，这样就更容易看到目录下的哪些文件占用空间最多。


tar   tar命令的功能

-A --concatenate 将一个已有tar归档文件追加到另一个已有tar归档文件
-c --create 创建一个新的tar归档文件
-d --diff 检查归档文件和文件系统的不同之处
 --delete 从已有tar归档文件中删除
-r --append 追加文件到已有tar归档文件末尾
-t --list 列出已有tar归档文件的内容
-u --update 将比tar归档文件中已有的同名文件新的文件追加到该tar归档文件中
-x --extract 从已有tar归档文件中提取文件

每个功能可用选项来针对tar归档文件定义一个特定行为。表4-9列出了这些选项中能和tar
命令一起使用的常见选项。
tar命令选项
-C dir 切换到指定目录
-f file 输出结果到文件或设备file
-j 将输出重定向给bzip2命令来压缩内容
-p 保留所有文件权限
-v 在处理文件时显示文件
-z 将输出重定向给gzip命令来压缩内容

tar -cvf test.tar test/ test2/
上面的命令创建了名为test.tar的归档文件，含有test和test2目录内容。接着，用下列命令：
tar -tf test.tar
列出tar文件test.tar的内容（但并不提取文件）。最后，用命令：
tar -xvf test.tar 

一. 探索后台模式
sleep命令接受一个参数，该参数是你希望进程等待（睡眠）的秒数。这个命令在脚本中常
用于引入一段时间的暂停。命令sleep 10会将会话暂停10秒钟，然后返回shell CLI提示符。

要想将命令置入后台模式，可以在命令末尾加上字符&。把sleep命令置入后台模式可以让我
们利用ps命令来小窥一番。
除了ps命令，你也可以使用jobs命令来显示后台作业信息。jobs命令可以显示出当前运行
在后台模式中的所有用户的进程（作业）。

sleep 3000&
[1] 2396
$ ps -f 

将相同的进程列表置入后台模式会在命令输出上表现出些许不同。
$ (sleep 2 ; echo $BASH_SUBSHELL ; sleep 2)&
[2] 2401
$ 1
[2]+ Done ( sleep 2; echo $BASH_SUBSHELL; sleep 2 )
$
把进程列表置入后台会产生一个作业号和进程ID，然后返回到提示符。不过奇怪的是表明单
一级子shell的数字1显示在了提示符的旁边！不要不知所措，只需要按一下回车键，就会得到另
一个提示符。
在CLI中运用子shell的创造性方法之一就是将进程列表置入后台模式。你既可以在子shell中
进行繁重的处理工作，同时也不会让子shell的I/O受制于终端。

3. 协程
协程可以同时做两件事。它在后台生成一个子shell，并在这个子shell中执行命令。
要进行协程处理，得使用coproc命令，还有要在子shell中执行的命令。
coproc sleep 10 

在上面的例子中可以看到在子shell中执行的后台命令是coproc COPROC sleep 10。COPROC
是coproc命令给进程起的名字。你可以使用命令的扩展语法自己设置这个名字。
coproc My_Job { sleep 10; } 
通过使用扩展语法，协程的名字被设置成My_Job。这里要注意的是，扩展语法写起来有点
麻烦。必须确保在第一个花括号（{）和命令名之间有一个空格。还必须保证命令以分号（;）结
尾。另外，分号和闭花括号（}）之间也得有一个空格。


理解 shell 的内建命令
1、 外部命令
外部命令，有时候也被称为文件系统命令，是存在于bash shell之外的程序。它们并不是shell
程序的一部分。外部命令程序通常位于/bin、/usr/bin、/sbin或/usr/sbin中。
ps就是一个外部命令。你可以使用which和type命令找到它。
which ps
type -a ps 

2、内建命令
内建命令和外部命令的区别在于前者不需要使用子进程来执行。它们已经和shell编译成了一
体，作为shell工具的组成部分存在。不需要借助外部程序文件来运行。
cd和exit命令都内建于bash shell。可以利用type命令来了解某个命令是否是内建的。
type cd

3、history
 history -a  可以在退出shell会话之前强制将命令历史记录写入.bash_history文件
 history -n  强制重新读取.bash_history文件，更新终端会话的历史记录
 !20  		 输入惊叹号和命令在历史列表中的编号以唤回历史列表中任意一条命令

4、全局环境变量
 printenv 系统为bash shell设置的全局环境变量数目众多，我们不得不在展示的时候进行删减。
		  其中有很多是在登录过程中设置的，另外，你的登录方式也会影响到所设置的环境变量。
		  要显示个别环境变量的值，可以使用printenv命令，但是不要用env命令。
 eg: printenv HOME 
 echo $HOME
 
5、局部环境变量
 set set命令会显示为某个特定进程设置的所有环境变量，包括局部变量、全局变量以及用户定义变量
 
 5、1 设置用户定义变量
 一旦启动了bash shell（或者执行一个shell脚本），就能创建在这个shell进程内可见的局部变
 量了。可以通过等号给环境变量赋值，值可以是数值或字符串。
 my_variable=Hello
 echo $my_variable
 非常简单！现在每次引用my_variable 环境变量的值，只要通过$my_variable引用即可。
 如果要给变量赋一个含有空格的字符串值，必须用单引号来界定字符串的首和尾。 
 
 5、2 设置全局环境变量
 my_variable="I am Global now"
 export my_variable 
 
 5、3 删除环境变量
  unset my_variable
 5、4 设置 PATH 环境变量
  echo $PATH
  
6、定位系统环境变量  
 $HOME表示的是某个用户的主目录。它和波浪号（~）的作用一样
 1. /etc/profile文件
 针对用户的启动文件 提供一个用户专属的启动文件来定义该用户所用到的环境变量
 2.$HOME/.bash_profile
? $HOME/.bashrc
? $HOME/.bash_login
? $HOME/.profile
 shell会按照按照下列顺序，运行第一个被找到的文件，余下的则被忽略：
 $HOME/.bash_profile
 $HOME/.bash_login
 $HOME/.profile
 注意，这个列表中并没有$HOME/.bashrc文件。这是因为该文件通常通过其他文件运行的。
 
6、1 交互式 shell 进程
	