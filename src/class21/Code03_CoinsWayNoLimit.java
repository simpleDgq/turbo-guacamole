package class21;

public class Code03_CoinsWayNoLimit {
	/**
	 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
		每个值都认为是一种面值，且认为张数是无限的。
		返回组成aim的方法数
		例如：arr = {1,2}，aim = 4
		方法如下：1+1+1+1、1+1+2、2+2
		一共就3种方法，所以返回3
	 */
	public static int ways(int arr[], int aim) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(0, aim, arr);
	}
	// 从左往右尝试模型
	// 当前来到index位置, index位置的面值有无数张可以用，要组成rest，你给我返回有多少种方法。
	public static int process(int index, int rest, int arr[]) {
		if(index == arr.length) {
			return rest == 0 ? 1 : 0;
		}
		// 每个位置都可以选无数张
		int ways = 0;
		for(int zhangs = 0; zhangs * arr[index] <= rest; zhangs++) {
			ways += process(index + 1, rest - zhangs * arr[index], arr);
		}
		return ways;
	}
	
	/**
	 * 动态规划
	 * 可变参数：index和rest
	 * index: 0 ~ N
	 * rest: 0 ~ aim
	 * ==> int dp[N+1][aim+1]
	 */
	public static int dp(int arr[], int aim) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int dp[][] = new int[N + 1][aim + 1];
		dp[N][0] = 1;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				for(int zhangs = 0; zhangs * arr[index] <= rest; zhangs++) {
					ways += dp[index + 1][rest - zhangs * arr[index]];
				}
				dp[index][rest] = ways;
			}
		}
		return dp[0][aim];
	}
	
	/**
	 * 上面的动态规划中，求一个格子的值的时候，有枚举行为，可以继续优化，
	 * 看能不能根据临近位置的格子直接求当前格子的值。
	 */
	public static int dp2(int arr[], int aim) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int dp[][] = new int[N + 1][aim + 1];
		dp[N][0] = 1;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				 // 对于一个普遍位置, 当前格子的值, 等于它右边的值+它下面的值
				dp[index][rest] = dp[index + 1][rest] +
						(rest - arr[index] >= 0 ? dp[index][rest - arr[index]] : 0); 
			}
		}
		return dp[0][aim];
	}
}
