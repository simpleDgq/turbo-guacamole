package class15;

import java.util.List;

// 使用数组实现并查集
// 面试以及笔试用的多(掌握)
public class Code02_UnionFind {

	public class UnionFind {
		public int[] father; // 记录每个节点的父亲节点是谁
		public int[] size; // 记录每个代表节点代表的集合的大小
		public int[] help; // help 数组代替栈
		
		// 根据给定的list初始化并查集
		public UnionFind(int limit, List<Integer> values ) {
			father = new int[limit];
			size = new int[limit];
			help = new int[limit];
			
			for(int value: values) {
				father[value] = value;
				size[value] = 1;
			}
		}

		// 从i开始寻找集合代表点
		public int find(int i) {
			int hi = 0;
			// 找i节点，沿途的节点放在help数组中
			while (i != father[i]) {
				help[hi++] = i;
				i = father[i];
			}
			// 从help数组的后面开始，依次将经过的节点直接挂在代表节点上
			for (hi--; hi >= 0; hi--) {
				father[help[hi]] = i;
			}
			return i;
		}

		// 查询x和y是不是一个集合
		public boolean isSameSet(int x, int y) {
			return find(x) == find(y);
		}

		// x所在的集合，和y所在的集合，合并成一个集合
		public void union(int x, int y) {
			int fx = find(x);
			int fy = find(y);
			if (fx != fy) {
				// 小挂大
				if (size[fx] >= size[fy]) {
					size[fx] += size[fy];
					father[fy] = fx;
				} else {
					size[fy] += size[fx];
					father[fx] = fy;
				}
			}
		}
	}
	
}
