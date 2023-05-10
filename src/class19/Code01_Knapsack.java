package class19;

public class Code01_Knapsack {
	/**
	 * 背包问题
	 * 
	 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
	给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
	返回能装下的最大价值
	 */
	public static int way1(int w[], int v[], int bag) {
		if(w == null || w.length == 0 || v == null || v.length == 0 || bag < 0) {
			return 0;
		}
		return process(w, v, 0, bag);
	}
	// 1. 憋尝试，暴力递归
	// 从左往右的尝试模型，每一个货物都可以要和不要，枚举所有的情况，正确答案一定在其中
	// index之前的货物都考虑完了，当前来到了index，考虑index及其之后的货物要还是不要，
	// 返回最大价值
	// rest为背包剩下的最大容量
	public static int process(int w[], int v[], int index, int rest) {
		if(index == w.length)  { // 没有货物可装了
			return 0;
		}
		if(rest < 0) { // 背包容量不够了，不是<=0, 因为有可能有货物重量是0，就能装
			return -1; // 返回-1，表示上一次的决策有问题
		}
		// 装当前的货物
		int p1 = 0;
		if(rest - w[index] >= 0) { // 能装下才去装
			p1 = v[index] + process(w, v, index + 1, rest - w[index]);
		}
		/*
		 * 	int p1 = process(w, v, index + 1, rest);
			int p2 = 0;
			int next = process(w, v, index + 1, rest - w[index]);
			if (next != -1) { // 上面能装的下才装，写法更好
				p2 = v[index] + next;
			}
		 */
		// 不要当前的货物
		int p2 = process(w, v, index + 1, rest);
		return Math.max(p1, p2);
	}
	
	// 2. 直接改动态规划
	// 可变参数index和rest，范围: index 是 0~N ; rest是 负数~bag ==> int dp[N+1][bag+1]. bag的范围是负数 ~ bag, 假设是0~bag，负数在代码里面处理
	// 分析依赖 ==> 看尝试，依赖(index + 1, rest - w[index])和(index + 1, rest) ==> 依赖下一行的位置
	public static int way2(int w[], int v[], int bag) {
		if(w == null || w.length == 0 || v == null || v.length == 0 || bag < 0 
				|| w.length != v.length) {
			return 0;
		}
		int N = w.length - 1;
		int dp[][] = new int[N + 1][bag + 1];
		// 装当前的货物
		for(int index = N - 1; index >= 0; index--) { // 从下往上，从左往右填表(注意点)
			for(int rest = 0; rest <= bag; rest++) {
				int p1 = 0;
				if(rest - w[index] >= 0) {
					p1 = v[index] + dp[index + 1][rest - w[index]];
				}
				// 不要当前的货物
				int p2 = dp[index + 1][rest];
				dp[index][rest] = Math.max(p1, p2);
			}
		}
		return dp[0][bag];
	}
	public static void main(String[] args) {
		int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
		int[] values = { 5, 6, 3, 19, 12, 4, 2 };
		int bag = 15;
		System.out.println(way1(weights, values, bag));
		System.out.println(way2(weights, values, bag));
	}
}
