package temp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:48 2019/10/16
 */

public class MainClass {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

    public int pathSum(TreeNode root, int sum) {
        if (root == null)
            return 0;
        return helper(root, sum, 0) + helper(root, sum, root.val);
    }

    public int helper(TreeNode root, int sum, int current) {
        if (root == null || (root.left == null && root.right == null))
            return sum == current ? 1 : 0;
        int res = 0;
        if (current == sum)
            res = 1;

        if (root.left != null && root.right == null)
            return res + helper(root.left, sum, current + root.left.val) + helper(root.left, sum, 0);
        if (root.left == null && root.right != null)
            return res + helper(root.right, sum, current + root.right.val) + helper(root.right, sum, 0);
        return res + helper(root.left, sum, current + root.left.val) + helper(root.left, sum, 0) +
                helper(root.right, sum, current + root.right.val) + helper(root.right, sum, 0);

    }

    public static TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }

    public static String treeNodeToString(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        String output = "";
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (node == null) {
                output += "null, ";
                continue;
            }

            output += String.valueOf(node.val) + ", ";
            nodeQueue.add(node.left);
            nodeQueue.add(node.right);
        }
        return "[" + output.substring(0, output.length() - 2) + "]";
    }

    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public void flatten(TreeNode root) {
        if (root == null)
            return;
        TreeNode result = new TreeNode(root.val);
        treeDST(root.left, result);     // 因为result已经包含了root.val
        treeDST(root.right, result);    // 如果直接传root做参数,root值会出现两次
        root.left = null;
        root.right = result.right;
    }

    // 前序遍历
    public void treeDST(TreeNode treeNode, TreeNode result) {
        if (treeNode == null)
            return;
        while (result.right != null)
            result = result.right;      // 由于递归,当前的result不一定指向最右(比如left遍历完,再遍历right的时候)
        result.left = null;
        result.right = new TreeNode(treeNode.val);
        treeDST(treeNode.left, result);
        treeDST(treeNode.right, result);
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null)
            return null;
        ListNode middle = findMiddle(head);
        TreeNode result = new TreeNode(middle.val);
        if (head == middle)
            return result;          // 只剩下了1个结点,那么就不要再递归了,会陷入无限递归!
        result.left = sortedListToBST(head);
        result.right = sortedListToBST(middle.next);
        return result;
    }

    public ListNode findMiddle(ListNode head) {
        if (head == null)  // 如果只有0个结点,返回null
            return null;
        if (head.next == null)
            return head;    // 只有1个结点

        ListNode slow = head, fast = head, prev = head;  // 双指针寻找中点
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        if (prev != null)
            prev.next = null;   // 断开前一个结点与中点的连接,否则就是无限循环了
        return slow;    // 当fast指针到达结尾,slow指针就是到达了中点
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            TreeNode root = stringToTreeNode(line);
            line = in.readLine();
            int sum = Integer.parseInt(line);

            int ret = new MainClass().pathSum(root, sum);

            String out = String.valueOf(ret);

            System.out.print(out);
        }
    }
}
