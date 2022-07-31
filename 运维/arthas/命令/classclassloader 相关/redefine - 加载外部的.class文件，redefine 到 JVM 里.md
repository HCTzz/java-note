<center><font size="4"><b>加载外部的.class文件，redefine 到 JVM 里</b></font></center>

# redefine

提示

推荐使用 [retransform](https://arthas.aliyun.com/doc/retransform.html) 命令

[`mc-redefine`在线教程在新窗口打开](https://arthas.aliyun.com/doc/arthas-tutorials?language=cn&id=command-mc-redefine)

提示

加载外部的`.class`文件，redefine jvm 已加载的类。

参考：[Instrumentation#redefineClasses在新窗口打开](https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/Instrumentation.html#redefineClasses-java.lang.instrument.ClassDefinition...-)

### [#](https://arthas.aliyun.com/doc/redefine.html#常见问题)常见问题

提示

推荐使用 [retransform](https://arthas.aliyun.com/doc/retransform.html) 命令

- redefine 的 class 不能修改、添加、删除类的 field 和 method，包括方法参数、方法名称及返回值
- 如果 mc 失败，可以在本地开发环境编译好 class 文件，上传到目标系统，使用 redefine 热加载 class
- 目前 redefine 和 watch/trace/jad/tt 等命令冲突，以后重新实现 redefine 功能会解决此问题

注意

注意， redefine 后的原来的类不能恢复，redefine 有可能失败（比如增加了新的 field），参考 jdk 本身的文档。

提示

`reset`命令对`redefine`的类无效。如果想重置，需要`redefine`原始的字节码。

提示

`redefine`命令和`jad`/`watch`/`trace`/`monitor`/`tt`等命令会冲突。执行完`redefine`之后，如果再执行上面提到的命令，则会把`redefine`的字节码重置。 原因是 jdk 本身 redefine 和 Retransform 是不同的机制，同时使用两种机制来更新字节码，只有最后修改的会生效。

### [#](https://arthas.aliyun.com/doc/redefine.html#参数说明)参数说明

|              参数名称 | 参数说明                                   |
| --------------------: | :----------------------------------------- |
|                  [c:] | ClassLoader 的 hashcode                    |
| `[classLoaderClass:]` | 指定执行表达式的 ClassLoader 的 class name |

### [#](https://arthas.aliyun.com/doc/redefine.html#使用参考)使用参考



```bash
   redefine /tmp/Test.class
   redefine -c 327a647b /tmp/Test.class /tmp/Test\$Inner.class
   redefine --classLoaderClass sun.misc.Launcher$AppClassLoader /tmp/Test.class /tmp/Test\$Inner.class
```

### [#](https://arthas.aliyun.com/doc/redefine.html#结合-jad-mc-命令使用)结合 jad/mc 命令使用



```bash
jad --source-only com.example.demo.arthas.user.UserController > /tmp/UserController.java

mc /tmp/UserController.java -d /tmp

redefine /tmp/com/example/demo/arthas/user/UserController.class
```

- jad 命令反编译，然后可以用其它编译器，比如 vim 来修改源码
- mc 命令来内存编译修改过的代码
- 用 redefine 命令加载新的字节码

### [#](https://arthas.aliyun.com/doc/redefine.html#上传-class-文件到服务器的技巧)上传 .class 文件到服务器的技巧

使用`mc`命令来编译`jad`的反编译的代码有可能失败。可以在本地修改代码，编译好后再上传到服务器上。有的服务器不允许直接上传文件，可以使用`base64`命令来绕过。

1. 在本地先转换`.class`文件为 base64，再保存为 result.txt

   

   ```bash
   base64 < Test.class > result.txt
   ```

2. 到服务器上，新建并编辑`result.txt`，复制本地的内容，粘贴再保存

3. 把服务器上的 `result.txt`还原为`.class`

   ```text
   base64 -d < result.txt > Test.class
   ```

4. 用 md5 命令计算哈希值，校验是否一致

### [#](https://arthas.aliyun.com/doc/redefine.html#redefine-的限制)redefine 的限制

- 不允许新增加 field/method
- 正在跑的函数，没有退出不能生效，比如下面新增加的`System.out.println`，只有`run()`函数里的会生效

```java
public class MathGame {
    public static void main(String[] args) throws InterruptedException {
        MathGame game = new MathGame();
        while (true) {
            game.run();
            TimeUnit.SECONDS.sleep(1);
            // 这个不生效，因为代码一直跑在 while里
            System.out.println("in loop");
        }
    }

    public void run() throws InterruptedException {
        // 这个生效，因为run()函数每次都可以完整结束
        System.out.println("call run()");
        try {
            int number = random.nextInt();
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);

        } catch (Exception e) {
            System.out.println(String.format("illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }
    }
}
```