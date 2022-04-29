package class11;

public class Code05_GetSuccessorNode {
	
	/**
	 * 得到某个节点的后继节点。
	 * 后继节点定义：在中序遍历中，一个节点X的后一个节点就是X的后继。
	 * 思路：分为两种情况
	 * 1. X节点有右节点，那么它的后继一定是它右树的最左节点（因为中序遍历是左中右）
	 * 2. X节点没有右节点，那么它的后继一定是它一直往上，遇到的第一个左节点的父节点。
	 */
	public static class Node{
		int value;
		Node left;
		Node right;
		Node parent;
		public Node(int value) {
			this.value = value;
			this.left = null;
			this.right = null;
			this.parent = null;
		}
	}
	
	/**
	 * 求一个节点的后继节点
	 */
	public static Node getSuccessorNode(Node node) {
		if(node == null) {
			return null;
		}
		Node ans = null;
		if(node.right != null) { // 有右子树
			ans = getLeftMost(node.right);
		} else { // 无右子树
			Node cur = node;
			while(cur != null) {
				if(cur.parent != null && cur.parent.left == cur) { // 当前节点是它父亲节点的左孩子（根据笔记的图来写代码）
					ans = cur.parent;
					break;
				}
				cur = cur.parent;
			}
		}
		return ans;
	}
	public static Node getLeftMost(Node node) {
		if(node == null) {
			return null;
		}
		while(node.left != null) { // 找一个节点的最左节点
			node = node.left;
		}
		return node;
	}
}
