package class12;

import java.util.ArrayList;

public class Code03_ISBST {
	/**
	 * 判断一棵树是否是搜索二叉树
	 * 定义：任意一个节点X，它左树的节点的值都小于X，右树都大于X，则是搜索二叉树
	 * 
	 * 思路：
	 * 一颗树是搜索二叉树，对于X来说，则它的左树必须是搜索二叉树，右树叶必须是
	 * 而且左树的最大值 < X; 右树的最小值 > X
	 * 
	 * 对左树来说，需要搜集：isBST, max
	 * 对右树来说：需要搜集：isBST, min
	 * 
	 * 左树和右树要搜集的信息不同，取并集, 需要搜集3个信息
	 * isBST, max, min
	 * 
	 * 从左树和右树搜集到这些信息之后，对于结点X为头的整棵树，也需要一样计算这些信息。（整棵树是否平衡，整棵树最大值和最小值）
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
	
	public static class Info {
		boolean isBST;
		int max;
		int min;
		public Info(boolean isBST, int max, int min) {
			this.isBST = isBST;
			this.max = max;
			this.min = min;
		}
	}
	
	public static boolean isBST(Node head) {
		if(head == null) {
			return true;
		}
		return process(head).isBST;
	}
	public static Info process(Node X) {
		if(X == null) {// 空树的时候，Info不好设置（max和min不知道怎么设置），直接返回null
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		// 构造X节点的相关信息
		boolean isBST = true;
		int max = X.value;
		int min = X.value;
		if(leftInfo != null) {
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
		}
		if(rightInfo != null) {
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
		}
		if((leftInfo != null && !leftInfo.isBST)  // 左树或者右树平衡，或者左树最大值大于等于X的值，
													//或者右树最小值小于等于X的值，那么X为头的整棵树就不是平衡的
				|| (rightInfo != null && !rightInfo.isBST)
				|| (leftInfo != null && leftInfo.max >= X.value)
				|| (rightInfo != null && rightInfo.min <= X.value)) {
			isBST = false;
		}
		return new Info(isBST, max, min);
	}
}
