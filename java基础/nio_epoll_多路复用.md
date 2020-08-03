# LINUX MAN
	
	1、Standard commands （标准命令）
	2、System calls （系统调用）
	3、Library functions （库函数）
	4、Special devices （设备说明）
	5、File formats （文件格式）
	6、Games and toys （游戏和娱乐）
	7、Miscellaneous （杂项）
	8、Administrative Commands （管理员命令）
	9 其他（Linux特定的）， 用来存放内核例行程序的文档。

# yum install -y man-pages 处理找不到命令手册的问题

# -------------------------------------------------------------------#


# yum -y install nmap-ncat	网络交互工具

# nc命令的作用：

	- 实现任意TCP/UDP端口的侦听，nc可以作为server以TCP或UDP方式侦听指定端口
	- 端口的扫描，nc可以作为client发起TCP或UDP连接 
	- 机器之间传输文件
	- 机器之间网络测速
 
# nc命令的安装

	- yum -y install nmap-ncat
 

# 常用参数

	-l  用于指定nc将处于侦听模式。指定该参数，则意味着nc被当作server，侦听并接受连接，而非向其它地址发起连接。
	-p  暂未用到（老版本的nc可能需要在端口号前加-p参数，下面测试环境是centos6.6，nc版本是nc-1.84，未用到-p参数）
	-s  指定发送数据的源IP地址，适用于多网卡机
	-u  指定nc使用UDP协议，默认为TCP
	-v  输出交互或出错信息，新手调试时尤为有用
	-w  超时秒数，后面跟数字   

# --------------------------------------------------------------------#

# netstat -natp 查看协议

# --------------------------------------------------------------------#

- 内核态
- 用户态 应用想访问IO数据需要调用系统方法(System call[80软中断]),硬件会发出中断信号（电信号）。

BIO ：阻塞IO

NIO ： 1、对于应用来说是new io  新的IO体系。 2、对于内核来说是 NO BLOKCING IO。

epoll : 1、对应于 select()，减少系统调用。多路复用 ：解决epoll中发送select系统调用时系统循环遍历的问题。使用硬件电信号中断。（事件驱动模型）


strace -ff -o ./xxx  java ServerSocketTest  抓取应用有没有发生系统调用

	1、-o ./xxx  日志存储位置
	
cd /proc  展示运行的应用目录	

   /proc/进程ID ： fd文件描述符，linux系统一切皆文件
   
	0 -> /dev/pts/0  标准输入IO
	1 -> /dev/pts/0  标准输出IO
	2 -> /dev/pts/0  错误输出IO
    3 -> /usr/local/java/jdk/jre/lib/rt.jar
	4 -> socket:[3023735]  IPV4
	5 -> socket:[3023737]  IPV6


