package class04;

public class Code02_SmallSum {
	/**
	 * 求给定得一个数组的最小和。
	 * 一个数组中，一个数的左边，比它小的数的和，叫做该数的小和。
	 * 所有数的这种小和加起来就是数组的最小和。
	 * 
	 * 思路:
	 * a,b,c三个数，假设a比b小，也比c小，那么最终的结果中会累加上两个a。
	 * 所以问题可以转换为: 针对a，求右边有多少个数比a大，则结果中就累加 2 * a。
	 * ==> 针对每一个数，看它右边有多少个数比它大，则结果中累加上几个这个数。
	 * 
	 * 在归并排序的merge过程中:
	 * 如果左组的数比右组小，则产生小和，（看右组中有多少个数比左组的当前数大，则累加几个左组的当前数）
	 * 如果左组的数大于等于右组的数，则不产生小和（因为要求的是右边有多少个数比它大）。
	 * 
	 */
	
	public static int smallSum(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}
	
	/**
	 * 计算左部分小和，计算右部分小和，左右部分合并的时候的小和。三者累加。
	 * @param arr
	 * @param L
	 * @param R
	 * @return
	 */
	public static int process(int arr[], int L, int R) {
		if(L == R) {
			return 0;
		}
		int M = L + ((R - L) >> 1);
				// 计算左边的小和
		return process(arr, L, M)
				// 计算右边边的小和
				+ process(arr, M + 1, R)
				// 计算最后一次merge的小和
				+ merge(arr, L, M, R);
	}
	
	public static int merge(int arr[], int L, int M, int R) {
		int p1 = L;
		int p2 = M + 1;
		int i = 0;
		int helper[] = new int[R - L + 1];
		int ans = 0;
		while(p1 <= M && p2 <= R) {
			if(arr[p1] < arr[p2]) {
				ans += arr[p1] * (R - p2 + 1); // 左组比右组小，产生小和 。 右组中p2 及其往后的位置的数，都比arr[p1]大，因为合并的时候，左右两部分都是已经排好序的。
				helper[i++] = arr[p1++];
			} else { // 左组大于等于右组，不产生小和。因为要求的是右边有多少个数比当前数大
				helper[i++] = arr[p2++];
			}
		}
		while(p1 <= M) {
			helper[i++] = arr[p1++];
		}
		while(p2 <= R) {
			helper[i++] = arr[p2++];
		}
		for(int j = 0; j <= helper.length - 1; j++) {
			arr[L + j] = helper[j];
		}
		return ans;
	}
}
