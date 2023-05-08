package class17;

public class Code01_Hanoi {
	
	/**
	 * 汉诺塔
	 * 打印n层汉诺塔从最左边移动到最右边的全部过程（递归实现）
	 * 非递归不重要
	 * 
	 * 复杂度，n层汉罗塔，最优解步数是2^n - 1，复杂度是O(2^N - 1)
	 * 
	 * 思路: 
	 * 将圆盘从左挪动到右，经历3个大步骤：
	 * 从左到右搞一个函数:
	 * 1. 1 - n-1个圆盘从左到中
	 * 2. n 从左到右， 打印
	 * 3. 1 - n-1从中到右
	 * 
	 * 从左到中搞一个函数:
	 * 1. 1 - n-1个圆盘从左到右
	 * 2. n 从左到中， 打印
	 * 3. 1 - n-1从右到中
	 * 
	 * 从中到右搞一个函数:
	 * 1. 1 - n-1个圆盘从中到左
	 * 2. n 从中到右，打印
	 * 3. 1 - n-1从左到右
	 * 
	 * 从右到中搞一个函数:
	 * 1. 1 - n-1个圆盘从右到左
	 * 2. n 从右到中，打印
	 * 3. 1 - n-1从左到中
	 * 
	 * 从中到左搞一个函数:
	 * 1. 1 - n-1个圆盘从中到右
	 * 2. n 从中到左，打印
	 * 3. 1 - n-1从右到左
	 * 
	 * 从右到左搞一个函数:
	 * 1. 1 - n-1个圆盘从右到中
	 * 2. n 从右到左，打印
	 * 3. 1 - n-1从中到左
	 */
	public static void hanoi1(int n) {
		leftToRight(n);
	}
	public static void leftToRight(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from left to right." );
		} else {
			leftToMid(N - 1);
			System.out.println("Move " + N + " from left to right." );
			midToRight(N - 1);
		}
	}
	public static void leftToMid(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from left to mid." );
		} else {
			leftToRight(N - 1);
			System.out.println("Move " + N + " from left to mid." );
			rightToMid(N - 1);
		}
	}
	public static void midToRight(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from mid to right." );
		} else {
			midToLeft(N - 1);
			System.out.println("Move " + N + " from mid to right." );
			leftToRight(N - 1);
		}
	}
	public static void rightToMid(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from right to mid." );
		} else {
			rightToLeft(N - 1);
			System.out.println("Move " + N + " from right to mid." );
			leftToMid(N - 1);
		}
	}
	public static void midToLeft(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from mid to left." );
		} else {
			midToRight(N - 1);
			System.out.println("Move " + N + " from mid to left." );
			rightToLeft(N - 1);
		}
	}
	public static void rightToLeft(int N) {
		if(N == 1) {
			System.out.println("Move " + 1 + " from right to left." );
		} else {
			rightToMid(N - 1);
			System.out.println("Move " + N + " rom right to left." );
			midToLeft(N - 1);
		}
	}
	
	/**
	 * 六合一
	 * 
	 * 没有左中右的概念，弄成from,to,other三个变量。
	 * 一个递归函数可以通过增加参数的方式，来表达更多的可能性，可以支持更多的
		功能。
	 * 三步：
		1. 1 - n-1 从 from 到 other，剩下的一个就是to
		2. n 从from到 to，直接打印
		3. 1 - n-1从other到to，剩下的就是from
	 */
	public static void hanoi2(int N) {
		process(N, "left", "right", "mid");
	}
	public static void process(int N, String from, String to, String other) {
		if(N == 1) { // 如果只有一个圆盘了，直接从from移动到to
			System.out.println("Move " + 1 + " from " + from + " to " + to);
		} else {
			// 1 到N-1个圆盘从from到other上
			process(N - 1, from, other, to);
			// N 号圆盘从from直接移动到to
			System.out.println("Move " + N + " from " + from + " to " + to);
			// 搞定剩下的1到N-1号圆盘，从other到to上
			process(N - 1, other, to, from);
		}
	}
	
	public static void main(String[] args) {
		int n = 3;
		hanoi1(n);
		System.out.println("============");
		hanoi2(n);
		System.out.println("============");
	}
}
