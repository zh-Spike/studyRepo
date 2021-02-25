- [KMP](#kmp)
    - [Code](#code)
- [Manacher](#manacher)
    - [Code](#code-1)
# KMP

啊这 该学 KMP 了

KMP的最重要的部分就是确定 最长前缀匹配数组


![](https://pic.leetcode-cn.com/Figures/28/mismatch2.png)

![](https://pic.leetcode-cn.com/Figures/28/match.png)


### Code
```java
    class Solution {
        public int strStr(String s, String m) {
            if (m == null || m.length() == 0)
                return 0;
            if (s == null || s.length() == 0 || m.length() > s.length())
                return -1;
            char[] chs = s.toCharArray();
            char[] chm = m.toCharArray();
            // 用来统计前面 相同前缀的最大长度
            int[] next = getNext(chm);
            int p1 = 0, p2 = 0;
            while (p1 < chs.length && p2 < chm.length) {
                if (chs[p1] == chm[p2]) {
                    p1++;
                    p2++;
                    // 第二数组到头了 不能再跳了
                } else if (next[p2] == -1) {
                    p1++;
                } else {
                    // 匹配跳到下一个位置
                    p2 = next[p2];
                }
            }
            // p1 越界  或者  p2越界了
            // p1 越界 说明 无匹配
            // p2 越界说明找到了匹配串
            return p2 == chm.length ? p1 - p2 : -1;
        }

        public int[] getNext(char[] m) {
            // 统计函数 第一项 next[0] =  -1 next[1] = 0  
            if (m.length == 1) {
                return new int[]{-1};
            }
            int[] next = new int[m.length];
            next[0] = -1;
            next[1] = 0;
            int i = 2;
            // 当前匹配位置
            int cur = 0;
            while (i < next.length) {
                // 如果x前一个字符 和 从前到后的相等 
                if (m[i - 1] == m[cur]) {
                    // 找到了匹配的端 
                    // cur 先往前移 当前能匹配的长度 +1  
                    // next数组 后移
                    next[i++] = ++cur;
                    // next[i] = cur + 1;
                    // i++;
                } else if (cur > 0) {// 当前 cur 的字符和 i-1 处字符不匹配
                    // 那长度就等于前一段的前缀
                    // 因为前面已经保证了 他是最长的前缀 
                    cur = next[cur];
                } else {
                    // 都无相同前缀 且 cur 处和 i-1 处也不匹配 那就 0
                    next[i++] = 0;
                }
            }
            return next;
        }
    }
```

# Manacher

马拉车的基本思路是 

1. 先间隔插入 # (虚拟点) 来填充数组 这样做到 中心扩散时不会漏掉偶数部分

    找回文串时 永远是虚拟点对虚拟点 不会出现多的情况

2. 找每个节点中心扩散出去的最大长度 
   
   用 R 来记录最右边界 C 来记录中心点 开始时 R = -1 C = -1
   
   设一个回文半径数组 pArr

   求 每个点的 回文长度三种情况:

       1. 当前点在之前的最右边界 R 外 两边直接中心拓展 暴力法
          
          1. 比如最开始 情况 他的回文中心就是 C = 1 R =1 pArr[0] = 1 

            它不用验的长度就是 1 
        
       2. 当前点在区间内  C < 当前点i < R 这也分三种情况:
            
           找到 i 在 [R',C,R] 内的对称点 i' = 2 * C -i

           看 i' 处的回文串的长度

           1. 长度在 [R',R] 内 那对称的 i 的回文长度就是 i' 值
           2. 长度正好在 R' 上 回文长度就是 R 也还是 i' 值
           3. 长度在 R' 外 回文长度就是 R - i 值 如果说是 还是和之前一样回文 
                
           那 这就不是 C 处的最长的回文长度了 所以就是 R - i
        
    回文子串更新后 相应 R C 位置也要更新 

### Code
    
```java
public class Code02_Manacher {

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	public static int maxLcpsLength(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = manacherString(s); // 1221 ->  #1#2#2#1#
		int[] pArr = new int[str.length]; // 回文半径数组
		int C = -1; // 中心
		int R = -1; // 回文右边界的再往右一个位置    最右的有效区是R-1位置
		int max = Integer.MIN_VALUE; // 扩出来的最大值
		for (int i = 0; i != str.length; i++) { // 每一个位置都求回文半径
			// i至少的回文区域，先给pArr[i]
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(max, pArr[i]);
		}
		return max - 1;
	}

	public static void main(String[] args) {
		String str1 = "abc1234321ab";
		System.out.println(maxLcpsLength(str1));
	}

}

```