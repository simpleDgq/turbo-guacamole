package class40;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/spiral-matrix
public class Code05_PrintMatrixSpiralOrder {

	public List<Integer> spiralOrder(int[][] matrix) {
		/**
		 * 顺时针打印矩阵的顺序是 “从左向右、从上向下、从右向左、从下向上” 循环。 
		 *  搞上下左右4个边界，每次打印完边界都要缩
		 * 
		 * 看leetcode题解，有图容易理解
		 */
		List<Integer> ans = new ArrayList<Integer>();
		if (matrix == null || matrix.length == 0) {
			return ans;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		// 4个边界变量
		int top = 0;
		int bottom = N - 1;
		int left = 0;
		int right = M - 1;

		while (true) {
			// 从左到右打印
			for (int i = left; i <= right; i++) {
				ans.add(matrix[top][i]);
			}
			// 从左往右打完一行，上边界往下缩
			if (++top > bottom) {
				break;
			}
			// 从上到下打印
			for (int i = top; i <= bottom; i++) {
				ans.add(matrix[i][right]);
			}
			// 从上到下打完一列，这一列打完了，右边界往左缩
			if (--right < left) {
				break;
			}
			// 从右往左打印
			for (int i = right; i >= left; i--) {
				ans.add(matrix[bottom][i]);
			}
			// 从右往左打印打完一行，这一行打完了，下边界往上缩
			if (--bottom < top) {
				break;
			}
			// 从下往上打印
			for (int i = bottom; i >= top; i--) {
				ans.add(matrix[i][left]);
			}
			// 从下往上打印一列，这一列打完了，左边界往右缩
			if (++left > right) {
				break;
			}
		}
		return ans;
	}

}
