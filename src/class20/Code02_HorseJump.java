package class20;

public class Code02_HorseJump {
	/**
	请同学们自行搜索或者想象一个象棋的棋盘，
	然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
	那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
	给你三个 参数 x，y，k
	返回“马”从(0,0)位置出发，必须走k步
	最后落在(x,y)上的方法数有多少种?
	10*9的棋盘
	*/
	public static int ways1(int a, int b, int step) {
		return process(0, 0, step, a, b);
	}
	// 当前来到x,y位置，剩余rest步数可以走，要去a,b位置，你给我返回有多少种走法
	public static int process(int x, int y, int rest, int a, int b) {
		if(x < 0 || x > 8 || y < 0 || y > 9) { // 越界，走出了棋盘，直接返回0
			return 0;
		}
		if(rest == 0) { // 剩余0步可走
			if(x == a && y == b) { // 并且到达了目标位置，说明找到了一种走法，返回1
				return 1;
			} else {
				return 0;
			}
		}
		// 普遍位置，每一个点，都有8种走法, 每一个方向都是一种截然不同的走法, 累加
		int ways = process(x + 1, y + 2, rest - 1, a, b);
		ways += process(x + 2, y + 1, rest - 1, a, b);
		ways += process(x + 2, y - 1, rest - 1, a, b);
		ways += process(x + 1, y - 2, rest - 1, a, b);
		ways += process(x - 1, y - 2, rest - 1, a, b);
		ways += process(x - 2, y - 1, rest - 1, a, b);
		ways += process(x - 2, y + 1, rest - 1, a, b);
		ways += process(x - 1, y + 2, rest - 1, a, b);
		return ways;
	}
	
	// 改动态规划。
	// 可变参数x,y,rest ==> x的范围0~8, y的范围0~9, rest的范围0-K ==> int dp[9][10][K+1]
	// 三维动态规划，通过递归可以发现，每一层rest, 只依赖rest - 1层, 初始化好了第0层, 则可以弄好其它的所有层
	public static int jump(int a, int b, int K) {
		int dp[][][] = new int[9][10][K+1];
		dp[a][b][0] = 1; // 第一层，base case
		for(int rest = 1; rest <= K; rest++) { // 从下往上
			for(int x = 0; x <= 8; x++) { // 从左往右，从下往上，填每一行
				for(int y = 0; y <= 9; y++) {
					// 取值的时候，x+1, y+2这些操作可能越界, 搞一个函数来取值, 如果越界就返回0
					int ways = pick(dp, x + 1, y + 2, rest - 1);
					ways += pick(dp, x + 2, y + 1, rest - 1);
					ways += pick(dp, x + 2, y - 1, rest - 1);
					ways += pick(dp, x + 1, y - 2, rest - 1);
					ways += pick(dp, x - 1, y - 2, rest - 1);
					ways += pick(dp, x - 2, y - 1, rest - 1);
					ways += pick(dp, x - 2, y + 1, rest - 1);
					ways += pick(dp, x - 1, y + 2, rest - 1);
					dp[x][y][rest] = ways;
				}
			}
		}
		return dp[0][0][K];
	}
	public static int pick(int dp[][][], int x, int y, int rest) {
		if(x < 0 || x > 8 || y < 0 || y > 9) { // 越界，走出了棋盘，直接返回0
			return 0;
		}
		return dp[x][y][rest];
	}
	
	public static void main(String[] args) {
		int x = 7;
		int y = 7;
		int step = 10;
		System.out.println(ways1(x, y, step));
		System.out.println(jump(x, y, step));
	}

}
