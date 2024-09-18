package class15;

import java.util.List;

// 使用数组实现并查集
// 面试以及笔试用的多(掌握)
public class Code02_UnionFind {
	 /** 
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
	public class UnionFind {
		public int[] father; // 记录每个节点的父亲节点是谁。parents[i] = k 表示的是i节点的父节点是k
		public int[] size; // 记录每个代表节点代表的集合的大小。size[i] = j 代表节点i代表的集合的大小是j
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

		// 找给定节点所在集合的代表节点, 并且将经过的所有节点，直接连接在代表节点下
		public int find(int i) {
			int hi = 0;
			// 当前节点的父亲节点不是自己。也就是没有到达集合的代表节点。并查集中集合的代表节点有个指针是指向自己的。
			// 沿途的节点放在help数组中
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
