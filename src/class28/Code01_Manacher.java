package class28;

public class Code01_Manacher {
	/**
	 * Manacher算法
	 * 求一个字符串中的最长回文子串长度
	 */
	// 返回的是最长回文半径 ==> 最长回文子串长度 = 最长回文半径 * 2 - 1
	public static int manacher(String str) {
		if(str == null || str.length() == 0) {
			return 0;
		}
		if(str.length() == 1) {
			return 1;
		}
		// 加特殊字符#
		char strArr[] = manacherStr(str.toCharArray());
		//处理每一个字符
		int R = -1; // 左右回文边界的下一个位置
		int C = 0; // 最右回文的中心位置
		int pArr[] = new int[strArr.length]; // 存储每个位置的回文半径的数组
		int max = Integer.MIN_VALUE;
		for(int i = 0; i <= strArr.length - 1; i++) {
			// 求i位置最小不用验的回文半径
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while(i + pArr[i] <= strArr.length - 1 && i - pArr[i] > -1) { // 不越界
				if(strArr[i + pArr[i]] == strArr[i - pArr[i]]) { // 回文能够继续扩，回文半径++
					 pArr[i]++;
				} else { // 不能扩
					break;
				}
			}
			if(i + pArr[i] > R) { // 能够将最右回文边界推大
				R = i + pArr[i];
				C = i;
			}
			// 求最大回文半径
			max = Math.max(max, pArr[i]);
		}
		return max - 1; // 加了特殊字符#的回文，对应到原始字符串，回文半径-1，举例观察得到
	}
	
	public static char[] manacherStr(char str[]) {
		char res[] = new char[str.length * 2 + 1];
		int index = 0;
		for(int i = 0; i <= res.length - 1; i++) {
			res[i] = (i % 2) == 0 ? '#' : str[index++];
		}
		return res;
	}
	
	
	// for test
	public static int right(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		
		char[] str = manacherStr(s.toCharArray());
		int max = 0;
		for (int i = 0; i < str.length; i++) { 
			// 暴力方法，每一个位置分别往左和往右扩
			int L = i - 1;
			int R = i + 1;
			while (L >= 0 && R < str.length && str[L] == str[R]) { // 相等，继续扩
				L--;
				R++;
			}
			max = Math.max(max, R - L - 1); // 计算能够扩到的最长回文长度
		}
		return max / 2; // 除2得到回文半径
	}

		// for test
		public static String getRandomString(int possibilities, int size) {
			char[] ans = new char[(int) (Math.random() * size) + 1];
			for (int i = 0; i < ans.length; i++) {
				ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
			}
			return String.valueOf(ans);
		}

		public static void main(String[] args) {
			int possibilities = 5;
			int strSize = 20;
			int testTimes = 5000000;
			System.out.println("test begin");
			for (int i = 0; i < testTimes; i++) {
				String str = getRandomString(possibilities, strSize);
				if (manacher(str) != right(str)) {
					System.out.println("Oops!");
				}
			}
			System.out.println("test finish");
		}

}
