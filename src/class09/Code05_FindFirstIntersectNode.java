package class09;

public class Code05_FindFirstIntersectNode {
	
	/**
	 * 给定两个可能有环也可能无环的单链表，头节点head1和head2
	 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交返回null 
	 * 要求如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
	 * 
	 * 思路：
	 * 1. 上来先判断两个链表是不是有环的，没有环返回null，有环返回入环第一个节点
	 * 	  1）快慢指针。快指针一次走两步，慢指针一次走一步，如果快指针能够到达null，说明改链表无环，直接返回
	 * 	  2）有环的情况下，当快慢指针第一次相遇的时候，将快指针回到头结点，而且变成一次也只走一步，当快慢指针再一次相遇的时候，
	 *       所指向的节点一定是第一个入环节点，直接返回。
	 * 2. 两个链表有环和无环的情况，可以分为3种：
	 * 	  1）两个链表都无环 ==> 如果相交，那么他们的最后一个节点一定是一样的。
	 * 		遍历两个链表，同时计算链表的长度，比较最后一个节点是不是一样，如果不是一样，说明不想交，返回null；
	 * 		如果是一样，说明相交，则长链表先走长度的差值步，然后短链表一起走，当长短链表相遇的时候，就是相交节点。
	 *    2）一个有环，一个无环 ==> 一定不能相交，直接返回null
	 *    3) 两个链表都有环，又可以分为3中情况
	 *        1）都有环，但是不相交
	 *        2）入环节点是一样 ==> 两个链表的入环节点 loop1 == loop2, 求相交节点的方法和两个链表都无环的方法一样，走到loop1就行
	 *        3）入环节点不一样
	 * ==> 1)和3) ==> 从loop1开始走，在回到loop1之前，如果能遇到loop2说明是情况3)，是相交的，返回loop1或者loop2都行；如果遇不到loop2，说明不想交
	 *        返回null
	 */
	public static Node getFirstIntersectNode(Node head1, Node head2) {
		if(head1 == null || head2 == null) {
			return null;
		}
		//判断是否都有环
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		// 如果都没有环
		if(loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		} else if(loop1 != null && loop2 != null) { // 两个都有环
			return bothLoop(head1, head2, loop1, loop2);
		} else { // 一个有环，一个没环，必不相交
			return null;
		}
	}
	
	public static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	/**
	 * 判断一个链表是否有环，求第一个入环节点
	 * @param head
	 * @return
	 */
	public static Node getLoopNode(Node head) {
		if(head == null || head.next == null || head.next.next == null) {
			return null;
		}
		Node fast = head.next.next;
		Node slow = head.next;
		while(fast != slow) { // 找到第一次相遇的节点
			if(fast.next == null || fast.next.next == null) {
				return null;
			}
			fast = fast.next.next;
			slow = slow.next;
		}
		fast = head; // fast回到头结点，以后每次只走一步
		while(fast != slow) {
			fast = fast.next;
			slow = slow.next;
		}
		return fast; // 再次相遇的时候的节点就是入环节点
	}
	
	/**
	 * 两个链表都没环，求相交节点
	 */
	public static Node noLoop(Node head1, Node head2) {
		if(head1 == null || head2 == null) {
			return null;
		}
		Node end1 = head1;
		Node end2 = head2;
		int n = 0;
		while(end1.next != null) { // 得到第一个链表的最后一个节点
			n++;
			end1 = end1.next;
		}
		while(end2.next != null) { // 得到第二个链表的最后一个节点
			n--;
			end2 = end2.next;
		}
		if(end1 != end2) {// 如果最后一个节点不一样，一定不相交
			return null;
		}
		// 将end1指向长链表，end2指向短链表
		end1 = n < 0 ? head2 : head1;
		end2 = (end1 == head2) ? head1 : head2;
		// 相交的情况下，长链表先走差值步
		n = Math.abs(n);
		while(n != 0) {
			n--;
			end1 = end1.next;
		}
		// 长短链表一起走，相遇的第一个节点就是相交节点
		while(end1 != end2) {
			end1 = end1.next;
			end2 = end2.next;
		}
		return end1;
	}
	
	/**
	 * 两个链表都有环，求相交节点
	 * 分为两种情况，入环节点是同一个节点，入环节点不是同一个节点。
	 */
	public static Node bothLoop(Node head1, Node head2, Node loop1, Node loop2) { 
		if(head1 == null || head2 == null || loop1 == null || loop2 == null) {
			return null;
		}
		Node end1 = head1;
		Node end2 = head2;
		// 入环节点是同一个节点，则判断方法和两个链表都无环的情况一样，砍掉入环节点后面的环，只看前面的就行(脑海中有个图)
		if(loop1 == loop2) {
			int n = 0;
			while(end1 != loop1) { // 得到第一个链表的最后一个节点
				n++;
				end1 = end1.next;
			}
			while(end2 != loop2) { // 得到第二个链表的最后一个节点
				n--;
				end2 = end2.next;
			}
//			if(end1 != end2) {// 如果最后一个节点不一样，一定不相交
//				return null;
//			}
			// 将end1指向长链表，end2指向短链表
			end1 = n < 0 ? head2 : head1;
			end2 = (end1 == head2) ? head1 : head2;
			// 相交的情况下，长链表先走差值步
			n = Math.abs(n);
			while(n != 0) {
				n--;
				end1 = end1.next;
			}
			// 长短链表一起走，相遇的第一个节点就是相交节点
			while(end1 != end2) {
				end1 = end1.next;
				end2 = end2.next;
			}
			return end1;
		} else { // 入环节点不一样
			end1 = loop1.next; // 得先走一步
			Node res = null;
			while(end1 != loop1) { // loop1 一直走，如果再次到达loop1之前，碰到loop2，说明相交，碰不到说明不相交
				if(end1 == loop2) {
					res = loop1;
					break;
				}
				end1 = end1.next;
			}
			return res;
		}
	}
}
