package class39;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Code02_SnackWays {

	/**
	 * 
		背包容量为w
		一共有n袋零食, 第i袋零食体积为v[i]
		总体积不超过背包容量的情况下，
		一共有多少种零食放法？(总体积为0也算一种放法)。
	
	 */
	public static int ways1(int v[], int w) {
		if(v == null || v.length == 0) {
			return -1;
		}
		return process(0, w, v);
	}
	
	// 当前来到index位置，背包剩余容量为rest, 你给我返回有多少种方法
	public static int process(int index, int rest, int arr[]) {
		if(rest < 0) {
			return -1;
		}
		
		if(index == arr.length) {
			return 1;
		}
		int res1 = process(index + 1, rest, arr);
		int res2 = process(index + 1, rest - arr[index], arr);
		return res1 + (res2 == -1 ? 0 : res2);
	}
	
	// 动态规划
	// 可变参数 index：0 ~ N rest: 0 ~ w
	// 如果N很大或者w很大那么，这种方法就不能完成
	public static int ways2(int v[], int w) {
		if(v == null || v.length == 0) {
			return -1;
		}
		int N = v.length;
		int dp[][] = new int[N + 1][w + 1];
		
		// base case, 最后一行，全是1
		for(int j = 0; j <= w; j++) {
			dp[N - 1][j] = 1;
		}
		// 从下往上，从左往右
		for(int i = N - 2; i >= 0; i--) {
			for(int j = 0; j <= w; j++) {
				int res1 = dp[i + 1][j];
				int res2 = -1;
				if(j - v[i] >=  0) {
					res2 = dp[i + 1][j - v[i]];
				}
				dp[i][j] = res1 + (res2 == -1 ? 0 : res2);
			}
		}
		return dp[0][w];
	}
	
	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 };
		int w = 8;
		System.out.println(ways1(arr, w));
		System.out.println(ways2(arr, w));
		System.out.println(ways3(arr, w));
	}
	
	/**
	 * 		本题数据量
			1<=n<=30, 1<=w<=2*10^9
			v[i]: (0<=v[i]<=10^9)
			如果按经典方法, 做二维表, 凑体积j最大值是所有零食体积的累加和, 你必须准备这么多空间，把它填满。
			结果你单独一包零食就是10^9, 这个方法不行。
			n很小用分治的办法
	 */
	public static long ways3(int arr[], int w) {
		int N = arr.length;
		
		int mid = (N - 1) >> 1;
		TreeMap<Integer, Integer> map1 = new TreeMap<Integer, Integer>();
		int ways = process(0, mid, 0, w, arr, map1); // 只取左边的数，答案累加到其中了
		TreeMap<Integer, Integer> map2 = new TreeMap<Integer, Integer>();
		ways += process(mid + 1, N - 1, 0, w, arr, map1); // 只取右边的数，答案累加到其中了
		
		// 生成preMap
		TreeMap<Integer, Integer> preMap = new TreeMap<Integer, Integer>();
		Integer pre = 0;
		for(Entry entry : map2.entrySet()) {
			pre += (Integer) entry.getValue();
			preMap.put((Integer) entry.getKey(),  pre);
		}
		// 一个取左边，一个取右边
		// 左侧我累加和1的时候有3种了, 因为bag最大为
		// 8，不能超过这个容量。所以对右侧要求是你的累加和必须小于等于7, 拿记录16，
		// 总共的方法数就是3*16 = 48种
		for(Entry entry : map1.entrySet()) {
			int lWays = (int) entry.getValue();
			int lWeight = (int)entry.getKey();
			Integer floor = preMap.floorKey(w - lWeight); // 求右边累加和最接近w-lWeight的 key是什么
			if(floor != null) {
				ways += lWays * preMap.get(floor);
			}
		}
		return ways;
	}
	
	// [index...end]位置，自由选择，当前组成的累加和是sum，不能超过背包的最大容量，返回有多少种方法，并且将能够组成的累加和
	// 的方法数填在map里
	public static int process(int index, int end, int sum, int w, int arr[], TreeMap<Integer, Integer> map) {
		if(sum > w) {
			return 0;
		}
		// sum <= w
		if(index == arr.length) {
			if(sum == 0) { // 不能一个商品也不选
				return 0;
			}
			if(map.containsKey(sum)) {
				map.put(sum, map.get(sum) + 1);
			} else {
				map.put(sum, 1);
			}
			return 1;
		}
		// 要index
		int ways = process(index + 1, end, sum + arr[index], w, arr, map);
		// 不要index
		ways += process(index + 1, end, sum, w, arr, map);
		return ways;
	}
}
