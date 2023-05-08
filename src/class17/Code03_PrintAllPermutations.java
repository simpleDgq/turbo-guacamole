package class17;

import java.util.ArrayList;
import java.util.List;

public class Code03_PrintAllPermutations {
	/**
	 * 打印一个字符串的全排列。
	 * 
	 * 确定一个位置的字符之后，其它位置的字符，只能从剩下的字符里面选。
	 * 例如: abcd这个字符串的全排列
	 * 0位置的字符，4个里面选一个，1位置的字符，剩下的3个字符里面选一个（因为0已经确定了）
	 * 2位置的字符，剩下的2个字符里面选一个（4,3已经确定了），1位置的字符，没得选，就是最后
	 * 剩下的字符	
	 * 
	 * 递归函数:
	 * 解法1:
	 * 给你剩余的没有考虑的所有字符rest，以及之前已经做过的决定path，你给我把
	 * 所有可能的结果都放到结果ans里面取。
	 * 
	 * 解法2:
	 * 给你str以及当前应该考虑的字符的index，
	 * 和后面的字符交换，考虑谁可以做index位置，你给我返回所有可能的结果
	 * 
	 * 解法3:更加优化解法2(掌握)   --> 也是一个变形题: 打印一个字符串的全部排列，要求不要出现重复的排列
	 * 在解法2的基础上进行了进一步的剪枝
	 * 如果当前考虑的字符，要和index位置交换的字符，和以前考虑过的字符相等，则
	 * 不需要再考虑，因为即使和index进行交换，得到的结果也是一样。
	 * 想象只有index位置进行交换，其它位置的字符不变，那么当前的字符如果和已经考虑过的字符是相等的，
	 * 那结果还是一样，就不需要考虑。
	 * 
	 * 搞一个bool数组，长度为256，假设最多只有256种字符，考虑过的字符记录为true
	 * 如果没有考虑过，才继续将当前字符和index位置的字符进行交换。
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
	//给你剩余的没有考虑的所有字符rest，以及之前已经做过的决定path，你给我把
	//所有可能的结果都放到结果ans里面取。
	public static void process(ArrayList<Character> rest, String path, ArrayList<String> ans) {
		// 当前没有字符可以使用了，path里面就是答案
		if(rest.isEmpty()) {
			ans.add(path);
			return;
		}
		int N = rest.size();
		// 依次考虑剩下可用的每一个字符
		for(int i = 0; i <= N - 1; i++) {
			char cur = rest.get(i);
//			path = path + cur; // 不能写在这里，破坏了现场，path被改变了，直接写在参数里面
			rest.remove(i);
			// 不存在要不要的问题，每个字符都必须被使用，因为是全排列，所以直接是path + cur
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
	 * 解法二(优化参数)
	 * 给你str以及当前应该考虑的字符的index，
	 * 和后面的字符交换，考虑谁可以做index位置，你给我返回所有可能的结果
	 */
	public static void g1(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			// 进行交换，index位置的值只能和它自己以及后面的字符交换
			for (int i = index; i < str.length; i++) {
				// 当前考虑的位置index，和后面的任意一个字符交换，得到一个答案
				swap(str, index, i);
				// 继续从index + 1 位置开始
				g1(str, index + 1, ans);
				// 下一个支路到来前，恢复现场
				swap(str, index, i); 
			}
		}
	}

	/**
	 * 解法三
	 * 如果字符串中有相等的字符，那么即使交换之后，得到的字符串也是和之前考虑过的字符串相等的，
	 * 就不需要继续递归下去。
	 * 
	 * 可以搞一个bool数组，记录当前考虑的字符，是不是和以前想等的，如果是相等的，
	 * 就不在考虑
	 */
	public static List<String> permutation3(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g2(str, 0, ans);
		return ans;
	}

	// 可以搞一个bool数组，记录当前考虑的字符，是不是和以前想等的，如果是相等的，
	// 就不在考虑
	public static void g2(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			boolean[] visited = new boolean[256];
			for (int i = index; i < str.length; i++) {
				// 已经考虑过相同的字符，则不再考虑
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
