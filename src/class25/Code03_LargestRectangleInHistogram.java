package class25;

import java.util.Stack;

public class Code03_LargestRectangleInHistogram {
	// 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram
	/**
	 * 给定一个非负数组arr，代表直方图，返回直方图的最大长方形面积
	 */
	
	/**
	 * 思路：遍历数组中的每一个元素，每个元素都作为高，往左右两边扩，能扩的最大长度（下标个数），乘上高，就得到了答案
	*	扩的时候，比它矮的就扩不过去。所有的结果取最大值。
	*  也就是在左边找到比当前元素小的第一个元素，往右找比当前元素小的第一个元素。扩出来的范围就是长方形的宽。
	 */
	public int largestRectangleArea1(int arr[]) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		int N = arr.length;
		Stack<Integer> stack = new Stack<Integer>();
		int max = Integer.MIN_VALUE;
		for(int i = 0; i <= N - 1; i++) {
			// 等于的时候也计算，虽然计算错，相当于只计算了一部分的面积
			// 但是后面相等的元素弹出的时候，往左和往右能扩充到最大的范围，总会计算正确
			while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]) { 
				int j = stack.pop();
				int leftLess = stack.isEmpty() ? -1 :  stack.peek();
				if(leftLess  == -1) {
					max = Math.max(max, arr[j] * i); // 这句可以省略leftLess = - 1，i - leftLess - 1 = i
				} else {
					max = Math.max(max, arr[j] *(i - leftLess - 1));
				}
			}
			stack.push(i);
		}
		while(!stack.isEmpty()) { 
			int j = stack.pop();
			int leftLess = stack.isEmpty() ? -1 :  stack.peek();
			if(leftLess  == -1) {
				max = Math.max(max, arr[j] * N);
			} else {
				max = Math.max(max, arr[j] *(N - leftLess - 1));
			} 
		}
		return max;
 	}
	
	// 数组代替栈
	public int largestRectangleArea(int arr[]) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		int N = arr.length;
//		Stack<Integer> stack = new Stack<Integer>();
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
