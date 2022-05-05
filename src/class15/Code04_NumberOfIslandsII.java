package class15;

import java.util.ArrayList;

public class Code04_NumberOfIslandsII {
	
	/**
	 * 给定一个矩阵的行和列数，初始状态全部是0，给定一个数组，数组的每一个元素是[i,j],
	 * 每次空降一个数组中的元素，将矩阵中的i行j列变成1，然后求岛屿的数量，最终返回每一次
	 * 空降，所有的岛屿数量（没空降一个元素，计算一次岛屿数量）。
	 * 
	 * [i,j]位置的数，表示的事将矩阵的i行j列变成1，每次变成1之后，都需要计算现在有多少岛屿。
	 * 
	 * 思路:
	 * 空降一个1, 动态的初始化并查集，然后看它上下左右是不是1，如果是，则加入到并查集中。
	 */
	public ArrayList<Integer> numberOfIslandsII(int M, int N, int positions[][]) {
		UnionFind unionFind = new UnionFind(M, N);
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for(int[] position : positions) {
			ans.add(unionFind.connect(position[0], position[1]));
		}
		return ans;
	}
	
	public class UnionFind {
		int parents[];
		int size[];
		int helper[];
		int sets;
		int col;
		int row;
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
			if(r1 < 0 || r1 >= row || r2 < 0 || r2 >= row || c1 < 0 || c1 >= col || c2 < 0 || c2 >= col) {
				return;
			}
			int i = index(r1, c1);
			int j = index(r2, c2);
			if(i == 0 || j == 0) { // 说明上下左右的某个位置还没有被设置成1过，不用合并，直接跳过（只有是1的位置才合并）
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
		
		public int connect(int r, int c) {
			int i = index(r, c);
			if(size[i] == 0) { // 如果当前位置没有空降过，则设置parent和size
							   // 如果已经空降过，就不需要再考虑了
				size[i] = 1;  // 动态设置，不像前面的题目一上来就全部初始化
				parents[i] = i;
				sets++;
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
