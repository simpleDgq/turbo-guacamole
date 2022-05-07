package class16;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code03_SortedTopology {
	/**
	 * 图的拓扑排序
	 * 
	 * 思路:
	 * 在所有的节点中，找到所有入度为0的节点，放入队列中，
	 * 每次从队列中弹出一个加入到结果集中；同时将该节点的所有nexts节点的入度都-1。
	 * 再减1的过程中，如果又发现了入度为0的节点，则加入到队列中。
	 * 
	 */
	public List<Node> topologySort(Graph graph) {
		if(graph == null) {
			return null;
		}
		ArrayList<Node> ans = new ArrayList<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		
		for(Node cur: graph.nodes.values()) {
			if(cur.in == 0) {
				queue.add(cur);
			}
		}
		Node node = null;
		while(!queue.isEmpty()) {
			node = queue.poll();
			ans.add(node);
			for(Node cur : node.nexts) {
				cur.in -= 1;
				if(cur.in == 0) {
					queue.add(cur);
				}
			}
		}
		return ans;
	}
}
