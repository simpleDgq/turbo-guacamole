package class17;

import java.util.ArrayList;
import java.util.List;

public class Code02_PrintAllSubsquencs {
	/**
	 * 打印一个字符串的全部子序列。
	 * 每一个字符都可以决定要和不要，每个决定都会产生一个新的字符串结果，
	 * 设计一个递归函数，给定下一个即将要考虑的index的位置，以及已经考虑过的字符生成的path，
	 * 返回给我所有可能得答案。
	 * 
	 * 如果要去重，可以直接用HashSet存储结果，自动去重。
	 * 
	 */
	public static ArrayList<String> printAllSubs(String s) {
		if(s == null || s.length() == 0) {
			return null;
		}
		char[] chars = s.toCharArray();
		ArrayList<String> ans = new ArrayList<String>();
		String path = "";
		process(chars, 0, ans, path);
		return ans;
	}
	
	public static void process(char[] chars, int index, ArrayList<String> ans, String path) {
		if(index == chars.length) {
			ans.add(path);
			return;
		}
		//考虑下一个字符，如果要当前这个字符，产生什么结果，放到ans中
		process(chars, index + 1, ans, path + String.valueOf(chars[index]));
		//考虑下一个字符，如果要当前这个字符，产生什么结果，放到ans中
		process(chars, index + 1, ans, path);
	}
	
	public static void main(String[] args) {
		String test = "acccc";
		List<String> ans1 = printAllSubs(test);

		for (String str : ans1) {
			System.out.println(str);
		}
	}
}
