package class40;

import java.util.HashMap;

public class Code02_LongestSumSubArrayLength {

	/**
	 * 整数数组子数组累加和等于K的最大长度
		（预处理数组）
		 数组二连

		给定一个整数组成的无序数组arr，值可能正、可能负、可能0
	给定一个整数值K
	找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
	返回其长度
	 */
	
	/**
	 * 思路
	 * 
		* 看到子数组，把求解流程定为什么？
		* 看到子数组问题, 必须以每个位置开头怎么怎么样；
		* 必须以每个位置结尾必须怎么怎么样去考虑
		* 
		* 求以0位置结尾的数组，等于K的子数组，最长长度
		* 求以1位置结尾的数组，等于K的子数组，最长长度
		* 求以2位置结尾的数组，等于K的子数组，最长长度
		* 求以3位置结尾的数组，等于K的子数组，最长长度
		* ...
		* 
		* 
		* 例子: 求以100结尾的子数组，累加和是30，最长长度
		* 求出100结尾的全部数组的累加和是200，其实也就是在
		* 找，前缀和数组中，最早出现170的位置是哪。
		* 
	 */
	public static int getMaxLength(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		
		// key: 前缀和 value: 前缀和最早出现的位置
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, -1); // 防止丢失以0位置开头的答案
		
		int len = 0;
		int sum = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			sum += arr[i];
			if (map.containsKey(sum - K)) { // 如果能够找到sum - K最早出现的位置，记录答案
				len = Math.max(i - map.get(sum - K), len);
			}
			if (!map.containsKey(sum)) { // 如果前缀和不在map中，加入进去
				map.put(sum, i);
			}
		}
		return len;
	}
	
	
	public static void main(String[] args) {
	
		int nums[] = {1,2,1,2,1};
		getMaxLength(nums, 3);
	
	}
}
