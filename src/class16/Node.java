package class16;

import java.util.ArrayList;

// 图点结构
public class Node {
	int value;
	int in; // 入度
	int out;// 出度
	ArrayList<Node> nexts; // 该节点出发能够直接到达的节点集合
	ArrayList<Edge> edges; // 该节点直接相连的边的集合
	
	public Node(int value) {
		this.value = value;
		this.in = 0;
		this.out = 0;
		this.nexts = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
	}
}
