package class27;

public class Code02_IsXuanZhuan {
	/**
	 * 判断str1和str2是否互为旋转字符串
	 * 
	 * 思路：
		1. 如果str1和str2的长度不相等，那么一定不互为旋转字符串
		2. 如果长度相等，生成str1+str1的新字符串str1'，然后根据KMP，判断str2是不是
		str1'的子串，如果是，则互为旋转字符串、
	 */
	
	public static boolean isRotation(String str1, String str2) {
		if(str1 == null || str2 == null || str1.length() != str2.length()) {
			return false;
		}
		str1 += str1;
		return getIndexOf(str1, str2) == -1? false: true;
	}
	
	public static int getIndexOf(String str1, String str2) {
		if(str1 == null || str2 == null || str1.length() == 0|| str2.length() == 0 ||
				str2.length() > str1.length()) {
			return -1;
		}
		char[] str1Arr = str1.toCharArray();
		char[] str2Arr = str2.toCharArray();
		int next[] = getNextArray(str2Arr);
		
		int x = 0;
		int y = 0;
		while(x <= str1Arr.length - 1 && y <= str2Arr.length - 1) {
			if(str1Arr[x] == str2Arr[y]) {
				x++;
				y++;
			} else if(next[y] == -1) { // 或者写y==0也行
				x++;
			} else {
				y = next[y];
			}
		}
		return y == str2Arr.length ? x - y : -1;
	}
	
	public static int[] getNextArray(char str2Arr[]) {
		if(str2Arr.length == 1) {
			return new int[] {-1};
		}
		int next[] = new int[str2Arr.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while(i <= str2Arr.length - 1) {
			if(str2Arr[i - 1] == str2Arr[cn]) {
				next[i++] = ++cn;
			} else if(next[cn] == -1) {
				next[i++] = 0;
			} else {
				cn = next[cn];
			}
 		}
		return next;
	}
}
