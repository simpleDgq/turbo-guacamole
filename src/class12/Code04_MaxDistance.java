package class12;


public class Code04_MaxDistance {
	/**
	 * 给定一颗二叉树，任意两个节点都有距离（从一个节点到任意一个节点，整条路上的节点数量），求最大距离。
	 * 
	 * 思路：
	 * 以X为头节点，
	 * 1. 最大距离和X无关，那么最大距离在左树或者右树上，需要求左树的最大距离和右树的最大距离；
	 * 2. 最大距离和X有关，最大距离经过X节点，从左树到达右树，这种情况下，最大距离是左树距离X的最大距离 + 右树距离X的最大距离 + 1
	 *    左树距离X的最大距离就是左树的高度；右树距离X的最大距离就是右树的高度
	 *    
	 * 对于左树，要搜集最大距离maxDistane和height
	 * 对于右树，要搜集最大距离maxDistane和height
	 * 
	 * 取并集：
	 * 要搜集的信息是：maxDistane和height
	 * 
	 * 对于X节点为头的整棵树，构造这些信息，最大距离就是左树最大距离、右树最大距离以及最大距离是左树距离X的最大距离 + 右树距离X的最大距离 + 1 三者的最大值
	 * height就是 左树高度和右树高度最大值 + 1
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
		int maxDistance;
		int height;
		public Info(int maxDistane, int height) {
			this.maxDistance = maxDistane;
			this.height = height;
		}
	}
	
	public static int maxDistance(Node head) {
		if(head == null) {
			return 0;
		}
		return process(head).maxDistance;
	}
	
	public static Info process(Node X) {
		if(X == null) { // 空树，Info好设置，maxDistanc和height都是0
			return new Info(0, 0);
		}
		Info leftIfno = process(X.left);
		Info rightIfno = process(X.right);
		// 以X节点为头的整棵树，构造Info
		int maxDistane = Math.max(Math.max(leftIfno.maxDistance, rightIfno.maxDistance), 
				leftIfno.height + rightIfno.height + 1);
		int height = Math.max(leftIfno.height, rightIfno.height) + 1;
		return new Info(maxDistane, height);
	}
}
