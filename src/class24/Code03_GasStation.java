package class24;

import java.util.LinkedList;
// 测试链接：https://leetcode.com/problems/gas-station
public class Code03_GasStation {
	/**
	 * 加油站的良好出发点问题
	 * 有两个int数组，分别存放的是gas和cost信息，例如图上的例子表示的就是，
	a号加油站有1个单位的油，a到达b的距离是2；b号加油站有1单位的油，到c的
	距离是2；c号加油站有3单位的油，到d的距离是1；d加油站有1单位的油，到
	a的距离是1。问从哪出发，能够跑完一圈，回到自己，返回所有可能的出发点？只能逆时针跑
	（为了表示方便，用abcd代表数组下标）
	 */
	/**
	 * 思路：
	 * 弄出一个长度为2*N的累加和数组。
	 * arr1 是gas - cost 得到的一个对应的数组
	 * arr是arr1中的每个数，循环累加得到的
	 */
	// 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		boolean[] good = goodArray(gas, cost);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean[] goodArray(int[] gas, int[] cost) {
        int N = cost.length;
		int M = N << 1;
		int arr[] = new int[M];
		for(int i = 0; i <= N - 1; i++) {
			arr[i] = gas[i] - cost[i];
			arr[i + N] = gas[i] - cost[i]; // 比较tricky
		}
		for(int i = 1; i <= M - 1; i++) {
			arr[i] += arr[i - 1];
		}
		LinkedList<Integer> minWindow = new LinkedList<Integer>();
		for(int R = 0; R <= N - 1; R++) { // 0~5先入窗口, 课上的例子N=6
			int cur = arr[R];
			while(!minWindow.isEmpty() && arr[minWindow.peekLast()] >= cur) {
				minWindow.pollLast();
			}
			minWindow.addLast(R);
		}
        boolean ans[] = new boolean[N];
//		int offset = 0;
//		int index = 0;
//		for(int R = N; R <= M - 1; R++, offset = arr[index++]) {
//			// 当前窗口最薄弱的地方 - 减去当前窗口最左边的前一个数，得到的累加大于等于0，则说明L开头是可以的
//			if(arr[minWindow.peekFirst()] - offset >= 0) { 
//				ans[index] = true;
//			}
//			if(index == minWindow.peekFirst()) { // L位置即将过期
//				minWindow.pollFirst();
//			}
//			while(!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) { // 加入一个右边的元素，扩大窗口
//				minWindow.pollLast();
//			}
//			minWindow.addLast(R);
//		}
        // 上面的方法也可以，上面是考虑的R，下面是考虑的L
        int R = N;
        int offset = 0;
        for(int L = 0; L <= N - 1; offset = arr[L], L++) {
			// 当前窗口最薄弱的地方 - 减去当前窗口最左边的前一个数，得到的累加大于等于0，则说明L开头是可以的
			if(arr[minWindow.peekFirst()] - offset >= 0) { 
				ans[L] = true;
			}
			if(L == minWindow.peekFirst()) { // L位置即将过期
				minWindow.pollFirst();
			}
			while(!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) { // 加入一个右边的元素，扩大窗口
				minWindow.pollLast();
			}
			minWindow.addLast(R);
			R++;
        }
		return ans;
	}
}
