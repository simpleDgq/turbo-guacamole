package class12;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBT {
	/**
	 * 判断一颗二叉树是不是完全二叉树
	 * 定义：1. 每一层都是满的
	 * 		2. 如果有不满的层。一定是最后一层，而且节点都是从左往右按顺序排列的
	 * 思路: 
	 * 层次遍历过程中：
	 * 1. 如果一个节点只有右节点，但是没有左节点，那么这棵树一定不是完全二叉树
	 * 2. 当第一次遇到左右节点不双全的节点的时候，该节点后面的节点一定都是叶子节点。
	 * 违反这两点其中的一点，都不是完全二叉树。
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
	
	public static boolean isCBT(Node head) {
		if(head == null) {
			return true;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(head);
		Node node = null;
		boolean isCBT = true;
		boolean firstNoLeftOrRight = false;
		while(!queue.isEmpty()) {
			node = queue.poll();
			if(node.left != null) {
				queue.add(node.left);
			}
			if(node.right != null) {
				queue.add(node.right);
			}
			if(node.left == null && node.right != null) { // 1. 有右但没左
				isCBT = false;
				break;
			}
			// 2. 遇到了第一个没有左或右节点的节点之后，如果后面的节点还有左孩子或者右孩子，那么一定不是叶子节点，返回false
			if(firstNoLeftOrRight && (node.left != null || node.right != null)) {
				isCBT = false;
				break;
			}
			if(node.left == null || node.right == null) { // 第一次遇到左右节点不双全的节点， 判断它后面的节点是不是都是叶子节点
				firstNoLeftOrRight = true; // 这个判断放到最后才正确. 
				//因为当遇到一个左或者右节点不为空的节点之后，需要判断的是该节点的后面一个节点，是否是叶子节点。 下一个出队列的就是需要判断的节点。
			}
		}
		return isCBT;
	}
}
