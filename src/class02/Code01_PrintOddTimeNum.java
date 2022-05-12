package class02;

public class Code01_PrintOddTimeNum {
	
	public static void main(String[] args) {
//		int arr[] = {1, 3, 3, 3, 1 , 5, 5, 6, 6};
//		printOddNum1(arr);
		
		int arr2[] = {1, 3, 3, 3, 1 , 5, 5, 5, 6, 6};
		printOddNum2(arr2);
	}
	/**
	 * 一个数组中，一个数字出现了奇数次，找到并且打印这个数。
	 * 思路: 0^N = N
	 *      N^N = 0
	 *    多个数异或运算，最终的结果和顺序无关。
	 * 
	 * 由于只有一个数出现了奇数次，其它数都是偶数次，所有的数异或起来，
	 * 就能得到出现奇数次的num，直接打印就行。
	 */
	public static void printOddNum1(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int eor = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}
	
	/**
	 * 一个数组中，有两种数字出现了奇数次，找到并且打印这两种数。
	 * 
	 * 思路: 假设数组中出现奇数次的数是a和b
	 * 1. 搞个eor异或上数组中所有的数，那么最终的结果一定是a^b
	 * 2. a^b一定不等于0（因为a和b都出现了奇数次），那么a^b的结果一定有以为是1。
	 * 3. 假设a^b的第3位是1，那么说明a和b的第3位一定是不一样的，那么将数组中的数可以分为两类：第3位是1的和第3位不是1的。
	 *  ==> a 和 b一定落在不同的区间，假设a落在第3位是1的区间，b落在第3位是0的区间，其它的数也一定会落在不同的区间。
	 * 4. 搞个eor', 异或上第三位是1的所有的数，就能得到a，其它的数出现偶数次，异或之后一定是0。
	 * 5. eor ^ eor' 得到b
	 */
	public static void printOddNum2(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int eor = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			eor ^= arr[i]; // 异或上所有的数，得到a^b
		}
		// 找出a^b最右侧的1
		int rightOne = eor ^ (-eor);
		int eor2 = 0;
		for(int j = 0; j <= arr.length - 1; j++) { // 异或上第3位是1的数
			eor2 ^= (arr[j] & rightOne) == 0 ? 0 : arr[j]; // 如果(arr[j] & rightOne) == 0, 说明对应的位置上是0
		}
		System.out.print(eor2 + " " + (eor ^ eor2));
	}
}
