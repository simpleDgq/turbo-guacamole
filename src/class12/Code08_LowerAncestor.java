package class12;

public class Code08_LowerAncestor {

	/**
	 * 给定一棵二叉树的头节点head，和另外两个节点a和b，返回a和b的最低公共祖先
	 * 
	 * 分析可能性:
	 * 与X无关(X不是最近公共祖先)：
	 * 1. 最近公共祖先在左树，a和b都在左树，左树上有答案节点，要搜集
	 * 2. 最近公共祖先在右树，a和b都在右树，右树上有答案节点，要搜集
	 * 与X有关(X是最近公共祖先):
	 * 1. a在左树，b在右树
	 * 2. b在左树，a在右树
	 * 2. X就是a或者b
	 * 
	 * 分析要搜集的信息:
	 * 左树是否找到a，是否找到b
	 * 右树是否找到a，是否找到b
	 * 最近公共祖先节点node
	 * 
	 * 取并集:
	 * findA 树上是否找到A
	 * findB 树上是否找到B
	 * node // 公共祖先，答案节点
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
		boolean findA;
		boolean findB;
		Node node;
		public Info(boolean findA, boolean findB, Node node) {
			this.findA = findA;
			this.findB = findB;
			this.node = node;
		}
	}
	
	public static Node lowestAncestor(Node head, Node A, Node B) {
		if(head == null || A == null || B == null) {
			return null;
		}
		return process(head, A, B).node;
	}
	public static Info process(Node X, Node A, Node B) {
		if(X == null) { // 空树，好设置
			return new Info(false, false, null);
		}
		Info leftInfo = process(X.left, A, B);
		Info rightInfo = process(X.right, A, B);
		// 以X为头节点的整棵树，构造Info
		// 以X为头节点的整颗树，是否发现了A和B
		boolean findA = leftInfo.findA || rightInfo.findA || X == A;
		boolean findB = leftInfo.findB || rightInfo.findB || X == B;
		Node node = null;
		if(leftInfo.node != null) { // 左树上有答案
			node = leftInfo.node;
		} else if(rightInfo.node != null) { // 右树上有答案
			node = rightInfo.node;
		} else {
			if(findA && findB) { // 左树、右树都没有答案, 又找到了a和b, 那么答案一定是X
				node = X;
			}
		}
		
		/**
		 * 上面的方法写法简单，下面这种更好理解
		 */
//		if(findA && findB) { // 只有在findA且findB的情况下，才有答案
//			if(X == A || X == B) { // X节点是a或者b
//				node = X;
//			} else if(leftInfo.findA && leftInfo.findB) { // 左树上找到了a和b
//				node = leftInfo.node;
//			} else if(rightInfo.findA && rightInfo.findB) { // 右树上找到了a和b
//				node = rightInfo.node;
//			} else if(leftInfo.findA && rightInfo.findB) { // 左a右b
//				node = X;
//			} else if(leftInfo.findB && rightInfo.findA) { // 左b右a
//				node = X;
//			}
//		}
		return new Info(findA, findB, node);
	}
}
