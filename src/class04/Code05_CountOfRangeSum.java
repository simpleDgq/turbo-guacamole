package class04;

public class Code05_CountOfRangeSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 难题：给定一个数组arr，两个整数lower和upper，返回arr中有多少个子数组的累加和在
	 * [lower, upper]范围上
	 * Leetcode题目：https://leetcode.com/problems/count-of-range-sum/
	 * 
	 * 思路: 对于
	 * 1. 对于数组中的每一个数，求以当前数结尾的子数组有多少个满足条件的，然后将所有的个数累加起来，就能得到最终的结果。
	 *  假设数组[2, 3, 4], 以2结尾的子数组有a个满足条件，以3结尾的子数组有b个满足条件，以4结尾的子数组有c个满足条件。
	 *  则a+b+c就是最终的结果。
	 * 2. 进一步转化，如何求以某个数结尾的子数组有多少个满足条件？
	 * 假设arr[0...17], 求以17结尾的子数组有多少个落在[10, 40]这个范围上，假设0-17的前缀和是100，
	 * 则问题转换为： 在前缀和数组中，0-16这个范围内，有多少个数落在[100-40, 100-10] ==> [60,90]这个范围上。
	 * 例如假设0-5的前缀和是70，落在了[60,90]这个范围上，那么6-17 一定是落在[10, 40]这个范围上的，则6-17是一个满足条件的子数组。
	 * 3. 问题最终转换成: 对于前缀和数组中的每一个数num，看它的左边有多少个数是落在[num-up, num-lo]上的。
	 * 4. merge过程中，对于右组的每一个数，判断左组有多少个是满足条件的。
	 * (左边0-0的时候，考虑的是1-17是否满足条件
	 *  左边0-1的时候，考虑的是2-17是否满足条件
	 *  ..
	 *  需要注意的是：merge过程中没有机会考虑0-17的情况，所以需要在base case中进行考虑。
	 *  也就是：L==R 的时候，判断sum[L] 是否落在[lower, upper]上。
	 * )
	 * 
	 */
	
	public static int countRangeSum(int nums[], int lower, int upper) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		// 生成前缀和数组
		long preSum[] = new long[nums.length]; // 为什么是long？ 数组的preSum可能超过int的最大值，防止越界
		preSum[0] = nums[0];
		for(int i = 1; i <= nums.length - 1; i++) {
			preSum[i] = preSum[i - 1] + nums[i]; 
		}
		return process(preSum, 0, nums.length - 1, lower, upper);
	}
	
	public static int process(long preSum[], int L, int R, int low, int up) {
		if(L == R) {
			return (preSum[L] >= low &&  preSum[L] <= up) ? 1 : 0; // 考虑0-17特殊case，因为merge过程中不会求0-17是否满足条件。
			                                                       // merge的时候，左组一定是有数的，没有考虑左组没有数的情况，也就是0-17这种case。这里考虑。
		}
		
		int M = L + ((R -L) >> 1);
		return process(preSum, L, M, low, up) 
				+ process(preSum, M + 1, R, low, up) + 
				merge(preSum, L, M, R, low, up);
	}
	
	public static int merge(long preSum[], int L, int M, int R, int low, int up) {
		// 计算对于右组中的每一个数num，左组中有多少个是落在[num - up, num - low]上的
		int windowL = L;
		int windowR = L;
		int ans = 0;
		for(int i = M + 1; i <= R; i++) { // 对于右组的每一个数
			long min = preSum[i] - up;
			long max = preSum[i] - low;
			while(windowL <= M && preSum[windowL] < min) { // windowL向右滑动, 找到大于等于min的第一个数
				windowL++;
			}
			while(windowR <= M && preSum[windowR] <= max) { // windowR向右滑动, 找到大于max的第一个数(容易写错)
				windowR++;
			}
			ans += windowR - windowL; // [windowL, windowR) 
		}
		
		// merge 去吧
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		long helper[] = new long[R - L + 1];
		while(p1 <= M && p2 <= R) {
			if(preSum[p1] < preSum[p2]) {
				helper[i++] = preSum[p1++];
			} else {
				helper[i++] = preSum[p2++];
			}
		}
		while(p1 <= M) {
			helper[i++] = preSum[p1++];
		}
		while(p2 <= R) {
			helper[i++] = preSum[p2++];
		}
		for(int j = 0; j <= helper.length - 1; j++) {
			preSum[L + j] = helper[j];
		}
		return ans;
	}
}
