package class26;

public class Code05_TieCizhuanProblem {
	/**
	 * 用1*2的瓷砖，把N*2的区域填满，返回铺瓷砖的方法数
	 */
	/**
	 * 思路：
	 * F(n)表示的是干干净净的2*n区域怎么摆放
	 * 两种方式：
	 * 第一块瓷砖，是竖着摆放，就变成了求干干净净的n-1列有多少种摆法的问题
	 * 第二块瓷砖，横着摆，下面只能填横着填一块，就变成了求干干净净的n-2列有多
	 *	少种摆法的问题
	 *
	 *==> n >= 3时, F(n) = F(n - 1) + F(n - 2) ==> 又是斐波那契，base矩阵一样。
	 *n = 1时，有1种方法，只能竖着摆放
	 *n = 2时，2种
	 */
	
	public static int f(int n) {
		if(n <= 0) {
			return 0;
		}
		if(n == 1) {
			return 1;
		}
		if(n == 2) {
			return 2;
		}
		int base[][] = {
				{1, 1},
				{1, 0}
		};
		int res[][] = martixPower(base, n - 2);
		return 2*res[0][0] + res[1][0];
	}
	
	public static int[][] martixPower(int base[][], int p) {
		int N = base.length;
		int M = base[0].length;
		int res[][] = new int[N][M];
		for(int i = 0; i <= N - 1; i++) {
			res[i][i] = 1;
		}
		int t[][] = base;
		for(; p != 0; p >>= 1) {
			if((p & 1) != 0) {
				res = product(res, t);
			}
			t = product(t, t);
		}
		return res;
	}
	
	public static int[][] product(int a[][], int b[][]) {
		int N = a.length;
		int M = b[0].length;
		int K = a[0].length;
		int res[][] = new int[N][M];
		
		for(int i = 0; i <= N - 1; i++) {
			for(int j = 0; j <= M - 1; j++) {
				for(int c = 0; c <= K - 1; c++) {
					res[i][j] += a[i][c] * b[c][j];
				}
			}
		}
		return res;
	}
}
