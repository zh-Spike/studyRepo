### 题目

我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。

示例:
```
输入: n = 10
输出: 12
解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
```
说明:  

- 1 是丑数。
- n 不超过1690。

注意：本题与主站 264 题相同：https://leetcode-cn.com/problems/ugly-number-ii/

链接：https://leetcode-cn.com/problems/chou-shu-lcof

### 思路

贪心 ？ 迭代法
```
第一个丑数                            1 
对每个丑数做乘2 3 5操作     2          3           5
                        4 6 10     6 9 15     10 15 25 
每次都从小到大取

对操作过的数 操作次数++ 迭代到dp[n-1]
```


### Code
```java
    class Solution {
        public int nthUglyNumber(int n) {
            int[] dp = new int[n];
            dp[0] = 1;
            int mod2 = 0, mod3 = 0, mod5 = 0;
            int index = 1;
            while (index < n) {
                dp[index] = Math.min(2 * dp[mod2], Math.min(3 * dp[mod3], 5 * dp[mod5]));
                if (dp[index] == 2 * dp[mod2]) {
                    mod2++;
                }
                if (dp[index] == 3 * dp[mod3]) {
                    mod3++;
                }
                if (dp[index] == 5 * dp[mod5]) {
                    mod5++;
                }
                index++;
            }
            return dp[n - 1];
        }
    }
```
*** 
### 收获
