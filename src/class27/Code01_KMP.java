package class27;

public class Code01_KMP {
	/**
	 * 字符串匹配(算法)
	 * 判断一个字符串中是否有某个子字符串，返回子串的开始位置
	 * 
	 * 匹配过程：
	 * x和y分别指向str1和str2的字符。
	 * 1. 如果当前字符匹配，则x++,y++
	 * 2.如果不匹配，y往左跳，y=next[y] ==> 等价于，将x往前推y记录的信息长度，到达j，然后将j和str2的0位置对齐，在将str2
	 * 和x对齐的字符（这个字符的位置其实就是next[y]）进行比较。
	 * 3.如果y到达了0位置，说明跳完了str2，都找不到一个和x位置相等的字符，x++，换一个字符  
	 * ===> 如果y一直++，等于了str2的length，说明匹配上了，return x-y，就是答案。
	 * 如果x一直++, 越界了，说明str1没有字符开头能够搞定str2，直接返回-1.
	 * 
	 * 求next数组：记录的是下一个要和i-1进行比较的位置。
	 * 规定
	 * next[0] = -1;
	 * next[1] = 0;
	 * cn = 0; // 当前和i-1位置进行比较的字符的位置，初始值是0，因为刚开始要求的是i=2的值，要将0位置和1位置进行比较
	 *           所有cn初始值是0
	 * 求i位置的next值：
	 * 1. 如果i - 1位置的最长前缀的后一个字符等于i-1位置，那么i位置的next值就是cn + 1
	 * 2. 如果i - 1位置的最长前缀的后一个字符不等于i-1位置，则cn往左跳，跳到next[cn], 也就是当前cn位置
	 * 的最长前缀的下一个字符位置
	 * 3.cn蹦到了0，没位置蹦了，还没有找到，i位置的next值直接设置为0
	 */
	
	public static int getIndexOf(String str1, String str2) {
		if(str1 == null || str2 == null || str1.length() == 0|| str2.length() == 0 ||
				str2.length() > str1.length()) {
			return -1;
		}
		char[] str1Arr = str1.toCharArray();
		char[] str2Arr = str2.toCharArray();
		int next[] = getNextArray(str2Arr);
		// 从0位置开始，进行匹配
		int x = 0;
		int y = 0;
		while(x <= str1Arr.length - 1 && y <= str2Arr.length - 1) {
			if(str1Arr[x] == str2Arr[y]) { // 匹配上了
				x++;
				y++;
			} else if(next[y] == -1) { // 跳到了y==0，说明str2，没有字符能匹配x位置。 这里写y==0也可以，next[y] 只有y==0的时候是-1.等价
				x++;
			} else { // 没有匹配上，往左跳
				y = next[y];
			}
		}
		// x或者y越界
		// y == str2Arr.length 说明y越界，否则就是x越界
		return y == str2Arr.length ? x - y : -1;
	}
	
	// 求next数组
	public static int[] getNextArray(char[] str2Arr) {
		if(str2Arr.length == 1) {
			return new int[] {-1};
		}
		int next[] = new int[str2Arr.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2; // 要求next值的位置i. 刚开始是从2开始
		int cn = 0; // 要和 i-1位置进行比较的字符的位置
		// 每一个位置求next值
		while(i <= str2Arr.length - 1) {
			if(str2Arr[i - 1] == str2Arr[cn]) { // i - 1位置和cn位置的字符相等，则next[i]=cn+1 。 （刚开始1位置和0位置的字符比较，相等，则2位置的next是1）
				next[i++] = ++cn; // 下一个要求i+1位置，cn就是i位置的最长前缀的下一个位置，就是cn+1 （下一步要求3位置的值，2位置的最长前缀的下一位就是1）
			} else if(next[cn] == -1) {// cn 跳到了0位置，还没有匹配
				next[i++] = 0;
			} else { // 不匹配，cn往左跳
				cn = next[cn];
			}
		}
		return next;
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
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

	
	
}
