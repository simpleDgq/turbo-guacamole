package class12;

import java.util.ArrayList;
import java.util.List;

public class Code09_MaxHappy {
	/**
	 * 派对的最大快乐值
	 * 
	 *  员工信息的定义如下:
	 *	class Employee {
	 *	    public int happy; // 这名员工可以带来的快乐值
	 *	    List<Employee> subordinates; // 这名员工有哪些直接下级
	 *	}
	 *	公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树
	 *	树的头节点是公司唯一的老板，除老板之外的每个员工都有唯一的直接上级
	 *	叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外每个员工都有一个或多个直接下级
	 *	这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
	 *	1.如果某个员工来了，那么这个员工的所有直接下级都不能来
	 *	2.派对的整体快乐值是所有到场员工快乐值的累加
	 *	3.你的目标是让派对的整体快乐值尽量大
	 *	给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
	 *
	 * 思路:
	 * 分析可能性：
	 * 1. X无关(X不来)的情况下，最大happy就是X下面的孩子节点来或者不来的最大happy值相加。
	 * 例如X下面有三个节点a，b，c, X 不来， abc可以来可以不来。
	 * maxHappy = max(a来，a不来) + max(b来，b不来) + max(c来，c不来)；
	 * 2. X有关(X来)的情况下，最大happy，是X下面的孩子节点都不来的最大happy值相加。
	 * maxHappy = max(a不来) + max(b不来) + max(c不来)；
	 * 
	 * 分析要搜集的信息:
	 * 第一种情况下，要搜集的是a来a不来的最大值，b来b不来的最大值，c来c不来的最大值。
	 * 第二种要搜集a不来的最大值，b不来的最大值，c不来的最大值
	 * 所以要搜集的信息就是一个节点，来的最大值，不来的最大值。
	 * 
	 * 取并集：
	 * 搜集一个节点来于不来的情况下，两种最大值。
	 * comingMaxHappy
	 * noComingMaxHappy
	 * 
	 */
	
	public static class Employee {
		public int happy; // 这名员工的快乐值
		List<Employee> subordinates;// 这名员工有哪些直接下级
		public Employee(int h) {
			happy = h;
			subordinates = new ArrayList<>();
		}
	}
	
	public static class Info {
		int comingMaxHappy; // 来能得到的最大快乐值
		int noComingMaxHappy; // 来能得到的最大快乐值
		public Info(int comingMaxHappy, int noComingMaxHappy) {
			this.comingMaxHappy = comingMaxHappy;
			this.noComingMaxHappy = noComingMaxHappy;
		}
	}
	public static int maxHappy(Employee employee) {
		if(employee == null) {
			return 0;
		}
		Info info = process(employee);
		return Math.max(info.comingMaxHappy, info.noComingMaxHappy);
	}
	public static Info process(Employee X) {
		if(X == null) { // 空，好设置
			return new Info(0, 0);
		}
		// 以X为头，构造Info.
		int comingMaxHappy = X.happy; // X来的情况下，maxHappy
		int noComingMaxHappy = 0;// X不来的情况下，maxHappy
		List<Employee> nexts = X.subordinates;
		for(Employee next: nexts) { 
			// X节点下的每一个节点，可来可不来，递归求出来和不来的最大值
			Info nextComingOrNoComingInfo = process(next);
			// 分别构造出X节点的来和不来的最大快乐值
			// 1. 如果 X 节点不来，它的noComingMaxHappy, 是X下面的节点来与不来的最大值累加
			noComingMaxHappy += Math.max(nextComingOrNoComingInfo.noComingMaxHappy, nextComingOrNoComingInfo.comingMaxHappy);
			// 2. 如果 X 节点来，它的noComingMaxHappy，是它下面的节点不来的最大值累加
			comingMaxHappy += nextComingOrNoComingInfo.noComingMaxHappy;
		}
		return new Info(comingMaxHappy, noComingMaxHappy);
	}
}
