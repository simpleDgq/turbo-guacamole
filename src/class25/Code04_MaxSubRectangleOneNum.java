package class25;

public class Code04_MaxSubRectangleOneNum {
	// 测试链接：https://leetcode.com/problems/maximal-rectangle/
	/**
	 * 最大子矩形1个个数
	 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩形内部有多少个1（面积）
	（每年都考，德高望重）
	 */
	/**
	 * 思路:
	 * 每一行作为地基，累加上上一行，是0就归0，构成所有的直方图数组，求各个直方图数组里面，
	   每个元素都作为高，能够往左和往右扩的最大长度，乘上高，得出面积。最终取各个直方图数组
	   能够得到的面积的最大值。
	 */
	public int maximalRectangle(char[][] map) {
		if(map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
			return 0;
		}
		int M = map[0].length;
		int N = map.length;
		int height[] = new int[M]; // 一行直方图数据
		int max = 0;
		for(int i = 0; i <= N - 1; i ++) {
			for(int j = 0; j <= M - 1; j++) {
				char cur = map[i][j];
				height[j] = cur != '0' ? height[j] + 1 : 0;
			}
			max = Math.max(max, largestRectangleArea(height));
		}
		return max;
	}
	
	public int largestRectangleArea(int arr[]) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		int N = arr.length;
		int stack[] = new int[N];
		int size = 0;
		int max = Integer.MIN_VALUE;
		for(int i = 0; i <= N - 1; i++) {
			// 等于的时候也计算，虽然计算错，相当于只计算了一部分的面积
			// 但是后面相等的元素弹出的时候，往左和往右能扩充到最大的范围，总会计算正确
			while(size != 0 && arr[stack[size - 1]] >= arr[i]) { 
				int j = stack[--size];
				int leftLess = size == 0 ? -1 : stack[size - 1];
				max = Math.max(max, arr[j] *(i - leftLess - 1));
			}
			stack[size++] = i;
		}
		while(size != 0) { 
			int j = stack[--size];
			int leftLess = size == 0 ? -1 : stack[size - 1];
			max = Math.max(max, arr[j] *(N - leftLess - 1));
		}
		return max;
 	}
}
