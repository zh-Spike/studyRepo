### 题目
输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。

示例 1：
```
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[1,2,3,6,9,8,7,4,5]
```
示例 2：
```
输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
输出：[1,2,3,4,8,12,11,10,9,5,6,7]
```
来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/shun-shi-zhen-da-yin-ju-zhen-lcof
### 思路

宏观考虑位置 写出转移

这里的宏观是 打印边框 然后用 while 来限制边框

### Code
```java
class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> list = new ArrayList<>();
            if (matrix.length == 0 || matrix == null) {
                return list;
            }
            int tR = 0;
            int tC = 0;
            int dR = matrix.length - 1;
            int dC = matrix[0].length - 1;
            while (tR <= dR && tC <= dC) {
                // 逐步缩小打印的边框
                printEdge(matrix, tR++, tC++, dR--, dC--, list);
            }
            return list;
        }

        public void printEdge(int[][] m, int a, int b, int c, int d, List<Integer> list) {
            // 处理只有 1 行 或者 1 列
            if (a == c) {
                for (int i = b; i <= d; i++) {
                    list.add(m[a][i]);
                }
            } else if (b == d) {
                for (int i = a; i <= c; i++) {
                    list.add(m[i][b]);
                }
            } else {// 答应边框 
                int curC = b;
                int curR = a;
                while (curC != d) {
                    list.add(m[a][curC]);
                    curC++;
                }
                while (curR != c) {
                    list.add(m[curR][d]);
                    curR++;
                }
                while (curC != b) {
                    list.add(m[c][curC]);
                    curC--;
                }
                while (curR != a) {
                    list.add(m[curR][b]);
                    curR--;
                }
            }
        }
    }
```
*** 
### 收获
