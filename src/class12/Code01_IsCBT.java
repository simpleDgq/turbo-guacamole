package class12;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBT {
	/**
	 * Complete Binary Tree
	 * 
	 * 判断一颗二叉树是不是完全二叉树
	 * 定义：1. 每一层都是满的
	 * 		2. 如果有不满的层。一定是最后一层，而且节点都是从左往右按顺序排列的
	 * 思路: 
	 * 层次遍历过程中：
	 * 1. 如果一个节点只有右节点，但是没有左节点，那么这棵树一定不是完全二叉树
	 * 2. 当第一次遇到左右节点不双全的节点的时候，该节点后面的节点一定都是叶子节点。
	 * 违反这两点其中的一点，都不是完全二叉树。
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
	
	public static boolean isCBT(Node head) {
		if(head == null) {
			return true;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(head);
		Node node = null;
		boolean isCBT = true;
		boolean firstNoLeftOrRight = false;
		while(!queue.isEmpty()) {
			node = queue.poll();
			if(node.left != null) {
				queue.add(node.left);
			}
			if(node.right != null) {
				queue.add(node.right);
			}
			if(node.left == null && node.right != null) { // 1. 有右但没左
				isCBT = false;
				break;
			}
			// 2. 遇到了第一个没有左或右节点的节点之后，如果后面的节点还有左孩子或者右孩子，那么一定不是叶子节点，返回false
			if(firstNoLeftOrRight && (node.left != null || node.right != null)) {
				isCBT = false;
				break;
			}
			if(node.left == null || node.right == null) { // 第一次遇到左右节点不双全的节点， 判断它后面的节点是不是都是叶子节点
				firstNoLeftOrRight = true; // 这个判断放到最后才正确. 
				// 因为当遇到一个左或者右节点不为空的节点之后，需要从该节点的后面一个节点开始判断，剩下的节点是否是叶子节点，
				// 下一个出队列的节点就是需要判断是否是叶子的节点
			}
		}
		return isCBT;
	}
	
	/**
	 * 用二叉树递归套路解决
	 * 
	 * 分析可能性：
	 * 一颗二叉树是完全二叉树可以分为四种可能性：
	 * 1. 左树满，右树满，左树的高度 == 右树的高度
	 * 2. 左树满，右树满，左树的高度 == 右树的高度 + 1
	 * 3. 左树完，右树满，左树的高度 == 右树的高度 + 1
	 * 4. 左树满，右树完，左树的高度 == 右树的高度
	 * 
	 * 要搜集的信息：
	 * 左树是否满、完，右树是否满、完
	 * 左树和右树高度
	 * 
	 * 并集：
	 * isFull、isComplete、height
	 */
	public static class Info {
		boolean isFull;
		boolean isComplete;
		int hegiht;
		public Info(boolean isFull, boolean isComplete, int height) {
			this.isFull = isFull;
			this.isComplete = isComplete;
			this.hegiht = height;
		}
	}
	public static boolean isCBT2(Node head) { 
		if(head == null) {
			return true;
		}
		return process(head).isComplete;
	}
	public static Info process(Node X) {
		if(X == null) { // 空树，Info好设置
			return new Info(true, true, 0);
		}
		// 递归收集左右子树信息
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		// X节点为头的树，构造Info
		// X节点为头的树是否满，需要满足左树、右树都是满的、而且高度相等
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.hegiht == rightInfo.hegiht;
		// X节点为头的树的高，左树和右树的高最大值 + 1
		int height = Math.max(leftInfo.hegiht, rightInfo.hegiht) + 1;
		boolean isComplete = false;
		if(leftInfo.isFull && rightInfo.isFull && leftInfo.hegiht == rightInfo.hegiht) {
			isComplete = true;
		} else if(leftInfo.isFull && rightInfo.isFull && leftInfo.hegiht == rightInfo.hegiht + 1) {
			isComplete = true;
		} else if(leftInfo.isFull && rightInfo.isComplete && leftInfo.hegiht == rightInfo.hegiht) {
			isComplete = true;
		} else if(leftInfo.isComplete && rightInfo.isFull && leftInfo.hegiht == rightInfo.hegiht + 1) {
			isComplete = true;
		}
		return new Info(isFull, isComplete, height);
	}
	
	public static void main(String args[]) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 10000;
		for(int i = 1; i <= testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if(isCBT2(head) != isCBT(head)) {
				System.out.println("oops");
			}
		}
	}
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}
	public static Node generate(int level, int maxLevel, int maxValue) {
		if(level > maxLevel || Math.random() <  0.5) {
			return null;
		}
		Node head = new Node((int)(maxValue * Math.random()));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}
}
