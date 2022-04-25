package class09;

public class Code03_SmallerEqualBigger {

	/**
	 * 将单向链表按某值划分成左边小，中间相等、右边大的形式
	 * 
	 * 思路：
	 * 1. 设置6个指针，分别为小头，小尾，等头，等尾，大头，大尾
	 * 2. 遍历链表，如果当前元素小于划分值；1）如果小头为空，则将小头小尾都指向改节点；否则将小尾的next指向该节点，同时小尾指向该元素
	 * 同理，如果当前元素等于或大于该元素的时候也一样处理
	 * 3. 将三个区域链接起来，小尾连等头，等尾连大头。需要注意的事，这三个区域都有可能为空。
	 * 
	 * 最后连接思路：
		如果有小于区，则小于区的尾指向等于区的头（如果没有等于区，则会指向null，并不影响）
		然后判断等于区的尾是不是null，如果是null，说明没有等于区，将eT变成小于区的尾sT，
		否则保持不变（决定谁去连接大于区的头，如果有等于区，那么应该等于区去连，反之，应该小于区去连接）
		然后用得到的eT去连接大于区
		最终返回头结点（返回的时候，要返回不为空的头结点，小于区不为空，返回小于区的，否则判断等于区是
		不是为空，是则返回大于区的，否则就返回等于区的）
	 */
	
	public static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
			this.next = null;
		}
	}
	
	public static Node smallerEqualBigger(Node head, int value) {
		if(head == null || head.next == null) {
			return head;
		}
		Node LH = null; // Less head
		Node LT = null; // Less Tail
		Node EH = null; // equal head
		Node ET = null; // equal Tail
		Node BH = null; // bigger head
		Node BT = null; // bigger Tail
		Node cur = head;
		while(cur != null) {
			Node next = cur.next;
			cur.next = null; // 注意这里处理当前节点的时候，得断开该节点的next
			if(cur.value < value) {
				if(LH == null) {
					LH = LT = cur;
				} else {
					LT.next = cur;
					LT = cur;
				}
			} else if(cur.value == value) {
				 if(EH == null) {
					 EH = ET = cur; 
				 } else {
					ET.next = cur;
					ET = cur; 
				 }
			} else {
				 if(BH == null) {
					BH = BT = cur; 
				 } else {
					BT.next = cur;
					BT = cur; 
				 }
			}
			cur = next;
		}
		// 链接各个区域
		if(LH != null) { // 如果有小于区
			LT.next = EH; // 不管有没有等于区，都不会报错
			ET = (ET == null) ? LT : ET; // 保证ET一定是正确的
		}
		if(ET != null) { // 如果小于去和等于区不是都没有，则用正确的ET去连BH
			ET.next = BH;
		}
		return LH != null ? LH : (EH != null) ? EH : BH;  // 返回的时候，如果小于区存在，则返回小于区；
		// 如果小于区不存在，如果等于区存在，应该返回等于区头；如果等于区也不存在，则返回大于区头
	}
}
