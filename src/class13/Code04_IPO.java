package class13;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_IPO {
	/**
	 * 做项目获得的最大钱数
	 * 
	 * 输入正数数组costs、正数数组profits、正数K和正数M
	* costs[i]表示i号项目的花费
	* profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
	* K表示你只能串行的最多做k个项目
	* M表示你初始的资金
	* 说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目。
	* 输出：最后获得的最大钱数
	*
	* 贪心策略：
	* 每次都做花费最小，但收益最大的。
	* 
	* 准备两个堆，一个小根堆，一个大根堆，将Program按照花费排序，花费小的排在前面，放入小根堆，每次从小根堆中取出所有初始
	* 资金能做的所有项目；放入大根堆中，大根堆按照收益进行排序，收益大的排在前面，然后做大根堆最前面的；
	* 更新收益，重新从小根堆取项目，放入大根堆，重复上面的过程。
	*/
	
	public static class Program {
		int cost;
		int profit;
		public Program(int cost, int profit) {
			this.cost = cost;
			this.profit = profit;
		}
	}
	// 花费从小到大排序
	public static class CostComparator implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o1.cost - o2.cost;
		}
	}
	// 利润从大到小排序
	public static class ProfitComparator implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o2.profit - o1.profit;
		}
	}
	public static int maxMoneyDoProgram(int costs[], int profits[], int K, int M) {
		if(costs == null || costs.length == 0 || profits == null || profits.length == 0 
				|| K == 0 || M == 0) {
			return 0;
		}
		// 花费小根堆
		PriorityQueue<Program> costsHeap = new PriorityQueue<Program>(new CostComparator());
		// 利润大根堆
		PriorityQueue<Program> profitsHeap = new PriorityQueue<Program>(new ProfitComparator());
		Program program = null;
		// 将所有的program放入小根堆
		for(int i = 0; i <= costs.length - 1; i++) {
			program = new Program(costs[i], profits[i]);
			costsHeap.add(program);
		}
		// 最多只能做K个项目
		for(int i = 1; i<= K; i++) {
			// 取出小根堆中能做的项目，放入到按利润从大到小排序的大根堆
			while(!costsHeap.isEmpty() && costsHeap.peek().cost <= M) {
				program = costsHeap.poll();
				profitsHeap.add(program);
			}
			// 大根堆中没有项目的时候，说明小根堆中的项目都不能被解锁，即使没做够K个，也应该直接返回
			if(profitsHeap.isEmpty()) {
				break;
			}
			// 否则，取出大根堆的堆顶项目，累加在扣除花费之后还能挣到的钱(利润)
			M += profitsHeap.poll().profit;
		}
		return M;
	}
}
