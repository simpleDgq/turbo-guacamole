package class02;

public class Code02_KMNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr2[] = {1, 3, 3, 3, 1, 1, 6,6 , 5, 5, 5};
		KMNum(arr2, 2, 3);
	}
	/**
	 * 推广:一个数组中，有一种数出现了K次，其它数都出现了M次，M> 1, K < M, 
	 * 找到出现了K次的数要求：额外空间复杂度O(1)，时间复杂度O(N).
	 * 
	 * 思路:
	 * 1. 一个整数可以用32位表示，定义一个t[32]
	 * 2. 遍历数组中的每一个数，对当前数的每一位进行处理，如果是1，就加到对应的t[i]中
	 * 3. 如果t[i]不是M的整数倍，说明出现K次的数的第i位一定是1，使用或运算，将该位并入到最终的结果中。
	 * 
	 * @param arr
	 */
	public static void KMNum(int arr[], int K, int M) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int t[] = new int[32];
		int ans = 0;
		for(int i = 0; i <= arr.length - 1; i++) { // 遍历数组中的每一个数，分别进行处理
			for(int j = 0; j < 32; j++) { // 处理当前数的每一位
				t[j] += ((arr[i] >> j) & 1); // 如果第j位是1，则t[j] + 1
			}
		}
		// 如果t[j] 不是 M的整数倍，说明出现K次的数的第j位一定是1，或运算并入结果中
		for(int j = 0; j < 32; j++) {
			if(t[j] % M != 0) { // K次数的第j位是1
				ans |= (1 << j); // 或进最终的结果
			}
		}
		System.out.print(ans);
	}

}
