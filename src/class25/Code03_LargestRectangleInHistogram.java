package class25;

import java.util.Stack;

public class Code03_LargestRectangleInHistogram {
	// 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram
	/**
	 * 给定一个非负数组arr，代表直方图，返回直方图的最大长方形面积
	 */

    /**
     * 必须以3作为高，左右能够扩多远，必须以1为高，左右能扩多远....
     * 每个元素都作为高，往左右两边扩，能扩的最大长度（下标个数），乘上高，就得到了答案; 扩的时候，比它矮的就扩不过去.
     * 
     * 也就是在左边找到比当前元素小的第一个柱子，往右找比当前元素小的第一个柱子,这两根柱子之间（不包括其本身），就是能够扩
     * 到的最大宽，乘上高就是答案
     */
    public int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int N = heights.length;
        Stack<Integer> stack = new Stack<Integer>(); // 可以用数组替代栈
        int max = 0;
        // 每一个位置的元素，求它左边第一个比它小的元素，以及右边比它小的元素
        for (int i = 0; i <= N - 1; i++) {
            int cur = heights[i];
            // 如果栈不为空，而且当前元素比栈顶元素小，则一直出栈，出栈的时候搜集答案
            // 这里只写小于，答案也正确。等于的时候，没办法求出正确的右边界，所以可能求出当前这个重复
            // 的元素求出的答案可能不对，但是后面重复的元素弹出的时候是可以求出正确的左右边界的
            while ((!stack.isEmpty()) && (cur <= heights[stack.peek()])) { 
                int pop = stack.pop();
                // 压着的数是左边第一个比它小的数的下标
                int leftLess = stack.isEmpty() ? -1 : stack.peek();
                // 弹出的每一个下标，对应的元素，都要搜集答案
                max = Math.max(max, (i - leftLess - 1) * heights[pop]); // 搜集当前元素的答案，看能不能推高max
            }
            // 当前元素的下标入栈
            stack.push(i);
        }
        // stack 不为空，压着的元素是左边比它小的元素，右边没有比它大的元素
        // 说明它能一直扩到最右边N，宽是N - leftless - 1
        while (!stack.isEmpty()) {
            int pop = stack.pop(); // 弹出的元素的下标
            // 压着的数是左边第一个比它小的数的下标
            int leftLess = stack.isEmpty() ? -1 : stack.peek();
            // 弹出的list中的每一个下标，对应的元素，都要搜集答案
            max = Math.max(max, (N - leftLess - 1) * heights[pop]); // 搜集当前元素的答案，看能不能推高max
        }
        return max;
    }

	// 数组代替栈
	public int largestRectangleArea(int arr[]) {
		if (arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		int N = arr.length;
//		Stack<Integer> stack = new Stack<Integer>();
		int stack[] = new int[N];
		int size = 0;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i <= N - 1; i++) {
			// 等于的时候也计算，虽然计算错，相当于只计算了一部分的面积
			// 但是后面相等的元素弹出的时候，往左和往右能扩充到最大的范围，总会计算正确
			while (size != 0 && arr[stack[size - 1]] >= arr[i]) {
				int j = stack[--size];
				int leftLess = size == 0 ? -1 : stack[size - 1];
				max = Math.max(max, arr[j] * (i - leftLess - 1));
			}
			stack[size++] = i;
		}
		while (size != 0) {
			int j = stack[--size];
			int leftLess = size == 0 ? -1 : stack[size - 1];
			max = Math.max(max, arr[j] * (N - leftLess - 1));
		}
		return max;
	}
}
