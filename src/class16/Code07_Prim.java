package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code07_Prim {
	/**
	 * P算法
	 * 任意一个节点开始，如果没有被考虑过，则考查它能解锁的边，都放入最小堆中，然后从堆中弹出一个权重最小的边并且能
	 * 解锁新的节点的边，加入结果集；选择的边会解锁新的节点，新的节点进入sets，新的节点上连接的所有的边，重新进入堆，
	 * 在所有的边中继续弹出一个权重最小的能解锁新节点的边。
	 *
	 * 边解锁点，点解锁新的边，然后考虑所有的边，选权重最小的
	 * 
	 */
	public static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			// TODO Auto-generated method stub
			return o1.weight - o2.weight; // 按权重从小到大进行排序
		}
	}
	public static ArrayList<Edge> prim(Graph graph) {
		 ArrayList<Edge> ans = new  ArrayList<Edge>();
		 Queue<Edge> heap = new PriorityQueue<Edge>(new EdgeComparator()); // 权重从小到大排序所有的边
		 HashSet<Node> sets = new  HashSet<Node>(); // 已经解锁的节点
		 
		 for(Node node : graph.nodes.values()) {
			 // 任意一个节点，如果没有被考虑过，则它出发所有的边都进入堆
			 if(!sets.contains(node)) {
				 sets.add(node);
				 for(Edge edge : node.edges) {
					 heap.add(edge);
				 }
				 while(!heap.isEmpty()) {
					 Edge edge = heap.poll();
					 if(!sets.contains(edge.to)) { // 权值最小的边，而且能够解锁新的节点
						 ans.add(edge); // 加入这条边到结果集合中
						 sets.add(edge.to); // 新的被考虑过的节点，加入sets
						 for(Edge e : edge.to.edges) { // 新的节点能解锁的边全部入堆
							 heap.add(e);
						 }
					 }
				 }
			 }
			 break; // 不break的话，可以防森林
		 }
		 return ans;
	}
}
