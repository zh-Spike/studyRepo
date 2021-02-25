### 数组转集合
```java
public class Solution {
    public static void main(String[] args) {
        // TO TEST
        int n = 5;
        String[] name = new String[n];
        for (int i = 0; i < n; i++) {
            name[i] = String.valueOf(i);
        }
        System.out.println(Arrays.toString(name));
        List<String> list = Arrays.asList(name);
        for (String li : list) {
            String str = li;
            System.out.println(str + "");
        }
    }
 
```

输出
```
[0, 1, 2, 3, 4]
0
1
2
3
4
```

### Arrays.asList

将一个数组转化为一个 List 对象，一般会想到 Arrays.asList() 方法，这个方法会返回一个 ArrayList 类型的对象。

但是用这个对象对列表进行添加删除更新操作，就会报 UnsupportedOperationException 异常。

```java
@SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... a) {
        return new ArrayList<>(a);
    }

    /**
     * @serial include
     */
    private static class ArrayList<E> extends AbstractList<E>
        implements RandomAccess, java.io.Serializable
    {
        @java.io.Serial
        private static final long serialVersionUID = -2764017481108945198L;
        @SuppressWarnings("serial") // Conditionally serializable
        private final E[] a;

        ArrayList(E[] array) {
            a = Objects.requireNonNull(array);
        }
        
        ···

    }
```

看到`private final E[] a;` 

由此判断，这个静态内部类是不能做任何内部元素的添加删除操作的！就跟 String 类一样，String 对象存储字符数组的变量也是有 final 修饰符的。因为一旦增加数组元素，这个数组容量已经定好的容器就无法装载增加的元素了。

内部类里面并没有 add、remove 方法，这个类继承的 AbstractList 类里面有这些方法。

```java

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */

    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
 
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    ···
}
```
AbstractList 这个抽象类所定义的 add 和 remove 方法，抛出了异常。

如果是想将一个数组转化成一个列表并做增加删除操作的话，建议代码如下：
```java
List<WaiterLevel> levelList = new ArrayList<WaiterLevel>(Arrays.asList("a", "b", "c"));
```