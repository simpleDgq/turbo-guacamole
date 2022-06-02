package class30;

public class Code01_Morris {
	
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	
	/**
	 * 假设来到当前节点cur，开始时cur来到头节点位置
	1）如果cur没有左孩子，cur向右移动(cur = cur.right)
	2）如果cur有左孩子，找到左子树上最右的节点mostRight：
	     a. 如果mostRight的右指针指向空，让其指向cur，
	        然后cur向左移动(cur = cur.left)
	     b. 如果mostRight的右指针指向cur，让其指向null，
	         然后cur向右移动(cur = cur.right)
	3）cur为空时遍历停止
	 */
	
	public static void morris(Node head) {
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
				}
			}
			cur = cur.right;
		}
	}
}
