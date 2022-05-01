package class12;

public class Code02_IsBalancedTree {
	/**
	 * 判断一颗二叉树是否是平衡二叉树
	 * 定义：左右子树的高度差的绝对值不超过1，则是平衡二叉树
	 * 思路：
	 * 判断整棵树是不是平衡的：
	 * 需要满足 以下条件：
	 * 1. 左树是平衡的
	 * 2. 右树也是平衡的
	 * 3. 左右树的高度差的绝对值不超过1
	 * 
	 * 需要从左树搜集两个信息：isBalanced, height
	 * 右树也是一样。
	 * 
	 * 从左树搜集到这些信息后，针对X，也需要计算出这些信息，判断以X为头的树是不是平衡的，以及计算整棵树的高度。
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
	/**
	 * 要搜集的节点信息
	 */
	public static class Info {
		boolean isBalanced;
		int height;
		public Info(boolean isBanlanced, int height) {
			this.isBalanced = isBanlanced;
			this.height = height;
		}
	}
	
	public static boolean isBalancedTree(Node head) {
		if(head == null) {
			return true;
		}
		return process(head).isBalanced;
	}
	
	public static Info process(Node head) {
		if(head == null) { // 空树，设置isBalanced和高度，好设置（有的题目不好设置，就直接返回null）
			return new Info(true, 0);
		}
		Info leftInfo = process(head.left); // 左树搜集
		Info rightInfo = process(head.right);// 右树搜集
		// 构造X节点的相关信息
		int height = Math.max(leftInfo.height, rightInfo.height) + 1 ;
		boolean isBalanced = true;
		if(!leftInfo.isBalanced || !rightInfo.isBalanced 
				|| Math.abs(leftInfo.height - rightInfo.height) > 1) {
			isBalanced = false;
		}
		return new Info(isBalanced, height);
	}
}
