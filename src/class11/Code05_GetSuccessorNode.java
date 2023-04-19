package class11;

public class Code05_GetSuccessorNode {
	
	/**
	 * 给你二叉树中的某个节点，返回该节点的后继节点
	 * 
	 * 二叉树结构如下定义：
	 * Class Node {
	 *   V value;
	 *   Node left;
	 *   Node right;
	 *   Node parent;
	 * }
	 * 
	 * 后继节点定义：在中序遍历中，一个节点X的后一个节点就是X的后继。
	 * 
	 * 后继节点分为两种情况:
	 * 1) X节点有右节点，那么它的后继一定是它右子树的最左节点（因为中序遍历是左中右）
	 * 2) X节点没有右节点，那么它的后继一定是它一直往上，遇到的第一个是左节点的父节点。
	 * 
	 * 1. 如果直接先求中序遍历的结果，然后再从中序遍历的结果中查X的后一个节点，
	 * 时间复杂度是O(N)
	 * 
	 * 2. 每个节点的指针都有一个parent指针，指向它的父节点，拿e来说，只需要向上走两步就能拿到它的后继节点a，
	 * 而且时间复杂度为O(K)，K表示的是要走几步。
	 * 每个节点有parent指针，X节点时哪里都可以去的，既然哪里都能去。有没有什么方法直接能够走到后继节点，把后继节点揪出来？
	 * 
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
		if(node.right != null) { // 有右子树。后继节点一定是该节点的右子树的最左节点
			ans = getLeftMost(node.right);
		} else { // 无右子树。后继节点一定是它一直往上，遇到的第一个是左节点的父节点
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
	// 求某个节点的最左节点
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
