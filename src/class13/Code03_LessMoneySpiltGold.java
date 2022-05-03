package class13;

import java.util.PriorityQueue;

public class Code03_LessMoneySpiltGold {
	/**
	 * 一块金条切成两半，是需要花费和长度数值一样的铜板
	 * 比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板? 
	 * 例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
	 * 如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
	 * 但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
	 * 输入一个数组，返回分割的最小代价
	 * 
	 * 贪心策略:
	 * 哈夫曼树。
	 * 准备一个小根堆，将所有的元素入堆，每次弹出两个元素，求和，和累加到结果中; 并且将和放入到小根堆，
	 * 下一次继续弹出两个元素，直到小根堆中只有一个元素。结束。
	 * 这是哈夫曼树的构造过程
	 */
	public static int lessMoneySpiltGold(int arr[]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		for(int item : arr) {
			heap.add(item);
		}
		int ans = 0;
		int cost = 0;
		while(heap.size() > 1) {
			cost = heap.poll() + heap.poll();
			ans += cost;
			heap.add(cost);
		}
		return ans;
	}

}
