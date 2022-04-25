package class09;

public class Code01_LinkedListMid {
	
	/**
	题目：
	1. 如果链表是奇数个节点，则返回唯一的中点；如果是偶数个节点，则返回上中点
	2. 如果链表是奇数个节点，则返回唯一的中点；如果是偶数个节点，则返回下中点
	3. 如果是奇数个，返回中点的前一个节点；如果是偶数个节点，则返回上中点的前一个节点
	4. 如果是奇数个，返回中点的前一个节点；如果是偶数个节点，则返回上中点
	*/
	public static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	/**
	 * 	1. 如果链表是奇数个节点，则返回唯一的中点；如果是偶数个节点，则返回上中点
	 */
	public static Node midOrUpMid(Node head) {
		if(head == null || head.next == null) {
			return head;
		}
		Node slow = head;
		Node fast = head;
		while(fast != null && fast.next != null) {
			fast = fast.next.next;
			if(fast != null) {
				slow = slow.next;
			}
		}
		return slow;
	}
	
	/**
	 * 	2. 如果是奇数个，返回中点的前一个节点；如果是偶数个节点，则返回上中点的前一个节点
	 */
	public static Node midOrDownMid(Node head) {
		if(head == null || head.next == null) {
			return head;
		}
		Node slow = head;
		Node fast = head;
		while(fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}
	
	/**
	 * 	3. 如果是奇数个，不返回中点，返回中点的前一个节点；如果是偶数个节点，则返回上中点的前一个点
	 */
	public static Node upMidOrUpMidPre(Node head) {
		if(head == null || head.next == null || head.next.next == null) {
			return null;
		}
		Node slow = head;
		Node fast = head;
		while(fast != null && fast.next != null) {
			fast = fast.next.next;
			if(fast != null && fast.next != null && fast.next.next != null) {
				slow = slow.next;
			}
		}
		return slow;
	}
	
	/**
	 * 4. 如果是奇数个，返回中点的前一个节点；如果是偶数个节点，则返回上中点
	 */
	public static Node midOrDownMidPreNode(Node head) {
		if (head == null || head.next == null) {
			return null;
		}
		if (head.next.next == null) {
			return head;
		}
		Node slow = head;
		Node fast = head;
		while (fast != null && fast.next != null && fast.next.next != null) {	
			fast = fast.next.next;
			if(fast.next != null) {
				slow = slow.next;
			}
		}
		return slow;
	}
}
