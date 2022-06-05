package class31;

public class Code01_SegmentTree {
	
	public static class SegmentTree {
		// arr[]为原序列的信息从0开始，但在arr里是从1开始的
		// sum[]模拟线段树维护区间和
		// lazy[]为累加和懒惰标记
		// change[]为更新的值
		// update[]为更新慵懒标记
		private int MAXN;
		private int arr[];
		private int sum[];
		private int lazy[];
		private int change[];
		private boolean update[];
		
		public SegmentTree(int origin[]) {
			MAXN = origin.length + 1;
			arr = new int[MAXN]; // 0位置不用
			for(int i = 0; i <= origin.length - 1; i++) {
				arr[i + 1] = origin[i];
			}
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
			change = new int[MAXN << 2];
			update = new boolean[MAXN << 2];
		}
		
		// 任意一个根的累加和，左孩子和右孩子，累加得到
		public void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1]; 
		}
		
		// 在初始化阶段，先把sum数组，填好
		// 在arr[l~r]范围上，去build，1~N，
		// rt : 这个范围在sum中的下标
		public void build(int l, int r, int rt) {
			if(l == r) {
				sum[rt] = arr[l];
				return;
			}
			// L!=R, 分左右，先求中点
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1); //build 树的左边
			build(mid + 1, r, rt << 1 | 1); //build 树的右边
			pushUp(rt); //左右孩子一加，得到累加数组中rt位置的值
		}
		
		// L~R, C 表示当前任务！L~R范围每个值给我累加个C
		// rt，l~r 当前来到的是rt格子，范围是l到r
		public void add(int L, int R, int C, int l, int r, int rt) {
			// 任务如果把此时的范围全包了！
			if(L <= l && r <= R) { // 注意是L <= l
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			// 没有全包，求中点，划分，往下分发任务
			int mid = (l + r) >> 1;
			// 先下发老任务
			pushDown(rt, mid - l + 1, r - mid);
			// 处理新任务
			if(L <= mid) { // 注意
				add(L, R, C, l, mid, rt << 1);
			}
			if(R >= mid + 1) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			// 左边和右边的累加和都完成，得到自己的累加和
			pushUp(rt);
		}
		
		// 之前的，所有懒增加，和懒更新，从父范围，发给左右两个子范围
		// 分发策略是什么
		// rt是下发任务的父节点的下标
		// ln表示左子树元素结点个数，rn表示右子树结点个数
		public void pushDown(int rt, int ln, int rn) {
			// 先下发更新信息，再下发累加信息
			if(update[rt]) { // 老的懒更新任务存在，往下分发
				// 分发到左
				update[rt << 1] = true;
				change[rt << 1] = change[rt];
				// 分发到右
				update[rt << 1 | 1] = true;
				change[rt << 1 | 1] = change[rt];
				// 更新左边的累加和和lazy
				sum[rt << 1] = change[rt] * ln;
				lazy[rt << 1] = 0; // 左孩子保存的懒信息清空
				// 更新右边的累加和和lazy
				sum[rt << 1 | 1] = change[rt] * rn;
				lazy[rt << 1 | 1] = 0; // 右孩子保存的懒信息清空
				// 下发成功，父节点的懒更新清空
				update[rt] = false;
			}
			// 下发累加信息
			if(lazy[rt] != 0) { // 有老的懒增加存在，才往下发
				sum[rt << 1] += lazy[rt] * ln; // 左树上所有的节点都加一个父节点下发下来的懒增加信息
				sum[rt << 1 | 1] += lazy[rt] * rn;// 右树上所有的节点都加一个父节点下发下来的懒增加信息
				lazy[rt << 1] += lazy[rt]; // 孩子节点的懒增加信息加上父节点下发下来的
				lazy[rt << 1 | 1] += lazy[rt];
				lazy[rt] = 0; // 左右孩子已经承接了父节点的懒信息，清空父节点的懒信息
			}
		}
		
		// L~R, C 表示当前任务！L~R范围每个值给我累加个C
		// rt，l~r 当前来到的是rt格子，范围是l到r
		public void update(int L, int R, int C, int l, int r, int rt) {
			// 任务如果把此时的范围全包了！
			if(L <= l && r <= R) {
				change[rt] = C; // 要更新的值是C
				update[rt] = true;
				sum[rt] = C * (r - l + 1);
				lazy[rt] = 0;
				return;
			}
			// 没有全包，求中点，划分，往下分发任务
			int mid = (l + r) >> 1;
			// 先下发老任务
			pushDown(rt, mid - l + 1, r - mid);
			// 处理新任务
			if(L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if(R >= mid + 1) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			// 左边和右边的更新都完成，得到自己的累加和
			pushUp(rt);
		}
		
		// 1~6 累加和是多少？ 1~8 rt
		// 查询L~R上的累加和
		public long query(int L, int R, int l, int r, int rt) { 
			// 任务如果把此时的范围全包了！
			if(L <= l && r <= R) { 
				return sum[rt];
			}
			// 没有全包，求中点，划分，往下分发老的任务
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			// 处理新任务
			int ans = 0;
			if(L <= mid) { // 查询左侧累加和
				ans += query(L, R, l, mid, rt << 1);
			}
			if(R >= mid + 1) { // 查询右侧累加和
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}
	}
	
	public static class Right {
		public int[] arr;
		public Right(int[] origin) {
			arr = new int[origin.length + 1];
			for (int i = 0; i < origin.length; i++) {
				arr[i + 1] = origin[i];
			}
		}
		public void update(int L, int R, int C) {
			for (int i = L; i <= R; i++) {
				arr[i] = C;
			}
		}
		public void add(int L, int R, int C) {
			for (int i = L; i <= R; i++) {
				arr[i] += C;
			}
		}
		public long query(int L, int R) {
			long ans = 0;
			for (int i = L; i <= R; i++) {
				ans += arr[i];
			}
			return ans;
		}
	}
	
	public static int[] genarateRandomArray(int len, int max) {
		int size = (int) (Math.random() * len) + 1;
		int[] origin = new int[size];
		for (int i = 0; i < size; i++) {
			origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return origin;
	}

	public static boolean test() {
		int len = 100;
		int max = 1000;
		int testTimes = 5000;
		int addOrUpdateTimes = 1000;
		int queryTimes = 500;
		for (int i = 0; i < testTimes; i++) {
			int[] origin = genarateRandomArray(len, max);
			SegmentTree seg = new SegmentTree(origin);
			int S = 1;
			int N = origin.length;
			int root = 1;
			seg.build(S, N, root);
			Right rig = new Right(origin);
			for (int j = 0; j < addOrUpdateTimes; j++) {
				int num1 = (int) (Math.random() * N) + 1;
				int num2 = (int) (Math.random() * N) + 1;
				int L = Math.min(num1, num2);
				int R = Math.max(num1, num2);
				int C = (int) (Math.random() * max) - (int) (Math.random() * max);
				if (Math.random() < 0.5) {
					seg.add(L, R, C, S, N, root);
					rig.add(L, R, C);
				} else {
					seg.update(L, R, C, S, N, root);
					rig.update(L, R, C);
				}
			}
			for (int k = 0; k < queryTimes; k++) {
				int num1 = (int) (Math.random() * N) + 1;
				int num2 = (int) (Math.random() * N) + 1;
				int L = Math.min(num1, num2);
				int R = Math.max(num1, num2);
				long ans1 = seg.query(L, R, S, N, root);
				long ans2 = rig.query(L, R);
				if (ans1 != ans2) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
		SegmentTree seg = new SegmentTree(origin);
		int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
		int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
		int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
		int L = 2; // 操作区间的开始位置 -> 可变
		int R = 5; // 操作区间的结束位置 -> 可变
		int C = 4; // 要加的数字或者要更新的数字 -> 可变
		// 区间生成，必须在[S,N]整个范围上build
		seg.build(S, N, root);
		// 区间修改，可以改变L、R和C的值，其他值不可改变
		seg.add(L, R, C, S, N, root);
		// 区间更新，可以改变L、R和C的值，其他值不可改变
		seg.update(L, R, C, S, N, root);
		// 区间查询，可以改变L和R的值，其他值不可改变
		long sum = seg.query(L, R, S, N, root);
		System.out.println(sum);

		System.out.println("对数器测试开始...");
		System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
	}


}
