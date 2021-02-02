### 题目
如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。

例如，

[2,3,4] 的中位数是 3

[2,3] 的中位数是 (2 + 3) / 2 = 2.5

设计一个支持以下两种操作的数据结构：

- void addNum(int num) - 从数据流中添加一个整数到数据结构中。
- double findMedian() - 返回目前所有元素的中位数。
示例 1：
```
输入：
["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
[[],[1],[2],[],[3],[]]
输出：[null,null,null,1.50000,null,2.00000]
```
示例 2：
```
输入：
["MedianFinder","addNum","findMedian","addNum","findMedian"]
[[],[2],[],[3],[]]
输出：[null,null,2.00000,null,2.50000]
```

限制：

- 最多会对 addNum、findMedian 进行 50000 次调用。

### 思路

很明显这个是 堆的问题 用一个大顶堆 小顶堆来解决即可

直接应用 java内置的优先队列就好了

会考你？ 手撸 堆 / 优先队列
### Code
```java
class MedianFinder {
    PriorityQueue<Integer> left;
    PriorityQueue<Integer> right;
    /** initialize your data structure here. */
    public MedianFinder() {
        // 大顶堆
        left = new PriorityQueue<>((n1,n2)->n2-n1);
        // 小顶堆
        right= new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // 先进大顶堆
        left.add(num);
        // 弹出大顶堆 把弹出的值进小顶堆
        // 这样保证 小顶堆会比较大
        right.add(left.poll());
        if(left.size()+1<right.size())
            //  当左右高度差超过 1  重新出小顶堆 压回 大顶堆 
            left.add(right.poll());
    }
    /*
        public void addNum(int num) {
        if (left.size() >= right.size()) {
                left.offer(num);
                right.offer(left.poll());
            } else {
                right.offer(num);
                left.offer(right.poll());
            }
    }
    */
    
    public double findMedian() {
        if(right.size()>left.size()) return right.peek();
        return (double)(left.peek()+right.peek())/2;
    }
}
```
*** 
### 收获

感觉学过了就是 乱杀！
