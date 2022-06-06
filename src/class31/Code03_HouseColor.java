package class31;

public class Code03_HouseColor {
	/**
	 * 有1~N这么多房子, 有56种颜色, 实现如下操作:
		1) L..R这些房子统一刷成某一个颜色
		可以随意调用1) 的方法, 返回 L..R范围上,不同的颜色有多少种?
	 */
	/**
	 * update 和 query结合的线段树
	 * 
	 * 用一个long类型, 64位表示颜色出没出现。
	 * 每个颜色只有一位是1。
	 * 范围上的颜色信息, 左边的  或上  右边的
	 */
	
	public static class SegmentTree {
		private long color[];
		private long change[];
		private boolean update[];
		
		public SegmentTree(int N) {
			N = N + 1; // 0位置不用
			color = new long[N << 2];
			change = new long[N << 2];
			update = new boolean[N << 2];
		}
		
		public void pushUp(int rt) {
			color[rt] = color[rt << 1] | color[rt << 1 | 1];
		}
		
		public void pushDown(int rt) {
			// 下发更新任务
			if(update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				
				color[rt << 1] = change[rt];
				color[rt << 1 | 1] = change[rt];
				
				update[rt] = false;
			}
		}
		
		public void update(int L, int R, long C, int l, int r, int rt) {
			if(L <= l && R >= r) { // 全包
				update[rt] = true;
				change[rt] = C;
				color[rt] = C;
				return;
			}
			// 求中点
			int mid = (l + r) >> 1;
			// 下发任务
			pushDown(rt);
			if(L <= mid) { // update左
				update(L, R, C, l, mid, rt << 1);
			}
			if(R >= mid + 1) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			// 左右求完，求父
			pushUp(rt);
		}
		
		public long query(int L, int R, int l, int r, int rt) {
			if(L <= l && R >= r) { // 全包
				return color[rt];
			}
			// 求中点
			int mid = (l + r) >> 1;
			// 下发任务
			pushDown(rt);
			int ans = 0;
			if(L <= mid) { // update左
				ans |= query(L, R,  l, mid, rt << 1);
			}
			if(R >= mid + 1) {
				ans |= query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}
	}
	
	public static void main(String args[]) {
		int N = 3;
		
		SegmentTree seg = new SegmentTree(N);
		seg.update(1, 1, 1, 1, N, 1); // 1~1 刷成0001 。 每个颜色只有一位是1
		seg.update(2, 3, 2, 1, N, 1); // 2~3 刷成0010
		seg.update(2, 2, 4, 1, N, 1); // 2~2 刷成0100
		seg.update(3, 3, 8, 1, N, 1); // 3~3 刷成1000
		seg.update(4, 4, 16, 1, N, 1); // 4~6 刷成10000
		
		long color = seg.query(1, 4, 1, N, 1);
		int ans = 0;
		while(color != 0) {
			ans += color & 1;
			color >>= 1;
		}
		System.out.print(ans);
	}
	
}
