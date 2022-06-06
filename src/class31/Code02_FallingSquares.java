package class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Code02_FallingSquares {
	/**
	 * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
		下面是这个游戏的简化版：
		1）只会下落正方形积木
		2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
		3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
		4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
		给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
		返回每一次掉落之后的最大高度
	 */
	/**
	 *  每个方块掉落，都会占一定的线段范围，每次掉落求当前的最大值、
	 *  在掉落之前先query出当前范围的最大值，然后加上下落的高度得到新的height，更新当前范围的最大高度为height
	 *  
	 *  max + query + update的线段树
	 */
	public class SegmentTree {
		private int max[];
		private int change[];
		private boolean update[];
		
		public SegmentTree(int size) {
			int N = size + 1; // 0位置不用
			max = new int[N << 2];
			change = new int[N << 2];
			update = new boolean[N << 2];
		}
		
		public void update(int L, int R, int height, int l, int r, int rt) {
			if(L <= l && R >= r) { // 全包
				max[rt] = height;
				change[rt] = height;
				update[rt] = true;
				return;
			}
			// 没有全包
			int mid = (l + r) >> 1;
			// 下发任务
			pushDown(rt);
			// 处理新任务
			if(L <= mid) {
				update(L, R, height, l, mid, rt << 1);
			}
			if(R >= mid +1) {
				update(L, R, height, mid + 1, r, rt << 1 | 1);
			}
			// 左右孩子都算好了，计算父rt
			pushUp(rt);
		}
		
		public void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}
		
		public void pushDown(int rt) {
			if(update[rt]) {
				// 下发任务到左右
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				max[rt << 1] = change[rt]; // 懒更新信息是高度最大值，下发的子节点
				max[rt << 1 | 1] = change[rt];
				update[rt] = false;
			}
		}
		
		public int query(int L, int R, int l, int r, int rt) {
			if(L <= l && R >= r) { // 全包
				return max[rt];
			}
			// 没有全包
			int mid = (l + r) >> 1;
			// 下发任务
			pushDown(rt);
			// 处理新任务
			int left = 0;
			int right = 0;
			if(L <= mid) {
				left = query(L, R, l, mid, rt << 1);
			}
			if(R >= mid +1) {
				right = query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return Math.max(left, right);
		}
	}
	
	
	public HashMap<Integer, Integer> index(int[][] positions) {
		TreeSet<Integer> pos = new TreeSet<>(); // set元素唯一，会去重
		for (int[] arr : positions) {
			pos.add(arr[0]);
			pos.add(arr[0] + arr[1] - 1);
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		int count = 0;
		for (Integer index : pos) {
			map.put(index, ++count);
		}
		return map;
	}
	
	public List<Integer> fallingSquares(int[][] positions) { 
		HashMap<Integer, Integer> map = index(positions);
		int N = map.size();
		SegmentTree seg = new SegmentTree(N);
		List<Integer> res = new ArrayList<>();
		int max = 0;
		int height = 0;
		// 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
		for(int[] arr : positions) {
			int L = map.get(arr[0]);
			int R = map.get(arr[0] + arr[1] - 1);
			// query 当前范围最大值, 当前范围高度最大值加上方块高度，得到新的这个范围上的最大高度, 
			height = seg.query(L, R, 1, N, 1) + arr[1]; // 根节点是1，范围从1~N
			// 更新最大高度
			seg.update(L, R, height, 1, N, 1);
			// 收集最大值
			max = Math.max(max, height);
			res.add(max);
		}
		return res;
	}
}
