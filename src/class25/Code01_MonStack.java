package class25;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Code01_MonStack {
	/**
	 * 单调栈的实现 ==> 一个数组的每一个数，求它左边比它小的离它最近的第一个数的位置
				  求它右边比它小的离它最近的第一个数位置
	* 注意：单调栈中存放的是元素下标。
	 * 无重复数:
	 * 	思路：搞一个栈，从小到大存放数组的元素，从左往右依次遍历数组，如果栈不为空，并且栈顶的元素比当前
	 * 遍历到的数组大，则栈顶元素一直弹出，直到栈顶元素小于当前元素，在弹出的过程中，答案，弹出的元素左边第一个比它小的
	 * 就是它压着的元素，右边第一个比它小的就是让它出栈的当前元素，记录下标。弹出完毕，加入当前元素的下标到栈中，
	 * 最后数组遍历完成，如果栈中还有剩下的元素，弹出，收集答案，它下面压着的就是左边比它小的，
	* 没有元素让它出来，所以它右边没有比它小的元素，搜集-1
	 *
	 * 有重复数:
	 * 和无重复数类似，只不过存放的是链表，相等的数都放在一个链表中，
		在弹出的时候，链表中的所有元素的答案是一样的，左边比它小的第一个数就是它
		压着的数，右边第一个比它小的就是让它出来的数
		最后数组遍历完成，栈中剩下的数，弹出，左边比它小的第一个数是压着的链表的最后一个数，
		右边比它小的第一个数是-1。
	 */
	
	// 用一个二维数组收集答案， 二维数组的每一行代表的是一个下标存在的答案。
	// 第0行，表示的是0位置的数，左边比它小的，和右边比它小的第一个数
	// arr = [ 3, 1, 2, 3]
		//     0  1  2  3
		//  [
		//     0 : [-1,  1] // 
		//     1 : [-1, -1]
		//     2 : [ 1, -1]
		//     3 : [ 2, -1]
		//  ]
	
	public static int[][] generateLearLessNoRepeat(int arr[]) {
		if(arr == null || arr.length == 0) {
			return null;
		}
		int N = arr.length;
		int res[][] = new int[N][2];
		Stack<Integer> stack = new Stack<>(); // 只存下标
		for(int i = 0; i <= N - 1; i++) {
			while(!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
				int j = stack.pop();
				res[j][0] = stack.isEmpty() ? -1 : stack.peek(); // 左边第一个比它小的数的下标，就是它压着的数
				res[j][1] = i; // 右边第一个比它小的数，就是让它出来的数
			}
			stack.add(i);
		}
		while(!stack.isEmpty()) {
			int j = stack.pop();
			res[j][0] = stack.isEmpty() ? -1 : stack.peek(); // 左边第一个比它小的数的下标
			res[j][1] = -1; // 右边
		}
		return res;
	}
	
	
	public static int[][] generateNearLess(int arr[]) {
		if(arr == null || arr.length == 0) {
			return null;
		}
		int N = arr.length;
		int res[][] = new int[N][2];
		Stack<List<Integer>> stack = new Stack<List<Integer>>(); // 只存下标
		for(int i = 0; i <= N - 1; i++) {
			while(!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) { // List存放的都是相等的元素。随便取一个就行
				List<Integer> list = stack.pop();
				int leftLess= stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1); // 左边第一个比它小的数的下标，就是它压着的数
				for(int item : list) {
					res[item][0]  = leftLess; // 同一个list所有的数，左边比它小的数，都是一样，就是压着的那个
					res[item][1] = i; // 右边第一个比它小的数，就是让它出来的数
				}
			}
			// 如果栈顶元素和当前元素相等， 存在相等的list，直接加入
			if(!stack.isEmpty() && arr[i] == arr[stack.peek().get(0)]) {
				stack.peek().add(i);
			} else {
				List<Integer> newList = new ArrayList<Integer>();
				newList.add(i);
				stack.add(newList);
			}
		}
		while(!stack.isEmpty()) {
			List<Integer> list = stack.pop();
			int leftLess= stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
			for(int item : list) {
				res[item][0]  = leftLess; // 同一个list所有的数，左边比它小的数，都是一样，就是压着的那个
				res[item][1] = -1; // 没有让它出来的数
			}
		}
		return res;
	}
}
