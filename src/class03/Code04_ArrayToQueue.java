package class03;

//使用数组实现队列
public class Code04_ArrayToQueue {
	/**
	 * 思路：
	 * 
	 * begin end 分别指向数组的头和尾
	 * size记录元素个数
	 * limit记录数组容量
	 * 
	 * 循环数组，每次push或者pop操作之后，都要计算下一个可用的位置
	 * （如果当前push/pop元素的位置，是数组的最后一个位置，
	 * 而且数组还有容量/数据，那么下一个位置应该从数组的0号开始）
	 *
	 */
	public class MyQueue {
		private int arr[];
		private int begin;
		private int end;
		private int size;
		private int limit;
		
		public MyQueue(int limit) {
			this.limit = limit;
			// begin end size自动初始化为0，不用再这里初始化
			arr = new int[limit];
		}
		
		public void push(int value) {
			if(size == limit) {	
				throw new RuntimeException("队列满了");
			}
			arr[end] = value; // 队尾加入元素
			// 计算下一次可用来push的位置
			end = (end < limit - 1) ? (end + 1) : 0; // 当前元素，还没有到队尾，则下一个位置是end+1，到了队尾，则下一个位置是0
			size++;
		}
		
		public int pop() {
			if(size == 0) {
				throw new RuntimeException("队列空了");
			}
			int ans = arr[begin]; // 队头取元素
			begin = (begin < limit - 1) ? (begin + 1) : 0;// 当前元素，还没有到队尾，则下一个位置是begin+1，到了队尾，则下一个位置是0
			size--;
			return ans;
		}
		
		public boolean isEmpty() {
			return size == 0;
		}
	}
}
