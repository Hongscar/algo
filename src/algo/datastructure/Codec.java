package algo.datastructure;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 16:14 2019/10/23
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return null;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        data = data.substring(1, data.length() - 1);        // 去掉中括号
        String[] elements = data.split(",");
        int prev = 0;
        if (elements.length == 0)
            return null;
        TreeNode root = new TreeNode(Integer.parseInt(elements[0]));
        if (elements.length == 1)
            return root;
        return null;
    }

    // prev是记录上一层非null的值的个数,懒得写了,TODO
    public TreeNode deserializeHelper(String data, int begin, int prev) {
        return null;
    }

    public static void main(String[] args) {

    }
}
