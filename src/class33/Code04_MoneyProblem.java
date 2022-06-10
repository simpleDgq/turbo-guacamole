package class33;

public class Code04_MoneyProblem {
	/**
	 * 腾讯 
	 * 打怪兽需要的最小钱数
	 * int[] d，d[i]：i号怪兽的能力
		int[] p，p[i]：i号怪兽要求的钱
		开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
		如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；
		如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，
		他的能力直接累加到你的能力上。
		
		返回通过所有的怪兽，需要花的最小钱数。
	 */
	
	/**
	怪兽数量N-->500
	能力 0~10^6
	钱数1~200
	**/
	
	/**
	 * 思路1：
	 * 来到index号怪兽:
	 * 1. 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽
	 * 2. 如果你当前的能力，大于等于i号怪兽的能力，可以贿赂或者不贿赂
	 */
	public static int minMoney(int d[], int p[]) {
		if(d == null || p == null || 
				d.length == 0 || p.length == 0) {
			return 0;
		}
		return process(d, p, 0, 0);
	}
	// 当前来到index号元素，我的能力值是abality，要通过index号怪兽，给我返回最小钱数
	public static int process(int d[], int p[], int index, int abality) {
		if(index == d.length) { // 通过了所有的怪兽，不需要再花钱了
			return 0;
		}
		// index位置做选择
		int ans = 0;
		if(abality < d[index]) {//如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽
			ans = p[index] + process(d, p, index + 1, abality + d[index]); // 贿赂的钱 + 后面的怪兽通过的最小钱
		} else { // 如果你当前的能力，大于等于i号怪兽的能力，可以贿赂或者不贿赂。 两种选择取最小值
			ans = Math.min(p[index] + process(d, p, index + 1, abality + d[index]), // 贿赂
					process(d, p, index + 1, abality)); // 不贿赂
		}
		return ans;
	}
	
	// 改动态规划
	// 可变参数： index 和 abality ==> 范围：index 0~N abality:0 ~ allD(所有的p[i]累加) ==> int dp[N + 1][allP + 1]
	public static int minMoney1(int d[], int p[]) {
		if(d == null || p == null || 
				d.length == 0 || p.length == 0) {
			return 0;
		}
		int allD = 0;
		int N = d.length;
		for(int i = 0; i <= N - 1; i++) {
			allD += d[i];
		}
		int dp[][] = new int[N + 1][allD + 1];
		// 分析位置依赖
		// base case ==> 最后一行全是0
		for(int i = 0; i <= allD; i++) {
			dp[N][i] = 0;
		}
		// 从下往上，从左往右填
		for(int row = N - 1; row >= 0; row--) {
			for(int col = 0; col <= allD; col++) {
				// index位置做选择
				int ans = 0;
				if(dp[row][col] < d[row]) {//如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽
					ans = p[row] + process(d, p, row + 1, dp[row][col] + d[row]); // 贿赂的钱 + 后面的怪兽通过的最小钱
				} else { // 如果你当前的能力，大于等于i号怪兽的能力，可以贿赂或者不贿赂。 两种选择取最小值
					ans = Math.min(p[row] + process(d, p, row + 1, dp[row][col] + d[row]), // 贿赂
							process(d, p, row + 1, dp[row][col])); // 不贿赂
				}
				dp[row][col] = ans;
			}
		}
		return dp[0][0];
	}
	
