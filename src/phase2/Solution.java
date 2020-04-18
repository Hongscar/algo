package phase2;

import javafx.util.Pair;

import java.util.*;

/**
 * @Author: Seth
 * @Description:    阶段2, 一题多解, 每一题要记录学到了什么, 题型考点是什么？ 不能仅仅满足于AC
 * @Date: Created in 10:28 2020/2/10
 */

public class Solution {

    /********************************************/
    // 5333 制造字母异位词的最小步骤数
    // https://leetcode-cn.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
    // 方法1, 最简单的, 计算两个字符串的频率, 然后求差即可, 值得注意的是, 当t的某个字符频次比s更大时
    //  会导致tmp为负数, 此时应该加0, 而不是加一个负数.    78ms
    public int minSteps(String s, String t) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        for (char c: s.toCharArray())
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        for (char c: t.toCharArray())
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        int res = 0;
        for (Map.Entry<Character, Integer> entry: map1.entrySet()) {
            char c = entry.getKey();
            int v = entry.getValue();
            int tmp = v - map2.getOrDefault(c, 0);
            res += tmp <= 0 ? 0 : tmp;
        }
        return res;
    }

    // 解法2, 每次想到频率, 最先想到的会是HashMap, 但实际上, 此题规定了字符串只包含小写字母
    // 所以可以用数组来代替Map的操作, 效率会大大的提高.以后看到计算频率, 不要忘记考虑是否可以用数组  14ms
    public int minSteps2(String s, String t) {
        int[] tmp1 = new int[26];
        int[] tmp2 = new int[26];
        for (char c: s.toCharArray())
            tmp1[c - 'a']++;
        for (char c: t.toCharArray())
            tmp2[c - 'a']++;
        int res = 0;
        for (int i = 0; i < 26; i++) {
            int tmp = tmp1[i] - tmp2[i];
            res += tmp <= 0 ? 0 : tmp;
        }
        return res;
    }

    /********************************************/
    public String minWindow(String s, String t) {
        int[] map1 = new int[58];
        int[] map2 = new int[58];
        String current = s;
        boolean flag = false;
        for (char c: t.toCharArray())
            map2[c - 'A']++;
        int left = 0, right = 0;
        while (right < s.length()) {
            map1[s.charAt(right) - 'A']++;
            right++;
            while (helper(map1, map2)) {
                flag = true;
                if (current.length() > (right - left))
                    current = s.substring(left, right);
                map1[s.charAt(left++) - 'A']--;
            }
        }
        if (!flag)
            return "";
        return current;
    }

    public boolean helper(int[] m1, int[] m2) {
        for (int i = 0; i < m1.length; i++)
            if (m1[i] < m2[i])
                return false;
        return true;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.equals(" ") || s.length() == 1)
            return 1;
        int left = 0, right = 0, res = 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            char c = s.charAt(right++);
            while (set.contains(c))
                set.remove(s.charAt(left++));
            set.add(c);
            res = res >= (right - left) ? res : right - left;
        }
        return res;
    }

    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new ArrayList<>();
        int length = S.length();
        int index = 0, prev = -1;
        while (index < length) {
            char current = S.charAt(index);
            index = S.lastIndexOf(current);
            for (int i = prev + 1; i < index; i++) {
                char tmpC = S.charAt(i);
                int tmpIndex = S.lastIndexOf(tmpC);
                index = index >= tmpIndex ? index : tmpIndex;
                if (index == length - 1)
                    break;
            }
            res.add(index - prev);
            prev = index;
            index++;
        }
        return res;
    }

    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (mid == 0 || mid == nums.length - 1)
                return nums[mid];
            boolean isLeft = false;
            if (nums[mid - 1] == nums[mid])
                isLeft = true;
            else if (nums[mid + 1] == nums[mid])
                isLeft = false;
            else
                return nums[mid];
            boolean leftEven = (mid - left) % 2 == 0;    // 不考虑 mid
            if (isLeft && leftEven)
                right = mid - 2;
            else if (!isLeft && !leftEven)
                right = mid - 1;
            else if (isLeft)
                left = mid + 1;
            else
                left = mid + 2;
        }
        return nums[left];
    }

    public int search(int[] nums, int target) {
        return helper(nums, target, 0, nums.length - 1);

    }

    public int helper(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] >= nums[left]) {  // 左边有序
                if (target >= nums[left] && target < nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }
            else {  // 右边有序
                if (target > nums[mid] && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }

        }
        return -1;
    }

    // 时间O(nC), 空间O(nC), C为max_weight
    public int bag_01(int[] weights, int[] values, int max_weight) {
        int n = weights.length;
        int[][] dp = new int[n][max_weight + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= max_weight; j++) {
                // base case
                if (i == 0) {
                    if (weights[i] > j)
                        dp[i][j] = 0;
                    else
                        dp[i][j] = values[i];
                    continue;
                }

                if (weights[i] > j)
                    dp[i][j] = dp[i - 1][j];
                    // dp[i][j + x]不可能比dp[i][j]小 (x > 0), 是递增的,所以如果当前放不下,直接获取上一个结果
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i]] + values[i]);
                // dp[i - 1][j - weights[i]] + values[i] 前 i - 1个物品使用了j - weights[i]重量情况下的最大value
                // 再加上当前第 i个物品的value。 与dp[i - 1][j]比较,实质上就是
                // 不考虑选择第 i 个物品与 选择第 i 个物品的结果比较
            }
        }
        return dp[n - 1][max_weight];
    }

    // 时间O(nC), 空间O(C), 错误的版本
    public int bag_011(int[] weights, int[] values, int max_weight) {
        int n = weights.length;
        int[] dp = new int[max_weight + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= max_weight; j++) {
                if (weights[i] <= j)
                    dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
            }
        }
        return dp[max_weight];
    }

    // 时间O(nC), 空间O(C), 第二层循环要倒序
    public int bag_012(int[] weights, int[] values, int max_weight) {
        int n = weights.length;
        int[] dp = new int[max_weight + 1];
        for (int i = 0; i < n; i++) {
            for (int j = max_weight; j >= 1; j--) {
                if (weights[i] <= j)
                    dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
            }
        }
        return dp[max_weight];
    }

    // 时间O(nC), 空间O(nC), C为max_weight
    public int bag_complete(int[] weights, int[] values, int max_weight) {
        int n = weights.length;
        int[][] dp = new int[n][max_weight + 1];

            for (int j = 1; j <= max_weight; j++) {
                for (int i = 0; i < n; i++) {

                    int tmp = j / weights[i];
                if (i == 0 && tmp == 0)
                    dp[i][j] = 0;
                else if (i != 0 && tmp == 0)
                    dp[i][j] = dp[i - 1][j];
                for (int k = 1; k <= tmp; k++) {
                    // base case
                    if (i == 0) {
                        dp[i][j] = values[i] * k;
                        continue;
                    }
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - k * weights[i]] + values[i] * k);
                }
            }
        }
        return dp[n - 1][max_weight];
    }

    // dp[i][j]表示靠前i个数字是否能组成和为j. 1表示可行
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num: nums)
            sum += num;
        if (sum % 2 != 0 || nums.length % 2 != 0)
            return false;
        int weight = sum / 2;
        boolean[][] dp = new boolean[nums.length][weight + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= weight; j++) {
                if (i == 0) {
                    dp[i][j] = nums[i] == j;
                    continue;
                }
                if (nums[i] <= j)
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[nums.length - 1][weight];
    }

    public int numDecodings(String s) {
        int length = s.length();
        int[] dp = new int[length + 1];
        dp[0] = 1;
        dp[1] = 1;
        char prev = s.charAt(0);
        char current = s.charAt(0);
        for (int i = 2; i <= length; i++) {
            prev = current;
            current = s.charAt(i - 1);
            if (prev >= '3' || (prev == '2' && current >= '7') || prev == '0')
                dp[i] = dp[i - 1];
            else if (current == '0')
                dp[i] = dp[i - 2];
            else
                dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[length];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int length1 = text1.length(), length2 = text2.length();
        int[][] dp = new int[length1 + 1][length2 + 1];
        for (int i = 0; i < length1; i++)
            for (int j = 0; j < length2; j++)
                if (i * j == 0)
                    dp[i][j] = 0;   // base case
                else if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[length1][length2];
    }

    public int findTargetSumWays(int[] nums, int S) {
        int[][] dp = new int[nums.length][2 * S + 55];  // 直接定义2001就不会超出范围了
        dp[0][20 + S + nums[0]] = 1;
        dp[0][20 + S - nums[0]] = 1;
        for (int i = 1; i < nums.length; i++)
            for (int j = 0; j <= i; j++) {
                dp[i][20 + S + j] = dp[i - 1][20 + S + j - nums[i]] + dp[i - 1][20 + S + j + nums[i]];
                dp[i][20 + S - j] = dp[i - 1][20 + S - j - nums[i]] + dp[i - 1][20 + S - j + nums[i]];
            }

        return dp[nums.length - 1][2 * S + 20];
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int[][] nums = new int[strs.length][2];
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            for (char c: str.toCharArray())
                nums[i][c - '0']++;
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < strs.length; i++)
            for (int j = m; j >= nums[i][0]; j--)
                for (int k = n; k >= nums[i][1]; k--)
                    dp[j][k] = Math.max(dp[j][k], dp[j - nums[i][0]][k - nums[i][1]] + 1);
        return dp[m - 1][n - 1];
    }

    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0)
            return 0;
        int[] dp = new int[amount + 1];
        for (int i = 1; i < dp.length; i++)
            dp[i] = amount + 1;
        for (int i = 0; i < coins.length; i++)
            for (int j = coins[i]; j <= amount; j++)
                dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

