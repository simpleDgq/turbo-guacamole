package class06;

public class Code03_HeapSort {
	/**
	 * 堆排序
	 * 
	 * 思路： 
	 * 1. 建立大根堆 (有两种方式，一种是从上往下建立，一种是从下往上)。 
	 * 2.将堆顶元素（也就是数组的0位置的数）和最后一个元素交换，向下调整堆， 这样最后一个元素就拍好序了，
	 *  继续0和N-2位置的数交换，调整堆
	 * ...类推，直到堆中没有元素，heapSize变成0的时候
	 * 
	 * ==== 这里的从上往下、从下往上指的是，整棵树建立的过程。 从上往下，来一个元素，建立第一层的节点，再来一个，建立
	 * 第二层的节点....,依次类推，树的构造过程是从上往下的。 从下往上，是假设整棵树都有了，然后从最后一层进行heapify， 然后是倒数第二层...
	 * 依次类推，树的构造过程是从下往上的。
	 * 
	 * 时间复杂度: O(N*logN)
	 * 
	 * 空间复杂度: O(1) 有限的变量，没有开辟新的空间。
	 */

	public static void heapSort(int arr[]) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 建大根堆, 从上往下
		// 时间复杂度为O(N*logN)
		/**
		 * 从上往下建大根堆： 遍历数组中的每一个数，从第一个数开始，每次调用heapInsert。
		 * 一个个元素来，新加一个元素，向上调整，每个元素logN，N个就是N*logN
		 */
//		for(int i = 0; i <= arr.length - 1; i++) {
//			heapInsert(arr, i);
//		}
		int heapSize = arr.length;

		/**
		 * 建大根堆，从数组最后一个元素开始，假设数组已经是二叉树了，从下往上, 每个元素都下沉调整成堆就行
		 * 每个元素都向下调整，比较两次，每一层比较的次数是等比数列，时间复杂度收敛到O(N) 
		 * 数据一股脑的全部给到的时候，就可以用这种方式，优化由上到下建堆的堆排序
		 */
		for (int i = arr.length - 1; i >= 0; i--) {
			heapIfy(arr, i, heapSize);
		}

		// 0 和 最后一个元素交换，向下调整堆
		swap(arr, 0, --heapSize);
		heapIfy(arr, 0, heapSize);
		while (heapSize != 0) { // 继续，0和N-2位置的数交换，调整堆，直到heapSize为0
			swap(arr, 0, --heapSize);
			heapIfy(arr, 0, heapSize);
		}
	}

	public static void heapInsert(int arr[], int index) {
		// 来到0位置的时候，-1/2 = 0, 所以while也会退出
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	public static void heapIfy(int arr[], int index, int heapSize) {
		int left = 2 * index + 1;
		while (left < heapSize) {
			int largestIndex = left + 1 < heapSize ? (arr[left] > arr[left + 1] ? left : left + 1) : left;
			if (arr[largestIndex] <= arr[index]) {
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
