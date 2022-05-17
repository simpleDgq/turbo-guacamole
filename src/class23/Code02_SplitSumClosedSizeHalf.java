package class23;

public class Code02_SplitSumClosedSizeHalf {
	/**
	 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
		如果arr长度为偶数，两个集合包含数的个数要一样多
		如果arr长度为奇数，两个集合包含数的个数必须只差一个
		请尽量让两个集合的累加和接近
		返回最接近的情况下，较小集合的累加和 ==> 较小集合，指的是集合里
		面的元素个数，元素个数少的集合更大，更接近rest。例如1，2,4 这个集合划分为[1,2]和4，返回4
		（字节）
	 */
//	两种情况：
//	1. 如果arr长度是偶数，比如是8，整个集合的和是100，那么问题变成怎么拿4个数
//	凑出离50最近的集合 （1）
//	2. 如果arr长度是奇数，比如是7，整个集合的和是100，那么问题变成怎么
//	拿4个数凑出离50最近的集合
//	拿3个数凑出离50最近的集合
//	这两种情况下，取离50最近的集合
	public static int way(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		int N = arr.length;
		for(int i = 0; i <= N - 1; i++) {
			sum += arr[i];
		}
		// 偶数
		if(N % 2 == 0) {
			return process(0, sum / 2, N / 2, arr);
		} else {
			int p1 = process(0, sum / 2, N / 2, arr);
			int p2 = process(0, sum / 2, N / 2 + 1, arr);
			return Math.max(p1, p2);
		}
	}
	
	// 当前来到index位置，index和之后的数都可以自由选择，凑出累加和尽量接近rest的，但不能超过rest的集合
	// 每个集合一定要挑够picks个数，给我返回最小集合的累加和
	public static int process(int index, int rest, int picks, int arr[]) {
		if(index == arr.length) { // 没有数了，如果picks=0,说明不用挑了，返回0，如果picks!=0,说明还要条，但是已经没有数，
			// 返回-1,表示不可能完成
			return picks == 0 ? 0: -1; 
		}
		// 不选index位置
		int p1 = process(index + 1, rest, picks, arr);
		// 选index位置
		int p2 = -1; // 返回-1表示没办法完成
		int next = -1;
		if(arr[index] <= rest) {
			next = process(index + 1, rest - arr[index], picks - 1, arr);
			if(next != -1) {
				p2 = arr[index] + next;
			}
		}
		return Math.max(p1, p2);	
	}
	
	/**
	 * 动态规划
	 * 可变参数：index, int rest, int picks
	 * 范围：index：0~N rest：0~sum/2 
	 * picks范围，8偶数的时候，0~4，7基数的时候，0~3和0~4，有个取0~4 ==> 向上取整 (N+1) / 2
	 *   ==> int dp[N+1][sum/2+1][(N+1) / 2 + 1]
	 * 
	 * 三维动态规划，index是第三维，搞定最下面的一层，从下往上搞定其它层
	 * process(index + 1, rest, picks, arr)
	 * process(index + 1, rest - arr[index], picks - 1, arr)
	 * index层只依赖index+1，所以index作为第三维
	 */
	public static int dp2(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		int N = arr.length;
		for(int i = 0; i <= N - 1; i++) {
			sum += arr[i];
		}
		sum /= 2;
		int M = (N+1) / 2;
		int dp[][][] = new int[N + 1][sum + 1][M + 1]; // index, rest, picks
		for(int rest = 0; rest <= sum; rest++) {
			for(int picks = 0; picks <= M; picks++) {
				dp[N][rest][picks] = -1; // N层全是-1
			}
		}
		for(int rest = 0; rest <= sum; rest++) {
			dp[N][rest][0] = 0; // index = N, picks = 0的时候，改成0
		}
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= sum; rest++) {
				for(int picks = 0; picks <= M; picks++) {
					int p1 = dp[index + 1][rest][picks];
					int p2 = -1; // 返回-1表示没办法完成
					int next = -1;
					if(arr[index] <= rest && picks - 1 >= 0) {
						next = dp[index + 1][rest - arr[index]][picks - 1];
						if(next != -1) {
							p2 = arr[index] + next;
						}
					}
					dp[index][rest][picks] = Math.max(p1, p2);	
				}
			}
		}
		// 偶数
		if(N % 2 == 0) {
			return dp[0][sum][N / 2];
		} else {
			int p1 = dp[0][sum][N / 2];
			int p2 = dp[0][sum][N / 2 + 1];
			return Math.max(p1, p2);
		}
	}
}
