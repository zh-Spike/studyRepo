# 一. 集合 Collection
## 1. 数组 Array

### 1.1 新建数组
```java
int[] tmp = new int[5];
```

### 1.2 数组长度
```java
tmp.length
```

### 1.3 数组排序
```java
// 基础类型值 直接排 不然不用比较器它排的内存地址
// 升序
Arrays.sort(tmp);
// 对index从    fromIndex 1 -> toIndex（5-1）= 4
Arrays.sort(tmp,1,5);
```

自定义排序 
```java
Arrays.sort(b,(o1,o2)->(o1.val - o2.val))

int[][] b = new int[4][2];
Arrays.sort(b, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
Arrays.sort(b, new Comparator<int[]>(){
    @Override
    public int compare(int[] o1, int[] o2){
        return o1[0]==o2[0] ? o1[1]-o2[1] : o2[0]-o1[0];
    }
})
```

##### 比较器的一点思考

```java
class MyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        //return o1 - o2; //语句一
        return o2 - o1; //语句二
    }
}
```

首先要知道，o1 是需要比较的数，o2 是一个标准，比较过程是

```java
o1 - o2 
	结果为负，则 o1 排到 o2 的前面，此时说明 o1 的数值小于 o2
	结果为正，则 o1 排到 o2 的后面，此时说明 o1 的数值大于 o2
	结果为0，则 o1 和 o2 并排，此时说明 o1 的数值等于 o2
这种排序方式是从小到大升序排列

o2 - o1 
	结果为负，则 o1 排到 o2 的前面，此时说明 o1 的数值大于 o2 
    ···

这种排序方式是从大到小降序排列

用来自己搞大小根堆的时候用用一下比较器 一般用λ表达式方便

left = new PriorityQueue<>((n1,n2)->n2-n1)

```

因为单纯记住结论并不能很好的理解，所以通过这种记录的方式能够更好的理解比较器的比较方式。

还有在比较val的时候是不能用 o1.val o2.val 的

### 1.4 数组复制

1. 使用 CopyOfRange() 方法对数组进行复制

    Arrays 类的 CopyOfRange() 方法是另一种复制数组的方法，其语法形式如下：
    ```java
    Arrays.copyOfRange(dataType[] srcArray,int startIndex,int endIndex)
    ```
    其中：

    srcArray 表示原数组。

    startIndex 表示开始复制的起始索引，目标数组中将包含起始索引对应的元素，另外，startIndex 必须在 0 到 srcArray.length 之间。

    endIndex 表示终止索引，目标数组中将不包含终止索引对应的元素，endIndex 必须大于等于 startIndex，可以大于 srcArray.length，如果大于 srcArray.length，则目标数组中使用默认值填充。

    注意：目标数组如果已经存在，将会被重构。
    ```java
    Arrays.copyOfRange(array,0,array.length);//左闭右开
    ```

2. 使用 arraycopy() 方法

    arraycopy() 方法位于 java.lang.System 类中，其语法形式如下：

    System.arraycopy(dataType[] srcArray,int srcIndex,int destArray,int destIndex,int length)

    其中，srcArray 表示原数组；srcIndex 表示原数组中的起始索引；destArray 表示目标数组；destIndex 表示目标数组中的起始索引；length 表示要复制的数组长度。

    使用此方法复制数组时，length+srcIndex 必须小于等于 srcArray.length，同时 length+destIndex 必须小于等于 destArray.length。

    注意：目标数组必须已经存在，且不会被重构，相当于替换目标数组中的部分元素。

    ```java
    int[] heightsP = new int[heights.length + 2];
    // 加两个哨兵
    System.arraycopy(heights, 0, heightsP, 1, heights.length);
    ```

    
    注意：在使用 arraycopy() 方法时要注意，此方法的命名违背了 Java 的命名惯例。即第二个单词 copy 的首字母没有大写，但按惯例写法应该为 arrayCopy。

3. 使用 clone() 方法
    clone() 方法也可以实现复制数组。该方法是类 Object 中的方法，可以创建一个有单独内存空间的对象。因为数组也是一个 Object 类，因此也可以使用数组对象的 clone() 方法来复制数组。

    clone() 方法的返回值是 Object 类型，要使用强制类型转换为适当的类型。其语法形式比较简单：
    ```java
    array_name.clone()
    ```
    ```java
    int[] targetArray=(int[])sourceArray.clone();
    ```
    注意：目标数组如果已经存在，将会被重构。


