package class01;

public class Code02_BubbleSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {3, 2, 3};
		bubbleSort(arr);
		printArr(arr);
	}
	
	// 冒泡排序
	/**
	 *  思路： 两两相邻的元素进行比较，谁大谁往后。
	 *  0-N-1范围上：
	 *  	0和1位置的数进行比较，谁大谁往后
	 *  	1-2 位置的数进行比较，谁大谁往后
	 *  	...
	 *  0-N-2范围上：
	 *  	0和1位置的数进行比较，谁大谁往后
	 *  	1-2 位置的数进行比较，谁大谁往后
	 *  	...
	 *  ...
	 *  0-1范围上：
	 *    	0和1位置的数进行比较，谁大谁往后
	 * @param arr
	 */
	public static void bubbleSort(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int length = arr.length;
		for(int i = 0; i < length - 1; i++) { // 控制总的循环次数
			for(int j = 0; j < length - i - 1; j++) { // 两两相邻的元素进行比较
				if(arr[j] > arr[j + 1]) {
					swap(arr, j, j + 1);
				}
			}
		}
	}
	
	public static void swap(int arr[], int i, int j) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void printArr(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		for(int i = 0; i <= arr.length - 1; i++) {
			System.out.println(arr[i]);
		}
	}

}
