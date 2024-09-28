package class05;

public class Code01_QuickSort {
	
	/** 左师傅在bilibili又讲了这版，以前的mashibing那版有问题
	 * 
	 * https://blog.csdn.net/m0_62573351/article/details/130092475
	 * 
	 * 随机快排1.0
	 * 
	 * <= X的放左边，> X的放右边
	 * 
	 * 划分思路：随机选择一个数作为划分值X
	 * 有一个小于等于区，从-1位置开始，
	 * 1. 如果当前元素小于等于划分值，则当前元素和小于等于区的下一个元素进行交换，当前元素跳下一个，小于等于区右扩
	 * 2. 如果当前元素大于划分值，当前元素直接调下一个。
	 * 3. 在划分过程中保证小于等于区的最后一个元素是划分值X
	 * 
	 * 排序思路:
	 * 划分一次，得到了小于等于区的最后一个元素X的位置，X位置就确定好了，递归去求X的左边和X的右边。
	 * 
	 * 时间复杂度: O(N^2)
	 */
	public void quickSort1(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}
	
	// L~R范围上去递归，一次划分确定好一个位置
	public void process(int arr[], int L, int R) {
		if(L >= R) { // 只有一个数，不用划分递归求了，直接返回
			return;
		}
		int X = arr[L + (int) (Math.random() * (R - L + 1))];
		// 划分
		int lessEqual = partition(arr, L, R, X);
		// 搞左边
		process(arr, L, lessEqual - 1);
		// 搞右边
		process(arr, lessEqual + 1, R);
	}
	
	//L~R范围上，用数组的随机一个元素X作为划分值，小于等于X的放左边，大于X的放右边
	//确保小于等于区的最后一个数是X
	public int partition(int arr[], int L, int R, int X) {
		int lessEqual = L - 1; // 从L - 1 开始
		int index = L;
		while(index < R) { // 没有和R撞上
			if(arr[index] <= X) { // 当前数小于等于划分值，当前数和小于等于区的下一个数交换，小于等于区右扩
				swap(arr, index++, ++lessEqual);
			}
		}
		return lessEqual + 1;
	}
	public void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/** 
	 * 
	 * 随机快排2.0 ==> 荷兰国旗问题 
	 * https://blog.csdn.net/m0_62573351/article/details/130092475 
	 * 
	 *  < X的放左边，=X的放中间，> X的放右边
	 *  
	 * 划分思路：随机选择一个数作为划分
	 * 有一个小于区，从-1位置开始，有一个大于区从arr.length开始。
	 * 
	 * 1. 如果当前元素小于划分值，则当前元素和小于区的下一个元素进行交换，当前元素跳下一个，小于区右扩
	 * 2. 如果当前元素等于划分值，当前元素直接调下一个。
	 * 3. 如果当前元素大于划分值，当前元素和大于区的前一个数交换，当前数不动(当前数是从后面交换过去的，还没有比较，当前数不能动)，大于区左扩
	 * 4.index撞上大于区表示结束。返回等于区的开始和结束位置。开始结束位置就是小于区和大于区的边界less+1，more-1 (因为小于区是从-1开始，大于区是从N开始
	 * 扩在less的数是小于区的，less+1就是等于区；同理，扩在more里面的都是大于区的，more-1就是等于区)
	 * 
	 * 排序思路：递归去求，等于区的左边和右边，每次递归搞定一个等于区的数。
	 * 
	 * 时间复杂度: O(N*logN)
	 */
	public void quickSort2(int arr[]) {
		if(arr == null || arr.length < 2) {
			return;
		}
		porcess2(arr, 0, arr.length - 1);
	}
	// arr[L~R]上进行排序
	public void porcess2(int arr[], int L, int R) {
		if(L >= R) {
			return;
		}
		// 随机选择一个数作为划分
		int X = arr[L + (int) (Math.random() * (R - L + 1))];
		// 划分
		int equals[] = partition2(arr, L, R, X);
		// 搞左边
		porcess2(arr, L, equals[0] - 1);
		// 搞右边
		porcess2(arr, equals[1] + 1, R);
	}
	public int[] partition2(int arr[], int L, int R, int X) {
		int less = L - 1; // 从L - 1位置开始
		int more = R + 1; // 从R+1位置开始
		int index = L;
		while(index < more) { // 当index和大于区撞上的时候，表示结束
			if(arr[index] < X) {
				swap(arr, index++, ++less); // 当前元素和小于区的下一个元素交换，小于区右扩，当前元素跳下一个
			} else if(arr[index] > X) {
				swap(arr, index, --more); // 当前元素和大于区的前一个元素交换，大于区左扩，当前元素不跳
			} else { // 相等，直接跳下一个
				index++;
			}
		}
		return new int[] {less + 1, more - 1}; // 返回等于区的范围
	}
	
}
