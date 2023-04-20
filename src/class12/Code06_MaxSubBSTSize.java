package class12;

public class Code06_MaxSubBSTSize {
	/**
	 * 一颗数整体不是搜索二叉树，但是它的子树有可能是搜索二叉树，返回节点数最多的搜索二叉树的节点数量。
	 * 
	 * 思路:
	 * 1. 和节点X无关(以X为头结点的整棵树不是搜索二叉树), 那么最大节点数量是左树的最大的搜索二叉树节点数和右树的最大搜索树的节点数，取最大值
	 * 2. 和节点X有关(以X为头结点的整棵树是搜索二叉树), 那么最大节点数量是左树的节点数 + 右树的节点数 + 1
	 *    同时，又要满足左树是搜索二叉树、右树是搜索二叉树、左树的最大值小于X、右树的最小值大于X
	 *    
	 * 要搜集的信息：
	 * 左树最大搜索二叉树节点数maxBSTSize
	 * 右树最大搜索二叉树节点数maxBSTSize
	 * 左树的节点数size
	 * 右树的节点数size
	 * 左树是否是搜索二叉树isBST，右树是否是搜索二叉树isBST ==> 左树和右树是否是搜索二叉树，可以通过maxBSTSize 是否等于size判断，所以isBST不用搜集。
	 * 左树的最大值，右树的最小值
	 * 
	 * 取并集，要搜集的信息为：
	 * maxBSTSize
	 * size
	 * max
	 * min
	 * 
	 * 对于X为头的树，构造Info
	 * maxBSTSize 
	 *   当X为头的整棵树都是平衡的时候，结果为左树的节点数 + 右树的节点数 + 1
	 *   否则为左树的maxBSTSize、右树的maxBSTSize，两者中的最大值。
	 *   化简：平衡的情况下求得左树的节点数 + 右树的节点数 + 1， 
	 *        求左树的maxBSTSize、右树的maxBSTSize
	 *        然后取三者的最大值。初始值都是-1。
	 *   
	 * size 为左树size + 右树size + 1
	 * max 为 X.value、左树的最大值、右树的最大值 三者中的最大值
	 * min 为 X.value、 左树的最小值、右树的最小值 三者中的最小值
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
		int maxBSTSize;
		int max;
		int min;
		int size;
		public Info(int maxBSTSize, int max, int min, int size) {
			this.maxBSTSize = maxBSTSize;
			this.max = max;
			this.min = min;
			this.size = size;
		}
	}
	public static int maxSubBSTSize(Node head) {
		if(head == null) {
			return 0;
		}
		return process(head).maxBSTSize;
	}
	public static Info process(Node X) {
		if(X == null) { // X为空树，Info不好构造（max和min不知道怎么设置), 返回null
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		// 以X为头节点的树，构造Info
		// 以X为头节点的整颗数，求最大、最小值、以及节点数量
		int maxBSTSize = 0;
		int max = X.value;
		int min = X.value;
		int size = 1;
		if(leftInfo != null) {
			max = Math.max(max, leftInfo.max);
			min = Math.min(min, leftInfo.min);
			size += leftInfo.size;
		}
		if(rightInfo != null) {
			max = Math.max(max, rightInfo.max);
			min = Math.min(min, rightInfo.min);
			size += rightInfo.size;
		}
		int p1 = -1;
		if(leftInfo != null) {
			p1 = leftInfo.maxBSTSize;
		}
		int p2 = -1;
		if(rightInfo != null) {
			p2 = rightInfo.maxBSTSize;
		}
		int p3 = -1;
		boolean leftBST = leftInfo == null ? true : leftInfo.maxBSTSize == leftInfo.size;
		boolean rightBST = rightInfo == null ? true : rightInfo.maxBSTSize == rightInfo.size;
		if(leftBST && rightBST) { // 以X为头的左右子树都是平衡的
			boolean leftMaxLessX = leftInfo == null ? true : leftInfo.max < X.value;
			boolean rightMinMoreX = rightInfo == null ? true : rightInfo.min > X.value;
			if(leftMaxLessX && rightMinMoreX) { // 整棵树都平衡
				int leftSize = leftInfo == null ? 0 : leftInfo.size;
				int rightSize = rightInfo == null ? 0 : rightInfo.size;
				p3 = leftSize + rightSize + 1;
			}
		}
		maxBSTSize = Math.max(p1, Math.max(p2, p3));
		return new Info(maxBSTSize, max, min, size);
	}
}
