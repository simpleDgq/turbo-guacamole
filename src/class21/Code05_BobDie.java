package class21;

public class Code05_BobDie {
	/**
	 * 给定5个参数，N，M，row，col，k
		表示在N*M的区域上，醉汉Bob初始在(row,col)位置
		Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
		任何时候Bob只要离开N*M的区域，就直接死亡
		返回k步之后，Bob还在N*M的区域的概率
	 */
	/*
	 * 在一个位置都有4个不同的方法可以走，走K步，总共走法是4^K.
	 * 走完K步之后，如果还在棋盘里面，搜集生存的点数，除以总数，就是要求的概率
	 */
	public static double way(int row, int col, int K, int N, int M) {
		double total = Math.pow(4, K);
		double lives = process(row, col, K, N, M);
		return lives / total;
	}
	// 当前来到(rol,col)位置，还剩rest步可以走，给我返回活着的点数、
	public static int process(int row, int col, int rest, int N, int M) {
		if(row < 0 || row > N - 1 || col < 0 || col > M - 1) {
			return 0;
		}
		if(rest == 0) {
			return 1;
		}
		// 普遍位置
		return process(row - 1, col, rest - 1, N, M) + 
		process(row, col -1, rest - 1, N, M) +
		process(row + 1, col, rest - 1, N, M) +
		process(row, col + 1, rest - 1, N, M);
	}
	
	
	/**
	 * 动态规划
	 * 可变参数，row,col, rest
	 * 范围: row: 0~N-1 col: 0~M-1 rest: 0~K ==> int dp[N][M][K+1] 
	 */
	public static double dp(int row, int col, int K, int N, int M) {
		int dp[][][] = new int[N][M][K+1];
		for(int i = 0; i <= N - 1; i++) {
			for(int j = 0; j <= M - 1; j++) {
				dp[i][j][0] = 1; // if(rest == 0) { return 1; }
			}
		}
		// 三维动态规划，从下往上一层层搞定
		for(int rest = 1; rest <= K; rest++ ) {
			for(int i = 0; i <= N - 1; i++) {
				for(int j = 0; j <= M - 1; j++) {
					 int ways = pick(dp, i - 1, j, rest - 1, N, M) + 
							pick(dp, i, j -1, rest - 1, N, M) +
							pick(dp, i + 1, j, rest - 1, N, M) +
							pick(dp, i, j + 1, rest - 1, N, M);
					 dp[i][j][rest] = ways;
				}
			}
		}
		return (double) dp[row][col][K] / Math.pow(4, K);
	}
	public static int pick(int dp[][][], int row, int col, int rest, int N, int M) {
		if(row < 0 || row > N - 1 || col < 0 || col > M - 1) {
			return 0;
		}
		return dp[row][col][rest];
	}
	
	public static void main(String[] args) {
		System.out.println(way(6, 6, 10, 50, 50));
		System.out.println(dp(6, 6, 10, 50, 50));
	}

}
