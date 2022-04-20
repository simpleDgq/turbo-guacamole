package class08;

import java.util.HashMap;

public class Code01_TrieTree {
	
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
	
	public Code01_TrieTree() {
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
			if(--node.nexts[index].pass == 0) { // 如果当前节点的下一个节点pass在--之后是0，说明没有边到达改节点，这个节点要删除
				node.nexts[index] = null; // 直接设置成null，java内存自动回收
				return;
			}
			node = node.nexts[index]; //例如， 有到达a的边，指向下一个节点，然后下一个节点的pass--
//			node.pass--;
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
	
	
	public static class Node2 {
		public int pass;
		public int end;
		public HashMap<Integer, Node2> nexts;

		public Node2() {
			pass = 0;
			end = 0;
			nexts = new HashMap<>();
		}
	}

	public static class Trie2 {
		private Node2 root;

		public Trie2() {
			root = new Node2();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			node.pass++;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					node.nexts.put(index, new Node2());
				}
				node = node.nexts.get(index);
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node2 node = root;
				node.pass--;
				int index = 0;
				for (int i = 0; i < chs.length; i++) {
					index = (int) chs[i];
					if (--node.nexts.get(index).pass == 0) {
						node.nexts.remove(index);
						return;
					}
					node = node.nexts.get(index);
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.pass;
		}
	}

	public static class Right {

		private HashMap<String, Integer> box;

		public Right() {
			box = new HashMap<>();
		}

		public void insert(String word) {
			if (!box.containsKey(word)) {
				box.put(word, 1);
			} else {
				box.put(word, box.get(word) + 1);
			}
		}

		public void delete(String word) {
			if (box.containsKey(word)) {
				if (box.get(word) == 1) {
					box.remove(word);
				} else {
					box.put(word, box.get(word) - 1);
				}
			}
		}

		public int search(String word) {
			if (!box.containsKey(word)) {
				return 0;
			} else {
				return box.get(word);
			}
		}

		public int prefixNumber(String pre) {
			int count = 0;
			for (String cur : box.keySet()) {
				if (cur.startsWith(pre)) {
					count++;
				}
			}
			return count;
		}
	}

	// for test
	public static String generateRandomString(int strLen) {
		char[] ans = new char[(int) (Math.random() * strLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			int value = (int) (Math.random() * 6);
			ans[i] = (char) (97 + value);
		}
		return String.valueOf(ans);
	}

	// for test
	public static String[] generateRandomStringArray(int arrLen, int strLen) {
		String[] ans = new String[(int) (Math.random() * arrLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = generateRandomString(strLen);
		}
		return ans;
	}

	public static void main(String[] args) {
		int arrLen = 100;
		int strLen = 20;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			String[] arr = generateRandomStringArray(arrLen, strLen);
			Code01_TrieTree trie1 = new Code01_TrieTree();
			Code01_TrieTree trie2 = new Code01_TrieTree();
			Right right = new Right();
			for (int j = 0; j < arr.length; j++) {
				double decide = Math.random();
				if (decide < 0.25) {
					trie1.insert(arr[j]);
//					trie2.insert(arr[j]);
					right.insert(arr[j]);
				} else if (decide < 0.5) {
					trie1.delete(arr[j]);
//					trie2.delete(arr[j]);
					right.delete(arr[j]);
				} else if (decide < 0.75) {
					int ans1 = trie1.search(arr[j]);
//					int ans2 = trie2.search(arr[j]);
					int ans3 = right.search(arr[j]);
					if (ans1 != ans3) {
						System.out.println("Oops!");
					}
				} else {
					int ans1 = trie1.searchPrefixNum(arr[j]);
//					int ans2 = trie2.searchPrefixNum(arr[j]);
					int ans3 = right.prefixNumber(arr[j]);
					if (ans1 != ans3 ) {
						System.out.println("Oops!");
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