	/**
	 * 思路2:
	 * 题目给出的条件
	 * 怪兽数量N-->500
		能力 0~10^6
		钱数1~200
		====
		能力值是 0~10^6, 那么第一种解法的情况下，会导致dp数组的第二维非常大，甚至超过10^8次方，导致填表不嫩完成。
		====
		
		只能换一种思路：
		从0~index怪兽，花的钱必须严格等于money,如果能通过index号怪兽，则返回获得的最大能力值，否则返回-1
	 */
	public static int minMoney2(int d[], int p[]) {
		if(d == null || p == null || 
				d.length == 0 || p.length == 0) {
			return 0;
		}
		int allMoney = 0;
		int N = p.length;
		for(int i = 0; i <= N - 1; i++) {
			allMoney += p[i];
		}
		// money从0元开始尝试，看能不能通过N-1号所有的怪兽，如果能够通过，则返回钱数，就是最小值
		for(int money = 0; money <= allMoney; money++) {
			if(process2(d, p, N - 1, money) != -1) {
				return money;
			}
		}
		// 尝试过所有的money都不能通过，那就是全部贿赂才能通过所有的怪兽
		return allMoney;
	}
	// 来到index号怪兽，花的钱必须严格等于money，给我返回通过index号怪兽之后的最大能力值
	public static int process2(int d[], int p[], int index, int money) {
		if(index < 0) {  // 注意，表示一个怪兽也没有遇到
			return money == 0 ? 0 : -1; //必须花0元， 则能力值是0. 如果必须花13，13，。元，根本没有遇到其怪兽，花不了，返回-1
		}
		// index 号怪兽做选择
		// 1. 不贿赂， 那么前面的0~index-1号怪兽，必须花掉严格的money
		int preMaxAbality = process2(d, p, index - 1, money);
		int p1 = -1;
		// preMaxAbality != -1 能通过前面的 index - 1号怪兽 && 获得的能力大于index号怪兽
		if(preMaxAbality != -1 && preMaxAbality >= d[index]) { 
			p1 = preMaxAbality;
		}
		// 2. 贿赂， 那0~index-1号必须能通过，而且必须严格花掉 money - p[index]这么多钱
		int preMaxAbality2 = process2(d, p, index - 1, money - p[index]);
		int p2 = -1;
		if(preMaxAbality2 != -1) { // 花掉 money - p[index]这么多钱能通过0~index-1号怪兽
			p2 = preMaxAbality2 + d[index];
		}
		return Math.max(p1, p2);
	}
	
	// 改动态规划
	// 可变参数 index 和 money 范围：index 0~N-1 money:0~allMoney ==> int dp[N][allMoney + 1] 
	// 适用于allMoney加起来比较小的情况
	public static int minMoney22(int d[], int p[]) {
		if(d == null || p == null || 
				d.length == 0 || p.length == 0) {
			return 0;
		}
		int allMoney = 0;
		int N = p.length;
		for(int i = 0; i <= N - 1; i++) {
			allMoney += p[i];
		}
		int dp[][] = new int[N][allMoney + 1];
		// 填好第0行
		// 0号怪兽，必须花p[0]的钱，获得d[0]的能力, 才能通过。其他第0行的状态一律是无效的
		dp[0][p[0]] = d[0];
		for (int j = 0; j <= allMoney; j++) {
			if(j != p[0]) { // 其他第0行的状态一律是无效的
				dp[0][j] = -1;
			}
		}
		
		// 分析依赖，每一行只依赖上一行
		// 从上往下，从左往右填表
		for(int index = 1; index <= N - 1; index++) {
			for(int money = 0; money <= allMoney; money++) {
				// index 号怪兽做选择
				// 1. 不贿赂， 那么前面的0~index-1号怪兽，必须花掉严格的money
				int preMaxAbality = dp[index - 1][money];
				int p1 = -1;
				// preMaxAbality != -1 能通过前面的 index - 1号怪兽 && 获得的能力大于index号怪兽
				if(preMaxAbality != -1 && preMaxAbality >= d[index]) { 
					p1 = preMaxAbality;
				}
				// 2. 贿赂， 那0~index-1号必须能通过，而且必须严格花掉 money - p[index]这么多钱
				int p2 = -1;
				if(money - p[index] >= 0) {
					int preMaxAbality2 = dp[index - 1][money - p[index]];
					if(preMaxAbality2 != -1) { // 花掉 money - p[index]这么多钱能通过0~index-1号怪兽
						p2 = preMaxAbality2 + d[index];
					}
				}
				dp[index][money] = Math.max(p1, p2);
			}
		}
		
		// 遍历最后一行，谁最先不是-1，谁就是答案
		int ans = 0;
		for(int money = 0; money <= allMoney; money++) {
			if(dp[N - 1][money] != -1) {
				ans = money;
				break;
			}
		}
		return ans;
	}
	
	public static int[][] generateTwoRandomArray(int len, int value) {
		int size = (int) (Math.random() * len) + 1;
		int[][] arrs = new int[2][size];
		for (int i = 0; i < size; i++) {
			arrs[0][i] = (int) (Math.random() * value) + 1;
			arrs[1][i] = (int) (Math.random() * value) + 1;
		}
		return arrs;
	}
	
	public static void main(String[] args) {
		int len = 10;
		int value = 20;
		int testTimes = 10000;
		for (int i = 0; i < testTimes; i++) {
			int[][] arrs = generateTwoRandomArray(len, value);
			int[] d = arrs[0];
			int[] p = arrs[1];
			long ans1 = minMoney(d, p);
			long ans2 = minMoney1(d, p);
			long ans3 = minMoney2(d, p);
			long ans4 = minMoney22(d,p);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
				System.out.println("oops!");
			}
		}

	}
}
