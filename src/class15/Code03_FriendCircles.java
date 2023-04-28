package class15;

//https://leetcode.cn/problems/friend-circles/
public class Code03_FriendCircles {
	/**
	 * 一群朋友中，有几个不相交的朋友圈（现在改成省份了）
	 * 
	 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
		省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
		给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 
		表示二者不直接相连。
		返回矩阵中 省份 的数量。
	*
	* 思路：并查集。
	* 遍历给定的数组的右上部分，如果(i,j)位置是1，表示这两个省份能够连接，
	* 直接将i和j合并到并查集中，最终返回并查集中集合的数量。
	* 
	* 为什么值遍历数组的右上部分? 比如矩阵是5*5的，那么1行3列位置的值是1，表示的事1城市和3城市直接连接，
	* 反过来，3行1列，也是表示的3城市和1城市相连；所以左下部分可以不考虑。
	* 
	* 用数组实现并查集
	*/
	
    public static int findCircleNum(int[][] isConnected) {
    	if(isConnected == null || isConnected.length == 0) {
    		return 0;
    	}
    	int N = isConnected.length;
    	UnionFind unfind = new UnionFind(N);
    	// 这里不包含对角线的元素，对角线的元素，在UnionFind初始化的时候，都加入到并查集中了
    	for(int i = 0; i <= N - 2; i++) {
    		for(int j = i + 1; j <= N - 1; j++) {
    			if(isConnected[i][j] == 1) { // 如果i和j相连，加入到并查集中
    				unfind.union(i, j);
    			}
    		}
    	}
    	return unfind.sets;
    }
    public static class UnionFind {
    	private int[] parents; // parents[i] = k, 表示的是i的代表节点是k
    	private int[] size; // size[i] = k, 表示的是代表节点i表示的集合的大小是k
    	private int sets; // 总共右多少个集合
    	private int helper[]; // 用数组代替栈
    	
    	public UnionFind(int N) {
    		parents = new int[N];
    		size = new int[N];
    		helper = new int[N];
    		sets = N; // 一开始有N个集合
    		// 刚开始，每个数单独成为一个集合
    		for(int i = 0; i <= N - 1; i++) {
    			parents[i] = i;
    			size[i] = 1;
    		}
    	}
    	public int findFather(int i) {
    		int hi = 0;
    		while(i != parents[i]) {
    			helper[hi++] = i;
    			i = parents[i];
    		}
    		// 这种写法，如果helper没有元素，全是0的时候，这个循环也会执行，会将parents设置为0
    		// 所以不能这样写
//    		for(int cur : helper) {
//    			parents[cur] = i;
//    		}
    		for(hi--; hi >= 0; hi--) {
                parents[helper[hi]] = i;
            }
    		return i;
    	}
    	public void union(int i, int j) {
    		int fatheri = findFather(i);
    		int fatherj = findFather(j);
    		if(fatheri != fatherj) {
    			if(size[i] < size[j]) { // 小挂大
    				size[j] += size[i];
    				// i代表节点代表的小集合，挂在j代表节点代表的大集合上
    				parents[fatheri] = fatherj;
    			} else {
    				size[i] += size[j];
    				parents[fatherj] = fatheri;
    			}
    			sets--;
    		}
    	}
    	public int getSets() {
    		return sets;
    	}
    }
}
