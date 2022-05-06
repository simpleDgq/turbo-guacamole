package class16;

import java.util.HashMap;
import java.util.HashSet;

// 图结构-> 点和边的集合
public class Graph {
	HashMap<Integer, Node> nodes; // 注意这里是<Integer, Node>, 用户给的就是一个整数
	HashSet<Edge> edges; // HashSet
	public Graph() {
		this.nodes = new HashMap<Integer, Node>();
		this.edges = new HashSet<Edge>();
	}
}
