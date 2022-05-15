package class21;

public class Code02_CoinsWithEveryPaperDifferent {
	/**
	 * 	arr是货币数组，其中的值都是正数。再给定一个正数aim。
		每个值都认为是一张货币，
		即便是值相同的货币也认为每一张都是不同的，
		返回组成aim的方法数
		例如：arr = {1,1,1}，aim = 2
		第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
		一共就3种方法，所以返回3
	 */
	public static int ways(int arr[], int aim) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(0, aim, arr);
	}
	// 从左往右尝试模型
	// 当前来到index位置，要组成rest这个数，你给我返回有多少种方法
	public static int process(int index, int rest, int arr[]) {
		if(rest < 0) { // 前面的决策出了问题
			return 0;
		}
		if(index == arr.length) { // 没有货币了，如果rest = 0, 说明前面的决定组成了一种方法，返回1
			return rest == 0 ? 1: 0;
		}
		// 不选index和选index位置
		return process(index + 1, rest, arr) + process(index + 1, rest - arr[index], arr);
	}
	
	/**
	 *  动态规划
	 *  可变参数index和rest
	 *  index: 0 ~ N
	 *  rest: 0 ~ aim
	 *  ==> int dp[N+1][aim+1]
	 *  
	 *  分析依赖： process(index + 1, rest, arr) + process(index + 1, rest - arr[index], arr);
	 *  index,rest 位置依赖index+1,rest 以及 index+1,rest-arr[index]位置，只依赖下一行
	 *  ==> 从下往上,从左往右填表
	 */
	public static int dp(int arr[], int aim) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int dp[][] = new int[N+1][aim+1];
		dp[N][0] = 1;// base case
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				dp[index][rest] = dp[index + 1][rest] + 
				((rest - arr[index] >= 0) ? dp[index + 1][rest - arr[index]] : 0);
			}
		}
		return dp[0][aim];
	}
}
