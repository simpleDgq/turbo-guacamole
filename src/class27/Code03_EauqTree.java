package class27;

import java.util.ArrayList;


public class Code03_EauqTree {
	/**
	 * 给定两棵二叉树的头节点head1和head2，返回head1中是否有某个子树的结构和head2完全一样
	 * 
	 * 思路：
	 * 将两颗树都序列化成字符串str1和str2，用KMP比对，如果str2是str1的一部分，则返回true，表示head1有
		子树和head2完全一样。用先序序列化。后序也可以。
	 */
	
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}
	
	/**
	 * 暴力递归
	 */
	// head1 中是否有head2为头的子树
	public static boolean containsTree1(Node head1, Node head2) {
		if(head1 == null && head2 == null) {
			return true;
		}
		if(head1 == null) { // head2不为空
			return false;
		}
		if(head2 == null) { // head1不为空， head2为空
			return true;
		}
		// 都不为空，当前两颗树，判断是否是同一棵树, 如果是则返回true
		if(isSameTree(head1, head2)) { 
			return true;
		}
		// 否则去左树和右树找
		return containsTree1(head1.left, head2) || containsTree1(head1.right, head2); // 左树和右树是否有
	}
	
	// 判断以head1和head2为头的两棵树是否一样
	public static boolean isSameTree(Node head1, Node head2) {
		if(head1 == null && head2 == null) {
			return true;
		}
		if(head1 == null || head2 == null) { // 任意一个为空，一个不为空
			return false;
		}
		// 都不为空，如果值不相等
		if(head1.value != head2.value) {
			return false;
		}
		// 判断左树是否一样，右树是否一样
		return isSameTree(head1.left, head2.left) && 
				isSameTree(head1.right, head2.right);
	}
	
	/**
	 * 先序加KMP
	 */
	public static boolean containsTree2(Node head1, Node head2) {
		if(head1 == null && head2 == null) {
			return true;
		}
		if(head1 == null) {
			return false;
		}
		if(head2 == null) {
			return true;
		}
		
		ArrayList<String> str1 = preSerial(head1);
		ArrayList<String> str2 = preSerial(head2);
		String[] str1Arr = new String[str1.size()];
		for (int i = 0; i < str1.size(); i++) {
			str1Arr[i] = str1.get(i);
		}
		String[] str2Arr = new String[str2.size()];
		for (int i = 0; i < str2.size(); i++) {
			str2Arr[i] = str2.get(i);
		}

		return getIndexOf(str1Arr, str2Arr) == -1 ? false : true;
	}
	// 先序序列化
	public static ArrayList<String> preSerial(Node head) {
		ArrayList<String> ans = new  ArrayList<String>();
		if(head == null) {
			return ans;
		}
		pre(head, ans);
		return ans;
	}
	public static void pre(Node head, ArrayList<String> ans) {
		if(head == null) {
			ans.add(null);
			return;
		}
		ans.add(String.valueOf(head.value));
		pre(head.left, ans);
		pre(head.right, ans);
	}
	// KMP
	public static int getIndexOf(String[] str1, String[] str2) { // 注意这里是字符串数组
		if(str1 == null || str2 == null || str1.length == 0|| str2.length == 0 ||
				str2.length > str1.length) {
			return -1;
		}
		int x = 0;
		int y = 0;
		int next[] = getNextArray(str2);
		while(x <= str1.length - 1 && y <= str2.length - 1) {
			if(isEqual(str1[x],str2[y])) { // 判断两个字符串是否相等，注意处理null
				x++;
				y++;
			} else if(next[y] == -1) { // 或者写y==0也行
				x++;
			} else {
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}
	// 求next数组
	public static int[] getNextArray(String[] str2) {
		if(str2.length == 1) {
			return new int[] {-1};
		}
		int next[] = new int[str2.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while(i <= str2.length - 1) {
			if(isEqual(str2[i - 1],str2[cn])) { // 比较是否相等
				next[i++] = ++cn;
			} else if(next[cn] == -1) {
				next[i++] = 0;
			} else {
				cn = next[cn];
			}
 		}
		return next;
	}
	
	public static boolean isEqual(String str1, String str2) {
		if(str1 == null && str2 == null) {
			return true;
		}
		if(str1 == null || str2 == null) { // 一个为null，一个不是null
			return false;
		}
		// 都不是null
		return str1.equals(str2);
	}
	
	
	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int bigTreeLevel = 7;
		int smallTreeLevel = 4;
		int nodeMaxValue = 5;
		int testTimes = 100000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
			Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
			boolean ans1 = containsTree1(big, small);
			boolean ans2 = containsTree2(big, small);
			if (ans1 != ans2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

	}
}
