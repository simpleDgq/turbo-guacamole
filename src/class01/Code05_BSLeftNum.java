package class01;

public class Code05_BSLeftNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {1, 2, 2, 4, 4, 4, 5};
		int ans = bsLeftNum(arr, 3);
		System.out.println(ans);
	}
	
	/**
	 * 有序数组中找到>=num的数，最左的位置
	 * 
	 * 思路：有一个变量t，从mid开始，如果mid位置的数大于等于2，t记录下来下标;
	 * 继续在t的左边找还有没有大于等于2的数
	 * @return
	 */
	public static int bsLeftNum(int arr[], int num) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int ans = -1;
		int L = 0;
		int R = arr.length - 1;
		while(L <= R) { 
			int mid = L + ((R - L) >> 1);
			if(arr[mid] >= num) {
				ans = mid;
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return ans;
	}

}
