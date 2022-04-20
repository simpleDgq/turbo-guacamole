package class08;

public class Code02_CountSort {
	
	/**
	 * 计数排序
	 * 思路：给定一个整型数组，数是有范围的，比如是0-200，将他们排序
	 * 
	 * 1. 弄一个辅助数组，长度为201，每一位表示的是该位置对应的数在原始数组中出现的次数
	 * 例如，0号位置，表示的是0在原始数组中出现的次数
	 * 
	 * 2. 遍历上面的辅助数组，例如，0位置是3，则表示0出现了3次，那么将3个3依次放入原数组中
	 * ... 依次类推，放入原数组中的数就拍好序了
	 */
	
	public static void countSort(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		// 找数组最大值
		int max = arr[0];
		for(int i = 1; i <= arr.length -1; i++) {
			max = Math.max(arr[i], max);
		}
		// 创建辅助数组
		int helper[] = new int[max + 1];
		for(int i = 0; i <= arr.length -1; i++) {
			helper[arr[i]]++; // 记录每个数出现了几次
		}
		// 遍历辅助数组，依次放入对应分数的元素到原始数组中
		int index = 0;
		for(int i = 0; i <= helper.length -1; i++) {
			for(int j = 1; j <= helper[i]; j++) {
				arr[index++] = i;
			}
		}
	}
}
