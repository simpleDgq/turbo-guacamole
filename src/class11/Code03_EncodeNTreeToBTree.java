package class11;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/encode-n-ary-tree-to-binary-tree/
public class Code03_EncodeNTreeToBTree {
	/**
	 * 将一颗多叉树用一颗二叉树进行表示，同时要能够根据二叉树恢复成多叉树
	 * 
	 * 思路: 左树右边界
	 */
	// N叉树节点
	public static class Node {
		public int val;
		public List<Node> children;
		public Node() {
		}
		public Node(int _val) {
			val = _val;
		}
		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	}
	// 二叉树节点
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	/**
	 * N tree to Binary tree
	 * 1. 遍历多叉树的第一个节点，先建立头节点，
	 * 然后遍历它的每一个孩子节点，将孩子节点都放在头结点的左树右边界上；就可以得到唯一的二叉树
	 * 遍历第一个孩子节点的时候，如果该节点还有孩子，则递归建立该节点的左边右子树
	 * @param head
	 * @return
	 */
	public static TreeNode NTreeToBTree(Node head) {
		if(head == null) {
			return null;
		}
		TreeNode BThead = new TreeNode(head.val); // 建立头
		BThead.left = en(head.children); // 遍历它的孩子，放在左树右边界上
		return BThead;
	}
	public static TreeNode en(List<Node> children) {
		TreeNode head = null; // 建立的左树右边界头结点
		TreeNode node = null;
		TreeNode cur = null;
		for(int i = 0; i < children.size(); i++) {
			node = new TreeNode(children.get(i).val);
			if(head == null) {
				head = node; // 搞定左树右边界头节点
				cur = node;
			} else {
				cur.right = node; // 挂在右边界上
				cur = node;
			}
			// children中的每一个节点，如果还有孩子，则递归建立，放在左树右边界上
			node.left = en(children.get(i).children);
		}
		return head;// 返回建立好的左树右边界的头结点，挂在头结点上
	}
	
	/**
	 * BT to NT
	 * 先建立NT的头，然后建它的孩子，把孩子挂在头上。
	 * 建孩子的过程中，遍历二叉树的左树右边界的每一个节点，将每一个节点放到children数组中；
	 * 需要注意的是，需要遍历到的每一个节点，如果还有左子树，则应该先递归建立该节点的孩子，也就是94行
	 * 递归。
	 */
	public static Node BTreeToNTree(TreeNode head) {
		if(head == null) {
			return null;
		}
		Node NThead = new Node(head.val);
		NThead.children = de(head.left); // 建立好头结点的孩子节点，挂上来
		return NThead;
	}
	public static List<Node> de(TreeNode head) {
		List<Node> children = new ArrayList<Node>();
		if(head == null) {
			return children;
		}
		while(head != null) {
			// 需要遍历到的每一个节点，如果还有左子树，则应该先递归建立该节点的孩子
			children.add(new Node(head.val, de(head.left))); // 每一次都建好一个节点以及该节点的孩子节点。new Node的时候，就建好了改节点以及它的孩子节点
			head = head.right;
		}
		return children;
	}
}
