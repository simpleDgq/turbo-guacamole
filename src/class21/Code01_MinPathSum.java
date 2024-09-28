package class21;

public class Code01_MinPathSum {

	/***
	 * 	给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
		沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
		返回最小距离累加和
	 */
    /**
     * dp[i][j]: 到达i,j位置，最小路径和是多少
     * 1. 最小路径和来自于左边
     * 2. 最小路径和来自于上边
     * 两者取最小值 + 当前数就是dp[i][j]的答案
     */
    public int minPathSum1(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int M = grid.length;
        int N = grid[0].length;
        int dp[][] = new int[M][N];
        dp[0][0] = grid[0][0]; // 到达i,j位置，最小路径和是grid[0][0]
        // 填好第一行
        for (int j = 1; j <= N - 1; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];// 只能由左边过来
        }
        // 填好第一列
        for (int i = 1; i <= M - 1; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0]; // 只能由上面过来
        }
        // 从上往下，从左往右填
        for (int i = 1; i <= M - 1; i++) {
            for (int j = 1; j <= N - 1; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]; // 左边或者上面取最小值
            }
        }
        return dp[M - 1][N - 1];
    }
    /**
     * 生成了原始规模的矩阵，浪费空间，只依赖左上元素，
     * 可以用一维数组。每次自动更新一维数组，表示dp表的每一行，
     * 最终一维数组的最后一个元素就是答案。
     * 空间压缩技巧。
     */
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int M = grid.length;
        int N = grid[0].length;
        int dp[] = new int[N];
        dp[0] = grid[0][0]; // 到达i,j位置，最小路径和是grid[0][0]
        // 填好第一行
        for (int j = 1; j <= N - 1; j++) {
            dp[j] = dp[j - 1] + grid[0][j];// 只能由左边过来
        }
        for (int i = 1; i <= M - 1; i++) {
            // 考虑更新第i行的数据， 第i行的第一个数据值依赖它上面的数据，也就是arr[0]
            dp[0] = dp[0] + grid[i][0]; // 这里容易忘
            for (int j = 1; j <= N - 1; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j]; // 左边或者上面去最小值
            }
        }
        return dp[N - 1];
    }
}
