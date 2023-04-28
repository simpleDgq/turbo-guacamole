package class15;

//https://leetcode.cn/problems/number-of-islands/
public class Code04_NumberOfIslandsI {
	/**
	 * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量.
	 * 
	 * 递归解法:
	 * 遍历martix中的每一个数，如果是1，岛屿数量加1，而且将它感染成2，同时，感染它的上下左右的点，同样如果是1，也感染成2；
	 * 如果是0的话，直接开始下一个元素。
	 * 
	 * 当前改2，然后递归感染上下左右。改2是因为处理下一个元素的时候，又会处理当前元素，为了递归能够退出。
	 * 
	 * 时间复杂度：O(M*N)， M和N是矩阵长宽。主过程每个元素遍历一遍，感染的过程，
	 * 每个元素只会被上下左右各处理一遍，每个元素总共5遍。 5  * M * N  ==> O(M*N)
	 * 
	 * 并查集解法:
	 * 
	 * 用数组实现: 对于一个M * N的matrix数组，并查集中开辟的数组的长度是M*N，matrix中没一个元素对应的下标是i * 列数 + j。
	 * 1. 初始化的时候，matrix中所有的1的代表节点和parent都是自己。
	 * 2. 遍历所有的元素，对当前元素的左上，如果是1，则进行合并。合并的过程中，集合数量--。
	 * 返回最终的集合数量。
	 * 
	 * 第一行没有上，第一列没有左，所以分开处理，处理完第一行和第一列，在处理剩下的，
	 * 避免很多边界条件判断。
	 * 
	 */
	// 递归解法
	public int numberOfIslands(char matrix[][]) {
		if(matrix == null || matrix.length == 0 ||
				matrix[0] == null ||  matrix[0].length == 0) {
			return 0;
		}
		int num = 0;
		for(int i = 0; i <= matrix.length - 1; i++) {
			for(int j = 0; j <= matrix[0].length - 1; j++) {
				if(matrix[i][j] == '1') {
					// 相连的1都被递归感染成2了，或者是0
					// 如果遇到了新的1，说明是一个单独的岛屿，num++
					num++; 
					infect(i, j, matrix);
				}
			}
		}
		return num;
	}
	// 当前来到i,j位置，将该位置感染成2，同时感染该位置上下左右相邻的1
	public void infect(int i, int j, char matrix[][]) {
		// 越界; 或者i,j位置不是1，说明该位置是0或者是2，直接返回
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

	// 并查集解法
	public int numberOfIslands2(char  matrix[][]) {
		if(matrix == null || matrix.length == 0 ||
				matrix[0] == null ||  matrix[0].length == 0) {
			return 0;
		}
		UnionFind unionFind = new UnionFind(matrix);
		int M = matrix.length;
		int N = matrix[0].length;
		
		// 处理第一行 如果当前位置是1，而且它左边也是1，则合并到一个集合中
		for(int i = 1; i <= N - 1; i++) {// 从第一列1开始，因为(0,0)没有左，不需要处理
			if(matrix[0][i - 1] =='1' && matrix[0][i] =='1') {
				unionFind.union(i - 1 , i);
			}
		}
		// 处理第一列 如果当前位置是1，而且它上边也是1，则合并到一个集合中
		for(int j = 1; j <= M - 1; j++) {// 从第一行1开始，因为(0,0)没有上，不需要处理
			if(matrix[j][0] =='1' && matrix[j - 1][0] =='1') {
				unionFind.union((j - 1) * N , j * N);
			}
		}
		// 处理其它剩下的部分，如果当前节点的左上节点是1，则进行合并
		for(int i = 1; i <= M - 1; i++) {
			for(int j = 1; j <= N - 1; j++) {
				if(matrix[i][j] == '1') {
					if(matrix[i - 1][j] == '1') { // 左边是1，合并
						unionFind.union((i - 1) * N + j , i * N + j);
					} 
					if(matrix[i][j - 1] == '1') { // 上边是1，合并
						unionFind.union((i) * N + j - 1, i * N + j);
					}
				}
			}
		}
		return unionFind.getSets();
	}
	
	public class UnionFind {
		int parents[]; // 记录每个节点的父节点是谁。parents[i] = k 表示的是i节点的父节点是k
		int size[]; // 记录代表节点代表的集合的大小。size[i] = j 代表节点i代表的集合的大小是j
		int helper[];
		int sets; // 并查集集合数量
		
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
					// 将fatheri节点的父亲设置成fatherj，也就是将小集合挂在了大集合上
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
			// hi要先-1，因为hi指向的是下一个可用位置
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
