java api 中常用的函数式接口

函数式接口          函数描述符          原始类型特化

Predicate<T>      T->boolean           IntPredicate,LongPredicate, DoublePredicate

Consumer<T>       T->void              IntConsumer,LongConsumer, DoubleConsumer

Function<T,R>     T->R                 IntFunction<R>,IntToDoubleFunction,IntToLongFunction,LongFunction<R>,LongToDoubleFunction,LongToIntFunction,DoubleFunction<R>,ToIntFunction<T>,ToDoubleFunction<T>,ToLongFunction<T>


Supplier<T>        ()->T               BooleanSupplier,IntSupplier, LongSupplier,DoubleSupplier

UnaryOperator<T>    T->T               IntUnaryOperator,LongUnaryOperator,DoubleUnaryOperator


BinaryOperator<T>  (T,T)->T            IntBinaryOperator,LongBinaryOperator,DoubleBinaryOperator

BiPredicate<L,R>  (L,R)->boolean

BiConsumer<T,U>    (T,U)->void         ObjIntConsumer<T>,    ObjLongConsumer<T>,ObjDoubleConsumer<T>

BiFunction<T,U,R>  (T,U)->R            ToIntBiFunction<T,U>,ToLongBiFunction<T,U>,ToDoubleBiFunction<T,U> 

一、Predicate 
java.util.function.Predicate<T>接口定义了一个名叫test的抽象方法，它接受泛型T对象，并返回一个boolean。这恰恰和你先前创建的一样，
现在就可以直接使用了。在你需要表示一个涉及类型T的布尔表达式时，就可以使用这个接口。比如，你可以定义一个接受String对象的Lambda表达式，如下所示。
@FunctionalInterface
public interface Predicate<T>{
  boolean test(T t);
}
public static <T> List<T> filter(List<T> list, Predicate<T> p) {
 List<T> results = new ArrayList<>();
 for(T s: list){
   if(p.test(s)){
    results.add(s);
   }
 }
 return results;
}
Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate); 

二、Consumer
java.util.function.Consumer<T>定义了一个名叫accept的抽象方法，它接受泛型T
的对象，没有返回（void）。你如果需要访问类型T的对象，并对其执行某些操作，就可以使用
这个接口。比如，你可以用它来创建一个forEach方法，接受一个Integers的列表，并对其中
每个元素执行操作。在下面的代码中，你就可以使用这个forEach方法，并配合Lambda来打印
列表中的所有元素。
@FunctionalInterface
public interface Consumer<T>{
 void accept(T t);
}
public static <T> void forEach(List<T> list, Consumer<T> c){ 
 for(T i: list){
  c.accept(i);
 }
}
forEach(
  Arrays.asList(1,2,3,4,5),
  (Integer i) -> System.out.println(i)
 ); 

 三、Function
 java.util.function.Function<T, R>接口定义了一个叫作apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象。
 如果你需要定义一个Lambda，将输入对象的信息映射到输出，就可以使用这个接口（比如提取苹果的重量，或把字符串映射为它的长度）。在下面的
代码中，我们向你展示如何利用它来创建一个map方法，以将一个String列表映射到包含每个String长度的Integer列表。
@FunctionalInterface
public interface Function<T, R>{
  R apply(T t);
}
public static <T, R> List<R> map(List<T> list,
 Function<T, R> f) {
 List<R> result = new ArrayList<>();
 for(T s: list){
  result.add(f.apply(s));
 }
 return result;
}

List<Integer> l = map(
   Arrays.asList("lambdas","in","action"),
   (String s) -> s.length()
); 

表3-3 Lambdas及函数式接口的例子

使用案例                          Lambda 的例子                                    对应的函数式接口
布尔表达式               (List<String> list) -> list.isEmpty()                   Predicate<List<String>>
创建对象                 () -> new Apple(10)                                     Supplier<Apple>
消费一个对象             (Apple a) ->System.out.println(a.getWeight())           Consumer<Apple>
从一个对象中选择/提取     (String s) -> s.length()                                Function<String, Integer>或ToIntFunction<String>
合并两个值               (int a, int b) -> a * b                                IntBinaryOperator
比较两个对象 (Apple a1, Apple a2) ->a1.getWeight().compareTo(a2.getWeight())     Comparator<Apple>或BiFunction<Apple, Apple, Integer>或 ToIntBiFunction<Apple, Apple> 

Collector接口
public interface Collector<T, A, R> {
 Supplier<A> supplier();
 BiConsumer<A, T> accumulator();
 Function<A, R> finisher();
 BinaryOperator<A> combiner();
 Set<Characteristics> characteristics();
}
本列表适用以下定义。
1、 T是流中要收集的项目的泛型。
2、 A是累加器的类型，累加器是在收集过程中用于累积部分结果的对象。
3、 R是收集操作得到的对象（通常但并不一定是集合）的类型。
例如，你可以实现一个ToListCollector<T>类，将Stream<T>中的所有元素收集到一个
List<T>里，它的签名如下：
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> 