//    public int change(int amount, int[] coins) {
//        int[] dp = new int[amount + 1];
//        for (int coin: coins)
//            if (coin <= amount)
//                dp[coin] = 1;
//        for (int i = 0; i < coins.length; i++)
//            for (int j = coins[i]; j <= amount; j++)
//                if (dp[j - coins[i]] != 0)
//                    dp[j] += dp[j - coins[i]];
//        return dp[amount];
//    }

    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (j == 0) {
                    dp[i][j] = 1;
                    continue;
                }
                for (int k = 0; k * coins[i - 1] <= j; k++)
                    dp[i][j] += dp[i - 1][j - k * coins[i - 1]];
            }
        }
        return dp[coins.length][amount];
    }

    public boolean canMeasureWater(int x, int y, int z) {
        if (x + y < z)
            return false;
        if (x > y) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        boolean[] dp = new boolean[2 * y + 1];
        dp[0] = true;
        for (int k = 0; k * x <= 2 * y; k++)
            dp[2 * y - k * x] = true;
        for (int i = 0; i < y; i++)
            for (int j = i + 1; j < y; j++)
                dp[i + j] = dp[i] && dp[j];
        return dp[z];
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int res =  0;
        for (int arr: arr1) {
            for (int arr22: arr2) {
                if (Math.abs(arr - arr22) > d) {
                    res--;
                    break;
                }
            }
            res++;
        }
        return res;
    }

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        int res = 2 * n;
        Arrays.sort(reservedSeats, new Comparator<int[]>() {
            @Override
            public int compare(int[] seat1, int[] seat2) {
                int tmp = seat1[0] - seat2[0];
                if (tmp != 0)
                    return tmp;
                return seat1[1] - seat2[1];
            }
        });
        for (int i = 0; i < reservedSeats.length; i++) {
            if (reservedSeats[i][1] == 1 || reservedSeats[i][1] == 10)
                continue;
            if (reservedSeats[i][1] == 2 || reservedSeats[i][1] == 3) {
                if (i == 0)
                    res--;
                else {
                    if (reservedSeats[i - 1][0] == reservedSeats[i][0] && (reservedSeats[i - 1][1] == 2 || reservedSeats[i - 1][1] == 3))
                        res = res;
                    else
                        res--;
                }
            }
            else if (reservedSeats[i][1] == 4 || reservedSeats[i][1] == 5) {
                if (i == 0)
                    res--;
                else {
                    if (reservedSeats[i - 1][0] == reservedSeats[i][0] && (reservedSeats[i - 1][1] == 2 || reservedSeats[i - 1][1] == 3))
                        res = res;
                    else
                        res--;
                }
            }
            else if (reservedSeats[i][1] == 6 || reservedSeats[i][1] == 7) {
                if (i == 0)
                    res--;
                else {
                    if (reservedSeats[i - 1][0] == reservedSeats[i][0] && (reservedSeats[i - 1][1] == 4 || reservedSeats[i - 1][1] == 5))
                        res--;
                    else
                        res--;
                }
            }
            else {
                if (i == 0)
                    res--;
                else {
                    if (reservedSeats[i - 1][0] == reservedSeats[i][0] && (reservedSeats[i - 1][1] == 6 || reservedSeats[i - 1][1] == 7))
                        res = res;
                    else
                        res--;
                }
            }
        }
        return res;
    }

    public int getKth(int lo, int hi, int k) {
        int[] q = new int[hi - lo + 1];
        for (int i = lo; i <= hi; i++) {
            int j = 0;
            int tmp = i;
            while (tmp != 1) {
                if (tmp % 2 == 0)
                    tmp /= 2;
                else
                    tmp = 3 * tmp + 1;
                j++;
            }
            q[i - lo] = j;
        }
        Arrays.sort(q);
        return q[k - 1];
    }

    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            if (i % 2 == 0)
                dp[i] = Math.min(dp[i - 1] + 1, dp[i / 2] + 2);
            else
                dp[i] = Math.min(dp[i - 1] + 1, dp[i / 2] + 3);
        return dp[n];
    }

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public int rob(TreeNode root) {
        int[] res = new int[2]; // root[0], root[1]
        res = helper(root);
        return Math.max(res[0], res[1]);
    }

    public int[] helper(TreeNode root) {
        int[] res = new int[2];
        if (root == null)
            return res;
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        res[1] = left[0] + right[0] + root.val;
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        if (root == null)
            return res;
        TreeNode current = root, right = null;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            TreeNode tmp = current;
            if (current.right != null) {
                stack.push(current.right);
                current = current.right;
                continue;
            }
            res.add(tmp.val);
            }
        return res;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode root = null;
        if (preorder.length == 0)
            return root;
        root = new TreeNode(preorder[0]);
        int index = 0;
        for (int i = 0; i < inorder.length; i++)
            if (inorder[i] == preorder[0]) {
                index = i;
                break;
            }
        int[] inorder1 = new int[index];
        int[] inorder2 = new int[inorder.length - index - 1];
        int[] preorder1 = new int[index];
        int[] preorder2 = new int[inorder2.length];
        System.arraycopy(inorder, 0, inorder1, 0, inorder1.length);
        System.arraycopy(inorder, index + 1, inorder2, 0, inorder2.length);
        System.arraycopy(preorder, 1, preorder1, 0, inorder1.length);
        System.arraycopy(preorder, index + 1, preorder2, 0, inorder2.length);
        root.left = buildTree(preorder1, inorder1);
        root.right = buildTree(preorder2, inorder2);
        return root;
    }

    public int minArray(int[] numbers) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (numbers[mid] > numbers[right])
                left = mid + 1;
            else if (numbers[mid] < numbers[right])
                right = mid;
            else
                right--;
        }
        return numbers[left];  // left == right
    }

    public int numRookCaptures(char[][] board) {
        int res = 0;
        int startI = 0, startJ = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 'R') {
                    startI = i;
                    startJ = j;
                    i = 100;
                    j = 100;
                    break;
                }
        res += helper(board, startI, startJ, 1, 0);
        res += helper(board, startI, startJ, -1, 0);
        res += helper(board, startI, startJ, 0, 1);
        res += helper(board, startI, startJ, 0, -1);
        return res;
    }

    public int helper(char[][] board, int startI, int startJ, int incre1, int incre2) {
        for (int i = startI; i < board.length && i >= 0; i += incre1)
            for (int j = startJ; j < board[0].length && j >= 0; j += incre2) {
                if (board[i][j] == 'B')
                    return 0;
                if (board[i][j] == 'p')
                    return 1;
            }
        return 0;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (String word: words)
            map.put(word, map.getOrDefault(word, 0) + 1);
        int wordLength = words[0].length();
        int length = words.length;
        Map<String, Integer> tmp = new HashMap<>();
        for (int i = 0; i + wordLength * length <= s.length(); i++) {
            tmp.putAll(map);
            int j = i;
            for (j = i; j < i + wordLength * length; j += wordLength) {
                String str = s.substring(j, j + wordLength);
                Integer integer = tmp.get(str);
                if (integer == null || integer == 0)
                    break;
                tmp.put(str, integer - 1);
            }
            tmp.clear();
            if (j == i + wordLength * length)
                res.add(i);
        }
        return res;
    }

    public int minimumMoves(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        for (int diff = 0; diff < arr.length; diff++) {
            for (int i = 0; i < arr.length; i++) {
                int j = i + diff;
                if (j >= arr.length)
                    break;
                if (diff == 0) {
                    dp[i][j] = 1;
                    continue;
                }
                if (arr[i] == arr[j]) {
                    if (i + 1 > j - 1)
                        dp[i][j] = 1;
                    else
                        dp[i][j] = dp[i + 1][j - 1];
                }
                else
                    dp[i][j] = dp[i][j - 1] + 1;
                for (int tmp = i; tmp < j; tmp++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][tmp] + dp[tmp + 1][j]);
                }
            }
        }
        return dp[0][arr.length - 1];
    }

    public boolean hasGroupsSizeX(int[] deck) {
        if (deck.length == 1)
            return false;
        int[] freq = new int[10001];
        for (int d: deck)
            freq[d]++;
        int min = 10000;
        int minIndex = 0;
        for (int i = 0; i <= 10000; i++)
            if (freq[i] != 0 && min > freq[i]) {
                min = freq[i];
                minIndex = i;
            }
        int first = -1;
        for (int i = 0; i < 10000; i++) {
            if (freq[i] == 0 || i == minIndex)
                continue;
            int tmp = gcd(min, freq[i]);
            if (first == -1)
                first = tmp;
            if (first != tmp)
                return false;
        }
        return true;
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public void hel() {
        int i = 1;
        while (i < 2000)
            System.out.print(i++ + ",");
    }

    public void hel2(int[] d) {
        d[1] = 22;
    }

    public boolean exist(char[][] board, String word) {
        if (word.length() == 0)
            return false;
        char first = word.charAt(0);
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == first) {
                    boolean temp = dfs(board, visited, i, j, word, 0);
                    if (temp)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, boolean[][] visited, int i, int j, String word, int current) {
        if (current == word.length())
            return true;
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 ||
                visited[i][j] || board[i][j] != word.charAt(current))
            return false;
        visited[i][j] = true;
        boolean f1 = dfs(board, visited, i + 1, j, word, current + 1);
        boolean f2 = dfs(board, visited, i - 1, j, word, current + 1);
        boolean f3 = dfs(board, visited, i, j + 1, word, current + 1);
        boolean f4 = dfs(board, visited, i, j - 1, word, current + 1);
        // 可以改成 boolean res = dfs(...) || dfs(...) || ... 短路运算符减少一定次数的重复dfs
        if (f1 || f2 || f3 || f4)
            return true;
        visited[i][j] = false;   // reset
        return false;
    }

    public int movingCount(int m, int n, int k) {
        int[] tmp1 = new int[m];
        int[] tmp2 = new int[n];
        for (int i = 0; i < m; i++)
            if (i == 100)
                tmp1[i] = 1;
            else
                tmp1[i] = i / 10 + i % 10;
        for (int j = 0; j < n; j++)
            if (j == 100)
                tmp2[j] = 1;
            else
                tmp2[j] = j / 10 + j % 10;
        boolean[][] ok = new boolean[m][n];
        int tmp = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (tmp1[i] + tmp2[j] <= k) {
                    ok[i][j] = true;
                    tmp++;
                }
        dfs(ok, 0, 0, m, n, 0);
        int rest = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (ok[i][j])
                    rest++;
        return tmp - rest;
    }

    // bfs
    public void dfs(boolean[][] ok, int i, int j, int m, int n, int length) {
        if (i < 0 || i >= m || j < 0 || j >= n || !ok[i][j])
            return;
        ok[i][j] = false;
        dfs(ok, i + 1, j, m, n, length + 1);
        dfs(ok, i - 1, j, m, n, length + 1);
        dfs(ok, i, j + 1, m, n, length + 1);
        dfs(ok, i, j - 1, m, n, length + 1);
    }

    public int minimumLengthEncoding(String[] words) {
        int res = 0;
        List<String> list = new ArrayList<>(Arrays.asList(words));
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        List<String> finish = new ArrayList<>();
        boolean exist;
        for (String str: list) {
            exist = false;
            int length1 = str.length();
            for (String tmp: finish) {
                int length2 = tmp.length();
                if (tmp.substring(length2 - length1, length2).equals(str)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                res += length1 + 1;
                finish.add(str);
            }
        }
        return res;
    }

    static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        // 快慢指针,找链表的中点
        ListNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = reverse(slow.next);
        while (slow != null) {
            if (head.val != slow.val)
                return false;
            head = head.next;
            slow = slow.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head) {
        if (head.next == null)
            return head;
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public int maxDepth(TreeNode root) {
        Map<TreeNode, Integer> map = new HashMap<>();
        int res = helper(root, map);
        return res;
    }

    public int helper(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null)
            return 0;
        if (map.containsKey(root))
            return map.get(root);
        int left = helper(root.left, map);
        int right = helper(root.right, map);
        int res = 1 + Math.max(left, right);
        map.put(root, res);
        return res;
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
            List<Integer> currentList = res.get(ints[1]);
            currentList.add(ints[2]);
            res.set(ints[1], currentList);
        }
        return res;
    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> numsSet = new HashSet<>();
        for (Integer num : nums) {
            numsSet.add(num);
        }
        int longest = 0;
        for (Integer num : nums) {
            if (numsSet.remove(num)) {
                // 向当前元素的左边搜索,eg: 当前为100, 搜索：99，98，97,...
                int currentLongest = 1;
                int current = num;
                while (numsSet.remove(current - 1)) current--;
                currentLongest += (num - current);
                // 向当前元素的右边搜索,eg: 当前为100, 搜索：101，102，103,...
                current = num;
                while(numsSet.remove(current + 1)) current++;
                currentLongest += (current - num);
                // 搜索完后更新longest.
                longest = Math.max(longest, currentLongest);
            }
        }
        return longest;
    }

    public int hammingWeight(int n) {
        int res = 0;
        while (n > 0) {
            if (n % 2 == 1)
                res++;
            n /= 2;
        }
        return res;
    }

    public int[] exchange(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (true) {
            while (left < nums.length && nums[left] % 2 != 0)
                left++;
            while (right >= 0 && nums[right] % 2 == 0)
                right--;
            if (left >= right)
                break;
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
        }
        return nums;
    }

    public int myAtoi(String str) {
        str.trim();
        int i = 0;
        int length = str.length();
        char first = str.charAt(i);
        boolean lessThanZero = false;
        if (first == '-') {
            lessThanZero = true;
            i++;
        }
        StringBuilder sb = new StringBuilder();
        while (i < length) {
            char c = str.charAt(i++);
            if (!Character.isDigit(c))
                break;
            sb.append(c);
        }
        if (sb.length() == 0)
            return 0;
        if (sb.length() >= 11)
            return lessThanZero ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        long tmp = Long.parseLong(sb.toString());
        if (tmp >= Integer.MAX_VALUE)
            return lessThanZero ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        return lessThanZero ? -(int)tmp : (int)tmp;
    }

    class Tmp {
        public int a;
        public Tmp(int a) {
            this.a = a;
        }

        public Tmp(Tmp x) {
            this.a = x.a;
        }

        @Override
        public String toString() {
            return String.valueOf(a);
        }
    }

    public int movingCount1(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        return bfs(m, n, k, visited, 0, 0);
    }

    public int bfs(int m, int n, int k, boolean[][] visited, int i, int j) {
        if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] ||
                (i / 10 + i % 10 + j / 10 + j % 10) > k)
            return 0;
        visited[i][j] = true;
        return 1 + bfs(m, n, k, visited, i + 1, j) + bfs(m, n, k, visited, i - 1, j) +
                bfs(m, n, k, visited, i, j + 1) + bfs(m, n, k, visited, i, j - 1);
    }

    public int search1(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        return helper1(nums, target, left, right);
    }

    public int helper1(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target)
                return 1 + helper1(nums, target, left, mid - 1) + helper1(nums, target, mid + 1, right);
            else if (nums[mid] > target)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return 0;
    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        helper(n, res, "", 0, 0);
        return res;
    }

    public void helper(int n, List<String> res, String prev, int left, int right) {
        if (prev.length() == n * 2) {
            if (left == right)
                res.add(prev);
            return;
        }
        if (left > right)
            helper(n, res, prev + ")", left, right + 1);
        helper(n, res, prev + "(", left + 1, right);
    }

    public int superEggDrop(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        for (int i = 1; i <= K; i++)        // 1层楼,无论多少个鸡蛋都为1次
            dp[i][1] = 1;
        for (int j = 1; j <= N; j++)        // 1个蛋,只能从第一层慢慢丢
            dp[1][j] = j;

        for (int i = 2; i <= K; i++) {
            for (int j = 2; j <= N; j++) {
                dp[i][j] = j;                   // 最多j次,去掉这行会出现min的值取到0的情况
                // for (int k = 1; k <= j; k++) {
                //     dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][k - 1], dp[i][j - k]) + 1);
                // }                                    // 遍历 K 会TLE
                int left = 2, right = j;
                while (left < right) {
                    int mid = (left + right + 1) / 2;
                    if (dp[i - 1][mid - 1] > dp[i][j - mid])
                        right = mid - 1;
                    else
                        left = mid;
                }
                dp[i][j] = Math.max(dp[i - 1][left - 1], dp[i][j - left]) + 1;
            }
        }
        return dp[K][N];
    }

    public int maxValue(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0)
            return 0;
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < grid.length; i++)
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        for (int j = 1; j < grid[0].length; j++)
            dp[0][j] = dp[0][j - 1] + grid[0][j];

        for (int i = 1; i < grid.length; i++)
            for (int j = 1; j < grid[0].length; j++)
                dp[i][j] = Math.max(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
        return dp[grid.length - 1][grid[0].length - 1];
    }

    public int translateNum(int num) {
        String str = String.valueOf(num);
        int[] dp = new int[str.length() + 1];
        dp[0] = 1;
        dp[1] = 1;
        char prev = str.charAt(0);
        for (int i = 2; i <= str.length(); i++) {
            char cur = str.charAt(i - 1);
            if (prev >= '3' || prev == '2' && cur >= '6')
                dp[i] = dp[i - 1];
            else
                dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[str.length()];
    }

    public int[][] findContinuousSequence(int target) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        current.add(1);
        int sum = 1;
        int left = 1, right = 2;
        while (right < target) {
            sum += right;
            current.add(right);
            if (sum == target) {
                list.add(new ArrayList<>(current));
                sum -= left;
                current.remove(0);
                left++;
                right++;
            }
            else if (sum > target) {
                sum -= left;
                sum -= right;
                current.remove(0);
                current.remove(current.size() - 1);
                left++;
            }
            else if (sum < target) {
                right++;
            }
        }
        int[][] res = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> l = list.get(i);
            for (int j = 0; j < l.size(); j++)
                res[i][j] = l.get(j);
        }

        return res;
    }

    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i + 1)
                continue;
            if (nums[i] == nums[nums[i] - 1])
                return nums[i];
            int tmp = nums[i];
            nums[i] = nums[nums[i] - 1];
            nums[tmp - 1] = tmp;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i + 1)
                continue;
            if (nums[i] == nums[nums[i] - 1])
                return nums[i];
            int tmp = nums[i];
            nums[i] = nums[nums[i] - 1];
            nums[tmp - 1] = tmp;
        }
        return -1;
    }

    public double[] twoSum(int n) {
        int[][] dp = new int[n + 1][6 * n + 1];
        for (int i = 1; i <= 6; i++)
            dp[1][i] = 1;
        for (int i = 2; i <= n; i++)
            for (int j = 1; j <= i * 6 ; j++)
                for (int k = 1; k <= 6; k++)
                    if (j - k >= 0)
                        dp[i][j] += dp[i - 1][j - k];
                    else
                        break;
        double[] res = new double[6 * n];
        double sum = Math.pow(6, n);
        for (int i = 0; i < res.length; i++)
            res[i] = dp[n][1 + i] / sum;
        return res;
    }

    public int numWays(int n, int[][] relation, int k) {
        return helper(n, relation, k, 0, 0);
    }

    public int helper(int n, int[][] relation, int k, int current, int index) {
        if (index == n - 1 && current == k)
            return 1;
        if (current >= k)
            return 0;
        int[] tmp = relation[index];
        int res = 0;
        for (int t: tmp) {
            res += helper(n, relation, k, current + 1, t);
        }
        return res;
    }

    public int minJump(int[] jump) {
        int n = jump.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        int prev = 0;
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int next = i + jump[i];
            if (i + jump[i] >= n)
                next = n - 1;
            dp[next] = dp[next] <= dp[i] + 1 ? dp[next] : dp[i] + 1;
            for (int j = Math.max(i + 1, prev); j < next; j++)
                dp[j] = dp[j] <= dp[next] + 1 ? dp[j] : dp[next] + 1;
            prev = next;
        }
        return dp[n - 1];
    }

