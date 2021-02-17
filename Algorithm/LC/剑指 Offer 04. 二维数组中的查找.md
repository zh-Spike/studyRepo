### 题目
在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。


示例:
```
现有矩阵 matrix 如下：

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。

给定 target = 20，返回 false。
```
 

限制：

- 0 <= n <= 1000

- 0 <= m <= 1000

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
### 思路

因为从左到右 从上到下 都是增的 写个 while 同时维护两个就好 

如果分开写 他弯折的情况无法考虑

### Code
```java
    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return false;
            }
            int col = matrix[0].length - 1, r = 0;
            // 要同时维护两个条件
            while (r < matrix.length && col >= 0) {
                if (target > matrix[r][col]) {
                    r++;
                } else if (target < matrix[r][col]) {
                    col--;
                } else {
                    return true;
                }
            }
            return false;
        }
    }
```
*** 
### 收获
