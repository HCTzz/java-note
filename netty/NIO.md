1、零拷贝（没有CPU拷贝）：
	传统情况：4次拷贝，3次切换。
	DMA（不可避免的一次操作）：直接内存拷贝，不使用CPU。
	mmap（适合小数据量读写）：内存映射：3次拷贝，3次上下文切换
	sendFile（linux内核提供，设和大文件传输）：存在一次CPU拷贝（拷贝一些描述信息消耗低，可以忽略不计），2次拷贝 2次上下文切换。
	
2、reactor模型:
	1、反应器模式
	2、分发者模式
	3、通知者模式
	4、基本设计思想
		1、通过一个或多个输入同时传递给服务器的模式（基于事件驱动）
		2、服务端程序处理传入的多个请求并将它们同步分派到相应的处理线程。
		4、IO复用监听事件
		
