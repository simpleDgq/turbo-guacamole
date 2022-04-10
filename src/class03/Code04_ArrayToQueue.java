package class03;

public class Code04_ArrayToQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// 使用数组实现队列
	public class MyQueue {
		private int arr[];
		private int begin;
		private int end;
		private int size;
		private int limit;
		
		public MyQueue(int limit) {
			this.limit = limit;
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
	}
}
