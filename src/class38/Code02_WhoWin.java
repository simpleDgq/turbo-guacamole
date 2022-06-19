package class38;

public class Code02_WhoWin {
	/**
	 * 牛羊吃N份青草谁会赢
	 * 
	 * 给定一个正整数N，表示有N份青草统一堆放在仓库里有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草不管是牛还是羊，每一轮能吃的草量必须是：
		1，4，16，64…(4的某次方)
		谁最先把草吃完，谁获胜假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定根据唯一的参数N，返回谁会赢
	 */
	

	
	public static String whoWin(int N) {
		return process(N);
	}
	
	/**
	 * 0 ~ 4, hard code
	 * 其它的递归去搞，先手选一个want，剩下的N - want，后手去搞，
	 * 
	 * 后手搞得时候，当前过程里面的先手，是后续过程里面的后手，如果后续过程后手赢了，表示的事当前过程的先手赢了。
	 * 
	 * 最终先手尝试了所有的want，都没有赢，就是后手赢
	 */
	public static String process(int N) {
		if(N < 5) {
			return (N == 0 || N == 2) ? "后手" : "先手";
		}
		
		int want = 1;
		// 先手先吃，从1到N都是一遍
		while(want <= N) {
			// 剩下的是后续过程需要吃的
			// 当前过程里面的先手，是后续过程里面的后手，如果后续过程后手赢了，表示的事当前过程的先手赢了
			if(process(N - want).equals("后手")) {
				return "先手";
			}
			
			if(want / 4 > N) {
				break;
			}
			want *= 4;
		}
		// 后手全部尝试一遍，先手都没有赢，那就是后手赢了
		return "后手";
	}
	
	// 规律是后先后先先
	public static String whoWin2(int N) {
		if(N % 5 == 0 || N % 5 == 2) {
			return "后手";
		} else {
			return "先手";
		}
	}
	
	public static void main(String args[]) {
		for(int i = 0; i <= 100; i++) {
			//System.out.println(i + ":" + whoWin(i));
			System.out.println(i + ":" + whoWin2(i));
		}
	}
}
