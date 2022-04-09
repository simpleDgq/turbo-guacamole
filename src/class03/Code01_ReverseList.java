package class03;

public class Code01_ReverseList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	public class DoubleNode {
		int value;
		DoubleNode pre;
		DoubleNode next;
		public DoubleNode(int value) {
			this.value = value;
			this.pre = null;
			this.next = null;
		}
	}
	
	// 翻转单链表
	public static Node reverseList(Node head) {
		if(head == null || head.next == null) {
			return head;
		}
		Node pre = null;
		Node next = null;
		while(head != null) {
			next = head.next;
			head.next = pre;
			pre = head;
			head = next;
		}
		return pre;
	}
	
	// 翻转双向链表
	public static DoubleNode reverseDoubleList(DoubleNode head) {
		if(head == null || head.next == null) {
			return head;
		}
		DoubleNode pre = null;
		DoubleNode next = null;
		while(head != null) {
			next = head.next;
			head.next = pre;
			head.pre = next;
			pre = head;
			head = next;
		}
		return pre;
	}
}
