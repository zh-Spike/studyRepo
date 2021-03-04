## Stream 流 

不会真有人不会把？

写点在算法中常用的 List 和 array 转化的东西

### `List<Integer> to int[] `

```java
int[] array = list.stream().mapToInt(i->i).toArray();
//OR
int[] array = list.stream().mapToInt(Integer::intValue).toArray();
```


Thought process:

- simple `Stream#toArray` returns `Object[]`, so it is not what we want. Also `Stream#toArray(IntFunction<A[]> generator)` doesn't do what we want because generic type A can't represent primitive `int`

- so it would be nice to have some stream which could handle primitive type `int` instead of wrapper `Integer`, because its toArray method will most likely also return `int[]` array (returning something else like `Object[]` or even boxed `Integer[]` would be unnatural here). And fortunately Java 8 has such stream which is `IntStream`

- so now only thing we need to figure out is how to convert our `Stream<Integer>` (which will be returned from `list.stream()`) to that shiny `IntStream`. Here `Stream#mapToInt(ToIntFunction<? super T> mapper)` method comes to a rescue. All we need to do is pass to it mapping from `Integer` to `int`. We could use something like `Integer#intValue` which returns int like :

- 就是 List 转成 `Steam<Integer>` 再把 `Stream<Integer>` 里的 `Integer` 映射成 `int` 得到 `SteamamInt` 那 `.toArray()` 得到了


    ```java
        mapToInt( (Integer i) -> i.intValue() )  
        // or if someone prefers 
        mapToInt(Integer::intValue) 
    ```
but similar code can be generated using unboxing, since compiler knows that result of this lambda must be int (lambda in mapToInt is implementation of ToIntFunction interface which expects body for int applyAsInt(T value) method which is expected to return int).

So we can simply write
```java
    mapToInt((Integer i)->i)
```
Also since Integer type in (Integer i) can be inferred by compiler because List<Integer>#stream() returns Stream<Integer> we can also skip it which leaves us with

```java
    mapToInt(i -> i)
```

### `List<String> 2 String[]`

```java
public static void main(String[] args) {
     //1. 通过 toArray()
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        list.add("value" + i);
    }
    String[] arrays1 = list.toArray(new String[0]);
    //2. jdk1.8 stream
    String[] arrays2 = list.stream().toArray(String[]::new);
}
```
### `int 2 List<Integer>`

注意 boxed() 把 int 打包 Integer 再 SteamInteger 收集

```java 
int[] ints = {1,2,3};
List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
```

### `Array 2 List<String>`

如果我们希望把Stream的元素保存到集合，例如List，因为List的元素是确定的Java对象，因此，把Stream变为List不是一个转换操作，而是一个聚合操作，它会强制Stream输出每个元素。

把Stream的每个元素收集到List的方法是调用collect()并传入Collectors.toList()对象，它实际上是一个Collector实例，通过类似reduce()的操作，把每个元素添加到一个收集器中（实际上是ArrayList）。

类似的，collect(Collectors.toSet())可以把Stream的每个元素收集到Set中。

```java
public static void main(String[] args) {
    /*
     * 此种方法生成的List不可进行add和remove操作
     * 因为它是一个定长的List集合，跟数组长度一致
     */
    String[] array = new String[]{"value1", "value2", "value3"};
    // 底层还是没变 还是个 array
    List<String> stringList1 = Arrays.asList(array);
    //2.通过 Collections.addAll(list, arrays);
    Collections.addAll(stringList2, array);
    //3. jdk1.8 通过 Stream
    List<String> listStrings = Stream
            .of(arrays)
            .collect(Collectors.toList());
}
```

### `List<List<Integer>> 2 int[][]`

二维转换

```java
    List<List<Integer>> nums = new ArrayList<>();
    // 转 maxtrix
    int[][] matrix = nums.stream()
        .map(u -> u.stream().mapToInt(i->i).toArray())
        .toArray(int[][]::new);
```

### `List<List<String>> 2 String[][]`

同上

```java
    List<List<String>> mainList = new ArrayList<>();

    String[][] stringArray = mainList.stream()
        .map(u -> u.toArray(new String[0]))
        .toArray(String[][]::new);
```

### 输出为Map
如果我们要把Stream的元素收集到Map中，就稍微麻烦一点。因为对于每个元素，添加到Map时需要key和value，因此，我们要指定两个映射函数，分别把元素映射为key和value：

```java
public class Main {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("APPL:Apple", "MSFT:Microsoft");
        Map<String, String> map = stream
                .collect(Collectors.toMap(
                        // 把元素s映射为key:
                        s -> s.substring(0, s.indexOf(':')),
                        // 把元素s映射为value:
                        s -> s.substring(s.indexOf(':') + 1)));
        System.out.println(map);
    }
}
```

#### 分组输出
```java
public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        Map<String, List<String>> groups = list.stream()
                .collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
        System.out.println(groups);
    }
}
```

分组输出使用`Collectors.groupingBy()`，它需要提供两个函数：

一个是分组的key，这里使用`s -> s.substring(0, 1)`，表示只要首字母相同的String分到一组

第二个是分组的value，这里直接使用`Collectors.toList()`，表示输出为List，上述代码运行结果如下：

```java
{
    A=[Apple, Avocado, Apricots],
    B=[Banana, Blackberry],
    C=[Coconut, Cherry]
}
```
