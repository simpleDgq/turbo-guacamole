package class01;

public class Code04_BinarySerch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {1, 2, 3, 4, 5};
		boolean ans = binarySearch(arr, 2);
		System.out.println(ans);

	}
	
	// 有序数组中查找一个数是否存在
	// 二分查找
	/**
	 * 有头尾以及mid指针。
	 * 每次比较mid位置的数和要查找的value，
	 * 如果mid位置的数比value大，去数组左边找，R = mid - 1
	 * 反之，去数组右边找， L = mid + 1
	 * 
	 * 时间复杂度: 有N个数，一次砍一半，总共需要砍了log⁡2N﻿ 次 ==> 时间复杂度logN
	 * @param arr
	 * @return
	 */
	public static boolean binarySearch(int arr[], int value) {
		if(arr == null || arr.length == 0) {
			return false;
		}
		int L = 0;
		int R = arr.length - 1;
		while(L <= R) {
			int mid = L + ((R - L)  >> 1); // 防止溢出, 等价于(L + R) / 2
			if(value == arr[mid]) {
				return true;
			} else if (arr[mid] > value) { // mid的值比value大，去数组左边找
				R = mid - 1;
			} else { // mid的值比value小，去数组右边找
				L = mid + 1;
			}
		}
		return false;
	}
}