//    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
//        Map<Integer, List<Integer>> xMap = new HashMap<>();
//        Map<Integer, List<Integer>> yMap = new HashMap<>();
//        Map<Integer, List<Integer>> zMap = new HashMap<>();
//        for (int i = 0; i < requirements.length; i++) {
//            xMap.put(i, new ArrayList<>());
//            yMap.put(i, new ArrayList<>());
//            zMap.put(i, new ArrayList<>());
//        }
//        for (int i = 0; i < requirements.length; i++) {
//            List<Integer> xList = xMap.get(i);
//            List<Integer> yList = yMap.get(i);
//            List<Integer> zList = zMap.get(i);
//            xList.add(requirements[i][0]);
//            yList.add(requirements[i][1]);
//            zList.add(requirements[i][2]);
//            xMap.put(i, xList);
//            yMap.put(i, yList);
//            zMap.put(i, zList)];
//        }
//        List<Map.Entry<Integer, List<Integer>>> xLists = new ArrayList<>(xMap.entrySet());
//        List<Map.Entry<Integer, List<Integer>>> yLists = new ArrayList<>(yMap.entrySet());
//        List<Map.Entry<Integer, List<Integer>>> zLists = new ArrayList<>(zMap.entrySet());
//        Collections.sort(xLists, new Comparator<Map.Entry<Integer, List<Integer>>>() {
//            @Override
//            public int compare(Map.Entry<Integer, List<Integer>> o1, Map.Entry<Integer, List<Integer>> o2) {
//                return o1.getValue().get()
//            }
//        });
////        // 对HashMap根据Value排序
////        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
////        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
////            @Override
////            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
////                return o2.getValue().compareTo(o1.getValue());
////            }
////        });
//    }

    class TempNode {
        int index;
        int value;
        TempNode(int i, int v) {
            index = i;
            value = v;
        }
    }

    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        TempNode[] req1 = new TempNode[requirements.length],
                req2 = new TempNode[requirements.length], req3 = new TempNode[requirements.length];
        for (int i = 0; i < requirements.length; i++) {
            req1[i] = new TempNode(i, requirements[i][0]);
            req2[i] = new TempNode(i, requirements[i][1]);
            req3[i] = new TempNode(i, requirements[i][2]);
        }
        Arrays.sort(req1, (o1, o2) -> {return o1.value - o2.value;});
        Arrays.sort(req2, (o1, o2) -> {return o1.value - o2.value;});
        Arrays.sort(req3, (o1, o2) -> {return o1.value - o2.value;});
        int[] tmpX = new int[requirements.length],
                tmpY = new int[requirements.length], tmpZ = new int[requirements.length];
        Arrays.fill(tmpX, -1);
        Arrays.fill(tmpY, -1);
        Arrays.fill(tmpZ, -1);
        int x = 0, y = 0, z = 0;
        int xIndex = 0, yIndex = 0, zIndex = 0;
        for (int j = xIndex; j < requirements.length; j++) {
            if (req1[j].value <= x)
                tmpX[req1[j].index] = 0;
            else {
                xIndex = j;
                break;
            }
        }   // 0的情况
        for (int j = yIndex; j < requirements.length; j++) {
            if (req2[j].value <= y)
                tmpY[req2[j].index] = 0;
            else {
                yIndex = j;
                break;
            }
        }
        for (int j = zIndex; j < requirements.length; j++) {
            if (req3[j].value <= z)
                tmpZ[req3[j].index] = 0;
            else {
                zIndex = j;
                break;
            }
        }
        for (int i = 0; i < increase.length; i++) {
            x += increase[i][0];
            y += increase[i][1];
            z += increase[i][2];
            for (int j = xIndex; j < requirements.length; j++) {
                if (req1[j].value <= x)
                    tmpX[req1[j].index] = i + 1;
                else {
                    xIndex = j;
                    break;
                }
                if (j == requirements.length - 1)
                    xIndex = requirements.length;
            }
            for (int j = yIndex; j < requirements.length; j++) {
                if (req2[j].value <= y)
                    tmpY[req2[j].index] = i + 1;
                else {
                    yIndex = j;
                    break;
                }
                if (j == requirements.length - 1)
                    yIndex = requirements.length;
            }
            for (int j = zIndex; j < requirements.length; j++) {
                if (req3[j].value <= z)
                    tmpZ[req3[j].index] = i + 1;
                else {
                    zIndex = j;
                    break;
                }
                if (j == requirements.length - 1)
                    zIndex = requirements.length;
            }
        }
        int[] res = new int[requirements.length];
        for (int i = 0; i < requirements.length; i++)
            if (tmpX[i] == -1 || tmpY[i] == -1 || tmpZ[i] == -1)
                res[i] = -1;
            else
                res[i] = Math.max(tmpX[i], Math.max(tmpY[i], tmpZ[i]));
        return res;
    }

    public int minJump1(int[] jump) {
        int n = jump.length;
        if (n == 1)
            return 1;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1000000);
        int prev = 0;
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int next = i + jump[i];
            if (i + jump[i] >= n)
                next = n;
            dp[next] = dp[next] <= dp[i] + 1 ? dp[next] : dp[i] + 1;

            for (int j = Math.max(i + 1, prev); j < next; j++)
                dp[j] = dp[j] <= dp[next] + 1 ? dp[j] : dp[next] + 1;
            prev = next;
        }
        return dp[n];
    }

    public int findMinFibonacciNumbers(int k) {
        List<Integer> list = new ArrayList<>();
        int pow = 1000000000;
        list.add(1);
        list.add(1);
        while (true) {
            int next = list.get(list.size() - 1) + list.get(list.size() - 2);
            System.out.println(next);
            if (next > pow)
                break;
            list.add(next);
        }
        int res = 0;
        Collections.reverse(list);
        while (true) {
            for (int i: list) {
                if (k == 0)
                    return res;
                if (k >= i)
                    res++;
            }
        }
    }

    public String getHappyString(int n, int k) {
        List<String> list = new ArrayList<>();
        helper(list, n, "", ' ');
        return list.get(k - 1);
    }

    public void helper(List<String> list, int n, String current, char prev) {
        if (current.length() == n) {
            list.add(current);
            return;
        }
        if (prev != 'a')
            helper(list, n, current + 'a', 'a');
        if (prev != 'b')
            helper(list, n, current + 'b', 'b');
        if (prev != 'c')
            helper(list, n, current + 'c', 'c');
    }

    public int numberOfArrays(String s, int k) {
        int[] dp = new int[s.length()];
        String ks = String.valueOf(k);
        int len = ks.length();
        if (s.charAt(0) == '0')
            return 0;
        dp[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(s.charAt(i));
            int t = Integer.valueOf(s.charAt(i));
            if (t > k)
                continue;
            if (s.charAt(i) != '0')
                dp[i] += dp[i - 1];
            for (int j = i - 1; j >= 0; j--) {
                char cur = s.charAt(j);
                sb.append(cur);
                if (cur == '0')
                    continue;
                if (Integer.valueOf(sb.reverse().toString()) > k)
                    break;
                dp[i] += dp[j];
                sb.reverse();
            }
            if (Integer.valueOf(sb.reverse().toString()) > k)
                continue;
            sb.reverse();
        }
        return dp[s.length() - 1];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.numberOfArrays("1234567890", 90));
        System.out.println(solution.findMinFibonacciNumbers(7));
