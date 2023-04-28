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
	 * 
	 * 准备一个栈，节点进栈的时候打印，出栈的时候不打印。
	 * 遍历一个节点的所有邻居，如果第一个遇到的邻居没有打印过，则加入set，
	 * 并且当前节点和邻居都加入栈中，不在继续遍历邻居。
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
					// 当前节点和第一个next节点入栈
					stack.add(cur);
					stack.add(next);
					set.add(next);
					// 入栈的时候打印节点
					System.out.println(next.value);
					// 不在搞其它的next
					break;
				}
			}
		}
	}
}
