package class38;

public class Code01_HuApple {
	/**
	 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
		1）能装下6个苹果的袋子
		2）能装下8个苹果的袋子
		小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
		给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
	 */
	
	/**
	 * 
	 * 思路：
	 * 100个苹果需要多少个袋子装？
	 * 100 / 8 = 12
	 * 
	 * 8号袋子从12个开始，一直往下尝试，
	 * 12个8号装 96个，  还剩4个，6号袋子装不了
	 * 11个8号装 88个，  还剩12个，2个6号袋子装的下，返回
	 * ....
	 * 依次往下，知道8号袋子用0个
	 */
	
	public static int buyApple(int N) {
		if(N <= 0) {
			return -1;
		}
		int bug8 = N >> 3;
		int rest = N - (bug8 << 3);
		while(bug8 >= 0) {
			if(rest % 6 == 0) {
				return bug8 + rest / 6;
			} else {
				bug8--;
				rest += 8;
			}
		}
		return -1;
	}
	
	
	public static int buyApple2(int N) {
		// 1 - 17不知道什么规律，直接hard code
		if(N < 18) {
			if(N == 6 || N == 8) {
				return 1;
			} else if(N == 12 || N == 14 || N == 16){
				return 2;
			} else {
				return -1;
			}
		}
		// 18往后，奇数是-1，偶数就是((N -18) / 8) + 3
		return N % 2 == 0 ? ((N -18) >> 3) + 3 : -1;
	}
	
	public static void main(String args[]) {
		for(int i = 0; i <= 200; i++) {
//			System.out.println(buyApple(i));
			System.out.println(buyApple2(i));
		}
	}

}
