package class03;

public class Code02_DeleteGivenValue {

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
	
	// 删除链表中给定的元素  两个指针pre和cur
	/**
	 * 思路: 1. 从头结点开始找到第一个不需要删除的位置
	 * 		2. 定义两个指针pre和cur, 从第一个不需要删除的位置开始,
	 * 		  判断cur指向的位置是否需要删除，如果需要，则
	 * 			pre.nex = cur.next; 
	 * 			cur = cur.next;
	 * 		  如果不需要删除，
	 * 			pre = cur;
	 * 			cur = cur.next;
	 * 
	 * @param head
	 * @param value
	 * @return
	 */
	public static Node deleteGivenValue(Node head, int value) {
		// 找到第一个不需要删除的位置，head指针移动
		while(head != null) {
			if(head.value != value) {
				break;
			}
			head = head.next;
		}
		Node pre = head;
		Node cur = head;
		while(cur != null) {
			if(cur.value == value) {
				pre.next = cur.next;
			} else {
				pre = cur;
			}
			cur = cur.next;
		}
		return head; // 返回头结点
	}
}
