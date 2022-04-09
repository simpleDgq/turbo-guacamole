package class03;

public class Code03_DoubleListToQueueAndStack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	
	public static class DoubleEndsQueue {
		DoubleNode head = null;
		DoubleNode tail = null;
		
		// 从头部添加元素
		public void addFromHead(DoubleNode node) {
			if(head == null) {
				head = tail = node;
				return;
			}
			head.pre = node;
			node.next = head;
			head = node;
		}
		
		// 从尾部添加元素
		public void addFromTail(DoubleNode node) {
			if(head == null) {
				head = tail = node;
				return;
			}
			tail.next = node;
			node.pre = tail;
			tail = node;
		}
		
		// 从头部取出元素
		public DoubleNode popFromHead() {
			if(head == null) {
				return null;
			}
			DoubleNode res = head; // 记录要返回的节点
			if(head == tail) { // 只有一个节点，直接返回
				head = tail = null;
			} else {
				head = head.next;
				head.pre = null;
				res.next = null;
			}
			return res;
		}
		
		// 从尾部取出元素
		public DoubleNode popFromTail() {
			if(tail == null) {
				return null;
			}
			DoubleNode res = head; // 记录要返回的节点
			if(head == tail) { // 只有一个节点，直接返回
				head = tail = null;
			} else {
				tail = tail.pre;
				tail.next = null;
				res.pre = null;
			}
			return res;
		}
		
		public boolean isEmpty() {
			return head == null;
		}
				
	}
	
	// 双向链表实现队列
	public static class MyQueue {
		DoubleEndsQueue queue;
		public MyQueue() {
			queue = new DoubleEndsQueue();
		}
		
		// 添加元素
		public void push(DoubleNode node) {
			queue.addFromTail(node);
		}
		
		// 取元素
		public DoubleNode poll() {
			return queue.popFromHead();
		}
		
		// 判断是否为空
		public boolean isEmpty() {
			return queue.isEmpty();
		}
	}
	
	// 双向链表实现栈
	public static class MyStack {
		DoubleEndsQueue queue;
		public MyStack() {
			queue = new DoubleEndsQueue();
		}
		// 添加元素
		public void push(DoubleNode node) {
			queue.addFromHead(node);
		}
		
		// 取元素
		public DoubleNode poll() {
			return queue.popFromTail();
		}
		
		// 判断是否为空
		public boolean isEmpty() {
			return queue.isEmpty();
		}
	}
}
