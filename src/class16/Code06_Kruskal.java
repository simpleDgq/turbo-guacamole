package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Code06_Kruskal {
	/**
	 * 最小生成数算法：克鲁斯卡尔算法
	 * 最小生成树: 在不影响图连通的情况下, 删掉一些边，使得剩下的边的权重的和最小，则生成的树就是最小生成树。
	 * 
	 * 思路：并查集
	 * 考查所有的边，从权值小的到大的一直考查，如果当前边不会形成环，则要当前边，如果会形成环，则不要当前边。
	 * 
	 * 通过并查集来判断是否会形成环，如果当前边的两个节点在同一个集合中，则会形成环，不要这条边。
	 * 
	 * 弄一个并查集, 初始状态是每条节点一个集合；
	 * 将所有的边从小到大排序，然后遍历，取出一条边，看它的from和to节点，是否在同一个并查集中，
	 * 如果在，则不要这条边; 不在，则要这条边，并且将两个节点合并到同一个集合中.
	 * 继续考察下一条最小的边。
	 */

	public static ArrayList<Edge> K(Graph graph) {
		// 结果集，存储所有的边
		ArrayList<Edge> ans = new ArrayList<Edge>();
		UnionFind unionFind = new UnionFind(graph);
		// 小根堆，存放边，边按权重从小到大排序
		Queue<Edge> heap = new PriorityQueue<Edge>(new EdgeComparator());
		for(Edge edge : graph.edges) {
			heap.add(edge);
		}
		// 从小到大遍历每一条边
		while(!heap.isEmpty()) {
			Edge edge = heap.poll();
			Node from = edge.from;
			Node to = edge.to;
			// 这条边的两个节点不在同一个集合中，要这条边，并且合并这两个节点所在的集合
			if(!unionFind.isSameSet(from, to)) {
				ans.add(edge);
				unionFind.union(from, to);
			}
		}
		return ans;
	}
	
	public static class EdgeComparator implements Comparator<Edge>{
		@Override
		public int compare(Edge o1, Edge o2) {
			// TODO Auto-generated method stub
			return o1.weight - o2.weight;
		}
	}
	
	public static class UnionFind {
		HashMap<Node, Node> parents; //key: 当前节点; value: 每个节点所属的集合的代表节点
		HashMap<Node, Integer> size;
		
		public UnionFind(Graph graph) {
			this.parents = new HashMap<Node, Node>();
			this.size = new HashMap<Node, Integer>();
			for(Node node : graph.nodes.values()) { // 初始化
				parents.put(node, node);
				size.put(node, 1);
			}
		}
		
		public boolean isSameSet(Node from, Node to) {
			return findFather(from) == findFather(to);
		}
		
		public Node findFather(Node node) {
			Stack<Node> stack = new Stack<Node>();
			if(node != parents.get(node)) {
				stack.add(node);
				node = parents.get(node);
			}
			// 将经过的所有节点，全部直接挂在node上
			Node cur = null;
			while(!stack.isEmpty()) {
				cur = stack.pop();
				parents.put(cur, node);
			}
			return node;
		}
		
		public void union(Node from, Node to) {
			if(from == null || to == null) {
				return;
			}
			Node fatherFrom = findFather(from);
			Node fatherTo = findFather(to);
			if(fatherFrom != fatherTo) {
				int sizeFrom = size.get(fatherFrom);
				int sizeTo = size.get(fatherTo);
				if(sizeFrom < sizeTo) {
					parents.put(fatherFrom, fatherTo);
					size.put(fatherTo, sizeFrom + sizeTo);
					size.remove(fatherFrom); // 删掉已经合并的
				} else {
					parents.put(fatherTo, fatherFrom);
					size.put(fatherFrom, sizeFrom + sizeTo);
					size.remove(fatherTo);
				}
			}
		}
	}
}
