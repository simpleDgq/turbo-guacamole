package class22;

public class Code01_KillMonster {
	/**
	 * 给定3个参数，N，M，K
		怪兽有N滴血，等着英雄来砍自己
		英雄每一次打击，都会让怪兽流失[0~M]的血量
		到底流失多少？每一次在[0~M]上等概率的获得一个值
		求K次打击之后，英雄把怪兽砍死的概率
	 */
	/*
	 * 每看一刀，都有M + 1个分支，所以所有的可能性是(M+1)^K。
	 * 求出砍了K到之后，怪兽被砍死的所有可能性，除以(M+1)^K，就是答案。
	 */
	public static double way(int N, int M, int K) {
		if (N < 1 || M < 1 || K < 1) {
			return 0;
		}
		double total = Math.pow(M + 1, K);
		double dies = process(K, M, N);
		return dies / total;
	}
	// 还剩times到可以砍，每一次的伤害范围在0~M上，还剩hp滴血，给我返回怪兽被砍死的所有可能性。
	public static long process(int times, int M, int hp) {
		if(times == 0) { // 没有刀数可用了,并且hp低于0, 说明被砍死了
			return hp <= 0 ? 1 : 0;
		}
		if (hp <= 0) { // 当血量小于等于0的时候，剩余的times刀，砍死的次数就是(M+1)^times
			return (long) Math.pow(M + 1, times); // 小于等于0了，剩下的times刀，每刀有M+1种情况
		}
		// 普遍情况
		int ways = 0;
		for(int m = 0; m <= M; m++) { // 每一刀砍0~M的血量，都去尝试一遍
			ways += process(times - 1, M, hp - m);
		}
		return ways;
	}
	
	/**
	 * 动态规划
	 * 可变参数：times和hp 范围: times: 0~K, hp: 0~N ==> long dp[K+1][N+1]
	 */
	public static double dp(int N, int M, int K) {
		if (N < 1 || M < 1 || K < 1) {
			return 0;
		}
		long dp[][] = new long[K+1][N+1];
		dp[0][0] = 1;
		for(int times = 1; times <= K; times++) {
//			dp[times][0] = (long) Math.pow(M + 1, times); // 注意这里。 第一列。 血量为0。砍times刀。方法为(M+1)^times种
			for(int hp = 0; hp <= N; hp++) { // 如果写上面一句，这里就该从第2列开始。不写也是对的
				long ways = 0;
				for(int m = 0; m <= M; m++) { // 每一刀砍0~M的血量，都去尝试一遍
					if(hp - m <= 0) {
						ways += (long) Math.pow(M + 1, times - 1);
					} else {
						ways += dp[times - 1][hp - m];
					}
				}
				dp[times][hp] = ways;
			}
		}
		return dp[K][N] / Math.pow(M + 1, K);
	}
	
	/**
	 * 优化枚举行为
	 *  
	 */
	public static double dp2(int N, int M, int K) {
		if (N < 1 || M < 1 || K < 1) {
			return 0;
		}
		long dp[][] = new long[K+1][N+1];
		dp[0][0] = 1;
		for(int times = 1; times <= K; times++) {
			dp[times][0] = (long) Math.pow(M + 1, times); // 注意这里。 第一列。 血量为0。砍times刀。方法为(M+1)^times种
			for(int hp = 1; hp <= N; hp++) {
				dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
				if (hp - 1 - M >= 0) {
					dp[times][hp] -= dp[times - 1][hp - 1 - M];
				} else { // 这句注意：拿不到格子的时候，依然得减掉，公式决定。
					dp[times][hp] -= Math.pow(M + 1, times - 1);
				}
			}
		}
		return dp[K][N] / Math.pow(M + 1, K);
	}
}
