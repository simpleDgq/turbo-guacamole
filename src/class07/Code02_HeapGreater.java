package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Code02_HeapGreater {
	/**
	 * 手动改写堆
	 * 1. 任意修改一个位置的元素，能够自动调整堆
	 * 2. 能够任意删除一个位置的元素，删除之后也能够自动调整堆 
	 */
	
	public static class HeapGreater<T> {
		private ArrayList<T> heap; // 堆，存放元素
		private int heapSize;
		private HashMap<T, Integer> indexMap; // 反向索引表， 记录每个元素在对中的位置
		private Comparator<T> comparator; // 比较器，定义加入堆中的元素的比较规则
		
		public HeapGreater(Comparator<T> comparator) {
			this.heap = new ArrayList<T>();
			this.heapSize = 0;
			this.indexMap = new HashMap<T, Integer>();
			this.comparator = comparator;
		}
		
		public boolean isEmpty() {
			return this.heapSize == 0;
		}
		
		
		public int size() {
			return this.heapSize;
		}
		
		public boolean contains(T obj) {
			return this.heap.contains(obj);
		}
		
		public T peek() {
			return this.heap.get(0);
		}
		
		public void push(T obj) {
			// 放入堆中的同时，反向索引表也记录下位置
			heap.add(obj);
			indexMap.put(obj, heapSize);
			// 向上调整堆
			heapInsert(heapSize);
			heapSize++;
		}
		
		public T pop() {
			T ans = heap.get(0);
			swap(0, heapSize - 1); // 0和最后一个元素互换
			heap.remove(heapSize - 1); // 从堆中删除最后一个元素
			indexMap.remove(ans); // 从反向索引表中删除对应的元素索引
			heapSize--;
			heapIfy(0); // 向下调整对
			return ans;
		}
		
		/**
		 * remove的时候，将要被remove的元素和最后一个元素交换，
		 * 然后重新调整堆
		 * @param obj
		 */
		public void remove(T obj) {
			int index = indexMap.get(obj);
			swap(index, heapSize - 1); // 将要被remove的元素和最后一个元素交换
			heap.remove(heapSize - 1); // 堆中干掉这个元素
			indexMap.remove(obj); // 从反向索引表中也干掉这个元素
			heapSize--;
			// 重新调整堆
			resign(obj);
		}
		/**
		 * 某个元素obj发生了改变，重新调整堆
		 * 向上或者向下调整
		 * @param obj
		 */
		public void resign(T obj) {
			int index = indexMap.get(obj);
			heapInsert(index);
			heapIfy(index);
		}
		
		
		public void heapInsert(int index) {
			while(comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) { // 如果新加入的节点比它的父节点小，则交换，继续往上
				swap(index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}
		
		public void heapIfy(int index) {
			int left = 2 * index + 1;
			while(left < heapSize) { // 当前节点还存在左子节点
				// 找左右子节点中较大的
				int largestIndex = left + 1 < heapSize ? // 存在右子节点
													comparator.compare(heap.get(left), heap.get(left + 1)) < 0 ? left + 1 : left // 右子节点比左子节点大 或者小 
													: left; // 不存在右子节点
				
				if(comparator.compare(heap.get(largestIndex), heap.get(index)) < 0) { // 如果子节点较大的，还小于等于当前节点，那么直接退出，不用交换
					break;
				}
				swap(index, largestIndex); // 与较大的子节点进行交换
				left = 2 * index + 1;
			} 
		}
		
		public void swap(int i, int j) {
			T temp = heap.get(i);
			heap.set(i, heap.get(j));
			heap.set(j, temp);
			// 更新反向索引表中，元素的位置
			indexMap.put(heap.get(j), j);
			indexMap.put(heap.get(i), i);
		}
	}
}
