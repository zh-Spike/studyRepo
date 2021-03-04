### 题目1

给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。

子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。

 
示例 1：
```
输入：nums = [10,9,2,5,3,7,101,18]
输出：4
解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
```
示例 2：
```
输入：nums = [0,1,0,3,2,3]
输出：4
```
示例 3：
```
输入：nums = [7,7,7,7,7,7,7]
输出：1
```

提示：

- 1 <= nums.length <= 2500
- -104 <= nums[i] <= 104
 

进阶：

- 你可以设计时间复杂度为 O(n2) 的解决方案吗？
- 你能将算法的时间复杂度降低到 O(n log(n)) 吗?

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/longest-increasing-subsequence

### 思路

朴素dp：

    dp[] 存 以当前字母结尾能达到的最长递增子序列

    每个字母都遍历 左边所有 dp[] 找到最大的那个 + 1 
    
    没有就是 本体 1

抓住`单调性`

二分dp：
    
    搞一个end[i] 用来存放 所有长度位 i + 1 的递增子序列的 最小结尾 
    
    二分查找end的有小区 如果有 更小 更新 

    搞一个 满足当前长度 且 结尾最小数字的数组 他单调所以有二段性 懂得都懂

    如果有 大于最长的最小数字 拓展长度 

    如果没有 更新当前数能组成的最长的最后一位 `二分查找`

### Code
朴素DP
```java
    class Solution {
        public int lengthOfLIS(int[] nums) {
            int length = nums.length;
            int dp[] = new int[length];

            Arrays.fill(dp, 1);
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            int res = 0;
            for (int i = 0; i < length; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
        }
    }

```

二分法
```java
class Solution {
        public int lengthOfLIS(int[] nums) {
            if (nums.length <= 1) {
                return nums.length;
            }
            // 满足长度为 len 的最小结尾数字 
            int[] end = new int[nums.length];
            end[0] = nums[0];
            int len = 0;
            // 当前 比 当前最大长度len 的结尾数字大 说明长度可以拓展 1 位
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > end[len]) {
                    len++;
                    end[len] = nums[i];
                } else {
                    // 不满足第一个条件 
                    // 二分查找 更新 满足当前数子和之前构成的最大长度 
                    int left = 0;
                    int right = len;
                    while (left < right) {
                        int mid = left + ((right - left) >>> 1);
                        if (end[mid] < nums[i]) {
                            left = mid + 1;
                        } else {
                            right = mid;
                        }
                    }
                    // 更新最小结尾
                    end[left] = nums[i];
                }
            }
            // 因为返回的是长度 len 下标 还要 +1
            len++;
            return len;
        }
    }
```
*** 
### 题目2

给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。

请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。

说明:
- 不允许旋转信封。

示例:
```
输入: envelopes = [[5,4],[6,4],[6,7],[2,3]]
输出: 3 
解释: 最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
```

链接：https://leetcode-cn.com/problems/russian-doll-envelopes

### 思路

二维转一维

其余同上

朴素dp：

    dp[] 存 以当前字母结尾能达到的最长递增子序列

    每个字母都遍历 左边所有 dp[] 找到最大的那个 + 1 
    
    没有就是 本体 1

抓住`单调性`

二分dp：
    
    搞一个end[i] 用来存放 所有长度位 i + 1 的递增子序列的 最小结尾 
    
    二分查找end的有小区 如果有 更小 更新 

    搞一个 满足当前长度 且 结尾最小数字的数组 他单调就有二段性 懂得都懂

    如果有 大于最长的最小数字 拓展长度 

    如果没有 更新当前数能组成的最长的最后一位 `二分查找

### Code
```java
    class Solution {
        public int maxEnvelopes(int[][] matrix) {
            // 宽度不等时 宽度升序 相等时长度降序
            Arrays.sort(matrix, (o1, o2) -> (o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]));
            // ends 数组是用 记录 长度为 index + 1 最小的结尾
            int[] ends = new int[matrix.length];
            ends[0] = matrix[0][1];
            // 用二分效率高
            int right = 0;
            int l = 0;
            int r = 0;
            int m = 0;
            for (int i = 1; i < matrix.length; i++) {
                l = 0;
                r = right;
                while (l <= r) {
                    m = (l + r) / 2;
                    if (matrix[i][1] > ends[m]) {
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                }
                right = Math.max(right, l);
                ends[l] = matrix[i][1];
            }
            return right + 1;
        }
    }
```