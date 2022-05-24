package class26;

import java.util.Stack;

//测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/
public class Code01_SumOfSubArrayMin {
	/**
	 * 给定一个数组arr，返回所有子数组最小值的累加和
	 * 思路：任意一个位置做为子数组的最小值，往左边求比当前值小的第一个数，假设位置为leftLess
	 * 右边第一个比当前数小的数，位置是i, 
	 * 则leftLess + 1 ~ i - 1  这个范围上，都是比当前值大的元素，这个子数组的最小值就是当前元素。
	 * 这个范围上，只要穿过当前元素，形成的子数组，都是以当前元素为最小值的，假设当前元素的下标为j，
	 * 当前元素的左边有 j - leftLess 个数，穿过j都能形成最小值数组，每个数穿过j能形成的子数组的数量是
	 * i - j个，所有子数组的数量是(j - leftLess) * (i - j) ==> 这些子数组的最小值累加和(j - leftLess) * (i - j) * arr[j]
	 * 
	 * 值相等的时候，停止，不往右扩，弹出，计算答案的时候，计算到右边比它小的数的前一个位置就行。
	 */
   public static int sumSubarrayMins(int[] arr) {
		Stack<Integer> stack = new Stack<Integer>();
		int N = arr.length;
		long sum = 0;
		for(int i = 0; i <= N - 1; i++) {
			while(!stack.isEmpty() && arr[stack.peek()] >= arr[i] ) {// 相等的时候，不往右扩，计算到i-1
				int j = stack.pop();
				int leftLess = stack.isEmpty() ? -1 : stack.peek();
				sum += (j - leftLess) * (i - j) * (long)arr[j];
                sum %= 1000000007; // 纯粹是为了满足题目要求，不超过最大整数
			}
			stack.push(i);
		}
		while(!stack.isEmpty()) {
			int j = stack.pop();
			int leftLess = stack.isEmpty() ? -1 : stack.peek();
			sum += (j - leftLess) * (N - j) * (long)arr[j];
            sum %= 1000000007; // 纯粹是为了满足题目要求，不超过最大整数
		}
		return (int) sum;
	}
}
