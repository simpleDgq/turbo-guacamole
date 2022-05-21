package class03;

import java.util.Stack;

public class Code06_TwoStackImplementsQueue {

	public static void main(String[] args) {
		TwoStackToQueue test = new TwoStackToQueue();
		test.add(2);
		test.add(2);
		test.poll();
		test.add(3);
		test.add(3);
		
		System.out.println(test.poll());
		System.out.println(test.poll());
		System.out.println(test.poll());
		System.out.println(test.poll());


	}
	
	/**
	 * 两个栈实现队列。
	 * 思路:
	 * 1. 准备两个栈，push栈和pop栈
	 * 2. 加数据的时候，直接加入push栈
	 * 3. 取数据的时候，先将push栈中的数据全部倒入pop栈中(注意: 只有在pop栈是空的时候才能倒数据, 如果pop不为空，则直接取数据)
	 * 然后从pop栈取数据
	 */
	
	public static class TwoStackToQueue {
		
		private Stack<Integer> pushStack;
		private Stack<Integer> popStack;
		
		public TwoStackToQueue() {
			pushStack = new Stack<Integer>();
			popStack = new Stack<Integer>();
		}
		
		public void add(int value) {
			pushStack.push(value);
		}
		
		public int poll() {
			if(pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("Queue is empty!");
			}
			// 将push栈中的数据倒入pop栈中
			pushToPop();
			// 去pop栈中的数据返回
			return popStack.pop();
		}
		
		public int peek() { 
			if(pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("Queue is empty!");
			}
			// 将push栈中的数据倒入pop栈中
			pushToPop();
			// 去pop栈中的数据返回
			return popStack.peek();
		}
		
		private void pushToPop() {
			if(popStack.isEmpty()) { // 只有pop栈位kong的时候，才能倒数据
				while(!pushStack.isEmpty()) { // 将push栈的所有数据倒入pop中
					popStack.push(pushStack.pop());
				}
			}
		}
	}

}
