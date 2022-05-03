package class13;

import java.util.Arrays;
import java.util.Comparator;

public class Code01_LowestString {
	/**
	 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中字典序最小的结果。
	 * 
	 * 思路: 贪心算法
	 * 贪心策略: 如果字符串数组中的两个字符串a和b连接之后，a + b 小于 b + a， 则a放在前面，b放在后面。
	 * 按照上面的策略将数组中的字符串，进行排序。
	 */
	public static String lowestStr(String strs[]) {
		if(strs == null || strs.length == 0) {
			return "";
		}
		Arrays.sort(strs, new StrComparator()); // 将strs中的元素，进行排序
		String ans = "";
		for(String str : strs) {
			ans += str;
		}
		return ans;
	}
	public static class StrComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return (o1 + o2).compareTo(o2 + o1); // compareTo 比较两个字符串，返回int
		}
	}

}
