## DP 优化技巧

一般的动态规划 空间压缩

像 fib 等 严格递归的 无条件转移的DP 

    有 O(logN) 解法 本质上是 线代

fib -> 递归法 —> DP -> 矩阵快速幂解法 —>补充一个费波纳茨的题

### 题目

字符串只由'0'和'1'两种字符构成，

    当字符串长度为1时，所有可能的字符串为"0"、"1"；

    当字符串长度为2时，所有可能的字符串为"00"、"01"、"10"、"11"；

    当字符串长度为3时，所有可能的字符串为"000"、"001"、"010"、"011"、"100"、"101"、"110"、"111"

如果某一个字符串中，只要是出现'0'的位置，左边就靠着'1'，这样的字符串叫作达标字符串。

给定一个正数N，返回所有长度为N的字符串中，达标字符串的数量。

- 比如，N=3，返回3，因为只有"101"、"110"、"111"达标。

### 解法

观察 达标字符串 就是 取决于前两位

对于位置 i，假定 i 前面是1，则 i 有两种情况

当 i=0 时，i+1位必为1，剩下的就是 f(i-2)

当 i=1 时，i+1位可以是1，也可以是0，因此剩下的是 f(i-1)

即 f(i)= f(i-2)+ f(i-1) 就是个 fib 

```java
public class Problem01_ZeroLeftOneStringNumber {

	public static int getNum1(int n) {
		if (n < 1) {
			return 0;
		}
		return process(1, n);
	}

    // 递归法
	public static int process(int i, int n) {
		if (i == n - 1) {
			return 2;
		}
		if (i == n) {
			return 1;
		}
		return process(i + 1, n) + process(i + 2, n);
	}

    // DP 法 空间优化
	public static int getNum2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		int pre = 1;
		int cur = 1;
		int tmp = 0;
		for (int i = 2; i < n + 1; i++) {
			tmp = cur;
			cur += pre;
			pre = tmp;
		}
		return cur;
	}

    // 快速幂
	public static int getNum3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return n;
		}
		int[][] base = { { 1, 1 }, { 1, 0 } };
		int[][] res = matrixPower(base, n - 2);
		return 2 * res[0][0] + res[1][0];
	}
	
	public static int fi(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		int[][] base = { { 1, 1 }, 
				         { 1, 0 } };
		int[][] res = matrixPower(base, n - 2);
		return res[0][0] + res[1][0];
	}
	
    // 二进制加速
	public static int[][] matrixPower(int[][] m, int p) {
		int[][] res = new int[m.length][m[0].length];
		for (int i = 0; i < res.length; i++) {
			res[i][i] = 1;
		}
		int[][] tmp = m;
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				res = muliMatrix(res, tmp);
			}
			tmp = muliMatrix(tmp, tmp);
		}
		return res;
	}
    
    // 矩阵乘法
	public static int[][] muliMatrix(int[][] m1, int[][] m2) {
		int[][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				for (int k = 0; k < m2.length; k++) {
					res[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		for (int i = 0; i != 20; i++) {
			System.out.println(getNum1(i));
			System.out.println(getNum2(i));
			System.out.println(getNum3(i));
			System.out.println("===================");
		}

	}
}
```

### 快速幂

快速幂的使用场景——除了初始项之外，其余项都有要个递归式的问题，可以使用快速幂进行求解。那种有if ，else的这种不能用快速幂，快速幂要求严格的递推式。

#### 递推式到通项

对于数列 f(n)=f(n-1)+f(n-2)，被减数最大值为2，因此我们的矩阵是2阶的。

![](../pics/fib%20(1).png)

通过 `递归式` 求 `通项公式`

![](../pics/fib%20(2).png)

同理见`三阶递归式` 

F(N) = F(N - 1) + F(N - 3)

![](../pics/fib%20(5).png)

求 矩阵就是线代做法

得到|F3 F2 F1| * 一个 3 * 3 的矩阵 次数也变了

![](../pics/fib%20(6).png)

得到了通项式 

#### 高次幂加速

利用二进制加速方法

如何提高高次幂运算速度，比如10的75次幂。

第一步：将75拆成二进制，即1001011

第二步：两个辅助变量，t=10，res=1（用于res记录结果）

![](../pics/fib%20(3).png)

上伪代码（时间复杂度O(logn)=O(logn)(即t=t*t的次数)+O(logn)（即75的二进制为1的项与t相乘的次数））：


他这个 m 是在我算通项的时候 可手算 出来

```java
// 二进制加速
	public static int[][] matrixPower(int[][] m, int p) {
		int[][] res = new int[m.length][m[0].length];
		for (int i = 0; i < res.length; i++) {
			res[i][i] = 1;
		}
		int[][] tmp = m;
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				res = muliMatrix(res, tmp);
			}
			tmp = muliMatrix(tmp, tmp);
		}
		return res;
	}
    
    // 矩阵乘法
	public static int[][] muliMatrix(int[][] m1, int[][] m2) {
		// res 矩阵初始化
		int[][] res = new int[m1.length][m2[0].length];
		// 行
		for (int i = 0; i < m1.length; i++) {
			// 列
			for (int j = 0; j < m2[0].length; j++) {
				for (int k = 0; k < m2.length; k++) {
					res[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return res;
	}
```

#### Fib 快速幂

那些除了初始项之外，其余项都有严格递推式的题，有if，else的，就不适合用费波纳茨，因为他有条件转移。


[参考](https://blog.csdn.net/qq_29996285/article/details/85160468)