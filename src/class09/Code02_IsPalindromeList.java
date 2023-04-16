package class09;

public class Code02_IsPalindromeList {

	/**
	 * 给定一个单链表的头结点head，判断该链表是否为回文结构
	 * 
	 * 思路 1: 将所有的链表元素放入栈，出栈和链表重新遍历进行对比（笔试用）
	 * 
	 * 思路 2：面试用
	 * 1. 快慢指针找到上中点
	 * 2. 将中点之后的链表进行翻转
	 * 3. 左右链表, 再搞两个指针L和R分别指向开始和最后, 分别遍历, 判断是否相等
	 * 4. 恢复原链表
	 * 
	 */
	public static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	public static boolean isPalindromeList(Node head) {
		if(head == null || head.next == null) {
			return true;
		}
		Node fast = head;
		Node slow = head;
		
		// 找中点，最终找到的是上中点
		while(fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		
		// 将右边部分的元素进行翻转（注意这部分）
		// 翻转链表两个变量，pre和next
		Node pre = slow;
		Node cur = slow.next; // 右边部分第一个节点
		slow.next = null;
		Node next = null;
		while(cur != null) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		
		// 左右部分遍历进行比较
		Node R = pre;
		Node L = head;
		boolean res = true;
		while(R != L) {
			if(R.value != L.value) {
				res = false;
				break;
			}
			if(R.next != null) {
				R = R.next;
			}
			if(L.next != null) {
				L = L.next;
			}
		}
		
		// 从后往前，恢复链表 (注意这部分) --> 三个指针
		Node n1 = null; // pre
		Node n2 = pre; // cur
		Node n3 = null; // next
		while(n2 != null) {
			n3 = n2.next;
			n2.next = n1;
			n1 = n2;
			n2 = n3;
		}
		return res;
	}
}
