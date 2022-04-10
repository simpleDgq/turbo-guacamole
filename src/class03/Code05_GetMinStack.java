package class03;

import java.util.Stack;

public class Code05_GetMinStack {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetMinStack stack = new GetMinStack();
		stack.push(1);
		stack.push(0);
		stack.push(2);
		stack.push(-1);
		stack.pop();
		stack.pop();
		System.out.print(stack.getMin());
	} 
	/**
	 * 实现一个特殊的栈，在基本概念的基础上，再实现返回栈中最小元素的功能
	 *	1. pop 、 push、getMin 操作的时间复杂度为O(1)
	 *	2. 设计的栈类型可以使用现成的栈结构
	 */
	
	/**
	 * 思路:
	 * 1.准备两个栈，一个数据栈和一个最小栈
	 * 2.当数据来的时候，往数据栈放入元素，同时和最小栈的栈顶元素进行比较，如果小于最小栈的栈顶元素
	 * 则放入最小栈，否则将最小栈的栈顶元素复制一份放入最小栈
	 * 3.出栈的时候，如果出栈的元素等于最小栈的栈顶元素，则最小栈也一起出栈，否则最小栈不出栈
	 * 4.最小栈的栈顶元素就是最小值
	 */
	
	public static class GetMinStack {
		private Stack<Integer> dataStack; // 数据栈
		private Stack<Integer> minStack; // 最小栈
		
		public GetMinStack() {
			this.dataStack = new Stack<Integer>();
			this.minStack = new Stack<Integer>();
		}
		
		public void push(int value) {
			dataStack.push(value);
			if(minStack.isEmpty()) { // 如果最小栈没有元素直接放入
				minStack.push(value);
			} else { // 最小栈有元素，取栈顶元素与value进行比较
				int min =  minStack.peek();
				if(value < min) {
					minStack.push(value);
				} else {
					minStack.push(min);
				}
			}
		}
		
		public int pop() {
			if(dataStack.isEmpty()) {
				throw new RuntimeException("Your stack is empty");
			}
			minStack.pop();
			return dataStack.pop();
		}
		
		public int getMin() {
			if(minStack.isEmpty()) {
				throw new RuntimeException("Your stack is empty");
			}
			return minStack.peek();
		}
	}

}
