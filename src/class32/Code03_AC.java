package class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code03_AC {
	/**
	 * AC自动机
	 * 大文章中找敏感词
	 */
	// 前缀树节点
	 public static class Node {
		 private String end; // 如果是敏感词的结尾，那这个节点的end就是该敏感词，否则就是null
		 private boolean endUse; // 搜集过的敏感词，记为true，防止重复搜集
		 
		 private Node nexts[];
		 private Node fail;
		 
		 public Node() {
			 this.end = null;
			 this.endUse = false;
			 this.fail = null;
			 this.nexts = new Node[26];
		 }
	 }
	 
	 // AC自动机
	 public static class ACAutomation {
		 // 前缀树根节点
		 private Node root;
		 
		 public ACAutomation() {
			 this.root = new Node();
		 }
		 
		 // 建立前缀树
		 public void insert(String s) {
			 char[] str = s.toCharArray();
			 // 每一个字符串来了，都是从root出发开始建前缀树
			 Node cur = root;
			 for(int i = 0; i <= str.length - 1; i++) {
				 int index = str[i] - 'a'; // 节点存在边上
				 if(cur.nexts[index] == null) { // 没有到该字符的边，建立
					 cur.nexts[index] = new Node();
				 }
				 // 到下一个节点
				 cur = cur.nexts[index];
			 }
			 // 一个字符串处理完了，end赋值为当前字符串
			 cur.end = s;
		 }
		 
		 // 建立fail指针
		 // 层次遍历过程中，依次建立好fail指针
		 public void build() {
			 Queue<Node> queue = new LinkedList<Node>();
			 queue.add(root); // 父节点入队
			 
			 Node cur = null;
			 Node cFail = null;
			 while(!queue.isEmpty()) {
				 cur = queue.poll();
				 for(int i = 0; i <= 25; i++) { // 检查一个节点所有的26条路，将下一层所有节点的fail指针都建立好
					 if(cur.nexts[i] != null) {
						 cur.nexts[i].fail = root; // 刚开始都指向root，后面检查是否有
						 cFail = cur.fail; // 跳到父节点的fail指针指向的节点
						 while(cFail != null) { // 父节点的fail指针不指向null
							 if(cFail.nexts[i] != null) { // 看父节点的fail指针指向的节点是否有走向当前节点的路，有的话，指向有
								 cur.nexts[i].fail = cFail.nexts[i];
								 break; // 找到break
							 }
							 cFail = cFail.fail; // 没有继续往上
						 }
						 queue.add(cur.nexts[i]);
					 } 
				 }
			 }
		 }
		 
		 // 查大文章含有的敏感字符串
		 public List<String> containsWords(String content) {
			 ArrayList<String> ans = new ArrayList<String>();
			 
			 char[] str = content.toCharArray();
			 Node cur = root;
			 Node follow = null;
			 for(int i = 0; i <= str.length - 1; i++) {
				 int index = str[i] - 'a';
				// 如果当前字符在这条路上没配出来，就随着fail方向走向下条路径
				 while(cur.nexts[index] == null && cur != root) {
					 cur = cur.fail;
				 }
				 
				 cur = cur.nexts[index] != null? cur.nexts[index] : root;
				 follow = cur;
				 // follow 转一圈，搜集答案
				 while(follow != root) {
					 if(follow.endUse) { // 搜集过答案，不再手机
						 break;
					 }
					 if(follow.end != null) {
						 ans.add(follow.end); // 搜集答案
						 follow.endUse = true; // 标记
					 }
					 follow = follow.fail;
				 }
			 }
			 return ans;
		 }
	 }
	 
	 public static void main(String[] args) {
			ACAutomation ac = new ACAutomation();
			ac.insert("dhe");
			ac.insert("he");
			ac.insert("abcdheks");
			// 设置fail指针
			ac.build();

			List<String> contains = ac.containsWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
			for (String word : contains) {
				System.out.println(word);
			}
		}
}
