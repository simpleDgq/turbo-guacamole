package class29;

public class Code03_WaterStore {
	/**
	 * 蓄水池算法
	 * 流程：
		1-10号球，都直接进袋子。进袋子的概率是1;
		10号之后的球，假设是第i个，都以10 / i （i分之10）的概率进袋子;
		袋子中的球随机等概率扔掉一个
	 *
	 */
	public static class RandomBox {
		private int[] bag;
		private int N;
		private int count;

		public RandomBox(int capacity) {
			bag = new int[capacity];
			N = capacity;
			count = 0;
		}

		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}

		public void add(int num) {
			count++;
			if (count <= N) { // 小于N的球，直接入袋子
				bag[count - 1] = num;
			} else {
				// 第count个来的数，以N / count 概率入袋子。也就是课上讲的10 / i.
				// rand(count) 产生1~ count的数，如果是小于等于N的话，就入袋子。
				// 假设N等于10, 也就是在1~count范围上产生的数小于等于10的时候的时候入袋子，所有概率是N / count.
				if (rand(count) <= N) {
					bag[rand(N) - 1] = num;
				}
			}
		}

		public int[] choices() {
			int[] ans = new int[N];
			for (int i = 0; i < N; i++) {
				ans[i] = bag[i];
			}
			return ans;
		}

	}
}
