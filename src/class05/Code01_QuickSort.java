package class05;

public class Code01_QuickSort {
	
	/**
	 * 快排1.0
	 * 
	 * <= X的放左边，> X的放右边
	 * 
	 * 划分思路：用数组的最后一个元素作为划分值
	 * 有一个小于等于区，从-1位置开始，
	 * 1. 如果当前元素小于等于划分值，则当前元素和小于等于区的下一个元素进行交换，当前元素跳下一个
	 * 2. 如果当前元素大于划分值，当前元素直接调下一个。
	 * 3. 最后数组的最后一个元素和小于等于区的下一个数交换，这样这个数X的位置就确定好了。
	 * 
	 * 排序思路:
	 * 划分一次，得到了小于等于区的最后一个元素X的位置，X位置就确定好了，递归去求X的左边和X的右边。
	 * 
	 * 时间复杂度: O(N^2)
	 */
	public static void quickSort1(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}
	
	// L~R范围上去递归，一次划分确定好一个位置
	public static void process(int arr[], int L, int R) {
		if(L >= R) { // 只有一个数，不用划分递归求了，直接返回
			return;
		}
		// 划分
		int lessEqual = partition(arr, L, R);
		// 搞左边
		process(arr, L, lessEqual - 1);
		// 搞右边
		process(arr, lessEqual + 1, R);
	}
	
	//L~R范围上，用数组的最后一个元素X作为划分值，小于等于X的放左边，大于X的放右边
	public static int partition(int arr[], int L, int R) {
		if(L > R) {
			return -1;
		}
		if(L == R) {
			return L;
		}
		int lessEqual = L - 1; // 小于等于区的右边界，小于等于区最后一个元素的位置
//		int index = L;
		while(L < R) {
			if(arr[L] <= arr[R]) {
				swap(arr, L, ++lessEqual);
			}
			L++;
		}
		swap(arr, ++lessEqual, R); // 最后一个元素和小于等于区的下一个位置交换，划分值放进小于等于的最后
		return lessEqual;
	}
	public static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 * 快排2.0 ==> 荷兰国旗问题
	 * 
	 *  < X的放左边，=X的放中间，> X的放右边
	 *  
	 * 划分思路：用数组的最后一个元素作为划分值
	 * 有一个小于区，从-1位置开始，有一个大于区从arr.length-1开始，将最后一个元素扩在大于区。后面和大于区的第一个数交换，
	 * 就将数组的最后一个数X放进了等于区。
	 * 1. 如果当前元素小于划分值，则当前元素和小于区的下一个元素进行交换，当前元素跳下一个
	 * 2. 如果当前元素等于划分值，当前元素直接调下一个。
	 * 3. 如果当前元素大于划分值，当前元素和大于区的前一个数交换，当前数不动(当前数是从后面交换过去的，还没有比较，当前数不能动)
	 * 4. 最后，当前元素和大于区撞上，数组的最后一个元素和大于区的第一个数交换，这样这个数X的位置就确定好了。
	 * 返回等于区的开始和结束位置。
	 * 
	 * 排序思路：递归去求，等于区的左边和右边，每次递归搞定一个等于区的数。
	 * 
	 * 时间复杂度: O(N^2)
	 */
	public static void quickSort2(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		porcess2(arr, 0, arr.length - 1);
	}
	// arr[L~R]上进行排序
	public static void porcess2(int arr[], int L, int R) {
		if(L >= R) {
			return;
		}
		// 划分
		int equals[] = partition2(arr, L, R);
		// 搞左边
		porcess2(arr, L, equals[0] - 1);
		// 搞右边
		porcess2(arr, equals[1] + 1, R);
	}
	public static int[] partition2(int arr[], int L, int R) {
		if(L > R) {
			return new int[] {-1, -1};
		}
		if(L == R) {
			return new int[] {L, L};
		}
		int less = L - 1; // 注意，从-1位置开始，每次新的L，都要减1
		int more = R; // 大于区包含最后一个元素
		int index = L;
		while(index < more) { //注意 不能和大于区的左边界撞上 
			if(arr[index] < arr[R]) {
				swap(arr, index++, ++less); // 当前元素和小于于区的下一个元素交换，小于区右扩，当前元素跳下一个
			} else if(arr[index] > arr[R]) {
				swap(arr, index, --more); // 当前元素和大于区的前一个元素交换，大于区左扩，当前元素不跳
			} else { // 相等，直接跳下一个
				index++;
			}
		}
		swap(arr, more, R); // 最后一个元素和大于区的第一个元素交换
		return new int[] {less + 1, more}; // 返回等于区的范围
	}
	
	
	/**
	 * 快排3.0 ==> 随机快排
	 * 
	 * 在快排2.0的基础上，随机选择一个元素，和数组的最后一个元素交换，作为划分值
	 * 
	 * 时间复杂度收敛到N*logN
	 * 
	 * 空间复杂度，
	 */
	public static void quickSort3(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		porcess3(arr, 0, arr.length - 1);
	}
	// arr[L~R]上进行排序
	public static void porcess3(int arr[], int L, int R) {
		if(L >= R) {
			return;
		}
		// 随机选择一个数，和最后一个元素交换
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R); // 注意要加L, 因为右半部分递归的时候，保证只有右半部分的数组和最后元素交换
		// 划分
		int equals[] = partition3(arr, L, R);
		// 左
		porcess3(arr, L, equals[0] - 1);
		// 右
		porcess3(arr, equals[1] + 1, R);
	}
	public static int[] partition3(int arr[], int L, int R) {
		if(L > R) {
			return new int[] {-1, -1};
		}
		if(L == R) {
			return new int[] {L, L};
		}
		int less = L - 1; // 注意
		int more = R;
		int index = L;
		while(index < more) { //注意 不能和大于区的左边界撞上 
			if(arr[index] < arr[R]) {
				swap(arr, index++, ++less); // 当前元素和小于于区的下一个元素交换，小于去右扩，当前元素跳下一个
			} else if(arr[index] > arr[R]) {
				swap(arr, index, --more); // 当前元素和大于区的前一个元素交换，大于去左扩，当前元素不跳
			} else { // 相等，直接跳下一个
				index++;
			}
		}
		swap(arr, more, R); // 最后一个元素和大于去的第一个元素交换
		return new int[] {less + 1, more};
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
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			quickSort1(arr1);
			quickSort2(arr2);
			quickSort3(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3) ) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");

	}

}
