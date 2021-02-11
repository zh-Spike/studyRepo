
import java.util.HashSet;

public class TrieTree {

	public static class TrieNode {
		public int pass;
		public int end;
		// HashMap<Char, Node> nexts;
		// TreeMap<Char, Node> nexts;
		public TrieNode[] nexts;

		public TrieNode() {
			pass = 0;
			end = 0;
			// nexts[0] == null 没有走向‘a’的路
			// nexts[0] != null 有走向‘a’的路
			// ...
			// nexts[25] != null 有走向‘z’的路
			nexts = new TrieNode[26];
		}
	}

	public static class Trie {
		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] chs = word.toCharArray();
			TrieNode node = root;
			node.pass++;
			int index = 0;
			for (int i = 0; i < chs.length; i++) { // 从左往右遍历字符
				index = chs[i] - 'a'; // 由字符，对应成走向哪条路
				if (node.nexts[index] == null) {
					node.nexts[index] = new TrieNode();
				}
				node = node.nexts[index];
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) { // 确定树中确实加入过word，才删除
				char[] chs = word.toCharArray();
				TrieNode node = root;
				node.pass--;
				int index = 0;
				for (int i = 0; i < chs.length; i++) {
					index = chs[i] - 'a';
					if (--node.nexts[index].pass == 0) {
						// java C++ 要遍历到底去析构
						node.nexts[index] = null;
						// ...
						return;
					}
					node = node.nexts[index];
				}
				node.end--;
			}
		}

		public void deleteCPP(String word) {
			if (search(word) != 0) { // 确定树中确实加入过word，才删除
				char[] chs = word.toCharArray();
				TrieNode node = root;
				node.pass--;
				int index = 0;

				TrieNode a = null;
				int deleteIndex = -1;

				HashSet<TrieNode> deleteSet = new HashSet<>();

				for (int i = 0; i < chs.length; i++) {
					index = chs[i] - 'a';
					if (--node.nexts[index].pass == 0) {
						a = a == null ? node : a;
						deleteIndex = deleteIndex == -1 ? index : deleteIndex;

						deleteSet.add(node.nexts[index]);
					}
					node = node.nexts[index];
				}
				node.end--;
				
				a.nexts[deleteIndex] = null;
				
				// deleteSet ... 析构

			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			TrieNode node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			TrieNode node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.pass;
		}
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		System.out.println(trie.search("zuo"));
		trie.insert("zuo");
		System.out.println(trie.search("zuo"));
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.insert("zuo");
		trie.insert("zuo");
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.insert("zuoa");
		trie.insert("zuoac");
		trie.insert("zuoab");
		trie.insert("zuoad");
		trie.delete("zuoa");
		System.out.println(trie.search("zuoa"));
		System.out.println(trie.prefixNumber("zuo"));

	}

}
