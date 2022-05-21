package class24;

import java.util.LinkedList;

public class Code02_AllLessBNumSubArray {
	/**
	 * 给定一个整型数组arr，和一个整数num
	某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
	返回arr中达标子数组的数量
	 */
	/**
	 * 搞两个队列，分别是窗口最大值更新结构和窗口最小值更新结构，
	 * 1.L从0到N, 依次收集以0作为开始下标的、达标的子数组数量，以1作为开始下标的。。。
	 * 2.R从0开始一直往右，遍历到的当前元素，和最大值、最小值更新结构的尾部元素进行比较，大于等于、小于等于，一直弹出，
	 * 然后当前元素的下标分别加入最大、最小值更新结构
	 * 3. 判断当前元素是否达标，如果达标，R继续往右，如果不达标，则break。
	 *     将R-L累加进结果，L++,开始求以L+1打头的子数组，达标的有多少个
	 * 4. L要加1了，说明马上L这个位置要过期了，在L+1前，需要从最大值和最小值更新结构中弹出L (队头元素等于L，表示过期)
	 */
	public static int lessNumSubArray(int arr[], int num) {
		if(arr == null || arr.length == 0 || num < 0) {
			return 0;
		}
		int N = arr.length;
		int R = 0;
		int ans = 0;
		LinkedList<Integer> maxWindow = new LinkedList<>();
		LinkedList<Integer> minWindow = new LinkedList<>();
		for(int L = 0; L <= N - 1; L++) { // L从0到N-1, 依次收集
			while(R <= N - 1) { // R一直往右，直到到达不达标的第一个元素
				int cur = arr[R];
				while(!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= cur) {
					maxWindow.pollLast();
				}
				maxWindow.addLast(R);
				while(!minWindow.isEmpty() && arr[minWindow.peekLast()] >= cur) {
					minWindow.pollLast();
				}
				minWindow.add(R);
				if(arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > num ) { // 不满足条件，直接break
					break;
				}
				R++;
			}
			//这句必须写在外面，不能写在break之前，因为可能整个数组的元素都是达标的，需要收集整个数组的长度，如果写
			// 在break前，则会丢失这种情况下的答案
			ans += R - L; //  收集答案. 
			// L 即将过期，从最大值和最小值更新结构弹出过期下标
			if(L == maxWindow.peekFirst()) { 
				maxWindow.pollFirst();
			}
			if(L == minWindow.peekFirst()) {
				minWindow.pollFirst();
			}
		}
		return ans;
	}
}
