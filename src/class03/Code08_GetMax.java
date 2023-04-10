package class03;

public class Code08_GetMax {

	// 求arr中的最大值
	public static int getMax(int[] arr) {
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上求最大值  L ... R   N
	/**
	 * 求中点，左边求一个最大值，右边求一个最大值，然后PK。
	 * 
	 * Master公式 (参考:https://blog.csdn.net/qq_43582366/article/details/124001445)

		T(N)=a*T(N/b)+O(N^d)
		理解：其中 a >= 1 and b > 1 是常量，其表示的意义是n表示问题的规模，a表示递归的次数也就是生成的子问题数，
		b表示每次递归是原来的1/b之一个规模，f（n）表示分解和合并所要花费的时间之和。
		条件：想要使用这个公式，必须满足一个条件，所有的子方法的规模必须相同
		计算方法：
		以下面的递归方法为例：子方法为process，int leftMax=process(arr,L,mid);调用了一次process，规模为N/2,
		因为将数组平均分为两半，int rightMax=process(arr,mid+1,R);调用了一次process，规模也是N/2，剩下的
		只是Math.max(leftMax,rightMax); 
		作了一次比较，没有进行循环，所以时间复杂度为O(N^0)=1,这就满足了上面master的条件，a为2(两次子方法)，
		b为1/2(规模为1/2),d为0，所以得到的master公式为T(N)=2*T(2/N)+O(1).根据计算方法，d=0,loga^b=loga/logb=log2/log2=1,
		所以时间复杂度为O(N)
	 * @param arr
	 * @param L
	 * @param R
	 * @return
	 */
	public static int process(int[] arr, int L, int R) {
		// arr[L..R]范围上只有一个数，直接返回，base case
		if (L == R) { 
			return arr[L];
		}
		// L...R 不只一个数
		// mid = (L + R) / 2
		int mid = L + ((R - L) >> 1); // 中点   	1
		int leftMax = process(arr, L, mid);
		int rightMax = process(arr, mid + 1, R);
		return Math.max(leftMax, rightMax);
	}

}
