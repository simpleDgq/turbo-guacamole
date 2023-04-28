package class15;

import java.util.ArrayList;

//https://leetcode.cn/problems/number-of-islands-ii/
public class Code05_NumberOfIslandsII {
	
	/**
	 * 给定一个矩阵的行和列数，初始状态全部是0，给定一个数组，数组的每一个元素是[i,j],
	 * 每次空降一个数组中的元素，将矩阵中的i行j列变成1，然后求岛屿的数量，最终返回每一次
	 * 空降，所有的岛屿数量（没空降一个元素，计算一次岛屿数量）。
	 * 
	 * [i,j]位置的数，表示的事将矩阵的i行j列变成1，每次变成1之后，都需要计算现在有多少岛屿。
	 * 
	 * 思路:
	 * 空降一个1, 动态的初始化并查集，然后看它上下左右是不是1，如果是，则合并到一个集合中。
	 *
	 * 根据size[i]是0还是1，表示该位置是否被空降过，如果是1，说明已经被空降过，
	 * 如果同一个位置，再次被空降，直接跳过。
	 * 
	 * 时间复杂度:
	 * 初始化的时候，时间复杂度是O(M*N)
	 * 每次空降一个元素，构造并查集的时候，并查集的操作时间复杂度是O(1)
	 * 空降K个元素，所以时间复杂度是O(K)
	 * 所以： O(M*N) + O(K)
	 */
	public ArrayList<Integer> numberOfIslandsII(int M, int N, int positions[][]) {
		UnionFind unionFind = new UnionFind(M, N);
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for(int[] position : positions) {
			// 每一个位置都空降，然后和上下左右合并
			ans.add(unionFind.connect(position[0], position[1]));
		}
		return ans;
	}
	
	public class UnionFind {
		int parents[]; //  parents[i] = k i节点的父节点是k
		int size[]; //  size[j] = m 代表节点j所代表的集合的大小是m
		int helper[];
		int sets; // 集合数量
		// 行和列，用于将二维坐标降成一维
		int row;
		int col;
		
		public UnionFind(int M, int N) {
			parents = new int[M * N];
			size = new int[M * N];
			helper = new int[M * N];
			col = N;
			row = M;
		}
		
		public int findFather(int i) {
			int hi = 0;
			while(i != parents[i]) {
				helper[hi++] = i;
				i = parents[i];
			}
			for(hi--; hi >= 0; hi--) {
				parents[helper[hi]] = i;
			}
			return i;
		}
		
		public void union(int r1, int c1, int r2, int c2) {
			// 防止越界
			if(r1 < 0 || r1 >= row || r2 < 0 || r2 >= row || c1 < 0 || c1 >= col || c2 < 0 || c2 >= col) {
				return;
			}
			// 根据空降的行号和列号，计算出一维数组中的位置
			int i = index(r1, c1);
			int j = index(r2, c2);
			// 说明上下左右的某个位置还没有被设置成1过，不用合并，直接跳过（只有是1的位置才合并）
			if(i == 0 || j == 0) {
				return;
			}
			
			int fatheri = findFather(i);
			int fatherj = findFather(j);
			if(fatheri != fatherj) {
				if(size[fatheri] < size[fatherj]) {
					parents[fatheri] = fatherj;
					size[fatherj] += fatheri;
				} else {
					parents[fatherj] = fatheri;
					size[fatheri] += fatherj;
				}
				sets--;
			}
		}
		// 来了一个位置的值，如果以前没有空降过，则则设置parent和size，然后和上下左右合并
		public int connect(int r, int c) {
			int i = index(r, c);
			if(size[i] == 0) { // 如果当前位置没有空降过，则设置parent和size
							   // 如果已经空降过，就不需要再考虑了
				size[i] = 1;  // 动态设置，不像前面的题目一上来就全部初始化
				parents[i] = i;
				sets++; // 空降的数，先单独形成一个集合
				// 和上下左右合并去吧
				union(r, c, r-1, c);
				union(r, c, r+1, c);
				union(r, c, r, c - 1);
				union(r, c, r, c + 1);
			}
			return sets;
		}
		
		public int index(int r, int c) {
			return r * col + c;
		} 
	}

}
