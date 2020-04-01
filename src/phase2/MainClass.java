package phase2;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 21:02 2020/3/30
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class MainClass {
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
        while(!nodeQueue.isEmpty()) {
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

    public static String integerArrayListToString(List<Integer> nums, int length) {
        if (length == 0) {
            return "[]";
        }

        String result = "";
        for(int index = 0; index < length; index++) {
            Integer number = nums.get(index);
            result += Integer.toString(number) + ", ";
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static String integerArrayListToString(List<Integer> nums) {
        return integerArrayListToString(nums, nums.size());
    }

    public static String int2dListToString(List<List<Integer>> nums) {
        StringBuilder sb = new StringBuilder("[");
        for (List<Integer> list: nums) {
            sb.append(integerArrayListToString(list));
            sb.append(",");
        }

        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Map<TreeNode, int[]> positions = new HashMap<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        positions.put(root, new int[] {0, 0});
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (size != 0)
                res.add(new ArrayList<>()); // 一层一个List
            while (size > 0) {
                TreeNode current = queue.poll();
                int[] pos = positions.get(current);
                if (current.left != null) {
                    queue.add(current.left);
                    positions.put(current.left, new int[] {pos[0] - 1, pos[1] + 1});
                }
                if (current.right != null) {
                    queue.add(current.right);
                    positions.put(current.right, new int[] {pos[0] + 1, pos[1] + 1});
                }
                size--;
            }
        }
        List<int[]> list = new ArrayList<>();
        for (Map.Entry<TreeNode, int[]> entry: positions.entrySet()) {
            int[] value = entry.getValue();
            int[] tmp = new int[] {value[0], value[1], entry.getKey().val};
            list.add(tmp);
        }
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int x = o1[0] - o2[0];
                if (x != 0)
                    return x;
                int y = o1[1] - o2[1];
                if (y != 0)
                    return y;
                return o1[2] - o2[2];
            }
        });
        for (int[] ints: list) {
            List<Integer> currentList = res.get(ints[0]);
            currentList.add(ints[2]);
            res.set(ints[1], currentList);
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            TreeNode root = stringToTreeNode(line);

            List<List<Integer>> ret = new MainClass().verticalTraversal(root);

            String out = int2dListToString(ret);

            System.out.print(out);
        }
    }
}