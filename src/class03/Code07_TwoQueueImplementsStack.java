package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code07_TwoQueueImplementsStack {

	public static void main(String[] args) {
		System.out.println("test begin");
		TwoQueueToStack myStack = new TwoQueueToStack();
		Stack<Integer> test = new Stack<>();
		int testTime = 1000000;
		int max = 1000000;
		for (int i = 0; i < testTime; i++) {
			if (myStack.isEmpty()) {
				if (!test.isEmpty()) {
					System.out.println("Oops");
				}
				int num = (int) (Math.random() * max);
				myStack.push(num);
				test.push(num);
			} else {
				if (Math.random() < 0.25) {
					int num = (int) (Math.random() * max);
					myStack.push(num);
					test.push(num);
				} else if (Math.random() < 0.5) {
					if (!(myStack.peek() == test.peek())) {
						System.out.println("Oops");
					}
				} else if (Math.random() < 0.75) {
					if (!(myStack.pop() == test.pop())) {
						System.out.println("Oops");
					}
				} else {
					if (myStack.isEmpty() != test.isEmpty()) {
						System.out.println("Oops");
					}
				}
			}
		}

		System.out.println("test finish!");

	}
	
	/**
	 * 两个队列实现栈。
	 * 
	 * 思路:
	 * 1. 准备两个队列，A和B
	 * 2. 加数据的时候,往A加
	 * 3. pop取数据的时候，留着A的最后一个元素，其它元素入B，
	 * A和B角色互换（peek的时候不需要互换角色）。
	 */
	
	public static class TwoQueueToStack {
		private Queue<Integer> A;
		private Queue<Integer> B;
		
		public TwoQueueToStack() {
			A = new LinkedList<Integer>();
			B = new LinkedList<Integer>();
		}
		
		public void push(int value) {
			A.add(value);
		}
		
		public int pop() {
			if(A.size() == 0) {
				throw new RuntimeException("Stack is empty!");
			}
			// 留着A队列的最后一个元素，其它元素进入B
			while(A.size() > 1) {
				B.add(A.poll());
			}
			// 取A的最后一个元素
			int ans = A.poll();
			// A 和 B 角色互换
			Queue<Integer> tmp = A;
			A = B;
			B = tmp;
			return ans;
		}
		
		public int peek() {
			if(A.size() == 0) {
				throw new RuntimeException("Stack is empty!");
			}
			// 留着A队列的最后一个元素，其它元素进入B
			while(A.size() > 1) {
				B.add(A.poll());
			}
			// 取A的最后一个元素
			int ans = A.peek();
			return ans;
		}
		
		public boolean isEmpty() {
			return A.isEmpty();
		}
	}

}
