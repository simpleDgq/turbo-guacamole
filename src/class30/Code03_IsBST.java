package class30;

import class30.Code01_MorrisTraversal.Node;

public class Code03_IsBST {
	
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	/**
	 * 判断一颗二叉树是不是搜索二叉树
	 * 
	 * 1. 二叉树递归套路解决（参考二叉树章节）
	 * 2. Morris遍历
	 *  中序遍历的结果是升序的，以前可以搞一个链表把中序遍历的结果存起来，
	 *  然后遍历这个链表，看是不是升序的，如果是，则是搜索二叉树。
	 *  使用Morris中序遍历，将打印行为变成对比行为，比较前一个节点和当前节点的值，
	 *  如果当前节点小于前一个节点的值，则不是搜索二叉树。
	 */
	public static boolean isBSTMorris(Node head) {
		if(head == null) {
			return true;
		}
		boolean ans = true;
		Node cur = head;
		Node mostRight = null;
		Node pre = null;
		while(cur != null) {
			mostRight = cur.left;
			if(mostRight != null) {
				while(mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if(mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			// 中序遍历打印行为，变成对比行为
			if(pre != null && pre.value >= cur.value) { // 上一个节点不为空，且上一个节点的值大于等于当前节点，则不是搜索二叉树
				ans = false; // 不能直接return, 因为有改树节点的过程，必须等Morris跑完了
				// 把节点指针改回来了才能返回，否则树的结构就被改坏了
			}
			pre.value = cur.value; // cur到下一个节点之前，记录值
			cur = cur.right;
		}
		return ans;
	}
}