//        int[] res = solution.getTriggerTime(new int[][] {{0,4,5},{4,8,8},{8,6,1},{10,10,0}},
//                new int[][] {{12,11,16}, {20,2,6},{9,2,6},{10,18,3},{8,14,9}});
//        for (int r: res)
//            System.out.println(r);
//        String str = "";
//        for (int i = 0; i < 500000; i++)
//            System.out.print("1,");
//        System.out.println();
//        int[] tmp = new int[1000000];
//        Arrays.fill(tmp, 1);

//        System.out.println(solution.minJump1(new int[] {10,4,4,9,3,4,6,6,4,8,8,4,1,3,5,7,5,1,5,4,
//                9,3,6,5,5,4,4,10,1,7,10,1,9,5,8,5,5,9,3,10,10,4}));

//        System.out.println(solution.numWays(5, new int[][] {{0, 2}, {2, 1}, }));
//        System.out.println(solution.twoSum(2));
//        System.out.println(solution.findDuplicate(new int[] {8,7,1,10,17,15,18,11,16,9,19,12,5,14,3,4,2,13,18,18}));
//        System.out.print(solution.findContinuousSequence(9));
//        System.out.println(solution.translateNum(12258));
//        System.out.println(solution.maxValue(new int[][] {{1,3,1},{1,5,1},{4,2,1}}));
//        System.out.println(solution.superEggDrop(2, 6));
//        System.out.println(solution.generateParenthesis(3));
//        System.out.println(solution.search1(new int[] {5,7,7,8,8,10}, 8));
//        System.out.println(solution.movingCount1(7, 2, 3));
//        List<Integer> l1 = new ArrayList<>(Arrays.asList(1, 2, 3));
//        List<Integer> l2 = new ArrayList<>(Arrays.asList(4, 5, 6));
//        l2.addAll(l1);
//        l1.set(1, 1234321);
//        System.out.println(l2); // [4, 5, 6, 1, 2, 3] 基本类型不改变
//
//        List<Tmp> l3 = new ArrayList<>(Arrays.asList(solution.new Tmp(2), solution.new Tmp(3)));
//        List<Tmp> l4 = new ArrayList<>(Arrays.asList(solution.new Tmp(4), solution.new Tmp(5)));
//        for (int i = 0; i < l3.size(); i++)
//            l4.add(solution.new Tmp(l3.get(i)));    // 用add重新new才避免了指向同一对象的问题
//        l4.addAll(new ArrayList<>(l3)); // 传参为 new ArrayList<>()也无法解决
//        l3.get(1).a = 23523;
//        System.out.println(l4); // [23523, 3] 直接修改l3, l4之一,另一个都会改变(指向同一个对象)
//
//        List<String> l5 = new ArrayList<>(Arrays.asList("aaa", "bbb"));
//        List<String> l6 = new ArrayList<>(Arrays.asList("ccc", "ddd"));
//        l6.addAll(l5);
//        l5.set(1, "qqq");
//        System.out.println(l6); // [ccc, ddd, aaa, bbb]  String不可修改
//
//        List<Tmp> l7 = new ArrayList<>(Arrays.asList(solution.new Tmp(2), solution.new Tmp(3)));
//        List<Tmp> l8 = new ArrayList<>(Arrays.asList(solution.new Tmp(4), solution.new Tmp(5)));
//        l8.addAll(l7);
//        l8.set(2, solution.new Tmp(99));
//        l8.get(2).a = 23523;
//        System.out.println(l7); // [2, 3] set之后,l7,l8在该位置上并不是指向同一个对象

