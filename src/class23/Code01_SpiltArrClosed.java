package class23;

public class Code01_SpiltArrClosed {
	/**
	 * 给定一个正数数组arr，
	请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近。
	返回最接近的情况下，较小集合(个数少的那个)的累加和
	 */
	// 数组划分成两个集合，最接近的情况下，一定都接近总的累加和的一半
	public static int way(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			sum += arr[i];
		}
		return process(0, sum / 2, arr);
	}
	// 当前来到index位置，index及其之后的数可以随便选择，
	// 要凑出累加和尽量接近rest，但不能超过rest的情况下，最接近rest的累加和是多少
	public static int process(int index, int rest, int arr[]) {
		if(index == arr.length) { // 没有数了，凑不了rest，最小累加和就是0
			return 0;
		}
		// 不选index位置
		int p1 = process(index + 1, rest, arr);
		// 选index位置
		int p2 = 0;
		if(arr[index] <= rest) {
			p2 = arr[index] + process(index + 1, rest - arr[index], arr);
		}
		return Math.max(p1, p2);	
	}
	
	/**
	 * 动态规划
	 * 可变参数index rest 范围：index： 0~N rest： 0~ sum/2 ==> int dp[N+1][sum/2] + 1
	 */
	public static int dp(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		int N = arr.length;
		for(int i = 0; i <= N - 1; i++) {
			sum += arr[i];
		}
		int dp[][] = new int[N + 1][sum / 2 + 1];
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= sum / 2; rest++) {
				// 不选index位置
				int p1 = dp[index + 1][rest];
				// 选index位置
				int p2 = 0;
				if(arr[index] <= rest) {
					p2 = arr[index] + dp[index + 1][rest - arr[index]];
				}
				dp[index][rest] = Math.max(p1, p2);
			}
		}
		return dp[0][sum / 2];
	}
}
