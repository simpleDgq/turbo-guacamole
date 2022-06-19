package class38;

public class Code03_MSumToN {
	/**
	 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
	比如:
	5 = 2+3，5就是这样的数
	12 = 3+4+5，12就是这样的数
	1不是这样的数，因为要求数量大于1个、连续正数和
	2 = 1 + 1，2也不是，因为等号右边不是连续正数
	给定一个参数N，返回是不是可以表示成若干连续正数和的数
	 */
	
	/**
	 * 以1开头，去尝试，看连续累加能不能得到N
	 *  以2开头，去尝试，看连续累加能不能得到N
	 *   以3开头，去尝试，看连续累加能不能得到N
	 *    以4开头，去尝试，看连续累加能不能得到N
	 *     以5开头，去尝试，看连续累加能不能得到N
	 *     ...
	 *     直到找到一种解法
	 */
	public static boolean MSumToN(int N) {
		if(N <= 0) {
			return false;
		}
		int sum = 0;
		for(int i = 1; i <= N; i++) {
			sum = i;
			for(int j = i + 1; j <= N; j++ ) {
				if(sum > N) {
					break;
				}
				if(sum == N) {
					return true;
				}
				sum += j;
			}
		}
		return false;
	}
	
	// 发现规律，只有是2的某次方的时候，才是false
	// 二进制只有一位1的数，都是2的某次方
	public static boolean MSumToN2(int N) {
		return N == (N & (~N + 1)) ? false : true;
	}
	
	public static void main(String args[]) {
		for(int i = 1; i <= 100; i++) {
//			System.out.println(i + ":" + MSumToN(i));
			System.out.println(i + ":" + MSumToN2(i));
		}
	}

}