//        System.out.println(solution.exchange(new int[] {3,5,8,2,4,5,6,7,8}));
//        System.out.println(solution.longestConsecutive(new int[] {100, 4, 200, 1, 3, 2}));
//       // System.out.println(solution.findKthLargest(new int[] {7, 6, 5, 4, 3, 2, 1}, 2));
////        HashMap<Integer, Integer> map = new HashMap<>();
//        List<Integer> list = new LinkedList<>();
//        Collections.synchronizedCollection(list);
//        ListNode l1 = new ListNode(0);
//        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(1);
//        ListNode l4 = new ListNode(3);
//        ListNode l5 = new ListNode(4);
//        ListNode l6 = new ListNode(5);
//        ListNode l7 = new ListNode(7);
//        ListNode l8 = new ListNode(6);
//
//        l1.next = l2; l2.next = l3; l3.next = l4; l4.next = l5; l5.next = l6;
//        System.out.println(solution.isPalindrome(l1));
//        String str = "abcddfg";
//        System.out.println(str.substring(6, 7));
//        System.out.println(solution.minimumLengthEncoding(new String[] {"time", "me", "bell"}));
        //System.out.println(solution.movingCount(2, 3, 1));

//        System.out.println(solution.exist(new char[][] {{'a','b','c','e'}, {'s','f','e','s'},
//                {'a','d','e','e'}}, "abceseeefs"));
//        int[] t = new int[] {1, 2, 3};
//        System.out.println(t[1]);
//        solution.hel2(t);
//        System.out.println(t[1]);
//        int i = 1;
//        while (i < 2000)
//            System.out.print(i++ + ",");
//        System.out.println();
//        System.out.println(solution.hasGroupsSizeX(new int[] {1,1,1,2,2,2,3,3}));
//        System.out.println(solution.minimumMoves(new int[] {4,5,4,5,6,6}));
//        int t = '2' - '0';
//        char c = (char)(2 + '0');
//        System.out.println((int)c);
//        System.out.println(solution.findSubstring("barfoothefoobarman", new String[] {"foo","bar"}));
//        HashMap<Integer, Integer> map = new HashMap<>();
//        System.out.println(map.get(3));
//        System.out.println(solution.numRookCaptures(new char[][] {
//                {'.','.','.','.','.','.','.','.'}, {'.','.','.','p','.','.','.','.'},{'.','.','.','R','.','.','.','p'},
//                        {'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','p','.','.','.','.'},
//                        {'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'}
//        }));
 //       System.out.println(solution.minArray(new int[] {3,3,1,3}));
        //System.out.println(solution.buildTree(new int[] {3,9,20,15,7}, new int[] {9,3,15,20,7}));
