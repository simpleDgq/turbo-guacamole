package class06;

import java.util.PriorityQueue;

public class Code04_SortArrayDistanceLessK {
	/**
	 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k,
	 * k相对于数组长度来说是比较小的。请选择一个合适的排序策略，对这个数组进行排序。
	 * 
	 * 思路:
	 * 一个数组，如果K = 5， 那么0位置的数，是最小的，它只可能从0-5的位置交换得来，不可能从0-5以外的位置，
	 * 移动得到，因为移动距离不能超过5。同理，1号位置的数，第二小，只可能从1-6位置的数交换得到。
	 * 
	 * 弄一个堆，将K + 1个数放进去，取出一个数，，放0号位置，再放一个，再取，放1号位置，
	 * 数放完了，从堆中取出剩下的数。就拍好序了
	 * 
	 * 时间复杂度: 堆中元素个数不超过K+1个，所以时间复杂度是O(N*logK)
	 * 
	 * 
	 * 
	 */
	
	public static void sortArrayDistanceLessK(int arr[], int  K) {
		if(K == 0 || arr == null || arr.length == 0) {
			return;
		}
		
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		int i = 0;
		// K > length的时候，将所有的元素入堆，然后出堆就行
		for(; i <= Math.min(K, arr.length - 1); i++) { // 0 - K+1 个元素入堆
			minHeap.add(arr[i]);
		}
		
		int index = 0;
		for(; i <= arr.length - 1; i++) {
			arr[index++] = minHeap.poll();  // 出一个元素放在原数组的index位置
			minHeap.add(arr[i]); // 加入数组中剩下的元素到堆
		}
		
		// 数组中没有元素可以加入了，将堆中其它的元素出堆，放入arr
		while(!minHeap.isEmpty()) {
			arr[index++] =  (int) minHeap.poll(); 
		}
	}
}
