package class17;

import java.util.Stack;

public class Code04_ReverseStackByRecursive {
	/**
	 * 给定一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数
	*	（微软）
	*  1. 有一个函数f，能够获取栈最底部的元素，并且将上面的元素盖下来
	*  2. 调用f之后，递归reverseStack，push f的返回值到stack
	 */
	
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if(stack.isEmpty()) {
			return result;
		} else {
			int last = f(stack);
			stack.push(result);
			return last;
		}
	}
	
	public static void reverseStack(Stack<Integer> stack) {
		if(stack.isEmpty()) {
			return;
		}
		int i = f(stack);
		reverseStack(stack);
		stack.push(i);
	}
}
