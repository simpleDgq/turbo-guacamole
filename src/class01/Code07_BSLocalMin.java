package class01;

public class Code07_BSLocalMin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int testTime = 2000;
		int maxSize = 5;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int ans = BSLocalMin(arr);
			System.out.println(ans);
			printArr(arr);
			if (!isRight(arr, ans)) {
				System.out.println("出错了！");
				break;
			}
		}
		
		System.out.println("测试结束");
		
//		int arr[] = {-5,-32,33,37};
//		int ans = BSLocalMin(arr);
//		System.out.println(ans);
	}
	
	/**
	 * 局部最小值问题
	 * 一个无序的数组，相邻的元素不相等，返回局部最小值的位置。
	 * 
	 * 思路: 三种情况
	 * 1. 0位置的元素，如果小于1位置的数或者数组长度为1，那0位置一定是局部最小值，直接返回
	 * 2. length - 1位置的元素，如果小于length - 2位置的数，那它一定是局部最小值，直接返回
	 * 3. 其它位置：mid 位置的数，如果小于mid + 1, 而且小于mid - 1, 一定是局部最小值；
	 * 		如果大于mid + 1位置的数，说明mid的右边一定有局部最小值，直接去右边找；
	 * 		如果大于mid - 1位置的数，说明mid的左边，一定有局部最小值，直接去左边找。
	 */
	public static int BSLocalMin(int arr[]) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		if(arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if(arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int ans = -1;
		int L = 1;
		int R = arr.length - 2;
		while(L <= R) {
			int mid = L + ((R - L) >> 1);
			if(arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
				ans = mid;
				break;
			} else if(arr[mid] > arr[mid + 1]) { // 如果大于mid + 1位置的数，说明mid的右边一定有局部最小值，直接去右边找；
				L = mid + 1;
			} else if(arr[mid] > arr[mid - 1]) { //如果大于mid - 1位置的数，说明mid的左边，一定有局部最小值，直接去左边找。
				R = mid - 1;
			}
		}
		return ans;
	}
	
	// 验证得到的结果，是不是局部最小
	public static boolean isRight(int[] arr, int index) {
		if (arr.length <= 1) {
			return true;
		}
		if (index == 0) {
			return arr[index] < arr[index + 1];
		}
		if (index == arr.length - 1) {
			return arr[index] < arr[index - 1];
		}
		return arr[index] < arr[index - 1] && arr[index] < arr[index + 1];
	}

	// 为了测试
	// 生成相邻不相等的数组
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) (Math.random() * maxSize) + 1];
		arr[0] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
		for (int i = 1; i < arr.length; i++) {
			do {
				arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
			} while (arr[i] == arr[i - 1]);
		}
		return arr;
	}
	
	public static void printArr(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		for(int i = 0; i <= arr.length - 1; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println();
	}

}
