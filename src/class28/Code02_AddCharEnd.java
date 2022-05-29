package class28;

public class Code02_AddCharEnd {
	/**
	 * 给定一个字符串str，只能在str的后面添加字符，想让str整体变成回文串，返回至少要添加几个字符.
	 * 
	 * 实质是求必须包含最后一个字符的情况下，最长回文子串，之前的部分不是回文串的
		部分逆序过来，就能将整体变成回文串。
		
		在manacher算法中，从左往右将R位置往右推的过程中，如果当来到x位置的时候，
		发现R位置到达了字符串的最后一个位置，那么x位置就是包含字符串结尾位置的最长回文子串的中心位置
	 */
	
	public static String addShortChar(String str) {
		if(str == null || str.length() == 0) {
			return null;
		}
		if(str.length() == 1) {
			return str;
		}
		int R = -1;
		int C = 0;
		char strArr[] = manacherStr(str.toCharArray());
		int pArr[] = new int[strArr.length];
		
		for(int i = 0; i <= strArr.length - 1; i++) {
			pArr[i] = R > i ? Math.min(pArr[2 * C - i] , R - i) : 1;
			while(i + pArr[i] <= strArr.length - 1 && i - pArr[i] > -1) {
				if(strArr[i + pArr[i]] == strArr[i - pArr[i]]) {
					pArr[i]++;
				} else {
					break;
				}
			}
			if(i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			if(R == strArr.length) { // R一直往右，到达了最后一个字符的下一个位置
				break;
			}
		}
		// 在加了特殊字符#的字符串中，C是包含字符串的最后一个字符的最长回文的中心位置
		// 则要添加的字符就是这个最长回文之前的部分的字符串逆序
		// C -( (R-1) - C) ==> 2 * C - R + 1 ==> 加了#字符的字符串中，包含字符串的最后一个字符的最长回文的第一个位置
		// 除2，就是原始字符串中的位置 ==> 也就是要求的需要逆序的字符串的长度
		char res[] = new char[(2 * C - R + 1) / 2];
		int index = res.length - 1;
		for(int i = 0; i <= res.length - 1; i++) {
			res[i] = str.charAt(index--);
		}
		return String.valueOf(res);
	}
	
	public static char[] manacherStr(char str[]) {
		char res[] = new char[str.length * 2 + 1];
		int index = 0;
		for(int i = 0; i <= res.length - 1; i++) {
			res[i] = (i % 2) == 0 ? '#' : str[index++];
		}
		return res;
	}
	
	public static void main(String[] args) {
		String str1 = "abcdde";
		System.out.println(addShortChar(str1));
	}
}
