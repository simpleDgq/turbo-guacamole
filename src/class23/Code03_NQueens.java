package class23;

public class Code03_NQueens {
	/**
	 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
		要求任何两个皇后不同行、不同列，&nbsp;也不在同一条斜线上
		给定一个整数n，返回n皇后的摆法有多少种。N=1，返回1
		N=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
		N=8，返回92
	 */
	public static int way(int n) {
		if (n < 1) {
			return 0;
		}
		int[] record = new int[n];
		return process(record, 0, n);
	}
	// 当前在i行上放皇后，前面的皇后放的位置记录在了record中，要保证皇后
	// 不打架，你给我返回i本身及其往后有多少种方法
	public static int process(int record[], int i, int n) {
		if(i == n) { // 放到了第n行，说明已经放完了，前面的方法是一种成功的方法，直接返回
			return 1;
		}
		// i放在那一列。 0~n-1全部尝试一遍
		int res = 0;
		for(int j = 0; j <= n - 1; j++) {
			if(isValid(record, i, j)) { // 如果放在i行j列不打架
				record[i] = j;
				res += process(record, i + 1, n);
			}
		}
		return res;
	}
	public static boolean isValid(int record[], int i, int j) {
		// 和前面行的皇后比较,看是否在同一列或者在同一条斜线上
		for(int k = 0; k <= i - 1; k++) {
			if(j == record[k] || Math.abs(i - k) == Math.abs(j - record[k])) {
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		int n = 8;

		long start = System.currentTimeMillis();
		System.out.println(way(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");
	}
}
