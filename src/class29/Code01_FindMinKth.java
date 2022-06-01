package class29;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_FindMinKth {
	/**
	 * 在无序数组中找到第K小的数
	 *  1. 改写快排
	 *  2. bfprt算法
	 *
	 */
	/**
	 * 1. 改写快排
	 * 	在快排过程中，做完一次partition，小于V的放左边，等于V
		放中间，小于v的放右边，然后查看要求的K在不在等于区的范围内
		如果在，则命中，第K小的数就是V，如果不在，如果K比等于区的
		左边界小，则继续去小于区找；否则去大于区找，递归下去。
	 */
	public static int findMinKth1(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		return process(arr, 0, arr.length - 1, K - 1);
	}
	
	// arr[L..R]  范围上，如果排序的话(不是真的去排序)，找位于index的数
	public static int process(int arr[], int L, int R, int index) {
		if(L == R) {
			return arr[L];
		}
		// 随机选择一个数和最后一个数交换
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R); 
		int equals[] = partition(arr, L, R);

		int ans = Integer.MIN_VALUE;
		if(index >= equals[0] && index <= equals[1]) { // 在等于区内
			ans = arr[index];
		} else if(index > equals[1]) { // 在大于区
			ans = process(arr, equals[1] + 1, R, index);
		} else {
			ans = process(arr, 0, equals[0] - 1, index);
		}
		return ans;
	}
	
	public static int[] partition(int arr[], int L, int R) {
		if(L > R) {
			return new int[] {-1, -1};
		}
		if(L == R) {
			return new int[] {L , R};
		}
		int less = L - 1;
		int more = R;
		int index = L; // 注意
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
	
	public static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 * bfprt算法
	 * 1. 将arr中的数划分成五个数一组
	 * 2. 将每个单独的数组，排好序
	 * 3.将每个小数组的中位数取出来组成m
	 * 4.求出m数组的中位数，作为划分值p ==> m数组长度是N / 5, 那么它的中位数就是第 N / 10小的数，所有可以调用bfprt(m, N / 10)求
	 * 5.用p作为划分值，对原数组进行划分
	 * 6.查看要求的K是否在等于区范围内，在的话，直接返回，不在的话，去小于区或者大于区找, 继续递归调用bfprt
	 */
	public static int findMinKth2(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}
		return bfprt(arr, 0, arr.length - 1, K - 1);
	}
	
	// arr[L..R]  如果排序的话，位于index位置的数，是什么，返回
	public static int bfprt(int arr[], int L, int R, int index) {
		if(L == R) {
			return arr[L];
		}
		int p = getMedian(arr, L, R); // 选划分值p
		int equals[] = partition2(arr, L, R, p); //根据p进行划分
		int ans = Integer.MIN_VALUE;
		if(index >= equals[0] && index <= equals[1]) {
			ans = arr[index];
		} else if( index > equals[1]) {
			ans = bfprt(arr, equals[1] + 1, R, index);
		} else {
			ans = bfprt(arr, L, equals[0] - 1, index);
		}
		return ans;
 	}
	
	// arr[L...R]  五个数一组
	// 每个小组内部排好序
	// 每个小组中位数领出来，组成marr
	// marr中的中位数，返回
	public static int getMedian(int arr[], int L, int R) {
		int N = R - L + 1;
		int mLength = (N % 5) == 0 ? (N / 5) : (N / 5) + 1;
		int mArr[] = new int[mLength];
		for(int i = 0; i <= mLength - 1; i++) {
			 int l = L + i * 5;
			 int r = Math.min(R, l + 4);
			 // 排序
			 insertSort(arr, l, r);
			 // 返回中位数，存储
			 mArr[i] = arr[ (r + l) / 2 ];
		}
		return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
	}
	
	// 划分
	public static int[] partition2(int arr[], int L, int R, int p) {
		if(L > R) {
			return new int[] {-1, -1};
		}
		if(L == R) {
			return new int[] {L, R};
		}
		int less = L - 1;
		int more = R + 1; // 注意，这里从R+1开始, more指向的是大于区的第一个数
		int index = L; // 注意
		while(index < more) {
			if(arr[index] < p) {
				swap(arr, index++, ++less);
			} else if(arr[index] > p) {
				swap(arr, index, --more);
			} else {
				index++;
			}
		}
		// 最后不用交换, 而且more - 1
		return new int[] {less + 1, more - 1}; 
	}
	
	// 插入排序，选别的排序也行
	public static void insertSort(int arr[], int L, int R) {
		for(int i = L + 1; i <= R; i++) { // 注意下标
			for(int j = i - 1; j >= L; j--) {
				if(arr[j] > arr[j + 1]) {
					swap(arr, j, j+1);
				}
			}
		}
	}
	
	
	/**
	 * 利用大根堆
	 *
	 */
	public static class MaxHeapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}

	}
	// 利用大根堆，时间复杂度O(N*logK)
	public static int minKth1(int[] arr, int k) {
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
		for (int i = 0; i < k; i++) {
			maxHeap.add(arr[i]);
		}
		for (int i = k; i < arr.length; i++) {
			if (arr[i] < maxHeap.peek()) {
				maxHeap.poll();
				maxHeap.add(arr[i]);
			}
		}
		return maxHeap.peek();
	}

}
