package class40;

public class Code03_LongestSumSubArray3 {
	/**
	 * 整数数组子数组累加和小于等于K的最大长度
		数组三连	
		
		*给定一个整数组成的无序数组arr，值可能正、可能负、可能0
		* 给定一个整数值K
		* 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
		* 返回其长度
	 */
	
	/**
	 * 
	 * 求以每一个位置开头的最小和minSum数组，以及记录每个最小和的结束位置的minSumEnd
	 * 
	 * 以某个位置开头，往右推，能够推到的最大范围，记录答案。
	 * 
	 * 假设必须从0开头的第1块，第2块。第3块，第4块，a这块，b这一块, c这一块, d这一块得到的
		整体累加和都是小于等于k的，但是一旦算上e这一块，就不再小于等于k, >K了, 那么到e之前停止
		必须以零开头的情况下，小于等于k的最长子数组是谁？-->这4块推到的范围
	 */
	
	public static int getMaxLength(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		
		int N = arr.length;
		int minSum[] = new int[N];
		int minSumEnd[] = new int [N];
		
		minSum[N - 1] = arr[N - 1];
		minSumEnd[N - 1] = N - 1;

		
		for(int i = N - 2; i >= 0; i--) {
			if(minSum[i + 1] < 0) {
				minSum[i] = minSum[i + 1] + arr[i];
				minSumEnd[i] = minSumEnd[i + 1];
			} else {
				minSum[i] = arr[i];
				minSumEnd[i] = i;
			}
		}
		
		// 以某个位置开头，往右推，能够推到的最大范围，记录答案。
		int sum = 0;
		int end = 0;
		int len = 0;
		for(int i = 0; i <= N - 1; i++) {
			while(end < N && sum + minSum[end] <= K ) {
				sum += minSum[end];
				end = minSumEnd[end] + 1; // end 指向minSum end的下一个位置
			}
			len = Math.max(len, end - i);
			if(end > i) { // i 即将++， 去计算下一个位置的答案
				// sum 要减去arr[i]位置的值。累加和计算的是从i开始的累加和，跳到下一个位置之前，得减去当前i的值
				sum -= arr[i];
			} else {
				end = i + 1; // end 在 i的后面，i即将++, end + 1 变成和下一个i同一个位置
			}
		}
		
		return len;
	}
}
