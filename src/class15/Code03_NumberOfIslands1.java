package class15;

public class Code03_NumberOfIslands1 {
	/**
	 * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量.
	 * 
	 * 递归解法:
	 * 遍历martix中的每一个数，如果是1，岛屿数量加1，而且将它感染成2，同时，感染它的上下左右的点，同样如果是1，也感染成2；
	 * 如果是0的话，直接开始下一个元素。
	 * 
	 * 并查集解法:
	 * 
	 * 用数组实现: 对于一个M * N的matrix数组，并查集中开辟的数组的长度是M*N，matrix中没一个元素对应的下标是i * 列数 + j。
	 * 1. 初始化的时候，matrix中所有的1的代表节点parent都是自己。
	 * 2. 遍历所有的元素，对当前元素的左上，如果是1，如果是1，则进行合并。合并的过程中，集合数量--。
	 * 返回最终的集合数量。
	 * 
	 */
	// 递归
	public int numberOfIslands(char matrix[][]) {
		if(matrix == null || matrix.length == 0 ||
				matrix[0] == null ||  matrix[0].length == 0) {
			return 0;
		}
		int num = 0;
		for(int i = 0; i <= matrix.length - 1; i++) {
			for(int j = 0; j <= matrix[0].length - 1; j++) {
				if(matrix[i][j] == '1') {
					num++; // 相连的1都被递归感染成2了，或者是0，遇到新的1，说明是一个单独的岛屿
					infect(i, j, matrix);
				}
			}
		}
		return num;
	}
	public void infect(int i, int j, char matrix[][]) {
		if(i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || matrix[i][j] != '1') {
			return ;
		}
		matrix[i][j] = '2'; // 将当前节点感染成2
		// 感染上下左右
		infect(i - 1, j, matrix);
		infect(i + 1, j, matrix);
		infect(i, j - 1, matrix);
		infect(i, j + 1, matrix);
	}

	// 并查集
	public int numberOfIslands2(char  matrix[][]) {
		if(matrix == null || matrix.length == 0 ||
				matrix[0] == null ||  matrix[0].length == 0) {
			return 0;
		}
		UnionFind unionFind = new UnionFind(matrix);
		int M = matrix.length;
		int N = matrix[0].length;
		
		// 处理第一行
		for(int i = 1; i <= N - 1; i++) {// 从第一列1开始，因为(0,0)没有左，不需要处理
			if(matrix[0][i - 1] =='1' && matrix[0][i] =='1') {
				unionFind.union(i - 1 , i);
			}
		}
		// 处理第一列
		for(int j = 1; j <= M - 1; j++) {// 从第一行1开始，因为(0,0)没有上，不需要处理
			if(matrix[j][0] =='1' && matrix[j - 1][0] =='1') {
				unionFind.union((j - 1) * N , j * N);
			}
		}
		// 处理其它剩下的部分，如果当前节点的左上节点是1，则进行合并
		for(int i = 1; i <= M - 1; i++) {
			for(int j = 1; j <= N - 1; j++) {
				if(matrix[i][j] == '1') {
					if(matrix[i - 1][j] == '1') { // 左
						unionFind.union((i - 1) * N + j , i * N + j);
					} 
					if(matrix[i][j - 1] == '1') { // 上
						unionFind.union((i) * N + j - 1, i * N + j);
					}
				}
			}
		}
		return unionFind.getSets();
	}
	
	public class UnionFind {
		int parents[];
		int size[];
		int helper[];
		int sets;
		
		public UnionFind(char matrix[][]) {
			int M = matrix.length;
			int N = matrix[0].length;
			parents = new int[M * N];
			size = new int[M * N];
			helper = new int[M * N];
			// 对于martix中所有的1，设置它的parent为它自己
			// 并且设置集合的大小为1
			for(int i = 0; i <= M - 1; i++) {
				for(int j = 0; j <= N - 1; j++) {
					if(matrix[i][j] == '1') {
						int index = i * N + j;
						parents[index] = index;
						size[index] = 1;
						sets++;
					}
				}
			}
		}
		// 将i和j位置的元素进行合并
		public void union(int i, int j) {
			int fatheri = findFather(i);
			int fatherj = findFather(j);
			if(fatheri != fatherj) {
				if(size[fatheri] < size[fatherj]) {
					parents[fatheri] = fatherj;
					size[fatherj] += size[fatheri];
				} else {
					parents[fatherj] = fatheri;
					size[fatheri] += size[fatherj];
				}
				sets--;
			}
		}
		public int findFather(int index) {
			int hi = 0;
			while(index != parents[index]) {
				helper[hi++] = index;
				index = parents[index];
			}
			for(hi--; hi >= 0;) {
				parents[helper[hi]] = index;
				hi--;
			}
			return index;
		}
		
		public int getSets() {
			return sets;
		}
	}
}
