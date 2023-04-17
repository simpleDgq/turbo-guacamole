package class11;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_LevelTraverBT {

	/**
	 * 二叉树的层次遍历 - 使用队列实现
	 * 
	 * Java中队列可以使用LinkedList
	 * 头结点先入队，在出队的时候，先判断有没有左孩子，有就入队，再判断有没有右孩子，有就入队。
	 * 
	 * 思路：
	 * 1. 使用队列，头结点入队
	 * 2. 队列不为空，头结点出队列，有左入左，有右入右。
	 * 3. 直到队列为空。
	 */
	
	public static class Node {
		int value;
		Node left;
		Node right;
		public Node(int value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}
	
	public static void levelTraveBT(Node head) {
		if(head == null ) {
			return;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(head);
		while(!queue.isEmpty()) {
			Node cur = queue.poll(); // 弹出节点，打印值
			System.out.println(cur.value);
			// 有左入左，有右入右
			if(cur.left != null) {
				queue.add(cur.left);
			}
			if(cur.right != null) {
				queue.add(cur.right);
			}
		}
	}
}
