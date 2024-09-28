package class28;

public class Code01_Manacher {
	/**
	 * Manacher算法 求一个字符串中的最长回文子串长度
	 */
	/**
	 * 1. 回文直径与回文半径：整个回文的长度就是回文直径，
	 * 如果整个长度是奇数，那么回文半径就是从中间位置开始，算上中间位置，一直到最右边的长度（回文直径 / 2） + 1;
	 * 如果是偶数，则是直径/2。在加了特殊字符之后，长度就都是奇数，不存在偶数情况
	 * 
	 * 2. 回文半径数组：
	 * str经过特殊处理变成str'，从左往右，每一个位置的字符，求回文半径存在数组里面
	 * 
	 * 3. 最右回文边界R：记录当前所有找到的回文能够到达的最右边界。
	 * 
	 * 4. 最右回文边界的中心C，记录的是与R对应的回文串的中心点位置。
	 * 
	 * 4种情况
	 * 1) i在R外，暴力扩
	 * 2) i在R内，三种情况，
	 *  1. 如果i'扩出来的回文区域在L~R内，i位置的结果直接获取，取的是i'位置的值（回文半径），O(1)  -> pArr[i] = pArr[2 * C - i]
	 *  2.如果i'扩出来的回文区域的左边界在L外，i位置的结果也是直接获取，取的是i~R（i位置回文半径就是i~R），O(1) -> pArr[i] = R - i
	 *  3. 如果i'刚好在L上，i位置的回文区域至少是i~R为半径的区域，需要去判断R往右扩的位置是不是回文
	 * 
	 */
	// 返回的是最长回文半径 ==> 最长回文子串长度 = 最长回文半径 * 2 - 1
	public static int manacher(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		if (str.length() == 1) {
			return 1;
		}
		// 加特殊字符#
		char strArr[] = manacherStr(str.toCharArray());
		// 处理每一个字符
		int R = -1; // 最右回文边界的下一个位置
		int C = 0; // 最右回文的中心位置
		int pArr[] = new int[strArr.length]; // 存储每个位置的回文半径的数组
		int max = Integer.MIN_VALUE;
		// 每一个i位置都去求回文半径
		for (int i = 0; i <= strArr.length - 1; i++) {
			// 求i位置最小不用验的回文半径
			// R > i说明包住了, 情况2.1和2.2 两者取最小，至少有一个回文半径长度； 如果没被包住，自己至少是一个回文，半径就是1(情况1)
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			// 当前形成的回文区域左、右都不越界，那么尝试一下当前回文的左、右两个字符是否相等
			// 如果相等，说明能够继续扩，更新i位置的回文半径  --> 这里就是情况2.3
			while (i + pArr[i] <= strArr.length - 1 && i - pArr[i] >= 0) { // 不越界
				if (strArr[i + pArr[i]] == strArr[i - pArr[i]]) { // 回文能够继续扩，回文半径++
					pArr[i]++;
				} else { // 不能扩
					break;
				}
			}
			// 当前以i为中心，冲到的回文边界，是否能够推高R，如果能则更新R，以及新的回文中心
			if (i + pArr[i] > R) { // 能够将最右回文边界推大
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
		for (int i = 0; i <= res.length - 1; i++) {
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
