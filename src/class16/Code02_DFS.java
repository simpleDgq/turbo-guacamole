package class16;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {
	/**
	 * 给定一个节点，进行图的宽度优先遍历。
	 * 
	 * 思路:
	 * 用栈实现，同时多一个set，记录已经遍历过的节点。
	 * 每次进栈的时候打印节点，同时记录到set中，然后遍历当前节点的nexts节点，如果遇到的第一个next没有
	 * 打印过，则加入next到set，同时将当前节点和next，都加入栈中；不在遍历其它的元素。
	 */
	public void DFS(Node node) {
		if(node == null) {
			return;
		}
		Stack<Node> stack = new Stack<Node>();
		HashSet<Node> set = new HashSet<Node>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		Node cur = null;
		while(!stack.isEmpty()) {
			cur = stack.pop();
			for(Node next : cur.nexts) {
				if(!set.contains(next)) {
					stack.add(cur);
					stack.add(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}
}
