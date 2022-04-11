package class04;

import java.util.Arrays;

public class Code01_MergeSort {
	/**
	 * 归并排序，递归实现。
	 * 思路:
	 * 1. 求中点M 。 base case: L==R ，直接return
	 * 2.f(arr,L,M)保证L - M 有序
	 * 3.f(arr,M + 1, R)保证M+1 - R 有序
	 * 4. 合并两边有序的数组
	 *   1）准备一个辅助数组，合并的时候，有两个指针分别指向左右两个数组元素，比较两个元素，谁小则将
	 *   谁拷贝到辅助数组中，指针向下移动; （等于的时候拷贝谁都可以）
	 *   2）将左右两边剩下的元素，直接全部拷贝到辅助数组中。注意拷贝的时候原数组的下标是L + j
	 */
	public static void mergeSort1(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}
	public static void process(int arr[], int L, int R) {
		if(L == R) { // Base Case: 只有一个元素，直接返回 (重点：写代码的时候容易错)
			return;
		}
		int mid = L + ( (R - L) >> 1);
		// 左边有序
		process(arr, L, mid);
		// 右边有序
		process(arr, mid + 1, R);
		// merge
		merge(arr, L, mid, R);
	}
	
	/**
	 * Merge 的时间复杂度是O(N), 因为比较元素，指针不回退。
	 * @param arr
	 * @param L
	 * @param M
	 * @param R
	 */
	public static void  merge(int arr[], int L, int M, int R) {
		int p1 = L;
		int p2 = M + 1;
		int helper[] = new int[R - L + 1]; // 辅助数组的长度，每次merge的时候都不一样，所以是R - L + 1
		int i = 0;
		while(p1 <= M && p2 <= R) {
			if(arr[p1] < arr[p2]) { // 左组小于等于右组，拷贝左组
				helper[i++] = arr[p1++];
			} else { // 拷贝右组
				helper[i++] = arr[p2++];
			}
		}
		while(p1 <= M) { // 左组还有元素
			helper[i++] = arr[p1++];
		}
		while(p2 <= R) { // 右组还有元素
			helper[i++] = arr[p2++];
		}
		// 将最终结果拷贝到arr中
		for(int j = 0; j <= helper.length - 1; j++) {
			arr[L + j] = helper[j]; // 注意这里是L + j (重点：写代码的时候容易错)
		}
	}
	
	/**
	 * 非递归实现
	 * 思路:
	 * 1. 有一个步长step，从1开始，按步长将元素分为多对左组和右组，从左往右一对对进行merge
	 * 2. 步长*2，继续下一轮merge
	 * 3. 当步长>= 数组长度的时候，merge结束
	 */
	public static void mergeSort2(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int step = 1;
		int N = arr.length;
		while(step < N) {
			int L = 0; // 每次调整步长之后，左组总是从0位置开始
			
			// 从左往右一组组merge去吧
			while(L < N) { // 每次merge完之后，需要去进行下一轮的merge，L会跳到下一个左组的位置上
						   //L不能超过N. 超过N说明已经没有元素了
				int M = L + step - 1; // 左组的最后一个元素的下标
				if(M >= N) { //左组不够了或者正好到达了N，说明右组也没了，不需要merge，直接break，调整步长开始下一轮
					break;
				}
				int R = Math.min(M + step, N - 1); // 右组的最后一个元素的下标。不够了的话，就是数组的最后下标
				merge(arr, L, M, R);
				L = R + 1; // 每次merge完之后，需要去进行下一轮的merge，L会跳到下一个左组的位置上
			}
			
			if(step > N / 2) { // 防止*2之后，超过整数界限， 如果> N / 2, 那么*2之后一定是> N的
				return;
			}
			// 做完一次merge之后，步长*2
			step <<= 1;
		}
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			mergeSort1(arr1);
//			mergeSort2(arr2);
			Arrays.sort(arr2);
			if (!isEqual(arr1, arr2)) {
				System.out.println("出错了！");
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
