package class09;

// 回文串扩展题
public class Code02_ReverseLink {
	
	/**
	 * 有一个链表L1， L2，L3，L4，R1，R2，R3，R4 变成L1 R4 L2 R3 L3 R2 L4 R1
	 * 思路：
	 * 1. 求上中点
	 * 2. 将中点之后的链表逆序
	 * 3. 左右依次遍历链表，进行链接
	 */
	public static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	public static Node link(Node head) {
		if(head == null || head.next == null) {
			return head;
		}
		// 求上中点
		Node slow = head;
		Node fast = head;
		while(fast.next != null && fast.next.next != null) { // 求上中点
			slow = slow.next;
			fast = fast.next.next;
		}
		// 右边部分翻转，以4个节点为例子
		Node pre = slow;
		Node cur = slow.next; // 中点的下一个节点，也就是右边部分链表的第一个节点
		slow.next = null; // 将slow的next指向空
		Node next = null;
		while(cur != null) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		// 遍历左右部分，进行链接
		Node L = head;
		Node LNext = null;
		Node R = pre;
		Node RNext = null;
		while(L != R) {
			LNext = L.next;
			RNext = R.next;
			L.next = R;
			R.next = LNext;
			R = RNext;
			if(LNext != null) {
				L = LNext;	
			}
		}
		return head;
	}
	
	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}
	
	public static void main(String[] args) {

		Node head = null;
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(2);
		head.next.next.next = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(2);
		head.next.next.next.next = new Node(1);
		printLinkedList(head);
		link(head);
		printLinkedList(head);
		System.out.println("=========================");

	}

}
