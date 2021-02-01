### 题目
输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。

示例 1：
```
输入：arr = [3,2,1], k = 2
输出：[1,2] 或者 [2,1]
```
示例 2：
```
输入：arr = [0,1,2,1], k = 1
输出：[0]
```

限制：

- 0 <= k <= arr.length <= 10000
- 0 <= arr[i] <= 10000

### 思路
和 215 差不多 这里只不过是小顶堆 还是要多用几种方法
### Code
```java
class Solution {
    public int[] getLeastNumbers(int[] nums, int k) {
            PriorityQueue<Integer> heap = new PriorityQueue<>();
            for (int i = 0; i < nums.length; i++) {
                heap.add(nums[i]);
            }
            int[]res = new int[k];
            for (int i = 0; i < k ; i++) {
                res[i]=heap.poll();
            }
            return res;
    }
}
```
*** 
### 收获
