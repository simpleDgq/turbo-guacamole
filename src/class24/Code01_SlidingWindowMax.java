package class24;

import java.util.LinkedList;

public class Code01_SlidingWindowMax {
	/**
	 * 窗口内最大值或最小值更新结构的实现
		假设一个固定大小为W的窗口，依次划过arr，
		返回每一次滑出状况的最大值
		例如，arr = [4,3,5,4,3,3,6,7], W = 3
		返回：[5,5,5,4,6,7]
		（微软98）
	 */
	/**
	 * 1.搞一个双端队列（存放的是下标），从左往右遍历数组中所有的数（R一直++)，如果当前位置的数比双端队列尾部的下标
	 * 指向的数大，则弹出尾部的下标，将当前元素的下标加进去
	 * 2. 加入一个元素之后，判断队列头元素是否过期，如果过期，需要弹出。因为窗口大小是w，最多只能存储w个元素，如果加入当前
	 * 元素之后，窗口大小超过了w，就需要弹出最左边的元素。（R=0，w=3，则只能存-2~0三个元素，-3就是过期的下标，应该弹出，
	 * 也就是队列头元素 如果 等于R-W，则弹出）
	 * 3. 如果窗口增长到了w这个长度，就该收集最大值，最大值就是队列头元素下标指向的值。R >= w - 1的时候，就成长到收集的时候了。
	 */
	
	public static int[] windowMax(int arr[], int w) {
		if(arr == null || arr.length == 0 || w <= 0) {
			return null;
		}
		int N = arr.length;
		int ans[] = new int[N - w + 1]; // 要收集的答案个数是N-W+1，举例子推
		LinkedList<Integer> win = new LinkedList<Integer>();
		int index = 0;
		for(int R = 0; R <= N - 1; R++) {
			int cur = arr[R];
			while(!win.isEmpty() && arr[win.peekLast()] <= cur) { // 如果尾部小，一直弹出
				win.pollLast();
			}
			win.addLast(R);
			// 判断下标是否过期，过期，则弹出队头
			if(win.peekFirst() == R - w) {
				win.pollFirst();
			}
			// 是否形成有效窗口，形成则收集答案
			if(R >= w - 1) {
				ans[index++] = arr[win.peekFirst()];
			}
		}
		return ans;
	}
}
