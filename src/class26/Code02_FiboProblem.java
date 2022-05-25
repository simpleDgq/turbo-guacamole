package class26;

public class Code02_FiboProblem {
	/**
	 * 用logN的方法求斐波那契数量的第n项
	 */
	/**
	 * 只要是有严格的递推式的问题，就一定有logN的求解方法。
	 * 有多个递推式，不同的条件使用不同的递推式的问题，就没有logN的方法
	 * |Fn, Fn-1| = |F2, F1|* |base|^(n-2)次方

		base是[ 1 1
	    	   1 0 ]  矩阵
	    
	    如何快速求 base的n-2次方。 判断次方数二进制的每一位，res从单位阵开始，如果二进制是1，
	    res里面就则乘上base, 否则不乘; 同时 base 变成 base * base（二次方递增）。
	 */
	public static int f(int n) {
		if(n == 1 || n == 2) {
			return 1;
		}
		int base[][] = {{1, 1}, 
					    {1, 0}};
		int res[][] =  martixPower(base, n - 2);
		return res[0][0] + res[1][0];
	}
	
	public static int[][] martixPower(int base[][], int p) {
		int N = base.length;
		int M = base[0].length;
		int res[][] = new int[N][M];
		for(int i = 0; i <= N -1; i++) { //res 从单位阵开始
			res[i][i] = 1;
		}
		int t[][] = base; // t从base开始
		for(; p != 0; p >>= 1) {
			if((p & 1) != 0) { // 当前位是1, 则res乘上t
				res = product(res, t);
			}
			t = product(t, t); //t 自己玩，二次方递增
		}
		return res;
	}
	
	// 两个矩阵相乘。有点不好写
	public static int[][] product(int a[][], int b[][]) {
		int N = a.length;
		int M = b[0].length;
		int K = a[0].length;
		int res[][] = new int[N][M];
		for(int i = 0; i <= N - 1; i++) {
			for(int j = 0; j <= M - 1; j++) {
				for(int c = 0; c <= K - 1; c++) {
					res[i][j] += a[i][c] * b[c][j]; // 注意, 这句容易写错(a的列和b的行，下标一定一样)，结果要加起来
				}
			}
		}
		return res;
	}
}
