package class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
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

	/**
	 * 二叉树的序列化于反序列化 (先序、后序、以及层次遍历)
	 * 使用先序和后序可以序列化和反序列haul一颗二叉树，中序不行，因为两颗不同的树，中序序列可能一样。
	 * 思路：
	 * 1.在先序遍历过程中，用一个队列记录遍历到的每个节点，如果是空，就记录成null。
	 * 2.反序列化的时候，取每一个节点，如果为空，直接返回null；
	 * 如果不为空，则建立好这个节点，然后递归建立先建立左，挂在当前接的的左边，在建立右，挂在当前节点的右边。
	 */
	
	/**
	 * 使用先序进行序列化和反序列化
	 */
	// 先序，进行序列化
	public static Queue<Integer> preSerial(Node head) {
		Queue<Integer> ans = new LinkedList<Integer>();
		if(head == null) {
			return ans;
		}
		pre(ans, head);
		return ans;
	}
	public static void pre(Queue<Integer> ans, Node head) {
		if(head == null) {
			ans.add(null);
			return;
		}
		ans.add(head.value);
		// 遍历左子树
		pre(ans, head.left);
		// 遍历右子树
		pre(ans, head.right);
	}
	
	// 使用先序序列进行反序列化
	public static Node buildByPreQueue(Queue<Integer> ans) {
		if(ans == null || ans.isEmpty()) {
			return null;
		}
		return buildBypre(ans);
	}
	public static Node buildBypre(Queue<Integer> ans) {
		Integer value = ans.poll(); // 取出队列中的节点
		if(value == null) {
			return null;
		}
		// 建头
		Node head = new Node(value);
		// 建立好头结点的左子树，链接上头结点
		head.left = buildBypre(ans);
		// 建立好头结点的右子树，链接上头结点
		head.right = buildBypre(ans);
		return head;
	}
	
	/**
	 * 使用后序进行序列化和反序列化
	 */
	// 后序进行序列化
	public static Queue<Integer> posSerail(Node head) {
		Queue<Integer> ans = new LinkedList<Integer>();
		if(head == null) {
			return ans;
		}
		pos(ans, head);
		return ans;
	}
	public static void pos(Queue<Integer> ans, Node head) {
		if(head == null) {
			ans.add(null);
			return;
		}
		// 遍历左子树
		pos(ans, head.left);
		// 遍历右子树
		pos(ans, head.right);
		// 加入头
		ans.add(head.value);
	}
	
	// 后序进行反序列化
	public static Node buildByPosQueue(Queue<Integer> ans) {
		if(ans == null || ans.isEmpty()) {
			return null;
		}
		Stack<Integer> stack = new Stack<Integer>();
		// ans中存放的是左右头顺序 ==> 加到栈中，弹出的时候是头右左顺序，这样就能方便的先建立头节点，然后递归建立右、左子树
		while(!ans.isEmpty()) {
			stack.add(ans.poll());
		}
		return buildByPos(stack);
	}
	public static Node buildByPos(Stack<Integer> stack) {
		Integer value = stack.pop();
		if(value == null) {
			return null;
		}
		// 建立头
		Node head = new Node(value);
		// 递归建立右子树
		head.right = buildByPos(stack);
		// 递归建立左子树
		head.left = buildByPos(stack);
		return head;
	}
	
	/**
	 * 使用层次遍历进行序列化和反序列化
	 * 
	 * 1. 使用两个队列，一个队列用于层次遍历，一个用于存储序列化的结果
	 * 首先，头结点入队，并且值被记录在结果中；
	 * 然后，进行层次遍历，出队列，有左加左，有右加右；如果是null，则结果中加入null。
	 * 2. 反序列化的时候，先建立头节点，放入队列中；
	 * 然后从队列中弹出当前节点，依次建立该节点的左孩子和右孩子，在建立孩子节点的时候，如果序列化结果中存储的是null，
	 * 则当前节点的孩子直接设置成null就行，否则挂在左边或者右边。建立好的孩子也要入队，进行层次遍历。
	 */
	// 序列化
	public static Queue<Integer> levelSerial(Node head) {
		Queue<Integer> ans = new LinkedList<Integer>(); // 用于层次遍历过程中，存储序列化结果
		if(head == null) {
			return ans;
		}
		Queue<Node> queue = new LinkedList<Node>(); // 用于层次遍历过程中，存储节点
		queue.add(head);
		ans.add(head.value); // 注意这里不能放在while循环里面，否则每个节点会被加入ans两次
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			if(cur.left == null) {
				ans.add(null);
			} else {  // 层次遍历过程中，有左加左，有右加右
				queue.add(cur.left);
				ans.add(cur.left.value);
			}
			if(cur.right == null) { // 如果是null，层次遍历队列不加，但是序列化结果中需要加入null
				ans.add(null);
			} else {
				queue.add(cur.right);
				ans.add(cur.right.value);
			}
		}
		return ans;
	}
	
	// 反序列化
	public static Node buildByLevelQueue(Queue<Integer> ans) {
		if(ans == null || ans.isEmpty()) {
			return null;
		}
		Integer value = ans.poll();
		Node head = new Node(value);
		Queue<Node> queue = new LinkedList<Node>(); // 用于层次遍历过程中，存储节点
		queue.add(head); // 放入头结点
		while(!queue.isEmpty()) { // 从队列中弹出每一个节点，建立左右孩子
			Node cur = queue.poll();
			value = ans.poll();
			cur.left = value == null ? null : new Node(value); // 建立头节点的左孩子节点
			if(cur.left != null) { // 为null的时候，说明没有左右孩子了，不需要在建，不放人
				queue.add(cur.left);
			}
			value = ans.poll();
			cur.right = value == null ? null : new Node(value); // 建立头节点的右孩子节点
			if(cur.right != null) {
				queue.add(cur.right);
			}
		}
		return head;
	}
	
	// for test
		public static Node generateRandomBST(int maxLevel, int maxValue) {
			return generate(1, maxLevel, maxValue);
		}

		// for test
		public static Node generate(int level, int maxLevel, int maxValue) {
			if (level > maxLevel || Math.random() < 0.5) {
				return null;
			}
			Node head = new Node((int) (Math.random() * maxValue));
			head.left = generate(level + 1, maxLevel, maxValue);
			head.right = generate(level + 1, maxLevel, maxValue);
			return head;
		}

		// for test
		public static boolean isSameValueStructure(Node head1, Node head2) {
			if (head1 == null && head2 != null) {
				return false;
			}
			if (head1 != null && head2 == null) {
				return false;
			}
			if (head1 == null && head2 == null) {
				return true;
			}
			if (head1.value != head2.value) {
				return false;
			}
			return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
		}

		// for test
		public static void printTree(Node head) {
			System.out.println("Binary Tree:");
			printInOrder(head, 0, "H", 17);
			System.out.println();
		}

		public static void printInOrder(Node head, int height, String to, int len) {
			if (head == null) {
				return;
			}
			printInOrder(head.right, height + 1, "v", len);
			String val = to + head.value + to;
			int lenM = val.length();
			int lenL = (len - lenM) / 2;
			int lenR = len - lenM - lenL;
			val = getSpace(lenL) + val + getSpace(lenR);
			System.out.println(getSpace(height * len) + val);
			printInOrder(head.left, height + 1, "^", len);
		}

		public static String getSpace(int num) {
			String space = " ";
			StringBuffer buf = new StringBuffer("");
			for (int i = 0; i < num; i++) {
				buf.append(space);
			}
			return buf.toString();
		}

		public static void main(String[] args) {
			int maxLevel = 3;
			int maxValue = 100;
			int testTimes = 100000;
			System.out.println("test begin");
			for (int i = 0; i < testTimes; i++) {
				Node head = generateRandomBST(maxLevel, maxValue);
//				printTree(head);
				Queue<Integer> pre = preSerial(head);
				Queue<Integer> pos = posSerail(head);
				Queue<Integer> level = levelSerial(head);
//				System.out.println(level.toString());
				Node preBuild = buildByPreQueue(pre);
//				printTree(preBuild);
				Node posBuild = buildByPosQueue(pos);
//				printTree(posBuild);
				Node levelBuild = buildByLevelQueue(level);
//				printTree(levelBuild);
				if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
					System.out.println("Oops!");
				}
			}
			System.out.println("test finish!");
		}
}
