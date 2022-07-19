package class43;

import java.util.ArrayList;

public class Code02_TSP {
	
	public static int tsp1(int martix[][]) {
		if(martix == null || martix.length == 0) {
			return Integer.MAX_VALUE;
		}
		
		int N = martix.length;
		/*
		 * set.get(i) != null 说明i号城在集合里面
		 * set.get(i) == null 说明i号城不在集合里面
		 */
		ArrayList<Integer> set = new ArrayList<Integer>();
		for(int i = 0; i <= N - 1; i++) {
			set.add(1); // 刚开始所有的城都在集合里面
		}
		
		return process(martix, set, 0);
	}
	
	// 任何两座城市之间的距离，可以在matrix里面拿到
	// set中表示着哪些城市的集合，
	// start这座城一定在set里，
	// 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
	public static int process(int martix[][], ArrayList<Integer> set, int start) {
		
		int cityNumber = 0;
		for(Integer item : set) {
			if(item != null) {
				cityNumber++;
			}
		}
		if(cityNumber == 1) { // 当集合中只有一座城市的时候，直接返回从最后一个城市到0城市的距离
			return martix[start][0];
		}
		
		set.set(start, null); // start从集合中移除
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < set.size(); i++) { // set集合中剩余的每座城市开头，都去尝试
			if(set.get(i) != null) {
				int cur = martix[start][i] + process(martix, set, i); // start 到一个 i， 下一步就是i开始，串联集合中的所有城市
				min = Math.min(min, cur);
			}
		}
		// 恢复现场
		set.set(start, 1);
		return min;
	}
	
	
	/**
	 * set突破了整型，set中的元素表示的是该城市存在和不存在
	 * 可以用一个int的每一位来表示
	 * 1 表示存在
	 * 0表示不存在
	 * 
	 */
	public static int tsp2(int martix[][]) {
		if(martix == null || martix.length == 0) {
			return Integer.MAX_VALUE;
		}
		
		int N = martix.length;
		
		int cityStatus = (1 << N) - 1;
		return process1(martix, cityStatus, 0);
	}
 	
	// 任何两座城市之间的距离，可以在matrix里面拿到
	// set中表示着哪些城市的集合，
	// start这座城一定在set里，
	// 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
	public static int process1(int martix[][], int cityStatus, int start) {
		if(cityStatus == (cityStatus & (~cityStatus + 1))) { // 取出最右侧的1，如果和当前元素相等，则表示只有一个1，只有一个城市
			return martix[start][0];
		}
		
		cityStatus &= ~(1 << start);// 移除start，标0
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < martix.length; i++) { // 城市数量
			if((cityStatus & (1 << i)) != 0) {
				int cur = martix[start][i] + process1(martix, cityStatus, i); // start 到一个 i， 下一步就是i开始，串联集合中的所有城市
				min = Math.min(min, cur);
			}
		}
		// 恢复现场
		cityStatus |= 1 << start;
		return min;
	}
	
	
	/**
	 * 两个可变参数cityStatus和start
	 * cityStatus: 0 ~ （1 << N) - 1
	 * start: 0 ~ N - 1
	 * 
	 * ==> dp[1 << N][N]
	 * 
	 * 傻缓存，如果dp不是-1，表示已经计算过，直接返回，否则去计算
	 */
	public static int tsp3(int martix[][]) {
		if(martix == null || martix.length == 0) {
			return Integer.MAX_VALUE;
		}
		
		int N = martix.length;
		int cityStatus = (1 << N) - 1;
		int dp[][] = new int[cityStatus + 1][N];
		
		//如果dp不是-1，表示已经计算过，直接返回，否则去计算
		for(int i = 0; i <= cityStatus; i++) {
			for(int j = 0; j <= N - 1; j++) {
				dp[i][j] = -1;
			}
		}
		
		return process2(martix, cityStatus, 0, dp);
	}
	
	public static int process2(int martix[][], int cityStatus, int start, int[][] dp) {
		if(dp[cityStatus][start] != -1) { // 有答案，直接缓存拿
			return dp[cityStatus][start];
		}
		if(cityStatus == (cityStatus & (~cityStatus + 1))) { // 取出最右侧的1，如果和当前元素相等，则表示只有一个1，只有一个城市
			dp[cityStatus][start] = martix[start][0];
			return martix[start][0];
		}
		
		cityStatus &= ~(1 << start);// 移除start，标0
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < martix.length; i++) { // 城市数量
			if((cityStatus & (1 << i)) != 0) {
				int cur = martix[start][i] + process2(martix, cityStatus, i, dp); // start 到一个 i， 下一步就是i开始，串联集合中的所有城市
				min = Math.min(min, cur);
			}
		}
		// 恢复现场
		cityStatus |= 1 << start;
		dp[cityStatus][start] = min;
		
		return dp[cityStatus][start];
	}
	
	
	public static int[][] generateGraph(int maxSize, int maxValue) {
		int len = (int) (Math.random() * maxSize) + 1;
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * maxValue) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		return matrix;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		System.out.println("功能测试开始");
		for (int i = 0; i < 200; i++) {
			int[][] matrix = generateGraph(len, value);
//			int origin = (int) (Math.random() * matrix.length);
			int ans1 = tsp1(matrix);
			int ans2 = tsp2(matrix);
			int ans3 = tsp3(matrix);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("fuck");
			}
		}
		System.out.println("功能测试结束");

		len = 22;
		System.out.println("性能测试开始，数据规模 : " + len);
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * value) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		long start;
		long end;
		start = System.currentTimeMillis();
		tsp1(matrix);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}


}
