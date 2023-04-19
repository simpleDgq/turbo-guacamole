package class12;

public class Code07_MaxSubBSTHead {
	/**
	 * 求二叉树中最大的二叉搜索子树的头结点
	 * 
	 * 思路：
	 * 分析可能性：
	 * 和X节点无关(以X为头节点的树不是搜索二叉树):
	 * 1. 最大二叉搜索树在左树 
	 * 2. 最大二叉搜索树在右树
	 * 和X节点有关(以X为头结点的树是搜索二叉树)
	 * 1. 左树要是搜索二叉树
	 * 2. 右树要是搜索二叉树
	 * 3. 左树的最大值小于X，右树的最小值要大于X
	 * 
	 * 需要比较左右二叉搜索树、以X为节点的整棵树为二叉搜索树的结点数，求最大值
	 * 
	 * 要搜集的信息：
	 * 1. 左树是否是搜索二叉树，右树是否是搜索二叉树 ==> isBST
	 * 2. 左树最大值，右树最小值 ==> max, min
	 * 3. 左树的二叉搜索树的头节点
	 * 4. 右树的二叉搜索树的头节点
	 * 5. 左树二叉搜索树的节点数 ==> maxSubSize 
	 * 6. 右树二叉搜索树的节点数
	 * 
	 * 取并集：
	 * isBST ===> 如果maxSubSize == size, 说明整棵树是BST，所以isBST不用搜集
	 * head // 二叉搜索树头结点
	 * max
	 * min
	 * size // 整棵树的节点数
	 * maxSubSize // 最大搜索二叉树节点数
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
		Node head;
		int max;
		int min;
		int size;
		int maxSubSize;
		public Info(Node head,
				int max, int min, int size, int maxSubSize) {
			this.head = head;
			this.max = max;
			this.min = min;
			this.size = size;
			this.maxSubSize = maxSubSize;
		}
	}
	
	public static Node maxSubBSTHead(Node head) {
		if(head == null) {
			return null;
		}
		return process(head).head;
	}
	public static Info process(Node X) {
		if(X == null) { // 空树不好设置
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		// X为头的整棵树，构造info
		// 以X为头的整棵树，求最大和最小值
		Node head = null;
		int max = X.value;
		int min = X.value; // 以X为头的整棵树的最小值
		int size = 1; // 以X为头的整棵树的节点数，左树size + 右树size + 1
		int maxSubSize; // 以X为头的整棵树，最大搜索二叉树节点数（左树和右树的maxSubSize 以及 整棵树如果是搜索二叉树的节点数取最大值）
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
		// 左树最大搜索二叉树
		int p1 = -1;
		Node headLeft = null;
		if(leftInfo != null) {
			p1 = leftInfo.maxSubSize;
			headLeft = leftInfo.head; // 左树最大搜索二叉树头
		}
		// 右树最大搜索二叉树
		int p2 = -1;
		Node headRight = null;
		if(rightInfo != null) {
			p2 = rightInfo.maxSubSize;
			headRight = rightInfo.head; // 右树最大搜索二叉树头
		}
		// 以X为头的整棵树搜索二叉树
		int p3 = -1;
		Node headX = null;
		// X为头的左右子树是否是搜索二叉树
		boolean isLeftBST = leftInfo == null ? true : leftInfo.maxSubSize == leftInfo.size;
		boolean isRightBST = rightInfo == null ? true : rightInfo.maxSubSize == rightInfo.size;
		// 如果左右子树都是搜索二叉树
		if(isLeftBST && isRightBST) {
			// 左子树的最大值是否小于X节点的值
			boolean leftMaxLessX = leftInfo == null ? true : leftInfo.max < X.value;
			// 右子树的最小值是否大于X节点的值
			boolean rightMinMoreX = rightInfo == null ? true : rightInfo.min > X.value;
			// 如果是，说明以X为头的整棵树都是搜索二叉树，求整颗树的节点数量
			if(leftMaxLessX && rightMinMoreX) {
				int leftSize = leftInfo == null ? 0 : leftInfo.size;
				int rightSize = rightInfo == null ? 0 : rightInfo.size;
				p3 = leftSize + rightSize + 1;
				headX = X;
			}
		}
		// 左子树的最大搜索二叉树、右子树的最大搜索二叉树、以X为头结点的搜索二叉树
		// 三者PK出最大值
		maxSubSize = Math.max(p1, Math.max(p2, p3));
		// 看最大值等于哪个size,返回对应的搜索二叉树的头节点
		if(maxSubSize == p1) {
			head = headLeft;
		} else if(maxSubSize == p2) {
			head = headRight;
		} else if(maxSubSize == p3) {
			head = headX;
		}
		return new Info(head, max, min, size, maxSubSize);
	}
}
