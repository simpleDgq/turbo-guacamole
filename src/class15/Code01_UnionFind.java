package class15;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code01_UnionFind {
	
	/***
	 * 
	 * // 这个文件的并查集是用map实现的
		// 但是笔试或者平时用的并查集一律用数组实现
		// 所以Code02_UnionFind更具实战意义
		// 一定要看！
	 * HashMap实现并查集
	 * 
	 * 并查集：
	 * 提供了两种操作： 1. isSameSet(a,b) 判断两个元素是否是属于同一个集合 2. union(a,b) 将
	 * 给定的两个元素所在的集合进行合并。
	 * 
	 * 刚开始的时候，所有的元素，都是自己成为一个集合。有一个指针自己指向自己。
	 * 1)判断是否是同一个集合：给定的元素一直往上，往上到不能再往上，到达的节点称为集合的代表节点，如果代表节点相同，则这两个元素是属于同一个集合。
	 * 2)合并两个元素所在的集合：一直往上找到代表节点，小挂大，将小的集合挂在大的集合的代表节点下。  ==> 减小链的长度
	 * 在寻找代表节点的过程中，将所有经过的节点都挂在代表节点的下面（栈保存经过的节点，然后弹出，挂在代表节点下面) ==> 减小链的长度
	 * 
	 */
	
	// 课上讲的时候
	// 包了一层
	// 其实不用包一层哦
	public class UnionFind<V> {
		private HashMap<V, V> parents; // 存储每个节点的父亲节点是谁
		private HashMap<V, Integer> sizeMap; // 存储代表节点代表的集合的大小
		
		// 初始化
		public UnionFind(List<V> values) { // 用户给定List
			parents = new HashMap<V, V>();
			sizeMap = new HashMap<V, Integer>();
			for(V value : values) {
				parents.put(value, value); // 初始的时候每一个节点的代表节点是自己
				sizeMap.put(value, 1);
			}
		}
		
		// 找给定节点所在集合的代表节点, 并且将经过的所有节点，直接连接在代表节点下
		public V findFather(V cur) {
			Stack<V> stack = new Stack<V>();
			// cur的父亲节点不是自己。也就是没有到达集合的代表节点。并查集中集合的代表节点有个指针是指向自己的。
			while(cur != parents.get(cur)) {
				stack.add(cur);
				cur = parents.get(cur);
			}
			//cur == parents.get(cur)了，表示到达了代表节点
			// 将栈中经过的所有节点都重新直接连接在代表节点下，以前是间接链接
			while(!stack.isEmpty()) {
				V node = stack.pop();
				parents.put(node, cur);
			}
			// 返回代表节点
			return cur;
		}
		
		// 两个元素是否在同一个集合
		public boolean isSameSet(V a, V b) {
			return findFather(a) == findFather(b);
		}
		
		// 合并两个元素所在的集合
		public void union(V a, V b) {
			V fatherA = findFather(a);
			V fatherB = findFather(b);
			if(fatherA != fatherB) {
				int sizeA = sizeMap.get(fatherA);
				int sizeB = sizeMap.get(fatherB);
				V big = sizeA > sizeB ? fatherA : fatherB;
				V small = big == fatherA ? fatherB : fatherA;
				parents.put(small, big); // 直接将小集合的代表节点挂在大集合上
				sizeMap.put(big, sizeA + sizeB);
				sizeMap.remove(small); // 小的并入了大的，所以要删除小的代表节点，所代表的集合的size
			}
		}
		
		// 返回有多少集合数
		public int sets() {
			return sizeMap.size();
		}
	}
}