注意：以上几种方法都是浅拷贝（浅复制）。浅拷贝只是复制了对象的引用地址，两个对象指向同一个内存地址，所以修改其中任意的值，另一个值都会随之变化。深拷贝是将对象及值复制过来，两个对象修改其中任意的值另一个值不会改变。

## 2. List

### 2.1 新建List
```java
List<Integer> num1 = new ArrayList<>();

List<Integer> num2 = new ArrayList<Integer>();

var num3 = new ArrayList<Integer>();
```

### 2.2 添加元素
```java
// 从尾部 添加 2
nums.add(2);
// 从 index = 0 处 添加 1
nums.add(0,1);
```

### 2.3 获取元素
```java
// index
int n = nums.get(0);
```

### 2.4 删除元素
```java
// index
nums.remove(0);
```

### 2.5 判空
```java
boolean flag = nums.isEmpty();

// 一般都是用来判非空
while(!stack.isEmpty()){
    .....   
}
```

### 2.6 是否包含元素
```java
nums.contains(2);
```

### 2.7 列表长度
```java
nums.size()
```

### 2.8 清空列表
```java
nums.clear();
```

### 2.9 reverse
```java
List<Integer> nums = new ArrayList<>();
Collection.reverse(nums);
```

### 2.10 List2Array

for 循环
```java
List<String> strList = new ArrayList<String>();
strList.add("list");
strList.add("to");
strList.add("array");

//初始化需要得到的数组
String[] array = new String[testList.size()];

//使用for循环得到数组
for(int i = 0; i < testList.size();i++){
    array[i] = testList.get(i);
}

//打印数组
for(int i = 0; i < array.length; i++){
    System.out.println(array[i]);
}
```

List 转 Array 数组

使用集合转数组的方法，必须使用集合的 toArray(T[] array)，传入的是类型完全一样的数组，大小就是 list.size()。

反例：直接使用 toArray 无参方法存在问题，此方法返回值只能是 Object[] 类，若强转其它类型数组将出现 ClassCastException 错误。

正例：

```java
List<String> strList = new ArrayList<String>();
strList.add("list");
strList.add("to");
strList.add("array");

String[] strArray = new String[strList.size()];
strList.toArray(strArray);//括号里的数组，为想要的结果数组的形式

String[]array2 = strList.toArray(new String[strList.size()]);
```

说明：使用 toArray 带参方法，入参分配的数组空间不够大时，toArray 方法内部将重新分配内存空间，并返回新数组地址；如果数组元素大于实际所需，下标为 [ list.size() ] 的数组元素将被置为 null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集合元素个数一致。


### 2.11 Array2List

使用工具类 Arrays.asList() 把数组转换成集合时，不能使用其修改集合相关的方法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常。

说明：asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组。

```java
String[] strArray = new String[]{"array", "to", "list"};
List<String> strList = Arrays.asList(strArray);
```

第一种情况：list.add("c"); 运行时异常。

第二种情况：str[0]= "gujin"; 那么 list.get(0)也会随之修改。

### 2.12 List合并

```java
list1.addAll(list2);
```

### 2.13 List 排序

In Java 8, Collections.sort(list, c) changed implementation:

JDK 1.8后期版本增加了 list.sort() using the specified list and comparator.

早期 Collections.sort() uses the list iterator's set() to modify the list.

```java
List<Integer> list1 = new ArrayList<>();
list1.add(11111);
list1.add(23);
// 从大到小
list1.sort((o1, o2) -> o2 - o1);
// sort 里面要放比较器的
default void sort(Comparator<? super E> c) {
    Object[] a = this.toArray();
    Arrays.sort(a, (Comparator) c);
    ListIterator<E> i = this.listIterator();
    for (Object e : a) {
        i.next();
        i.set((E) e);
    }
}


Collections.sort(list);//从小到大

Collections.sort(list,Collections.reverseOrder());//从大到小

//自定义
Collections.sort(list,new Comparator<Point>(){
    public int compare(Point a,Point b){
        return a.get(x)==b.get(x)?a.get(y)-b.get(y):a.get(x)-b.get(x);
    }
});
```

## 3 Set

`Set和List一样，也是继承Collection的接口，但Set是不包含重复元素的集合`

3种实现类 HashSet LinkedHashSet TreeSet

