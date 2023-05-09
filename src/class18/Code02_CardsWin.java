package class18;

public class Code02_CardsWin {
	/**
	 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
	 * 玩家A和玩家B依次拿走每张纸牌
	 * 规定玩家A先拿，玩家B后拿
	 * 但是每个玩家每次只能拿走最左或最右的纸牌
	 * 玩家A和玩家B都绝顶聪明
	 * 请返回最后获胜者的分数
	 * 
	 * 思路:
	 * 两个函数:
	 * 1.先手拿牌，arr[L..R]，L到R上，先手拿牌能获得的最好分数返回
	 * 2.后手拿牌，arr[L..R]，L到R上，后手拿牌能获得的最好分数返回
	 * 两者取最大值
	 */
	public static int win1(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int first = f(arr, 0, N - 1); // 先手拿，能获得的最好分数
		int second = g(arr, 0, N - 1);// 后手拿，能获得的最好分数
		return Math.max(first, second);
	}
	// 1.憋尝试
	//先手拿牌，arr[L..R]，L到R上，先手拿牌能获得的最好分数返回
	public static int f(int arr[], int L, int R) {
		if(L == R) { // 只剩一张牌，直接返回
			return arr[L];
		}
		// 剩多张牌，有两种拿法
		int p1 = arr[L] + g(arr, L + 1, R); // 拿左边，然后自己变成后手拿牌，L+1~R上拿最好的分数
		int p2 = arr[R] + g(arr, L , R - 1); // 拿右边，然后自己变成后手拿牌，L~R-1上拿最好的分数
		return Math.max(p1, p2);
	}
	//后手拿牌，arr[L..R]，L到R上，后手拿牌能获得的最好分数返回
	public static int g(int arr[], int L, int R) {
		if(L == R) { // 只剩一张牌，因为是后手，所以拿不到，返回0
			return 0;
		}
		// 你是后手，现在对方在拿牌，对方拿完之后你才可以拿。
		// 对方拿了左边，对方拿完之后，你就变成了先手，则能获得的最好分数是f(arr, L + 1, R)
		int p1 = f(arr, L + 1, R); // 先手拿了左边，L+1~R上拿最好的分数
		// 对方拿了右边，对方拿完之后，你变成了先手，则能获得的最好分数是f(arr, L, R - 1)
		int p2 = f(arr, L , R - 1); // 先手拿右边，L~R-1上拿最好的分数
		// 为什么取min？ 因为是在对手挑完之后，你才拿的，对手也是绝顶聪明，只会把最小的结果留给你
		return Math.min(p1, p2); // 两个人都是绝对聪明，先手一定会把最小的选择留给你
	}
	
	
	// 2.傻缓存， f依赖g，g依赖f，搞两张缓存表fMap，gMap
	// 可变参数L和R，范围都是0~N-1 ==> int dp[N][N]
	public static int win2(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int fMap[][] = new int[N][N];
		int gMap[][] = new int[N][N];
		// -1表示没有计算过
 		for(int i = 0; i <= N - 1; i++) {
 			for(int j = 0; j <= N - 1; j++) {
 				fMap[i][j] = -1;
 				gMap[i][j] = -1;
 	 		}
 		}
 		// 挂着缓存去玩
		int first = f(arr, 0, N - 1, fMap, gMap); // 先手拿，能获得的最好分数
		int second = g(arr, 0, N - 1, fMap, gMap);// 后手拿，能获得的最好分数
		return Math.max(first, second);
	}
	//先手拿牌，arr[L..R]，L到R上，先手拿牌能获得的最好分数返回
	public static int f(int arr[], int L, int R, int fMap[][], int gMap[][]) {
		// 如果计算过，直接返回
		if(fMap[L][R] != -1) {
			return fMap[L][R];
		}
		int ans = 0;
		if(L == R) { // 只剩一张牌，直接返回
			ans = arr[L];
		} else {
			// 剩多张牌，有两种拿法
			int p1 = arr[L] + g(arr, L + 1, R, fMap, gMap); // 拿左边，然后变成后手拿牌，L+1~R上拿最好的分数
			int p2 = arr[R] + g(arr, L , R - 1, fMap, gMap); // 拿右边，然后变成后手拿牌，L~R-1上拿最好的分数
			ans = Math.max(p1, p2);
		}
		// 记录在缓存表中
		fMap[L][R] = ans;
		return ans;
	}
	//后手拿牌，arr[L..R]，L到R上，后手拿牌能获得的最好分数返回
	public static int g(int arr[], int L, int R, int fMap[][], int gMap[][]) {
		// 如果计算过，直接返回
		if(gMap[L][R] != -1) {
			return gMap[L][R];
		}
		int ans = 0;
//		if(L == R) { // 只剩一张牌，因为是后手，所以拿不到，返回0
//			return 0;
//		} // 这个if, ans已经是0了，就不需要这个分支了
		if(L != R) {
			// 后手拿牌，先手拿了左边或者右边，然后当前变成了先手，
			int p1 = f(arr, L + 1, R, fMap, gMap); // 先手拿了左边，L+1~R上拿最好的分数
			int p2 = f(arr, L , R - 1, fMap, gMap); // 先手拿右边，L~R-1上拿最好的分数
			ans = Math.min(p1, p2); // 两个人都是绝对聪明，先手一定会把最小的选择留给你
		}
		// 记录在缓存表中
		gMap[L][R] = ans;
		return ans;
	}
	
	//3. 改动态规划
	// 分析依赖，
	// arr[L] + g(arr, L + 1, R)  ==> fMap依赖gMap(L + 1, R) ==> 下
	// arr[R] + g(arr, L , R - 1); ==> fMap还依赖gMap(L, R - 1) ==> 左
	public static int win3(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int fMap[][] = new int[N][N];
		int gMap[][] = new int[N][N];
		// 填好对角线，fMap的对角线就是arr[L]的值
		// gMap的对角线全是0，不用填
		for(int i = 0; i <= N - 1; i++) {
			fMap[i][i] = arr[i];
		}
		// 斜着按对角线填表，从第一列开始，
		for(int startCol = 1; startCol <= N -1; startCol++) {
			int L = 0;
			int R = startCol;
			while(L <= N -1 && R <= N -1) {
				// fMap依赖gMap的左和下位置
				fMap[L][R] = Math.max(arr[L] + gMap[L + 1][R], arr[R] + gMap[L][R - 1]);
				// gMap依赖fMap的左和下位置
				gMap[L][R] = Math.min(fMap[L + 1][R], fMap[L][R - 1]);
				L++;
				R++;
			}
		}
		// fMap个gMap的(0,N-1)位置PK出最大值
		return Math.max(fMap[0][N - 1], gMap[0][N - 1]);
	}
	
	public static void main(String[] args) {
		int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));
		System.out.println(win3(arr));
	}
}
