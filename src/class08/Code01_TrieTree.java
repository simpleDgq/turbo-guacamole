package class08;

import java.util.HashMap;

public class Code01_TrieTree {
	
	public static class Trietree1 {
		private TrieNode root; // 根节点
		/**
		 * 前缀树。
		 * 对于一个字符串数组，可以建立一颗前缀树，，树的每一条边是字符串的某一个字符，表示有没有到改字符的路。
		 */
		
		public class TrieNode {
			private int pass;
			private int end;
			private TrieNode nexts[]; // 
			
			public TrieNode() {
				this.pass = 0; // 在构建前缀树的过程中，该节点到达了几次
				this.end = 0; // 有多少个字符串是以当前节点结尾的
				
				/**
				 * // 如果字符种类很少，比如26个英文数组，就可以用这种方法
				 * 0位置，表示的事有没有a的边
				 * 1位置表示的事有没有到b的边
				 * ...
				 * 
				 * 当前字符 - 'a' 就能得到数组下标，例如当前字符是b，b - a 是1，TrieNode[1] 就表示有没有到b的边。
				 * 
				 * 如果TrieNode[i] = null, 则表示当前节点不存在到达当前字符的边，
				 * TrieNode[i] != null, 则表示存在
				 */
				this.nexts = new TrieNode[26]; 
			}
		}
		
		public Trietree1() {
			this.root = new TrieNode();
		}
		
	
		public void insert(String word) {
			if(word == null) {
				return;
			}
			char charArr[] = word.toCharArray();
			// 每个字符串，都是从头结点开始
			// 对于cahrArr中的每一个字符进行遍历，判断从当前节点开始有没有到达改字符的边
			TrieNode node = root;
			node.pass++; // 字符串进来，说明就有字符串经过了头节点，pass++
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = charArr[i] - 'a';
				if(node.nexts[index] == null) { // 没有到达该字符的边，新建一个节点出来
					node.nexts[index] = new TrieNode();
				}
				node = node.nexts[index];
				node.pass++;
			}
			node.end++;
		}
		
		
		/**
		 * 依次遍历每个字符，将对应节点的p--，
		 * 当某个节点的下一个节点的p--之后，变成0，说明改节点不能到达了，需要删掉，直接将当前节点的next变成null。
		 * 当到达了最后一个节点，end--
		 * @param word
		 */
		public void delete(String word) {
			if(word == null) {
				return;
			}
			if(search(word) == 0) { //说明字符串不存在，不需要删除
				return;
			}
			char charArr[] = word.toCharArray();
			TrieNode node = root;
			node.pass--;
			for(int i = 0; i <= charArr.length - 1; i++) { 
				int index = charArr[i] - 'a';
				if(--node.nexts[index].pass == 0) { // 如果当前节点的下一个节点pass在--之后是0，说明没有边到达改节点，这个节点要删除。 注意：这一步pass已经--了
					node.nexts[index] = null; // 直接设置成null，java内存自动回收
					return;
				}
				node = node.nexts[index]; //例如， 有到达a的边，指向下一个节点
			}
			node.end--; // 最后end--
		}
		
		/**
		 * 查找一个字符串出现了几次
		 * @return
		 */
		public int search(String word) {
			if(word == null) {
				return 0;
			}
			// 对于字符串的每一个字符，在前缀树中,从根节点开始找有没有到达的边
			char charArr[] = word.toCharArray();
			TrieNode node = root;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = charArr[i] - 'a';
				if(node.nexts[index] == null) { // 没有到某个字符的边，直接返回0
					return 0;
				}
				node = node.nexts[index]; // 继续判断下一个节点有没有到下一个字符的边
			}
			return node.end; // 返回end。就是字符串出现的次数
		}
		
		/**
		 * 查找以某个字符串作为前缀的字符串出现了几次
		 * @return
		 */
		public int searchPrefixNum(String prefix) {
			if(prefix == null) {
				return 0;
			}
			// 对于前缀字符串的每一个字符，在前缀树中,从根节点开始找有没有到达的边
			// 当搜索完最后一个字符的时候，所在的节点p值，就是答案（因为p表示的事该节点到达了几次）
			char charArr[] = prefix.toCharArray();
			TrieNode node = root;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = charArr[i] - 'a';
				if(node.nexts[index] == null) { // 没有到某个字符的边，直接返回0
					return 0;
				}
				node = node.nexts[index]; // 继续判断下一个节点有没有到下一个字符的边
			}
			return node.pass; // 返回pass。就是字符串出现的次数
		}
	}
	
	public static class TrieTree2 {
		
		private TrieNode2 root;
		
		public TrieTree2() {
			this.root = new TrieNode2();
		}
		/**
		 * 当字符种类比较多的时候，借助hashMap，key是
		 * 每个字符代表的整数值
		 * 则hashmap中的value数表示的事有没有到某个对应字符的边
		 * @author gqdeng
		 *
		 */
		public class TrieNode2 {
			private int pass;
			private int end;
			private HashMap<Integer, TrieNode2> nexts; 
			
			public TrieNode2() {
				this.pass = 0; // 在构建前缀树的过程中，该节点到达了几次
				this.end = 0; // 有多少个字符串是以当前节点结尾的
				this.nexts = new HashMap<Integer, TrieNode2>(); 
			}
		}
		
		public void insert2(String word) { 
			if(word == null) {
				return;
			}
			char charArr[] = word.toCharArray();
			TrieNode2 node = this.root;
			node.pass++;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = (int)charArr[i];
				if(node.nexts.get(index) == null) {
					node.nexts.put(index, new TrieNode2());
				}
				node = node.nexts.get(index);
				node.pass++;
			}
			node.end++;
		}
		
		public void delete2(String word) { 
			if(word == null) {
				return;
			}
			if(search2(word) == 0) {
				return;
			}
			char charArr[] = word.toCharArray();
			TrieNode2 node = this.root;
			node.pass--;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = (int)charArr[i];
				if(--node.nexts.get(index).pass == 0) {
					node.nexts.put(index, null);
					return;
				}
				node = node.nexts.get(index);
			}
			node.end--;
		}
		
		public int search2(String word) { 
			if(word == null) {
				return 0;
			}
			char charArr[] = word.toCharArray();
			TrieNode2 node = this.root;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = (int)charArr[i];
				if(node.nexts.get(index) == null) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.end;
		}
		
		public int searchPrefixNum2(String prefix) { 
			if(prefix == null) {
				return 0;
			}
			char charArr[] = prefix.toCharArray();
			TrieNode2 node = this.root;
			for(int i = 0; i <= charArr.length - 1; i++) {
				int index = (int)charArr[i];
				if(node.nexts.get(index) == null) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.pass;
		}
	}
}