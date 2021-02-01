### 题目
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。

示例：

```
输入：nums = [1,2,3,4]
输出：[1,3,2,4] 

注：[3,1,2,4] 也是正确的答案之一。
```


提示：

- 1 <= nums.length <= 50000
- 1 <= nums[i] <= 10000

### 思路

简单的双指针

### Code
```java
    class Solution {
        public int[] exchange(int[] nums) {
            int l = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] % 2 == 1) {
                    int tmp = nums[i];
                    nums[i] = nums[l];
                    nums[l++]=tmp;
                }
            }
            return nums;
        }
    }
```
```java
    class Solution {
        public int[] exchange(int[] nums) {
            var res = new int[nums.length];
            int head = 0, tail = nums.length - 1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] % 2 != 0) {
                    res[head++] = nums[i];
                } else {
                    res[tail--] = nums[i];
                }
            }
            return res;
        }
    }
```
*** 
### 收获

之前看了堆的资料 还以为这是堆的题