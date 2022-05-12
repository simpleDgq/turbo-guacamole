package class18;

public class Code02_CardsWin {
	/**
	 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
		玩家A和玩家B依次拿走每张纸牌
		规定玩家A先拿，玩家B后拿
		但是每个玩家每次只能拿走最左或最右的纸牌
		玩家A和玩家B都绝顶聪明
		请返回最后获胜者的分数
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
		int p1 = arr[L] + g(arr, L + 1, R); // 拿左边，然后变成后手拿牌，L+1~R上拿最好的分数
		int p2 = arr[R] + g(arr, L , R - 1); // 拿右边，然后变成后手拿牌，L~R-1上拿最好的分数
		return Math.max(p1, p2);
	}
	//后手拿牌，arr[L..R]，L到R上，后手拿牌能获得的最好分数返回
	public static int g(int arr[], int L, int R) {
		if(L == R) { // 只剩一张牌，因为是后手，所以拿不到，返回0
			return 0;
		}
		// 后手拿牌，先手拿了左边或者右边，然后自己变成了先手，
		int p1 = f(arr, L + 1, R); // 先手拿了左边，L+1~R上拿最好的分数
		int p2 = f(arr, L , R - 1); // 先手拿右边，L~R-1上拿最好的分数
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
 		for(int i = 0; i <= N - 1; i++) {
 			for(int j = 0; j <= N - 1; j++) {
 				fMap[i][j] = -1;
 				gMap[i][j] = -1;
 	 		}
 		}
		int first = f(arr, 0, N - 1, fMap, gMap); // 先手拿，能获得的最好分数
		int second = g(arr, 0, N - 1, fMap, gMap);// 后手拿，能获得的最好分数
		return Math.max(first, second);
	}
	//先手拿牌，arr[L..R]，L到R上，先手拿牌能获得的最好分数返回
	public static int f(int arr[], int L, int R, int fMap[][], int gMap[][]) {
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
		fMap[L][R] = ans;
		return ans;
	}
	//后手拿牌，arr[L..R]，L到R上，后手拿牌能获得的最好分数返回
	public static int g(int arr[], int L, int R, int fMap[][], int gMap[][]) {
		if(gMap[L][R] != -1) {
			return gMap[L][R];
		}
		int ans = 0;
//		if(L == R) { // 只剩一张牌，因为是后手，所以拿不到，返回0
//			return 0;
//		} // 这个if, ans已经是0了，就不需要这个分支了
		if(L != R) {
			// 后手拿牌，先手拿了左边或者右边，然后自己变成了先手，
			int p1 = f(arr, L + 1, R, fMap, gMap); // 先手拿了左边，L+1~R上拿最好的分数
			int p2 = f(arr, L , R - 1, fMap, gMap); // 先手拿右边，L~R-1上拿最好的分数
			ans = Math.min(p1, p2); // 两个人都是绝对聪明，先手一定会把最小的选择留给你
		}
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
		for(int i = 0; i <= N - 1; i++) {
			fMap[i][i] = arr[i];
		}
		for(int startCol = 1; startCol <= N -1; startCol++) { // 斜着填表，从第一列开始，
			int L = 0;
			int R = startCol;
			while(L <= N -1 && R <= N -1) {
				fMap[L][R] = Math.max(arr[L] + gMap[L+1][R], arr[R] + gMap[L][R - 1]);
				gMap[L][R] = Math.min(fMap[L + 1][R], fMap[L][R - 1]);
				L++;
				R++;
			}
		}
		return Math.max(fMap[0][N - 1], gMap[0][N - 1]);
	}
	
	public static void main(String[] args) {
		int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));
		System.out.println(win3(arr));
	}
}
