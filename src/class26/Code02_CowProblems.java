package class26;

public class Code02_CowProblems {
	/**
	 * 奶牛生小牛问题
		第一年农场有1只成熟的母牛A，往后的每年：
		1）每一只成熟的母牛都会生一只母牛
		2）每一只新出生的母牛都在出生的第三年成熟
		3）每一只母牛永远不会死
		返回N年后牛的数量
		（微软，2面第二题）
	 */
	/**
	 * (一) (二) (三) (四) (五)
	 *  A   A	A    A    A
	 *  1   B	B	 B    B
	 *      2	C    C    C
	 *          3    D    D
	 *          	 4    E
	 *                    F
	 *                    6 
	 * 观察法: F(1) = 1, F(2) = 2,  F(3) = 3,  F(4) = 4, F(5) = 6
	 * ==> n>=3时; F(n) = F(n - 1) + f(n - 3)
	 * 严格递推式，logN求，三阶递推式 ==> base是3*3的
	 * |Fn, Fn-1, Fn-2| = |F3, F2, F1| * |base|^(n-3) ==> ans = 3 * res[0][0] + 2*res[1][0] + res[2][0]
	 * 1. 求base矩阵
	 * 
	 * n = 4, |4, 3, 2| = |3, 2, 1| * |a, b, c|
	 * 								  |d, e, f|
	 * 								  |g, h, j|
	 * 
	 * ==> 3a + 2d + g = 4;
	 *     3b + 2e + h = 3;
	 *     3c + 2f + j = 2;
	 * n = 5时，又可以得到一组方程。这里为了方便，就不列了. 直接给出base
	 * 
	 * | 1 1 0|
	 * | 0 0 1|
	 * | 1 0 0|  
	 *  
	 */
	public static int f(int n) {
		if(n <= 0) {
			return 0;
		}
		if(n == 1 || n == 2 || n == 3) {
			return n;
		}
		int base[][] = {
				{1, 1, 0},
				{0, 0, 1},
				{1, 0, 0}
		};
		int res[][] = martixPower(base, n - 3);
		return 3 * res[0][0] + 2*res[1][0] + res[2][0];
	}
	
	public static int[][] martixPower(int base[][] , int p) {
		int N = base.length;
		int M = base[0].length;
		int res[][] = new int[N][M];
		for(int i = 0; i <= N - 1; i++) {
			res[i][i] = 1; // 从单位阵开始
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
