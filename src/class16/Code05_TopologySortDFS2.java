package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import class16.Code04_TopologySortDFS1.DirectedGraphNode;
import class16.Code04_TopologySortDFS1.Record;
import class16.Code04_TopologySortDFS1.recordComparator;

public class Code05_TopologySortDFS2 {
	/**
	 * https://www.lintcode.com/problem/topological-sorting
	 * 
	 * 在给定的图结构上进行拓扑排序。
	 * 
	 * 题目中给出的图的结构，只有当前节点label和它的直接邻居。
	 * 如果转换成上面经典的结构，会增加常数时间，可以直接在该结构上进行拓扑排序。
	 * 
	 * 思路1: 
	 * 
	 * 从X出发走过的最大深度，如果是100，
	 * 从X出发走过的最大深度，如果是80，
	 * 100 > 80, 那么拓扑排序结果中X一定排在Y的前面。
	 * 
	 * 求所有节点能够走到的最大深度，然后按深度从大到小对节点进行排序。
	 * 
	 * 同样也是递归，并且
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
		
		// 记录每个节点能到大的最大深度
		public static class Record {
			long deepth; // 点次
			DirectedGraphNode node;
			public Record(DirectedGraphNode node, long deepth) {
				this.deepth = deepth;
				this.node = node;
			}
		}
		
		public static class recordComparator implements Comparator<Record> {
			@Override
			public int compare(Record o1, Record o2) {
				// TODO Auto-generated method stub
//				return (int) (o1.nodes - o2.nodes); // 可能越界
				return o1.deepth == o2.deepth ? 0 : (o1.deepth > o2.deepth ? -1 : 1); // 从大到小排序
			}
		}
		
		// 求每个点能到达的最大深度
		public static long fDeepth(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> records) {
			if(node == null) {
				return 0;
			}
			if(records.containsKey(node)) {
				return records.get(node).deepth;
			}
			long max = 0;
			// 计算所有邻居节点的深度，找最大的, 然后+ 1
			for(DirectedGraphNode cur : node.neighbors) {
				max = Math.max(max, fDeepth(cur, records));
			}
			records.put(node, new Record(node, max + 1));
			return max + 1;
		}
		
		public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
			 ArrayList<DirectedGraphNode> ans = new  ArrayList<DirectedGraphNode>();
			 HashMap<DirectedGraphNode, Record> records = new HashMap<DirectedGraphNode, Record>();
			 for(DirectedGraphNode cur : graph) {
				 fDeepth(cur, records);
			 }
			 // 根据点次对record中的节点进行排序
			 ArrayList<Record> recordArr = new ArrayList<>();
			 for(Record record : records.values()) {
				 recordArr.add(record);
			 }
			 recordArr.sort(new recordComparator());
			 
			 for(Record record : recordArr) {
				 ans.add(record.node);
			 }
			 return ans;
		}
}
