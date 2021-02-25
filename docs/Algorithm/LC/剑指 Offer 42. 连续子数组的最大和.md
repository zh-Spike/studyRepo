### 题目

输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。

要求时间复杂度为O(n)。

示例1:
```
输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```

提示：

- 1 <= arr.length <= 10^5
- -100 <= arr[i] <= 100

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof

### 思路

max 随 cur 变化 取max

cur = cur + arr[i] 

cur < 0 加上负的没用 不如本身 所以 cur = 0

### Code
```java
    class Solution {
        public int maxSubArray(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int max = Integer.MIN_VALUE;
            int cur = 0;
            for (int i = 0; i != arr.length; i++) {
                cur += arr[i];
                max = Math.max(max, cur);
                cur = Math.max(0, cur);
            }
            return max;
        }
    }
```
*** 
### 收获
