package class20;

public class Code01_PalindromeSus {
	// 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
	/**
	 * 最长回文子序列
	 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
		比如 ： str = “a12b3c43def2ghi1kpm”
		最长回文子序列是“1234321”或者“123c321”，返回长度7
	 */
	public int longestPalindromeSubseq(String s) {
		if(s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		return f(str, 0, str.length - 1);
    }
	
	// 范围尝试模型
	/**
	 * 一个函数f，给你str，在L~R范围上，给我返回最长回文子序列的长度是多少？
	 * base case：
	 * L==R， 只有一个字符，最长回文子序列长度就是1，返回1
	 * L==R-1，剩下两个字符，如果相等，则返回2，否则返回1（这个base case可以不补）
	 */
	public static int f(char str[], int L, int R) {
		if(L == R) {
			return 1;
		}
		if(L == R - 1) { // 两个字符
			return str[L] == str[R] ? 2 : 1;
		}
		// 普遍情况
		// 以L开始，以R结尾   4 2 3 1 3 2 4
		// 不以L开始，以R结尾  1 4 2 3 1 3 2 4
		// 以L开始，不以R结尾   4 2 3 1 3 2 4 5
		// 不以L开始，不以R结尾  6 2 3 1 3 2 5
		int p1 = 0;
		if(str[L] == str[R]) {
			p1 = f(str, L + 1, R - 1) + 2;
		}
		int p2 = f(str, L + 1, R);
		int p3 = f(str, L, R - 1);
		int p4 = f(str, L + 1, R - 1);
		return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
	}
	
	/**
	 * 改动态规划
	 * 
	 * 分析可变参数， L和R，范围是0~N-1 ==> int dp[N][N]
	 * 
	 * 分析依赖：L,R 位置依赖于(L + 1, R - 1)（左下） 、 (L + 1, R)（下）、(L, R - 1)（左）
	 * 
	 * 填表
	 * 
	 */
	public static int longestPalindromeSubseq2(String s) {
		if(s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int dp[][] = new int[N][N];
		dp[N - 1][N -1] = 1;
		for(int i = 0; i < N - 1; i++) {
			dp[i][i] = 1;
			dp[i][i+1] = str[i] == str[i + 1] ? 2 : 1;
		}
		for(int startCol = 2; startCol <= N - 1; startCol++) {
			int R = startCol;
			int L = 0;
			while(L < N - startCol) { // 这里不能写成N - R， 因为R在变
				int p1 = 0;
				if(str[L] == str[R]) {
					p1 = dp[L + 1][R - 1] + 2;
				}
				int p2 = dp[L + 1][R];
				int p3 = dp[L][R - 1];
//				int p4 =dp[L + 1][R - 1]; // 左下这个格子不用比拼，因为求当前格子的值的时候，会和它的左边比拼
				// 但是它的左边的格子再求的时候，已经和它的下面格子(也就是当前格子的左下)比拼过了
				dp[L][R] = Math.max(Math.max(p1, p2), p3);
				L++;
				R++;
			}
		}
		return dp[0][N - 1];
	}
	
	public static void main(String args[]) {
		String str = "aabaaba";
		System.out.println(longestPalindromeSubseq2(str));
	}
}
