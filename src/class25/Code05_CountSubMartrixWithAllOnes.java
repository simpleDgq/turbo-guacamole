package class25;

//测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
public class Code05_CountSubMartrixWithAllOnes {
	/**
	 * 统计全是1的子矩形的所有数量
	 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的子矩形数量、
	 * 思路: 求必须以第0行为底的矩阵，有多少个子矩形
		求必须以第1行为底的矩阵，有多少个子矩形，
		....
		所有的结果累加，就是答案
		
		====
		同样也是每一行累加构成直方图，然后每一行的直方图去求数量.
		求比弹出栈顶的元素小的左边的第一个数i，和右边比它小的第一个数k，构成的范围内，所有的矩形数量
		(k - i - 1) ==> 构成的范围长度，记成L
	 */
	
	public int maxMartix(int martix[][]) {
		if(martix == null || martix.length == 0 || martix[0] == null || martix[0].length == 0) {
			return 0;
		}
		int N = martix.length;
		int M = martix[0].length;
		int height[] = new int[M];
		int nums = 0;
		for(int i = 0; i <= N - 1; i++) {
			for(int j = 0; j <= M - 1; j++) {
				height[j] = martix[i][j] != 0 ? height[j] + 1 : 0;
			}
			nums += countFromBottom(height);
		}
		return nums;
	}
	
	public int countFromBottom(int height[]) {
		int nums = 0;
		int N = height.length;
//		Stack<Integer> stack = new Stack<Integer>();
		int stack[] = new int[N];
		int size = 0;
		for(int i = 0; i <= N - 1; i++) {
			while(size != 0 && height[stack[size - 1]] >= height[i] ) { // 等于的时候弹出，但是不计算答案
				int j = stack[--size];
				if(height[j] > height[i]) { // 只有大于的时候计算答案
					int leftLess = size == 0 ? -1 : stack[size - 1];
					if(leftLess == -1) {
						nums += num(i) * (height[j] - height[i]);
					} else {
						nums += num(i - leftLess - 1) * (height[j] - Math.max(height[leftLess], height[i]));
					}
				}
			}
			stack[size++] = i;
		}
		while(size != 0) {
			int j = stack[--size];
			int leftLess = size == 0 ? -1 : stack[size - 1];
			if(leftLess == -1) { // 容易写出，既没有左边比它小的，又没有右边比它小的。需要计算整个数组范围。
				nums += num(N) * (height[j]);
			} else {
				nums += num(N - leftLess - 1) * (height[j] - height[leftLess]);
			}
		}
		return nums;
	}
	
	public int num(int L) {
		return ((L + 1) * L) >> 1;
	}

}
