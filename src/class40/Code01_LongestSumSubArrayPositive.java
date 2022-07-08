package class40;

public class Code01_LongestSumSubArrayPositive {

	//正整数组子数组累加和等于K的最大长度
	//(滑动窗口单调性)
	//数组一连
	/**
	 * 给定一个正整数组成的无序数组arr，给定一个正整数值K
	找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
	返回其长度
	 */
	
	/**
	 * 流程:
	 * 
	 * 搞一个滑动窗口，L和R
	 * 
	 * 如果滑动窗口内的累加和< K, R++
	 * ==K, 搜集答案， L++ 或者R++都行(如果是正数数组, 当你相等的时候，先让 L 动也行，先让 R 动也行，反正另外一个会紧跟着发生.)
	 * >K. L++
	 */
	public static int getMaxLength(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		
		int left = 0;
		int right = 0;
		int sum = arr[0];
		int len = 0;
		
		while(right < arr.length) { // R不回退
			if(sum == K) {
				len = Math.max(len, right - left + 1);
				sum -= arr[left++]; // left++
			} else if(sum > K) {
				sum -= arr[left++]; // left++
			} else {
				right++;
				if(right == arr.length) {
					break;
				}
				sum += arr[right]; // right++
			}
		}
		return len;
	}
	
	// for test
		public static int right(int[] arr, int K) {
			int max = 0;
			for (int i = 0; i < arr.length; i++) {
				for (int j = i; j < arr.length; j++) {
					if (valid(arr, i, j, K)) {
						max = Math.max(max, j - i + 1);
					}
				}
			}
			return max;
		}

		// for test
		public static boolean valid(int[] arr, int L, int R, int K) {
			int sum = 0;
			for (int i = L; i <= R; i++) {
				sum += arr[i];
			}
			return sum == K;
		}

		// for test
		public static int[] generatePositiveArray(int size, int value) {
			int[] ans = new int[size];
			for (int i = 0; i != size; i++) {
				ans[i] = (int) (Math.random() * value) + 1;
			}
			return ans;
		}

		// for test
		public static void printArray(int[] arr) {
			for (int i = 0; i != arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}

		public static void main(String[] args) {
			int len = 50;
			int value = 100;
			int testTime = 500000;
			System.out.println("test begin");
			for (int i = 0; i < testTime; i++) {
				int[] arr = generatePositiveArray(len, value);
				int K = (int) (Math.random() * value) + 1;
				int ans1 = getMaxLength(arr, K);
				int ans2 = right(arr, K);
				if (ans1 != ans2) {
					System.out.println("Oops!");
					printArray(arr);
					System.out.println("K : " + K);
					System.out.println(ans1);
					System.out.println(ans2);
					break;
				}
			}
			System.out.println("test end");
		}
}
