package class29;

import java.util.Arrays;

public class Code02_TopK {
	
	// 时间复杂度O(N*logN)
	// 排序+收集
	public static int[] maxTopK1(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int N = arr.length;
		k = Math.min(N, k);
		Arrays.sort(arr);
		int[] ans = new int[k];
		for (int i = N - 1, j = 0; j < k; i--, j++) {
			ans[j] = arr[i];
		}
		return ans;
	}
		
	
	// 方法二，时间复杂度O(N + K*logN)
	// 解释：堆
	public static int[] topK2(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return null;
		}
		int N = arr.length;
		if(K >= N) {
			return null;
		}
		K = Math.min(N, K);
		// 从底往上建堆
		for(int i = N - 1; i >= 0; i--) {
			heapify(arr, i, N);
		}

		int heapSize = N;
//		int ans[] = new int[K];
//		int index = 0;
		swap(arr, 0, --heapSize);
//		ans[index++] = arr[heapSize];
		heapify(arr, 0, heapSize);
		int count = 1;
		while(count < K && heapSize > 0) { // 只确定K个数，进行调整
			swap(arr, 0, --heapSize);
//			ans[index++] = arr[heapSize]; // 直接取，也可以后面for循环取
			heapify(arr, 0, heapSize);
			count++;
		}
		// 取后面排好序的K个数
		int ans[] = new int[K];
		for(int i = 1; i <= K; i++ ) {
			ans[i - 1] = arr[N - i];
		}
		return ans;
	}
	
	public static void heapify(int arr[], int index, int heapSize) {
		int left = index * 2 + 1;
		while(left < heapSize) {
			int largestIndex =  left + 1 < heapSize ? 
					(arr[left + 1] > arr[left] ? left + 1 : left) : left;
			if(arr[index] >= arr[largestIndex]) {
				break;
			}
			swap(arr, index, largestIndex);
			index = largestIndex;
			left = index * 2 + 1;
		}
	}
	
	public static void heapInsert(int arr[], int index) {
		// 来到0位置的时候，-1/2 = 0, 所以while也会退出
		while(arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
		}
	}
	
	public static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	
	// 方法3
	/**
	 * 例如数组有300个数，要求Top 100的数。
		则先求出第200小的数（300 - 100 = 200）
		，那么这个第200小的数就是第100个最大的数。
		=====
	 * 1.求Top K个数，可以先求第N - K小的数，假设是p
		那么这个p的后面都是大于等于p的数（假设
		数组是有序的，便于理解）==> 求第K小的数，O(N)
		
		2. 然后遍历一遍数组，将大于第K小的数p的数都放入一个
		数组中，数组长度为K，如果遍历完了原数组，没有凑够K个数，那么
		剩余的位置都是p ==> O(N)
		
		3. 将答案数组排序 ==> K*logK
	 */
	public static int[] topK3(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return null;
		}
		int N = arr.length;
		K = Math.min(N, K);
		int minthK = minK(arr, N - K);
		int ans[] = new int[K];
		int index = 0;
		
		// 将数组中大于minthK的数放入答案中
		for(int i = 0; i <= N - 1; i++) {
			if(arr[i] > minthK) {
				ans[index++] = arr[i];
			}
		}
		// 如果遍历完数组，没有找到足够的元素，剩下的元素都是minthK
		for(int i = index; i <= K - 1; i++) {
			ans[i] = minthK;
		}
		// 排序
		Arrays.sort(ans); // 排序是从小到大
		// 需要从大到小逆序
		for(int L = 0, R = K - 1; L < R; L++, R--) {
			swap(ans, L, R);
		}
		return ans;
	}
	
	// 求第K小的值
	public static int minK(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		
		return process(arr, 0, arr.length - 1, K - 1);
	}
	
	public static int process(int arr[], int L, int R, int index) {
		if(L > R) { // 这句不知道为什么，不加就越界
			return Integer.MIN_VALUE;
		}
		if(L == R) {
			return arr[L];
		}
		// 随机选择一个数和最后一个数交换
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
		int equals[] = partition(arr, L, R);
		
		int ans = Integer.MIN_VALUE;
		if(index >= equals[0] && index <= equals[1]) {
			ans = arr[index];
		} else if(index > equals[1]) {
			ans = process(arr, equals[1] + 1, R, index);
		} else {
			ans = process(arr, L, equals[0] - 1, index);
		}
		return ans;
	}
	
	public static int[] partition(int arr[], int L, int R) {
		if(L > R) {
			return new int[] {-1, -1};
		}
		if(L == R) {
			return new int[] {L, R};
		}
		int less = L - 1;
		int more = R;
		int index = L;
		while(index < more) {
			if(arr[index] < arr[R]) {
				swap(arr, index++, ++less);
			} else if(arr[index] > arr[R]) {
				swap(arr, index, --more);
			} else {
				index++;
			}
		}
		swap(arr, more, R);
		return new int[] {less + 1, more};
	}
	
	
	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			// [-? , +?]
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 生成随机数组测试
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean pass = true;
		System.out.println("测试开始，没有打印出错信息说明测试通过");
		for (int i = 0; i < testTime; i++) {
			int k = (int) (Math.random() * maxSize) + 1;
			int[] arr = generateRandomArray(maxSize, maxValue);

			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);

			int[] ans1 = maxTopK1(arr1, k);
			int[] ans2 = topK2(arr2, k);
			int[] ans3 = topK3(arr3, k);

			if (!isEqual(ans1, ans3)) {
				pass = false;
				System.out.println("出错了！");
				printArray(ans1);
				printArray(ans2);
				printArray(ans3);
				break;
			}
		}
		System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
	}

}
