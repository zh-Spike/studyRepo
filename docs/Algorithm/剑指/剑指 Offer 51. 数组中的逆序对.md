### 题目
在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。

 

示例 1:
```
输入: [7,5,6,4]
输出: 5
```
### 思路
这就是 模板题 无需多言

比前面个简单

### Code
```java
    class Solution {
        public int reversePairs(int[] arr) {
            if (arr == null || arr.length < 2) {
                return 0;
            }
            return mergeSort(arr, 0, arr.length - 1);
        }

        public int mergeSort(int[] arr, int l, int r) {
            if (l == r) {
                return 0;
            }
            int mid = l + ((r - l) >> 1);
            return mergeSort(arr, l, mid) + mergeSort(arr, mid + 1, r) + merge(arr, l, mid, r);
        }

        public int merge(int[] arr, int l, int m, int r) {
            int[] help = new int[r - l + 1];
            int i = 0;
            int p1 = l;
            int p2 = m + 1;
            int res = 0;
            while (p1 <= m && p2 <= r) {
                res += arr[p1] > arr[p2] ? (r - p2 + 1) : 0;
                help[i++] = arr[p1] > arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= m) {
                help[i++] = arr[p1++];
            }
            while (p2 <= r) {
                help[i++] = arr[p2++];
            }
            for (i = 0; i < help.length; i++) {
                arr[l + i] = help[i];
            }
            return res;
        }
    }
```
*** 
### 收获

发现刷题都是工业化的 模板 + 小改

