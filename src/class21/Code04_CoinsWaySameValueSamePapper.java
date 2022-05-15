package class21;

import java.util.HashMap;
import java.util.Set;

public class Code04_CoinsWaySameValueSamePapper {
	/**
	 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
		每个值都认为是一张货币，
		认为值相同的货币没有任何不同，
		返回组成aim的方法数
		例如：arr = {1,2,1,1,2,1,2}，aim = 4
		方法：1+1+1+1、1+1+2、2+2
		一共就3种方法，所以返回3
	 */
	/**
	 * 这题每种面值，都有了张数限制。
	 * 先从原始数组中，求出有哪些面值，以及每种面值有几张
	 */
	public static int coinsWay(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Info info = getInfo(arr);
		int[] coins = info.coins;
		int[] zhangs = info.zhangs;
		return process(0, aim, coins, zhangs);
	}

	public static class Info {
		int coins[];
		int zhangs[];
		public Info(int coins[], int zhangs[]) {
			this.coins = coins;
			this.zhangs = zhangs;
		}
	}
	
	public static Info getInfo(int arr[]) {
		if(arr == null ||arr.length == 0) {
			return null;
		}
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int cur : arr) {
			if(!map.containsKey(cur)) {
				map.put(cur, 1);
			} else {
				map.put(cur, map.get(cur) + 1);
			}
		}
		int N = map.size();
		int[] coins = new int[N];
		int[] zhangs = new int[N];
		// key 就是 coins
		// value 就是zhangs
		Set<Integer> coinsSet  = map.keySet();
		int index = 0;
		for(int coin : coinsSet) {
			coins[index] = coin;
			zhangs[index] = map.get(coin);
			index++;
		}
		return new Info(coins, zhangs);
	}
	
	// 当前来到了index位置，要组成rest这个数，给我返回所有的方法总数
	public static int process(int index, int rest, int coins[], int zhangs[]) {
		if(index == coins.length) {
			return rest == 0 ? 1 : 0;
		}
		// 每个位置只能选对应的张数
		int ways = 0;
		for(int zhang = 0; zhang <= zhangs[index] && rest - zhang*coins[index] >= 0; zhang++) {
			ways += process(index + 1, rest - zhang*coins[index], coins, zhangs);
		}
		return ways;
	}
	
	/**
	 * 改动态规划
	 * 可变参数，index和rest
	 * 范围：index: 0 ~ N  rest: 0 ~ aim ==> int dp[N+1][aim+1]
	 */
	public static int dp(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Info info = getInfo(arr);
		int[] coins = info.coins;
		int[] zhangs = info.zhangs;
		int N = coins.length;
		
		int dp[][] = new int[N+1][aim+1];
		dp[N][0] = 1;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				for(int zhang = 0; zhang <= zhangs[index] && rest - zhang*coins[index] >= 0; zhang++) {
					ways += dp[index + 1][rest - zhang*coins[index]];
				}
				dp[index][rest] = ways;
			}
		}
		return dp[0][aim];
	}
	
	/**
	 * 有枚举行为，用临近位置进行优化
	 */
	public static int dp2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Info info = getInfo(arr);
		int[] coins = info.coins;
		int[] zhangs = info.zhangs;
		int N = coins.length;
		
		int dp[][] = new int[N+1][aim+1];
		dp[N][0] = 1;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				dp[index][rest] = dp[index + 1][rest];
				if(rest - coins[index] >= 0) {
					dp[index][rest] += dp[index][rest - coins[index]];
				}
				if(rest - coins[index]*(zhangs[index] + 1) >= 0) {
					dp[index][rest] -= dp[index + 1][rest - coins[index]*(zhangs[index] + 1)];
				}
			}
		}
		return dp[0][aim];
	}
}
