package class19;

public class Code04_LongestCommonSubsequence {
	// 链接：https://leetcode.com/problems/longest-common-subsequence/
	/**
	 * 求两个字符串的最长公共子串的长度
	 * 样本对应模型
	 */
	public static int longestCommonSubs(String str1, String str2) {
		if(str1 == null || str2 == null || 
				str1.length() == 0 || str2.length() == 0) {
			return 0;
		}
		char[] arr1 = str1.toCharArray();
		char[] arr2 = str2.toCharArray();
		
		return process(arr1, arr2, arr1.length - 1, arr2.length - 1);
	}
	// str1[0...i]和str2[0...j]，这个范围上最长公共子序列长度是多少？
	public static int process(char[] str1, char[] str2, int i, int j) {
		if(i == 0 && j == 0) { // 放最前，否则会报错（i和j同时为0，j-1会越界）
			return str1[i] == str2[j] ? 1 : 0;
		} else if(i == 0) { // str1只有一个字符了
			if(str1[i] == str2[j]) { // 如果最后一个字符相等，说明已经找到了，返回1
				return 1;
			} else {
				return process(str1, str2, i, j - 1); // 不相等，去j - 1位置找
			}
		} else if(j == 0) { // str2 只有一个字符了
			if(str1[i] == str2[j]) { // 如果最后一个字符相等，说明已经找到了，返回1
				return 1;
			} else {
				return process(str1, str2, i - 1, j); // 不相等，去i - 1位置找
			}
		} else { // i和j都不等于0
			// 分3种情况
			//1. 最长公共子串可能以str[i]结尾，一定不以str[j]结尾
			//2. 最长公共子串可能以str[j]结尾，一定不以str[i]结尾
			//3. 一定以str[i]和str[j]作为结尾
			int p1 = process(str1, str2, i, j - 1);
			int p2 = process(str1, str2, i - 1, j);
			int p3 = 0;
			if(str1[i] == str2[j]) {
				p3 = 1 + process(str1, str2, i - 1, j - 1); // 注意加上1，最后一个字符也要算上
			}
			return Math.max(p1, Math.max(p2, p3));
		}
	}
	
	/**
	 * 改动态规划
	 * 分析可变参数及其范围 ==> i和j， 范围 0~N-1 和0~M-1 N和M分别是字符串长度
	 * int dp[N][M]
	 * 
	 * 分析依赖，根据递归代码
	 * 一个i和j位置依赖，(i, j - 1)(左) ， (i - 1, j)(上), (i - 1, j - 1)（左上）
	 * 三个位置。
	 * 
	 * 根据递归填表 ==> 填好第一行和第一列，然后填剩下的格子
	 * 
	 */
	public static int longestCommonSubs2(String str1, String str2) {
		if(str1 == null || str2 == null || 
				str1.length() == 0 || str2.length() == 0) {
			return 0;
		}
		char[] arr1 = str1.toCharArray();
		char[] arr2 = str2.toCharArray();
		int N = arr1.length;
		int M = arr2.length;
		
		int[][] dp = new int[N][M];
		dp[0][0] = arr1[0] == arr2[0] ? 1 : 0;
		for(int j = 1; j < M; j++) {
			if(arr1[0] == arr2[j]) {
				dp[0][j] = 1;
			} else {
				dp[0][j] = dp[0][j - 1];
			}
		}
		for(int i = 1; i < N; i++) {
			if(arr1[i] == arr2[0]) {
				dp[i][0] = 1;
			} else {
				dp[i][0] = dp[i - 1][0];
			}
		}
		for(int i = 1; i < N; i++) {
			for(int j = 1; j < M; j++) {
				int p1 = dp[i][j - 1];
				int p2 = dp[i - 1][j];
				int p3 = 0;
				if(arr1[i] == arr2[j]) {
					p3 = 1 + dp[i - 1][j - 1]; // 注意加上1，最后一个字符也要算上
				}
				dp[i][j] = Math.max(p1, Math.max(p2, p3));
			}
		}
		return dp[arr1.length - 1][arr2.length - 1];
	}
}