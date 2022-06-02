package class30;

public class Code02_MorrisTrav {
	/**
	 * Morris实现先序、中序、后序遍历
	 * 
	 * Morris遍历的实质：
		就是在用当前节点左树上的最右节点的右指针状态来标记到底是
		第一次到达该节点，还是第二次到达该节点。
		如果是null，则是第一次到达，如果指向的是自己cur，则是第二次到达。
		
	 * ====
	 * Morris遍历过程，只要节点有左子树，那么就会来到两次，其它节点只会来到一次。
	 * 
		先序：有左树的节点，能到达自己两次，第一次到达的时候就打印，
		没有左树的节点，只能到达自己一次的节点，直接打印
		
		中序：有左树的节点，能到达自己两次，第二次到达的时候就打印，
		没有左树的节点，只能到达自己一次的节点，直接打印
		
		后序：有左树的节点，能到达自己两次，第二次回到自己的时候，逆序打印
			它的左树右边界；
			Morris跑完之后，整体逆序打印整棵树的右边界
	 */
	
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	
	// 先序
	public static void morrisPre(Node head) {
		if(head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while(cur != null) {
			mostRight = cur.left;
			if(mostRight != null) {
				while(mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if(mostRight.right == null) { // 有左树的节点，第一次来到的时候打印
					System.out.print(cur.value + " ");
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else { // 当前节点没有左树，只会到达一次，直接打印
				System.out.print(cur.value + " ");
			}
			cur = cur.right;
		}
		System.out.println();
	}
	
	//中序
	public static void morrisIn(Node head) {
		if(head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
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
				} else { // 有左树的节点，第二次来到的时候打印
					System.out.print(cur.value + " ");
					mostRight.right = null;
				}
			} else { // 当前节点没有左树，只会到达一次，直接打印
				System.out.print(cur.value + " ");
			}
			cur = cur.right;
		}
		System.out.println();
	}
	
	// 后序
	public static void morrisPos(Node head) {
		if(head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
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
					printEage(cur.left); // 有左树的节点，第二次来到的时候，逆序打印左树右边界
				}
			}
			cur = cur.right;
		}
		printEage(head); // Morris 跑完之后，打印整棵树的右边界
		System.out.println();
	}
	
	public static void printEage(Node head) {
		if(head == null) {
			return;
		}
		Node tail = reverseList(head); // 逆序
		Node node = tail;
		while(node != null) {
			System.out.print(node.value + " ");
			node = node.right;
		}
		reverseList(tail);
	}
	
	public static Node reverseList(Node head) {
		Node cur = head;
		Node pre = null;
		Node next = null;
		while(cur != null) {
			next = cur.right;
			cur.right = pre;
			pre = cur;
			cur = next;
		}
		return pre;
	}
	
	
	// for test -- print tree
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
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		morrisIn(head);
		morrisPre(head);
		morrisPos(head);
		printTree(head);
	}
}
