package class04;

public class Code04_BiggerThanRightTwice {
	/**
	 * 在一个数组中，对于任何一个数num，求有多少个(后面的数*2)依然<num，返回总个数
	 *（num的右边有多少个数*2之后依然小于num，求总个数）
	 *
	 * 思路: 对于每一个数，求它的右边有多少个数*2之后，还比它小。
	 * 
	 * 先求满足条件的数，再进行merge。
	 *	 在merge之前，对于左组的每一个数，判断右组有多少个*2之后小于当前左组数。
	 *	 算出来之后，在进行merge。
	 * Trick:将计算满足条件的数，放在了merge之前。
	 * 
	 * 左组的数小于右组的数*2，说明对于L指向的位置，右边没有满足条件的数，L向后滑动，看下一个数
	 * 左组的数等于右组的数*2，L向后滑动（与小于同理）
	 * 左组的数大于右组的数*2，R向后滑动，滑动到不能滑动了，求R - mid - 1，
	 */
	
	public static int biggerThanRightTwice(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}
	
	public static int process(int arr[], int L, int R) {
		if(L == R) {
			return 0;
		}
		int M = L + ((R - L) >> 1);
		return process(arr, L, M) 
		+ process(arr, M + 1, R) 
		+ merge(arr, L, M, R);	 
	}
	
	public static int merge(int arr[], int L, int M, int R) {
		int windowR = M + 1;
		int ans = 0;
		/**
		 * 有一个指针windowR，指向右组的数，
		 * 如果右组的数*2  < 左组的当前数，则windowR++
		 */
		for(int i = L; i <= M; i++) {// 对于左组中的一个数，求右组中有多少个数*2 < 当前数
			while(windowR <= R && arr[i] > arr[windowR] << 1) {
				windowR++;
			}
			ans += windowR - M - 1; // windowR不回退，当前windowR指向的元素满足条件，
			                        //那么它之前得也一定满足，windowR - M - 1已经算上了前面的数了
		}
		
		// 去merge吧
		int p1 = L;
		int p2 = M + 1;
		int i = 0;
		int helper[] = new int[R - L + 1];
		while(p1 <= M && p2 <= R) {
			if(arr[p1] < arr[p2]) {
				helper[i++] = arr[p1++];
			} else {
				helper[i++] = arr[p2++];
			}
		}
		
		while(p1 <= M) {
			helper[i++] = arr[p1++];
		}
		
		while(p2 <= R) {
			helper[i++] = arr[p2++];
		}
		
		for(int j = 0; j <= helper.length - 1; j++) {
			arr[L + j] = helper[j];
		}
		return ans;
	}

}
