package class19;

public class Code02_ConvertNumToStr {
	
	/**
	 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
	 * 那么一个数字字符串比如"111”就可以转化为:
	 * "AAA"、"KA"和"AK"
	 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
	 */
	// 1. 憋尝试
	// 从左往右尝试模型。每一个数字都可以要或者不要，每一中情况都枚举，就是答案
	// 当前来到index位置，index之前的已经确定了，不用管，index及其之后的位置的字符都可以单转或者和下一个字符合在一起转
	// ，给我返回，有多少中转化结果
	public static int number(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		return process(str.toCharArray(), 0);
	}
	public static int process(char arr[], int index) {
		if(index == arr.length) { // 没有字符了，前面已经形成的转化就是一种方案
			return 1;
		}
		if(arr[index] == '0') { // 上一次的决策有错
			return 0;
		}
		// 当前字符单转
		int ways = process(arr, index + 1);
		// 和下一个字符和在一起转化
		// index + 1可能越界，或者和index + 1拼接在一起后，超过了26（总共只有26个字符） ==> 这两种情况，都不应该是一种转化方案
		if(index + 1 < arr.length &&
				((arr[index] - '0') * 10 + (arr[index + 1] - '0')) <= 26) {
			ways += process(arr, index + 2);
		}
		return ways;
	}
	
	// 2. 可变参数inde，范围: 0~N ==> int dp[N + 1]
	// 分析依赖，看尝试，依赖(index + 1 ==> 依赖后一个元素 ==> 从右往左初始化
	public static int number2(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int N = str.length();
		int dp[] = new int[N + 1];
		char arr[] = str.toCharArray();
		dp[N] = 1;
		
		for(int index = N - 1; index >= 0; index--) {
			if(arr[index] != '0') {
				int ways = dp[index + 1];
				if(index + 1 < N &&
						((arr[index] - '0') * 10 + (arr[index + 1] - '0')) <= 26) {
					ways += dp[index + 2];
				}
				dp[index] = ways;
			}
		}
		return dp[0];
	}
	
	
	// 为了测试
	public static String randomString(int len) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * 10) + '0');
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			String s = randomString(len);
			int ans0 = number(s);
			int ans1 = number2(s);
			if (ans0 != ans1) {
				System.out.println(s);
				System.out.println(ans0);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
