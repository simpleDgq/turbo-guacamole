package class08;

public class Code03_RadixSrot {
	
	/**
	 * 不用桶(队列)是先基数排序
	 * 依次处理个位数、十位数、百位数...
	 * 
	 * 处理个位数：
	 * 1. 准备一个count数组，十位长度，0位置表示的是在原数组中，个位数是0的数出现的次数，
	 * 1表示的是原数组中是个位数是1的数出现的次数
	 * 2. 计算count的前缀和数组，则0表示的是原数组中个位数<=0的元素个数；1位置表示的是原数组中个位数<=1的元素个数。。。
	 * 3. 创建一个helper数组，从右往左遍历原数组中的每一个元素，看个位数，比如个位数是2，则从前缀和数组中取出下标位2的数，表示的是原数组中<=2的元素的个数，
	 * 比如是5，则说明helper数组0-4位置都应该放<=4的元素，将4位置放入当前遍历到的元素；
	 * 然后将前缀和数组2位置的数--，已经放好了，<=2的元素少了一个，下一个小于等于2的元素才能放正确（不然下一个小于等于2的元素，一看前缀和还是5，就又会放在4位置，就。
	 * 出错了）
	 * 
	 * 继续处理十位数。。。相同逻辑
	 */
	
	public static void radixSort(int arr[]) {
		if(arr == null || arr.length == 0) {
			return;
		}
		int digit = getMaxDigit(arr);
		radixSort(arr, 0, arr.length - 1, digit);
	}
	
	/**
	 * digit 表示的是，给定的数组中，最大的元素的十进制位数
	 * @param arr
	 * @param L
	 * @param R
	 * @param digit
	 */
	public static void radixSort(int arr[], int L, int R, int digit) {
		int helper[] = new int[R - L + 1];
		int radix = 10;
		int length = arr.length;
		
		for(int d = 1; d <= digit; d++) { // 依次处理每一位
			int count[] = new int[radix];
			for(int j = 0; j <= length - 1; j++) { // 处理每一个元素
				int dig = getDigit(arr[j], d); // 得到个位数。。十位数。。。百位数
				count[dig]++; // 0位置表示的是。个位数是0的出现了几次
			}
			for(int j = 1; j <= radix - 1; j++) { // 计算前缀和数组
				count[j] +=  count[j - 1];
			}
			// 从右往左遍历原始数组，看个位数是几，然后从前缀和数组中取出对应位置的数，看
			// 小于等于该个位数的元素有几个，将当前被处理的元素放在helper中
			for(int j = length - 1; j >=0; j--) {
				int dig = getDigit(arr[j], d);
				helper[count[dig] - 1] = arr[j];
				count[dig]--; // 一个元素放好了，记得--，代表小于等于2的数少了一个
			}
			// 拷贝helper到原始数组中，继续处理下一位
			for(int j = 0; j <= length - 1; j++) {
				arr[j] = helper[j];
			}
		}
	}
	
	/**
	 * 得到一个数组中最大的数有多少位
	 * @param arr
	 * @return
	 */
	public static int getMaxDigit(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int max = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			if(max < arr[i]) {
				max = arr[i];
			}
		}
		int res = 0;
		while(max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}
	
	/**
	 * 求一个数的第d位是什么，从个位数开始
	 * @param value
	 * @param d
	 * @return
	 */
	public static int getDigit(int value, int d) {
		return (value / (int) (Math.pow(10, d - 1))) % 10;
	}
}
