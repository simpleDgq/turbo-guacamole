package class25;

import java.util.Stack;

public class Code02_SubArrayMinSumMax {
	// 测试链接 : https://leetcode.com/problems/maximum-subarray-min-product/
	/**
	 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
		一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
		那么所有子数组中，这个值最大是多少？
	 */
	
	/**
	 * 思路： 
	 * 	求必须以0位置作为最小值的子数组中，哪个数组的指标（sum*min）是最大的
		求必须以1位置作为最小值的子数组中，哪个数组的指标（sum*min）是最大的
		.....
		所有的答案求max，就行
		===
		数组中都是正数，i位置的值是X，一定要以X作为最小值min，同时又要使min*sum最大，
		那么需要sum尽可能的大，都是正数，那么最多，sum就最大。也就是左边比X小的第一个数j，到右边比X小的第一个数K，j和K都不要的范围。

		任何一个范围上的累加和可以用前缀和数组来求。
		===
		准备一个从小到大的单调栈，遍历数组元素，将当前元素当成子数组里面的最小值，为了是min*sum
		尽可能的大，需要sum尽可能大，也就是需要子数组的范围尽可能大，求每一个数左边第一个比它小的数的位置
		和右边第一个比它小的数的位置，构成的范围里面的数，就是以当前值作为最小值，而且sum最大的子数组。
		相等的时候，栈里面的元素也弹出，将和栈顶元素相等的元素当成右边第一个比它小的数，计算答案，虽然计算错了，但是没有关系，
		后面相等的元素出栈的时候，能够贯穿左右两边最大的子数组范围，求得正确的答案
	 */
	public static long max(int arr[]) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int N = arr.length;
		int preSum[] = new int[N];
		preSum[0] = arr[0];
		for(int i = 1; i <= N - 1; i++) {
			preSum[i] = preSum[i - 1] + arr[i]; // 生成前缀和数组，便于计算任意范围上子数组的和
		}
		Stack<Integer> stack = new Stack<>(); // 只存下标
		long max = Integer.MIN_VALUE; // long 防止溢出
		for(int i = 0; i <= N - 1; i++) { // 以数组的每一个元素作为min
			while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]) { // 栈顶元素大于等于当前元素的时候 弹出计算答案
				int j = stack.pop(); // 弹出的元素的下标，去计算该元素的答案
				int leftLess = stack.isEmpty() ? -1 : stack.peek(); // 左边第一个比它小的元素的下标
				// 右边第一个比它小或者等于的元素下标 就是i
				if(leftLess == -1) {
					max = Math.max(max, arr[j] * preSum[i - 1]);
				} else {
					max = Math.max(max, arr[j] * (preSum[i - 1] - preSum[leftLess]));
				}
			}
			stack.push(i);
		}
		while(!stack.isEmpty()) {
			int j = stack.pop();
			int leftLess = stack.isEmpty() ? -1 : stack.peek(); // 左边第一个比它小的元素的下标
			if(leftLess == -1) { // 左边不存在比它小的元素，同时右边也不存在比它小的元素。说明整个数组范围，都是子数组
				max = Math.max(max, arr[j] * preSum[N - 1]);
			} else {// 左边存在比它小的，右边不存在比它小的
				max = Math.max(max, arr[j] * (preSum[N - 1] - preSum[leftLess]));
			}
		}
		return max % 1000000007; // leetcode要求
	}
	
	// 用数组代替Java栈，优化
	public static long max3(int arr[]) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int N = arr.length;
		int preSum[] = new int[N];
		preSum[0] = arr[0];
		for(int i = 1; i <= N - 1; i++) {
			preSum[i] = preSum[i - 1] + arr[i]; // 生成前缀和数组，便于计算任意范围上子数组的和
		}
//		Stack<Integer> stack = new Stack<>(); // 只存下标
		int stack[] = new int[N];
		int size = 0;
		long max = Integer.MIN_VALUE; // long 防止溢出
		for(int i = 0; i <= N - 1; i++) { // 以数组的每一个元素作为min
			while(size != 0 && arr[stack[size - 1]] >= arr[i]) { // 栈顶元素大于等于当前元素的时候 弹出计算答案
				int j = stack[--size]; // 弹出的元素的下标，去计算该元素的答案
				int leftLess = size == 0 ? -1 : stack[size - 1]; // 左边第一个比它小的元素的下标
				// 右边第一个比它小或者等于的元素下标 就是i
				if(leftLess == -1) {
					max = Math.max(max, arr[j] * preSum[i - 1]);
				} else {
					max = Math.max(max, arr[j] * (preSum[i - 1] - preSum[leftLess]));
				}
			}
			stack[size++] = i;
		}
		while(size != 0) {
			int j = stack[--size];
			int leftLess = size == 0 ? -1 : stack[size - 1]; // 左边第一个比它小的元素的下标
			if(leftLess == -1) { // 左边不存在比它小的元素，同时右边也不存在比它小的元素。说明整个数组范围，都是子数组
				max = Math.max(max, arr[j] * preSum[N - 1]);
			} else {// 左边存在比它小的，右边不存在比它小的
				max = Math.max(max, arr[j] * (preSum[N - 1] - preSum[leftLess]));
			}
		}
		return max % 1000000007; // leetcode要求
	}
}
