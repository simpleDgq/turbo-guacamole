package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_BFS {
	
	/**
	 * 从给定的节点开始，进行图的宽度优先遍历
	 *  
	 *  思路:
	 *  和二叉树的层次遍历一样，用队列实现，只不过多了一个set，记录已经遍历过的节点，
	 *  每次进队列之前，都先看看set中有没有，如果没有有才入队，有就不入队，否则就遍历不完了。
	 */
	public void BFS(Node node) {
		if(node == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> set = new HashSet<Node>();
		queue.add(node);
		set.add(node);
		Node cur = null;
		while(!queue.isEmpty()) {
			cur = queue.poll();
			System.out.print(cur.value);
			for(Node next : cur.nexts) {
				if(!set.contains(next)) {
					queue.add(next);
					set.add(next);
				}
			}
		}
	}
}
