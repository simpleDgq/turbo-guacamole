package class12;

public class Code05_IsFullTree {
	/**
	 * 判断一颗二叉树是不是满二叉树。
	 * 性质：满二叉树，高度为h，那么节点数是2^h -1
	 * 
	 * 思路：
	 * 判断二叉树是不是满的,判断以X为头的整棵树的节点数是不是2^h - 1
	 * 需要从左树搜集 height和节点数nodes
	 * 需要从右树搜集 height和节点数nodes
	 * 
	 * 并集：
	 * height 和 nodes
	 * 
	 * 对于X为头的整棵树，构造Info
	 * height = max(左树高，右树高) + 1
	 * nodes = 左树节点数 + 右树节点数 + 1
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
		int height;
		int nodes;
		public Info(int height, int nodes) {
			this.height = height;
			this.nodes = nodes;
		}
	}
	
	public static boolean isFull(Node head) {
		if(head == null) {
			return true;
		}
		Info info = process(head);
		return ((1 << info.height ) - 1) == info.nodes;
	}
	
	public static Info process(Node X) {
		if(X == null) { // 空树，Info好设置，高和节点数都为0
			return new Info(0, 0);
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		// 以X为头的整棵树，构造Info
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		int nodes = leftInfo.nodes + rightInfo.nodes + 1;
		return new Info(height, nodes);
	}
	
	// 第二种方法
	// 收集子树是否是满二叉树
	// 收集子树的高度
	// 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
	public static boolean isFull2(Node head) {
		if (head == null) {
			return true;
		}
		return process2(head).isFull;
	}

	public static class Info2 {
		public boolean isFull;
		public int height;

		public Info2(boolean f, int h) {
			isFull = f;
			height = h;
		}
	}

	public static Info2 process2(Node h) {
		if (h == null) {
			return new Info2(true, 0);
		}
		Info2 leftInfo = process2(h.left);
		Info2 rightInfo = process2(h.right);
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		return new Info2(isFull, height);
	}
}
