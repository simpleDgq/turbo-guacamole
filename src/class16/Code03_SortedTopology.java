package class16;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code03_SortedTopology {
	/**
	 * 图的拓扑排序
	 * 
	 * 1. 根据一个顺序能够依次把工作做完，而且不缺依赖的一个顺序，就是拓扑序。
	 * 2. 拓扑排序必须是有向无环图。如果有环的话，就不能确定顺序了。
	 * 3. 拓扑排序结果不唯一，每一个都对。
	 * 
	 * 思路:
	 * 在所有的节点中，找到所有入度为0的节点，放入队列中，
	 * 每次从队列中弹出一个加入到结果集中；同时将该节点的所有nexts节点的入度都减1。
	 * 再减1的过程中，如果又发现了入度为0的节点，则加入到队列中。
	 * 
	 */
	public List<Node> topologySort(Graph graph) {
		if(graph == null) {
			return null;
		}
		// 结果集，存放遍历过的节点
		ArrayList<Node> ans = new ArrayList<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		// 将所有入度为0的节点都放入队列中
		for(Node cur: graph.nodes.values()) {
			if(cur.in == 0) {
				queue.add(cur);
			}
		}
		Node node = null;
		while(!queue.isEmpty()) {
			// 每次从队列中弹出一个加入到结果集中
			node = queue.poll();
			ans.add(node);
			// 将弹出的节点的所有nexts节点的入度都减去1
			for(Node cur : node.nexts) {
				cur.in -= 1;
				// 如果发现有入度为0的节点，加入队列中
				if(cur.in == 0) {
					queue.add(cur);
				}
			}
		}
		return ans;
	}
}
