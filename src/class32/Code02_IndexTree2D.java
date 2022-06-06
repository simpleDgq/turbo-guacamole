package class32;

public class Code02_IndexTree2D {
	
	/**
	 * indexTree 推到二维非常容易
		help[i][j]: 原数组中以1行1列做左上角, i行j列做右下角这一整块的累加和这个值填在i,j这个格子里
		
		给你一个 2D 矩阵 matrix，请计算出从左上角 (row1, col1) 到右下角 (row2, col2) 组成的矩形中所有元素的和。
		上述粉色矩形框内的，该矩形由左上角 (row1, col1) = (2, 1) 和右下角 (row2, col2) = (4, 3) 确定。其中，所包括的元素总和 sum = 8。
		示例：
		
		给定 matrix = [
		[3, 0, 1, 4, 2],
		[5, 6, 3, 2, 1],
		[1, 2, 0, 1, 5],
		[4, 1, 0, 1, 7],
		[1, 0, 3, 0, 5]
		]
		
		sumRegion(2, 1, 4, 3) -> 8
		update(3, 2, 2)
		sumRegion(2, 1, 4, 3) -> 10
		注意:
		
		矩阵 matrix 的值只能通过 update 函数来进行修改 你可以默认 update 函数和 sumRegion 函数的调用次数是均匀分布的 你可以默认 row1 ≤ row2，col1 ≤ col2
	 */
	
	public class IndexTree2D {
		private int N; // 行
		private int M; // 列
		private int tree[][];
		private int nums[][]; // 记录矩阵元素
		
		public IndexTree2D(int matrix[][]) {
			N = matrix.length;
			M = matrix[0].length;
			nums = new int[N][M];
			tree = new int[N + 1][M + 1]; // 0行0列不用
			for (int i = 0; i <= N - 1; i++) {
				for (int j = 0; j <= M - 1; j++) {
					update(i, j, matrix[i][j]);
				}
			}
		}
		
		// i,j 位置的数更新成value
		public void update(int row, int col, int value) {
			if(row > N || col > M) {
				return;
			}
			// 计算要加的值 更新成value，相当于原先的值加上 value - 原先的值
			int add = value - nums[row][col];
			// 记录matrix值
			nums[row][col] = value;
			for(int i = row + 1; i <= N; i += i & (-i)) { // 0行和0列是不用的，所以从+1开始
				for(int j = col + 1; j <= M; j += j & (-j)) {
					tree[row][col] += add;
				}
			}
		}
		
		// 求1,1 到row,col的累加和
		public int sum(int row, int col) {
			if(row > N || col > M) {
				return 0;
			}
			int sum = 0;
			for(int i = row + 1; i > 0; i -= i & (-i)) { // 0行和0列是不用的，所以从+1开始
				for(int j = col + 1; j > 0 ; j -= j & (-j)) {
					sum += tree[row][col];
				}
			}
			return sum;
		}
		
		// 求任意范围上的累加和
		public int sumRegion(int row1, int col1, int row2, int col2) { 
			if(row1 > N || col1 > M || row2 > N || col2 > M) {
				return 0;
			}
			return sum(row2, col2) - sum(row2, col1 - 1) - sum(row1 - 1, col2) + sum(row1 - 1, col1 - 1);
		}
	}

}
