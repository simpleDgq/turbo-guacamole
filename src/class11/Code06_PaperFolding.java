package class11;

public class Code06_PaperFolding {
	/**
	 * 
	 * 折纸问题
		请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开
		此时折痕是凹下去的，即折痕突起的方向指向纸条的背面
		如果从纸条的下边向上方连续对折2次，压出折痕后展开
		此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。 
		给定一个输入参数N，代表纸条都从下边向上方连续对折N次
		请从上到下打印所有折痕的方向。 
		N=1时，打印: down 
		N=2时，打印: down down up
		
	*
	* 思路：
	* 第一次折，是凹折痕，
		第二次是凹凹凸
		第三次是凹凹凸凹凹凸凸
		想象一颗二叉树，任意一个节点的左边都是凹，右边都是凸。中序遍历的结果就是要打印的结果
	 */
	
	public static void paperFold(int N) {
		if(N <= 0) {
			return;
		}
		process(1, N, true);
	}
	// 当前你来了一个节点，脑海中想象的！
	// 这个节点在第i层，一共有N层，N固定不变的
	// 这个节点如果是凹的话，down = T
	// 这个节点如果是凸的话，down = F
	// 函数的功能：中序打印以你想象的节点为头的整棵树！
	public static void process(int i, int N, boolean down) {
		if(i > N) { // 超过了最大层数
			return;
		}
		process(i + 1, N, true);// 中序遍历先从左边开始，左边都是凹的，所以是true
		System.out.print(down? "凹" : "凸"); // 打印头
		process(i + 1, N, false);// 右边，右边都是凸的，所以down为false
	}
}
