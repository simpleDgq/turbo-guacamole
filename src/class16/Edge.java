package class16;

// 图边结构
public class Edge {
	int weight; // 权重
	Node from; // 出发节点
	Node to; // 到达节点
	
	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}
}
