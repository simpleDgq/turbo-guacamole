package class16;

import java.util.HashMap;

public class Code09_DijkstraByHeap {
	
	/**
	 * 
	 * 使用heap实现D算法。
	 * 前一种实现的方法，使用HashMap，每次都要遍历HashMap，选一个distance最小的点。
	 * 
	 * 如果使用小根堆，存储的元素是(node, 距离)对象，按照距离进行排序 例如(a,0) 表示的是到a这个点的距离是0.
	 * 
	 * 每次从小根堆中弹出一个对象，遍历节点的所有的边，考察能够到达的节点，计算出距离，如果比小根堆中的小则更新，对堆进行调整；
	 * 如果小根堆中不存在，则直接加入，对堆进行调整；如果小根堆中存在该对象但是距离是-1，则ignore；考察完所有的边，搜集答案。
	 * 
	 * 反向索引表中，如果堆中弹出了某个元素，不删除这个元素，只是将距离改成-1。
	 * 表示这个节点曾经进来过，而且已经出去了。
	 *
	 * 
	 */
	// 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
	// size为图的大小
	public HashMap<Node, Integer> D(Node head, int size) {
		HashMap<Node, Integer> ans = new HashMap<Node, Integer>();
		RecordHeap heap = new RecordHeap(size);
		heap.addOrUpdateOrIgnore(new Record(head, 0)); // 初始head,0入堆
		while(!heap.isEmpty()) {
			Record cur = heap.pop();
			Node node = cur.node;
			int distance = cur.distance;
			for(Edge edge : node.edges) {
				Node to = edge.to;
				heap.addOrUpdateOrIgnore(new Record(to, edge.weight + cur.distance));
			}
			ans.put(cur.node, distance);
		}
		return ans;
	}
	
	//小根堆中的Record
	public class Record {
		Node node;
		int distance;
		public Record(Node node, int distance) {
			this.node = node;
			this.distance = distance;
		}
	}
	
	public class RecordHeap {
		Record[] heap;
		HashMap<Record, Integer> indexMap;
		int heapSize;
		public RecordHeap(int size) {
			this.heap = new Record[size];
			this.indexMap = new HashMap<Record, Integer>();
			this.heapSize = 0;
		}
		
		public boolean isEmpty() {
			return this.heapSize == 0;
		}
		
		// 添加或者更新元素
		public void addOrUpdateOrIgnore(Record record) {
			if(record == null) {
				return;
			}
			if(inHeap(record)) { // update 如果在堆中，且距离不是-1，说明没有被弹出过，直接更新，然后调整堆
				int index = indexMap.get(record);
				if(record.distance >= heap[index].distance) { // 如果比小根堆中已经存在的元素的距离小，才更新，否则直接退出
					return;
				}
				heap[index] = record;
				heapInsert(index);
				//heapify(index); // 不需要，因为进入堆的元素的距离，只会往小了改，所以不可能再需要下沉
			} 
			if(!isEntered(record)) { // add 从来没有进入过堆，则是添加操作，指行heapInsert
				heap[heapSize] = record;
				indexMap.put(record, heapSize);
				heapInsert(heapSize);
				heapSize++;
			} // ignore
		}
		
		// 弹出一个元素
		public Record pop() {
			Record ans = heap[0];
			swap(indexMap.get(ans), heapSize - 1);
			indexMap.put(ans, -1);
			heap[heapSize - 1] = null;
			heapSize--;
			heapify(0);
			return ans;
		}
		
		// Record 不在堆里面，而且没有被弹出过
		// 如果被弹出过，indexMap里面记录的距离是-1
		public boolean inHeap(Record record) {
			return isEntered(record) && indexMap.get(record) != -1;
		}
		// 是否进入过堆
		public boolean isEntered(Record record) {
			return indexMap.containsKey(record);
		}
		
		public void heapInsert(int index) {
			while(heap[index].distance < heap[(index - 1) / 2].distance) {
				swap(index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}
		
		public void heapify(int index) {
			int left = 2 * index + 1;
			while(left < heapSize) {
				int smallestIndext = left + 1 < heapSize ? (heap[left].distance < heap[left + 1].distance
						? left : left + 1) : left;
				if(heap[smallestIndext].distance >= heap[index].distance) {
					break;
				}
				index = smallestIndext;
				left = 2 * index + 1;
			}
		}
		
		// 交换heap中的两个元素，同时记得更新indexMap
		public void swap(int i, int j) {
			Record tmp = heap[i];
			heap[i] = heap[j];
			heap[j] = tmp;
			// 跟下indexMap
			indexMap.put(heap[i], i);
			indexMap.put(heap[j],j);
		}
	}
}
