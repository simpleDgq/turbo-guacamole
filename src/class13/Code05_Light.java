package class13;

public class Code05_Light {
	/**
	 * 给定一个字符串str，只由'X'和'.'两种字符构成
	 * 'X'表示墙，不能放灯，也不需要点亮；'.'表示居民点，可以放灯，需要点亮
	 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
	 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
	 * 
	 * 贪心策略:
	 * 考虑第i个位置放不放灯，如果i个位置是X，不放灯，直接考虑i+1位置；
	 *                    如果i个位置是.，考虑i + 1个位置, 如果i+1是X，i位置放灯，灯数量加1，考虑 i+2位置
	 *                    				               如果i+1是., 考虑i+2位置, 如果i+2是X，i+1位置放灯，灯数量加1，考虑i+3位置
	 *                    													 如果i+2是.，i+1位置方凳，灯数量加1，考虑i+3位置
	 * i+3之前都被点亮了，考虑i+3的时候，其实i+3相当于i位置，重复上面的步骤即可。               
	 */
	
	public static int minLight(String str) {
		if(str == null || str.length() == 0) {
			return 0;
		}
		int light = 0;
		char[] chars = str.toCharArray();
		// 从0位置开始，每个位置都考虑一遍
		for(int i = 0; i <= chars.length - 1;) {
			// 如果i位置是X，直接考虑i+1位置
			if(chars[i] == 'X') {
				 i++;
			} else {// i位置是.
				light++;
				// i + 1可能越界，直接break
				if(i + 1 == chars.length) {
					break;
				}
				// 如果i+1位置是X，i位置放灯，灯数量加1，考虑i+2位置
				if(chars[i + 1] == 'X') {
					i = i + 2;
				} else {// i + 1位置是. 考虑i+2位置，其实i+2位置不管是X还是.,都只需要在i+1位置，放一盏灯，就能点亮所有的位置，直接考虑i+3位置
					i = i + 3;
				}
			}
		}
		return light;
	}
}
