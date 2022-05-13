package class19;

import java.util.HashMap;

public class Code03_StickersToSpellWord {
	/**
	 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
	 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
	 * 返回需要至少多少张贴纸可以完成这个任务
	 * 例子：str= "babac"，arr = {"ba","c","abcd"}
	 * ba + ba + c  3  abcd + abcd 2  abcd+ba 2
	 * 所以返回2
	 */
	// 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
	public static int minStickers1(String[] tickers, String target) {
		if(tickers == null || tickers.length == 0 || target == null || target.length() == 0) {
			return -1;
		}
		int ans = process(tickers, target);
		return  ans == Integer.MAX_VALUE ? -1 : ans;
	}
	 // 给你tickers和目标target，你给我返回至少需要几张贴纸完成任务
	 public static int process(String[] tickers, String target) {
		 if(target.length() == 0) { // 如果target没有了，就不需要新的贴纸了，直接返回0
			 return 0;
		 }
		 // 每张贴纸都作为第一张，看看能减少多少target
		 int min = Integer.MAX_VALUE;
		 for(String first : tickers) {
			 String rest = minu(first, target); // 当前贴纸作为第一张，能够减掉多少字符，剩下的字符继续用贴纸减
			 if(rest.length() != target.length()) { // 如果当前first不能减掉target中的任何字符，不需要进入下个分支
				 min = Math.min(process(tickers, rest), min); // 剩下的字符rest，用贴纸去减。 每张贴纸作为第一张的方案都求最小值。
			 }
		 }
		 return min + (min == Integer.MAX_VALUE ? 0 : 1); // 如果所有的贴纸都不能减完target中的字符，返回MAX_VALUE。否则加上第一张贴纸1。
	 }
	 
	 public static String minu(String first, String target) {
		 char[] str1 = first.toCharArray();
		 char[] str2 = target.toCharArray();
		 int[] counts = new int[26];
		 for(char cur : str2) { // 统计target的各个字符出现的次数
			 counts[cur - 'a']++;
		 }
		 for(char cur: str1) { // 用first中的字符去减个数，减完之后剩下的就是rest字符
			 counts[cur - 'a']--;
		 }
		 StringBuilder rest = new StringBuilder();
		 for(int i = 0; i < 26; i++) {
			 if(counts[i] > 0) {
				 for(int j = 0; j < counts[i]; j++) {
					 rest.append((char)(j + 'a'));
				 }
			 }
		 }
		 return rest.toString();
	 }
	 
	 // 优化的写法
	 // 1. 用二维数组表示所有的贴纸 ==> 关键优化(用词频表替代贴纸数组)
	 // 2. 最关键的优化(重要的剪枝!这一步也是贪心!)  ==> 选贴纸中含有target第一个字符的贴纸去试
	 // 给你tickers和目标target，你给我返回至少需要几张贴纸完成任务
	 public static int minStickers2(String[] tickers, String target) {
			if(tickers == null || tickers.length == 0 || target == null || target.length() == 0) {
				return -1;
			}
			int N = tickers.length;
			int[][] arr = new int[N][26];
			for(int i = 0; i < N; i++) { // 用二维数组表示所有的贴纸 ==> 关键优化(用词频表替代贴纸数组)
				for(char cur : tickers[i].toCharArray()) {
					arr[i][cur - 'a']++;
				}
			}
			int ans = process2(arr, target);
			return  ans == Integer.MAX_VALUE ? -1 : ans;
	 }
	 public static int process2(int[][] tickers, String t) {
		 if(t.length() == 0) { // 如果target没有了，就不需要新的贴纸了，直接返回0
			 return 0;
		 }
		 char[] target = t.toCharArray();
		 int[] tcounts = new int[26];
		 for(char cur : target) { // 统计target的各个字符出现的次数
			 tcounts[cur - 'a']++;
		 }
		 // 每张贴纸都作为第一张，看看能减少多少target
		 int min = Integer.MAX_VALUE;
		 for(int[] first : tickers) {
			 if(first[target[0] - 'a'] > 0) { //选贴纸中含有target第一个字符的贴纸去试。最关键的优化(重要的剪枝!这一步也是贪心!) 
				 StringBuilder restBuilder = new StringBuilder();
				 for(int i = 0; i < 26; i++) {
					 if(tcounts[i] > 0) {
						 int nums = tcounts[i] - first[i]; // 用first中的字符去减，减完之后剩下的就是rest字符
						 for(int j = 0; j < nums; j++) {
							 restBuilder.append((char)(i + 'a'));
						 }
					 }
				 }
				 String rest = restBuilder.toString();
				 min = Math.min(process2(tickers, rest), min); // 剩下的字符rest，用贴纸去减。 每张贴纸作为第一张的方案都求最小值。
			 }
		 }
		 return min + (min == Integer.MAX_VALUE ? 0 : 1); // 如果所有的贴纸都不能减完target中的字符，返回MAX_VALUE。否则加上第一张贴纸1。
	 }
	 
	 
	 // 分析2方法，改动态规划
	 // 可变参数是String t, 没有办法确定范围，==> 直接加傻缓存
	 public static int minStickers3(String[] tickers, String target) {
			if(tickers == null || tickers.length == 0 || target == null || target.length() == 0) {
				return -1;
			}
			int N = tickers.length;
			int[][] arr = new int[N][26];
			for(int i = 0; i < N; i++) { // 用二维数组表示所有的贴纸 ==> 关键优化(用词频表替代贴纸数组)
				for(char cur : tickers[i].toCharArray()) {
					arr[i][cur - 'a']++;
				}
			}
			HashMap<String, Integer> dp = new HashMap<String, Integer>();
			int ans = process3(arr, target, dp);
			return  ans == Integer.MAX_VALUE ? -1 : ans;
	 }
	 public static int process3(int[][] tickers, String t, HashMap<String, Integer> dp) {
		 if(dp.containsKey(t)) {
			 return dp.get(t);
		 }
		 int ans = 0;
		 if(t.length() != 0) { // 如果target没有了，就不需要新的贴纸了，直接返回0
			 char[] target = t.toCharArray();
			 int[] tcounts = new int[26];
			 for(char cur : target) { // 统计target的各个字符出现的次数
				 tcounts[cur - 'a']++;
			 }
			 // 每张贴纸都作为第一张，看看能减少多少target
			 int min = Integer.MAX_VALUE;
			 for(int[] first : tickers) {
				 if(first[target[0] - 'a'] > 0) { //选贴纸中含有target第一个字符的贴纸去试。最关键的优化(重要的剪枝!这一步也是贪心!) 
					 StringBuilder restBuilder = new StringBuilder();
					 for(int i = 0; i < 26; i++) {
						 if(tcounts[i] > 0) {
							 int nums = tcounts[i] - first[i]; // 用first中的字符去减，减完之后剩下的就是rest字符
							 for(int j = 0; j < nums; j++) {
								 restBuilder.append((char)(i + 'a'));
							 }
						 }
					 }
					 String rest = restBuilder.toString();
					 min = Math.min(process3(tickers, rest, dp), min); // 剩下的字符rest，用贴纸去减。 每张贴纸作为第一张的方案都求最小值。
				 }
			 }
			 ans =  min + (min == Integer.MAX_VALUE ? 0 : 1); // 如果所有的贴纸都不能减完target中的字符，返回MAX_VALUE。否则加上第一张贴纸1。
		 }
		 dp.put(t, ans);
		 return ans;
	 }
}
