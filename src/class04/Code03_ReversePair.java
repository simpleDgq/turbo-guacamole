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
	 * =========1=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *            ^            ^
	 * 如果左组等于右组，不累加，拷贝右组，右组--（要求的是右边有多少个数比6小，相等的时候右组应该--，判断还有没有比6小的）
	 * =========1=============
	 * 
	 * =========2=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *            ^          ^
	 * 如果左组大于右组，则要累加，而且要将左组拷贝，左组-- (要求的目标是看它右边有多少个数比它小，4 <  6, 那么4之前的数一定都比6小)
	 * =========2=============
	 * 
	 * =========3=============
	 * 例子：1 2 3 6   和 2 4 4 6
	 *          ^            ^
	 * 如果左组小于右组，不累加，拷贝右组，右组--
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
				+ merge2(arr, L, M, R);	
	}
	
	public static int merge(int arr[], int L, int M, int R) {
		int p1 = M; // 从右往左merge，指向左组的最后一个元素
		int p2 = R; // 指向右组的最后一个元素
		int helper[] = new int[R - L + 1];
		int i = 0;
//		int i = help.length - 1; // 如果从help最后一个位置开始放，下面代码中的i++全部要改成i--
		int ans = 0;
		
		while(p1 >= L && p2 >= M + 1) {
			if(arr[p1] <= arr[p2]) { // 左组小于等于右组
				helper[i++] = arr[p2--]; // 拷贝右组
			} else { //左组大于右组，拷贝左组， 需要累加，因为求得是右边有多少个数比当前数小
				ans += p2 - M; // 右组有序，从M到p2都是比左组小的
				helper[i++] = arr[p1--];
			}
		}
		while(p1 >= L) {
			helper[i++] = arr[p1--];
		}
		while(p2 >= M + 1) {
			helper[i++] = arr[p2--];
		}
		// 这里需要注意的是: 在merge完成之后，helper数组里面的数是从大到小排序的（实际举例子来看到这点），
		// 需要逆序拷贝到arr中，变成正序，所以从R开始放，放一个之后，R--
		for(int j = 0; j <= helper.length - 1; j++) {
			arr[R--] = helper[j];
		}
		/**
		 * 如果i初始值是int i = help.length - 1; 就不用逆序拷贝，可以用下面的代码
		 * 因为放入helper数组中的时候，是从helper最后一个位置开始放的。
		 * for (i = 0; i < help.length; i++) {
		 *		arr[L + i] = help[i];
		 * }
		 */
		return ans;
	}
	
	/**
	 * 先计算出有多少个满足条件的数，再去merge。
	 * 
	 * 掌握这种解法，写起来比较简单。
	 */
	public static int merge2(int arr[], int L, int M, int R) {
		int windowR = M + 1;
		int ans = 0;
		// 先计算对于左组中的每个数，右组中有多少个满足条件的数
		for(int i = L; i <= M; i++) { // 计算出右组中有多少个数比左组的当前数小
			while(windowR <= R && arr[i] > arr[windowR]) {
				windowR++;
			}
			ans += windowR - M - 1;
		}
		
		// 后merge
		int p1 = L;
		int p2 = M + 1; 
		int helper[] = new int[R - L + 1];
		int i = 0;
		while(p1 <= M && p2 <= R) {
			if(arr[p1] < arr[p2]) { // 左组小于右组（等于拷贝谁都无所谓）
				helper[i++] = arr[p1++];
			} else { //左组大于等于右组
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
	
	
	// for test
		public static int comparator(int[] arr) {
			int ans = 0;
			for (int i = 0; i < arr.length; i++) {
				for (int j = i + 1; j < arr.length; j++) {
					if (arr[i] > arr[j]) {
						ans++;
					}
				}
			}
			return ans;
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
				if (reversePair(arr1) != comparator(arr2)) {
					System.out.println("Oops!");
					printArray(arr1);
					printArray(arr2);
					break;
				}
			}
			System.out.println("测试结束");
		}

}
