package class17;

import java.util.ArrayList;
import java.util.List;

public class Code03_PrintAllPermutations {
	/**
	 * 打印一个字符串的全排列。
	 * 确定一个位置的字符之后，其它位置的字符，只能从剩下的字符里面选。
	 * 
	 * 递归函数:
	 * 给你剩余的没有考虑的所有字符rest，以及之前已经做过的决定path，你给我把
	 * 所有可能的结果都放到结果ans里面取。
	 */
	
	public static ArrayList<String> printAllPers(String s) {
		if(s == null || s.length() == 0) {
			return null;
		}
		ArrayList<String> ans = new ArrayList<String>();
		char[] chars = s.toCharArray();
		ArrayList<Character> rest = new ArrayList<Character>();
		for(char cur : chars) {
			rest.add(cur);
		}
		String path = "";
		process(rest, path, ans);
		return ans;
	}
	
	public static void process(ArrayList<Character> rest, String path, ArrayList<String> ans) {
		if(rest.isEmpty()) {
			ans.add(path);
			return;
		}
		int N = rest.size();
		for(int i = 0; i <= N - 1; i++) {
			char cur = rest.get(i);
//			path = path + cur; // 不能写在这里，破坏了现场，直接写在参数里面
			rest.remove(i);
			process(rest, path + cur, ans);
			rest.add(i, cur); // 当前字符选在这个位置上之后，当考虑下一个字符选在这个位置上的时候，需要恢复现场
						   // remove之后，在考虑下一个可能的字符的时候，需要add回来以前remove掉的字符。
						   // 解释：假如，字符是abc，考虑a的时候，remove a，剩下的是bc；当考虑完a了，
						   // 需要考虑b可能的情况，剩下的rest应该是ac，所以要把a加回来	
		}
	}
	
	public static List<String> permutation2(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g1(str, 0, ans);
		return ans;
	}

	/**
	 * 给你str以及当前应该考虑的字符的index，
	 * 和后面的字符交换，考虑谁可以做index位置，你给我返回所有可能的结果
	 */
	public static void g1(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			// 进行交换，index位置的值只能和它自己以及后面的字符交换
			for (int i = index; i < str.length; i++) {
				swap(str, index, i); // 当前考虑的位置index，和后面的任意一个字符交换，得到一个答案
				g1(str, index + 1, ans);
				swap(str, index, i); // 下一个支路到来前，恢复现场
			}
		}
	}

	public static List<String> permutation3(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g2(str, 0, ans);
		return ans;
	}

	public static void g2(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			boolean[] visited = new boolean[256];
			for (int i = index; i < str.length; i++) {
				if (!visited[str[i]]) {
					visited[str[i]] = true;
					swap(str, index, i);
					g2(str, index + 1, ans);
					swap(str, index, i);
				}
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}
}
