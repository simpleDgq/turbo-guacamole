package class06;

public class Code01_Heap {
	/**
	 * 使用数组实现堆。
	 * 
	 * heapInsert:
	 * 添加元素的时候，放到最后一个位置，
	 * 向上调整堆，当前元素和它的父节点进行比较，如果大于父节点，则和父节点交换；
	 * 直到干不过父节点，或者到达了根节点的位置，结束
	 * 
	 * heapify:
	 * 取元素的时候，堆顶的元素和最后一个元素进行交换，heapSize--，
	 * 然后向下调整对，堆顶的元素下沉，和它的左右子节点进行比较，和左右子节点中较大的进行交换，
	 * 继续下沉，直到干不过子节点，或者到达了叶子节点，结束
	 */
	
	public static class MaxHeap {
		private int heapSize;
		private int[] heap;
		private int limit;
		
		public MaxHeap(int limit) {
			this.limit = limit;
			this.heap = new int[limit];
			this.heapSize = 0;
		}
		
		
		public  boolean isEmpty() {
			return heapSize == 0;
		}
		
		public boolean isFull() {
			return heapSize == limit;
		}
		
		public void push(int value) {
			if(this.isFull()) {
				throw new RuntimeException("heap is full");
			}
			heap[heapSize] = value;
			heapInsert(heap, heapSize);
			heapSize++;
		}
		
		
		public int pop() {
			if(this.isEmpty()) {
				throw new RuntimeException("heap is empty");
			}
			int ans = heap[0];
			swap(heap, 0, --heapSize); // 第一个元素和堆的最后一个元素交换
			// 向下调整堆
			heapIfy(heap, 0);
			return ans;
		}
		/**
		 * 向上调整堆，
		 * 在index的位置加入了元素，去调整吧
		 * @param heap
		 * @param index
		 */
		public void heapInsert(int heap[], int index) {
			while(heap[index] > heap[(index - 1) / 2]) { // 如果当前节点的值大于父节点或者到达了根节点，交换，继续向上调整
				swap(heap, index, (index-1) / 2);
				index = (index-1) / 2;
			}
		}
		/**
		 * 
		 * @param arr
		 * @param index 插入堆元素的位置
		 */
		public void heapIfy(int arr[], int index) {
			int left = index * 2 + 1; // 左节点的位置
			while(left < heapSize) { // 左节点存在，右节点可能在，也可能不在
				// 求左右节点中谁最大。
				// 右节点存在, 且比左节点大，则返回右节点的index， 否则返回左节点的index
				// 右节点不存在，则返回左节点的index	
				int largestIndex = left + 1 < heapSize ? (arr[left + 1] > arr[left] ? left + 1 : left) : left;
				if(arr[largestIndex] <= arr[index]) { // 当前节点的值大于等于左右节点中较大的值, 直接退出
					break;
				}
				swap(arr, index, largestIndex);  // 当前节点的值大于左右节点中较大的值, 交换
				index = largestIndex;
				left = 2 * index + 1;
			}
		}
		
		public void swap(int arr[], int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}
}
