package class09;

public class Code04_CopyListWithRandom {

	// https://leetcode-cn.com/problems/copy-list-with-random-pointer/submissions/
	/**
	 * 复制包含random指针的链表。
	 * 一种特殊的单链表节点类描述如下 
		class Node {
		int value; 
		Node next; 
		Node rand; 
		Node(int val) { value = val; } 
		} 
		rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
		给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制
		返回复制的新链表的头节点，要求时间复杂度O(N)，额外空间复杂度O(1)
		
	*
	* 思路:
	* 1. 遍历链表，将每个节点copy一份放在当前节点的后面
	* 2. 再次遍历节点，得到当前节点random指针的指向，修改copy出来的节点对应random的指向。
	* （如果当前节点random指向3这个节点，那么copy出来的节点的random一定指向3的下一个节点3‘）
	* 3. 遍历链表，将整个链表拆分出来
	 */
	
	public static class Node {
		int value;
		Node next;
		Node random;
		public Node(int value) {
			this.value = value;
			this.next = null;
			this.random = null;
		}
	}
	
	public Node copyListWithRandom(Node head) {
		if(head == null) { // 没有节点的时候不需要copy
			return head;
		}
		Node cur = head;
		Node next = null;
		// copy节点，放在对应节点后面
		while(cur != null) {
			next = cur.next;
			cur.next = new Node(cur.value);;
			cur.next.next = next;
			cur = next;
		}
		// 遍历新生成的链表。改变复制出来的节点的random指向
		cur = head;
		Node newNode = null;
		while(cur != null) {
			newNode = cur.next;
			newNode.random = cur.random == null ? null : cur.random.next;
			cur = cur.next.next;
		}
		// 拆分整个链表
		Node res = head.next;
		cur = head;
		while(cur.next != null) {
			next = cur.next;
			cur.next = next.next;
			cur = next;
		}
		return res;
	}
}
