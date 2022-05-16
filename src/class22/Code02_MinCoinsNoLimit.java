package class22;

public class Code02_MinCoinsNoLimit {
	/**
	 * 是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
	 * 每个值都认为是一种面值，且认为张数是无限的。
	 * 返回组成aim的最少货币数
	 */
	// 从左往右
	public static int minCoins(int arr[], int aim) {
		int zhangs =  process(arr, 0, aim);
		return zhangs;
	}
	// 当前来到index位置，每种面值可以用无数张，要组成rest这个数，你给我返回组成rest这个数的最少货币数。
	// 拿Integer.MAX_VALUE标记怎么都搞定不了
	public static int process(int arr[], int index, int rest) {
		if(index == arr.length) { // 没有货币了,rest == 0, 还需要的张数就是0张。如果rest != 0,说明搞不定
			return rest == 0 ? 0 : Integer.MAX_VALUE;
 		}
		int ans = Integer.MAX_VALUE;
		for(int zhang = 0; zhang * arr[index] <= rest; zhang++) {
			int next = process(arr, index + 1, rest - zhang * arr[index]); // 组成rest - zhang * arr[index]需要的最小张数
			//每一个面值，都来1,张，2张...n张，求所有情况的最小值
			if(next != Integer.MAX_VALUE) { // 说明能够搞定, 则更新最小值。不能搞定的情况下不需要更新。
											//如果不加这个判断，如果next是系统最大值, next+zhang就会溢出，变成一个负数，再求最小值就不对了
				ans = Math.min(ans, next + zhang); // next + zhang ==> 组成rest的最小张数。
			}
		}
		return ans;
	}
	
	/**
	 * 改动态规划
	 * 可变参数: index, rest
	 * 范围: index: 0~N rest 0~aim ==> int dp[N+1][aim+1]
	 */
	public static int dp(int arr[], int aim) {
		int N = arr.length;
		int dp[][] = new int[N+1][aim+1];
		dp[N][0] = 0;
		for (int j = 1; j <= aim; j++) {
			dp[N][j] = Integer.MAX_VALUE;
		}
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				int ans = Integer.MAX_VALUE;
				for(int zhang = 0; zhang * arr[index] <= rest; zhang++) {
					int next = dp[index + 1][rest - zhang * arr[index]];
					if(next != Integer.MAX_VALUE) { // 说明能够搞定
						ans = Math.min(ans, next + zhang);
					}
					dp[index][rest] = ans;
				}
			}
		}
		return dp[0][aim];
	}
	
	/**
	 * 优化枚举行为
	 */
	public static int dp2(int arr[], int aim) {
		int N = arr.length;
		int dp[][] = new int[N+1][aim+1];
		dp[N][0] = 0;
		for (int j = 1; j <= aim; j++) {
			dp[N][j] = Integer.MAX_VALUE;
		}

		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				dp[index][rest] = dp[index + 1][rest];
				if(rest - arr[index] >= 0 
						&& dp[index][rest - arr[index]] != Integer.MAX_VALUE) { // 这句注意：打叉的位置得是有效值。才更新
					// 否则加1之后，最大值Integer.MAX_VALUE就溢出了，变成了负的最小，导致结果不正确
					dp[index][rest] = Math.min(dp[index][rest - arr[index]] + 1, dp[index][rest]);
				}
			}
		}
		return dp[0][aim];
	}
	
	// 为了测试
	public static int[] randomArray(int maxLen, int maxValue) {
		int N = (int) (Math.random() * maxLen);
		int[] arr = new int[N];
		boolean[] has = new boolean[maxValue + 1];
		for (int i = 0; i < N; i++) {
			do {
				arr[i] = (int) (Math.random() * maxValue) + 1;
			} while (has[arr[i]]);
			has[arr[i]] = true;
		}
		return arr;
	}

	// 为了测试
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 为了测试
	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 30;
		int testTime = 300000;
		System.out.println("功能测试开始");
		System.out.println(Integer.MAX_VALUE);
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxLen);
			int[] arr = randomArray(N, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = minCoins(arr, aim);
//			int ans2 = dp1(arr, aim);
			int ans3 = dp2(arr, aim);
			if ( ans1 != ans3) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("功能测试结束");
	}

}
