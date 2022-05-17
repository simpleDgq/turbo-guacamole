package class22;

public class Code03_SplitNumber {
	/**
	 * 给定一个正数n，求n的裂开方法数，
		规定：后面的数不能比前面的数小
		比如4的裂开方法有：
		1+1+1+1、1+1+2、1+3、2+2、4
		5种，所以返回5
	 * 
	 */
	public static int way(int n) {
		if(n < 0) {
			return 0;
		}
		return process(1, n);
	}
	// 前一个拆出来的数是pre, 剩下的是rest, 你给我返回有多少种拆解方法
	public static int process(int pre, int rest) {
		if(rest == 0) {// 拆解完了，说明前面的拆分是一种有效的方法
			return 1;
		}
		if(pre > rest) { // 后面的不能小于前面的
			return 0;
		}
		// pre < rest的情况
		int ways = 0;
		for(int i = pre; i <= rest; i++) { // 从pre到rest进行尝试
			ways += process(i, rest - i);
		}
		return ways;
	}
	
	/**
	 * 动态规划
	 * 可变参数：pre和rest 范围：pre：1~n rest：0~n ==> int dp[n][n+1] ==> 第一行不用
	 * 
	 */
	public static int dp(int n) {
		if(n < 0) {
			return 0;
		}
		int dp[][] = new int[n + 1][n + 1];
		for(int i = 1; i <= n; i++) {
			dp[i][0] = 1; // 第一列全是1
			dp[i][i] = 1; // 对角线也全是1
		}
		// 从下往上，从左往右依次填剩下的格子
		for(int pre = n - 1; pre >= 1; pre--) {
			for(int rest = pre + 1; rest <= n; rest++) {
				int ways = 0;
				for(int i = pre; i <= rest; i++) { // 从pre到rest进行尝试
					ways += dp[i][rest - i];
				}
				dp[pre][rest] = ways;
			}
		}
		return dp[1][n];
	}
	
	/**
	 * 优化枚举行为
	 * 
	 * 举实际的例子，来分析依赖。pre,rest 依赖它下面的元素加上他自己左边的某个元素。
	 * dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre]
	 */
	// 分析依赖，举几个例子。
	public static int dp2(int n) {
		if(n < 0) {
			return 0;
		}
		int dp[][] = new int[n + 1][n + 1];
		for(int i = 1; i <= n; i++) {
			dp[i][0] = 1; // 第一列全是1
			dp[i][i] = 1; // 对角线也全是1
		}
		// 从下往上，从左往右依次填剩下的格子
		for(int pre = n - 1; pre >= 1; pre--) {
			for(int rest = pre + 1; rest <= n; rest++) {
				dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre];
			}
		}
		return dp[1][n];
	}
	public static void main(String args[]) {
		int n = 7;
		System.out.println(way(n));
		System.out.println(dp(n));
		System.out.println(dp2(n));
	}
}
