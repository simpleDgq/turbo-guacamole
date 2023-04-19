package class11;

import java.util.LinkedList;
import java.util.Queue;

public class Code04_TreeMaxWidth {
	
	/**
	 * 求一个二叉树的最大宽度
	 * 思路：三个变量
	 * curend: 记录当前层的最后一个节点
	 * nextend: 下一层的最后一个节点。
	 * count: 记录每一层的节点数
	 * 
	 * 1. 头结点入队，curend设置成头结点，nextend设置成null
	 * 2. 在层次遍历的过程中，头结点出队，有左入左，有右入右，入队的过程中将nextend指向下一层的最后一个节点
	 * 出队的时候，count++， 记录当前层的节点数
	 * 如果curend == node, 说明到达了当前层的最后一个节点，则将curend设置成 nextend，nextend变为null，max和count PK
	 * 出最大值
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
	
	public static int getMaxWidth(Node head) {
		if(head == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(head);
		Node curend = head; // 当前层的最后一个节点
		Node nextend = null; // 下一层的最后一个节点
		int count = 0; // 当前层的节点数
		int max = 0;
		Node node = null;
		while(!queue.isEmpty()) {
			node = queue.poll();
			count++;
			if(node.left != null) {
				queue.add(node.left);
				nextend = node.left;
			}
			if(node.right != null) {
				queue.add(node.right);
				nextend = node.right;
			}
			// 达到了当前层的最后一个节点
			if(node == curend) {
				max = Math.max(max, count);
				count = 0;
				curend = nextend; // 为下一层准备
				nextend = null;
			}
		}
		return max;
	}
}
