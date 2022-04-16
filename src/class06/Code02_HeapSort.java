package class06;

public class Code02_HeapSort {
	/**
	 * 堆排序
	 * 
	 * 思路：
	 * 1. 建立大根堆 (有两种方式，一种是从上往下建立，一种是从下往上)
	 * 2. 将堆顶元素（也就是数组的0位置的数）和最后一个元素交换，向下调整堆，
	 * 这样最后一个元素就拍好序了，继续0和N-2位置的数交换，调整堆
	 * ...类推，直到堆中没有元素，heapSize变成0的时候
	 */
	
	public static void heapSort(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		// 建大根堆, 从上往下
		// 时间复杂度为O(N*logN)
//		for(int i = 0; i <= arr.length - 1; i ++) {
//			heapInsert(arr, i);
//		}
		int heapSize = arr.length;
		
		// 建大根堆，假设数组已经是二叉树了，直接从最后一个元素开始，从下往上, 下沉调整成堆就行
		// 时间复杂度为O(N)
		for(int i = arr.length - 1; i >= 0; i--) {
			heapIfy(arr, i, heapSize);
		}
		
		// 0 和 最后一个元素交换，向下调整堆
		swap(arr, 0, --heapSize);
		heapIfy(arr, 0, heapSize);
		while(heapSize != 0) { // 继续，0和N-2位置的数交换，调整堆，直到heapSize为0
			swap(arr, 0, --heapSize);
			heapIfy(arr, 0, heapSize);
		}
	}
	
	public static void heapInsert(int arr[], int index) {
		while(arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}
	
	public static void heapIfy(int arr[], int index, int heapSize) {
		int left = 2 * index + 1;
		while(left < heapSize) {
			int largestIndex = left + 1 < heapSize ? (arr[left] > arr[left + 1] ? left : left + 1) : left;
			if(arr[largestIndex] <= arr[index]) {
				break;
			}
			swap(arr, index, largestIndex);
			index = largestIndex;
			left = index * 2 + 1;
		}
	}
	
	public static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
