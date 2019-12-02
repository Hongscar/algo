package algo.datastructure;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 10:02 2019/11/30
 */
// PS: 可以用数组代替List, 因为此处只存储字母, 直接开辟一个26长度的数组即可
    // 然后 a[0]代表a, a[1]代表b, a[25]代表z. (0表示不存在, 1表示存在即可) 这样无须任何遍历,更加高效.
class Trie {
    class Node {
        public int val;
        private List<Node> children;
        public boolean isLeaf;          // 用于记录当前结点是否为叶子(否贼先添加"abc",再添加"a",这时候无法判断"a"是否为叶子)
        Node(int val) {
            this.val = val;
            isLeaf = false;
        }
    }

    private Node root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new Node(0);         // represent the root element
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node current = root;
        // boolean flag = false;               // 如果其中一层新insert,后面必定全部都要新insert
        int length = word.length();
        for (int j = 0; j < length; j++) {
            char c = word.charAt(j);
            List<Node> list = current.children;         // List用于遍历当前层结点的子结点
            int size = list == null ? 0 : list.size();  // 如果为空, 要特殊处理, 否则NullPointer
            for (int i = 0; i <= size; i++) {
                if (size != 0 && list.get(i).val == c) {
                    current = list.get(i);
                    break;              // exists
                }
                if (i == size - 1 || size == 0) {
                    Node node = new Node(c);
                    if (size == 0) {
                        list = new ArrayList<>();           // List为空的情况, 重新new一个新的List
                        list.add(node);
                        current.children = new ArrayList<>(list);
                    }
                    else {
                        list.add(node);
                        current.children = new ArrayList<>(list);
                    }
                    current = node;
                    break;
                }
            }
            if (j == length - 1)
                current.isLeaf = true;
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return searchHelper(word, false);
        // flag 为false, 表示search, 找到最后还要判断ifLeaf.为true则表示startsWith, 无须判断ifLeaf
    }

    public boolean searchHelper(String word, boolean flag) {
        Node current = root;
        int curr = 0;
        List<Node> list = current.children;
        int size = list == null ? 0 : list.size();
        for (char c: word.toCharArray()) {
            for (int i = 0; i <= size; i++) {
                if (size == 0)
                    return false;
                Node temp = list.get(i);
                if (temp.val == c) {
                    current = temp;
                    list = current.children;
                    size = list == null ? 0 : list.size();
                    curr++;
                    break;
                }
                if (i == size - 1)
                    return false;
            }
        }
        return flag || current.isLeaf;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return searchHelper(prefix, true);
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("b");
        System.out.println(trie.search("abc"));
        trie.insert("abc");
        System.out.println(trie.search("abc"));
        trie.insert("abd");
        System.out.println(trie.search("abd"));
        System.out.println(trie.search("bc"));

//        trie.insert("apple");
//        System.out.println(trie.search("apple"));   // 返回 true
//        System.out.println(trie.search("app"));     // 返回 false
//        trie.startsWith("app"); // 返回 true
//        trie.insert("app");
//        trie.search("app");     // 返回 true
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
