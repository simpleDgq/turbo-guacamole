package class32;

public class Code01_IndexTree {
	public static class IndexTree {
		private int tree[]; // 也就是help
		private int N;
		
		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}
		
		// index位置的数加上一个d
		public void add(int index, int d) {
			while(index <= N) {
				tree[index] += d; // 当前元素加d
				index += index & (-index); // 受影响的元素也要加
			}
		}
		
		// 求1~index的累加和
		public int sum(int index) {
			int sum = 0;
			while(index > 0) {
				sum += tree[index]; // 当前元素累加进去
				index -= index & (-index); // 每次抹掉一个1，得到的新位置，累加
			}
			return sum;
		}
	}
	
	
	public static class Right {
		private int[] nums;
		private int N;

		public Right(int size) {
			N = size + 1;
			nums = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			for (int i = 1; i <= index; i++) {
				ret += nums[i];
			}
			return ret;
		}

		public void add(int index, int d) {
			nums[index] += d;
		}

	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.out.println("Oops!");
				}
			}
		}
		System.out.println("test finish");
	}

}
