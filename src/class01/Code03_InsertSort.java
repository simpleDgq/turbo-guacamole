package class01;

public class Code03_InsertSort {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int arr[] = {3, 2, 3, 4, 1, 2, 5, 0, 2};
//		insertSort(arr);
//		printArr(arr);
//	}
	
	// 插入排序
	/**
	 * 思路：后面的数，在前面找到一个合适的位置进行插入。
	 * 
	 * 0-1范围保证有序: 1位置的数和0位置进行比较，小于，则交换
	 * 0-2范围保证有序: 2位置的数和1位置进行比较，如果小则交换；然后1位置的数和0位置的数进行比较，如果小，则交换
	 * ...
	 * 0-N-1范围保证有序:N-1和N-2进行比较,....1和0进行比较
	 * 
	 * 循环次数: 1 到 N - 1次, 能取到 N - 1
	 * @param arr
	 */
	public static void insertSort(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int length = arr.length;
		for(int i = 1; i <= length - 1; i++) {// 循环次数
			for(int j = i - 1; j >= 0; j--) { // 0 ~ 1 0 ~ 2
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
