package class16;

public class GraphGenerator {
	
	// 给定一个 matrix, 里面是所有的边, 根据这些数据生成通用图结构。
	// N*3 的矩阵
	// [weight, from节点上面的值，to节点上面的值]
	// 
	// [ 5 , 0 , 7]
	// [ 3 , 0,  1]
	/**
	 * 思路: 遍历matrix中的每一个元素，生成对应的node和edge加入graph中。
	 */
	public Graph createGraph(int matrix[][]) {
		Graph graph = new Graph();
		for(int i = 0; i <= matrix.length - 1; i++) {
				int weight = matrix[i][0];
				int fromValue = matrix[i][1];
				int toValue = matrix[i][2];
				Node fromNode = new Node(fromValue);
				Node toNode = new Node(toValue);
				if(!graph.nodes.containsKey(fromValue)) {
					graph.nodes.put(fromValue, fromNode);
				}
				if(!graph.nodes.containsKey(toValue)) {
					graph.nodes.put(toValue, toNode);
				}
 				Edge edge = new Edge(weight, fromNode, toNode);
 				if(!graph.edges.contains(edge)) {
 					graph.edges.add(edge);
 					fromNode.out += 1;
 					fromNode.nexts.add(toNode);
 					fromNode.edges.add(edge);
 					toNode.in += 1;
 				}
		}
		return graph;
	}
}
