package class05;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_QuickSortUnRecurse {
	/**
	 * 非递归实现快排
	 * 1. 使用栈
	 * 每次划分，产生新的任务，加入栈中，下次弹出，继续处理
	 */
	// 记录任务信息
	public static class OP {
		int l;
		int r;
		public OP(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}
	
	public static void quickSort(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	public static void process(int arr[], int L, int R) {
		if(L >= R) {
			return;
		}
		// [L,R]范围随机选择一个数
		int X =  arr[L + (int)(Math.random() * (R - L + 1))];
		int equals[] = partition(arr, L, R, X);
		// 一次划分，多出两个左右任务，放入栈
		Stack<OP> stack = new Stack<OP>();
		stack.push(new OP(L, equals[0] - 1));
		stack.push(new OP(equals[1] + 1, R));
		while(!stack.isEmpty()) { // 取出子任务处理
			OP op = stack.pop();
			// 如果L >= R, 只有一个元素或者越界，直接return
			if(op.l < op.r) { // L>=R 直接return
				// 划分
				int val =  arr[op.l + (int)(Math.random() * (op.r - op.l + 1))];
				equals = partition(arr, op.l, op.r, val);
				// 一次划分，产生新的子任务，加入栈
				stack.push(new OP(op.l, equals[0] - 1));
				stack.push(new OP(equals[1] + 1, op.r));
			}
		}
	}
	
	// 荷兰国旗问题
	public static int[] partition(int arr[], int L, int R, int X) {
		int less = L - 1; // 小于区的第一个数
		int more = R + 1;
		int index = L;
		while(index < more) {
			if(arr[index] < X) {
				swap(arr, index++, ++less);
			} else if(arr[index] > X) {
				swap(arr, index, --more);
			} else {
				index++;
			}
		}
		return new int[] {less + 1, more - 1}; // 返回等于区第一个数和最后一个数
	}
	
	public static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 *  2. 快排3.0 非递归版本 用队列来实现
	 * @param arr
	 */
	public static void quickSort3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int N = arr.length;
		int L = 0;
		int R = N - 1;
		int X =  arr[L + (int)(Math.random() * (R - L + 1))];
		int[] equalArea = partition(arr, 0, N - 1, X);
		int el = equalArea[0];
		int er = equalArea[1];
		Queue<OP> queue = new LinkedList<>();
		queue.offer(new OP(0, el - 1));
		queue.offer(new OP(er + 1, N - 1));
		while (!queue.isEmpty()) {
			OP op = queue.poll();
			if (op.l < op.r) {
				int val =  arr[L + (int)(Math.random() * (R - L + 1))];
				equalArea = partition(arr, op.l, op.r, val);
				el = equalArea[0];
				er = equalArea[1];
				queue.offer(new OP(op.l, el - 1));
				queue.offer(new OP(er + 1, op.r));
			}
		}
	}

	// 生成随机数组（用于测试）
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// 拷贝数组（用于测试）
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

	// 对比两个数组（用于测试）
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

	// 打印数组（用于测试）
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 跑大样本随机测试（对数器）
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr3 = copyArray(arr1);
			quickSort(arr1);
			quickSort3(arr3);
			if (!isEqual(arr1, arr3)) {
				succeed = false;
				break;
			}
		}
		System.out.println("test end");
		System.out.println("测试" + testTime + "组是否全部通过：" + (succeed ? "是" : "否"));
	}
}