`Set基本上都是以Map为基础实现的，例如两个主要集合HashSet以HashMap为基础实现，是无序的；而TreeSet以TreeMap为基础实现，是有序的。`
```
一.HashSet 特点：

1.HashSet中不能有相同的元素，可以有一个Null元素，存入的元素是无序的。

2.HashSet如何保证唯一性？

1).HashSet底层数据结构是哈希表，哈希表就是存储唯一系列的表，

而哈希值是由对象的hashCode()方法生成。

2).确保唯一性的两个方法：hashCode()和equals()方法。

3.添加、删除操作时间复杂度都是O(1)。

4.非线程安全

二.LinkedHashSet 特点：

1.LinkedHashSet中不能有相同元素，可以有一个Null元素，元素严格按照放入的顺序排列。

2.LinkedHashSet如何保证有序和唯一性？

1).底层数据结构由哈希表和链表组成。

2).链表保证了元素的有序即存储和取出一致，哈希表保证了元素的唯一性。

3.添加、删除操作时间复杂度都是O(1)。

4.非线程安全

三.TreeSet 特点：

1.TreeSet是中不能有相同元素，不可以有Null元素，根据元素的自然顺序进行排序。

2.TreeSet如何保证元素的排序和唯一性？

底层的数据结构是红黑树(一种自平衡二叉查找树)

3.添加、删除操作时间复杂度都是O(log(n))

4.非线程安全

四.总结：
通过以上特点可以分析出，三者都保证了元素的唯一性，如果无排序要求可以选用HashSet；如果想取出元素的顺序和放入元素的顺序相同，那么可以选用LinkedHashSet。如果想插入、删除立即排序或者按照一定规则排序可以选用TreeSet。
```

### 3.1 新建集合
```java
HashSet<Integer> set = new HashSet<>();
```

### 3.2 删除元素
```java
//这里只能删除元素，无index
set.remove(1);
```

### 3.3 是否包含
```java
set.contains(5);
```

### 3.4 遍历
```java
for(Integer num:set){
    System.out.println(...);
}
```

## 4. Map

- HashMapis implemented as a hash table, and there is no ordering on keys or values. 
- TreeMap is implemented based on red-black tree structure, and it is ordered by the key. 
- LinkedHashMap preserves the insertion order
- Hashtable  is synchronized in contrast to HashMap. 

一般做算法题都是 HashMap 偶尔可能用到 TreeMap

### 4.1 新建HashMap
```java
HashMap<String, Integer> map = new HashMap<>();
```

### 4.2 添加 K-V
```java
map.put("LBW",12);
```

### 4.3 获取 V
```java
map.get("LBW");
```

### 4.4 是否包含
```java
map.containsKey("LBW");

map.containsValue(12);
```
### 4.5 删除键值对

```java
map.remove("LBW");

// 只有 K—V 匹配的时候才能删掉
//Removes the entry for the specified key only if it is currently mapped to the specified value
map.remove("LBW",12);
```
### 4.6  KeySet

public Set<K> keySet()

Returns a Set view of the keys contained in this map. The set is backed by the map, so changes to the map are reflected in the set, and vice-versa. If the map is modified while an iteration over the set is in progress (except through the iterator's own remove operation), the results of the iteration are undefined. The set supports element removal, which removes the corresponding mapping from the map, via the Iterator.remove, Set.remove, removeAll, retainAll, and clear operations. It does not support the add or addAll operations.

Specified by:

- keySet in interface Map<K,V>

Overrides:

- keySet in class AbstractMap<K,V>

Returns:

- a set view of the keys contained in this map

~~就返回一个 map 里所有key的set~~ 

返回 hashMap 中所有 key 组成的集合视图

```java
map.ketSet();
```
### 4.7 values

返回 hashMap 中存在的所有 value 值

```java
map.values();
```

### 4.8 遍历HashMap

迭代 HashMap

可以使用 for-each 来迭代 HashMap 中的元素。

如果你只想获取 key，可以使用 keySet() 方法，然后可以通过 get(key) 获取对应的 value

如果你只想获取 value，可以使用 values() 方法

```java
HashMap<String, String> map = new HashMap<>();
for (String key : map.keySet()) {
	map.get(key);
}
```

### 4.9 getOrDefault

获取指定 key 对应对 value，如果找不到 key ，则返回设置的默认值

public V getOrDefault(Object key,
                      V defaultValue)

Description copied from interface: Map

Returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key.

Specified by:

- getOrDefault in interface Map<K,V>

Parameters:
- key - the key whose associated value is to be returned
- defaultValue - the default mapping of the key

Returns:
- the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key

```java
map.put(str, map.getOrDefault(str, 0) + 1);//如果存在就+1，不存在就为0
```