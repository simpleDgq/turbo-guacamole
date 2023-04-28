package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Code04_TopologySortDFS1 {
	
	/**
	 * https://www.lintcode.com/problem/topological-sorting
	 * 
	 * 在给定的图结构上进行拓扑排序。
	 * 
	 * 题目中给出的图的结构，只有当前节点label和它的直接邻居。
	 * 如果转换成上面经典的结构，会增加常数时间，可以直接在该结构上进行拓扑排序。
	 * 
	 * 思路1: 生成好所有的节点的点次之后，将所有节点，按点次从小到大进行排序，拍好的顺序就是拓扑序。
	 * 从X出发走过的所有的路，所有的点都统计完之后，如果数量是100；
	 * 从Y出发走过的所有的路，所有的点都统计完之后，如果数量是80；
	 * 100 > 80, 那么在拓扑排序的结果中，X一定在Y的前面。
	 * 
	 * 
	 * 如何生成一个点的点次？递归。 弄一个map存储已经计算过点次的节点。下次要用的时候直接取。
	 * 如果当前节点的点次计算过，则直接从map缓存中取出返回，
	 * 如果没有计算过，则递归计算所有的邻居节点的点次，累加返回，然后加上自己，放在mao缓存中。
	 * 
	 */
	
	// 不要提交这个类， 题目给出的图
	public static class DirectedGraphNode {
		public int label;
		public ArrayList<DirectedGraphNode> neighbors;

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}
	
	// 记录每个节点的点次
	public static class Record {
		long nodes; // 对应的点次是多少
		DirectedGraphNode node; // 从哪个节点出发
		public Record(DirectedGraphNode node, long nodes) {
			this.nodes = nodes;
			this.node = node;
		}
	}
	
	public static class recordComparator implements Comparator<Record> {
		@Override
		public int compare(Record o1, Record o2) {
			// TODO Auto-generated method stub
//			return (int) (o1.nodes - o2.nodes); // 这样写，int可能越界
			return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1); // 从大到小排序
		}
	}
	
	// 求每个点能到达的所有的点的点次
	public static long fNodes(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> records) {
		if(node == null) {
			return 0;
		}
		// 计算过。map中取直接返回
		if(records.containsKey(node)) {
			return records.get(node).nodes;
		}
		// 没有计算过，则遍历该节点的所有邻居节点个数(点次)，然后累加
		long nodes = 0;
		for(DirectedGraphNode cur : node.neighbors) {
			// 所有邻居节点的点次累加
			nodes += fNodes(cur, records);
		}
		// node+1加上自己，放入map缓存中
		records.put(node, new Record(node, nodes + 1));
		return nodes + 1;
	}
	
	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		 ArrayList<DirectedGraphNode> ans = new  ArrayList<DirectedGraphNode>();
		 HashMap<DirectedGraphNode, Record> records = new HashMap<DirectedGraphNode, Record>();
		 // 给定的图中，每个节点出发，都计算好点次，放入map中
		 for(DirectedGraphNode cur : graph) {
			 fNodes(cur, records);
		 }
		 // map中取出每个节点的点次记录record，放入一个数组中，方便后面进行排序
		 ArrayList<Record> recordArr = new ArrayList<>();
		 for(Record record : records.values()) {
			 recordArr.add(record);
		 }
		 // 根据点次对recordArr中的节点进行排序。从大到小。
		 recordArr.sort(new recordComparator());
		 // 排好序的结果就是答案
		 for(Record record : recordArr) {
			 ans.add(record.node);
		 }
		 return ans;
	}
}
