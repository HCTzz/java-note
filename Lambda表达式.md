一、可以把Lambda 表达式理解为简洁地表示可传递的匿名函数的一种方式：它没有名称，但它有参数列表、函数主体、返回类型，可能还有一个可以抛出的异常列表。

匿名 -- 我们说匿名，是因为它不像普通的方法那样有一个明确的名称：写得少而想得多！
函数 -- 我们说塔是函数，是因为Lambda 函数不像方法那样属于某个特定的类。但和方法一样Lambda有参数列表、函数主体、返回类型。
传递 -- Lambda表达式可以作为参数传递给方法或存储在变量中。
简介 -- 无需像匿名类那样写很多模版代码。

二、在哪里以及如何使用Lamdba

1、函数式接口 -- 函数式接口就是只定义一个抽象方法的接口
eg:

  public interface Comparator<T> {
   int compare(T o1, T o2);
  }
  public interface Runnable{
   void run();
  }
  public interface ActionListener extends EventListener{
   void actionPerformed(ActionEvent e);
  }
  public interface Callable<V>{
   V call();
  }
  public interface PrivilegedAction<V>{
   V run();
  } 

用函数式接口可以干什么呢？Lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例（具体说来，是函数式接口一个具体实现
的实例）。你用匿名内部类也可以完成同样的事情，只不过比较笨拙：需要提供一个实现，然后再直接内联将它实例化。

1.1  函数描述符
函数式接口的抽象方法的签名基本上就是Lambda表达式的签名。我们将这种抽象方法叫作
函数描述符。

2、 把 Lambda 付诸实践：环绕执行模式
public static String processFile() throws IOException {
   try (BufferedReader br =
    new BufferedReader(new FileReader("data.txt"))) {
   return br.readLine();
   }
} 

2.1 第 1 步：记得行为参数化
现在这段代码是有局限的。你只能读文件的第一行。如果你想要返回头两行，甚至是返回使
用最频繁的词，该怎么办呢？在理想的情况下，你要重用执行设置和清理的代码，并告诉
processFile方法对文件执行不同的操作。这听起来是不是很耳熟？是的，你需要把
processFile的行为参数化。你需要一种方法把行为传递给processFile，以便它可以利用
BufferedReader执行不同的行为。
传递行为正是Lambda的拿手好戏。那要是想一次读两行，这个新的processFile方法看起
来又该是什么样的呢？基本上，你需要一个接收BufferedReader并返回String的Lambda。例
如，下面就是从BufferedReader中打印两行的写法：
String result = processFile((BufferedReader br) ->
 br.readLine() + br.readLine());
这就是做有用工
作的那行代码

2.2 第 2 步：使用函数式接口来传递行为
我们前面解释过了，Lambda仅可用于上下文是函数式接口的情况。你需要创建一个能匹配
BufferedReader -> String，还可以抛出IOException异常的接口。让我们把这一接口叫作
BufferedReaderProcessor吧。
@FunctionalInterface
public interface BufferedReaderProcessor {
 String process(BufferedReader b) throws IOException;
}
现在你就可以把这个接口作为新的processFile方法的参数了：
public static String processFile(BufferedReaderProcessor p) throws
 IOException {
 …
}
2.3 第 3 步：执行一个行为
任何BufferedReader -> String形式的Lambda都可以作为参数来传递，因为它们符合
BufferedReaderProcessor接口中定义的process方法的签名。现在你只需要一种方法在
processFile主体内执行Lambda所代表的代码。请记住，Lambda表达式允许你直接内联，为
函数式接口的抽象方法提供实现，并且将整个表达式作为函数式接口的一个实例。因此，你可
以在processFile主体内，对得到的BufferedReaderProcessor对象调用process方法执行
处理：
public static String processFile(BufferedReaderProcessor p) throws
 IOException {
 try (BufferedReader br =
 new BufferedReader(new FileReader("data.txt"))) {
 return p.process(br);
 }
}
2.4 第 4 步：传递 Lambda 
现在你就可以通过传递不同的Lambda重用processFile方法，并以不同的方式处理文件了。
处理一行：
String oneLine =
 processFile((BufferedReader br) -> br.readLine());
处理两行：
String twoLines =
 processFile((BufferedReader br) -> br.readLine() + br.readLine()); 




