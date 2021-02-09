### 题目
从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。

示例 1:
```
输入: [1,2,3,4,5]
输出: True
```

示例 2:

```
输入: [0,0,1,2,5]
输出: True
```

限制：

- 数组长度为 5 
- 数组的数取值为 [0, 13] .

### 思路

0 就是万能排 sort一下后

1. 有无 0  
2. 有无重复

有 0 看看个数 4 / 5 直接true

其他的 看剩下数字的 max 和 min 若剩下正好是两头 即 max - min < 5 不然边界用 0 来变 

### Code
```java
    class Solution {
        public boolean isStraight(int[] nums) {
            Arrays.sort(nums);
            int zero = nums[0] == 0 ? 1 : 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == 0) {
                    zero++;
                } else if (nums[i] == nums[i - 1]) {
                    return false;
                }
            }
            if (zero == 5 || zero == 4) return true;
            return nums[nums.length - 1] - nums[zero] < 5 ? true : false;
        }
    }
```
*** 
### 收获
