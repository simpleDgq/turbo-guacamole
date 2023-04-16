package class10;

import java.util.Stack;

public class Code02_UnRecursiveTraveraiBT {
	
	/**
	 * 非递归实现二叉树的先序、中序、后序遍历
	 * 思路：
	 * 1. 先序，使用栈实现
	 * 头结点先入栈，然后出栈，出栈之后，如果出栈的当前节点存在右子树，则右子树节点入栈；
	 * 如果有左子树，则左子树入栈。（先右后左，出栈的时候才是先左后右）
	 * 2. 后序
	 * 在先序遍历中，是右子树先入栈，左子树后入栈
	 * 如果改成左子树先入栈，右子树后入栈，就可以得到头右左的序列，
	 * 那么将头右左的序列，出栈，然后加入到另一个栈中就可以得到左右头的序列，也就是后序遍历
	 * 3. 中序
	 * 从头结点开始，当前节点的左边界节点一直入栈，直到当前节点为null
	 * 当前节点为null，则从栈中弹出元素进行打印，同时cur变成当前节点的右节点，
	 * 然后从右节点开始又一直入栈
	 */
	public static class Node {
		int value;
		Node left;
		Node right;
		public Node(int value) {
			this.value = value;
			this.right = null;
			this.left = null;
		}
	}
	
	// 先序
	public static void pre(Node head) {
		if(head == null) {
			return;
		}
		Stack<Node> stack = new Stack<Node>();
		stack.push(head);
		while(!stack.isEmpty()) {
			Node cur = stack.pop();// 头出栈
			System.out.println(cur.value);
			if(cur.right != null) { // 右子树先入栈
				stack.push(cur.right);
			}
			if(cur.left != null) { // 左子树后入栈
				stack.push(cur.left);
			}
		}
	}
		
	// 后序
	public static void pos(Node head) {
		if(head == null) {
			return;
		}
		Stack<Node> stack = new Stack<Node>();
		Stack<Node> stack2 = new Stack<Node>();
		stack.push(head);
		while(!stack.isEmpty()) {
			Node cur = stack.pop();// 头出栈
			stack2.push(cur); // 不打印，而是以头右左的顺序进栈
			if(cur.left != null) { // 左子树先入栈
				stack.push(cur.left);
			}
			if(cur.right != null) { // 右子树后入栈
				stack.push(cur.right);
			}
		}
		while(!stack2.isEmpty()) {
			System.out.println(stack2.pop().value);
		}
	}
	
	//中序
	public static void in(Node head) {
		if(head == null) {
			return;
		}
		Stack<Node> stack = new Stack<Node>();
		Node cur = head;
		while(!stack.isEmpty() || cur != null) { // cur不为空，左边界节点一直入栈
			if(cur != null) {
				stack.push(cur);
				cur = cur.left;
			} else {
				cur = stack.pop();
				System.out.println(cur.value);
				cur = cur.right; // cur变成当前弹出节点的右节点，后面cur的left又会入栈
			}
		}
	}	
}
