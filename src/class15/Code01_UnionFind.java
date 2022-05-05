package class15;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code01_UnionFind {
	
	/***
	 * HashMap实现并查集
	 * 
	 * 并查集：
	 * 提供了两种操作： 1. isSameSet(a,b) 判断两个元素是否是属于同一个集合 2. union(a,b) 将
	 * 给定的两个元素所在的集合进行合并。
	 * 
	 * 刚开始的时候，所有的元素，都是自己成为一个集合。有一个指针自己指向自己。
	 * 判断是否是同一个集合：给定的元素一直往上，往上到不能再往上，到达的节点称为集合的代表节点，如果代表节点相同，则这两个元素是属于同一个集合。
	 * 合并两个元素所在的集合：一直往上找到代表节点，小挂大，将小的集合挂在大的集合的代表节点下。  ==> 减小链的长度
	 * 再寻找代表节点的过程中，将所有经过的节点都挂在代表节点的下面（栈保存经过的节点，然后弹出，挂在代表节点下面) ==> 减小链的长度
	 * 
	 */
	public class Node<V> { // 用户给定的是int，用Node包装下
		V value;
		public Node(V value) {
			this.value = value;
		}
	}
	
	public class UnionFind<V> {
		private HashMap<V, Node<V>> nodes; // 存储所有的节点，给定value，对应到一个Node，方便找father以及进行union
		private HashMap<Node<V>, Node<V>> parents; // 存储每个节点的代表节点
		private HashMap<Node<V>, Integer> sizeMap; // 存储代表节点代表的集合的大小
		
		public UnionFind(List<V> values) { // 用户给定List
			nodes = new HashMap<V, Node<V>>();
			parents = new HashMap<Node<V>, Node<V>>();
			sizeMap = new HashMap<Node<V>, Integer>();
			for(V value : values) {
				Node<V> cur = new Node<V>(value);
				nodes.put(value, cur);
				parents.put(cur, cur);
				sizeMap.put(cur, 1);
			}
		}
		
		// 找给定节点所在集合的代表节点, 并且将经过的所有节点，直接连接在代表节点下
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> stack = new Stack<Node<V>>();
			while(cur != parents.get(cur)) {
				stack.add(cur);
				cur = parents.get(cur);
			}
			//cur == parents.get(cur);
			while(!stack.isEmpty()) { // 将所有节点都重新直接连接在代表节点下，以前是间接链接
				Node<V> node = stack.pop();
				parents.put(node, cur);
			}
			return cur;
		}
		
		// 两个元素是否在同一个集合
		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}
		
		// 合并两个元素所在的集合
		public void union(V a, V b) {
			Node<V> fatherA = findFather(nodes.get(a));
			Node<V> fatherB = findFather(nodes.get(b));
			if(fatherA != fatherB) {
				int sizeA = sizeMap.get(fatherA);
				int sizeB = sizeMap.get(fatherB);
				Node<V> big = sizeA > sizeB ? fatherA : fatherB;
				Node<V> small = big == fatherA ? fatherB : fatherA;
				parents.put(small, big);
				sizeMap.put(big, sizeA + sizeB);
				sizeMap.remove(small); // 小的并入了大的，所以要删除小的代表节点，集合的size
			}
		}
		
		// 返回有多少集合数
		public int sets() {
			return sizeMap.size();
		}
	}
}
