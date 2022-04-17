package Class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_CoverMax {
	
	/**
	 * 给定很多线段，每个线段都有两个数[start, end]，
     * 表示线段开始位置和结束位置，左右都是闭区间
		规定：
			1）线段的开始和结束位置一定都是整数值
			2）线段重合区域的长度必须>=1

			每个重合区域，一定是包含有几条线段的，求包含的线段数的最大值
	*
	*思路: 
	* 每一个重合区域的左边界一定是某个线段的左边界。
	* 对于每一个可能得左边界，得到有多少个线段穿过它的，就能得到改左边界有多少条线段。
	* 
	* 1. 将所有的线段按左边界进行排序
	* 2. 依次遍历排好序的线段，弄一个堆，如果堆中有数是<=当前被处理的线段的左边界的，那么直接弹出
	* 然后将线段的右边界加入到堆中
	* 3. 看堆中有几个数，那么久说明有几个线段是穿过左边界的，也就是对于改左边界而言，有几个线段与它有重合区
	* 4. 算出所有的左边界，可能得线段数，取最大值
	 */
	
	/**
	 * 线段类
	 */
	public static class Line {
		private int start;
		private int end;
		
		public Line(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	
	public static class LineCompar implements Comparator<Line> {
		@Override
		public int compare(Line o1, Line o2) {
			// TODO Auto-generated method stub
			return o1.start - o2.start;
		}
		
	}
	
	public static int coverMax(int arr[][]) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		
		ArrayList<Line> lines = new ArrayList<Line>();
		for(int i = 0; i <= arr.length - 1; i++) {
			lines.add(new Line(arr[i][0], arr[i][1]));
		}
		// 将线段按左边界排序
		lines.sort(new LineCompar()); // 自定义比较器进行排序
		
		// 对于每一个数，当成重合区域的左边界，判断有哪些线段穿过它
		// 
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		int max = 0;
		for(int j = 0; j <= lines.size() - 1; j++) {
			while(!heap.isEmpty() && heap.peek() <= lines.get(j).start ) { // 堆中小于等于start的(说明以堆中被弹出的数结尾的线段不会穿过当前start)，全部弹出
				heap.poll();
			}
			heap.add(lines.get(j).end); // 加入当前数的右边界到堆中
			max = Math.max(max, heap.size()); // 堆中还剩的数就是有多少个线段穿过当前节点的左边界，每次求最大值，保存下来
		}
		return max;
	}
}
