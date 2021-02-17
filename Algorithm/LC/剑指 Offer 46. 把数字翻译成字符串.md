### 题目
给定一个数字，我们按照如下规则把它翻译为字符串：

    0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。
    
一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。

示例 1:

```
输入: 12258
输出: 5
解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
```

提示：

- 0 <= num < 231

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof

### 思路

从左往右搞 当未到右边界时 每次最少可以走一步 

当未到 index - 2 时 每次也可走两步 

注意当 当前的 str[index] * 10 + str[index + 1] < 25 且 str[index] != 0 才能算一个方法 

### Code
```java
    class Solution {
        public int translateNum(int num) {
            return process(String.valueOf(num).toCharArray(), 0);
        }

        // 0..index-1  已经转换完毕，并且转换正确
        // str[index...] 能转出多少种有效的字符串表达
        public int process(char[] str, int index) {
            if (index == str.length) {
                return 1;
            }
            // index及其后续是还有数字字符的
            // 做了一个决定，就让str[index]自己作为一个部分
            int res;
            res = process(str, index + 1);
            // 除了index之外，后续没有字符串了
            if (index == str.length - 1) {
                return res;
            }
            // index+1依然没越界
            // index和index+1 共同构成一个部分   <26
            if (((str[index] - '0') * 10 + str[index + 1] - '0') < 26 && str[index] != '0') {
                res += process(str, index + 2);
            }
            return res;
        }
    }
```

优化成 1 维 DP

```java
    class Solution {
        public int translateNum(int num) {
            char[] str = String.valueOf(num).toCharArray();
            int N = str.length;
            int[] dp = new int[N + 1];
            dp[N] = dp[N - 1] = 1;
            for (int i = N - 2; i >= 0; i--) {
                dp[i] = dp[i + 1] + (((str[i] - '0') * 10 + str[i + 1] - '0') < 26 && str[i] != '0' ? dp[i + 2] : 0);
            }
            return dp[0];
        }
    }
```
*** 
### 收获