系统调用号	函数名	功能简介	起始内核版本	详解链接
0	read	读文件内容	------	Linux系统调用 - read
1	write	向文件中写入内容	------	Linux系统调用 - write
2	open	打开指定的文件	------	Linux系统调用 - open
3	close	关闭指定的文件	------	Linux系统调用 - close
4	stat	获取文件状态信息	 	Linux系统调用 - 获取文件状态
5	fstat	获取文件状态信息	 	Linux系统调用 - 获取文件状态
6	lstat	获取文件状态信息，对链接文件不解引用	 	Linux系统调用 - 获取文件状态
7	poll	监听一组文件描述符上的发生的事件	 	Linux系统调用 - 文件IO复用
8	lseek	在文件中定位	 	 
9	mmap	映射虚拟内存页	 	 
10	mprotect	控制虚拟内存权限	 	 
11	munmap	删除虚拟内存映射	 	 
12	brk	调整堆空间范围	 	 
13	sigaction	设置信号的处理函数	 	 
14	sigprocmask	检查并修改阻塞的信号	 	 
15	sigreturn	从信号处理函数中返回并清空栈帧	 	 
16	ioctl	输入输出控制	 	 
17	pread64	对大文件随机读	 	 
18	pwrite64	对大文件随机写	 	 
19	readv	从文件中读取内容并分散到指定的多个缓冲区	 	 
20	writev	从指定的多个缓冲区中获取数据并集中写入到文件	 	 
21	access	检查文件的访问权限	 	 
22	pipe	创建管道	 	 
23	select	多路同步IO轮询	 	 
24	sched_yield	进程主动放弃处理器，并把自己放到调度队列的队尾	 	 
25	mremap	重新映射虚拟内存页	 	 
26	msync	将映射内存中的内容刷新到磁盘	 	 
27	mincore	测试指定的内存页是否在物理内存中	 	 
28	madvise	为内存使用提供建议	 	 
29	shmget	获取共享内存	 	 
30	shmat	连接共享内存	 	 
31	shmctl	共享内存属性控制	 	 
32	dup	复制一个已经打开的文件描述符	 	 
33	dup2	复制一个已经打开的文件描述符	 	 
34	pause	将当前进程挂起，等待信号唤醒	 	 
35	nanosleep	精确的进程睡眠控制	 	 
36	getitimer	获取定时器值	 	 
37	alarm	设置进程的定时提醒	 	 
38	setitimer	设置定时器的值	 	 
39	getpid	获取当前进程的进程ID	 	 
40	sendfile	在文件或端口建传输数据	 	 
41	socket	创建一个套接字	 	 
42	connect	连接远程主机	 	 
43	accept	接受socket上的连接请求	 	 
44	sendto	发送UDP消息	 	 
45	recvfrom	接收UDP消息	 	 
46	sendmsg	发送消息	 	 
47	recvmsg	接收消息	 	 
48	shutdown	关闭Socket上的连接	 	Linux系统调用 - shutdown
49	bind	绑定socket	 	 
50	listen	在指定套接字上监听网络事件	 	 
51	getsockname	获取本地套接字的名字	 	 
52	getpeername	获取通信的对端套接字的名字	 	 
53	socketpair	创建一对已连接的无名socket	 	 
54	setsockopt	设置socket的各种属性	 	 
55	getsockopt	读取socket的各种属性	 	 
56	clone	创建线程或进程的底层支持接口	 	 
57	fork	创建子进程	 	 
58	vfork	创建子进程，比fork更加高效，但是有局限	 	 
59	execve	在当前进程中运行指定的程序	 	 
60	exit	退出当前进程	 	 
61	wait4	等待子进程终止，并可获取子进程资源使用数据	 	 
62	kill	给指定的进程发送信号	 	 
63	uname	获取系统名称、版本、主机等信息	 	 
64	semget	获取一组信号量	 	 
65	semop	操作指定的信号量	 	 
66	semctl	信号量属性控制	 	 
67	shmdt	卸载共享内存	 	 
68	msgget	获取消息队列	 	 
69	msgsnd	向消息队列发送消息	 	 
70	msgrcv	从消息队列中读取消息	 	 
71	msgctl	控制消息队列	 	 
72	fcntl	文件描述符属性控制	 	 
73	flock	文件加锁、解锁	 	 
74	fsync	将所有文件内容和文件元数据修改都同步到磁盘	 	 
75	fdatasync	将文件内容和重要的元数据修改同步到磁盘	 	 
76	truncate	截断文件	 	 
77	ftruncate	对文件执行截断	 	 
78	getdents	读取目录项	 	 
79	getcwd	获取当前工作目录	 	 
80	chdir	改变当前工作目录	 	 
81	fchdir	改变当前工作目录	 	 
82	rename	重命名指定的文件	 	 
83	mkdir	创建目录	 	 
84	rmdir	删除目录	 	 
85	creat	创建新文件	 	 
86	link	创建文件链接	 	 
87	unlink	删除文件链接	 	 
88	symlink	创建符号链接	 	 
89	readlink	读取符号链接的内容	 	 
90	chmod	修改文件权限	 	 
91	fchmod	修改文件权限，参数为已经打开的文件描述符	 	 
92	chown	修改文件所有者	 	 
93	fchown	修改文件所有者	 	 
94	lchown	修改链接文件的所有者，不解引用	 	 
95	umask	设置文件权限掩码	 	 
96	gettimeofday	获取当前系统时间	 	 
97	getrlimit	获取当前系统限制	 	 
98	getrusage	获取当前资源使用数据	 	 
99	sysinfo	获取系统信息	 	 
100	times	获取进程运行时间	 	 
101	ptrace	非常强大的进程跟踪系统调用	 	 
102	getuid	获取当前用户标识号	 	 
103	syslog	读取并清空内核消息环形缓存	 	 
104	getgid	获取组标识号	 	 
105	setuid	设置用户标识号	 	 
106	setgid	设置组标识号	 	 
107	geteuid	获取有效用户标识号	 	 
108	getegid	获取有效的组标识号	 	 
109	setpgid	设置指定进程组标识号	 	 
110	getppid	获取父进程的进程ID	 	 
111	getpgrp	获取指定进程组标识号	 	 
112	setsid	设置临时权限用户ID	 	 
113	setreuid	设置真实和有效的用户标识号	 	 
114	setregid	设置真实和有效的组标识号	 	 
115	getgroups	获取当前进程的附属组ID列表	 	 
116	setgroups	设置当前进程的附属组ID列表	 	 
117	setresuid	设置进程的真实用户ID、有效用户ID和特权用户ID	 	 
118	getresuid	获取进程的真实用户ID、有效用户ID和特权用户ID	 	 
119	setresgid	设置进程的真实组ID，有效组ID和特权组ID	 	 
120	getresgid	获取进程的真实组ID，有效组ID和特权组ID	 	 
121	getpgid	获取进程组ID	 	 
122	setfsuid	设置进程组ID	 	 
123	setfsgid	设置文件系统检查时使用的组ID	 	 
124	getsid	 获取特权用户ID	 	 
125	capget	获取进程权限	 	 
126	capset	设置进程权限	 	 
127	sigpending	检查挂起的信号	 	 
128	sigtimedwait	同步地等待排队的信号	 	 
129	sigqueueinfo	 	 	 
130	sigsuspend	挂起进程来等待一个信号	 	 
131	sigaltstack	定义或获取进程的信号栈	 	 
132	utime	修改文件的访问时间或修改时间	 	 
133	mknod	创建文件系统节点	 	 
134	uselib	加载要使用的动态链接库	 	 
135	personality	设置进程的运行域	 	 
136	ustat	获取文件系统信息	 	 
137	statfs	获取文件系统信息	 	 
138	fstatfs	获取文件系统信息	 	 
139	sysfs	获取系统支持的文件系统类型	 	 
140	getpriority	获取进程运行优先级	 	 
141	setpriority	设置进程运行优先级	 	 
142	sched_setparam	设置进程的调度参数	 	 
143	sched_getparam	获取进程的调度参数	 	 
144	sched_setscheduler	设置进程的调度策略和参数	 	 
145	sched_getscheduler	获取进程的调度策略和参数	 	 
146	sched_get_priority_max	获取进程静态优先级上限	 	 
147	sched_get_priority_min	获取进程静态优先级下限	 	 
148	sched_rr_get_interval	取得按RR算法调度的实时进程的时间片长度	 	 
149	mlock	为内存页面加锁	 	 
150	munlock	为内存页面解锁	 	 
151	mlockall	当前进程的所有内存页面加锁	 	 
152	munlockall	当前进程的所有内存页面解锁	 	 
153	vhangup	挂起当前终端	 	 
154	modify_ldt	读写进程的本地描述表	 	 
155	pivot_root	修改当前进程的根文件目录	 	 
156	_sysctl	读/写系统参数	 	 
157	prctl	进程特殊控制	 	 
158	arch_prctl	设置架构相关的线程状态	 	 
159	adjtimex	调整系统时钟	 	 
160	setrlimit	设置系统资源限制	 	 
161	chroot	修改根目录	 	 
162	sync	将内存缓冲区数据写回磁盘	 	 
163	acct	启用或关闭进程记账	 	 
164	settimeofday	设置当前系统时间和时区	 	 
165	mount	挂载文件系统	 	 
166	umount2	卸载文件系统	 	 
167	swapon	开启交换文件和设备	 	 
168	swapoff	关闭交换文件和设备	 	 
169	reboot	重启系统	 	 
170	sethostname	设置主机名称	 	 
171	setdomainname	设置主机域名	 	 
172	iopl	改变进程IO权限级别	 	 
173	ioperm	设置端口IO权限	 	 
174	create_module	创建可装载的模块	 	 
175	init_module	初始化模块	 	 
176	delete_module	删除可装载的模块	 	 
177	get_kernel_syms	获取核心符号（已经被query_module取代）	 	 
178	query_module	查询模块信息	 	 
179	quotactl	控制磁盘配额	 	 
180	nfsservctl	控制NFS守护进程	 	 
181	getpmsg	未实现的系统调用	 	 
182	putpmsg	未实现的系统调用	 	 
183	afs_syscall	未实现的系统调用	 	 
184	tuxcall	未实现的系统调用	 	 
185	security	未实现的系统调用	 	 
186	gettid	获取线程ID	 	 
187	readahead	把文件预读取到页缓存内	 	 
188	setxattr	设置文件或路径的扩展属性	 	 
189	lsetxattr	设置链接文件的扩展属性	 	 
190	fsetxattr	设置文件的扩展属性	 	 
191	getxattr	获取文件或路径的扩展属性	 	 
192	lgetxattr	获取链接文件的扩展属性	 	 
193	fgetxattr	获取文件或路径的扩展属性	 	 
194	listxattr	列出文件或路径的扩展属性	 	 
195	llistxattr	列出链接文件的扩展属性	 	 
196	flistxattr	列出文件或路径的扩展属性	 	 
197	removexattr	移除文件的扩展属性	 	 
198	lremovexattr	移除链接文件的扩展属性	 	 
199	fremovexattr	移除链接文件的扩展属性	 	 
200	tkill	给指定的线程发送信号	 	 
201	time	获取系统时间	 	 
202	futex	快速用户空间锁	 	 
203	sched_setaffinity	设置进程的CPU亲和性掩码	 	 
204	sched_getaffinity	获取进程的CPU亲和性掩码	 	 
205	set_thread_area	设置线程的本地存取区	 	 
206	io_setup	创建异步IO上下文	 	 
207	io_destroy	销毁异步IO上下文	 	 
208	io_getevents	从完成队列中获取异步IO事件	 	 
209	io_submit	提交异步IO块	 	 
210	io_cancel	取消一个未完成的同步IO操作	 	 
211	get_thread_area	获取线程本地存储区	 	 
212	lookup_dcookie	获取一个cookie的完整目录	 	 
213	epoll_create	创建epoll实例	 	 
214	epoll_ctl_old	老的epoll控制接口	 	 
215	epoll_wait_old	老的epoll监控接口	 	 
216	remap_file_pages	创建一个非线性的文件映射	 	 
217	getdents64	获取目录入口	 	 
218	set_tid_address	设置存储线程ID的内存地址	2.5.49	 
219	restart_syscall	重新启动一个被信号打断的系统调用	2.6	 
220	semtimedop	System V信号操作函数	2.4.22	 
221	fadvise64	提前声明一个文件的访问模式	2.5.60	 
222	timer_create	 	 	 
223	timer_settime	 	 	 
224	timer_gettime	 	 	 
225	timer_getoverrun	 	 	 
226	timer_delete	 	 	 
227	clock_settime	 	 	 
228	clock_gettime	 	 	 
229	clock_getres	 	 	 
230	clock_nanosleep	 	 	 
231	exit_group	 	 	 
232	epoll_wait	监听epoll上发生的事件	 	 
233	epoll_ctl	epoll控制接口	 	 
234	tgkill	 	 	 
235	utimes	修改文件的修改或访问时间	 	 
236	vserver	 	 	 
237	mbind	 	 	 
238	set_mempolicy	 	 	 
239	get_mempolicy	 	 	 
240	mq_open	 	 	 
241	mq_unlink	 	 	 
242	mq_timedsend	 	 	 
243	mq_timedreceive	 	 	 
244	mq_notify	 	 	 
245	mq_getsetattr	 	 	 
246	kexec_load	 	 	 
247	waitid	 	 	 
248	add_key	 	 	 
249	request_key	 	 	 
250	keyctl	 	 	 
251	ioprio_set	 	 	 
252	ioprio_get	 	 	 
253	inotify_init	 	 	 
254	inotify_add_watch	 	 	 
255	inotify_rm_watch	 	 	 
256	migrate_pages	 	 	 
257	openat	 	 	 
258	mkdirat	 	 	 
259	mknodat	 	 	 
260	fchownat	 	 	 
261	futimesat	 	 	 
262	newfstatat	 	 	 
263	unlinkat	 	 	 
264	renameat	 	 	 
265	linkat	 	 	 
266	symlinkat	 	 	 
267	readlinkat	 	 	 
268	fchmodat	 	 	 
269	faccessat	 	 	 
270	pselect6	 	 	 
271	ppoll	 	 	 
272	unshare	 	 	 
273	set_robust_list	 	 	 
274	get_robust_list	 	 	 
275	splice	 	 	 
276	tee	 	 	 
277	sync_file_range	 	 	 
278	vmsplice	 	 	 
279	move_pages	 	 	 
280	utimensat	 	 	 
281	epoll_pwait	 	 	 
282	signalfd	 	 	 
283	timerfd_create	 	 	 
284	eventfd	 	 	 
285	fallocate	 	 	 
286	timerfd_settime	 	 	 
287	timerfd_gettime	 	 	 
288	accept4	 	 	 
289	signalfd4	 	 	 
290	eventfd2	 	 	 
291	epoll_create1	 	 	 
292	dup3	 	 	 
293	pipe2	 	 	 
294	inotify_init1	 	 	 
295	preadv	 	 	 
296	pwritev	 	 	 
297	rt_tgsigqueueinfo	 	 	 
298	perf_event_open	 	 	 
299	recvmmsg	 	 	 
300	fanotify_init	 	 	 
301	fanotify_mark	 	 	 
302	prlimit64	 	 	 
303	name_to_handle_at	 	 	 
304	open_by_handle_at	 	 	 
305	clock_adjtime	 	 	 
306	syncfs	更新指定文件描述符的文件系统	 	 
307	sendmmsg	sendmsg的扩展，可在一次系统调用中向socket发送多块数据	 	 
308	setns	设置一个文件描述符的命名空间	 	 
309	getcpu	获取当前线程所在的处理器和节点	 	 
310	process_vm_readv	 	 	 
311	process_vm_writev	 	 	 
312	kcmp	 	 	 
313	finit_module	 	 	 
314	sched_setattr	 	 	 
315	sched_getattr	 	 	 
316	renameat2	 	 	 
317	seccomp	 	 	 
318	getrandom	 	 	 
319	memfd_create	 	 	 
320	kexec_file_load	 	 	 
323	userfaultfd	 	 	 
326	copy_file_range	 把文件的一部分内容拷贝到另一个文件 4.5	 
329	pkey_mprotect	 	 	 
330	pkey_alloc	 	 	 
331	pkey_free	 	 	 
 