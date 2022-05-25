package class26;

public class Code04_ZeroLeftOneStringNum {
	/**
	 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
		如果某个字符串，任何0字符的左边都有1紧挨着，认为这个字符串达标
		返回有多少达标的字符串
	 */
	/**
	 * N = 1, 1种达标，就只有字符'1'
	 * N = 2, 2种情况达标，'10', '11'
	 * N = 3, 3种情况达标，'101', '110', '111'
	 * N = 4, 5种情况达标，'1010' '1011' '1101' '1110' '1111'
	 * N = 5, 8种情况达标，'10101' '10110' '10111' '11010' '11011' '11101' '11110' '11111'
		===>  n >= 2时, F(n) = F(n - 1) + f(n - 2) ==> 二阶严格递推式问题
		|Fn, Fn-1| = |F2, F1| * |base|^(n - 2) base是2*2矩阵 ==> Fn = 2*res[0][0] + res[1][0]
		
	*	求base:
	*	n = 3 时, |F3, F2| = |F2, F1| * |a,b|
	*								   |c,d|
	*		2a + c = 3;
	*		2b + d = 2;					   
	*   n = 4 时, |F4, F3| = |F2, F1| * |a,b| ^ (2)
	*								   |c,d|
	*
	*   |5, 3| = |2, 1| * |a,b| ^ (2)
	*					  |c,d|
	*	==>	草稿纸算去吧！						
	*   最终结果为: 其实这也是斐波那契，只不过1,2项不一样。base矩阵是一样的。记不住就自己算。
	*   |1, 1|
	*   |1, 0|
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
	
	// 递归用于验证
	public static int process(int n) {
		if(n <= 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		if (2 == n) {
			return 2;
		}
		return process(n - 1) + process(n - 2);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i != 20; i++) {
			System.out.println(f(i));
			System.out.println(process(i));
			System.out.println("===================");
		}

	}
}
