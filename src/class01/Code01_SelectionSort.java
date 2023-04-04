package class01;

public class Code01_SelectionSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {3, 2, 3};
		selectionSort(arr);
		printArr(arr);
	}
	
	// 选择排序
	/**
	 *  思路： 从后面选择一个最小值，与前面一个数进行交换。
	 *  0-N-1 选择一个最小值与0位置的数交换
	 *  1-N-1 选择一个最小值与1位置交换
	 *  ...
	 *  N-2 - N-1 选择一个最小值与N-2位置进行交换
	 *  
	 *  循环次数: 0 到 N - 2, 可以取到N - 2
	 * @param arr
	 */
	public static void selectionSort(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int length = arr.length;
		for(int i = 0; i < length - 1; i++) { // 控制循环次数
			int minValueIndex = i;
			for(int j = i + 1; j <= length - 1; j++) { // 从 后面剩下的数找一个 最小值
				if(arr[j] < arr[minValueIndex]) {
					minValueIndex = j;
				}
			}
			// 进行交换
			swap(arr, i, minValueIndex);
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
