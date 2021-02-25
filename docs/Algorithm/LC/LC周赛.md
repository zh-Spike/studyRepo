 1710. 卡车上的最大单元数

请你将一些箱子装在 一辆卡车 上。给你一个二维数组 boxTypes ，其中 

`boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi]` ：

numberOfBoxes[i]是类型 i 的箱子的数量。
numberOfUnitsPerBox[i] 是类型 i 每个箱子可以装载的单元数量。
整数 truckSize 表示卡车上可以装载 箱子 的 最大数量 。只要箱子数量不超过 truckSize ，你就可以选择任意箱子装到卡车上。

返回卡车可以装载 单元 的 最大 总数。

示例 1：
```
输入：boxTypes = [[1,3],[2,2],[3,1]], truckSize = 4
输出：8
解释：箱子的情况如下：
- 1 个第一类的箱子，里面含 3 个单元。
- 2 个第二类的箱子，每个里面含 2 个单元。
- 3 个第三类的箱子，每个里面含 1 个单元。
可以选择第一类和第二类的所有箱子，以及第三类的一个箱子。
单元总数 = (1 * 3) + (2 * 2) + (1 * 1) = 8
```

示例 2：
```
输入：boxTypes = [[5,10],[2,5],[4,7],[3,9]], truckSize = 10
输出：91
```
 
提示：

- 1 <= boxTypes.length <= 1000
- 1 <= numberOfBoxesi, numberOfUnitsPerBoxi <= 1000
- 1 <= truckSize <= 106

***
### 思路

没啥想法 贪心 每次都放max的 


### Code
```java
    class Solution {
        public int maximumUnits(int[][] boxTypes, int truckSize) {
            Arrays.sort(boxTypes, (o1, o2) -> (o2[1] - o1[1]));
            int res = 0;
            for (int i = 0; i < boxTypes.length && truckSize > 0; i++) {
                int num = Math.min(truckSize, boxTypes[i][0]);
                truckSize -= num;
                res += num * boxTypes[i][1];
            }
            return res;
        }
    }
```
*** 
### 1711. 大餐计数

大餐 是指 恰好包含两道不同餐品 的一餐，其美味程度之和等于`2 的幂`。

你可以搭配 任意 两道餐品做一顿大餐。

给你一个整数数组 deliciousness ，其中 deliciousness[i] 是第 i​​​​​​​​​​​​​​ 道餐品的美味程度，返回你可以用数组中的餐品做出的不同 大餐 的数量。结果需要对 109 + 7 取余。

注意，只要餐品下标不同，就可以认为是不同的餐品，即便它们的美味程度相同。

示例 1：
```
输入：deliciousness = [1,3,5,7,9]
输出：4
解释：大餐的美味程度组合为 (1,3) 、(1,7) 、(3,5) 和 (7,9) 。
它们各自的美味程度之和分别为 4 、8 、8 和 16 ，都是 2 的幂。
```
示例 2：
```
输入：deliciousness = [1,1,1,3,3,3,7]
输出：15
解释：大餐的美味程度组合为 3 种 (1,1) ，9 种 (1,3) ，和 3 种 (1,7) 。
```

提示：

- 1 <= deliciousness.length <= 10^5
- 0 <= deliciousness[i] <= 2^20
*** 
### 思路

~~老暴力哥了~~ ~~周赛震怒 暴力哥都得死!~~

翻译下题目就是 找一组(x,y) 满足 (x+y) 为2的幂的个数

正确应该是把算出来的值放到map里 查找就可以了

### Code
```java
    class Solution{ 
    public int countPairs(int[] deliciousness) {
        Map<Integer, Integer> map = new HashMap<>();
        int mod = 1000000007, answer = 0, length = deliciousness.length;
        for (int num : deliciousness) {
            int powerOfTwo = 1;
            for (int i = 0; i <= 21; i++) {
                if (powerOfTwo >= num && map.containsKey(powerOfTwo - num)) {
                    answer += map.get(powerOfTwo - num);
                    answer %= mod;
                }
                    powerOfTwo *= 2;
                }
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    return (int)answer;
}
}
```
### 1712. 将数组分成三个子数组的方案数
我们称一个分割整数数组的方案是 好的 ，当它满足：

- 数组被分成三个 非空 连续子数组，从左至右分别命名为 left ， mid ， right 。
- left 中元素和小于等于 mid 中元素和，mid 中元素和小于等于 right 中元素和。
给你一个 非负 整数数组 nums ，请你返回 好的 分割 nums 方案数目。由于答案可能会很大，请你将结果对 10e9 + 7 取余后返回。

示例 1：
```
输入：nums = [1,1,1]
输出：1
解释：唯一一种好的分割方案是将 nums 分成 [1] [1] [1] 。
```
示例 2：
```
输入：nums = [1,2,2,2,5,0]
输出：3
解释：nums 总共有 3 种好的分割方案：
[1] [2] [2,2,5,0]
[1] [2,2] [2,5,0]
[1,2] [2,2] [5,0]
```
示例 3：
```
输入：nums = [3,2,1]
输出：0
解释：没有好的分割方案。
```
提示：
- 3 <= nums.length <= 10^5
- 0 <= nums[i] <= 10^4
***
### 思路

连续的 left就是直接前缀和 right就是数组后面的后缀和 mid的前缀和>=left的两倍

把 left 和 right 用数组存出来 然后右移 mid 的右指针 `但是这样做又是超时！`

```

|        |                 |                           |
  left         mid                    right

```

后来看了题解 使用二分来处理这个前缀和 


### Code
```java
class Solution {
    public int waysToSplit(int[] nums) {
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        final int MOD = 1000000000 + 7;
        long ans = 0;
        // |______|________|_______|________|
        // 1      i        l       r        n
        // i 表示第一刀的位置，枚举第一刀的位置，计算第二刀的可选位置数
        for (int i = 1, l = 2, r = 2; i <= n - 1; i++) {
            l = Math.max(l, i + 1);
            r = Math.max(r, i + 1);
            // sum(right) >= sum(mid)，r最大为n-1，right保证要有一个数
            while (r <= n - 1 && sum[n] - sum[r] >= sum[r] - sum[i]) {
                r++;
            }
            // sum(mid) >= sum(left)
            while (l <= n - 1 && sum[l] - sum[i] < sum[i]) {
                l++;
            }
            if (l <= r) {
                ans += r - l;
            }
        }
        return (int) (ans % MOD);
    }
}
```
### 收获

两数之和用Map用来存大数对还是第一次学

在对大数的处理还是不够 为什么没想到二分呢

用lambda表达式来重写sort方法比较优雅

`Arrays.sort(boxTypes, (o1, o2) -> (o2[1] - o1[1]));`
