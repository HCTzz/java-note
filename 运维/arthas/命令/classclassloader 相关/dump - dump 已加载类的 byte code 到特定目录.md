<center><font size="4"><b>dump 已加载类的 byte code 到特定目录</b></font></center>

# dump

提示

dump 已加载类的 bytecode 到特定目录

### [#](https://arthas.aliyun.com/doc/dump.html#参数说明)参数说明

|              参数名称 | 参数说明                                   |
| --------------------: | :----------------------------------------- |
|       *class-pattern* | 类名表达式匹配                             |
|                `[c:]` | 类所属 ClassLoader 的 hashcode             |
| `[classLoaderClass:]` | 指定执行表达式的 ClassLoader 的 class name |
|                `[d:]` | 设置类文件的目标目录                       |
|                   [E] | 开启正则表达式匹配，默认为通配符匹配       |

### [#](https://arthas.aliyun.com/doc/dump.html#使用参考)使用参考



```bash
$ dump java.lang.String
 HASHCODE  CLASSLOADER  LOCATION
 null                   /Users/admin/logs/arthas/classdump/java/lang/String.class
Affect(row-cnt:1) cost in 119 ms.
```



```bash
$ dump demo.*
 HASHCODE  CLASSLOADER                                    LOCATION
 3d4eac69  +-sun.misc.Launcher$AppClassLoader@3d4eac69    /Users/admin/logs/arthas/classdump/sun.misc.Launcher$AppClassLoader-3d4eac69/demo/MathGame.class
             +-sun.misc.Launcher$ExtClassLoader@66350f69
Affect(row-cnt:1) cost in 39 ms.
```



```bash
$ dump -d /tmp/output java.lang.String
 HASHCODE  CLASSLOADER  LOCATION
 null                   /tmp/output/java/lang/String.class
Affect(row-cnt:1) cost in 138 ms.
```

- 指定 classLoader

注意 hashcode 是变化的，需要先查看当前的 ClassLoader 信息，提取对应 ClassLoader 的 hashcode。

如果你使用`-c`，你需要手动输入 hashcode：`-c <hashcode>`



```bash
$ dump -c 3d4eac69 demo.*
```

对于只有唯一实例的 ClassLoader 可以通过`--classLoaderClass`指定 class name，使用起来更加方便：



```bash
$ dump --classLoaderClass sun.misc.Launcher$AppClassLoader demo.*
 HASHCODE  CLASSLOADER                                    LOCATION
 3d4eac69  +-sun.misc.Launcher$AppClassLoader@3d4eac69    /Users/admin/logs/arthas/classdump/sun.misc.Launcher$AppClassLoader-3d4eac69/demo/MathGame.class
             +-sun.misc.Launcher$ExtClassLoader@66350f69
Affect(row-cnt:1) cost in 39 ms.
```

- 注: 这里 classLoaderClass 在 java 8 是 sun.misc.Launcher$AppClassLoader，而java 11的classloader是jdk.internal.loader.ClassLoaders$AppClassLoader，katacoda 目前环境是 java8。

`--classLoaderClass` 的值是 ClassLoader 的类名，只有匹配到唯一的 ClassLoader 实例时才能工作，目的是方便输入通用命令，而`-c <hashcode>`是动态变化的。