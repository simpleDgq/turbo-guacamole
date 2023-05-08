package class17;

import java.util.Stack;

public class Code04_ReverseStackByRecursive {
	/**
	 * 给定一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数
	*	（微软）
	*  1. 有一个函数f，能够获取栈最底部的元素，并且将上面的元素盖下来
	*  2. 调用f之后，递归reverseStack，push f的返回值到stack
	*  
	*  函数f：移除栈底元素并返回，下面的元素盖下来。
	*  例子: 栈中元素，1，2，3。递归过程分析。
	*  第一次调用f，弹出栈顶元素，用自己的result记住栈顶的值1，
	*  判断栈是否为空，不为空，则继续调用f。f1的last等待f2的返回
	*  第二次调用f，弹出栈顶元素，用自己的result记住栈顶的值2，
	*  判断栈是否为空，不为空，则继续调用f。f2的last等待f3的返回
	*  第三次调用f，弹出栈顶元素，用自己的result记住栈顶的值3，
	*  判断栈是否为空，为空，返回result.
	*  f3返回3给f2，f2中将2放进栈中，并且返回last
	*  f2返回3给f1，f2中将1放进栈中，并且返回last
	*  
	*  reverseStack函数：
	*  第一次调用reverseStack，判断栈是否为空，为空则直接返回，
	*  不为空，则调用f，i 记录栈底元素3；等待r2返回
	*  第二次调用reverseStack，判断栈是否为空，为空则直接返回，
	*  不为空，则调用f，i 记录栈底元素2；等待r3返回
	*  第三次调用reverseStack，判断栈是否为空，为空则直接返回，
	*  不为空，则调用f，i 记录栈底元素1；等待r4返回
	*  第四次调用reverseStack，判断栈是否为空，为空则直接返回。
	*  返回给r3，将r3记录的1放进栈中，然后r3返回给r2，r2将自己记录的
	*  2放进栈；最后返回给r1，r1将3放进栈。
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
