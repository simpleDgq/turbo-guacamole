package class21;

public class Code01_MinPathSum {

	/***
	 * 	给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
		沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
		返回最小距离累加和
	 */
	public static int minPathSum(int arr[][]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int M = arr[0].length;
		int dp[][] = new int[N][M];
		
		// 第一行，只能由左边的元素往右走得到，所有只依赖右边
		dp[0][0] = arr[0][0];
		for(int i = 1; i <= M - 1; i++) {
			dp[0][i] = dp[0][i - 1] + arr[0][i];
		}
		// 第一列，只能由上边的元素往下走得到，所有只依赖上边
		for(int j = 1; j <= N - 1; j++) {
			dp[j][0] = dp[j - 1][0] + arr[j][0];
		}
		// 其它位置
		for(int i = 1; i <= N - 1; i++) {
			for(int j = 1; j <= M - 1; j++) {
				dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + arr[i][j];
			}
		}
		return dp[N - 1][N - 1];
	}
	
	/**
	 * 生成了原始规模的矩阵，浪费空间，只依赖左上元素，
	 * 可以用一维数组。每次自动更新一维数组，表示dp表的每一行，
	 * 最终一维数组的最后一个元素就是答案。
	 * 空间压缩技巧。
	 */
	public static int minPathSum2(int matrix[][]) {
		if(matrix == null || matrix.length == 0) {
			return 0;
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int arr[] = new int[col];
		
		// 第一行
		arr[0] = matrix[0][0];
		for(int i = 1; i <= col - 1; i++) {
			arr[i] = arr[i - 1] + matrix[0][i];
		}
		// 考虑更新第二行的数据， 第二行的第一个数据值依赖它上面的数据，也就是arr[0]
		for(int i = 1; i <= row - 1; i++) {
			arr[0] = arr[0] + matrix[i][0];
			for(int j = 1; j <= col - 1; j++) {
				arr[j] = Math.min(arr[j - 1], arr[j]) + matrix[i][j];
			}
		}
		return arr[col - 1];
	}
}
