package class15;

public class Code02_FriendCircles {
	/**
	 * 一群朋友中，有几个不相交的朋友圈（现在改成省份了）
	 * https://leetcode.com/problems/friend-circles/
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
	* 用数组实现并查集
	*/
	
    public static int findCircleNum(int[][] isConnected) {
    	if(isConnected == null || isConnected.length == 0) {
    		return 0;
    	}
    	int N = isConnected.length;
    	UnionFind unfind = new UnionFind(N);
    	for(int i = 0; i <= N - 1; i++) {
    		for(int j = i + 1; j <= N - 1; j++) {
    			if(isConnected[i][j] == 1) { // 如果i和j相连
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
//    		for(int cur : helper) { // 如果helper没有元素，全是0的时候，这个循环也会执行，会将parents设置为0
//    			parents[cur] = i;
//    		}
    		 for(hi--; hi >= 0;) {
                 parents[helper[hi]] = i;
                 hi--;
             }
    		return i;
    	}
    	public void union(int i, int j) {
    		int fatheri = findFather(i);
    		int fatherj = findFather(j);
    		if(fatheri != fatherj) {
    			if(size[i] < size[j]) { // 小挂大
    				size[j] += size[i];
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
