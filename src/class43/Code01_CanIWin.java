package class43;

public class Code01_CanIWin {
	/**
	 * 给定一个num和rest，1~num的数都有，先手拿一个数，rest减去这个数
	 * 最后使后手面对0或者负数，先手赢。
	 */
	
	/*
	 * 暴力解
	 */
	/*
	 * 1~choose 都有
	 * total开始的剩余
	 * 给我返回先手会不会赢
	 */
	public static boolean canIWin(int choose, int total) {
		if(total == 0) { // 题目规定
			return true;
		}
		if(choose * (choose + 1) >> 1 < total) { // 所有数总和小于total
			return false;
		}
		
		int arr[] = new int[choose];
		for(int i = 0; i <= choose - 1; i++) {
			arr[i] = i + 1;
		}
		
		return process(arr, total);
	}
	
	/**
	 * 当前轮到先手拿，先手只能拿arr中剩余的数字，还剩rest这么多值，
	 * 返回先手会不会赢
	 */
	public static boolean process(int arr[], int rest) {
		if(rest <= 0) { // 先手上来就是负数，说明先手输了
			return false;
		}
		
		// 先手每一个数都去尝试一遍
		for(int i = 0; i <= arr.length - 1; i++) {
			int cur = arr[i];
			if(cur != -1) {
				arr[i] = -1;
				boolean next = process(arr, rest - cur);
				arr[i] = cur;
				if(!next) { // 后手输了，就是先手赢了
					return true;
				}
			}
		}
		return false; // 尝试过所有的情况，先手都没有赢，返回false
	}
	
	
	/**位信息**/
	//两个可变参数, arr 可变参数的复杂程度突破了整型
	// arr这个可变参数，它是一个线性结构。这个线性结构，它其实代表一个集合某个数字存在或不存在的这么一种
	//状况
	
	//查看题目数字范围: choose 不会大于 20， rest不会大于 300。范围非常的窄。
	// 用位信息表示数字存在不存在 ==> 一个整型32位就可以表示choose存在不存在
	
	// 这个是暴力尝试，思路是正确的，超时而已
	public static boolean canIWin1(int choose, int total) {
		if (total == 0) {
			return true;
		}
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		return process1(choose, 0, total);
	}
	/**
	 * status 表示choose存不存在
	 * 为0表示存在，为1表示被拿过了
	 */
	public static boolean process1(int choose, int status, int rest) {
		if(rest <= 0) { // 先手上来就是负数，说明先手输了
			return false;
		}
		
		// 先手每一个数都去尝试一遍
		for(int i = 1; i <= choose; i++) {
			if(((1 << i) & status) == 0) { // status 第i 位 是0 ，才去尝试
				boolean next = process1(choose, (1 << i) | status, rest - i);
				if(!next) { // 后手输了，就是先手赢了
					return true;
				}
			}
		}
		return false; // 尝试过所有的情况，先手都没有赢，返回false
	}
	
	/**
	 * 动态规划
	 */
	// 你拿着数字的状态，可以决定rest，它俩不独立
	// 于是一维动态规划表就够了。
	
	public static boolean canIwin1(int choose, int total) {
		if (total == 0) {
			return true;
		}
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		
		// dp[status] == 1  true
		// dp[status] == -1  false
		// dp[status] == 0  process(status) 没算过！去算！
		int dp[] = new int[1 << (choose + 1)];
		
		return process2(choose, 0, total, dp);
	}
	
	public static boolean process2(int choose, int status, int rest, int dp[]) {
		if(dp[status] != 0) { // 先手上来就是负数，说明先手输了
			return dp[status] == 1 ? true : false;
		}
		
		// 先手每一个数都去尝试一遍
		for(int i = 1; i <= choose; i++) {
			if(rest > 0) {
				if(((1 << i) & status) == 0) { // status 第i 位 是0 ，才去尝试
					boolean next = process2(choose, (1 << i) | status, rest - i, dp);
					dp[status] = next ? -1 : 1; // 后手输了，就是先手赢了
					if(!next) { 
						return true;
					}
				}
			}
			
		}
		return false; // 尝试过所有的情况，先手都没有赢，返回false
	}
	
}
