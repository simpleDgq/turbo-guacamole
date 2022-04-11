package class04;

public class Code03_ReversePair {
	/**
	 * 在一个数组中，任何一个前面的数a，和任何一个后面的数b，
	 *	如果(a,b)是降序的，就称为降序对.
	 *	给定一个数组arr，求数组的降序对总数量
	 *
	 * 思路: 一个数a，比它右边的数大，则是一个逆序对。
	 *  ==> 题目转换为：
	 * 针对一个数a，看它的右边有多少个数比a小，总数加起来就是要求
	 * 的结果。
	 * 
	 * 在merge过程中: （通过举例子来摸清条件）
	 * 从右往左进行merge。
	 * =============1=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *            ^            ^
	 * 如果左组等于右组，不累加，右组--，拷贝右组 （要求的是右边有多少个数比6小，相等的时候右组应该--，判断还有没有比6小的）
	 * =============1=============
	 * 
	 * =========2=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *            ^          ^
	 * 如果左组大于右组，则要累加，而且要将左组拷贝，左组-- (要求的目标是看它右边有多少个数比它小，1 <  4, 那么1之前的数一定都比4小)
	 * =========2=============
	 * 
	 * =========3=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *          ^            ^
	 * 如果左组小于右组，不累加，右组--，拷贝右组
	 * =========3=============
	 */
	
	public static int reversePair(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}
	
	public static int process(int arr[], int L, int R) {
		if(L == R) {
			return 0;
		}
		int M = L + ((R - L) >> 1);
		return 
				process(arr, L, M) 
				+ process(arr, M + 1, R) 
				+ merge(arr, L, M, R);	
	}
	
	public static int merge(int arr[], int L, int M, int R) {
		int p1 = M; // 从右往左merge，指向左组的最后一个元素
		int p2 = R; 
		int helper[] = new int[R - L + 1];
		int i = 0;
		int ans = 0;
		
		while(p1 >= L && p2 >= M + 1) {
			if(arr[p1] <= arr[p2]) { // 左组小于等于右组
				helper[i++] = arr[p2--]; // 拷贝右组
			} else { //左组大于右组，拷贝左组， 需要累加，因为求得是右边有多少个数比当前数小
				ans += p2 - M;
				helper[i++] = arr[p1--];
			}
		}
		while(p1 >= L) {
			helper[i++] = arr[p1--];
		}
		while(p2 >= M + 1) {
			helper[i++] = arr[p2--];
		}
		// 这里需要注意的是: 在merge完成之后，helper数组里面的数是从大到小排序的，
		// 需要逆序拷贝到arr中，变成正序，所以从R开始放，放一个R--
		for(int j = 0; j <= helper.length - 1; j++) {
			arr[R--] = helper[j];
		}
		return ans;
	}
}
