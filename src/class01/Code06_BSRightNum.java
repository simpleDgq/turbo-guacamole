package class01;

public class Code06_BSRightNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {1, 2, 2, 4, 4, 4, 5};
		int ans = bsRightNum(arr, 3);
		System.out.println(ans);
	}
	
	/**
	 * 有序数组中找到<-num最右的位置
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int bsRightNum(int arr[], int num) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int ans = -1;
		int L = 0;
		int R = arr.length - 1;
		while(L <= R) {
			int mid = L + ((R - L) >> 1);
			if(arr[mid] <= num) { // mid位置的数比num小，记录下，继续到右边找
				ans = mid;
				L = mid + 1;
			} else { // mid位置的数比num大，记录下，继续到左边找
				R = mid - 1;
			}
		}
		return ans;
	}

}
