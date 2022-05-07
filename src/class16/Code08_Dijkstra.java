package class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Code08_Dijkstra {
	/**
	 * Dijkstra算法：
	 * 求一个节点到其它节点的最短距离的集合。
	 * 
	 * 思路1:
	 * 弄一个HashMap，记录给定节点到各个节点的距离；初始状态只有给定的节点，距离是0，其它的不在里面的节点，距离相当于是正无穷。
	 * 从HashMap选一个没有考察过的且距离最小的节点，从这个节点出发，能直接到达的节点，计算出距离，如果距离比表中记录的
	 * 距离小，则更新，否则不更新。
	 * 再继续从HashMap中找一个没有考察过，且距离最小的节点，考察它能到达的节点的距离，如果比HashMap中的小，则更新
	 * 
	 */
	public static HashMap<Node, Integer> K(Node from) {
		HashMap<Node, Integer> distanceMap = new HashMap<Node, Integer>();
		if(from == null) {
			return distanceMap;
		}
		distanceMap.put(from, 0);
		HashSet<Node> sets = new HashSet<Node>(); // 记录已经被考察过的节点
		Node minNode = getMinDistanceAndNotSelectedNode(distanceMap, sets);
		while(minNode != null) {
			int distance = distanceMap.get(minNode); // 原始点到minNode的最小距离
			for(Edge edge : minNode.edges) { // 考察该节点能到达的所有的节点的距离，会不会比hashMap中的小，如果是则更新
				Node to = edge.to;
				if(!distanceMap.containsKey(to)) { // 以前没有存在
					distanceMap.put(to, distance + edge.weight); 
				} else {
					distanceMap.put(to, Math.min(distance + edge.weight, distanceMap.get(to))); 
				}
			}
			sets.add(minNode); // 加入sets
			minNode = getMinDistanceAndNotSelectedNode(distanceMap, sets); // 继续选择一个最小距离的点
		}
		return distanceMap;
	}
	
	// 从所有的没有被考察过的节点中，选择一个距离最小的
	public static Node getMinDistanceAndNotSelectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> sets) {
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE; // 相当于正无穷
		for(Entry<Node, Integer> entry: distanceMap.entrySet()) { 
			Node node = entry.getKey();
			int distance = entry.getValue();
			if(!sets.contains(node) && distance < minDistance) { // 如果当前节点没有被考察过，并且到该节点的距离是最小的
				minDistance = distance;
				minNode = node;
			}
		}
		return minNode;
	}

}
