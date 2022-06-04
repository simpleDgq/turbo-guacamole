package class30;

//本题测试链接 : https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
public class Code04_MinDepth {
	
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int x) {
			val = x;
		}
	}
	
	// 二叉树递归套路
	public static int minDepth(TreeNode head) {
		if(head == null) {
			return 0;
		}
		return process(head);
	}
	
	public static int process(TreeNode X) {
		if(X.left == null && X.right == null) {
			return 1;
		}
		// 以X为头构造信息
		int p1 = Integer.MAX_VALUE;
		if(X.left != null) {
			p1 = process(X.left);
		}
		int p2 = Integer.MAX_VALUE;
		if(X.right != null) {
			p2 = process(X.right);
		}
		return 1 + Math.min(p1, p2);
	}
	
	// Morris遍历
	public static int minDepth2(TreeNode head) {
		if (head == null) {
			return 0;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		int curLevel = 0;
		int minHeight = Integer.MAX_VALUE;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				int rightBoardSize = 1;
				while (mostRight.right != null && mostRight.right != cur) {
					rightBoardSize++;
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) { // 第一次到达
					curLevel++;
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // 第二次到达
					if (mostRight.left == null) {
						minHeight = Math.min(minHeight, curLevel);
					}
					curLevel -= rightBoardSize;
					mostRight.right = null;
				}
			} else { // 只有一次到达
				curLevel++;
			}
			cur = cur.right;
		}
		int finalRight = 1;
		cur = head;
		while (cur.right != null) {
			finalRight++;
			cur = cur.right;
		}
		if (cur.left == null && cur.right == null) {
			minHeight = Math.min(minHeight, finalRight);
		}
		return minHeight;
	}
}
