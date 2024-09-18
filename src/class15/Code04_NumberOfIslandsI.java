package class15;

//https://leetcode.cn/problems/number-of-islands/
public class Code04_NumberOfIslandsI {
	/**
	 * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量.
	 * 
	 * 递归解法:  DFS
	 * 遍历martix中的每一个数，如果是1，岛屿数量加1，而且将它感染成2，同时，感染它的上下左右的点，同样如果是1，也感染成2；
	 * 如果是0的话，直接开始下一个元素。
	 * 
	 * 当前改2，然后递归感染上下左右。改2是为了标记这个位置已经被遍历过，不用再处理，避免处理下一个元素的时候，又会处理当前元素，避免
	 * 递归不能退出
	 * 
	 * 时间复杂度：O(M*N)， M和N是矩阵长宽。主过程每个元素遍历一遍，感染的过程，
	 * 每个元素只会被上下左右各处理一遍，每个元素总共5遍。 5  * M * N  ==> O(M*N)
	 * 
	 * 并查集解法:
	 * 
	 * 用数组实现: 对于一个M * N的matrix数组，并查集中开辟的数组的长度是M*N，
	 * 用一维数组表示matrix中每一个元素，对应的下标是i * 列数 + j。
	 * 
	 * 1. 初始化的时候，每个1单独搞成一个并查集： matrix中所有的1的代表节点和parent都是自己，size都是1。
	 * 2. 遍历所有的元素，对当前元素的左上，如果是1，则进行合并。合并的过程中，集合数量--。
	 * 返回最终的集合数量。
	 * 
	 * 第一行没有上，第一列没有左，所以分开处理，处理完第一行和第一列，在处理剩下的，
	 * 避免很多边界条件判断。
	 * 
	 */
	// 递归解法
    public int numIslands(char[][] grid) {
        /**
         * 解法1：
         * DFS遍历
         * 对于每一个位置，都遍历一遍，如果当前位置是1，则岛屿数量加1，当前位置感染成2，然后去递归搞上下左右的位置
         * ，如果当前位置是0，说明不是岛屿，直接搞下一个位置
         * 如果是2，说明以前已经遍历过这个节点，直接搞下一个位置
         */
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int N = grid.length;
        int M = grid[0].length;
        int ans = 0;
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= M - 1; j++) {
                // 当前位置是1，说明是独立的1，没有被感染还
                // 岛屿数量+1
                if (grid[i][j] == '1') {
                    ans++;
                    inject(grid, i, j); // 发现一个1，就感染和它连通的1
                }
            }
        }
        return ans;
    }

    // 当前来到i,j位置，将当前位置感染成2，递归去搞上下左右的位置
    public void inject(char[][] grid, int i, int j) {
        // 如果越界了，直接返回
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        // 如果当前位置是2，说明已经感染过了，直接返回
        // 如果当前位置是0，说明不是有效的岛屿，直接搞下一个位置
        if(grid[i][j] == '2' || grid[i][j] == '0') {
            return;
        }
        // 感染当前位置，递归感染上下左右位置
        grid[i][j] = '2';
        inject(grid, i - 1, j);
        inject(grid, i + 1, j);
        inject(grid, i, j - 1);
        inject(grid, i, j + 1);
    }

    /**
     * 并查集解法:
     * 
     * 用数组实现: 对于一个M * N的matrix数组，并查集中开辟的数组的长度是M*N，
     * 用一维数组表示matrix中每一个元素，对应的下标是i * 列数 + j。
     * 
     * 1. 初始化的时候，每个1单独搞成一个并查集： matrix中所有的1的代表节点和parent都是自己，size都是1。
     * 2. 遍历所有的元素，对当前元素的左上，如果是1，则进行合并。合并的过程中，集合数量--。
     * 返回最终的集合数量。
     * 
     * 第一行没有上，第一列没有左，所以分开处理，处理完第一行和第一列，在处理剩下的，
     * 避免很多边界条件判断。
     * 
     */
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0 ||
                grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        // 初始话并查集
        Union union = new Union(grid);
        int N = grid.length;
        int M = grid[0].length;
        // 搞第一行, 如果左边是1，则合并到一个集合里面 从1开始，0位置没有左边
        for (int j = 1; j <= M - 1; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                union.union(j, j - 1); // m,n位置对应的下标是m * M + n 第0行m是0，只取决于n n其实就是这里的j
            }
        }
        // 搞第一列
        for (int i = 1; i <= N - 1; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                union.union(i * M, (i - 1) * M); // m,n位置对应的下标是m * M + n 第0列n是0，只取决于m m其实就是这里的i
            }
        }
        // 剩余的位置
        for (int i = 1; i <= N - 1; i++) {
            for (int j = 1; j <= M - 1; j++) {
                // 如果左边是1，则合并
                if (grid[i][j - 1] == '1' && grid[i][j] == '1') {
                   union.union(i * M + j, i * M + j - 1);
                }
                // 如果上边是1，则合并
                if (grid[i - 1][j] == '1' && grid[i][j] == '1') {
                    union.union(i * M + j, (i - 1) * M + j);
                }
            }
        }
        return union.getSets();
    }

    class Union {
        private int parents[]; // 记录每个节点的父节点是谁
        private int size[]; // 记录每个代表节点所代表的集合的大小
        private int sets; // 记录集合数
        private int helper[]; // 栈，往上父节点的过程中，存储所有经过的节点

        public Union(char[][] grid) {
            int N = grid.length;
            int M = grid[0].length;
            // 用一维数组表示matrix中每一个元素，对应的下标是i * 列数 + j。 下标不好超过元素个数N*M
            this.parents = new int[N * M];
            this.size = new int[N * M];
            this.sets = 0;
            this.helper = new int[N * M];
            // 初始化的时候，每个1单独搞成一个并查集
            for (int i = 0; i <= N - 1; i++) {
                for (int j = 0; j <= M - 1; j++) {
                    if (grid[i][j] == '1') { // 如果是1，单独成集合
                        int index = i * M + j;
                        // 当前节点的父节点是自己
                        parents[index] = index;
                        // 当前节点代表的集合的大小是1
                        size[index] = 1;
                        // 集合数加1
                        sets++;
                    }
                }
            }
        }

        // 给定一个节点，找它的代表节点
        public int findFather(int index) {
            int hi = 0;
            // 当前节点的父节点不是它自己，说明当前节点不是代表节点，继续往上
            while (index != parents[index]) {
                // 经过的节点放在helper中
                helper[hi++] = index;
                // 继续往上
                index = parents[index];
            }
            // 从help数组的后面开始，依次将经过的节点直接挂在代表节点上
            // hi要先-1，因为hi指向的是下一个可用位置
            for (hi--; hi >= 0;) {
                // 当前经过的节点的父节点设置成代表节点，就表示挂上了
                parents[helper[hi]] = index;
                hi--;
            }
            return index;
        }

        // 给定两个节点，合并这两个节点所在的集合
        public void union(int i, int j) {
            int fatheri = findFather(i);
            int fatherj = findFather(j);
            // 如果不是在同一个机会才合并
            if (fatheri != fatherj) {
                // 小的集合，挂在大的集合下面，减缓链生成的速度
                if (size[fatheri] < size[fatherj]) {
                    // 将小集合的代表节点设置成大集合
                    parents[fatheri] = fatherj;
                    // 大的集合的size加上小的集合
                    size[fatherj] += size[fatheri];
                    sets--;
                } else {
                    // 将小集合的代表节点设置成大集合
                    parents[fatherj] = fatheri;
                    // 大的集合的size加上小的集合
                    size[fatheri] += size[fatherj];
                    sets--;
                }
            }
        }
        public int getSets() {
            return this.sets;
        }
    }
}
