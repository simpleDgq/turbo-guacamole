package class18;

public class Code01_RobotWalk {
	
	/**
	 * 假设有排成一行的N个位置记为1~N，N一定大于或等于2
	 * 开始时机器人在其中的M位置上(M一定是1~N中的一个)
	 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
	 * 如果机器人来到N位置，那么下一步只能往左来到N-1位置；
	 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走
	 * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
	 * 给定四个参数 N、M、K、P，返回方法数
	 */
	//1. 暴力递归 --> 尝试
	public static int ways1(int P, int K, int M, int N) {
		if(P <= 0 || P > N || K <= 0 || M <= 0 || M > N || N < 2 ) {
			return -1;
		}
		return process(P, K, M, N);
	}
	
	// aim 要走到的位置
	// rest 剩余的要走的步数
	// cur 当前来到的位置
	// 有哪些位置 1~N
	// 当前机器人在cur位置，还有rest步要走，要走到aim位置，给我返回有多少种走法
	public static int process(int aim, int rest, int cur, int N) {
		if(rest == 0) { // 剩余0步，到达目标，返回1，没有到达，返回0
			return aim == cur? 1 : 0;
		}
		if(cur == 1) { // 当前在1，下一步只能走向2，递归求方法数
			return process(aim, rest - 1, 2, N);
		}
		if(cur == N) { // 当前在N，下一步只能走向N - 1，递归求方法数
			return process(aim, rest - 1, N - 1, N);
		}
		// 其它位置，可以向左或者向右走，两者累加就是答案
		return process(aim, rest - 1, cur - 1, N) + //向左
				process(aim, rest - 1, cur + 1, N);  // 向右
	}
	
	// 2. 傻缓存
	// 找重复解，分析可变参数及其范围
	// 递归函数中，只有cur和rest是可变的，只有它们会影响递归函数执行的结果
	// cur的范围是1~N，rest的范围是0~K ==> 建一个dp[N + 1][K + 1] , 能存下所有可能的结果
	public static int ways2(int P, int K, int M, int N) {
		if(P <= 0 || P > N || K <= 0 || M <= 0 || M > N || N < 2 ) {
			return -1;
		}
		// 0表示不能到达目标位置
		// -1 表示还没有计算过
		// 大于0的数，表示有多少种方法能够到达目标位置
		int dp[][] = new int[N + 1][K + 1];
		for(int i = 0; i<= N; i++) {
			for(int j = 0; j <= K; j++) {
				// 初始化为-1，表示当前没有缓存值，需要计算
				dp[i][j] = -1;
			}
		}
		return process(P, K, M, N, dp);
	}
	
	// aim 要走到的位置
	// rest 剩余的步数
	// cur 当前在哪
	// 有哪些位置 1~N
	// 当前机器人在cur位置，还有rest步能走，要走到aim位置，给我返回有多少中走法
	public static int process(int aim, int rest, int cur, int N, int dp[][]) {
		if(dp[cur][rest] != -1) { // 缓存有，直接取
			return dp[cur][rest];
		}
		int ans = 0;
		if(rest == 0) { // 剩余0步，到达目标，返回1，没有到达，返回0
			ans = aim == cur? 1 : 0;
		} else if(cur == 1) { // 当前在1，下一步只能走向2，递归求方法数
			ans = process(aim, rest - 1, 2, N);
		} else if(cur == N) { // 当前在N，下一步只能走向N - 1，递归求方法数
			ans = process(aim, rest - 1, N - 1, N);
		} else {
			// 其它位置，可以向左或者向右走
			ans = process(aim, rest - 1, cur - 1, N) + //向左
				process(aim, rest - 1, cur + 1, N);  // 向右
		}
		dp[cur][rest] = ans; // 缓存起来，下次用 
		return ans;
	}
	
	// 3. 动态规划，画出dp表，根据暴力递归进行分析
	public static int ways3(int P, int K, int M, int N) {
		if(P <= 0 || P > N || K <= 0 || M <= 0 || M > N || N < 2 ) {
			return -1;
		}
		int dp[][] = new int[N + 1][K + 1];
		// 根据basecase，填好第一列。只有cur=aim的时候才是1，其它都是0 --> 这里有启发，后面按照列填
		dp[P][0] = 1;
		// 一列一列填
		for(int rest = 1; rest <= K; rest++) { // 按列生成
			// 填好第一行。依赖左下位置
			dp[1][rest] = dp[2][rest - 1]; 
			// 中间行。依赖左上和左下位置
			for(int cur = 2; cur <= N - 1; cur++) {
				dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
			}
			// 填好第N行。依赖左上位置
			dp[N][rest] = dp[N - 1][rest - 1];
		}
		return dp[M][K];
	}
	
	public static void main(String args[]) {
		int res = ways1(4, 6, 2, 5);
		int res2 = ways2(4, 6, 2, 5);
		int res3 = ways3(4, 6, 2, 5);
		System.out.println(res);
		System.out.println(res2);
		System.out.println(res3);
	}
	
}