//        TreeNode node1 = new TreeNode(1);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//        //TreeNode node4 = new TreeNode(3);
//        node1.right = node2;
//        node2.left = node3;
//        //node2.right = node4;
//        System.out.println(solution.postorderTraversal(node1));
//        System.out.println(solution.maxNumberOfFamilies(3, new int[][] {new int[] {1, 2},
//                new int[]{1,3},new int[]{1,8},new int[]{2,6},new int[]{3,1},new int[]{3,10}}));
        //System.out.println(solution.minSteps(25));

       // System.out.println(solution.getKth(7, 11, 4));

//        System.out.println(solution.findTheDistanceValue(new int[] {4, 5, 8}, new int[] {10, 9, 1, 8}, 2));
 //       System.out.println(solution.canMeasureWater(3, 5, 4));
 //       System.out.println(solution.change(5, new int[] {1, 2, 5}));
 //       System.out.println(solution.findTargetSumWays(new int[] {1, 1, 1, 1, 1}, 3));
 //       System.out.println(solution.longestCommonSubsequence("abcde", "ace"));
//        System.out.println(solution.numDecodings("3459812032"));
//        System.out.println(solution.canPartition(new int[] {3, 3, 3, 4, 5}));
//        int i = (int)(4 / 0.3);
////        System.out.println(i);N
//        int[] weights = {2, 2, 6, 5, 4};
//        int[] values = {6, 3, 5, 4, 6};
//        int[] weights1 = {4, 3, 1, 1};
//        int[] values1 = {3000, 2000, 1500, 2000};
//        long start = System.currentTimeMillis();
//        System.out.println(solution.bag_complete(weights1, values1, 10));
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
        // System.out.println(solution.search(new int[] {5, 1, 2, 3, 4}, 1));
//        System.out.println(solution.singleNonDuplicate(new int[] {1,1,2,2,3,3,4,4,6,6,7,7,9}));
//        System.out.println(solution.partitionLabels("asdgasdsafdhaseroterioteriotoiyjcvbjcvbjcvklbk"));
//        System.out.println(solution.lengthOfLongestSubstring("ab"));
//        System.out.println(solution.minWindow("a", "a"));
//        System.out.println("   ".equals("            "));
    }
}
