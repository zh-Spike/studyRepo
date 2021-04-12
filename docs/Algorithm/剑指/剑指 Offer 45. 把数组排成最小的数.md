### 题目

输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。

示例 1:
```
输入: [10,2]
输出: "102"
```
示例 2:
```
输入: [3,30,34,5,9]
输出: "3033459"
```

提示:

- 0 < nums.length <= 100

说明:

- 输出结果可能非常大，所以你需要返回一个字符串而不是整数
- 拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0

链接：https://leetcode-cn.com/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof

### 思路

类似 原站 179 maxNumber

比较器条件一改即可

### Code
```java
    class Solution {
        public String minNumber(int[] nums) {
            // 几个要拼接的数
            int n = nums.length;
            // 拆分出来放到字符串数组里
            String[] ss = new String[n];
            for (int i = 0; i < n; i++) {
                ss[i] = String.valueOf(nums[i]);
            }
            // 两两组合排序 组合出小的放前面
            // 就单纯字典序 0123456789 也很字典序
            Arrays.sort(ss, ((o1, o2) -> {
                String s1 = o1 + o2, s2 = o2 + o1;
                return s1.compareTo(s2);
            }));
            StringBuilder sb = new StringBuilder();
            for (String s : ss) {
                sb.append(s);
            }
            // 这题 [0,0] === > 00 不用输出 0  
            return sb.toString();
        }
    }
```
*** 
### 收获
