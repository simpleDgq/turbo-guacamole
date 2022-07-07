package class39;

import java.util.HashSet;
import java.util.TreeSet;

public class Code01_SubArrMaxModalM {
	/**
	 * 非负数组子序列中累加和%m的最大值。
	 * 给定一个非负数组arr，和一个正数m，返回arr的所有子序列中累加和%m之后的最大值。
	 */
	
	public static int max1(int arr[], int m) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		process(0, 0, set, arr);
		int max = 0;
		for(int sum : set) {
			max = Math.max(max, sum % m);
		}
		return max;
	}

	public static void process(int index, int sum, HashSet<Integer> set, int arr[]) {
		if(index == arr.length) {
			set.add(sum);
			return;
		}
		process(index + 1, sum + arr[index], set, arr);
		process(index + 1, sum, set, arr);
	}
	
	// 动态规划
	/*
	 * dp[i][j]: 是个布尔类型的值
	 * arr 从0 ~i 自由选择这些数字，能不能把某一个累加和j搞出来
	 *
	 *
	 * 如果arr的值巨大，加起来之后，可能就超过了10^8，那么这种解法就失效了。
	 */
	public static int max2(int arr[], int m) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int sum = 0;
		for(int v : arr) {
			sum += v;
		}
		boolean dp[][] = new boolean[N][sum + 1];
		// 填好第一列
		for(int i = 0; i <= N - 1; i++) { 
			dp[i][0] = true; // 一个都不选的情况下， 一定能够凑出累加和0
		}
		dp[0][arr[0]] = true; // 选择0位置的情况下，一定能够凑出arr[0]
		
		//两种选择。
		//第一种情况是不要i位置的数，这个问题就变成了你是否能用之前 0 到 i-1的数字，能不能把j搞出来
		//第二种情况是我想使用 i位置的数字，这个问题就变成了你利用之前的 0 到 i-1的所有数字，
		//你能不能把j - i位置的值给去掉，把这个累加和搞出来，就是之前帮你搞定j-arr[i]值你再自己凑个arr[i], 正好是j 
		//所有数字的累加和决定了 j的上限
		for(int i = 1; i <= N - 1; i++) {
			for(int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j]; // 不要i位置
				// 要i位置, 只要i-1位置能够搞出j - arr[i]
				if(j - arr[i] >= 0) {
					dp[i][j] |= dp[i - 1][j - arr[i]];
				}
			}
		}
		
		// 最后一行，求最大值
		int max = 0;
		for(int j = 0; j <= sum ; j++) {
			if(dp[N - 1][j]) {
				max = Math.max(max, j % m);
			}
		}
		return max;
 	}
	
	/**
	 * dp[i][j]:
	 * arr 0..i所有的数字自由选择, 所搞出来的所有累加和再模m之后有没有余数j
	 * 
	 * 适用于m不大的情况，j的范围是0 ~ m - 1
	 */
	public static int max3(int arr[], int m) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		boolean dp[][] = new boolean[N][m];
		for(int i = 0; i <= N - 1; i++) { // 一个都不选的情况下，一定能够凑出余数0
			dp[i][0] = true;
		}
		dp[0][arr[0] % m] = true; // 只选0位置的情况下，一定能够凑出余数arr[0] % m
		
		for(int i = 1; i <= N - 1; i++) {
			for(int j = 1; j <= m - 1; j++) {
				dp[i][j] = dp[i - 1][j]; // 不选i位置， 只需要i-1位置能够搞出余数j就行
				// 选i位置
				// 如果i位置能够得到的余数是a, 小于等于j， 只需要i-1位置能够搞出j-a即可
				// 如果i位置能够得到的余数是a, 大于j， 只需要i-1位置能够搞出m+j-a即可
				int a = arr[i] % m;
				if(a <= j) { 
					dp[i][j] |= dp[i - 1][j - a];
				} else {
					dp[i][j] |= dp[i - 1][m + j - a];
				}
			}
		}
		
		// 最后一行
		int max = 0;
		for(int j = 0; j <= m - 1; j++) {
			if(dp[N - 1][j]) {
				max = Math.max(max, j);
			}
		}
		return max;
	}
	
	
	// 分治
	public static int max4(int arr[], int m) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int mid = (arr.length - 1) / 2;
		TreeSet<Integer> set1 = new TreeSet<Integer>();
		process(arr, 0, 0, mid, set1, m);
		TreeSet<Integer> set2 = new TreeSet<Integer>();
		process(arr, mid + 1, 0, arr.length - 1, set2, m);
		
		int max = 0;
		for(Integer value : set1) {
			// 从右边找一个最接近m的数
			max = Math.max(max, value + set2.floor(m - value - 1));	
		}
		return max;
	}
	
	public static void process(int arr[], int index, int sum,  int end, TreeSet<Integer> set, int m) {
		if(index == end + 1) {
			set.add(sum % m);
			return;
		}
		process(arr, index + 1, sum + arr[index], end, set, m);
		process(arr, index + 1, sum, end, set, m);
	}
	
	

	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		int m = 76;
		int testTime = 500000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = max1(arr, m);
			int ans2 = max2(arr, m);
			int ans3 = max3(arr, m);
			int ans4 = max4(arr, m);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

	}
}
