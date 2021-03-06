package temp;

import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import sun.reflect.generics.tree.Tree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Seth
 * @Description:    阶段1, 刷题的开始, 没有任何目的, 在刷题的过程中熟练编程, 并且熟悉基本的算法题
 *                  大部分情况下, 只满足于把题做出来, 缺少一题多解, 归纳总结等等。
 * @Date: Created in 10:22 2019/8/28
 */

public class Test {
    public static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        int index = 0;  //最长子串下标
        int max = 0;    //最长子串长度

        for (int i = 0; i < len; i++) {
            for (int j = index; j < i; j++) { //遍历当前这个子串,下标index ~ i,看有没有重复字符
                if (s.charAt(j) == s.charAt(i)) {
                    index = j + 1; //出现重复字符，不符合，更新index
                    break;
                }
            }
            if (i - index + 1 > max) {
                max = i - index + 1;
            }
        }
        return max;
    }

    public static String longestPalindrome1(String s) {
        int len = s.length();
        int index = 0;  //最长回文串begin_index
        int max = 0;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String substring = s.substring(i, j);
                StringBuilder temp = new StringBuilder(substring);
                String reverse = temp.reverse().toString();
                if (substring.equals(reverse)) {
                    if (max < j - i) {
                        max = j - i;
                        index = i;
                    }
                }
            }
        }

        String result = s.substring(index, max + index);
        return result;
    }

    // 5  暴力法 405ms
    public static String longestPalindrome(String s) {
        int length = s.length();
        if (length <= 1)
            return s;

        StringBuilder sb = new StringBuilder(s);
        String reverse_str = sb.reverse().toString();
        int index = 0;  //longest回文串的begin index
        int max = 1;    //longest回文串的length
        int current_max = max;

        for (int i = 0; i < length - max; i++) {
            int current_length = 0;
            char begin = s.charAt(i);

            for (int j = 0; j < length - max; j++) {
                int tempI = i;
                int tempJ = j;
                char end = reverse_str.charAt(j);
                if (end != begin)
                    continue;

                current_length = 1;
                tempI++;
                tempJ++;

//                    if (arr[i][j] > maxLen) {
//                        int beforeRev = length - 1 - j;
//                        if (beforeRev + arr[i][j] - 1 == i) { //判断下标是否对应
//                            maxLen = arr[i][j];
//                            maxEnd = i;
//                        }

                while (tempI < length && tempJ < length) {
                    char begin_next = s.charAt(tempI);
                    char end_next = reverse_str.charAt(tempJ);
                    if (begin_next == end_next) {
                        current_length++;
                        tempI++;
                        tempJ++;
                    } else
                        break;
                }
                if (max < current_length) {
                    tempI--;
                    tempJ--;
                    int beforeRev = length - 1 - tempJ;
                    if (beforeRev + current_length - 1 == tempI) {
                        max = current_length;
                        index = i;
                    }
                }
            }
        }
        String result = s.substring(index, index + max);
        return result;
    }

    // 5 DP 71ms
    public String longestPalindrome2(String s) {
        boolean[][] dp = longestPalindromeHelper(s);
        String res = "";
        int max = 1;
        for (int i = 0; i < dp.length; i++)
            for (int j = i; j < dp.length; j++)
                if (dp[i][j])
                    if (max < j - i + 1) {
                        max = j - i + 1;
                        res = s.substring(i, j + 1);
                    }
        return res;
    }

    public boolean[][] longestPalindromeHelper(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int j = 0; j < s.length(); j++)
            for (int i = 0; i <= j; i++)
                if (i == j)
                    dp[i][j] = true;
                else if (s.charAt(i) == s.charAt(j) && (j - i == 1 || dp[i + 1][j - 1]))
                    dp[i][j] = true;
        return dp;
    }

    public static String convert1(String s, int numRows) {

        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
    }

    public static String convert(String s, int numRows) {
        int length = s.length();
        if (numRows == 1 || length <= numRows)
            return s;

        List<StringBuilder> list = new ArrayList<>();
        boolean flag = true;

        for (int i = 0; i < numRows; i++)
            list.add(new StringBuilder());
        int currentRow = 0;

        for (char c : s.toCharArray()) {
            list.get(currentRow).append(c);

            currentRow += flag ? 1 : -1;

            if (currentRow == 0 || currentRow == numRows - 1)
                flag = !flag;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : list)
            result.append(sb);
        return result.toString();

    }

    public static int myAtoi(String str) {
        boolean flag = false;
        int i = 1;
        String new_str = str.trim();
        int length = new_str.length();
        char first = new_str.charAt(0);
        char next;
        long result = 0;
//            StringBuilder sb = new StringBuilder();
        if (first != '-' && first != '+' && !Character.isDigit(first))
            return 0;
        else if (first == '-' || first == '+') {
//                sb.append(first);
            while (i < length) {
                next = new_str.charAt(i);
                if (Character.isDigit(next)) {
                    flag = true;
//                        sb.append(next);
                    result = result * 10 + next - '0';
                } else {
                    if (flag) {
                        if (result > Integer.MAX_VALUE && first == '+')
                            return Integer.MAX_VALUE;
                        else if (result > Integer.MAX_VALUE && first == '-')
                            return Integer.MIN_VALUE;
                        else
                            return (int) result;
//                                return Integer.parseInt(sb.toString());

                    } else
                        return 0;
                }
            }
            return 0;
        } else {
            result = result * 10 + first - '0';
//                sb.append(first);
            for (int ii = 1; ii < length; ii++) {
                next = new_str.charAt(ii);
                if (!Character.isDigit(next))
                    return 0;
//                        sb.append(next);
                else
                    result = result * 10 + next - '0';
            }
//                return Integer.parseInt(sb.toString());
            if (result > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            else
                return (int) result;
        }
    }

    public static boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        if (x < 10)
            return true;

        int m = 1;
        for (int i = 0; i < (int) Math.log10(x); i++)
            m *= 10;
        while (x > 0) {
            int end = x % 10;

            int first = x / m;

            if (end != first)
                return false;

            x = (x % m) / 10;   //middle
            m /= 100;
        }
        return true;
    }

    public static boolean isPalindrome1(int x) {
        //边界判断
        if (x < 0) return false;
        int div = 1;
        //
        while (x / div >= 10) div *= 10;
        while (x > 0) {
            int left = x / div;
            int right = x % 10;
            if (left != right) return false;
            x = (x % div) / 10;
            div /= 100;
        }
        return true;
    }

    public static int romanToInt(String s) {
        String[] reps = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int final1 = 0;
        for (int i = 0; i < s.length(); ) {
            char token = s.charAt(i);
            switch (token) {
                case 'M': {
                    final1 += 1000;
                    i++;
                    break;
                }
                case 'C': {
                    if (i == s.length() - 1) {
                        final1 += 100;
                        return final1;
                    }

                    char next = s.charAt(i + 1);
                    if (next == 'M') {
                        final1 += 900;
                        i += 2;
                    } else if (next == 'D') {
                        final1 += 400;
                        i += 2;
                    } else {
                        final1 += 100;
                        i++;
                    }
                    break;
                }
                case 'D': {
                    final1 += 500;
                    i++;
                    break;
                }
                case 'X': {
                    if (i == s.length() - 1) {
                        final1 += 10;
                        return final1;
                    }
                    char next = s.charAt(i + 1);
                    if (next == 'C') {
                        final1 += 90;
                        i += 2;
                    } else if (next == 'L') {
                        final1 += 40;
                        i += 2;
                    } else {
                        final1 += 10;
                        i++;
                    }
                    break;
                }
                case 'L': {
                    final1 += 50;
                    i++;
                    break;
                }
                case 'I': {
                    if (i == s.length() - 1) {
                        final1++;
                        return final1;
                    }
                    char next = s.charAt(i + 1);
                    if (next == 'X') {
                        final1 += 9;
                        i += 2;
                        break;
                    } else if (next == 'V') {
                        final1 += 4;
                        i += 2;
                        break;
                    } else {
                        final1++;
                        i++;
                    }
                    break;
                }
                case 'V': {
                    final1 += 5;
                    i++;
                    break;
                }
            }
        }
        return final1;
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1)
            return strs[0];

        String result = "";
        String first_str = strs[0];
        int index = 0;
        int min_length = first_str.length();
        for (int i = 1; i < strs.length; i++) {
            String curr = strs[i];
            int current_length = curr.length();
            if (current_length < min_length) {
                min_length = current_length;
                index = i;
            }
        }

        String min_str = strs[index];
        for (int i = 0; i < min_length; i++) {
            char mins = min_str.charAt(i);
            for (String str : strs) {
                char other = str.charAt(i);
                if (other != mins)
                    return result;
            }
            result += mins;
        }
        return result;

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int length = nums.length;
        // List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
        Set<List<Integer>> lists = new HashSet<>();
        for (int i = 0; i < length - 2; i++) {
            int first_num = nums[i];
            if (first_num > 0) break;
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int sum = first_num + nums[left] + nums[right];
                if (sum > 0)
                    right--;
                else if (sum < 0)
                    left++;
                else {
                    List<Integer> list = new ArrayList<>();
                    list.add(first_num);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    lists.add(list);
                    if (left == right - 1)
                        break;
                    else
                        left++;
                }
            }

        }
        List<List<Integer>> lists1 = new ArrayList<>(lists);
        return lists1;
    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min_offset = Integer.MAX_VALUE;
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                int offset = Math.abs(sum - target);
                if (offset == 0)
                    return target;
                else if (offset < min_offset) {
                    min_offset = offset;
                    answer = sum;
                }

                if (sum > target)
                    right--;
                else
                    left++;
            }

        }
        return answer;
    }

    static List<String> list = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {

        letterCombination("", digits);
        return list;
    }

    public static void letterCombination(String result, String digits) {
        HashMap<Integer, String> values = new HashMap<>();
        values.put(2, "abc");
        values.put(3, "def");
        values.put(4, "ghi");
        values.put(5, "jkl");
        values.put(6, "mno");
        values.put(7, "pqrs");
        values.put(8, "tuv");
        values.put(9, "wxyz");

        if (digits.length() == 0)
            list.add(result);
        else {
            String first = digits.substring(0, 1);
            int first_num = Integer.parseInt(first);
            String value = values.get(first_num);
            for (int i = 0; i < value.length(); i++) {
                String val = value.substring(i, i + 1);
                letterCombination(result + val, digits.substring(1));
            }
        }
    }

    @SuppressWarnings("all")
    public static List<List<Integer>> qfourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int length = nums.length;
        // List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
        Set<List<Integer>> lists = new HashSet<>();
        for (int t = 0; t < length - 3; t++) {
            for (int i = t + 1; i < length - 2; i++) {
                int first_num = nums[i];
                int left = i + 1;
                int right = length - 1;
                while (left < right) {
                    int sum = first_num + nums[left] + nums[right];
                    if (sum > target - nums[t])
                        right--;
                    else if (sum < target - nums[t])
                        left++;
                    else {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[t]);
                        list.add(first_num);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        lists.add(list);
                        if (left == right - 1)
                            break;
                        else
                            left++;
                    }
                }
            }
        }
        List<List<Integer>> lists1 = new ArrayList<>(lists);
        return lists1;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode makeNode(int x) {
        return new ListNode(x);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        if (head.next == null)
            return null;

        int length = 1;
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
            length++;
        }
        current = head;
        if (n == length)
            return head.next;

        for (int i = 0; i < length - n - 1; i++) {
            current = current.next;
        }

        current.next = current.next.next;
        return head;

    }

    public static boolean isValid(String s) {
//        int length = s.length();
//        if (length == 0)
//            return true;
//        if (length % 2 != 0)
//            return false;
//        int f_index = length / 2 - 1;
//        int b_index = length / 2;
//        char front = s.charAt(f_index);
//        char back = s.charAt(b_index);
//        if (front != '(' && front != '[' && front !='{')
//            return false;
//        else if (front == '(' && back != ')')
//            return false;
//        else if (front == '[' && back != ']')
//            return false;
//        else if (front == '{' && back != '}')
//            return false;
//        else {
//            return isValid(s.substring(0, f_index)) && isValid(s.substring(b_index + 1, length));
//        }
        int length = s.length();
        if (length == 0)
            return true;
        if (length % 2 != 0)
            return false;
        for (int i = 0; i < s.length() - 1; i++) {
            char first = s.charAt(i);
            char next = s.charAt(i + 1);
            if ((first == '(' && next == ')') ||
                    (first == '[' && next == ']') || (first == '{' && next == '}')) {
                String rest = s.substring(0, i) + s.substring(i + 2, length);
                return isValid(rest);
            }
        }
        return false;
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        int l1_val = l1.val;
        int l2_val = l2.val;

        ListNode result = new ListNode(100);
        ListNode current = result;


        while (l1 != null && l2 != null) {
            ListNode next;
            l1_val = l1.val;
            l2_val = l2.val;
            if (l1_val <= l2_val) {
                next = new ListNode(l1_val);
                l1 = l1.next;
            } else {
                next = new ListNode(l2_val);
                l2 = l2.next;
            }
            current.next = next;
            current = current.next;
        }
        if (l1 == null)
            current.next = l2;
        else
            current.next = l1;

        result = result.next;

        return result;
    }

    public static List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        backtrack(list, "", 0, 0, n);
        return list;
    }

    public static void backtrack(List<String> list, String result, int left, int right, int n) {
        int diff = left - right;

        if (result.length() == 2 * n) {
            if (diff == 0)
                list.add(result);
            return;
        }
        String temp = result;
        if (0 < diff && diff < n) {
            backtrack(list, result + '(', left + 1, right, n);
            backtrack(list, result + ')', left, right + 1, n);
        } else if (diff == 0)
            backtrack(list, result + '(', left + 1, right, n);
        else
            backtrack(list, result + ')', left, right + 1, n);
    }

    public ListNode swapPairs(ListNode head) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode temp = pre;
        while (temp.next != null && temp.next.next != null) {
            ListNode first = temp.next;
            ListNode end = temp.next.next;

            first.next = end.next;
            end.next = first;
            temp.next = end;
            temp = temp.next.next;
        }
        //head一直指向1，而第一次循环时的temp.next就是pre.next，已经指向了2.因此pre.next ≠ head
        return pre.next;
    }

    public static int strStr(String haystack, String needle) {
        int length = needle.length();
        if (length == 0)
            return 0;
        for (int i = 0; i < haystack.length() - length + 1; i++) {
            String subStr = haystack.substring(i, i + length);
            if (subStr.equals(needle))
                return i;
        }
        return -1;
    }

    public static int searchInsert(int[] nums, int target) {
        int length = nums.length;
        if (target == nums[length - 1])
            return length - 1;
        if (target > nums[length - 1])
            return length;
        for (int i = 0; i < length - 1; i++) {
            if (nums[i] == target)
                return i;
            if (nums[i] < target && nums[i + 1] > target)
                return i + 1;
        }
        return 0;
    }

    public static int removeDuplicates(int[] nums) {
        int dup = 0;
        for (int i = 0; i < nums.length - 1 - dup; i++) {
            int first = nums[i];
            int next = nums[i + 1];
            if (first == next) {
                for (int j = i; j < nums.length - 1 - dup; j++)
                    nums[j] = nums[j + 1];
                dup++;
                i--;
            }
        }
        return nums.length - dup;
    }

    public static int removeElement(int[] nums, int val) {
        int dup = 0;
        for (int i = 0; i < nums.length - dup; i++) {
            if (nums[i] == val) {
                for (int j = i; j < nums.length - dup - 1; j++)
                    nums[j] = nums[j + 1];
                dup++;
                i--;
            }
        }
        return nums.length - dup;
    }

    public static int divide(int dividend, int divisor) {
        int fd = divisor;
        int fdend = dividend;
        boolean flag = false;
        boolean ifMin = false;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0))
            flag = true;

        if (divisor == Integer.MIN_VALUE) {
            if (dividend == Integer.MIN_VALUE)
                return 1;
            return 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (Math.abs(divisor) > Math.abs(Integer.MIN_VALUE / 2)) {
                if (flag)
                    return -1;
                else
                    return 1;
            }
            dividend += Math.abs(divisor);
            ifMin = true;
        }

        divisor = Math.abs(divisor);
        dividend = Math.abs(dividend);

//            if (dividend < 0) {
//                if (flag)
//                    return Integer.MIN_VALUE;
//                return Integer.MAX_VALUE;
//            }
        int result = 0;
        int temp = 1;
        int former = divisor;
        if (dividend < divisor)
            return 0;

        int half = Math.abs(Integer.MIN_VALUE / 2);
        if (divisor >= half) {
            if (divisor == half && fdend == Integer.MIN_VALUE) {
                if (flag)
                    return -2;
                else
                    return 2;
            } else if (divisor > dividend)
                return 0;
            else {
                if (flag)
                    return -1;
                else
                    return 1;
            }

        }

        while (divisor <= dividend) {
            divisor = divisor << 1;
            temp = temp << 1;
            if (divisor > (dividend >> 1)) {
                if (divisor > dividend)
                    temp--;
                result += temp;
                temp = 1;
                dividend -= divisor;
                divisor = former;
            }
        }
        if (ifMin && fd != -1)
            result++;


        if (flag)
            result = -result;
        return result;
    }

    public static int[] nextPermutation(int[] nums) {
        int count = 1;
        for (int i = nums.length - 1; i > 0; i--) {
            int current = nums[i];
            int prev = nums[i - 1];
            if (prev < current) {
                int tempI = i - 1;
                for (int j = i; j < nums.length; j++) { //两个数字反转
                    if (j == nums.length - 1) { //到了最后一个
//                        int tempIval = nums[tempI];
                        nums[tempI] = nums[j];
                        nums[j] = prev;
                        break;
                    } else if (nums[j + 1] <= prev) {
                        nums[tempI] = nums[j];
                        nums[j] = prev;
                        break;
                    }

                }
                //
//                    for (int iq = 0; iq < nums.length; iq++)
//                        System.out.print(nums[iq] + " , ");

                for (int k = 0; k < count / 2; k++) {     //右边数字全部反转
                    int temp = nums[k + i];
                    nums[k + i] = nums[count - 1 - k + i];
                    nums[count - 1 - k + i] = temp;
                }
                return nums;
            } else
                count++;
        }

        for (int i = 0; i < nums.length / 2; i++) { //无反转，已经是最大，所以全部反转取最小
            int temp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = temp;
        }
        return nums;
    }

    public static int longestValidParentheses(String s) {
        int most_long = 0;
        int longest = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            int left = 0;
            int right = 0;
            longest = 1;
            char first = s.charAt(i);
            if (first == '(')
                left++;
            else
                continue;
            int diff = left - right; // 1
            for (int j = i + 1; j < s.length(); j++) {
                int token = s.charAt(j);
                if (token == '(') {
                    left++;
                    longest++;
                } else {
                    if (diff > 0) {
                        right++;
                        longest++;
                    } else
                        break;
                }
                diff = left - right;

                if (longest > most_long && diff == 0)
                    most_long = longest;
            }
        }
        return most_long;
    }


    //33 直接n了,去特么的logn
    public int search(int[] nums, int target) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int num: nums)
            list.add(num);
        return list.indexOf(target);
    }

    static class Result {
        int min_left;
        int max_right;
    }

    public static int[] searchRange(int[] nums, int target) {

        if (nums.length == 0)
            return new int[]{-1, -1};
        int left = 0;
        int right = nums.length - 1;
        Result result = new Result();
        result.min_left = nums.length - 1;
        result.max_right = 0;

        backtrack(result, nums, left, right, target);

        if (nums[result.max_right] != target) {
            result.max_right = -1;
            result.min_left = -1;
        }
        int[] results = new int[]{result.min_left, result.max_right};

        return results;
    }

    public static void backtrack(Result result, int[] nums, int left, int right, int target) {
        if (left < 0 || right < 0 || left > right)
            return;
        int middle = (left + right) / 2;
        if (nums[middle] == target) {
            if (result.min_left > middle)
                result.min_left = middle;
            if (result.max_right < middle)
                result.max_right = middle;
            backtrack(result, nums, left, middle - 1, target);
            backtrack(result, nums, middle + 1, right, target);
        } else if (nums[middle] < target) {
            backtrack(result, nums, middle + 1, right, target);
        } else
            backtrack(result, nums, left, middle - 1, target);
    }

    static ArrayList<String> stringsList = new ArrayList<>();

    public static String countAndSay(int n) {
        if (n == 1)
            return "1";
        backtrack_countAndSay("1", 2, n);

        return stringsList.get(n - 2);
    }

    public static void backtrack_countAndSay(String str, int current, int n) {
        StringBuilder sb = new StringBuilder();

        int count = 1;
        for (int i = 0; i < str.length(); i += count) {
            char a = str.charAt(i);
            count = 1;
            for (int j = i + 1; j < str.length(); j++) {
                char next = str.charAt(j);
                if (a == next)
                    count++;
                else
                    break;
            }
            sb.append(String.valueOf(count));
            sb.append(a);
        }

        stringsList.add(sb.toString());
        if (current == n)
            return;
        backtrack_countAndSay(sb.toString(), current + 1, n);
    }


    //TODO  39  感觉是一个思路极其不清晰的回溯法，先注释掉了，重写。
//    static ArrayList<List<Integer>> listList = new ArrayList<>();
//
//    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
//        Arrays.sort(candidates);
//
//        ArrayList<Integer> list = new ArrayList<>();
//        backtrack_combinationSum(candidates, 0, target, list);
//
//        return listList;
//    }
//
//    public static void backtrack_combinationSum(int[] candidates, int former, int target, ArrayList<Integer> list) {
//
//        for (int i = former; i < candidates.length; i++) {
//            ArrayList<Integer> list1 = new ArrayList<>();
//
//            int nth = target / candidates[i];
//            for (int j = 0; j <= nth; j++) {
//                for (int k = 0; k < j; k++)
//                    list1.add(candidates[i]);
//
//                //
////                for (int q: list)
////                    System.out.print(q + " , ");
////                System.out.println();
//
//                backtrack_combinationSum(candidates, i + 1, target, list1);
//            }
//        }
//
//        int sum = 0;
//        for (int integer : list)
//            sum += integer;
//        if (sum == target)
//            listList.add(list);
//    }

    public static int findKthNumber(int n, int k) {
        int p = 1, prefix = 1;
        while (p < k) {
//            if (p == k)
//                return prefix;
            long count = getPrefixCount(prefix, n);
            if (p + count > k) {
                prefix *= 10;
                p++;
            } else if (p + count <= k) {
                prefix++;
                p += count;
            }
        }
        return prefix;
    }

    /*
     * @Description: get the count of each prefix
     * @param: [prefix, n]  n is the max_number
     * @return: int
     * @Date: 2019/9/20 9:43
     */
    public static long getPrefixCount(int prefix, int n) {
        long current = prefix;
        long next = prefix + 1;
        long count = 0;
//        let getCount = (prefix, n) => {
//            let count =  0;
//            for(let cur = prefix, next = prefix + 1; cur <= n; cur *= 10, next *= 10)
//                count += Math.min(next, n+1) - cur;
//            return count;
//        }

        for (; current <= n; current *= 10, next *= 10) {
            count += Math.min(n + 1, next) - current;
        }

//        while (current <= n && current > 0) {  //current number can't bigger than n
//            count += Math.min(n + 1, next) - current;
//            current *= 10;
//            next *= 10;
//        }

        return count;
    }

    public static int[][] highFive(int[][] items) {
        HashSet<Integer> set = new HashSet<>();

        for (int[] item : items)
            set.add(item[0]);
        int length = set.size();

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(200);

        for (Object aSet : set)
            map.put((int) aSet, new ArrayList<>());

        for (int[] item : items) {
            map.get(item[0]).add(item[1]);
            System.out.println(item[0] + " , " + map.get(item[0]));
        }

        int[][] results = new int[length][];
        int i = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            int key = entry.getKey();
            ArrayList<Integer> values = entry.getValue();
            values.sort(Collections.reverseOrder());
            int avg = (values.get(0) + values.get(1) + values.get(2) + values.get(3) +
                    values.get(4)) / 5;
            results[i] = new int[]{key, avg};
            i++;
        }
        return results;
    }

    // speed 100%
    public int numberOfArithmeticSlices(int[] A) {
        int length = A.length;
        int total = 0;
        for (int i = 0; i < length - 1; ) {              // 循环到倒数第二个元素就可以结束了
            int count = 2;
            int diff = A[i] - A[i + 1];                 // 先取最近两个相邻元素的差值
            for (int j = i + 2; j < length; j++) {
                if (A[i] - A[j] != diff * count)
                    break;
                count++;                                // 连续count个元素的差值相等
            }
            if (count >= 3) {                           // count不小于3才算等差数列
                i += count - 1;
                // 直接跳过count - 1个元素，为什么不是count？看这个测试用例： [1, 3, 5, 7, 9, 12, 15]
                // 如果是跳过count个元素，那么会错过[9, 12, 15]这个数列
                count -= 2;
                while (count > 0) {
                    total += count;
                    count--;
                }
            } else
                i++;
        }

        return total;
    }

    // 496
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int length = nums1.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            int start = 0;
            for (int j = 0; j < nums2.length; j++)
                if (nums2[j] == nums1[i]) {
                    start = j;
                    break;
                }
            for (int j = start; j < nums2.length; j++) {
                if (j == nums2.length - 1) {
                    result[i] = -1;
                    break;
                }
                if (nums2[j + 1] > nums1[i]) {
                    result[i] = nums2[j + 1];
                    break;
                }
            }
        }
        return result;
    }

    @SuppressWarnings("ALL")
    public static ArrayList<Integer> nextGreaterElement(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.getFirst() <= nums[i])
                stack.removeFirst();
            list.add(stack.isEmpty() ? -1 : stack.getFirst());
            stack.addFirst(nums[i]);
        }
        return list;
    }

    // 496优化(bad 优化）
    @SuppressWarnings("All")
    public int[] nextGreaterElement1(int[] nums1, int[] nums2) {
        int length = nums1.length;
        ArrayList<Integer> result = new ArrayList<>(length);
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.getFirst() <= nums2[i])
                stack.removeFirst();
            result.add(stack.isEmpty() ? -1 : stack.getFirst());
            stack.addFirst(nums2[i]);
        }
        Collections.reverse(result);

        ArrayList<Integer> final_result = new ArrayList<>(length);
        //这里循环多一遍，太费时了，果然上面不应该使用ArrayList，而是使用HashMap。
        //通过key即可搜索出相应的value
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < nums2.length; j++)
                if (nums2[j] == nums1[i])
                    final_result.add(result.get(j));
        }
        int[] ints = new int[length];
        for (int i = 0; i < length; i++)
            ints[i] = final_result.get(i);
        return ints;
    }

    //对496 bad优化再次优化（使用HashMap，而非ArrayList，省取一次遍历.成功提速
    //执行用时 : 4 ms , 在所有 Java 提交中击败了 96.96% 的用户
    //内存消耗 : 37.3 MB , 在所有 Java 提交中击败了 68.23% 的用户
    public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
        LinkedList<Integer> stack = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        int[] result = new int[nums1.length];

        for (int num : nums2) {
            while (!stack.isEmpty() && stack.getFirst() < num)
                map.put(stack.removeFirst(), num);
            stack.addFirst(num);
        }

        for (int i = 0; i < nums1.length; i++)
            result[i] = map.getOrDefault(nums1[i], -1);

        return result;
    }

    public static int[] nextGreaterElements(int[] nums) {
        int length = nums.length;
        int[] number = new int[length * 2];
        System.arraycopy(nums, 0, number, 0, length);
        System.arraycopy(nums, 0, number, length, length);

        LinkedList<Integer> stack = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>(length);

        for (int i = number.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.getFirst() <= number[i])
                stack.removeFirst();
            map.put(i, stack.isEmpty() ? -1 : stack.getFirst());
            stack.addFirst(number[i]);
        }

        int[] result = new int[length];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (i == length)
                break;
            result[i] = entry.getValue();
            i++;
        }
        return result;
    }

    public static int nextGreaterElement(int n) {
        String str = String.valueOf(n);
        int length = str.length();
        int[] ints = new int[length];
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++)
            ints[i] = Integer.parseInt(String.valueOf(str.charAt(i)));  // 将int值转换成字符数组

        // 主要的思路：从最小位开始，当高位比低位小的时候，则高位的位置就是要进行改变的位置
        // 如 74986321,最右边的"986321"已经是最大，不可能再在这一部分修改而获取更大的值
        // 此时需要从右边的"低位"中寻找大于高位（此处是4）的最小数，即6
        // 更高的位都不需要改变，则我们的结果会是： "7" + "4" + "123489" (原本是123689,6跟4位置替换)
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.getFirst() <= ints[i])
                stack.removeFirst();
            boolean isEmpty = stack.isEmpty();
            if (isEmpty) {
                if (i == 0)
                    return -1;
                res.append(ints[i]);    //因为遍历是从右遍历的，所以默认就已经是反转了，9876会转换成6789
            } else {      // 找到要修改的高位
                int max = 0;
                for (int j = 0; j < res.length(); j++) {
                    int current = Character.getNumericValue(res.charAt(j)); // 否则char会转成ASCII值
                    if (current > ints[i]) {
                        max = current;          // 低位中要与高位替换的值
                        String s = String.valueOf(ints[i]); // int转成String
                        res.setCharAt(j, s.charAt(0));      // String再转为char
                        break;
                    }
                }
                String result = str.substring(0, i) + max + res;
                long value = Long.valueOf(result);  // 先转成long，看会不会超出int的范围
                if (value > Integer.MAX_VALUE)
                    return -1;
                return Integer.parseInt(result);    // 超出就返回-1，没超出才在范围内。
            }

            stack.addFirst(ints[i]);
        }

        return -1;
    }

    //err 714
//    public static int maxProfit(int[] prices, int fee) {
//        int total = 0;
//        int offset = 1;
//        int length = prices.length;
//        for (int i = 0; i < length - 1; i += offset + 1) {
//            if (prices[i] >= prices[i + 1]) {
//                offset = 0;
//                continue;
//            }
//            for (int j = i + 1; j < length; j++) {
//                if (j != length - 1)
//                    if (prices[j] <= prices[j + 1])
//                        continue;
//
//                if (prices[i] - prices[j] >= fee) {
//                    offset = j - i;
//                    break;
//                }
//
//                if (j == length - 1) {
//                    while (prices[j - 1] > prices[j]) {
//                        j--;
//                    }
//
//                    if (prices[j] - prices[i] - fee <= 0) {
//                        offset = length;
//                        break;
//                    }
//                    total += prices[j] - prices[i] - fee;
//                    offset = length;
//                    break;
//                }
//
//                if (prices[j] > prices[j + 1]) {
//                    offset = j - i;
//                    if (prices[j] - prices[i] - fee > 0)
//                        total += prices[j] - prices[i] - fee;
//                    break;
//                }
//            }
//        }
//
//        return total;
//    }

    // 714第二个题解很厉害，很详细，6道股票题
    public static int maxProfit(int[] prices, int fee) {
        int cash = 0, hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node() {
        }

        public Node(int _val, Node _prev, Node _next, Node _child) {
            val = _val;
            prev = _prev;
            next = _next;
            child = _child;
        }

    }

    public static void getOrder(Node node) {
        while (node != null) {

            System.out.print(node.val + " - ");
            if (node.child != null) {
                getOrder(node.child);
            }
            node = node.next;
        }
    }

    static Node result1 = new Node(1, null, null, null);

    public static Node flatten(Node head) {
        //Node result = new Node(head.val, head.prev, head.next, head.child);

        backtrack_flatten(head, result1);

        return result1;
    }

    // 还是链表，下次写个链表操作图，记住中间变量什么时候不变，画好图，不然一次记得，下次又忘记
    public static void backtrack_flatten(Node node, Node current) {
        if (node == null)
            return;

        Node temp = result1;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(node.val, node.prev, node.next, node.child);
        result1.next = temp.next;

//        result = new Node(node.val, node.prev, node.next, node.child);
//        result.next = result.next.next;

        // return

        if (node.child != null)
            backtrack_flatten(node.child, current);

        Node next_element = node.next;
        Node next_result = current.next;

        backtrack_flatten(next_element, next_result);
    }

    public static int maxArea(int[] height) {
        int length = height.length;
        int max = 0;
        int current = 0;

//        ArrayList<Integer> nextGreaterElements = nextGreaterElement(height);
//
//        ArrayList<Integer> whoIsRightMost = new ArrayList<>(length);

//        for (int k = 0; k < length; k++) {
//            if (k == length - 1)
//                break;
//            if (nextGreaterElements.get(k) == -1)
//                whoIsRightMost.add(k);
//
//        }

        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {              // 直接暴力法试一下
                current = (j - i) * Math.min(height[i], height[j]);
                if (current > max)
                    max = current;
            }
            // 发现我的花里胡哨改进不如直接暴力法= =
            // 暴力法用时332ms，花里胡哨法用时854ms。内存消耗都差不多。

//            for (int rm : whoIsRightMost) {
//                if (rm <= i)
//                    continue;
//                current = (rm - i) * Math.min(height[i], height[rm]);
//                if (current > max) {
//                    max = current;
//                }
//            }

//            for (int j = whoIsRightMost.get(whoIsRightMost.size() - 1); j < length; j++) {
//                if (j <= i)
//                    continue;
//                current = (j - i) * Math.min(height[i], height[j]);
//                if (current > max) {
//                    max = current;
//                }
//            }

        }
        return max;
    }

//    public static int trap(int[] height) {
//        int length = height.length;
//        if (length <= 2)
//            return 0;
//
//        int sum = 0;
//        int nextBigger;
//        boolean flag = false;
//        //  int final_index = 0;
//        //    int rightMost = 0;
//
////        if (height[length - 1] > height[length - 2]) {
////            for (int i = length - 3; i >= 0; i--) {
////                if (height[i] > height[length - 1]) {
////                    rightMost = i;
////                    break;
////                }
////            }
////        }
//
//        for (int i = 0; i < height.length; ) {
//
//            nextBigger = -1;
//            for (int j = i + 1; j < length; j++) {
//                if (height[j] >= height[i]) {
//                    nextBigger = j;
//                    break;
//                }
//            }
//
//            if (nextBigger == -1) {
//                i++;
//                flag = true;
//                continue;
//            }
//
//            for (int j = i; j < nextBigger; j++) {
//                if (!flag)
//                    sum += height[i] - height[j];
//                else
//                    sum += height[nextBigger] - height[j];
//            }
//
//            flag = false;
//
//            i = nextBigger;
//        }
//
//        return sum;
//    }

    public static boolean checkObs(int x, int y, int[][] obstacles) {
        for (int i = 0; i < obstacles.length; i++) {
            if (x < obstacles[i][0])
                return false;
            if (x > obstacles[i][0])
                continue;
            if (x == obstacles[i][0]) {
                if (y == obstacles[i][1])
                    return true;
            }
        }
        return false;
    }

    public static boolean robot(String command, int[][] obstacles, int x, int y) {
        int length = command.length();
        String[] commands = command.split("");
        Arrays.sort(obstacles, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] >= o2[0])
                    return 1;
                return -1;
            }
        });
        int beginX = 0;
        int beginY = 0;
        boolean ifObs = false;
        while (true) {
            for (String cmd : commands) {
                if (cmd.equals("U")) {
                    beginY++;
                    if (beginX == x && beginY == y)
                        return true;
                    else if (beginX > x || beginY > y)
                        return false;
                    ifObs = checkObs(beginX, beginY, obstacles);
                    if (ifObs)
                        return false;
                } else {
                    beginX++;
                    if (beginX == x && beginY == y)
                        return true;
                    else if (beginX > x || beginY > y)
                        return false;
                    ifObs = checkObs(beginX, beginY, obstacles);
                    if (ifObs)
                        return false;
                }
            }
        }
    }

    public static HashSet<Integer> getSubLeader(HashMap<Integer, HashSet<Integer>> leader, HashSet<Integer> sub) {
        HashSet<Integer> token = new HashSet<>();
        Iterator<Integer> it = sub.iterator();
        while (it.hasNext()) {
            int temp = it.next();
            if (leader.get(temp).size() == 0) {
                token.add(temp);
            } else {
                token.addAll(getSubLeader(leader, leader.get(temp)));
                token.add(temp);
            }
        }
        return token;
    }

    // timeout
    public static int[] bonus(int n, int[][] leadership, int[][] operations) {
        HashMap<Integer, HashSet<Integer>> leader = new HashMap<>();

        for (int i = 1; i <= n; i++)
            leader.put(i, new HashSet<>());

        for (int i = 0; i < leadership.length; i++) {
            HashSet<Integer> hashSet = leader.get(leadership[i][0]);
            hashSet.add(leadership[i][1]);
        }
        for (int i = 1; i <= n; i++) {
            HashSet<Integer> hashSet = leader.get(i);
            if (hashSet.size() == 0)
                continue;
            hashSet.addAll(getSubLeader(leader, hashSet));
        }


        ArrayList<Integer> result = new ArrayList<>();
        int[] single_coins = new int[n];

        for (int[] operation : operations) {
            if (operation[0] == 1) {
                single_coins[operation[1] - 1] += operation[2];
            } else if (operation[0] == 2) {
                //boolean isFinished = false;
                single_coins[operation[1] - 1] += operation[2];
                HashSet<Integer> integers = leader.get(operation[1]);
                for (int i : integers)
                    single_coins[i - 1] += operation[2];


            } else if (operation[0] == 3) {
                int sum = single_coins[operation[1] - 1];
                HashSet<Integer> integers = leader.get(operation[1]);

                for (int i : integers)
                    sum += single_coins[i - 1];
                result.add(sum);
            }
        }


        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int numRabbits(int[] answers) {
        int length = answers.length;
        HashSet<Integer> total = new HashSet<>(length);
        HashMap<Integer, Integer> each = new HashMap<>(length);
        int result = 0;

        for (int i : answers) {                  // 获取数组中每一种值的个数，存储到HashMap中
            Integer current = each.get(i);
            each.put(i, current == null ? 1 : current + 1);
        }

        for (Map.Entry<Integer, Integer> entry : each.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            int amount = value % (key + 1) == 0 ? value / (key + 1) : value / (key + 1) + 1;

            result += (key + 1) * amount;
        }

        return result;

    }

    public static int totalFruit(int[] tree) {
        int length = tree.length;
        if (length == 1)
            return 1;

        int total = 0;
        int max = 0;
        int nextBegin = 0;
        int currentType = 0;
        int firstFruit = -1;
        int secondFruit = -1;

        for (int i = 0; i < length - max; ) {
            currentType = 1;
            firstFruit = tree[i];
            total = 1;
            for (int j = i + 1; j < length; j++) {

                if (tree[j] == firstFruit) {
                    total++;
                } else if (tree[j] == secondFruit) {
                    total++;
                } else {
                    if (currentType == 2) {
                        max = total > max ? total : max;
                        nextBegin = j;
                        break;
                    } else {
                        total++;
                        secondFruit = tree[j];
                        currentType = 2;
                    }
                }
                if (j == tree.length - 1) {
                    max = total > max ? total : max;
                    nextBegin = 2;
                    break;
                }
            }

            for (int j = nextBegin - 1; j >= 1; j--) {
                if (tree[j] != tree[j - 1]) {
                    i = j;
                    break;
                }
                nextBegin--;
            }
        }

        return max;
    }

    public boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>(arr.length);

        for (int i : arr) {
            Integer temp = map.get(i);
            map.put(i, temp == null ? 1 : temp + 1);
        }

        HashSet<Integer> set = new HashSet<>(map.values());

        if (set.size() == map.size())
            return true;
        return false;
    }

    // 遍历，暴力，还没有优化成功 1667ms
    public static int equalSubstring(String s, String t, int maxCost) {
        int length = s.length();
        int current_cost = 0;
        int current_length = 0;
        int max = 0;
        int beginI = 0;

        for (int i = 0; i < length; ) {
            int cost = Math.abs(s.charAt(i) - t.charAt(i));
            if (current_length == 0)
                beginI = i;
            current_cost += cost;
            current_length++;
            if (maxCost < current_cost) {       // 超出
                current_length--;
                max = max > current_length ? max : current_length;
                current_length = 0;
                i = beginI + 1;
                current_cost = 0;
                continue;
            }
            i++;
            if (i == length)
                max = max > current_length ? max : current_length;
        }

        return max;
    }

    static String target = "asvdsfwe";

    public static String removeDuplicates(String s, int k) {
        target = s;
        backtrackRemoveDuplicates(k);
        return target;
    }

    public static void backtrackRemoveDuplicates(int k) {
        int length = target.length();
        if (length < k)
            return;
        int current;
        ArrayList<Integer> toRemove = new ArrayList<>(length);
        for (int i = 0; i <= length - k; ) {
            current = 1;
            for (int j = i + 1; j < length; j++) {
                if (target.charAt(j) == target.charAt(i))
                    current++;
                else {
                    i = j;
                    break;
                }
                if (current == k) {
                    toRemove.add(j);
                    i = j + 1;
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder(target);
        int sb_length = sb.length();
        for (int i = 0; i < toRemove.size(); i++)
            sb.delete(toRemove.get(i) - k + 1 - k * i, toRemove.get(i) + 1 - k * i);
        target = sb.toString();
        if (target.length() == length)
            return;
        backtrackRemoveDuplicates(k);
    }

    public static double myPow(double x, int n) {
        double former_x = x;
        if (x == 1)
            return 1;
        boolean flag = n % 2 == 0;
        if (x == -1)
            return flag ? 1 : -1;
        if (x == 0)
            return 0;
        if (n == Integer.MIN_VALUE)
            return 0;
        x = Math.abs(x);
        if (n == 0)
            return 1;
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        if (former_x < 0)
            return flag ? backtrackMyPow(x, n) : -backtrackMyPow(x, n);
        return backtrackMyPow(x, n);
    }

    public static double backtrackMyPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        //偶数的情况
        if ((n & 1) == 0) {
            return backtrackMyPow(x * x, n / 2);
        } else { //奇数的情况
            return backtrackMyPow(x * x, n / 2) * x;
        }

    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        List<List<String>> result = new ArrayList<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            String temp = String.valueOf(chars);

            List<String> list = map.get(temp);
            if (list == null) {
                List<String> list1 = new ArrayList<>();
                list1.add(str);
                map.put(temp, list1);
            } else {
                list.add(str);
                map.put(temp, list);
            }
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet())
            result.add(entry.getValue());

        return result;
    }

    // 矩阵翻转
    public void rotate(int[][] matrix) {
        int length = matrix.length;
        for (int i = 0; i < length; i++)            // 转置
            for (int j = 0; j < length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }

        for (int i = 0; i < length; i++)            // 反转每一行
            for (int j = 0; j < length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][length - i - 1];
                matrix[i][length - i - 1] = temp;
            }
    }

    // curSize 表示当前的路径 path 里面有多少个元素

    private void generatePermution(int[] nums, boolean[] visited, int curSize, int len, Stack<Integer> path, List<List<Integer>> res) {
        if (curSize == len) {
            // 此时 path 已经保存了 nums 中的所有数字，已经成为了一个排列
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!visited[i]) {
                path.push(nums[i]);
                visited[i] = true;
                generatePermution(nums, visited, curSize + 1, len, path, res);
                // 刚开始接触回溯算法的时候常常会忽略状态重置
                // 回溯的时候，一定要记得状态重置
                path.pop();
                visited[i] = false;
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        boolean[] used = new boolean[len];
        if (len == 0) {
            return res;
        }
        generatePermution(nums, used, 0, len, new Stack<>(), res);
        return res;
    }


//    // 46 全排列
//    public List<List<Integer>> permute(int[] nums) {
//        List<List<Integer>> lists = new ArrayList<>();
//        List<String> temp = new ArrayList<>();
//        for (int i = 0; i < nums.length; i++)
//            trackbackPermute(nums[i], temp, "", nums.length, i);
//    }
//
//    public void trackbackPermute(int number, List<String> lists, String current, int length, int index) {
//        if (index == length - 1) {
//            lists.add(current);
//            return;
//        }
//        int curr_length = current.length();
//        if (curr_length == 0)
//            current = String.valueOf(number);
//        for (int i = 0; i <= curr_length; i++) {
//            if (i == curr_length)
//
//        }
//    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i : nums) {
            Integer integer = map.get(i);
            map.put(i, integer == null ? 1 : integer + 1);
        }
        return null;
    }

    public void backtrackPermuteUnique(List<List<Integer>> lists, int number) {

    }

    @SuppressWarnings("ALL")
    public static boolean isValidSudoku(char[][] board) {
        ArrayList<Character> list = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.')
                    list.add(board[i][j]);
            }
            HashSet<Character> set = new HashSet<>(list);
            if (set.size() != list.size())
                return false;
            list.clear();
        }
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] != '.')
                    list.add(board[i][j]);
            }
            HashSet<Character> set = new HashSet<>(list);
            if (set.size() != list.size())
                return false;
            list.clear();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(board[3 * i][3 * j]);
                list.add(board[3 * i + 1][3 * j]);
                list.add(board[3 * i + 2][3 * j]);
                list.add(board[3 * i][3 * j + 1]);
                list.add(board[3 * i + 1][3 * j + 1]);
                list.add(board[3 * i + 2][3 * j + 1]);
                list.add(board[3 * i][3 * j + 2]);
                list.add(board[3 * i + 1][3 * j + 2]);
                list.add(board[3 * i + 2][3 * j + 2]);
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(k) == '.') {
                        list.remove(k);
                        k--;
                    }
                }
                HashSet<Character> set = new HashSet<>(list);
                if (set.size() != list.size())
                    return false;
                list.clear();
            }

        }
        return true;
    }

    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode makeTreeNode(int x) {
        return new TreeNode(x);
    }

    // 树的遍历-递归实现
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrder(list, root);
        return list;
    }

    // 前序
    public void preOrder(List<Integer> list, TreeNode root) {
        if (root == null)
            return;
        list.add(root.val);
        preOrder(list, root.left);
        preOrder(list, root.right);
    }

    // 后序
    public void postOrder(List<Integer> list, TreeNode root) {
        if (root == null)
            return;
        postOrder(list, root.left);
        postOrder(list, root.right);
        list.add(root.val);
    }

    // 中序
    public void inOrder(List<Integer> list, TreeNode root) {
        if (root == null)
            return;
        inOrder(list, root.left);
        list.add(root.val);
        inOrder(list, root.right);
    }

    // N叉树的结构
    class NTreeNode {
        public int val;
        public List<NTreeNode> children;

        public NTreeNode() {
        }

        public NTreeNode(int _val, List<NTreeNode> _children) {
            val = _val;
            children = _children;
        }
    }

    // 前序， 后序是一样的，懒得写了 (N叉没有中序）
    public void preOrderForN(List<Integer> list, NTreeNode root) {
        if (root == null)
            return;
        list.add(root.val);
        for (NTreeNode children : root.children)
            preOrderForN(list, children);
    }

    // 层次遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        // 如果要反层次输出，可以直接将lists反转，也可以将List<List<Integer>>换成LinkedList<List<Integer>>即可
        // 然后方法里改成addFirst，而不是add
        List<List<Integer>> lists = new ArrayList<>();
        ArrayList<TreeNode> nodes = new ArrayList<>();
        if (root == null)
            return lists;
        nodes.add(root);
        btLevelOrder(lists, nodes);
        return lists;
    }

    // 如果是N叉的层次遍历，left，right改成for children即可
    public void btLevelOrder(List<List<Integer>> lists, ArrayList<TreeNode> root) {
        int size = root.size();
        if (size == 0)
            return;
        List<Integer> list = new ArrayList<>(size);
        ArrayList<TreeNode> next = new ArrayList<>();
        for (TreeNode node : root) {
            list.add(node.val);
            if (node.left != null)
                next.add(node.left);
            if (node.right != null)
                next.add(node.right);
        }
        lists.add(list);
        btLevelOrder(lists, next);
    }

    public boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        list = inorderTraversal(root);
        for (int i = 0; i < list.size() - 1; i++)       // 使用中序遍历判断是否为BST，比较stupid
            if (list.get(i) >= list.get(i + 1))
                return false;
        return true;
    }

    public boolean helper(TreeNode node, Integer lower, Integer upper) {
        if (node == null) return true;

        int val = node.val;
        if (lower != null && val <= lower) return false;
        if (upper != null && val >= upper) return false;

        // 感觉比我之前错误的代码简洁很多，因为，真的不需要判断左右null，反正如果null，就是返回true。
        if (!helper(node.right, val, upper)) return false; // 把返回值放在if里，巧妙地进行DFS
        if (!helper(node.left, lower, val)) return false;  // 如果直接return，会变成，如果左子树是BST，就返回True

        //当然了，其实可以这样：    return helper(node.right, val, upper) && helper(node.left, lower, val);

        return true;
    }

    public boolean isValidBST1(TreeNode root) {         // DFS，实际上不应该跟parent比较，指定子集的范围
        return helper(root, null, null);    // 即上下界即可，lower，upper
    }

    //static List<List<Integer>> listList = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        LinkedList<Integer> list = new LinkedList<>();
        btCombinationSum(candidates, target, list, 0, result);
        return result;
    }

    public void btCombinationSum(int[] candidates, int target, LinkedList<Integer> list,
                                 int prev, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] < prev || target - candidates[i] < 0)
                continue;
            list.addLast(candidates[i]);
            btCombinationSum(candidates, target - candidates[i], list, candidates[i], result);
            list.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        LinkedList<Integer> list = new LinkedList<>();
        btCombinationSum2(candidates, target, list, 0, result, 1);
//        List<List<Integer>> res = new ArrayList<>(new HashSet<>(result));
        return result;
    }

    public void btCombinationSum2(int[] candidates, int target, LinkedList<Integer> list,
                                  int prev_index, List<List<Integer>> result, int layer) {
        if (target == 0) {
            System.out.println(list);
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = prev_index; i < candidates.length; i++) {
            if (layer == 1 && i != 0 && candidates[i] == candidates[i - 1])
                continue;

            if (target - candidates[i] < 0)
                continue;
            list.addLast(candidates[i]);
            btCombinationSum2(candidates, target - candidates[i], list, i + 1, result, 2);
            list.removeLast();
        }
    }

//    public boolean btIsValidBST(TreeNode root, TreeNode parent, boolean ifLeft) {
//        if (root == null)
//            return true;
//        TreeNode left = root.left;
//        TreeNode right = root.right;
//        if (root.left == null && root.right == null)
//            return true;
//        if (left != null && left.val >= root.val || (!ifLeft && left != null && parent != null && left.val <= parent.val))
//            return false;
//        if (right != null && right.val <= root.val || (ifLeft && right != null && parent != null && right.val >= parent.val))
//            return false;
//        if (left == null)
//            return btIsValidBST(root.right, root, false);
//        if (right == null)
//            return btIsValidBST(root.left, root, true);
//        return btIsValidBST(root.left, root, true) && btIsValidBST(root.right, root, false);
//    }

    public TreeNode getter(int i) {
        return new TreeNode(i);
    }

    // 123 * 456 ==> 123 * (400 + 50 + 6) ==> 400 * (100 + 20 + 3) + 50 * ……
//    public String multiply(String num1, String num2) {
////        char[] num22 = num2.toCharArray();
////        char[] num11 = num1.toCharArray();
////        int length1 = num11.length;
////        int length2 = num22.length;
////        ArrayList<LinkedList<String>> result = new ArrayList<>();
////        LinkedList<String> temp = new LinkedList<>();
////
////        for (int i = length2 - 1; i >= 0; i--) {
////            temp.clear();
////            int increment = 0;
////            int second = (num22[i] - '0');
////            for (int j = length1 - 1; j >= 0; j--) {
////                int first = (num11[j] - '0');
////                int next = second * first + increment;
////                increment = next / 10;
////                next -= increment * 10;
////                temp.addFirst(String.valueOf(next));
////                if (j == 0 && increment != 0)
////                    temp.addFirst(String.valueOf(increment));
////            }
////            result.add(new LinkedList<>(temp));
////        }
////
////        for (LinkedList<String> strings: result) {
////            for (String string: strings)
////                System.out.print(string + " , ");
////            System.out.println();
////        }
////
////        return null;
//
////        StringBuilder sb = new StringBuilder();
////        for (String str: result)
////            sb.append(str);
////        return sb.toString();
//
//    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int length1 = num1.length();
        int length2 = num2.length();
        int[] result = new int[length1 + length2];

        StringBuilder sb = new StringBuilder();
        for (int i = length1 - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = length2 - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int res = result[i + j + 1] + n1 * n2;
                result[i + j + 1] = res % 10;
                result[i + j] += res / 10;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (i == 0 && result[i] == 0)
                continue;
            sb.append(result[i]);
        }

        return sb.toString();
    }

    public String addStrings(String num1, String num2) {
        int length1 = num1.length() - 1;
        int length2 = num2.length() - 1;

        StringBuilder sb = new StringBuilder();
        int increment = 0;
        while (length1 >= 0 || length2 >= 0) {
            int n1 = length1 >= 0 ? num1.charAt(length1) - '0' : 0;
            int n2 = length2 >= 0 ? num2.charAt(length2) - '0' : 0;
            int sum = n1 + n2 + increment;
            sb.append(sum % 10);
            increment = sum / 10;
            length1--;
            length2--;
            if (length1 < 0 && length2 < 0)     // while will be end
                if (increment == 1)
                    sb.append(increment);
        }
        sb = sb.reverse();

        return sb.toString();
    }

    public int mySqrt(int x) {
        int temp = 0;
        long res = 0;
        int left = temp;
        int right = 46341;
        int mid = (left + right) / 2;
        while (true) {
            long midPow = (long)mid * mid;
            long midPow1 = (long)(mid + 1) * (mid + 1);
            long midPow2 = (long)(mid - 1) * (mid - 1);
            if (midPow == x || (midPow < x && midPow1 > x))
                return mid;
            if (midPow > x && midPow2 < x)
                return mid - 1;
            if (midPow > x)
                right = mid;
            else
                left = mid;
            mid = (left + right) / 2;
        }
    }

    public int[] plusOne(int[] digits) {
        if (digits.length == 0)
            return new int[]{};
        int i = digits.length - 1;
        boolean flag = false;
        while (true) {
            if (digits[i] != 9) {
                digits[i] += 1;
                break;
            }
            digits[i] = 0;
            i--;
            if (i == -1)
                flag = true;
        }
        int[] result;
        if (flag) {
            result = new int[digits.length + 1];
            result[0] = 1;
            for (int j = 0; j < digits.length; j++)
                result[j + 1] = digits[j];
        }
        else {
            result = new int[digits.length];
            for (int j = 0; j < digits.length; j++)
                result[j] = digits[j];
        }

        return result;

    }

    public List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        btPermute1(nums, lists, list, visited);
        List<List<Integer>> result = new ArrayList<>(new HashSet<>(lists));
        return result;
    }

    public void btPermute1(int[] nums, List<List<Integer>> lists, List<Integer> list, boolean[] visited) {
        if (list.size() == nums.length) {
            lists.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                list.add(nums[i]);
                visited[i] = true;
                btPermute1(nums, lists, list, visited);
                list.remove(list.size() - 1);
                visited[i] = false;
            }
        }
    }

    // 拿自身当作bitmap的算法，很强，时间复杂度为n，空间复杂度为常数
    // 因为是要常数空间，那么就不能另外开辟空间存储，否则能轻松许多
    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0)
            return 1;

        boolean ifHasOne = false;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == 1) {
                ifHasOne = true;
                break;
            }

         if (!ifHasOne)
             return 1;

        if (nums.length == 1 && nums[0] == 1)
            return 2;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] <= 0 || nums[i] > nums.length)
                nums[i] = 1;
        for (int i = 0; i < nums.length; i++) {
            int current = Math.abs(nums[i]);
            if (current != nums.length)
                nums[current] = nums[current] > 0 ? -nums[current] : nums[current];
            else
                nums[0] = -Math.abs(nums[i]);       // nums[0]保存最后一个元素的值
        }
        for (int i = 1; i < nums.length; i++)
            if (nums[i] > 0)
                return i;
        if (nums[0] > 0)
            return nums.length;
        return nums.length + 1;  // for example, 1, 2, 3, 4  ==> return 5
    }

    public int firstMissingPositive1(int[] nums) {
        int n = nums.length;

        // 基本情况
        int contains = 0;
        for (int i = 0; i < n; i++)
            if (nums[i] == 1) {
                contains++;
                break;
            }

        if (contains == 0)
            return 1;

        // nums = [1]
        if (n == 1)
            return 2;

        // 用 1 替换负数，0，
        // 和大于 n 的数
        // 在转换以后，nums 只会包含
        // 正数
        for (int i = 0; i < n; i++)
            if ((nums[i] <= 0) || (nums[i] > n))
                nums[i] = 1;

        // 使用索引和数字符号作为检查器
        // 例如，如果 nums[1] 是负数表示在数组中出现了数字 `1`
        // 如果 nums[2] 是正数 表示数字 2 没有出现
        for (int i = 0; i < n; i++) {
            int a = Math.abs(nums[i]);
            // 如果发现了一个数字 a - 改变第 a 个元素的符号
            // 注意重复元素只需操作一次
            if (a == n)
                nums[0] = - Math.abs(nums[0]);
            else
                nums[a] = - Math.abs(nums[a]);
        }

        // 现在第一个正数的下标
        // 就是第一个缺失的数
        for (int i = 1; i < n; i++) {
            if (nums[i] > 0)
                return i;
        }

        if (nums[0] > 0)
            return n;

        return n + 1;
    }

    public int lengthOfLastWord(String s) {
        s = s.trim();
        String[] strings = s.split("\\s+");
        return strings[strings.length - 1].length();
    }

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        return nums[n - k];     // 直接调用了库函数，应该还要自己改写。找到数组第K大的元素

    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> lists = new ArrayList<>();
        if (numRows == 0)
            return lists;
        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        lists.add(firstRow);
        if (numRows == 1)
            return lists;
        btGenerate(lists, numRows, 2, firstRow);
        return lists;
    }

    public void btGenerate(List<List<Integer>> result, int numRows, int current, List<Integer> prevRow) {
        if (current > numRows)
            return;
        List<Integer> row = new ArrayList<>(current);
        row.add(1);
        for (int i = 1; i < current - 1; i++)
            row.add(prevRow.get(i - 1) + prevRow.get(i));
        row.add(1);
        result.add(new ArrayList<>(row));
        btGenerate(result, numRows, current + 1, row);
    }

    // 这是获取杨辉三角的第N列 （虽然可以直接获取出完整的，然后再get第N列，但效率并不好）
    // 公式： A(n, i) = (n - i) * (n - i + 1) * (n - i + 2) …… * (n - 1)  / i! (i >= 2, n >= 2)
    // 即从 n - i开始乘，乘到n - 1(一共乘了i次，i是从0开始算的，毕竟a[i]),最后再除以i的阶乘
    // 结果最后写得太烂了= =还不如直接获取出完整的再get
    public List<Integer> getRow(int rowIndex) {
        rowIndex++;
       List<Integer> list = new ArrayList<>(rowIndex);
        if (rowIndex == 0)
            return list;
        list.add(1);
        if (rowIndex == 1)
            return list;
        list.add(rowIndex - 1);
        if (rowIndex == 2)
            return list;
        for (int i = 2; i < (int)Math.ceil((double)rowIndex / 2); i++) {
            if (i == (int)Math.ceil(rowIndex / 2) && i % 2 != 0)
                break;
            if (i == rowIndex - 1) {
                list.add(1);
                return list;
            }
            BigInteger ai = new BigInteger("1");
            for (int j = rowIndex - i; j < rowIndex; j++)
                ai = ai.multiply(new BigInteger(String.valueOf(j)));
            for (int j = 2; j <= i; j++)
                ai = ai.divide(new BigInteger(String.valueOf(j)));
            list.add(ai.intValue());
        }
        ArrayList<Integer> next = new ArrayList<>(list);
        Collections.reverse(next);
        if (rowIndex % 2 != 0)
            next.remove(0);
        list.addAll(next);

        return list;
    }

    public int maxSubArray(int[] nums) {
        int length = nums.length;
        boolean flag = true;
        for (int i = 0; i < length; i++)
            if (nums[i] > 0) {
                flag = false;
                break;
            }
        if (flag) {
            int max = Integer.MIN_VALUE;
            for (int num: nums)
                if (num > max)
                    max = num;
            return max;
        }

        int max = 0;
        int sum = 0;
        int currentMax = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0 && sum != 0)
                continue;

            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= currentMax)
                    currentMax = sum;
                if (sum <= 0)
                    break;
            }
            if (currentMax > max)
                max = currentMax;
            currentMax = 0;
            sum = 0;
        }
        return max;
    }

    // 时间复杂度为n，更加快速。
    public int maxSubArray1(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum > 0)
                sum += num;
            else
                sum = num;
            res = Math.max(res, sum);
        }
        return res;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int m = matrix.length;
        if (m == 0)
            return result;
        int n = matrix[0].length;
        if (n == 1) {
            for (int i = 0; i < m; i++)
                result.add(matrix[i][0]);
            return result;
        }
        int times = 0;
        while (true) {
            for (int i = times; i < n - times; i++)
                result.add(matrix[times][i]);
            System.out.println(result);
            if (result.size() == m * n)
                return result;
            for (int i = times + 1; i < m - times; i++)
                result.add(matrix[i][n - times - 1]);
            System.out.println(result);
            if (result.size() == m * n)
                return result;
            for (int i = n - times - 2; i > times; i--)
                result.add(matrix[m - times - 1][i]);
            System.out.println(result);
            if (result.size() == m * n)
                return result;
            for (int i = m - times - 1; i >= times + 1; i--)
                result.add(matrix[i][times]);
            System.out.println(result);

            if (result.size() == m * n)
                return result;
            times++;
        }
    }

    public boolean canJump(int[] nums) {
        // if (nums.length <= 1)           // 一定要判断，否则[0]这个case会导致后面的size变成0，然后返回false
        //     return true;
        ArrayList<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == 0)
                zeros.add(i);
        if (zeros.size() == 0)
            return true;
        if (zeros.get(0) == 0)              // 是在这里，因为只有1个0，会导致出问题，确保这个0不是最后一个元素
            return false;
        if (nums[nums.length - 1] == 0)
            zeros.remove(zeros.size() - 1); // 如果最后一个是0，那么并没有任何影响
        boolean flag;
        for (int zero: zeros) {
            flag = false;
            for (int i = 0; i < zero; i++)
                if (i + nums[i] > zero) {
                    flag = true;
                    break;
                }
            if (!flag)
                return false;
        }
        return true;
    }

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1)
            return intervals;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < intervals.length;) {
            int[] current = intervals[i];
            while (i < intervals.length - 1 && current[1] >= intervals[i + 1][0]) {
                i++;
                current[1] = Math.max(current[1], intervals[i][1]);
            }
            list.add(new int[] {current[0], current[1]});
            i++;
        }
        return list.toArray(new int[0][]);
    }

    // err,不要什么都用for，有的时候用while不仅代码更简单，逻辑还不容易出错
    // 当你要使用多次for的时候，好好想想能不能用while去简化（偷懒很有可能陷入各种奇怪的临界值错误的情况）
    public int[][] insert1(int[][] intervals, int[] newInterval) {
        int length = intervals.length;
        if (newInterval.length == 0)
            return intervals;
        if (length == 0)
            return new int[][] {{newInterval[0], newInterval[1]}};
        ArrayList<int[]> result = new ArrayList<>();
        if (newInterval[0] > intervals[length - 1][1]) {
            for (int[] interval: intervals)
                result.add(new int[] {interval[0], interval[1]});
            result.add(new int[] { newInterval[0], newInterval[1]});
            return result.toArray(new int[0][]);
        }
        if (newInterval[1] < intervals[0][0]) {
            result.add(new int[] {newInterval[0], newInterval[1]});
            for (int[] interval: intervals)
                result.add(new int[] {interval[0], interval[1]});
            return result.toArray(new int[0][]);
        }
        int leftIndex = 0;
        for (int i = 0; i < length; i++) {
            if (intervals[i][1] >= newInterval[0]) {
                leftIndex = i;
                break;
            }
            result.add(new int[] {intervals[i][0], intervals[i][1]});
        }
        int rightIndex = leftIndex;
        for (int i = leftIndex + 1; i < length; i++) {
            if (intervals[i][0] > newInterval[1]) {
                rightIndex = i;
                break;
            }
        }
        if (rightIndex != leftIndex)
            result.add(new int[] {intervals[leftIndex][0], Math.max(newInterval[1], intervals[rightIndex - 1][1])});
        else {
            result.add(new int[] {Math.min(intervals[leftIndex][0], newInterval[0]),Math.max(newInterval[1], intervals[leftIndex][1])});
            rightIndex++;
        }
        for (int i = rightIndex; i < length; i++)
            result.add(new int[] {intervals[i][0], intervals[i][1]});
        return result.toArray(new int[0][]);
    }

    // 思路一模一样，用while不仅代码简洁，而且逻辑清晰，不会出错。（for很有可能在各种边界值就出错了）
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int i = 0;
        while (i < intervals.length && newInterval[0] > intervals[i][1]) {
            res.add(intervals[i]);
            i++;
        }
        int[] tmp = new int[]{newInterval[0], newInterval[1]};
        while (i < intervals.length && newInterval[1] >= intervals[i][0]) {
            tmp[0] = Math.min(tmp[0], intervals[i][0]);
            tmp[1] = Math.max(tmp[1], intervals[i][1]);
            i++;
        }
        res.add(tmp);
        while (i < intervals.length) {
            res.add(intervals[i]);
            i++;
        }
        return res.toArray(new int[0][]);
    }

    public int[][] generateMatrix(int n) {
        int token = 1;
        int times = 0;
        int[][] result = new int[n][n];
        while (true) {
            for (int i = times; i < n - times; i++) {
                result[times][i] = token;
                token++;
            }
            if (token == n * n + 1)
                return result;
            for (int i = times + 1; i < n - times; i++) {
                result[i][n - times - 1] = token;
                token++;
            }
            if (token == n * n + 1)
                return result;
            for (int i = n - times - 2; i > times; i--) {
                result[n - times - 1][i] = token;
                token++;
            }
            if (token == n * n + 1)
                return result;
            for (int i = n - times - 1; i >= times + 1; i--) {
                result[i][times] = token;
                token++;
            }
            if (token == 1 + n * n)
                return result;
            times++;
        }
    }

    public String getPermutation(int n, int k) {
        List<String> rest = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            rest.add(String.valueOf(i));
        StringBuilder sb = new StringBuilder();
        btGetPermutation(n, k, sb, rest);
        return sb.toString();
    }

    public void btGetPermutation(int n, int k, StringBuilder sb, List<String> rest) {
        if (n == 1) {
            sb.append(rest.get(0));
            return;
        }
        int nn = 1;     // n!
        for (int i = 2; i <= n; i++)
            nn *= i;
        int nn1 = nn / n;   // (n-1)!
        int a = k / nn1;
        int b = k % nn1;
        if (b == 0) {
            b += nn1;
            a--;
        }
        sb.append(rest.get(a));
        rest.remove(a);
        btGetPermutation(n - 1, b, sb, rest);

    }

    // Definition for singly-linked list.
//    public class ListNode {
//        int val;
//        ListNode next;
//        ListNode(int x) { val = x; }
//    }

    public ListNode rotateRight(ListNode head, int k) {
        ListNode tail = head;
        int length = 1;
        if (head == null)
            return null;
        ListNode currentHead = head;
        while (currentHead.next != null) {
            length++;
            if (currentHead.next.next == null)
                tail = currentHead.next;
            currentHead = currentHead.next;
        }
        if (length == 1)
            return head;
        k %= length;
        if (k == 0)
            return head;
        tail.next = head;
        ListNode current = head;
        for (int i = 0; i < length - k - 1; i++)
            current = current.next;
        head = current.next;
        current.next = null;
        return head;
    }

    public long uniquePaths(int m, int n) {
        // if (m == 1 || n == 1)     这样可行，但会timeout，没有利用好前面的value
        //     return 1;
        // return uniquePaths(m - 1, n) + uniquePaths(m, n - 1);

        // 第一想法是用哈希来存以前的value，但实际上……一个二维数组就做到了，所以也不要盲目哈希
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                btPaths(i, j, map);
            }
        }
        return map.get(m + "000" + n);

        /*用数组，迭代，甚至不需要递归，效率爆炸
        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0)
                    res[0][j] = 1;
                else if (j == 0)
                    res[i][0] = 1;
                else
                    res[i][j] = res[i - 1][j] + res[i][j - 1];
            }
        }
        return res[m - 1][n - 1];
        */

        /*
            更NB的解法：机器人一定会走m+n-2步，即从m+n-2中挑出m-1步向下走不就行了。即C（（m+n-2），（m-1））。
        */
    }

    public int btPaths(int m, int n, HashMap<String, Integer> map) {
        if (m == 1 || n == 1) {
            map.put(m + "000" + n, 1);
            return 1;
        }
        Integer res = map.get(m + "000" + n);
        if (res != null)
            return res;
        map.put(m + "000" + n, btPaths(m - 1, n, map) + btPaths(m, n - 1, map));
        return map.get(m + "000" + n);
    }

    // 有障碍物的情况，拿上面的数组动态规划进行修改
    // 最关键是要理解这个规划是如何实现的，先获取第一行，第一列，然后后面的就是左边第一个和上面第一个的和
    // 即 a[i][j] = a[i - 1][j] + a[i][j - 1] ，所以关键其实是对第一行第一列的处理，只是上一题直接就是1
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] res = new int[m][n];
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1)
            return 0;
        /*      说白了，当时就是对第一行，第一列的处理不正确，导致使用了错误的方法去计算result
        if (m == 1 || n == 1) {
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    if (obstacleGrid[i][j] == 1)
                        return 0;
            return 1;
        }
        */
        res[0][0] = 1;
        // 第一行
        for (int i = 1; i < m; i++)
            if (obstacleGrid[i][0] != 1)
                res[i][0] = res[i - 1][0];  // 等于前一个就好了
            else
                res[i][0] = 0;      // 只要有1个0，那么后面全部都是0

        // 第一列，同理的，跟第一行是一样的情况
        for (int i = 1; i < n; i++)
            if (obstacleGrid[0][i] != 1)
                res[0][i] = res[0][i - 1];
            else
                res[0][i] = 0;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (i == 0) {
                    if (obstacleGrid[0][j] != 1) {
                        res[0][j] = 1;
                        continue;
                    }
                    else
                        break;
                }
                else if (j == 0) {
                    if (obstacleGrid[i][0] != 1) {
                        res[i][0] = 1;
                        continue;
                    }
                    else
                        break;
                }
                if (obstacleGrid[i][j] == 1) {
                    res[i][j] = 0;
                    continue;
                }
                res[i][j] = res[i - 1][j] + res[i][j - 1];
            }
        }
        return res[m - 1][n - 1];   // 记住，当你的代码变得奇怪的时候，一般就是错了！emm
    }

    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int a1: arr1)
            map.put(a1, 1);
        for (int a2: arr2) {
            Integer i = map.get(a2);
            map.put(a2, i == null ? 1 : i + 1);
        }
        for (int a3: arr3) {
            Integer i = map.get(a3);
            map.put(a3, i == null ? 1 : i + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry: map.entrySet())
            if (entry.getValue() == 3)
                list.add(entry.getKey());
        return list;
    }

    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {

        TreeNode head22 = root2;
        while (head22 != null) {
            if (head22.val == target)
                return true;
            if (head22.val < target)
                head22 = head22.right;
            else
                head22 = head22.left;
        }
        TreeNode head111 = root1;
        while (head111 != null) {
            if (head111.val == target)
                return true;
            if (head111.val < target)
                head111 = head111.right;
            else
                head111 = head111.right;
        }

        HashSet<Boolean> set = new HashSet<>();
        TreeNode head1 = root1;
        TreeNode head2 = root2;
        btTwoSumBST(head1, head2, target, set);

        if (set.size() == 2 || (set.size() == 1 && set.contains(true)))
            return true;
        return false;
    }

    public void btTwoSumBST(TreeNode t1, TreeNode t2, int target, HashSet<Boolean> set) {
        if (t1 == null)
            return;
        if (set.size() == 2 || (set.size() == 1 && set.contains(true)))
            return;
        TreeNode head1 = t1;
        TreeNode head2 = t2;
        int val1 = head1.val;
        while (head2 != null) {
            if (val1 + head2.val == target) {
                set.add(true);
                return;
            }
            if (val1 + head2.val < target) {
                head2 = head2.right;
            }
            else
                head2 = head2.left;
        }
        set.add(false);
        btTwoSumBST(t1.left, t2, target, set);
        btTwoSumBST(t1.right, t2, target, set);
    }

//    public boolean isValidPalindrome(String s, int k) {
//        int current = 0;
//        int length = s.length();
//        if (length - k <= 1)
//            return true;
//
//    }
//
//    public void btIsValidPalindrome(String s, int k, int current) {
//        StringBuilder sb = new StringBuilder(s);
//        if (s.equals(sb.reverse().toString()))  // true
//            return;
//
//    }

    public List<Integer> countSteppingNumbers(int low, int high) {
        HashSet<Integer> set = new HashSet<>();     // 我这里用了set,因为循环过程中有重复的情况
                                                    // 说明可以对循环进行优化
        for (int i = 0; i < 10; i++)        // 先考虑 0到 10
            if (i >= low && i <= high)
                set.add(i);
            else
                continue;
        for (int i = 1; i <= 9; i++)    // 主要思路是，考虑1~9开头的情况，然后逐位添加下一个数
            btC(low, high, set, i);
        ArrayList<Integer> result = new ArrayList<>(set);
        Collections.sort(result);
        return result;
    }

    // 比如第一位是1,那么第二位只能是0或者2,使用10,12进行下一次递归.10的最后一位是0,下一位只能是0,使用100进行递归……
    // 12的最后一位是2,下一位可以是1或者3,使用121或者123进行下一次递归……
    public void btC(int low, int high, HashSet<Integer> result, int first) {
        if (first > high)
            return;
        int rest = first % 10;                      // 上一个数的最后一位是rest
        int[] re1 = {1, 0, 1, 2, 3, 4, 5, 6, 7};
        int[] re2 = {8, 2, 3, 4, 5, 6, 7, 8, 9};
        // 如果rest不等于 0或 9, 那么下一位数字可以是rest + 1 或者 rest - 1    re1[rest], re2[rest]
        // 如果rest 等于0, 下一位数字只能是1(re1[0]), 如果rest等于9, 下一位只能是8 (re2[0])

        if (first > 2 * (10 ^ 8))
            // 如果超过214748364，再乘以10就溢出了，其实> 2 * 10 ^ 8也行，因为题目的high最大是 2 * 10 ^ 9
            return;
        int f1 = first * 10;   // 不能直接用是否小于0来判断, 因为 10亿 * 10,结果会是14亿！
        int f2 = first * 10;

        if (rest != 0 && rest != 9) {   // rest为1~8,有两种情况f1, f2
            f1 += re1[rest];
            f2 += re2[rest];
            if (f1 >= low && f1 <= high) {
                result.add(f1);
                btC(low, high, result, f1);
            }
            if (f2 >= low && f2 <= high) {
                result.add(f2);
                btC(low, high, result, f2);
            }
        }
        else {
            if (rest == 0)              // rest为0或者9，只有一种情况f1
                f1 += re1[0];
            else
                f1 += re2[0];
            if (f1 >= low && f1 <= high) {
                result.add(f1);
                btC(low, high, result, f1);
            }
        }
        if (f1 <= high)
            btC(low, high, result, f1);
        if (rest != 0 && rest != 9 && f2 <= high)
            btC(low, high, result, f2);
    }

    public int minCostToMoveChips(int[] chips) {
        int odd = 0;
        int even = 0;
        for (int i: chips)
            if (i % 2 == 0)
                even++;
            else
                odd++;
        return Math.min(odd, even);
    }

    public int longestSubsequence(int[] arr, int difference) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i: arr) {
            Integer integer = map.get(i - difference);
            map.put(i, integer == null ? 1 : integer + 1);
        }
        int max = 1;
        for (Integer integer: map.values())
            max = max > integer ? max : integer;
        return max;
    }

    public int getMaximumGold(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int max = 0;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0)
                    continue;
                int currentSum = grid[i][j];
                visited[i][j] = true;
                int a1 = i - 1;
                int a2 = i + 1;
                int b1 = j - 1;
                int b2 = j + 1;
                int res1 = 0, res2 = 0, res3 = 0, res4 = 0;
                if (a1 >= 0)
                    res1 = !visited[a1][j] ? grid[a1][j] : 0;
                if (a2 < n)
                    res2 = !visited[a2][j] ? grid[a2][j] : 0;
                if (b1 >= 0)
                    res3 = !visited[i][b1] ? grid[i][b1] : 0;
                if (b2 < n)
                    res4 = !visited[i][b2] ? grid[i][b2] : 0;
                int next = Math.max(res1, res2);
                next = Math.max(next, res3);
                next = Math.max(next, res4);
                if (next == 0) {
                    max = max > currentSum ? max : currentSum;
                    continue;
                }

            }
        }
        return 1;
    }

    // 10 TODO bad dp
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        if (m == 0) {
            if (n != 0 && n % 2 == 0) {
                for (int i = 1; i < n; i += 2) {
                    if (p.charAt(i) == '*' && p.charAt(i - 1) != '*')
                        continue;
                    else
                        return false;
                }
                return true;
            }
            if (n == 0)
                return true;
            return false;
        }
        if (n == 0)
            return false;
        boolean[][] dp = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    char current = p.charAt(j);
                    if (j == 0) {
                        dp[i][j] = current == s.charAt(i) || current == '.';
                        continue;
                    }
                    String cp = p.substring(0, j + 1);
                    for (int c = 1; c < cp.length(); c++) {
                        if (cp.length() == 2)
                            break;
                        if (cp.charAt(c) == '*' && cp.charAt(c - 1) != '*' && cp.charAt(c - 1) != s.charAt(i)) {
                            cp = cp.substring(0, c - 1) + cp.substring(c + 1);
                            c -= 2;
                        }
                    }
                    if (cp.length() == 2 && cp.charAt(1) == '*' &&
                            (cp.charAt(0) == s.charAt(i) || cp.charAt(0) == '.'))
                        dp[i][j] = true;
                    else if (cp.length() == 1 && (cp.charAt(0) == '.' || cp.charAt(0) == s.charAt(i)))
                        dp[i][j] = true;
                    else if (cp.length() >= 2) {
                        boolean flag = true;
                        if (cp.charAt(0) == '*') {
                            dp[i][j] = false;
                            continue;
                        }
                        for (int q = 0; q < cp.length() - 1; q++) {
                            if ((cp.charAt(q) == s.charAt(i) || cp.charAt(q) == '*') && cp.charAt(q + 1) != '*')
                                cp = cp.substring(1);
                            else
                                break;
                        }
                        if (cp.length() < 2) {
                            dp[i][j] = false;
                            continue;
                        }
                        for (int q = 0; q < cp.length() - 1; q += 2)
                            if (cp.charAt(q) != s.charAt(i) || cp.charAt(q + 1) != '*') {
                                flag = false;
                                break;
                            }
                        dp[i][j] = flag;
                    }
                    else
                        dp[i][j] = false;
                    continue;
                }
                if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
                    if (j == 0)
                        dp[i][j] = false;   // j == 0,a/.都只能匹配1个字符，而i!=0，至少有两个字符
                    else
                        dp[i][j] = dp[i - 1][j - 1];
                }
                else if (p.charAt(j) == '*') {  // p.charAt(j) == '*'
                    if (j == 0) {
                        dp[i][j] = false;
                        continue;
                    }
                    else if (j == 1) {
                        if (p.charAt(j - 1) == s.charAt(i) || p.charAt(j - 1) == '.')
                            dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                        continue;
                    }
                    if (p.charAt(j - 1) != s.charAt(i))
                        dp[i][j] = dp[i][j - 2];
                    if (p.charAt(j - 1) == s.charAt(i) || p.charAt(j - 1) == '.')
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i][j - 2];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public boolean isHappy(int n) {
        HashMap<String, Integer> map = new HashMap<>();
        while(n != 1) {
            String temp = String.valueOf(n);
            if (map.get(temp) != null)
                return false;
            map.put(temp, 1);
            int sum = 0;
            char[] temps = temp.toCharArray();
            for (char c: temps)
                sum += (c - '0') * (c - '0');
            n = sum;
        }
        return true;
    }

    public boolean isUgly(int num) {
        if (num <= 0)
            return false;
        while (num > 1)
            if (num % 2 == 0)
                num = num / 2;
            else if (num % 3 == 0)
                num = num / 3;
            else if (num % 5 == 0)
                num = num / 5;
            else
                return false;
        return true;
    }

    public int countPrimes(int n) {
        if (n <= 2)
            return 0;
        int count = 1;
        List<Integer> primes = new ArrayList<>();
        primes.add(2);
        for (int i = 1; i < n; i++)
            if (isPrime(i, primes))
                count++;
        return count;
    }

    /*      elder solution
        // use i * i <= num, insted of i <= sqrt(num)
        // it can avoid repeatedly calling an expensive function sqrt()
        for (int i = 2; i * i <= num; i++)      // n ^ 1.5 , too slow;
            if (num % i == 0)
                return false;
                */
    public boolean isPrime(int num, List<Integer> list) {
        // not good either, use an array to store each element
        // this algo use only about n / 10 space memory, but we want to use n space memory
        // and then we get the time cost is : nloglogn! This method is sieve of eratosthenes
        if (num <= 1)
            return false;
        for (int i = 0; i < list.size(); i++) {
            if (num % list.get(i) == 0)
                return false;
            if (i == list.size() - 1)
                list.add(num);
        }
        return true;
    }

    public int numSquares(int n) {
        List<Integer> squares = new ArrayList<>();
        int i1 = 1;
        while (i1 * i1 <= n)
            squares.add(i1 * i1++);
        int size = squares.size();
        int leftIndex = 0;
        int currentSum = 0;
        int min = n;

        for (int i = size - 1; i >= 0; i--) {
            int count = 0;
            int j = i;
            int beginN = n;
            while (j < size) {
                int curr = beginN / squares.get(j);
                currentSum += curr * squares.get(j);
                count += curr;
                beginN -= currentSum;
                currentSum = 0;
                if (beginN == 0) {
                    min = min < count ? min : count;
                    break;
                }
                if (count >= min) {
                    return min;
                }
                for (int k = 0; k <= j - 1; k++) {
                    if (squares.get(k) == beginN)
                        min = min < count + 1 ? min : count + 1;
                    if (squares.get(k + 1) > beginN && squares.get(k) <= beginN) {
                        // 这个逻辑就是错的，你需要循环n次。最外层你也知道不一定是最大的就是最少的平方数
                        // 那么内层也是同理的，这里就是默认了内层使用内层最大的数。这个逻辑是错误的
                        leftIndex = k;
                        break;
                        //例子： 435 = 361 + 49 + 25
                        // 如果按照这层逻辑，那么取了 361之后(最外层不一定是400获得min)，原本内层需要选49的
                        // 但这个逻辑只会选择64.
                    }
                }
                j = leftIndex;
            }
        }
        return min;
    }

    int[] memo;
    public int numSquares1(int n) {
        memo = new int[n + 1];
        return numSqu(n);
    }

    private int numSqu(int n) {
        if (memo[n] != 0)
            return memo[n];
        int val = (int)Math.sqrt(n);
        if (val * val == n)
            return memo[n] = 1;
        int res = Integer.MAX_VALUE;
        for (int i = 1; i * i < n; i++)
            res = Math.min(res, numSqu(n - i * i) + 1);
        return memo[n] = res;
    }

    public int countVowelPermutation(int n) {
        long[][] dp = new long[5][n];
        int sum = 0;
        int mod = (int)Math.pow(10, 9) + 7;
        for (int i = 0; i < 5; i++)
            dp[i][0] = 1;
        // a : 0, e : 1, i : 2, o : 3, u : 4
        for (int i = 0; i < n - 1; i++) {
            dp[1][i + 1] += dp[0][i];
            dp[0][i + 1] += dp[1][i];
            dp[2][i + 1] += dp[1][i];
            dp[0][i + 1] += dp[2][i];
            dp[1][i + 1] += dp[2][i];
            dp[3][i + 1] += dp[2][i];
            dp[4][i + 1] += dp[2][i];
            dp[2][i + 1] += dp[3][i];
            dp[4][i + 1] += dp[3][i];
            dp[0][i + 1] += dp[4][i];

            for (int j = 0; j < 5; j++)
                dp[j][i + 1] %= mod;
        }
        for (int i = 0; i < 5; i++) {
            sum += dp[i][n - 1];
            sum %= mod;
        }
        return sum;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ArrayList<Integer> result = new ArrayList<>();
        for (ListNode list: lists) {
            while (list != null) {
                result.add(list.val);
                list = list.next;
            }
        }
        Collections.sort(result);
        ListNode res = new ListNode(0);
        ListNode current = res;
        for (int i: result) {
            current = new ListNode(i);
            current = current.next;
        }
        return res;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        int length = nums.length;
        if (length == 0)
            return result;
        Arrays.sort(nums);
        boolean[] visited = new boolean[length];
        List<Integer> curr = new ArrayList<>();
        btSubsets(nums, result, visited, Integer.MIN_VALUE, curr);
        return result;
    }

    public void btSubsets(int[] nums, List<List<Integer>> result, boolean[] visited, int current, List<Integer> curr) {
        if (current == nums[nums.length - 1])
            return;
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i] && nums[i] >= current) {
                curr.add(nums[i]);
                visited[i] = true;
                result.add(new ArrayList<>(curr));
                btSubsets(nums, result, visited, nums[i], curr);
                curr.remove(curr.size() - 1);
                visited[i] = false;
            }
        }
    }

    public boolean isOneBitCharacter(int[] bits) {
        int length = bits.length;
        int current = 0;
        while (current < length)
            if (current == length - 1)
                return true;
            else if (bits[current] == 0)
                current++;
            else
                current += 2;
        return false;
    }

    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        for (int i = 0; i < n; i++) {
            int increment = (int)Math.pow(2, i);
            List<Integer> prev = new ArrayList<>(result);
            Collections.reverse(prev);
            for (int val: prev)
                result.add(val + increment);
        }
        return result;
    }

    public int maxDepth(TreeNode root) {
        int depth = 1;
        depth = getDepth(root, depth);
        return depth;
    }

    public int getDepth(TreeNode root, int currentDepth) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return currentDepth;
        else
            return Math.max(getDepth(root.left, currentDepth + 1), getDepth(root.right, currentDepth + 1));
    }

    public int maxProfit1(int[] prices, int fee) {
        int cash = 0, hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }

    // 判断环形链表solution1  -- 使用哈希表来判断，时空复杂度均为n
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head))
                return true;
            else
                set.add(head);
            head = head.next;
        }
        return false;
    }

    // 判断环形链表solution2 -- 使用双指针，时间复杂度n，空间复杂度1
    public boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode fast = head.next.next;
        ListNode slow = head;
        while (fast != null && slow != null && fast.next != null) {
            if (fast.next == slow || slow.next == fast)
                return true;
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    // 官方的双指针解法
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {                          // 拿 是否相等来判断，也是一样的。
            if (fast == null || fast.next == null) {    // 因为fast走得更快，所以确实没必要判断slow ifnull
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public ListNode detectCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head))
                return head;
            else
                set.add(head);
            head = head.next;
        }
        return null;
    }

    public ListNode detectCycle1(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode fast = head, slow = head;
        boolean ifCycle = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ifCycle = true;
                break;
            }
        }
        if (!ifCycle)
            return null;
        while (head != slow) {
            head = head.next;
            slow = slow.next;
        }
        return head;
    }

    public String reverseWords(String s) {
        String[] strings = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String s1: strings) {
            sb.append(new StringBuilder(s1).reverse().toString());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public String reverseStr(String s, int k) {
        StringBuilder result = new StringBuilder();
        while (s.length() > 0) {
            if (s.length() >= 2 * k) {
                StringBuilder sb1 = new StringBuilder(s.substring(0, k));
                sb1.reverse();
                sb1.append(s.substring(k, 2 * k));
                result.append(sb1);
                s = s.substring(2 * k);
            }
            else if (s.length() < 2 * k && s.length() >= k) {
                StringBuilder sb1 = new StringBuilder(s.substring(0, k));
                result.append(sb1.reverse());
                s = s.substring(k);
                result.append(s);
                break;
            }
            else {  // s.length() < k
                StringBuilder sb1 = new StringBuilder(s);
                result.append(sb1.reverse());
                break;
            }
        }
        return result.toString();
    }

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left++] = s[right];
            s[right--] = temp;
        }
    }

    // 用栈存储倒置的链表。如果第一个就不相同，返回null。否则，返回开始不相同的前一个. 4ms
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        LinkedList<ListNode> list1 = new LinkedList<>();
        LinkedList<ListNode> list2 = new LinkedList<>();
        ListNode currentA = headA, currentB = headB;
        while (currentA != null) {
            list1.addFirst(currentA);
            currentA = currentA.next;
        }
        while (currentB != null) {
            list2.addFirst(currentB);
            currentB = currentB.next;
        }
        int minSize = Math.min(list1.size(), list2.size());
        ListNode temp = list1.peek();
        for (int i = 0; i < minSize; i++) {
            ListNode temp1 = list1.pop();
            ListNode temp2 = list2.pop();
            if (temp1 != temp2)
                if (i == 0)
                    return null;
                else
                    return temp;
            temp = temp1;
        }
        return temp;
    }

    // 超级暴力法,效率也算是创造了自己的历史,647ms！
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode currentA = headA, currentB = headB;
        while (currentA != null) {
            while (currentB != null) {
                if (currentA == currentB)
                    return currentA;
                currentB = currentB.next;
            }
            currentB = headB;
            currentA = currentA.next;
        }
        return null;
    }

    // 哈希表,其实最开始想到的就是这个方法,但debug的时候却出错了,现在重新简单写一遍却直接AC了。
    // 用时9s,理论上时间复杂度应该跟stack差不多的,但毕竟是理论,哈希表还是需要消耗的
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode currentA = headA, currentB = headB;
        HashSet<ListNode> set = new HashSet<>();
        while (currentA != null) {
            set.add(currentA);
            currentA = currentA.next;
        }
        while (currentB != null) {
            if (set.contains(currentB))
                return currentB;
            currentB = currentB.next;
        }
        return null;
    }

    // 双指针。假设链表1比链表2长A,那么链表1到达尾部后,指向链表2的头结点,链表2到达尾部则指向链表1的头结点
    // 然后继续循环,在第二圈的时候,如果存在交点,那么必定会碰到,直接返回。
    // 如果没有交点,那么第二圈,二者都会同时遍历到对方的尾结点
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        ListNode currentA = headA,  currentB = headB;
        while (currentA != null && currentB != null) {
            if (currentA == currentB)
                return currentA;
            if (currentA.next == null && currentB.next == null) {
                return null;
            }
            currentA = currentA.next == null ? headB : currentA.next;
            currentB = currentB.next == null ? headA : currentB.next;
        }
        return null;
    }

    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i: nums) {
            Integer integer = map.get(i);
            map.put(i, integer == null ? 1 : integer + 1);
        }
        int max = 0;
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (max < value) {
                max = value;
                result = key;
            }
        }
        return result;

        // or
//        Arrays.sort(nums);            因为题目的求众数,有一个特殊的规定,假设众数必定大于floor(n/2)的元素
//        return nums[nums.length/2];   无论n是奇数还是偶数,中间的元素都会是众数       nlogn
    }

    // Boyer-Moore 投票算法,将众数记为+1,其他记为-1,那么最后的count结果肯定是大于0.当如果count为0
    // 那么就把前面的去掉(去掉的众数无关紧要,因为同时还去掉了其他同样多数目的非众数)
    // ps:这个算法有前提,就是众数必定大于floor(n/2)个,不然下面的例子就错了:
    // [1,1,1,2,2,2,3]  ==> 这里的输出会是3,而LeetCode的测试结果竟然也是3(看来Leetcode内部就是用这个算法)
    public int majorityElement1(int[] nums) {
        int count = 0;
        int candidate = 0;
        for (int num: nums) {
            if (count == 0)
                candidate = num;
            count += candidate == num ? 1 : -1;
        }
        return candidate;
    }

    // 反转链表。用三个变量来记录。一直不变的tail(也就是head)，新的head，旧的head
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return head;
        ListNode currentHead = head;
        ListNode newHead = head;
        ListNode tail = head;
        while (tail.next != null) {
            newHead = tail.next;
            tail.next = newHead.next;
            newHead.next = currentHead;
            currentHead = newHead;
        }
        return newHead;
    }

    // 递归
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i]))
                return true;
            if (set.size() > k)
                set.remove(nums[i - k]);
        }
        return false;
    }

    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
        // if n is Power of two, its binary form is 1000...000
        // n - 1 is 111.1111        then n & (n - 1) == 0

//        long temp = 2147483648L;             // 2 ^ 31
//        return n > 0 && temp % n == 0;
    }

    // 查找 BST中两个结点的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != p && root != q) {
            if ((p.val < root.val && q.val > root.val) || (p.val > root.val && q.val < root.val))
                return root;
            else if (p.val < root.val)
                root = root.left;
            else
                root = root.right;
        }
        return root;
    }

    public ListNode removeElements(ListNode head, int val) {
        if (head == null)
            return head;
        ListNode current = head;
        while (current.next != null)
            if (current.next.val == val)
                current.next = current.next.next;
            else
                current = current.next;
        ListNode result = head;
        if (head.val == val)
            result = head.next;
        return result;
    }

    //
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        int temp = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = temp;          // 存储的是除去当前元素的左边所有元素的乘积
            temp *= nums[i];
        }
        temp = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= temp;         // 再乘以后边的所有元素的乘积
            temp *= nums[i];
        }
        return res;
    }

    // 查找二叉树中两个结点的最近公共祖先
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            boolean ifLeft = isChildren(root.left, p) && isChildren(root.left, q);
            boolean ifRight = isChildren(root.right, p) && isChildren(root.right, q);
            if (ifLeft)
                root = root.left;
            else if (ifRight)
                root = root.right;
            else
                return root;
        }
        return null;
    }

    public boolean isChildren(TreeNode root, TreeNode children) {
        if (root == null)
            return false;
        if (root == children)
            return true;
        return isChildren(root.left, children) || isChildren(root.right, children);
    }

    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0)
            return;
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i1 = 0; i1 < res.length; i1++)
            for (int j1 = 0; j1 < res[0].length; j1++)
                res[i1][j1] = matrix[i1][j1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    for (int i1 = 0; i1 < matrix.length; i1++)
                        res[i1][j] = 0;
                    for (int j1 = 0; j1 < matrix[0].length; j1++)
                        res[i][j1] = 0;
                }
            }
        }
        matrix = res;
    }

    public String addBinary(String a, String b) {
        int lengthA = a.length(), lengthB = b.length();
        if (lengthA == 0)
            return b;
        if (lengthB == 0)
            return a;
        int minLength = Math.min(lengthA, lengthB);
        boolean ifA = minLength == lengthA;
        int increment = 0;
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < minLength; i++) {
            int charA = a.charAt(lengthA - 1 - i) - '0';
            int charB = b.charAt(lengthB - 1 - i) - '0';
            int current = charA + charB + increment;
            if (current <= 1)
                increment = 0;
            else
                increment = 1;
            result.addFirst(String.valueOf(current % 2));
        }
        if (!ifA) {
            for (int i = minLength; i < lengthA; i++) {
                int char1 = a.charAt(lengthA - 1 - i) - '0';
                int current = char1 + increment;
                if (current <= 1)
                    increment = 0;
                else
                    increment = 1;
                result.addFirst(String.valueOf(current % 2));
            }
        }
        else {
            for (int i = minLength; i < lengthB; i++) {
                int char1 = b.charAt(lengthB - 1 - i) - '0';
                int current = char1 + increment;
                if (current <= 1)
                    increment = 0;
                else
                    increment = 1;
                result.addFirst(String.valueOf(current % 2));
            }
        }
        if (increment != 0)
            result.addFirst(String.valueOf(increment));
        String res1 = "";
        for (String s: result)
            res1 += s;
        return res1;
    }

    public ListNode deleteDuplicates(ListNode head) {
        HashSet<Integer> set = new HashSet<>();
        if (head == null)
            return head;
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;
        ListNode headNode = head;
        set.add(head.val);
        while (headNode.next != null) {
            if (set.contains(headNode.next.val)) {
                headNode.next = headNode.next.next;
                continue;
            }
            set.add(headNode.next.val);
            headNode = headNode.next;
        }
        return virtualHead.next;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        // 可行,但太慢了
        Integer[] nums11 = Arrays.stream(nums1).boxed().toArray(Integer[]::new);
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(nums11));
        Integer[] nums22 = Arrays.stream(nums2).boxed().toArray(Integer[]::new);
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(nums22));
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer i: set1)
            if (set2.contains(i))
                result.add(i);
        int[] res = result.stream().mapToInt(Integer::valueOf).toArray();
        return res;
    }

    public int balancedStringSplit(String s) {
        int result = 0;
        int status = 0;
        for (char c: s.toCharArray()) {
            status += c == 'L' ? 1 : -1;
            if (status == 0)
                result++;
        }
        return result;
    }

    // 能写 8 个 while, 你也是人才. 调用额外函数也没必要,直接对queens数组遍历一次,存储在一个全新的数组即可
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int kx = king[0], ky = king[1];
        int currentX = kx - 1, currentY = ky;
        int[] toCheck = new int[2];

        while (currentX >= 0) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX--;
        }
        list.clear();
        currentX = kx + 1;
        currentY = ky;
        while (currentX < 8) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX++;
        }
        list.clear();
        currentX = kx;
        currentY = ky - 1;
        while (currentY >= 0) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentY--;
        }
        list.clear();
        currentX = kx;
        currentY = ky + 1;
        while (currentY < 8) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentY++;
        }
        list.clear();
        currentX = kx - 1;
        currentY = ky - 1;
        while (currentX >= 0 && currentY >= 0) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX--;
            currentY--;
        }
        list.clear();
        currentX = kx + 1;
        currentY = ky + 1;
        while (currentX < 8 && currentY < 8) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX++;
            currentY++;
        }
        list.clear();
        currentX = kx + 1;
        currentY = ky - 1;
        while (currentX < 8 && currentY >= 0) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX++;
            currentY--;
        }
        list.clear();
        currentX = kx - 1;
        currentY = ky + 1;
        while (currentX >= 0 && currentY < 8) {
            toCheck[0] = currentX;
            toCheck[1] = currentY;
            if (ifInDoubleArray(queens, toCheck)) {
                list.add(currentX);
                list.add(currentY);
                lists.add(new ArrayList<>(list));
                break;
            }
            currentX--;
            currentY++;
        }
        return lists;
    }

    // to check whether res is in source
    public boolean ifInDoubleArray(int[][] source, int[] res) {
        for (int i = 0; i < source.length; i++)
            if (source[i][0] == res[0] && source[i][1] == res[1])
                return true;
        return false;
    }

    // 直接给queens传递到另一个flag数组,就不需要额外函数了.
    // 其次,增加一个direction二维数组,表示下一个方向,即x, y 下一次的增量是direction[i][0]和direction[i][1]
    // 当你需要很多次while的时候,用一个增量数组可以减少while语句的次数
    public List<List<Integer>> queensAttacktheKing1(int[][] queens, int[] king) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[][] exists = new boolean[8][8];
        for (int[] queen: queens)
            exists[queen[0]][queen[1]] = true;
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 1}};
        for (int i = 0; i < direction.length; i++)
            for (int x = king[0], y = king[1]; x >= 0 && y >= 0 && x < 8 && y < 8;
                 x += direction[i][0], y += direction[i][1])
                if (exists[x][y]) {
                    result.add(Arrays.asList(x, y));
                    break;
                }
        return result;
    }

    // dp,错的。
    public int dieSimulator(int n, int[] rollMax) {
        int result = 0;
        int[][] dp = new int[n + 1][6];
        int mod = (int)Math.pow(10, 9) + 7;
        dp[1][1] = 6;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0)
                    dp[i][j] = 1;
                else if (i == 1 && j == 1)
                    continue;
                else if (i < j)
                    break;
                else {
                    int nextCount = 0;
                    for (int max: rollMax)
                        if (max > i)
                            nextCount++;
                    dp[i][j] = dp[i - 1][j - 1] + nextCount;
                }
            }
        }
        for (int res: dp[n]) {
            result += res;
            result %= mod;
        }
        return result;
    }

    // 贪心算法, 局部最优,但复杂度是 n ^ 2,可以优化(这里的反向取最优,也可以正向取最优)
    public int jump(int[] nums) {
        int target = nums.length - 1;
        if (target == 0)
            return 0;
        int current = 0;
        int count = 0;
        while (true)
            for (int i = 0; i < nums.length; i++)
                if (nums[i] + i >= target) {
                    if (i == 0)
                        return count + 1;
                    target = i;
                    count++;
                    break;
                }
    }

    // 正向局部最优,时间复杂度为 n
    public int jump1(int[] nums) {
        int maxPosition = 0;
        int end = 0;
        int steps = 0;
        for (int i = 0; i < nums.length; i++) {
            maxPosition = Math.max(maxPosition, nums[i] + i);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

//    public ListNode reverseList(ListNode head) {
//        if (head == null)
//            return head;
//        ListNode currentHead = head;
//        ListNode newHead = head;
//        ListNode tail = head;
//        while (tail.next != null) {
//            newHead = tail.next;
//            tail.next = newHead.next;
//            newHead.next = currentHead;
//            currentHead = newHead;
//        }
//        return newHead;
//    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1)
            return head;
        int num = 0;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;       // 是用来判断是否剩下的子链长度不足k
        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++)
                end = end.next;
            if (end == null)
                break;
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;        // 直接改变对象,作用是与后续链表断开(因为reverseList方法默认反转全部)
            pre.next = reverseList(start);
            start.next = next;      // 翻转后,start已经到达了那一段的结尾(重新连接后半段)
            pre = start;
            end = start;
        }

        return dummy.next;
    }

    public ListNode reverseList11(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public ListNode removeElements1(ListNode head, int val) {
         if (head == null)
            return head;
        ListNode current = head;
        ListNode ttt = new ListNode(999);
        while (current.next != null) {
            if (current.next.val == val)
                current.next = ttt;
            else
                current = current.next;
            System.out.println(current.next == null ? 0 : current.next.val);
            ttt = new ListNode(888);
            System.out.println(current.next == null ? 0 : current.next.val);
        }
        ListNode result = head;
        if (head.val == val)
            result = head.next;
        return result;
    }

    public ListNode deleteDuplicates1(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode temp = dummy;
        while (temp.next != null && temp.next.next != null) {
            int tempVal = temp.next.val;
            int nextVal = temp.next.next.val;
            if (tempVal == nextVal) {
                ListNode current = temp.next;
                while (current != null && current.val == tempVal)
                    current = current.next;
                temp.next = current;
            }
            else
                temp = temp.next;
        }

        return dummy.next;
    }

    public ListNode partition(ListNode head, int x) {
        ListNode list1 = new ListNode(0);
        ListNode list2 = new ListNode(0);
        ListNode temp1 = list1;
        ListNode temp2 = list2;
        ListNode current = head;
        while (head != null) {
            if (head.val < x) {
                temp1.next = new ListNode(head.val);
                temp1 = temp1.next;
            }
            else {
                temp2.next = new ListNode(head.val);
                temp2 = temp2.next;
            }
            head = head.next;
        }
        list1 = list1.next;
        list2 = list2.next;
        if (list1 == null)
            return list2;
        ListNode temp11 = list1;
        while (temp11.next != null)
            temp11 = temp11.next;
        temp11.next = list2;
        return list1;
    }

    // 817
    public int numComponents(ListNode head, int[] G) {
        HashSet<Integer> set = new HashSet<>();
        // 一开始不知道为什么,会使用list,速度奇慢,改用哈希速度快了20倍
        // Integer[] g = Arrays.stream(G).boxed().toArray(Integer[]::new);
        // ArrayList<Integer> list = new ArrayList<>(Arrays.asList(g));
        for (int i: G)
            set.add(i);
        int count = 0;
        while (head.next != null) {
            if (set.contains(head.val) && !set.contains(head.next.val))
                count++;
            head = head.next;
        }
        if (set.contains(head.val))        // now it's the tail
            count++;
        return count;
    }

    // 817, 由于G的长度限制为10000,可以使用bitmap,比哈希还要快
    public int numComponents1(ListNode head, int[] G) {
        int[] ints = new int[10000];
        for (int i: G)
            ints[i]++;
        boolean pre = false;
        boolean current = false;
        int count = 0;
        while (head != null) {
            current = ints[head.val] > 0;
            if (!pre && current)
                count++;
            pre = current;
            head = head.next;
        }
        return count;
    }

    // 725
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] result = new ListNode[k];
        int length = 0;
        ListNode head = root;
        while (head != null) {
            length++;
            head = head.next;
        }
        int eachPart = length / k;
        int rest = length % k;
        head = root;
        int i = 1, j = 0;
        ListNode begin = head;
        ListNode end = head;
        while (j < k) {
            if (rest != 0 && i % (eachPart + 1) == 0) {
                ListNode endNext = end.next;
                end.next = null;        // 断开
                result[j++] = begin;
                begin = end = endNext;
                i = 1;
                rest--;
                continue;   // must continue, or end may be null, then nullPointer Exception
            }
            else if (eachPart == 0 && rest == 0) {  // end == null;
                result[j++] = null;
                continue;
            }
            else if (rest == 0 && i % eachPart == 0) {
                ListNode endNext = end.next;
                end.next = null;
                result[j++] = begin;
                begin = end = endNext;
                i = 1;
                continue;
            }

            end = end.next;
            i++;
        }
        return result;
    }

    // 328
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead=  even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    // 对于链表,获取最后一个元素的效率太低了(在没有tail指针的情况下)   也可以自己加一个tail指针
    // 转换成数组(ArrayList),效率提升非常大。 但还有更好的方法
    public void reorderList(ListNode head) {
        ListNode pre = new ListNode(-1);
        btReorderList(pre, head);
        head = pre.next;
    }

    public void btReorderList(ListNode result, ListNode head) {
        if (head == null)
            return;
        else {
            ListNode first = head;
            ListNode second = head.next;
            ListNode end = second;
            while (head.next != null) {
                if (head.next.next == null) {
                    end = head.next;
                    head.next = null;
                    break;
                }
                head = head.next;
            }
            ListNode res = result;
            while (res.next != null)
                res = res.next;
            res.next = first;
            if (first != end) {
                res = res.next;
                res.next = end;
                res = res.next;
            }
            btReorderList(result, second);
        }
    }

    // 仍然是递归,但增加了尾指针,效率比数组还快(毕竟省去了转换成数组的时间),还有更快的
    public void reorderList1(ListNode head) {

        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        int len = 0;
        ListNode h = head;
        //求出节点数
        while (h != null) {
            len++;
            h = h.next;
        }

        reorderListHelper(head, len);
    }

    private ListNode reorderListHelper(ListNode head, int len) {
        if (len == 1) {
            ListNode outTail = head.next;
            head.next = null;
            return outTail;
        }
        if (len == 2) {
            ListNode outTail = head.next.next;
            head.next.next = null;
            return outTail;
        }
        //得到对应的尾节点，并且将头结点和尾节点之间的链表通过递归处理
        ListNode tail = reorderListHelper(head.next, len - 2);
        ListNode subHead = head.next;//中间链表的头结点
        head.next = tail;
        ListNode outTail = tail.next;  //上一层 head 对应的 tail
        tail.next = subHead;
        return outTail;
    }

    // 先找到中点(可以遍历一次get length,再遍历,也可以直接快慢指针找到中点)。
    // 根据中点,分成两个链表,第一个不需要处理,第二个做翻转处理
    // 然后第二个链表以此插入到第一个链表的缝隙中(如果length为奇数,那么会多出一个,一定会在最后,直接添加到最后即可
    public void reorderList2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        //找中点，链表分成两个
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode newHead1 = slow.next;
        slow.next = null;

        //第二个链表倒置
        newHead1 = reverseList111(newHead1);

        //链表节点依次连接
        while (newHead1 != null) {
            ListNode temp = newHead1.next;
            newHead1.next = head.next;
            head.next = newHead1;
            head = newHead1.next;
            newHead1 = temp;
        }

    }

    private ListNode reverseList111(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode tail = head;
        head = head.next;

        tail.next = null;

        while (head != null) {
            ListNode temp = head.next;
            head.next = tail;
            tail = head;
            head = temp;
        }

        return tail;
    }

    // 234
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

    // 92  主要思路,找到第 m - 1个(m≠1)个元素left,第 m个元素tempBegin,反转m~n的元素之后
    // left.next = prev (左边的最后一个元素指向第n个元素), tempBegin.next = right (第m个元素指向第 n + 1个元素)
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(-1);      // 加一个 虚拟指针,处理 m为1的特殊情况
        dummy.next = head;
        ListNode current = head;
        for (int i = 1; i < m - 1; i++)
            current = current.next;
        ListNode begin = m == 1 ? current : current.next;   // 注意 m 到底是不是1,1的话需要用到dummy特殊处理
        ListNode left = m == 1 ? dummy : current;
        ListNode prev = null;
        ListNode tempBegin = begin;
        ListNode right = null;
        int times = m;
        while (begin != null) {
            ListNode nextNode = begin.next;
            if (times == n)             // 此时已经是最后一次遍历, 下一个元素就是第 n + 1个元素
                right = begin.next;
            begin.next = prev;
            prev = begin;
            begin = nextNode;
            if (times == n + 1)
                break;
            times++;
        }
        left.next = prev;
        tempBegin.next = right;
        return dummy.next;
    }

    // 114
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

    // 1019
    public int[] nextLargerNodes(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[] ints = list.stream().mapToInt(Integer::intValue).toArray();
        ArrayList<Integer> res = nextGreaterElement(ints);
        Collections.reverse(res);
        return res.stream().mapToInt(Integer::valueOf).toArray();
    }

    // 这个变成了后面最大的元素,用链表存储结果看来还是不行！
    public int[] nextLargerNodes1(ListNode head) {
        ArrayList<Integer> result = new ArrayList<>();
        LinkedList<Integer> stack = new LinkedList<>();
        ListNode head1 = head;
        while (head1 != null) {
            if (stack.isEmpty()) {
                result.add(0);
                stack.addFirst(head1.val);
                head1 = head1.next;
                continue;
            }
            int nextElement = head1.val;
            int times = 0;
            while (!stack.isEmpty() && nextElement > stack.getFirst()) {
                int current = result.get(result.size() - times - 1);
                if (current == 0) {
                    stack.removeFirst();
                    result.set(result.size() - times - 1, nextElement);
                }
                times++;
            }
            stack.addFirst(nextElement);
            result.add(0);      // the nextElement
            head1 = head1.next;
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    // 168
    public String convertToTitle(int n) {
        if (n == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            sb.append((char)((n % 26) + 'A'));
            n /= 26;
        }
        return sb.reverse().toString();
    }

    // 171
    public int titleToNumber(String s) {
        int result = 0;
        for (int i = s.length() - 1; i >= 0; i--)
            result += Math.pow(26, s.length() - 1 - i) * (int)(s.charAt(i) - 'A' + 1);
        return result;
    }

    // 1171
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;              // dummy结点指向head
        ListNode prev = dummy;          // prev表示可能移除的连续节点的前一个结点
        ListNode begin = head;          // begin表示移除的连续结点的第一个结点
        boolean flag = false;           // 如果移除成功,对dummy.next进行递归
        int sum = 0;
        while (begin != null) {
            sum = 0;
            flag = false;
            ListNode cBegin = begin;    // cBegin为begin的一个临时引用,用于从begin开始向后遍历

            while (cBegin != null) {
                sum += cBegin.val;
                if (sum == 0) {
                    prev.next = cBegin.next;    // sum为0之后,prev指向了当前cBegin的下一个结点
                    flag = true;
                    break;
                }
                cBegin = cBegin.next;
            }
            if (flag)
                return removeZeroSumSublists(dummy.next);
            else {
                prev = prev.next;       // begin开始的连续结点,不存在总和为0,从next开始重新遍历
                begin = prev.next;
            }
        }
        return dummy.next;
    }

    public int numDecodings(String s) {
        int length = s.length();
        int[] dp = new int[length + 1];
        for (int i = 1; i <= length; i++) {
            if (i == 1) {
                dp[i] = s.charAt(length - 1) == '0' ? 0 : 1;
                continue;
            }
            if (i == 2) {
                if (s.charAt(length - 1) != '0' && (s.charAt(length - 2) == '1' || (s.charAt(length - 2) == '2'
                        && s.charAt(length - 1) <= '6')))
                    dp[i] = 2;
                else {
                    if (s.charAt(length - 2) == '0' || (s.charAt(length - 1) == '0' && s.charAt(length - 2) >= '3'))
                        dp[i] = 0;
                    else
                        dp[i] = 1;
                }
                continue;
            }
            if (s.charAt(length - i) == '1' || (s.charAt(length - i) == '2' && s.charAt(length - i + 1) <= '6'))
                dp[i] = dp[i - 1] + dp[i - 2];
            else
                dp[i] = s.charAt(length - i) == '0' ? 0 : dp[i - 1];
        }
        return dp[length];
    }

    // 84, n ^ 3,太暴力了,not pass
    public int largestRectangleArea(int[] heights) {
        int sum = 0, height = 1, current = 0;
        Integer[] integers = Arrays.stream(heights).boxed().toArray(Integer[]::new);
        HashSet<Integer> set = new HashSet<>(Arrays.asList(integers));
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            height = it.next();
            for (int i = 0; i < heights.length; i++) {
                if (heights[i] >= height) {
                    current = height;
                    for (int j = i + 1; j < heights.length; j++) {
                        if (heights[j] >= height)
                            current += height;
                        else {
                            i = j - 1;
                            break;
                        }
                    }
                    sum = sum > current ? sum : current;
                }
            }
            height++;
        }
        return sum;
    }

    // 使用一个单调栈,时空复杂度均为n.  (主要思路,只遍历一遍,使得包含第n个矩形的两边左右延长,获取那个矩形的面积
    // 基本思路：https://blog.csdn.net/Zolewit/article/details/88863970
    public int largestRectangleArea1(int[] heights) {
        LinkedList<Integer> stack = new LinkedList<>();
        int sum = 0;
        stack.addFirst(-1);     // -1使得后续代码简化不少
        for (int i = 0; i < heights.length; i++) {
            while ((stack.getFirst() != -1 && heights[i] <= heights[stack.getFirst()])) {
                int current = heights[stack.removeFirst()] * (i - stack.getFirst() - 1);
                sum = sum > current ? sum : current;
            }
            stack.addFirst(i);
        }
        while (stack.getFirst() != -1)          // 用于处理最后一个元素的情况
            sum = Math.max(sum, heights[stack.removeFirst()] * (heights.length - stack.getFirst() - 1));
        return sum;
    }

    // 101      递归解法
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;        // 空树
        return isSymmetricHelper(root.left, root.right);
    }

    public boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;
        else if (left.val != right.val)
            return false;
        else
            return isSymmetricHelper(left.left, right.right) && isSymmetricHelper(left.right, right.left);
    }

    // 179
    public String largestNumber(int[] nums) {
        boolean flag = false;
        for (int i: nums)
            if (i != 0) {
                flag = true;
                break;
            }
        if (!flag)
            return "0";         // 全是0的情况
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        for (int num: nums)
            list.add(String.valueOf(num));
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int first = o2.charAt(0) - o1.charAt(0);
                if (first != 0)
                    return first;
                String temp = o1;
                o1 += o2;
                o2 += temp;
                int i = 1;
                int length = o1.length();
                while (i < length) {
                    first = o2.charAt(i) - o1.charAt(i);
                    i++;
                    if (first == 0)
                        continue;
                    else
                        return first;
                }
                return first;
            }
        });
        System.out.println(list);
        for (String str: list)
            sb.append(str);
        return sb.toString();
    }

    // 105
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0)
            return null;
        int rootVal = preorder[0];
        int i;
        for (i = 0; i < inorder.length; i++)
            if (inorder[i] == rootVal)
                break;
        TreeNode root = new TreeNode(rootVal);
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, i);
        int[] rightInorder = Arrays.copyOfRange(inorder, i + 1, inorder.length);
        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, leftInorder.length + 1);
        int[] rightPreorder = Arrays.copyOfRange(preorder, 1 + leftPreorder.length, preorder.length);
        root.left = buildTree(leftPreorder, leftInorder);
        root.right = buildTree(rightPreorder, rightInorder);
        return root;
    }

    // 108
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0)
            return null;
        int middle = nums[nums.length / 2];
        TreeNode root = new TreeNode(middle);
        int[] left = Arrays.copyOf(nums, nums.length / 2);
        int[] right = Arrays.copyOfRange(nums, nums.length / 2 + 1, nums.length);
        root.left = sortedArrayToBST(left);
        root.right = sortedArrayToBST(right);
        return root;
    }

    // 125  更快的应该是双指针慢慢遍历, 如果遇到大写字母,就转成小写.如果遇到小写&数字,就加入到char数组(其他都pass
    // 最后再对char进行双指针遍历。
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("[^a-z0-9]", "");
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString().equals(s);
    }

    class Node1 {
        public int val;
        public Node1 left;
        public Node1 right;
        public Node1 next;

        public Node1() {}

        public Node1(int _val,Node1 _left,Node1 _right,Node1 _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    // 116
    public Node1 connect(Node1 root) {
        if (root == null || root.left == null || root.right == null)
            return root;
        root.left.next = root.right;
        if (root.next != null)
            root.right.next = root.next.left;
        connect(root.left);
        connect(root.right);
        return root;
    }

    // 112
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null && root.val == sum) // 当前结点必须是叶子结点
            return true;
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    // 113
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        hasPathSumHelper(root, sum, current, result);
        return result;
    }

    public void hasPathSumHelper(TreeNode root, int sum, List<Integer> current, List<List<Integer>> result) {
        if (root == null)
            return;
        current.add(root.val);
        if (root.left == null && root.right == null && root.val == sum)
            result.add(new ArrayList<>(current));
        hasPathSumHelper(root.left, sum - root.val, current, result);
        hasPathSumHelper(root.right, sum - root.val, current, result);
        current.remove(current.size() - 1);     // 左右子树遍历完,才remove当前的root结点
    }

    // 128
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0)
            return 0;
        Arrays.sort(nums);
        int length = 1, max = 1;
        for (int i = 0; i < nums.length - 1; i++)
            if (nums[i] == nums[i + 1] - 1)
                length++;
            else if (nums[i] == nums[i + 1])
                continue;
            else {
                max = max > length ? max : length;
                length = 1;
            }
        max = max > length ? max : length;
        return max;
    }

    // 347
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num: nums) {
            Integer i = map.get(num);
            map.put(num, i == null ? 1 : i + 1);
        }
        // 对HashMap根据Value排序
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++)
            result.add(list.get(i).getKey());
        return result;
    }

    // 387      直接用哈希表
    public int firstUniqChar(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        int i = 0;
        for (char c: s.toCharArray()) {
            if (set.contains(c)) {
                map.put(c, Integer.MIN_VALUE);
                i++;
            }
            else {
                set.add(c);
                map.put(c, i++);
            }
        }
        int result = Integer.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry: map.entrySet())
            if (entry.getValue() >= 0)
                result = result < entry.getValue() ? result : entry.getValue();
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // 387改进,由于是找字符,直接遍历26个即可！没有重复的条件 indexOf == lastIndexOf !!
    public int firstUniqChar1(String s) {
        int index = -1;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            int beginIndex = s.indexOf(ch);
            if (beginIndex != -1 && beginIndex == s.lastIndexOf(ch))
                index = (index == -1 || index > beginIndex) ? beginIndex : index;
        }
        return index;
    }

    // 739
    public int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = T.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && T[stack.getFirst()] <= T[i])     // 与496相比,存储的是index而不是值
                stack.removeFirst();
            res[i] = stack.isEmpty() ? 0 : stack.getFirst() - i;
            stack.addFirst(i);
        }
        return res;
    }

    // 647 DP
    public int countSubstrings(String s) {
        int res = 0;
        boolean[][] dp = countSubstringsHelper(s);
        for (int i = 0; i < dp.length; i++)
            for (int j = 0; j < dp[0].length; j++)
                if (dp[i][j])
                    res++;
        return res;
    }

    // DP方程: dp[i][j] == true (when i == j)     i表示左边第一位, j表示右边第一位.
    // dp[i][j] ==  true  (when i == j - 1 (子串长度为2), 或者dp[i][j] == dp[i + 1][j - 1],左右各靠近一位)
    public boolean[][] countSubstringsHelper(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int j = 0; j < s.length(); j++)
            for (int i = 0; i <= j; i++)
                if (i == j)
                    dp[i][j] = true;
                else if (s.charAt(i) == s.charAt(j) && (j - i == 1 || dp[i + 1][j - 1]))
                    dp[i][j] = true;
        return dp;
    }

    // 1143
    public int longestCommonSubsequence(String text1, String text2) {
        int length1 = text1.length();
        int length2 = text2.length();
        int[][] dp = new int[length1 + 1][length2 + 1];
        // 不需要考虑临界值, 因为i, j从1开始,即便是临界值,也是符合这个DP方程的.
        for (int i = 1; i <= length1; i++)
            for (int j = 1; j <= length2; j++)
                if (text1.charAt(i - 1) != text2.charAt(j - 1))
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                else
                    dp[i][j] = dp[i - 1][j - 1] + 1;
        return dp[length1][length2];
    }

    // 376 贪心算法
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2)
            return nums.length;
        int current = nums[0];
        boolean first = true;
        boolean flag = false;   // 这里初始化为什么都无所谓,反正first的时候会重置
        // 但不初始化编译器会以为flag在使用的时候可能没有初始化,只好礼貌性初始化一哈了
        // true for nums[i] - nums[prev] > 0,  false for nums[i] - nums[prev] < 0
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == current)
                continue;
            if (first) {
                flag = nums[i] - current > 0 ? true : false;
                first = false;
                res++;
                current = nums[i];
                continue;
            }
            if (((nums[i] < current) && flag) || ((nums[i] > current) && !flag)) {
                flag = !flag;
                res++;
                current = nums[i];
            }
            if ((flag && nums[i] > current) || (!flag && nums[i] < current))
                current = nums[i];
            // 不能找到一个current就满足,要找到最好的current,即上升序列时,如果是连续的上升,current应该一直取最大的那个新值
        }
        return res;
    }

    // 376 DP
    public int wiggleMaxLength1(int[] nums) {
        int n = nums.length;
        if (n < 2)
            return n;
        int up = 1, down = 1;
        for (int i = 1; i < n; i++)
            if (nums[i] > nums[i - 1])
                up = down + 1;
            else if (nums[i] < nums[i - 1])
                down = up + 1;
        return Math.max(up, down);
    }

    // 491
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 2)
            return result;
        List<Integer> current = new ArrayList<>();
        HashSet<List<Integer>> set = new HashSet<>();
        findSubsequencesHelper(set, current, nums,0, Integer.MIN_VALUE);
        result = new ArrayList<>(set);
        return result;
    }

    public void findSubsequencesHelper(HashSet<List<Integer>> result, List<Integer> current, int[] nums,
                                       int nextIndex, int prev) {
        if (current.size() >= 2)
            result.add(new ArrayList<>(current));

        HashSet<Integer> set = new HashSet<>();         // 记录本层的情况,本层如果重复是可以剪枝的
        for (int i = nextIndex; i < nums.length; i++) {
            // 一开始想着直接在if里判断nums[i] == nums[i - 1],但这并不正确
            // 我们需要减去的是同一层的重复元素,但不同层是可以相同的
            // 如果重复元素越多,set做出的剪枝提升会越明显
            if (nums[i] < prev || set.contains(nums[i]))
                continue;
            current.add(nums[i]);
            set.add(nums[i]);
            findSubsequencesHelper(result, current, nums,i + 1, nums[i]);
            current.remove(current.size() - 1);
        }
    }

    // 51
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n < 0)
            return res;
        List<String> current = new ArrayList<>();
        if (n == 0) {
            res.add(current);
            return res;
        }
        boolean[][] conflict = new boolean[n][n];
        solveNQueensHelper(res, current, conflict, 0, n);
        return res;
    }

    // 实际上a[i][j], 不需要八个方向都考虑，考虑上面的3个方向即可,因为我们是顺序递归
    public boolean checkConflict(boolean[][] conflict, int i, int j, int n) {
        //int[][] increments = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        int[][] increments1 = {{-1, 0}, {-1, -1}, {-1, 1}};
        for (int times = 0; times < increments1.length; times++) {
            int tempI = i, tempJ = j;
            while (tempI >= 0 && tempI < n && tempJ >= 0 && tempJ < n) {
                if (conflict[tempI][tempJ])
                    return false;
                tempI += increments1[times][0];
                tempJ += increments1[times][1];
            }
        }
        return true;
    }

    public void solveNQueensHelper(List<List<String>> result, List<String> current, boolean[][] conflict,
                                   int row, int n) {
        if (row == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        String row1 = "";
        for (int i = 0; i < n; i++) {
            if (checkConflict(conflict, row, i, n)) {
                conflict[row][i] = true;
                row1 += "Q";
                while (row1.length() < n)
                    row1 += ".";
                current.add(row1);
                solveNQueensHelper(result, current, conflict, row + 1, n);
                current.remove(current.size() - 1);
                conflict[row][i] = false;
                if (row1.length() != 1)                 // 这个只是针对n == 1的情况,也可以另外特殊处理n == 1,没区别
                    row1 = row1.split("Q")[0];
                row1 += ".";
            }
            else if (i == n - 1) {
                return;     // 返回上一层
            }
            else
                row1 += ".";
        }
    }

    // 64   classic DP
    public int minPathSum(int[][] grid) {
        if (grid == null)
            return 0;
        int m = grid.length;
        if (m == 0)
            return 0;
        int n = grid[0].length;
        if (n == 0)
            return 0;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        for (int x = m - 2; x >= 0; x--)
            dp[x][n - 1] = dp[x + 1][n - 1] + grid[x][n - 1];
        for (int y = n - 2; y >= 0; y--)
            dp[m - 1][y] = dp[m - 1][y + 1] + grid[m - 1][y];
        for (int x = m - 2; x >= 0; x--)
            for (int y = n - 2; y >= 0; y--)
                dp[x][y] = Math.min(dp[x + 1][y], dp[x][y + 1]) + grid[x][y];
        return dp[0][0];
    }

    // 71
    public String simplifyPath(String path) {

        LinkedList<Character> stack = new LinkedList<>();
        int length = path.length();
        for (int i = 0; i < length; i++) {
            char str = path.charAt(i);
            if (stack.isEmpty())
                stack.addLast(str);
            else if (stack.getLast() == '/' && str == '/')      // 去掉重复的'/'
                continue;
            else if (stack.getLast() == '/' && str == '.') {
                if (i == length - 1 || path.charAt(i + 1) != '.')  // 去掉 /.
                    if (i == length - 1 || path.charAt(i + 1) == '/')      //   /./才正确
                        stack.removeLast();
                    else {
                        stack.addLast(str);
                        continue;
                    }
                else if (i < length - 2 && path.charAt(i + 1) == '.' && path.charAt(i + 2) == '.')  // 多于两个.的情况
                    stack.addLast(str);
                else if (i != length - 1 && path.charAt(i + 1) == '.') {
                    if (i < length - 2 && path.charAt(i + 2) != '/') {   // 并不是/../的情况,而是/..abc/的情况
                        stack.addLast(str);
                        continue;
                    }
                    stack.removeLast();         // 先去掉一个/
                    while (!stack.isEmpty() && stack.getLast() != '/')      // isEmpty防止开头就是/..的情况
                        stack.removeLast();     // 去掉路径名
                    if (!stack.isEmpty())
                    stack.removeLast();         // 去掉再上一个的/,即到达了上一层路径
                    i++;        // 直接跳过下一个 '.'
                }
            }
            else
                stack.addLast(str);     // 路径的length不是1的情况,比如 /abc/defgh
        }
        if (stack.size() == 0)      // 不仅仅是path.length()为0的情况,比如还有/.. , /a/..等等的情况
            return "/";
        if (stack.size() != 1 && stack.getLast() == '/')
            stack.removeLast();
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(stack.removeFirst());
        return sb.toString();
    }

    // 71 参考Python的一个写法     好像并不成功,下次再研究
    public String simplifyPath1(String path) {
        LinkedList<String> stack = new LinkedList<>();
        for (String str: path.split("/")) {
            if (str != "" || str != "." || str != "..")
                stack.addLast(str);
            else if (!stack.isEmpty() && str == "..")
                stack.removeLast();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        while (!stack.isEmpty()) {
            sb.append(stack.removeLast());
            sb.append("/");
        }
        while (sb.length() != 1 && sb.charAt(sb.length() - 1) == '/')  // 去掉最后的'/'
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public int missingNumber(int[] arr) {
        int first = arr[0], second = arr[1], third = arr[2];
        int diff1 = Math.abs(second - first);
        int diff2 = Math.abs(third - second);
        if (diff1 > diff2)
            return (first + second) / 2;
        else if (diff1 < diff2)
            return (second + third) / 2;
        int diff = second - first;
        for (int i = 3; i < arr.length; i++)
            if (arr[i] - arr[i - 1] != diff)
                return (arr[i] + arr[i - 1]) / 2;
        return arr[0];
    }

    public double probabilityOfHeads(double[] prob, int target) {
        BigDecimal result = new BigDecimal(Double.toString(prob[0]));
        for (int i = 1; i < prob.length; i++)
            result = result.multiply(new BigDecimal(Double.toString(prob[i])));
        return result.doubleValue();
    }

    public boolean checkStraightLine(int[][] coordinates) {
        int x1 = coordinates[0][0], y1 = coordinates[0][1], x2 = coordinates[1][0], y2 = coordinates[1][1];
        int k = 0, b = 0;
        if (x1 == x2) {
            for (int i = 2; i < coordinates.length; i++)
                if (coordinates[i][0] != x1)
                    return false;
            return true;
        }
        else {
            k = (y2 - y1) / (x2 - x1);
            b = y2 - k * x2;
        }
        for (int i = 2; i < coordinates.length; i++)
            if (coordinates[i][1] != k * coordinates[i][0] + b)
                return false;
        return true;
    }

    public List<String> removeSubfolders(String[] folder) {
        HashSet<String> set = new HashSet<>();
        for (String str: folder) {
            if (str.equals("/"))
                continue;
            String[] paths = str.split("\\/");
            String temp = "";
            for (int i = 0; i < paths.length; i++) {
                temp += "/" + paths[i];
            }
        }
        return null;
    }

    public int balancedString(String s) {
//        int res = 0;
//        int eachLength = s.length() / 4;
//        HashMap<Character, Integer> map = new HashMap<>();
//        for (char c: s.toCharArray()) {
//            Integer i = map.get(c);
//            map.put(c, i == null ? 1 : i + 1);
//        }
//        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
//            int length = entry.getValue();
//            res += length > eachLength ? (length - eachLength) : 0;
//        }
//        return res;
        int length = s.length();
        int res = 0;
        for (int i = 0; i * 4 < length; i++) {
            if (s.charAt(i * 4) != s.charAt(i * 4 + 1) && s.charAt(i * 4 + 1) != s.charAt(i * 4 + 2) &&
                    s.charAt(i * 4 + 2) != s.charAt(i * 4 + 3))
                res = res > 1 ? res : 1;
            else if (s.charAt(i * 4) == s.charAt(i * 4 + 1) && s.charAt(i * 4 + 1) == s.charAt(i * 4 + 2) &&
                    s.charAt(i * 4 + 2) == s.charAt(i * 4 + 3))
                res = 1;
        }
        return res;
    }

    // 788  每位都在[2,5,6,9,0,1,8]中,且至少有一位在[2,5,6,9]中  但显然,这样慢慢replace会很慢
    public int rotatedDigits(int N) {
        int res = 0;
        for (int i = 2; i <= N; i++) {
            String s = String.valueOf(i);
            s = s.replaceAll("[018]", "");
            if (!"".equals(s)) {
                s = s.replaceAll("[2569]", "");
                if ("".equals(s))
                    res++;
            }
        }
        return res;
    }

    // 788  同样的逻辑,速度比上面快了几十倍,所以并不是代码越简单越好的。
    public int rotatedDigits1(int N) {
        int res = 0;
        int temp, digit = 0;
        boolean flag, flag1;        // flag for whether one digit is [2,5,6,9]
        for (int i = 2; i <= N; i++) {
            flag = false;
            flag1 = true;
            temp = i;
            while (temp != 0) {
                digit = temp % 10;     // the digit to check
                temp /= 10;
                if (digit == 2 || digit == 5 || digit == 6 || digit == 9)
                    flag = true;
                else if (digit == 3 || digit == 4 || digit == 7) {
                    flag1 = false;
                    break;
                }
            }
            if (flag && flag1)
                res++;
        }
        return res;
    }

    // 728
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new ArrayList<>();
        boolean flag;
        for (int i = left; i <= right; i++) {
            flag = true;
            String string = String.valueOf(i);
            char[] chars = string.toCharArray();
            for (char c: chars) {
                String s = String.valueOf(c);
                int i1 = Integer.valueOf(s);
                if (i1 == 0 || i % i1 != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                res.add(i);
        }
        return res;
    }

    // 1185
    public String dayOfTheWeek(int day, int month, int year) {
        int daySumBeforeThisYear = 0;
        String[] result = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (int i = 1971; i < year; i++)
            if (ifLeapYear(i))
                daySumBeforeThisYear += 366;
            else
                daySumBeforeThisYear += 365;
        int thisYearFirstDay = (daySumBeforeThisYear % 7 + 5) % 7;
        int thisYearDay = 0;
        for (int i = 1; i < month; i++)
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10)
                thisYearDay += 31;
            else if (i == 4 || i == 6 || i == 9 || i == 11)
                thisYearDay += 30;
            else if (i == 2)
                if (ifLeapYear(year))
                    thisYearDay += 29;
                else
                    thisYearDay += 28;
        thisYearDay += day;
        thisYearDay--;      // 减去第一天
        int res = (thisYearDay % 7 + thisYearFirstDay) % 7;
        return result[res];
    }

    public boolean ifLeapYear(int year) {
        if (year % 100 != 0 && year % 4 == 0)
            return true;
        else if (year % 100 == 0 && year % 400 == 0)
            return true;
        return false;
    }

    // 1128     用bitmap更快
    public int numEquivDominoPairs(int[][] dominoes) {
        HashMap<String, Integer> map = new HashMap<>();
        String str;
        for (int i = 0; i < dominoes.length; i++) {
            int first = dominoes[i][0], second = dominoes[i][1];
            if (first < second)
                str = dominoes[i][0] + "" + dominoes[i][1];
            else
                str = dominoes[i][1] + "" + dominoes[i][0];
            Integer integer = map.get(str);
            map.put(str, integer == null ? 1 : integer + 1);
        }
        int res = 0;
        for (Map.Entry<String, Integer> entry: map.entrySet())
            res += entry.getValue() * (entry.getValue() - 1) / 2;
        return res;
    }

    // 1128 bitmap
    public int numEquivDominoPairs1(int[][] dominoes) {
        int[][] map = new int[10][10];
        for (int i = 0; i < dominoes.length; i++) {
            int first = dominoes[i][0], second = dominoes[i][1];
            if (first < second)
                map[first][second]++;
            else
                map[second][first]++;
        }
        int res = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                res += map[i][j] * (map[i][j] - 1) / 2;
        return res;
    }

    // 621      getMax,每次都要重新排序,可以预见的效率奇低,但确实能完成任务,而且并不是套公式直接get答案
    // 改用优先队列应该会快很多. TODO
    public int leastInterval(char[] tasks, int n) {
        HashMap<Character, Integer> waiting = new HashMap<>();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char task: tasks) {
            Integer integer = map.get(task);
            map.put(task, integer == null ? 1 : integer + 1);
            waiting.put(task, 0);
        }
        int time = 0;
        while (map.size() != 0) {
            getMax(map, waiting, n);
            time ++;
        }
        return time;
    }

    public void getMax(HashMap<Character, Integer> map, HashMap<Character, Integer> waiting, int n) {
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        char doTask = 'a';
        boolean canDo = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            Map.Entry<Character, Integer> entry = list.get(i);
            doTask = entry.getKey();
            if (waiting.get(doTask) > 0)
                continue;
            else {
                canDo = true;
                int count = entry.getValue();
                if (count == 1)
                    map.remove(doTask);
                else
                    map.put(doTask, entry.getValue() - 1);
                break;
            }
        }
        for (Map.Entry<Character, Integer> entry: waiting.entrySet())
            if (entry.getValue() != 0)
                entry.setValue(entry.getValue() - 1);
        if (canDo)
            waiting.put(doTask, n);
    }

    // 560  暴力法应该把前面的sum记录下来(空间换时间),确保每一次加法都只需要使用一次
    // sum(i, j)  (i ~ j - 1的和)  ==  sum(0, j) - sum(0, i)
    public int subarraySum(int[] nums, int k) {
        int res = 0, sum;
        int length = nums.length;
        int[] prevSum = new int[length + 1];
        for (int i = 1; i <= nums.length; i++)
            prevSum[i] = prevSum[i - 1] + nums[i - 1];

        for (int i = 1; i <= nums.length; i++) {
            for (int j = i; j <= nums.length; j++) {
                if (i != j)
                    sum = prevSum[j] - prevSum[i];
                else
                    sum = prevSum[i];
                if (sum == k)
                    res++;
            }
        }
        return res;
    }

    // 不用数组,直接用HashMap存
    public int subarraySum1(int[] nums, int k) {
        /**
         扫描一遍数组, 使用map记录出现同样的和的次数, 对每个i计算累计和sum并判断map内是否有sum-k
         **/
        Map<Integer, Integer> map = new HashMap<>(nums.length * 2);     // 省去扩容的消耗
        map.put(0, 1);          // 空数组可以表示sum为0的情况
        int sum = 0, ret = 0;

        for(int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            if(map.containsKey(sum - k))
                ret += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return ret;
    }

    // 130  用DFS,先对边界的'O'进行DFS,使得与其连通的'O'更改为'-'.
    // 接着再遍历,没有连通的'O'直接替换成'X',而连通的'O',已经更改为'-',改回'O'
    //      也可以用并查集 disjointSet
    public void solve(char[][] board) {
        int row = board.length;
        if (row == 0)
            return;
        int column = board[0].length;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                if (i == 0 || j == 0 || i == row - 1 || j == column - 1)
                    dfs(board, i, j, row, column);      // 对边界的'O'进行DFS
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if (board[i][j] == '-')
                    board[i][j] = 'O';
    }

    public void dfs(char[][] board, int i, int j, int row, int column) {
        if (i < 0 || j < 0 || i >= row || j >= column || board[i][j] != 'O')
            return;
        board[i][j] = '-';
        dfs(board, i - 1, j, row, column);
        dfs(board, i + 1, j, row, column);
        dfs(board, i, j - 1, row, column);
        dfs(board, i, j + 1, row, column);
        return;
    }

    // 200
    public int numIslands(char[][] grid) {
        int row = grid.length;
        if (row == 0)
            return 0;
        int column = grid[0].length;
        int res = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j, row, column);
                }
        return res;
    }

    // 695
    public int maxAreaOfIsland(int[][] grid) {
        int row = grid.length;
        if (row == 0)
            return 0;
        int column = grid[0].length;
        int current = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                if (grid[i][j] == 1)
                    dfs(grid, i, j, row, column, current++, list);
        int res = 0;
        for (int area: list)
            res = res > area ? res : area;
        return res;
    }

    public void dfs(int[][] grid, int i, int j, int row, int column, int current, List<Integer> list) {
        if (i < 0 || j < 0 || i >= row || j >= column || grid[i][j] != 1)
            return;
        grid[i][j] = 0;
        if (current == list.size())
            list.add(1);
        else {
            int prev = list.remove(list.size() - 1);
            list.add(prev + 1);
        }
        dfs(grid, i - 1, j, row, column, current, list);
        dfs(grid, i + 1, j, row, column, current, list);
        dfs(grid, i, j - 1, row, column, current, list);
        dfs(grid, i, j + 1, row, column, current, list);
    }

    // 152  DP
    public int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {      // 记录最小,最大. 当下一个值为负数,会导致最大的变成最小,最小变成最大.
                int temp = imax;
                imax = imin;
                imin = temp;
            }
            imax = Math.max(nums[i], imax * nums[i]);
            imin = Math.min(nums[i], imin * nums[i]);
            max = Math.max(max, imax);
        }
        return max;
    }

    // 198  DP
    public int rob(int[] nums) {
        int[] dp = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++)
            if (i == 1)
                dp[i] = nums[i - 1];
            else if (i == 2)
                dp[i] = Math.max(nums[0], nums[1]);
            else
                dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 1]);
        return dp[nums.length];
    }

    // 213  问题退化成1~ n-1, 2~n 的最大值
    public int rob1(int[] nums) {
        if (nums.length == 0)
            return 0;
        if (nums.length == 1)       // 1也是特殊情况了,否则还得在helper方法里对i == 1进行特殊判定,更麻烦
            return nums[0];
        return Math.max(rob(nums, 1, nums.length - 1, true), rob(nums, 2, nums.length, false));
    }

    public int rob(int[] nums, int begin, int end, boolean isFirst) {
        int[] dp = new int[end + 1];
        for (int i = begin; i <= end; i++)
            if (i == 1)
                dp[i] = nums[i - 1];
            else if (i == 2)
                if (isFirst)
                    dp[i] = Math.max(nums[0], nums[1]); // 如果从1开始,并非一定要从Nums[1]开始,nums[2]也不会违反规则
                else
                    dp[i] = nums[1];    // 如果是从2开始,那么dp[2]只能是nums[1]
            else
                dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 1]);
        return dp[end];
    }

    // 221  暴力法
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return 0;
        int n = matrix[0].length;
        int current = 0;
        boolean flag;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    while (i + current < m && j + current < n) {
                        flag = true;
                        for (int tempI = i; tempI <= i + current && tempI < m; tempI++)
                            for (int tempJ = j; tempJ <= j + current && tempJ < n; tempJ++)
                                if (matrix[tempI][tempJ] == '0') {
                                    flag = false;
                                    break;
                                }
                        if (flag)
                            current++;
                        else
                            break;
                    }
                }
            }
        return current * current;
    }

    // 221  DP: dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])
    public int maximalSquare1(char[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int max = 0;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    max = Math.max(max, dp[i][j]);
                }
        return max * max;
    }

    // 226
    public TreeNode invertTree(TreeNode root) {
        if (root == null || root.right == null && root.left == null)
            return root;
        TreeNode left = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(left);
        return root;
    }

    // 283 普通做法,比较慢
    public void moveZeroes(int[] nums) {
        int length = nums.length;
        int count = 0;
        for (int i = length - 1; i >= 0; i--) {
            if (nums[i] != 0)
                continue;
            for (int j = i; j < length - count - 1; j++)
                nums[j] = nums[j + 1];
            nums[length - count++ - 1] = 0;
        }
    }

    // 283  双指针,先移动非0到前面,然后在最后补0即可！
    public void moveZeroes1(int[] nums) {
        int length = nums.length;
        int j = 0;
        for (int i = 0; i < length; i++)
            if (nums[i] != 0)
                nums[j++] = nums[i];        // 把非0的数放到前j位
        while (j < length)
            nums[j++] = 0;                  // 全部非0已经放置到前j位,在最后补0即可
    }

    // 300  n ^ 2   奇怪的要求
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0)
            return 0;
        int res = 1;
        for (int i = 0; i < nums.length - res; i++) {
            int temp = 1;
            int current = nums[i], prev = nums[i];
            for (int j = i + 1; j < nums.length; j++)
                if (nums[j] > current) {
                    temp++;
                    prev = current;
                    current = nums[j];
                }
                else if (nums[j] > prev)
                    current = nums[j];          // 寻找最佳的下一个上升点

            res = res > temp ? res : temp;
        }
        return res;
    }

    // 300  DP方程:  dp[i] = Math.max(dp[j] + 1, dp[i])   where j < i 且 nums[j] < nums[i] (其实就是往后去查找结果!
    public int lengthOfLIS1(int[] nums) {
        int length = nums.length;
        if (length < 2)
            return length;
        int[] dp = new int[length];
        Arrays.fill(dp, 1);         // 自己一定是一个子序列
        for (int i = 1; i < length; i++)
            for (int j = 0; j < i; j++)
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
        int res = dp[0];
        for (int d: dp)
            res = Math.max(res, d);
        return res;
    }

    // 287  使用了n空间
    public int findDuplicate(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num: nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        for (Map.Entry<Integer, Integer> entry: map.entrySet())
            if (entry.getValue() > 1)
                return entry.getKey();
        return -1;

        // 也可以直接sort,但这样又少满足了一个条件,题目要求数组不可变
//        Arrays.sort(nums);
//        for (int i = 0; i < nums.length - 1; i++)
//            if (nums[i] == nums[i + 1])
//                return nums[i];
//        return -1;
    }

    // 494      这个其实就是暴力, dont cheat yourself.
    public int findTargetSumWays(int[] nums, int S) {
        int length = nums.length;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        for (int i = 0; i < nums.length; i++)
            list = findTargetSumWaysHelper(list, i, nums);
        int res = 0;
        for (int integer: list)
            if (integer == S)
                res++;
        return res;
    }

    public ArrayList<Integer> findTargetSumWaysHelper(ArrayList<Integer> list, int index, int[] nums) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i: list) {
            temp.add(i + nums[index]);
            temp.add(i - nums[index]);
        }
        return temp;
    }

    // 461
    public int hammingDistance(int x, int y) {
        int res = x ^ y;
        String str = Integer.toBinaryString(res);
        int r = 0;
        for (char c: str.toCharArray())
            if (c == '1')
                r++;
        return r;
    }

    // 617
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null)
            return null;
        int val;
        TreeNode left1 = null, left2 = null, right1 = null, right2 = null;
        if (t1 == null) {
            val = t2.val;
            left2 = t2.left;
            right2 = t2.right;
        }
        else if (t2 == null) {
            val = t1.val;
            left1 = t1.left;
            right1 = t1.right;
        }
        else {
            val = t1.val + t2.val;
            left1 = t1.left;
            right1 = t1.right;
            left2 = t2.left;
            right2 = t2.right;
        }
        TreeNode root = new TreeNode(val);
        root.left = mergeTrees(left1, left2);
        root.right = mergeTrees(right1, right2);
        return root;
    }

    // 416      TODO    背包问题
    public boolean canPartition(int[] nums) {
        Arrays.sort(nums);
        int length = nums.length;
        int sum = 0;
        for (int num: nums)
            sum += num;
        int[] prevSum = new int[length];
        prevSum[0] = nums[0];
        for (int i = 1; i < length; i++)
            prevSum[i] = prevSum[i - 1] + nums[i];
        int[][] dp = new int[length][length];
        int current;
        for (int i = 0; i < length; i++)
            for (int j = i; j < length; j++) {
                if (i == j)
                    current = prevSum[i];
                else
                    current = prevSum[j] - prevSum[i];
                if (current * 2 == sum)
                    return true;
            }
        return false;
    }

    // 581          n ^ 2
    public int findUnsortedSubarray(int[] nums) {
        int len = nums.length;
        int left = len, right = 0;
        for (int i = 0; i < len - 1; i++)
            for (int j = i + 1; j < len; j++)
                if (nums[j] < nums[i]) {
                    right = Math.max(right, j);
                    left = Math.min(left, i);
                }
        return right - left < 0 ? 0 : right - left + 1;
    }

    // 543  没有使用全局变量,只好对两个函数都进行了递归
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null)
            return 0;
        int max = 0;
        int current = helper(root.left) + helper(root.right);
        max = max > current ? max : current;
        max = Math.max(diameterOfBinaryTree(root.left), Math.max(max, diameterOfBinaryTree(root.right)));
        return max;
    }

    public int helper(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        return 1 + Math.max(helper(root.left), helper(root.right));
    }

    // 538      中序遍历是从小到大,反过来就是从大到小了,注意累加
    int num = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            root.val = root.val + num;
            num = root.val;
            convertBST(root.left);
            return root;
        }
        return null;
    }

    // 448  时间复杂度是2n,空间复杂度是n,较慢
    public List<Integer> findDisappearedNumbers(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        List<Integer> res = new ArrayList<>();
        for (int num: nums)
            set.add(num);
        for (int i = 1; i <= nums.length; i++)
            if (!set.contains(i))
                res.add(i);
        return res;
    }

    // 448 时间复杂度是n,空间复杂度是1,并且没有使用Set操作,速度大大加快.
    // 关键思想跟41题一样,拿自身做bitmap(当然这题要简单很多)
    // 由于数组里的值是有范围的, 因而我们甚至不用担心超出范围的情况
    // (以后看到数组的范围就是 1 ~ length的时候,就应该想到拿自身bitmap (当前的值指向的index做出修改)
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = Math.abs(nums[i]);
            nums[temp - 1] = nums[temp - 1] > 0 ? -nums[temp - 1] : nums[temp - 1];
        }
        for (int i = 0; i < nums.length; i++)
            if (nums[i] > 0)
                res.add(i + 1);
        return res;
    }

    // 65 直接正则表达式
    public boolean isNumber(String s) {
        String regex = "^\\s*[+-]?((\\d+(\\.\\d*)?)|\\.\\d+)([eE][+-]?\\d+)?\\s*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    // 68       写得很乱,应该根据length,考虑是否insert到ArrayList,这样代码会清晰很多.这个答案意义不大,尽管AC
    public List<String> fullJustify(String[] words, int maxWidth) {
        int length = words.length;
        List<String> res = new ArrayList<>();
        int currentLength = 0;
        int beginIndex = 0;
        int numberOfStr = 0;
        String temp11 = "";
        for (int i = beginIndex; i < length;) {
            if (currentLength + words[i].length() > maxWidth) {         // 因为这里没有包含空格,所以不含等号
                if (numberOfStr == 1 || numberOfStr == 0) {
                    if (numberOfStr == 0)
                        i++;
                    words[i - 1] = String.format("%-" + maxWidth + "s", words[i - 1]);
                    res.add(words[i - 1]);
                    numberOfStr = 0;
                    currentLength = 0;
                    beginIndex = i;
                    temp11 = "";
                    continue;
                }
                String temp = "";
                for (int j = 0; j < numberOfStr; j++)
                    temp += words[beginIndex + j] + " ";
                if (currentLength + words[i].length() == maxWidth) {
                    temp += words[i];
                    numberOfStr++;  // final one
                }
                // sth do here to handle white space
                temp = temp.trim();
                int length1 = temp.length();
                String[] temps = temp.split("\\s+");
                int strLength = 0;
                for (String temp1: temps)
                    strLength += temp1.length();
                int diff = maxWidth - strLength;
                int eachSpace = diff / (numberOfStr - 1);
                int rest = diff % (numberOfStr - 1);
                String row = "";
                for (int k = 0; k < numberOfStr; k++) {
                    row += temps[k];
                    if (k == numberOfStr - 1)
                        break;
                    for (int kk = 0; kk < eachSpace; kk++)
                        row += " ";
                    if (rest != 0) {
                        row += " ";
                        rest--;
                    }
                }
                res.add(row);
                i = beginIndex + numberOfStr;
                beginIndex = i;
                numberOfStr = 0;
                currentLength = 0;
                temp11 = "";
            }
            else if (i != length - 1) {
                currentLength += words[i].length() + 1;
                temp11 += words[i] + " ";
                i++;
                numberOfStr++;
            }
            else {
                temp11 += words[i];
                temp11 = String.format("%-" + maxWidth + "s", temp11);
                res.add(temp11);
                break;
            }
        }
        return res;
    }

    // 77
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        combineHelper(result, current, n, k, 1, 0);
        return result;
    }

    public void combineHelper(List<List<Integer>> result, List<Integer> current, int n, int k, int now, int prev) {
        if (now - 1 == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = now; i <= n; i++) {
            if (i <= prev)
                continue;
            current.add(i);
            combineHelper(result, current, n, k, now + 1, i);
            current.remove(current.size() - 1);
        }
    }

    // 72   一定要清楚,为何要多开拓1的空间?真的仅仅是方便数组操作吗?需要好好利用。
    // 而且一开始没有考虑到,insert, delete, replace的区别,实际上都是同样如此,只需要考虑新的那两个字符是否相等即可！
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= n; j++)
                if (i == 0)
                    dp[i][j] = j;
                else if (j == 0)
                    dp[i][j] = i;
                else if (word1.charAt(i - 1) == word2.charAt(j - 1))     // 无须操作
                    dp[i][j] = dp[i - 1][j - 1];
                else        // 分别是 insert, delete, replace操作
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1));
        return dp[m][n];
    }

    // 76   关键: 滑动窗口的思想,一个先行,后行的找更优解.
    // 其次,不要无脑用HashMap,比如这题,范围也就52个字母,直接数组效率会更高。
    // 然后,递归的时候,费时的操作千万别在递归里完成.你第一个tMap(数组)知道要在外面初始化,为什么sMap一开始会放在递归函数里？
    // 最后,要follow normal logic.自己debug的时候就觉得resFreq应该放在if判定前,然而为什么会觉得最后一个case放在判定后
    // 结果比较接近,就改了呢？不要为了迎合答案而修改,要明白为什么
    public String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0)
            return "";
        int left = 0, right = 0;
        int[] tMap = new int[64];
        int[] windows = new int[3];         // 0 for length, 1 for begin, 2 for end
        int[] resFreq = new int[64];
        windows[0] = Integer.MAX_VALUE;
        for (char c: t.toCharArray())
            tMap[c - 'A']++;
        while (right < s.length()) {
            resFreq[s.charAt(right) - 'A']++;
            if (!minWindowHelper(resFreq, tMap)) {       // not OK
                right++;
            }
            else {                          // OK, but whether there is a better choice?
                while (left <= right) {
                    resFreq[s.charAt(left) - 'A']--;
                    left++;             // left will move a step to make it unavailable
                    if (minWindowHelper(resFreq, tMap))    // left + 1 still OK
                        continue;
                    else {
                        int currentLen = right - left + 2;      // one available position is left - 1, not left
                        if (currentLen < windows[0]) {
                            windows[0] = currentLen;
                            windows[1] = left - 1;
                            windows[2] = right;
                        }
                        break;
                    }
                }
                right++;    // 一开始最后一个case出错,原因就在于没有这个步骤,这个步骤丢失了是会陷入死循环的
                // 那么为什么不会陷入死循环呢?因为前面还加了一条语句,判断currentLen是否最短了,那个路径是错的,导致会提前结束
                // 后来直接把那句判定删除,加上这句right++,就全部AC了
            }
        }

        return windows[0] == Integer.MAX_VALUE ? "" : s.substring(windows[1], windows[2] + 1);
    }

    public boolean minWindowHelper(int[] sMap, int[] tMap) {
        for (int i = 0; i < tMap.length; i++)
            if (tMap[i] == 0)
                continue;
            else if (tMap[i] > sMap[i])
                return false;
        return true;
    }

    // 79
    public boolean exist(char[][] board, String word) {
        int length = word.length();
        if (length == 0)
            return true;
        int m = board.length;
        if (m == 0)
            return false;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int beginI = 0; beginI < m; beginI++)              // 当前不行,找下一个begin点
            for (int beginJ = 0; beginJ < n; beginJ++)
                if (existHelper(board, word, visited, beginI, beginJ, 0))
                    return true;
        return false;
    }

    public boolean existHelper(char[][] board, String word, boolean[][] visited, int pi, int pj, int next) {
        int length = word.length();
        int m = visited.length;
        int n = visited[0].length;
        if (next == length)
            return true;
        int[][] increments = { {0, 0}, {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < increments.length; i++) {
            pi += increments[i][0];
            pj += increments[i][1];
            if (pi >= 0 && pj >= 0 && pi < m && pj < n && !visited[pi][pj]) {
                if (board[pi][pj] == word.charAt(next)) {
                    visited[pi][pj] = true;
                    if (existHelper(board, word, visited, pi, pj, next + 1))
                        // 如果到达了true,就要返回.否则后面又会被状态reset
                        return true;
                    visited[pi][pj] = false;
                }
            }
            pi -= increments[i][0];
            pj -= increments[i][1];     // rollback
        }
        return false;
    }

    // 80   逻辑不够清晰,需要充分利用排好序的特点
    public int removeDuplicates1(int[] nums) {
        int length = nums.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[j] != nums[i] || j == length - 1) {
                    int times = j - i;
                    if (j == length - 1 && nums[j] == nums[i]) {        // 避免最后直接pass掉了
                        if (times - 2 >= 0)             // 特殊情况处理   最后的连续j位置跟前面的有所不同
                            length -= times - 1;
                        break;
                    }
                    if (times <= 2)
                        break;
                    int temp = i + 2;
                    int k;
                    for (k = 0; k < times - 2 && j + k < length; k++)
                        nums[temp + k] = nums[j + k];       // 把重复的全部从后面替换
                    if (j + k >= length){
                        length -= times - 2;
                        break;          // 剩余的不够的,直接中止
                    }
                    for (int k2 = j; k2 + times - 2 < length; k2++)
                        nums[k2] = nums[k2 + times - 2];
                    length -= times - 2;
                    i++;        // 跳转
                    break;
                }
            }
        }
        return length;
    }

    // 80 主要逻辑：前两个必定add进去,后面的是否加入到数组里,取决于和在此的前两位数字是否相等。
    // 比如nums[i]是否要add?只需要看 nums[i] == nums[i - 2] ? 等于,说明至少连续3个相同的了,那么当前的就不add了
    public int removeDuplicates2(int[] nums) {
        int length = nums.length;
        if (length <= 2)
            return length;
        int current = 2;
        for (int i = 2; i < length; i++)
            if (nums[i] != nums[current - 2])             // 是跟current的位置比,不能跟真的i - 2比
                nums[current++] = nums[i];
        return current;
    }

    // 33 good 二分法
    public int search1(int[] nums, int target) {
        return search(nums, 0, nums.length - 1, target);
    }

    // 如果递归到了乱序数组,那么仍然会自动递归到下一个有序数组中,不会到一个乱序数组中查找的,除非nums[mid]直接等于target
    public int search(int[] nums, int low, int high, int target) {
        if (low > high)
            return -1;
        int mid = (low + high) / 2;
        if (nums[mid] == target)
            return mid;
        if (nums[mid] < nums[high])                         // 说明右边是有序,(左边是可能有序,可能乱序)
            if (nums[mid] < target && target <= nums[high])     // 右边有序,那么就是跟high比较
                return search(nums, mid + 1, high, target);
            else
                return search(nums, low, mid - 1, target);
        else                                                // 说明左边是有序,(右边不一定)
            if (nums[mid] > target && target >= nums[low])      // 左边有序,所以跟low比较
                return search(nums, low, mid - 1, target);
            else
                return search(nums, mid + 1, high, target);
        // 判断两边必定有序,只要目的是为了确定是与low比较,还是与high比较！
    }

    // 90
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == 0)
            return res;
        List<Integer> current = new ArrayList<>();
        res.add(current);
        subsetWithDupHelper(res, current, nums, 0);
        return res;
    }

    public void subsetWithDupHelper(List<List<Integer>> res, List<Integer> current, int[] nums, int prev) {
        if (prev == nums.length)
            return;
        // HashSet<Integer> set = new HashSet<>();
        // 无须Set！如果是i == prev,那么就是当前层的第一个！必定能insert。如果不是当前层第一个,就要考虑是否和前面的相同(已排序)
        for (int i = prev; i < nums.length; i++) {
            if (i == prev || nums[i] != nums[i - 1]) {            // !set.contains(nums[i])
                current.add(nums[i]);
//                set.add(nums[i]);
                res.add(new ArrayList<>(current));
                subsetWithDupHelper(res, current, nums, i + 1);
                if (!current.isEmpty())
                    current.remove(current.size() - 1);
            }
        }
    }

    // 93
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        restoreIpAddressesHelper(res, s, sb, 0, 0);
        return res;
    }

    public boolean restoreIpAddressesHelper(List<String> res, String s, StringBuilder prev, int prev_length, int ip_num) {
        if (ip_num == 3) {
            String four = s.substring(prev_length - 3, s.length());
            if (checkIpAddresses(four)) {
                prev.append(four);
                res.add(prev.toString());
                return true;
            }
            return false;
        }
        int times = 1;
        for (int i = prev_length - ip_num; i < s.length(); i++) {       //  - ip_num 是因为 .
            if (i + times > s.length())
                return false;                           // 超出了范围,那就直接false
            String sub = s.substring(i, i + times++);
            if (sub.length() > 3)
                return false;                         // 这层已经废了
            if (!checkIpAddresses(sub)) {
                i--;
                continue;
            }
            prev.append(sub);
            prev.append(".");
            int currentLength = prev.length();
            boolean flag = restoreIpAddressesHelper(res, s, prev, prev.length(), ip_num + 1);
            if (flag)
                prev.delete(currentLength, prev.length());         // 添加成功时,需要把最后一个也删掉
            prev.delete(prev.length() - times, prev.length());
            i--;    // 重新来一次,但这次times++了

        }
        return false;
    }

    public boolean checkIpAddresses(String s) {
        if (s.length() == 0 || s.length() > 3 || s.charAt(0) == '0')        // 避免最后的four太长了
            return false;       // 0 开头不可以
        int i = Integer.valueOf(s);
        if (i > 255)
            return false;
        return true;
    }

    // 95, 递归,选mid Node,递归生成左右子树(暂时懒得做了,TODO
    public List<TreeNode> generateTrees(int n) {
        boolean[] visited = new boolean[n + 1];
        visited[0] = true;
        List<TreeNode> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            visited[i] = true;
            generateTreesHelper(res, new TreeNode(i), visited, n, i);
        }
        return res;
    }

    public TreeNode generateTreesHelper(List<TreeNode> res, TreeNode root, boolean[] visited, int n, int current) {
        if (n == 0)
            return null;
        boolean full = true;
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                full = false;
                break;
            }
        }
        if (full)
            return null;        // full
        for (int i = 1; i <= n; i++) {
            if (visited[i])
                continue;
            root.left = generateTreesHelper(res, root, visited, i - 1, i);
            root.right = generateTreesHelper(res, root, visited, i + 1, i);
        }
        return null;
    }

    // 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        if (p.val != q.val)
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // 131      回溯又是回溯
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> current = new ArrayList<>();
        partitionHelper(res, current, s, 0);
        return res;
    }

    public void partitionHelper(List<List<String>> res, List<String> current, String s, int prev) {
        if (prev == s.length()) {
            res.add(new ArrayList<>(current));
            return;
        }

        int times = 1;
        for (int i = prev; i < s.length(); i++) {
            String curr = s.substring(i, i + times);

        }

    }

    // 110
    public boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        int left = getDepth1(root.left, 0);
        int right = getDepth1(root.right, 0);
        System.out.println(root + ": " + left + " : " + right);
        if (left - right > 1 || left - right < -1)
            return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    // 增加了一个参数,变成尾递归
    public int getDepth1(TreeNode root, int currentLength) {
        if (root == null)
            return currentLength;
        return Math.max(getDepth1(root.left, currentLength + 1), getDepth1(root.right, currentLength + 1));
    }

    // 111
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        return minDepthHelper(root, 1);
    }

    public int minDepthHelper(TreeNode root, int current) {
        if (root == null)
            return Integer.MAX_VALUE;
        if (root.left == null && root.right == null)
            return current;
        return Math.min(minDepthHelper(root.left, current + 1), minDepthHelper(root.right, current + 1));
    }

    // 151
    public String reverseWords1(String s) {
        s = s.trim();
        //s.replaceAll("\\s+", " ");
        String[] strings = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            sb.append(strings[i]);
            if (i != 0)
                sb.append(" ");
        }
        return sb.toString();
    }

    // 153
    public int findMin(int[] nums) {
        if (nums.length == 0)
            return 0;
        return findMinHelper(nums, 0, nums.length - 1, nums[0]);
    }

    public int findMinHelper(int[] nums, int low, int high, int currentMin) {
        if (low > high)
            return currentMin;
        int mid = (low + high) / 2;
        if (nums[mid] < nums[high]) {
            currentMin = currentMin < nums[mid] ? currentMin : nums[mid];
            high = mid - 1;
        }
        else {
            currentMin = currentMin < nums[low] ? currentMin : nums[low];
            low = mid + 1;
        }
        return findMinHelper(nums, low, high, currentMin);
    }

    // 37
    public void solveSudoku(char[][] board) {
        HashMap<Integer, List<Integer>> rows = new HashMap<>();
        HashMap<Integer, List<Integer>> columns = new HashMap<>();
        HashMap<Integer, List<Integer>> subboxes = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            rows.put(i, new ArrayList<>());
            columns.put(i, new ArrayList<>());
            subboxes.put(i, new ArrayList<>());
        }
        boolean[][] exist = new boolean[9][9];      // 回溯的时候,避免改变原来就存在的值
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == '.')
                    continue;
                else {
                    List<Integer> row = rows.get(i);
                    List<Integer> column = columns.get(j);
                    List<Integer> subbox = subboxes.get(i / 3 * 3 + j / 3);
                    row.add((int)board[i][j] -'0');
                    column.add((int)board[i][j] - '0');
                    subbox.add((int)board[i][j] - '0');
                    rows.put(i, row);
                    columns.put(j, column);
                    subboxes.put(i / 3 * 3 + j / 3, subbox);
                    exist[i][j] = true;
                }
        solveSudokuHelper(board, rows, columns, subboxes, 0, 0, exist, 1, false);
    }

    // 这个回溯写得有问题,会迭代很多层,复杂度跟暴力法差不多了,所以会直接StackOverflow的. TODO
    public void solveSudokuHelper(char[][] board, HashMap<Integer, List<Integer>> rows,
                                  HashMap<Integer, List<Integer>> columns, HashMap<Integer,                                           List<Integer>> subboxes, int i, int j, boolean[][] exist,
                                  int prev, boolean back) {
        if (i == 9 || j == 9)
            return;
        if (board[i][j] == '.') {
            for (int k = prev; k <= 9; k++) {
                List<Integer> row = rows.get(i);
                if (row.contains(k))
                    continue;
                List<Integer> column = columns.get(j);
                if (column.contains(k))
                    continue;
                List<Integer> subbox = subboxes.get(i / 3 * 3 + j / 3);
                if (subbox.contains(k))
                    continue;
                board[i][j] = (char)k;
                row.add(k);
                column.add(k);
                subbox.add(k);
                rows.put(i, new ArrayList<>(row));
                columns.put(j, new ArrayList<>(column));
                subboxes.put(i / 3 * 3 + j / 3, new ArrayList<>(subbox));
                if (j == 8)
                    solveSudokuHelper(board, rows, columns, subboxes, i + 1, 0, exist, 1, false);
                else
                    solveSudokuHelper(board, rows, columns, subboxes, i, j + 1, exist, 1, false);
                row.remove((Integer)k);
                column.remove((Integer)k);
                subbox.remove((Integer)k);
                rows.put(i, new ArrayList<>(row));
                columns.put(j, new ArrayList<>(column));
                subboxes.put(i / 3 * 3 + j / 3, new ArrayList<>(subbox));
            }
            if (j == 0)
                solveSudokuHelper(board, rows, columns, subboxes, i - 1, 8, exist, 1, true);
            else
                solveSudokuHelper(board, rows, columns, subboxes, i, j - 1, exist, 1, true);
        }
        else if (!exist[i][j]) {      // 回溯的时候reset状态
            List<Integer> row = rows.get(i);
            List<Integer> column = columns.get(j);
            List<Integer> subbox = subboxes.get(i / 3 * 3 + j / 3);
            int pre = row.get(row.size() - 1);
            row.remove(row.size() - 1);
            column.remove(column.size() - 1);
            subbox.remove(subbox.size() - 1);
            board[i][j] = '.';
            solveSudokuHelper(board, rows, columns, subboxes, i, j, exist, pre + 1, false);
        }
        else {
            if (!back) {
                if (j == 8)
                    solveSudokuHelper(board, rows, columns, subboxes, i + 1, 0, exist, 1, false);
                else
                    solveSudokuHelper(board, rows, columns, subboxes, i, j + 1, exist, 1, false);
            }
            else {
                if (j == 0)
                    solveSudokuHelper(board, rows, columns, subboxes, i - 1, 8, exist, 1, true);
                else
                    solveSudokuHelper(board, rows, columns, subboxes, i, j - 1, exist, 1, true);
            }
        }
    }

    // 162 寻找峰值 (时间复杂度为 2N)  实际上可以优化成N,因为nums[-1]为-∞,只需要向后比较即可,如果nums[i] > nums[i + 1]
    // 那么i就是峰值,而i前面必定是单调递增.
    // 如果要logn,就是二分法,因为峰值的前面必定单调递增
    public int findPeakElement(int[] nums) {
        if (nums.length == 1)
            return 0;

        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                if (nums[i] > nums[i + 1])
                    return i;
                continue;
            }
            if (i == nums.length - 1) {
                if (nums[i] > nums[i - 1])
                    return i;
                continue;
            }
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1])
                return i;
        }
        return -1;          // 空数组的情况
    }

    // 165
    public int compareVersion(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        int length1 = ver1.length;
        int length2 = ver2.length;
        if (length1 > length2) {
            for (int i = 0; i < length1 - length2; i++)
                version2 += ".0";
            ver2 = version2.split("\\.");
        }
        else {
            for (int i = 0; i < length2 - length1; i++)
                version1 += ".0";
            ver1 = version1.split("\\.");
        }
        for (int i = 0; i < ver1.length; i++) {
            int i1 = Integer.valueOf(ver1[i]);
            int i2 = Integer.valueOf(ver2[i]);
            if (i1 > i2)
                return 1;
            else if (i1 < i2)
                return -1;
        }
        return 0;
    }

    // 165 其实不用对String修改,如果判断i超出了length, 将对应的i1或者i2赋值为0即可
    public int compareVersion1(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        int length1 = ver1.length;
        int length2 = ver2.length;
        int length = Math.max(length1, length2);
        for (int i = 0; i < length; i++) {
            int i1 = i < length1 ? Integer.valueOf(ver1[i]) : 0;
            int i2 = i < length2 ? Integer.valueOf(ver2[i]) : 0;
            if (i1 > i2)
                return 1;
            else if (i1 < i2)
                return -1;
        }
        return 0;
    }

    // 907
    public int sumSubarrayMins(int[] A) {
        LinkedList<Integer> stack = new LinkedList<>();
        int[] a1 = new int[A.length + 1];
        a1[0] = 0;
        int res = 0;
        int mod = (int)Math.pow(10, 9) + 7;
        for (int i = 0; i < A.length; i++)
            a1[i + 1] = A[i];
        for (int i = 0; i < a1.length; i++) {
            while (!stack.isEmpty() && a1[i] < stack.getFirst()) {
                int current = stack.removeFirst();
                res += (current - stack.getFirst()) * (i - current) * a1[current];
                res %= mod;
            }
            stack.addFirst(i);
        }
        return res;
    }

    // 209      n ^ 2
    public int minSubArrayLen(int s, int[] nums) {
        int res = Integer.MAX_VALUE;
        int current;
        int count;
        for (int i = 0; i < nums.length; i++) {
            current = 0;
            count = 0;
            for (int j = i; j < nums.length; j++) {
                current += nums[j];
                count++;
                if (current >= s) {
                    res = res > count ? count : res;
                    break;
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    // 209  n  双指针  right移动n次, left也是最多移动 n次.
    public int minSubArrayLen1(int s, int[] nums) {
        int left = 0, right = 0, res = Integer.MAX_VALUE, sum = 0;
        while (right < nums.length) {
            while (sum < s && right < nums.length)
                sum += nums[right++];
            while (sum >= s && left <= right) {     // 如果s是正整数,那么这里无须考虑left <= right,不会超出的
                res = res > right - left ? right - left : res;
                sum -= nums[left++];
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public List<Integer> transformArray(int[] arr) {
        boolean flag = false;
        List<Integer> res = new ArrayList<>();
        if (arr.length == 0)
            return res;
        if (arr.length == 1) {
            res.add(arr[0]);
            return res;
        }
        if (arr.length == 2) {
            res.add(arr[1]);
            return res;
        }
        while (true) {
            int prev = arr[0];
            flag = false;
            for (int i = 1; i < arr.length - 1; i++) {
                if (arr[i] > prev && arr[i] > arr[i + 1]) {
                    prev = arr[i];
                    arr[i] = arr[i] - 1;
                    flag = true;
                }
                else if (arr[i] < prev && arr[i] < arr[i + 1]) {
                    prev = arr[i];
                    arr[i] = arr[i] + 1;
                    flag = true;
                }
                else
                    prev = arr[i];
            }
            if (!flag)
                break;
        }
        for (int i: arr)
            res.add(i);
        return res;
    }

    // 5247
    public int minimumSwap(String s1, String s2) {
        int i1 = 0, i2 = 0;
        for (int i = 0; i < s1.length(); i++)
            if (s1.charAt(i) != s2.charAt(i))
                if (s1.charAt(i) == 'x')
                    i1++;
                else
                    i2++;
        if ((i1 + i2) % 2 != 0)
            return -1;
        int temp1 = i1 / 2 + i2 / 2;
        int temp2 = i1 % 2 + i1 % 2;      // temp2 must be 2 actually
        return temp1 + temp2;
    }

    // 5248     统计每一个奇数的左边有多少个偶数,还要记录最后一个奇数的右边有多少个偶数
    // 每一次的总和就是  odds[i] * odds[i + k + 1]  (加1就相当于right的右边偶数,而非左边偶数)
    public int numberOfSubarrays(int[] nums, int k) {
        int odd = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] % 2 == 0)
                nums[i] = 0;
            else {
                nums[i] = 1;
                odd++;
            }
        int res = 0;
        List<Integer> list = new ArrayList<>();
        int[] odds = new int[odd + 1];
        int j = 0, prev = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == 1)
                if (j == 0) {
                    odds[j++] = i + 1;
                    prev = i;
                }
                else {
                    int tmp = i - prev;
                    prev = i;
                    odds[j++] = tmp;
                }
        odds[j] = nums.length - prev;    // the rest
        int left = 0, right = k;
        int i = 0;
        while (right <= j)
            res += odds[left++] * odds[right++];
        return res;
    }

    // 167
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        int[] res = new int[2];
        while (true) {
            int current = numbers[left] + numbers[right];
            if (current == target)
                break;
            else if (current < target)
                left++;
            else
                right--;
        }
        res[0] = 1 + left;
        res[1] = 1 + right;
        return res;
    }

    // 172 只有2*5的结果才有0,其他的都可以简化为这个形式,比如6*5 = 2 * 5 *3(3无关紧要
    // 所以只需要数2和5的最小个数,显然5的个数会是更少
    // 而例如125可以分为5 * 5 * 5
    public int trailingZeroes(int n) {
        int res = 0;
        while (n >= 5) {
            res += n / 5;
            n /= 5;
        }
        return res;
    }

    // 233 分别统计个位,十位,百位。。。的个数
    public int countDigitOne(int n) {
        int num = n, i = 1, s = 0;
        while (num != 0) {
            if (num % 10 == 0)
                s += num / 10 * i;
            if (num % 10 == 1)
                s += num / 10 * i + (n % i) + 1;
            if (num % 10 > 1)
                s += Math.ceil(num / 10.0) * i;
            num /= 10;
            i *= 10;
        }
        return s;
    }

    // 793  这个时间复杂度为n,会超时
    public int preimageSizeFZF(int K) {
        int numOfZeros = 0;
        int current = 5;
        int prev = 0;
        while (numOfZeros <= K) {
            prev = 0;
            int temp = current;
            while (temp % 5 == 0 && temp >= 5) {
                prev++;
                temp /= 5;
            }
            numOfZeros += prev;
            current += 5;
        }
        if (numOfZeros - prev != K)
            return 0;
        return 5;
    }

    // 793 二分
    public int preimageSizeFZF1(int K) {
        // 确定阶梯值范围,最终得到 K < start
        int start = 1;
        while (start < K)
            start = start * 5 + 1;      // 递推: f(x + 1) = f(x) * 5 + 1 (x为5次幂)
        // 确定范围之后,进行精确查找
        while (start > 1) {
            // 只有5以下的阶乘才会出现start - 1成立,其他情况不会存在,因为任何一个阶段分界值都包含超过1个5
            if (start - 1 == K)
                return 0;

            // 从f(x + 1)推导出f(x)
            start = (start - 1) / 5;

            // 获取剩余值,进行下一阶梯运算
            K %= start;
        }
        return 5;
    }

    // 316  构造一个stack,如果遇到一个新字符,就与栈顶比较,如果后面还有跟栈顶一样的元素,就把栈顶元素去掉(用while)
    public String removeDuplicateLetters(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (stack.contains(c))
                continue;
            while (!stack.isEmpty() && c < stack.getFirst() && s.indexOf(stack.getFirst(), i) != -1)
                stack.removeFirst();
            stack.addFirst(c);
        }
        StringBuilder sb = new StringBuilder();
        for (Character c: stack)
            sb.append(c);
        return sb.reverse().toString();
    }

    // 306
    public boolean isAdditiveNumber(String num) {
        int length = num.length();
        for (int i = 1; i <= length / 2; i++) {
            for (int j = 1; j <= length / 2; j++) {
                int max = Math.max(i, j);
                if (num.length() - i - j < max)         // 100200300, 只要剩下的长度跟max的i,j相等,还是可能true的
                    break;
                if (isAdditiveNumberHelper(num, i, j))
                    return true;
                if (num.charAt(i) == '0')       // 只允许0,不允许0xxx
                    break;
            }
            if (num.charAt(0) == '0')
                break;                  // 同样的,first也是只允许0,不允许0xxx
        }
        return false;
    }

    public boolean isAdditiveNumberHelper(String num, int first, int second) {
        int begin = 0;
        for (int i = first + second; i < num.length();) {
            long first1 = Long.valueOf(num.substring(begin, first));
            long second1 = Long.valueOf(num.substring(first, first + second));
            long sum = first1 + second1;
            String s = String.valueOf(sum);
            if (first + second + s.length() > num.length () ||
                    !num.substring(first + second, first + second + s.length()).equals(s))
                return false;       // 如果不行,说明这两个first跟second选错了,重选
            if (first + second + s.length() == num.length())
                break;
            begin = first;
            first += second;            // 注意以下begin, first, second的改变值就好
            second = s.length();
        }
        return true;
    }

    // 842 题目规定了符合的数必定是Integer范围内,但我们遍历的时候还是要自己考虑Integer.MAX_VALUE跟Long.MAX_VALUE的情况
    public List<Integer> splitIntoFibonacci(String num) {
        int length = num.length();
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= length / 2 && i <= 10; i++) {          // Integer.MAX的位数是10
            for (int j = 1; j <= length / 2 && j <= 10; j++) {      // Long.MAX的位数是19
                int max = Math.max(i, j);
                if (num.length() - i - j < max)
                    break;
                if (isAdditiveNumberHelper(num, i, j, res))
                        return res;
                if (num.charAt(i) == '0')       // 只允许0,不允许0xxx
                    break;
            }
            if (num.charAt(0) == '0')
                break;                  // 同样的,first也是只允许0,不允许0xxx
        }
        return res;
    }

    // 一开始直接用了最stupid的方法,try-catch,可行但效率并不高。 63ms
    // 后来想想主要是甚至还可能超出Long.MAX,其实直接判定长度不超过10即可(Integer.MAX的位数是10)
    // 于是在最开始的循环里加上了i <= 10 跟 j <= 10,就直接把异常处理去掉了,时间 6ms
    // 可见,异常处理虽然简单粗暴,但实际上所耗费的资源也是极大的
    public boolean isAdditiveNumberHelper(String num, int first, int second, List<Integer> res) {
        int begin = 0;
        int first1, second1, sum;
        for (int i = first + second; i < num.length();) {
            String sub1 = num.substring(begin, first);
            String sub2 = num.substring(first, first + second);
            long f1 = Long.valueOf(sub1);
            long s1 = Long.valueOf(sub2);
            long sum1 = f1 + s1;
            if (f1 > Integer.MAX_VALUE || s1 > Integer.MAX_VALUE || sum1 > Integer.MAX_VALUE) {
                res.clear();
                return false;
            }
            first1 = Integer.valueOf(sub1);
            second1 = Integer.valueOf(sub2);
            sum = first1 + second1;
            String s = String.valueOf(sum);
            if (first + second + s.length() > num.length () ||
                    !num.substring(first + second, first + second + s.length()).equals(s)) {
                res.clear();
                return false;
            }
            res.add(first1);
            if (first + second + s.length() == num.length()) {
                res.add(second1);
                res.add(sum);
                break;
            }
            begin = first;
            first += second;
            second = s.length();
        }
        return true;
    }

    // 289  可以用temp数组,但这样空间复杂度为n,直接在原数组上进行标记即可
    // -1 表示 1变成 0 , -2表示 0变成 1
    public void gameOfLife(int[][] board) {
        int m = board.length;
        if (m == 0)
            return;
        int n = board[0].length;
        if (n == 0)
            return;
//        int[][] temp = new int[m][n];
//        for (int i = 0; i < m; i++)
//            for (int j = 0; j < n; j++)
//                temp[i][j] = board[i][j];
        int[][] eights = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int k = 0; k < eights.length; k++) {
                    int tempI = i + eights[k][0];
                    int tempJ = j + eights[k][1];
                    if (tempI < 0 || tempJ < 0 || tempI >= m || tempJ >= n)
                        continue;
                    if (board[tempI][tempJ] == 1 || board[tempI][tempJ] == -1)
                        live++;
                }
                if ((board[i][j] == 1 || board[i][j] == -1) && (live < 2 || live > 3))
                    //temp[i][j] = 0;
                    board[i][j] = -1;
                if ((board[i][j] == 0 || board[i][j] == -2) && live == 3)
                    //temp[i][j] = 1;
                    board[i][j] = -2;
            }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == -1)
                    board[i][j] = 0;
                else if (board[i][j] == -2)
                    board[i][j] = 1;
    }

    // 274          用哈希表的方法
    public int hIndex(int[] citations) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int length = citations.length;
        Arrays.sort(citations);
        for (int i = 0; i < length; i++) {
            Integer integers = map.get(citations[i]);
            if (integers != null)
                continue;
            map.put (citations[i], length - i);
        }
        int res = 0;
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());  // 逆序,即根据value从大到小排列
            }
        });
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Map.Entry<Integer, Integer> entry = list.get(i);
            int key = entry.getKey();
            int value = entry.getValue();
            if (key < value)
                if (i != size - 1)
                    continue;           // 如果是最后一系列数是result,这时就不能简单地continue！
                else {
                    res = Math.min(list.get(i).getKey(), list.get(i).getValue());   // 此时就是key跟value取最小
                    break;
                }
            if (i == 0)
                res = list.get(i).getValue();       // 考虑i == 0的情况
            else
                res = Math.max(list.get(i).getValue(), list.get(i - 1).getKey());
                // i是第一个刚好开始 key > value的,然后就是取当前的value和上一个的key的最大值
                // (一开始举的例子具有迷惑性,导致卡了很久)
            break;
        }
        return res;
    }

    // 274      感觉我弄复杂了,什么前一个的key跟当前的value,其实要返回的就是 length - i,当且仅当length - i <= c[i]
    // 但是呢,理论上哈希表的时间复杂度可以到达n(但我上面转换成list再排序,太费时,其实不需要这样做),而这里复杂度是nlogn
    public int hIndex1(int[] citations) {
        if(citations.length < 1)
            return 0;
        Arrays.sort(citations);
        for (int i = 0; i < citations.length; i++)
            if (citations.length - i <= citations[i])
                return citations.length - i;
        return 0;
    }

    // 207      50ms, use DFS recursively
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[][] adjacency = new int[numCourses][numCourses];
        int[] flags = new int[numCourses];
        // 0 for never visited, 1 for visited this times, -1 for visited by other nodes
        int length = prerequisites.length;
        for (int i = 0; i < length; i++)
            adjacency[prerequisites[i][0]][prerequisites[i][1]] = 1;
        // set the prerequisites become the adjacency matrix
        for (int i = 0; i < numCourses; i++)
            if (!dfs(adjacency, flags, i))
                return false;
        return true;
    }

    // actually it cost n ^ 2 time complexity, so it's not high efficiency
    public boolean dfs(int[][] adjacency, int[] flags, int i) {
        if (flags[i] == 1)
            return false;
        // it means it exists a cycle cause that one node visited twice by the current node
        if (flags[i] == -1)
            return true;
        // this node has been visited by others, so we can stop here
        int length = adjacency.length;
        flags[i] = 1;   // visited this times
        for (int j = 0; j < length; j++)
            if (adjacency[i][j] == 1 && !dfs(adjacency, flags, j))
                return false;
        // dfs from this node and check whether there is a cycle
        flags[i] = -1;
        // well there is no cycle in these nodes, so we set it status -1
        // and finish this recursion.
        return true;
        // if it's cyclic, the result is can not finish, otherwise, it can finish.
    }

    // 207 this time we use a table to save the indegree of each node
    // we delete all the nodes that's indegree is 0
    // finially if there still exists nodes, it means it's cyclic.      16ms, faster than DFS
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        int length = prerequisites.length;
        for (int i = 0; i < length; i++)
            indegrees[prerequisites[i][1]]++;
        boolean check = false;  // if we can't find a node has 0 indegree, finish the while cycle
        boolean[] visited = new boolean[numCourses];
        // check whether all the nodes have been gone
        int num = 0;        // to count the node that's visited(deleted)
        while (true) {
            check = false;
            for (int i = 0; i < numCourses; i++)
                if (indegrees[i] == 0 && !visited[i]) {
                    check = true;
                    visited[i] = true;      // it means we delete this node!
                    num++;
                    for (int[] prerequisite: prerequisites)
                        if (prerequisite[0] == i)
                            indegrees[prerequisite[1]]--;
                    // the indegree of node i is 0, we delete it,and those nodes that's pointed by i
                    // we need to decrease they indegree by 1.
                }
            if (!check)
                break;      // finish the while cycle.
        }
        if (num != numCourses)  // still exists nodes, so it's cyclic, can't finish
            return false;
        return true;            // acyclic,can be finished.
    }

    // 210
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        if (canFinish1(numCourses, prerequisites, res))
            return res;     // can finish so return res,but we need to reverse it!
        return new int[0];         // acyclic then return am empty array
    }

    public boolean canFinish1(int numCourses, int[][] prerequisites, int[] res) {
        int[] indegrees = new int[numCourses];
        int length = prerequisites.length;
        for (int i = 0; i < length; i++)
            indegrees[prerequisites[i][1]]++;
        boolean check = false;
        // if we can't find a node has 0 indegree, finish the while cycle
        boolean[] visited = new boolean[numCourses];
        // check whether all the nodes have been gone
        int num = 0;        // to count the node that's visited(deleted)
        while (true) {
            check = false;
            for (int i = 0; i < numCourses; i++)
                if (indegrees[i] == 0 && !visited[i]) {
                    check = true;
                    visited[i] = true;      // it means we delete this node!
                    res[num++] = i;
                    for (int[] prerequisite: prerequisites)
                        if (prerequisite[0] == i)
                            indegrees[prerequisite[1]]--;
                    // the indegree of node i is 0, we delete it,and those nodes that's pointed by i
                    // we need to decrease they indegree by 1.
                }
            if (!check)
                break;      // finish the while cycle.
        }
        if (num != numCourses)  // still exists nodes, so it's cyclic, can't finish
            return false;
        for (int i = 0; i < numCourses / 2; i++) {
            int temp = res[i];
            res[i] = res[numCourses - 1 - i];
            res[numCourses - 1 - i] = temp;      // reverse the array
        }
        return true;            // acyclic,can be finished.
    }

    // 131 分治
    public List<List<String>> partition1(String s) {
        return partition1Helper(s, 0);
    }

    private List<List<String>> partition1Helper(String s, int start) {
        if (start == s.length()) {
            List<String> list = new ArrayList<>();
            List<List<String>> res = new ArrayList<>();
            res.add(list);
            return res;         // 返回一个List<List<String>>,包含一个空的List<String>
        }
        List<List<String>> res = new ArrayList<>();
        for (int i = start; i < s.length(); i++)
            if (isPalindrome3(s.substring(start, i + 1))) {
                String left = s.substring(start, i + 1);
                for (List<String> list: partition1Helper(s, i + 1)) {
                    // 很巧妙地从后向前递归。 aabb = a + abb = a + a + bb = a + a + b + b
                    // 最开始先return一个空的res,这时候是从后往前add.
                    list.add(0, left);
                    res.add(list);
                }
            }
        return res;
    }

    private boolean isPalindrome3(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j)
            if (s.charAt(i++) != s.charAt(j--))
                return false;
        return true;
    }

    // 131 分治优化,使用一个dp二维数组去维护值,减少重复遍历的操作
    public List<List<String>> partition2(String s) {
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        // 这里是根据i~j之间的len来循环的
        // 不能直接for i in range(1, length), for j int range(1, length)
        // 为什么呢,比如我们要get dp[2][5]的时候需要get dp[3][4],如果是顺序循环,这时候dp[3][4]还没有被处理
        // 所以正确的循环顺序就是 根据len来循环！
        for (int len = 1; len <= length; len++) {
            for (int i = 0; i <= length - len; i++) {
                int j = i + len - 1;
                dp[i][j] = s.charAt(i) == s.charAt(j) && (len < 3 || dp[i + 1][j - 1]);
            }
        }
        return partition2Helper(s, 0, dp);
    }

    // dp[i][j]表示 s.substring(i, j + 1)是否是回文串,减少了重复的操作(比如a是回文,那么bbabb,fffqaqfff也是回文)
    public List<List<String>> partition2Helper(String s, int start, boolean[][] dp) {
        if (start == s.length()) {
            List<List<String>> res = new ArrayList<>();
            List<String> list = new ArrayList<>();
            res.add(list);
            return res;
        }
        List<List<String>> res = new ArrayList<>();
        for (int i = start; i < s.length(); i++)
            if (dp[start][i]) {
                String left = s.substring(start, i + 1);
                for (List<String> list: partition2Helper(s, i + 1, dp)) {
                    list.add(0, left);
                    res.add(list);
                }
            }
        return res;
    }

    // 131 回溯也可以解决,只要画出递归树
    public List<List<String>> partition3(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> current = new ArrayList<>();
        partition3Helper(s, current, res, 0);
        return res;
    }

    public void partition3Helper(String s, List<String> current, List<List<String>> res, int start) {
        if (s.length() == start) {
            res.add(new ArrayList<>(current));
            return;             // 递归分割,直到剩下空子串,就返回
        }
        for (int i = start; i < s.length(); i++) {
            String sub = s.substring(start, i + 1);
            if (isPalindrome3(sub)) {           // 也可以用dp代替,效率更高！
                current.add(sub);
                partition3Helper(s, current, res, i + 1);
                // 不是start + 1, 而是 i + 1,因为i有可能已经横跨了几个长度,不一定len为 1
                current.remove(current.size() - 1);     // 删除最后那个,也就是上面才刚刚add的sub(Status reset)
            }
        }
    }

    //132
    // a tricky solution    TODO NOT FINISH
    public int minCut(String s) {
        int length = s.length();
        if (length <= 1)
            return 0;
        int[] cut = new int[length + 1];    // number of cuts for the first k-characters
        for (int i = 0; i <= length; i++)
            cut[i] = i - 1;     //initialize, if a string have x characters, it needs x - 1 times to cut at most
        // ps: why we need to set cut[0] to -1 ?
        // When the whole characters is palindrome,we need to compare the cut[length] with cut[0] + 1
        // Actually in this case, the result is 0, if we don't set cut[0] to -1, then the final result is 1,which is wrong.

        for (int i = 0; i < length; i++) {   // i is the center character of the palindrome,we expand it from its center
            for (int j = 0; i >= j && i + j < length; j++)   // j represents the 'radius' of the palindrome.
                // Apparently, j <= i is a must, and i + j < n is prevent the OutOfBoundsException
                if (s.charAt(i - j) != s.charAt(i + j))
                    break;
                else
                    cut[i + j + 1] = Math.min(cut[i + j + 1], 1 + cut[i - j]);
            // we know that s.substring(i - j, i + j + 1) is a palindrome, so we needn't cut it
            // and if we need to cut s.substring(0, i + j + 1), we only need to cut between 0 and i - j - 1
            // and one more cut at the i - j obviously.

            for (int j = 1; i >= j - 1 && i + j < length; j++)   // the former is for the odd length, here is the even length
                if (s.charAt(i - j + 1) != s.charAt(i + j))
                    break;
                else
                    cut[i + j + 1] = Math.min(cut[i + j + 1], cut[i - j + 1]);
        }
        return cut[length];
    }

    // 189      时间复杂度 n, 空间复杂度 k
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0)
            return;
        boolean[] visited = new boolean[k];
        int from = 0, to = k;
        int prev = nums[from];
        for (int i = 0; i < n; i++) {
            int temp = nums[to];
            nums[to] = prev;
            prev = temp;
            if (from < k)
                visited[from] = true;           // record the first-k elements
            from = to;  // next
            to = (to + k) % n;
            if (from < k && visited[from]) {        // go to next.
                from++;
                to++;
                prev = nums[from];                  // temp need to change
            }
        }
        System.out.println(nums);
        System.out.println("xxx");
    }

    // 205
    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, List<Integer>> map = new HashMap<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            Character c = s.charAt(i);
            List<Integer> list = map.getOrDefault(c, new ArrayList<>());
            list.add(i);
            map.put(c, list);
        }
        int n = map.size();
        HashSet<Character> set = new HashSet<>();
        for (Map.Entry<Character, List<Integer>> entry: map.entrySet()) {
            List<Integer> list = entry.getValue();
            Character current = t.charAt(list.get(0));
            if (set.contains(current))
                return false;           // exists previously
            set.add(current);
            for (int i = 1; i < list.size(); i++)
                if (t.charAt(list.get(i)) != current)
                    return false;
        }
        return true;
    }

    // 205  tricky
    public boolean isIsomorphic1(String s, String t) {
        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if(s.indexOf(ch1[i]) != t.indexOf(ch2[i])){         // so tricky!!!
                return false;
            }
        }
        return true;
    }

    // 214  暴力
    public String shortestPalindrome(String s) {
        StringBuilder sb = new StringBuilder(s);
        int i = 0, length = s.length();
        while (!isPalindrome(sb) && i < length) {
            char c = s.charAt(length - 1 - i);
            sb.insert(i++, c);
        }
        return sb.toString();
    }

    public boolean isPalindrome(StringBuilder sb) {
        int left = 0, right = sb.length() - 1;
        while (left < right)
            if (sb.charAt(left++) != sb.charAt(right--))
                return false;
        return true;
    }

    // 216
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (k >= 10)            // 只能1~9,且不能重复
            return res;
        List<Integer> current = new ArrayList<>();
        combinationSum3Helper(k, n, res, current, 0, 0, 0);
        return res;
    }

    public void combinationSum3Helper(int k, int n, List<List<Integer>> res, List<Integer> current, int num,
                                      int sum, int prev) {
        if (num == k || prev == 9) {
            if (sum == n && num == k)
                res.add(new ArrayList(current));
            return;
        }

        for (int i = prev + 1; i <= 9; i++) {
            sum += i;
            num++;
            current.add(i);
            combinationSum3Helper(k, n, res, current, num, sum, i);
            current.remove(current.size() - 1);
            num--;
            sum -= i;
        }
    }

    public int oddCells(int n, int m, int[][] indices) {
        boolean[][] odds = new boolean[n][m];
        for (int[] indice: indices) {
            int row = indice[0];
            int column = indice[1];
            for (int i = 0; i < m; i++)
                odds[row][i] = !odds[row][i];
            for (int i = 0; i < n; i++)
                odds[i][column] = !odds[i][column];
        }
        int res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (odds[i][j])
                    res++;
        return res;
    }

    // 748
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] license = new int[26];
        licensePlate = licensePlate.toLowerCase();
        for (char c: licensePlate.toCharArray())
            if (Character.isLetter(c))
                license[c - 'a']++;
        int currentMinLength = Integer.MAX_VALUE;
        String result = "";
        for (int i = 0; i < words.length; i++) {
            int[] current = new int[26];
            String str = words[i];
            if (str.length() >= Integer.MAX_VALUE)
                continue;
            String temp = words[i];
            str = str.toLowerCase();
            for (char c: str.toCharArray())
                if (Character.isLetter(c))
                    current[c - 'a']++;
            for (int j = 0; j < 26; j++)
                if (license[j] > current[j])
                    break;
                else {
                    if (j == 25 && str.length() < currentMinLength) {
                        result = temp;
                        currentMinLength = str.length();
                    }
                }
        }
        return result;
    }

    // 744 顺序遍历 n   但题目实际上提到了是有序数组,所以可以二分查找的
    public char nextGreatestLetter(char[] letters, char target) {
//        ArrayList<Character> list = new ArrayList<>();
//        for (char c: letters)
//            if (c > target)
//                list.add(c);
//        if (list.size() == 0)
//            for (char c: letters)
//                list.add(c);    // insert all elements
//        char currentMin = list.get(0);
//        for (char c: list)
//            currentMin = currentMin > c ? c : currentMin;
//        return currentMin;
        for (char c: letters)
            if (c > target)
                return c;
        return letters[0];
    }

    // 744 二分, 要弄清楚二分结束时指针的位置。
    public char nextGreatestLetter1(char[] letters, char target) {
        int length = letters.length;
        if (target >= letters[length - 1] || target < letters[0])
            return letters[0];
        int left = 0, right = length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target < letters[mid])
                right = mid - 1;
            else
                left = mid + 1;
        }
        return letters[left];  // 结束时left指向比目标字母最小的字母
    }

    // 1249
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        int left = 0;
        for (int i = 0; i < length; i++) {
            char c = sb.charAt(i);
            if (c == '(')
                left++;
            else if (c == ')') {
                if (left == 0) {
                    sb.deleteCharAt(i);
                    length--;
                    i--;            // stay at the current position
                    continue;
                }
                left--;
            }
        }
        int right = 0;
        for (int i = length - 1; i >= 0; i--) {
            char c = sb.charAt(i);
            if (c == ')')
                right++;
            else if (c == '(') {
                if (right == 0) {
                    sb.deleteCharAt(i);
                    continue;
                }
                right--;
            }
        }
        return sb.toString();
    }

    // 1253 贪心算法, 优先insert到 min(upper, lower)当中.
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        List<List<Integer>> res = new ArrayList<>();
        int length = colsum.length;
        int[] init = new int[length];
        List<Integer> first = new ArrayList<>(length);
        // init the length reduce the cost of resize
        List<Integer> second = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            if (colsum[i] != 0 && colsum[i] != 1 && colsum[i] != 2)
                return res;   // unvaild result, then return the empty list
            else if (colsum[i] == 0) {
                first.add(0);
                second.add(0);
            }
            else if (colsum[i] == 2) {
                first.add(1);
                second.add(1);
                upper--;
                lower--;
            }
            else {
                if (upper > lower) {
                    upper--;
                    first.add(1);
                    second.add(0);
                }
                else if (upper == 0 && lower == 0)
                    return res;     // empty
                else {
                    lower--;
                    second.add(1);
                    first.add(0);
                }
            }
        }
        if (upper != 0 || lower != 0)
            return res;             // empty still.
        res.add(first);
        res.add(second);
        return res;
    }

    // 1254     DFS, 首先把边界的0以及与其相邻的0全部置换为1(因为题意认为边界的0不符合要求)
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;

        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 0)
                dfsInit(grid, 0, j);    // init the border.
            if (grid[m - 1][j] == 0)
                dfsInit(grid, m - 1, j);
        }

        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 0)
                dfsInit(grid, i, 0);    // init the border.
            if (grid[i][n - 1] == 0)
                dfsInit(grid, i, n - 1);
        }

        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 0) {
                    res++;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    public void dfsInit(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 1)
            return;
        grid[i][j] = 1;
        dfsInit(grid, i, j + 1);
        dfsInit(grid, i, j - 1);
        dfsInit(grid, i + 1, j);
        dfsInit(grid, i - 1, j);
    }

    public void dfs(int[][] grid, int i, int j) {
        if (i == 0 || j == 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 1)
            return;
        grid[i][j] = 1;
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
    }

    // 223
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int a, b;
        int area1 = (D - B) * (C - A);
        int area2 = (H - F) * (G - E);
        if ((A >= E && C <= G && B >= F && D <= H) || (E >= A && C >= G && F >= B && D >= H))   // 其中一个完全在另一个之中
            return Math.max(area1, area2);
        if ((C <= E || G <= A || B >= H || F >= D))
            return area1 + area2;                   // 两个完全不相交
        b = Math.min(D, H) - Math.max(B, F);
        a = Math.min(C, G) - Math.max(A, E);
        return area1 + area2 - a * b;
    }

    // 406
    public int[][] reconstructQueue(int[][] people) {
        int length = people.length;
        if (length == 0 || people[0].length == 0)
            return new int[0][0];
        int[][] res = new int[length][people[0].length];
        int[][] copies = new int[length][people[0].length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < people[0].length; j++)
                copies[i][j] = people[i][j];
        int finish = 0;
        List<Integer> candidates = new ArrayList<>(length);
        List<Integer> indexs = new ArrayList<>(length);
        while (finish < length) {
            for (int i = 0; i < length; i++)
                if (people[i][1] == 0) {
                    candidates.add(people[i][0]);
                    indexs.add(i);
                }
            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            int size = candidates.size();
            for (int i = 0; i < size; i++) {
                int current = candidates.get(i);
                if (current < min) {
                    min = current;
                    minIndex = i;
                }
            }
            int tempIndex = indexs.get(minIndex);
            res[finish][0] = copies[tempIndex][0];
            res[finish++][1] = copies[tempIndex][1];
            for (int i = 0; i < length; i++)
                if (i != tempIndex && people[i][0] <= min)
                    people[i][1]--;             // change the status
            people[tempIndex][0] = -1;           // won't reduce any more
            people[tempIndex][1] = 1111;
            candidates.clear();
            indexs.clear();
        }
        return res;
    }

    // 797      经典回溯
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        current.add(0);
        if (graph[0].length == 0) {
            res.add(new ArrayList<>(current));
            return res;
        }
        allPathsSourceTargetHelper(graph, res, current, 0);
        return res;
    }

    public void allPathsSourceTargetHelper(int[][] graph, List<List<Integer>> res, List<Integer> current, int cur) {
        if (cur == graph.length - 1) {
            res.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < graph[cur].length; i++) {
            current.add(graph[cur][i]);
            allPathsSourceTargetHelper(graph, res, current, graph[cur][i]);
            current.remove(current.size() - 1);
        }
    }

    // 134   gas[start] - cost[start] + ... + gas[length - 1] + cost[length - 1] >
    //          gas[0] - cost[0] +... + gas[start - 1] - cost[start - 1]
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int rest = 0, run = 0, start = 0, length = gas.length;
        for (int i = 0; i < length; i++) {
            run += gas[i] - cost[i];
            rest += gas[i] - cost[i];
            if (run < 0) {
                start = i + 1;
                run = 0;
            }
        }
        return rest < 0 ? -1 : start;
    }

    // 229
    public List<Integer> majorityElement2(int[] nums) {
        List<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num: nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        int length = nums.length;
        int temp = (int)Math.floor(length / 3);
        for (int num: nums)
            if (map.get(num) > temp && !list.contains(num))
                list.add(num);
        return list;
    }

    // 814
    public TreeNode pruneTree(TreeNode root) {
        if (root == null)
            return root;
        if (root.left != null)
            root.left = pruneTree(root.left);
        if (root.right != null)
            root.right = pruneTree(root.right);
        if (root.left == null && root.right == null)    // a leaf node
            if (root.val == 0)
                root = null;
        return root;
    }

    // 925      这道题的test case相当有问题
    public boolean isLongPressedName(String name, String typed) {
        int left = 0, right = 0, length1 = name.length(), length2 = typed.length();
        boolean reset = false;
        char former = 'A';
        //char firstChar = name.charAt(left);
        while (left < length1 && right < length2) {
            char c = name.charAt(left);
            if (former == 'A' || reset)
                former = c;
            reset = false;
            boolean flag = false;
            for (int i = right; i <length2; i++) {
                if (typed.charAt(i) == c) {     // typed find char c, then right go on.
                    flag = true;
                    right = i + 1;
                    former = c;
                    break;
                }
                if (typed.charAt(i) != former && typed.charAt(i) != c) {
                    // if typed diff from c or former, some other characters appear, so set left to zero
                    left = 0;
                    right = i + 1;
                    reset = true;
                    break;
                }
                if (i == length2 - 1)
                    return false;       // match the end point
            }
            if (flag)
                left++;
        }
        if (left == length1)
            return true;
        return false;
    }

    // 495
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int res = 0;
        int length = timeSeries.length;
        if (length == 0)
            return res;
        for (int i = 0; i < length - 1; i++) {
            if (timeSeries[i] + duration <= timeSeries[i + 1])
                res += duration;
            else
                res += timeSeries[i + 1] - timeSeries[i];
        }
        res += duration;        // the final
        return res;
    }

    // 509
    public int fib(int N) {
        if (N <= 1)
            return N;
        int[] fibs = new int[N + 1];
        fibs[0] = 0;
        fibs[1] = 1;
        for (int i = 2; i <= N; i++)
            fibs[i] = fibs[i - 1] + fibs[i - 2];
        return fibs[N];
    }

    public boolean judgeSquareSum(int c) {
        List<Integer> list = new ArrayList<>();
        int temp = (int)Math.floor(Math.sqrt(c));
        for (int i = 0; i <= temp; i++)
            list.add(i * i);
        for (int i = 0; i < list.size(); i++)
            if (list.contains(c - list.get(i)))
                return true;
        return false;
    }

    // 985
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int length = queries.length;
        int[] res = new int[length];
        int evenSum = 0;
        for (int a: A)
            if (a % 2 == 0)
                evenSum += a;
        for (int i = 0; i < length; i++) {
            int former = A[queries[i][1]];
            A[queries[i][1]] += queries[i][0];
            if (former % 2 != 0 && A[queries[i][1]] % 2 != 0);  // doNothing
                //res[i] = evenSum;
            else if (former % 2 != 0 && A[queries[i][1]] % 2 == 0)
                evenSum += A[queries[i][1]];
            else if (former % 2 == 0 && A[queries[i][1]] % 2 != 0)
                evenSum -= former;
            else    // former % 2 == 0 && A[queries[i][1]] % 2 == 0
                evenSum += queries[i][0];
            res[i] = evenSum;
        }
        return res;
    }

    // 830
    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        int length = S.length();
        int left = -1, right = -1;
        for (int i = 0; i < length - 2;) {
            char c1 = S.charAt(i);
            char c2 = S.charAt(i + 1);
            if (left >= 0) {
                char former = S.charAt(right);
                for (int k = right + 1; k < length; k++)
                    if (S.charAt(k) == former)
                        right++;
                    else
                        break;
                current.add(left);
                current.add(right);
                res.add(new ArrayList<>(current));
                current.clear();
                i = right + 1;
                left = -1;
                right = -1;
                continue;
            }
            char c3 = S.charAt(i + 2);
            if (c1 == c2 && c1 == c3) {
                left = i;
                right = i + 2;
            }
            else {
                left = -1;
                right = -1;
                i++;
            }
        }
        return res;
    }

    // 1260
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        int mount = n * m;
        k %= (n * m);
        int[] temp = new int[mount];
        int index = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                temp[index++] = grid[i][j];
        int first = n * m - k;
        for (int i = 0; i < mount; i++) {
            current.add(temp[first % mount]);
            first++;
            if (current.size() == m) {
                res.add(new ArrayList<>(current));
                current.clear();
            }
        }
        return res;
    }

    // 1261 暴力递归, 速度很慢, 752ms
    class FindElements {
        public TreeNode root;

        public FindElements(TreeNode root) {
            root.val = 0;
            initHelper(root);
            this.root = root;
        }

        public void initHelper(TreeNode root) {
            if (root == null)
                return;
            if (root.left != null)
                root.left.val = root.val * 2 + 1;
            if (root.right != null)
                root.right.val = root.val * 2 + 2;
            initHelper(root.left);
            initHelper(root.right);
        }

        public boolean find(int target) {
            return findHelper(root, target);
        }

        public boolean findHelper(TreeNode root, int target) {
            if (root == null)
                return false;
            if (root.val == target)
                return true;
            return findHelper(root.left, target) || findHelper(root.right, target);
        }
    }

    // 1261 给类添加一个HashSet成员变量, 使得find直接到Set中查找是否contains即可, 30ms
    class FindElements1 {
        public TreeNode root;
        public HashSet<Integer> set;

        public FindElements1(TreeNode root) {
            set = new HashSet<>();
            set.add(0);
            root.val = 0;
            initHelper(root);
            this.root = root;
        }

        public void initHelper(TreeNode root) {
            if (root == null)
                return;
            if (root.left != null) {
                root.left.val = root.val * 2 + 1;
                set.add(root.left.val);
            }
            if (root.right != null) {
                root.right.val = root.val * 2 + 2;
                set.add(root.right.val);
            }
            initHelper(root.left);
            initHelper(root.right);
        }

        public boolean find(int target) {
            return set.contains(target);
        }
    }

    // 1262
    public int maxSumDivThree(int[] nums) {
        int[] dps = {0, 0, 0};      // 记录前i个nums中,mod 3为0/1/2的最大和
        int[] temp = new int[3];    // 在循环里遍历一次之后,再赋值回给dps
        int sum = 0;
        for (int num: nums) {
            for (int dp: dps) {
                int current = num + dp;
                if (current % 3 == 0)
                    temp[0] = temp[0] > current ? temp[0] : current;
                else if (current % 3 == 1)
                    temp[1] = temp[1] > current ? temp[1] : current;
                else
                    temp[2] = temp[2] > current ? temp[2] : current;
            }
            dps[0] = temp[0];
            dps[1] = temp[1];
            dps[2] = temp[2];
        }
        return dps[0];
    }

    // 242
    public boolean isAnagram(String s, String t) {
        int[] s1 = new int[26];
        int[] s2 = new int[26];
        for (char c: s.toCharArray())
            s1[c - 'a']++;
        for (char c: t.toCharArray())
            s2[c - 'a']++;
        for (int i = 0; i < 26; i++)
            if (s1[i] != s2[i])
                return false;
        return true;
    }

    // 722      麻烦, 还要加空格? 毫无必要的题   (需要正则)
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        boolean flag = false;
        for (String src: source) {
            src = src.trim();
            int length = src.length();
            if (length == 0)
                continue;
            if (!flag) {
                if (length < 2)
                    res.add(src);
                else if (src.charAt(0) == '/' && src.charAt(1) == '/')
                    continue;
                else if (src.charAt(0) == '/' && src.charAt(1) == '*') {
                    if (length > 4 && src.charAt(length - 1) == '/' && src.charAt(length - 2) == '*')
                        continue;
                    flag = true;
                    continue;
                }
                else
                    res.add(src);
            }
            else {
                if (length > 2 && src.charAt(length - 1) == '/' && src.charAt(length - 2) == '*')
                    flag = false;
            }
        }
        return res;
    }

    // 938
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null)
            return 0;
        else if (root.val < L)
            return rangeSumBST(root.right, L, R);
        else if (root.val > R)
            return rangeSumBST(root.left, L, R);
        else
            return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }

    // 1005
    public int largestSumAfterKNegations(int[] A, int K) {
        Arrays.sort(A);
        for (int i = 0; i < K; i++) {
            A[0] = -A[0];
            Arrays.sort(A);
        }
        int sum = 0;
        for (int a: A)
            sum += a;
        return sum;
    }

    // 795
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        // 最大元素大于等于L,小于等于R. 相当于: 最大元素大于等于R - 最大元素小于等于L - 1
        return numSubarrayBoundedMaxHelper(A, R) - numSubarrayBoundedMaxHelper(A, L - 1);
    }

    public int numSubarrayBoundedMaxHelper(int[] A, int max) {
        int res = 0;
        int numSubArray = 0;
        for (int num: A) {
            if (num <= max) {
                numSubArray++;
                res += numSubArray;
            }
            else
                numSubArray = 0;
        }
        return res;
    }

    // 392
    public boolean isSubsequence(String s, String t) {
        int prev = 0;
        for (char c: s.toCharArray()) {
            int index = t.indexOf(c, prev);
            if (index == -1)
                return false;
            prev = index + 1;
        }
        return true;
    }

    // 792 最暴力的方法,直接循环调用P392,但效率肯定会很低
    public int numMatchingSubseq(String S, String[] words) {
        int res = 0;
        for (String word: words)
            if (isSubsequence(word, S))
                res++;
        return res;
    }

    // 1154
    public int dayOfYear(String date) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
        String[] dates = date.split("-");
        int year = Integer.valueOf(dates[0]);
        int month = Integer.valueOf(dates[1]);
        int day = Integer.valueOf(dates[2]);
        if (dayOfYearHelper(year))
            days[1]++;
        int res = 0;
        for (int i = 0; i < month - 1; i++)
            res += days[i];
        res += day;
        return res;
    }

    public boolean dayOfYearHelper(int year) {
        if (year % 100 != 0 && year % 4 == 0)
            return true;
        if (year % 100 == 0 && year % 400 == 0)
            return true;
        return false;
    }

    public String toHexspeak(String num) {
        Long l = Long.valueOf(num);
        String hex = Long.toHexString(l).toUpperCase();
        Character[] temp = {'2', '3', '4', '5', '6', '7', '8', '9'};
        List<Character> list = new ArrayList<>(Arrays.asList(temp));
        StringBuilder sb = new StringBuilder();
        for (char c: hex.toCharArray())
            if (list.contains(c))
                return "ERROR";
            else {
                if (c == '1')
                    c = 'I';
                if (c == '0')
                    c = 'O';
                sb.append(c);
            }
        return sb.toString();
    }

    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> res = new ArrayList<>();
        int left = 0, length = intervals.length;

        for (int i = 0; i < length; i++) {
            List<Integer> current = new ArrayList<>();
            if (intervals[i][1] <= toBeRemoved[0] || intervals[i][0] >= toBeRemoved[1]) {
                current.add(intervals[i][0]);
                current.add(intervals[i][1]);
                res.add(new ArrayList<>(current));
                continue;
            }
            else if (intervals[i][0] >= toBeRemoved[0] && intervals[i][1] <= toBeRemoved[1])
                continue;
            else if (intervals[i][0] < toBeRemoved[0] && intervals[i][1] < toBeRemoved[1]) {
                current.add(intervals[i][0]);
                current.add(toBeRemoved[0]);
                res.add(new ArrayList<>(current));
                continue;
            }
            else if (intervals[i][0] < toBeRemoved[0] && intervals[i][1] > toBeRemoved[1]) {
                current.add(intervals[i][0]);
                current.add(toBeRemoved[0]);
                res.add(new ArrayList<>(current));
                current.clear();
                current.add(toBeRemoved[1]);
                current.add(intervals[i][1]);
                res.add(new ArrayList<>(current));
                continue;
            }
            else if (intervals[i][0] < toBeRemoved[1] && intervals[i][1] > toBeRemoved[1]) {
                current.add(toBeRemoved[1]);
                current.add(intervals[i][1]);
                res.add(new ArrayList<>(current));
                continue;
            }
        }
        return res;
    }

    // 1275     其实枚举也就8种情况.
    public String tictactoe(int[][] moves) {
        int[][] temp = new int[3][3];
        int length = moves.length;
        int[] ac = {7, 56, 448, 73, 146, 292, 273, 84};     // 二进制AC结果
        int a = 0, b = 0;
        boolean flag = true;
        for (int[] move: moves) {
            if (flag)
                a += Math.pow(2, move[0] * 3 + move[1]);
            else
                b += Math.pow(2, move[0] * 3 + move[1]);
            flag = !flag;
        }
        for (int c: ac) {
            if ((a & c) == c)       // 必须用异或, 不能用等于. 比如A走了4格,只有3格是最终有效的,此时不等于
                return "A";
            if ((b & c) == c)
                return "B";
        }
        return length == 9 ? "Draw" : "Pending";
    }

    // 1276
    public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
        List<Integer> res = new ArrayList<>();
        if (tomatoSlices % 2 != 0 || (tomatoSlices - 2 * cheeseSlices) < 0 || (tomatoSlices - 4 * cheeseSlices > 0))
            return res;
        int a = (tomatoSlices - 2 * cheeseSlices) / 2;
        int b = cheeseSlices - a;
        res.add(a);
        res.add(b);
        return res;
    }

    // 1277, 找到当前位置所能构成的最大正方形的长度 dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1
    public int countSquares(int[][] matrix) {
        int res = 0;
        int m = matrix.length;
        if (m == 0)
            return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int min = Math.min(m, n);
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i -1][j - 1] == 1)
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        }
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                res += dp[i][j];

        return res;
    }

    // 1266
    public int minTimeToVisitAllPoints(int[][] points) {
        int length = points.length;
        if (length == 1)
            return 0;
        int prevX = points[0][0];
        int prevY = points[0][1];
        int res = 0;
        for (int i = 1; i < length; i++) {
            int currentX = points[i][0];
            int currentY = points[i][1];
            int diffX = Math.abs(currentX - prevX);
            int diffY = Math.abs(currentY - prevY);
            int max = Math.max(diffX, diffY);
            res += max;
            prevX = currentX;
            prevY = currentY;
        }
        return res;
    }

    // 1267     24ms, so slow.
    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] temp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int j1 = 0; j1 < n; j1++) {
                        if (j1 == j)
                            continue;
                        temp[i][j1] = 1;
                    }
                    for (int i1 = 0; i1 < m; i1++) {
                        if (i1 == i)
                            continue;
                        temp[i1][j] = 1;
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && temp[i][j] == 1)
                    res++;
            }
        }
        return res;
    }

    // 1267  4ms, 无须一个temp数组, 上面的复杂度是 m^2 * n^2 ,这里的复杂度是 2 * m * n
    public int countServers1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        int[] row = new int[m];
        int[] column = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    count++;
                    row[i]++;
                    column[j]++;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && row[i] == 1&& column[j] == 1)
                    count--;
            }
        }
        return count;
    }

    // 1268
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        List<String> current = new ArrayList<>();
        List<String> list = new ArrayList<>();
        char first = searchWord.charAt(0);
        for (String product: products) {
            if (product.charAt(0) == first)
                list.add(product);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            if (current.size() == 3)
                break;
            current.add(list.get(i));           // 不能直接get0,1,2,因为list可能长度不足3
        }
        res.add(new ArrayList<>(current));
        current.clear();
        int length = searchWord.length();
        for (int i = 1; i < length; i++) {
            char c = searchWord.charAt(i);
            int size = list.size();
            for (int k = 0; k < size; k++) {
                String tmp = list.get(k);
                if (tmp.length() <= i || tmp.charAt(i) != c) {  // 要判断长度,否则可能刚好是最后一个字符就超出边界
                    list.remove(k);
                    size--;
                    k--;
                }
            }
            for (int j = 0; j < size; j++) {
                if (current.size() == 3)
                    break;
                current.add(list.get(j));
            }
            res.add(new ArrayList<>(current));
            current.clear();
        }
        return res;
    }

    // 1269
    // Notice that the computational complexity does not depend of "arrlen".
    // 准确的说, 是当arrLen过大的时候, 后面的一长串都没有意义的。
    // 最简单的就是arrLen超出steps,后面都是废设。还能继续优化便是 steps / 2, 因为去到尽头也回不去原点了.
    public int numWays(int steps, int arrLen) {
        arrLen = arrLen > steps + 1 ? steps + 1 : arrLen;   // arrLen如果比steps大, 那么后面的其实都是废设
        int[][] dp = new int[arrLen + 2][steps + 1];
        int MOD = (int)Math.pow(10, 9) + 7;

        for (int j = 1; j <= steps; j++) {
            for (int i = 1; i <= arrLen; i++) {
                if (j == 1 && (i == 1 || i == 2))
                    dp[i][j] = 1;
                else {
                    long temp = (long)dp[i][j - 1] + dp[i - 1][j - 1] + dp[i + 1][j - 1];
                    // 原地不动 + 左移 + 右移  (不用担心超出边界, 因为超出边界就是0, dp数组特地调大也是因为如此)
                    temp %= MOD;
                    dp[i][j] = (int)temp;
                }
            }
        }
        return dp[1][steps];
    }

    // 1109 暴力
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] res = new int[n];
        for (int[] booking: bookings)
            for (int i = booking[0]; i <= booking[1]; i++)
                res[i - 1] += booking[2];
        return res;
    }

    // 1109 优化了一个先减再加, 速度快了几百倍
    public int[] corpFlightBookings1(int[][] bookings, int n) {
        int count[] = new int[n];
        for(int booking[]: bookings) {
            count[booking[0] - 1] += booking[2];    // 只给起始位置 增加
            if(booking[1] < n)                      // 其实就是booking[1] == n - 1
                count[booking[1]] -= booking[2];      // 给结束位置的下一个位置先减
        }

        for(int i = 1; i < n; i++) {          // 往前叠加, 那么booking[0] ~ booking[1]中间没有加的都会加回去
            count[i] += count[i - 1];         // 而booking[1] + 1减去的也会加回去, 也就是先减再加
        }
        return count;
    }

    // 1237
//    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {
//        List<List<Integer>> res = new ArrayList<>();
//        for (int x = 1; x <= z; x++)
//            for (int y = 1; y <= z; y++)
//                if (customfunction.f(x, y) == z)
//                    res.add(new ArrayList<>(Arrays.asList(x, y)));
//        return res;
//    }

    // 1110 后序遍历        *
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> list = new ArrayList<>();
        //建立set方便节点进行判断
        HashSet set = new HashSet();
        for (int i = 0; i < to_delete.length; i++) {
            set.add(to_delete[i]);
        }
        //直接把根插入集合，因为经过后续判断后就是删除后的root
        if (!set.contains(root.val)) {
            list.add(root);
        }
        //进行后序遍历
        dfs(root, set, list);
        return list;

    }

    public void dfs(TreeNode root, HashSet set, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        //后序遍历，即左右根
        dfs(root.left, set, list);
        dfs(root.right, set, list);

        //若该节点是需要删除的节点
        if (set.contains(root.val)) {
            //查看左节点是否在删除set，不在的话进行插入
            if (root.left != null && !set.contains(root.left.val)) {
                list.add(root.left);
            }
            //类似，查看右节点是否在删除set，不在的话进行插入
            if (root.right != null && !set.contains(root.right.val)) {
                list.add(root.right);
            }
        }
        //若该节点是不需要删除的节点，则判断左右节点，若需要删除直接指向null
        if (root.left != null && set.contains(root.left.val)) {
            root.left = null;
        }
        if (root.right != null && set.contains(root.right.val)) {
            root.right = null;
        }
    }

    // 1122 没有排序, 用HashMap记录出现在arr2的每个元素的次数, 然后先add这一部分, 再add剩余的部分
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int length = arr1.length;
        int[] res = new int[length];
        ArrayList<Integer> rests = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        for (int arr: arr2)
            set.add(arr);
        for (int arr: arr1)
            if (set.contains(arr))
                map.put(arr, map.getOrDefault(arr, 0) + 1);
            else
                rests.add(arr);
        Collections.sort(rests);
        int i = 0;
        for(int arr: arr2) {
            int value = map.get(arr);
            for (int k = 0; k < value; k++)
                res[i++] = arr;
        }
        for (Integer rest: rests)
            res[i++] = rest;
        return res;
    }

    public int[] relativeSortArray1(int[] arr1, int[] arr2) {
        int[] counts = new int[1001];           // 题目规定了数组的值范围是 0 ~ 1000
        int[] res = new int[arr1.length];
        int i = 0;
        for (int arr: arr1)
            counts[arr]++;      // 记录每一个数组元素的个数
        for (int arr: arr2)
            while (counts[arr]-- > 0)
                res[i++] = arr;     // 顺序添加arr2的元素
//        for (int arr: arr1)           // 这样剩下的并没有排序, 所以还是老实地0 ~ 1000慢慢来
        for (int arr = 0; arr <= 1000; arr++)
            while (counts[arr]-- > 0)
                res[i++] = arr;     // 添加剩下的
        return res;
    }

    // 1123 题意是指找所有最深节点的LCA,所以当一个节点的左右子树深度相同,那么结果就是该节点
    // 如果是左子树更深, 那么结果在左子树中,递归左子树. 右子树同理。
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if (root == null)
            return root;
        int leftDepth = maxDepth1(root.left);
        int rightDepth = maxDepth1(root.right);
        if (leftDepth == rightDepth)
            return root;
        if (leftDepth > rightDepth)
            return lcaDeepestLeaves(root.left);
        return lcaDeepestLeaves(root.right);
    }

    // 1123 helper
    public int maxDepth1(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(maxDepth1(root.left) + 1, maxDepth1(root.right) + 1);
    }

    // 859
    public boolean buddyStrings(String A, String B) {
        int times = 0;              // record the number of characters which are difference
        int lengthA = A.length();
        int lengthB = B.length();
        int first = -1, second = -1;    // record the two difference index if exists.
        if (lengthA != lengthB || lengthA < 2)
            return false;
        HashSet<Character> set = new HashSet<>();
        boolean flag = false;
        // if times == 0, check whether there are at least two same characters in A.
        for (int i = 0; i < lengthA; i++) {
            char a = A.charAt(i);
            if (!flag && set.contains(a))
                flag = true;
            if (!flag)
                set.add(a);
            char b = B.charAt(i);
            if (a != b) {
                times++;
                if (times == 1)
                    first = i;
                if (times == 2)
                    second = i;
            }
            if (times >= 3)
                return false;
        }
        if (times == 2)
            return A.charAt(first) == B.charAt(second) && A.charAt(second) == B.charAt(first);
        return times == 0 && flag;    // length >= 2
    }

    // 856      思路: "(()(()))" == "(())" + ((()))"
    // 当遇到 "()",其实无须考虑后面的,肯定平衡,所以直接加上当前的结果2 ^ 层数 - 1
    public int scoreOfParentheses(String S) {
        int depth = 0;
        int res = 0;
        int length = S.length();
        for (int i = 0; i < length; i++) {
            char c = S.charAt(i);
            if (c == '(')
                depth++;
            else
                depth--;
            if (c == ')' && S.charAt(i - 1) == '(')
                res += 1 << depth;
        }
        return res;
    }

    // 858
    public int mirrorReflection(int p, int q) {
        if (q == 0)
            return 0;
        int g = gcd(p, q);
        int s = p * q / g;
        int k = s / q;
        if (k % 2 == 0)
            return 2;
        return k * q / p % 2 == 0 ? 0 : 1;
    }

    public int gcd(int a, int b) {
        //return a == 0 ? b : gcd(b % a, a);
        return b == 0 ? a : gcd(b, a % b);
    }

    // 860
    public boolean lemonadeChange(int[] bills) {
        int numOfFive = 0, numOfTen = 0;
        for (int bill: bills) {
            if (bill == 20) {
                if (numOfTen >= 1 && numOfFive >= 1) {
                    numOfTen--;
                    numOfFive--;
                }
                else if (numOfTen == 0 && numOfFive >= 3)
                    numOfFive -= 3;
                else
                    return false;
            }
            else if (bill == 10) {
                if (numOfFive == 0)
                    return false;
                numOfFive--;
                numOfTen++;
            }
            else   // bill == 5
                numOfFive++;
        }
        return true;
    }

    public int matrixScore(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int temp = 0;
        for (int i = 0; i < m; i++) {
            if (A[i][0] == 0)
                rotateHelper(A, false, i);
        }
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (A[i][j] == 1)
                    temp++;
            }
            if (temp <= m / 2)
                rotateHelper(A, true, j);
            temp = 0;
        }
        int res = 0;
        int base;
        for (int i = 0; i < m; i++) {
            base = 0;
            for (int j = n - 1; j >= 0; j--) {
                res += A[i][j] == 0 ? 0 : Math.pow(2, base);
                base++;
            }
        }
        return res;
    }

    public void rotateHelper(int[][] A, boolean flag, int index) {
        if (flag) {     // 垂直rotate
            for (int i = 0; i < A.length; i++)
                A[i][index] = A[i][index] == 1 ? 0 : 1;
        }
        else {
            for (int j = 0; j < A[0].length; j++)
                A[index][j] = A[index][j] == 1 ? 0 : 1;
        }
    }

    // 867  矩阵转置
    public int[][] transpose(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res[j][i] = A[i][j];
        return res;
    }

    // 868
    public int binaryGap(int N) {
        int res = 0;
        if (isPowerOfTwo(N))
            return res;
        StringBuilder sb = new StringBuilder();
        while (N > 0) {
            int mod = N % 2;
            sb.append(mod);
            N /= 2;
        }
        sb.reverse();
        String str = sb.toString();
        int length = str.length();
        int prev = 0, next = -1;
        boolean flag = false;
        for (int i = 1; i < length; i++) {
            if (str.charAt(i) == '1') {
                if (!flag) {
                    next = i;
                    flag = true;
                }
                else {
                    prev = next;
                    next = i;
                }
                res = res > next - prev ? res : next - prev;
            }
        }
        return res;
    }

    // 869 回溯,无剪枝 613ms
    public boolean reorderedPowerOf2(int N) {
        String str = String.valueOf(N);
        int length = str.length();
        boolean[] visited = new boolean[length];
        return reorderedPowerOf2Helper(str, visited, length, 0, "");
    }

    public boolean reorderedPowerOf2Helper(String str, boolean[] visited, int length, int current, String tmp) {
        if (current == length)
            return isPowerOfTwo(Integer.valueOf(tmp));

        for (int i = 0; i < length; i++) {
            if (visited[i])
                continue;
            char c = str.charAt(i);
            if (current == 0 && c == '0')
                continue;
            tmp += c;
            visited[i] = true;
            if (reorderedPowerOf2Helper(str, visited, length, current + 1, tmp))
                return true;
            tmp = tmp.substring(0, current);
            visited[i] = false;
        }
        return false;
    }

    // 870      习惯性地直接回溯找全排列,但会超时,这题不适合用回溯
    public int[] advantageCount1(int[] A, int[] B) {
        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        int length = A.length;
        boolean[] visited = new boolean[length];
        advantageCountHelper(combinations, combination, visited,  0, length, A);
        int[] res = new int[length];
        int max = 0;
        int maxIndex = 0;
        int length1 = combinations.size();
        for (int i = 0; i < length1; i++) {
            List<Integer> list = combinations.get(i);
            int current = 0;
            int size = list.size();
            for (int j = 0; j < size; j++)
                if (list.get(j) > B[j])
                    current++;
            if (max < current) {
                max = current;
                maxIndex = i;
            }
        }
        List<Integer> maxList = combinations.get(maxIndex);
        for (int i = 0; i < length; i++)
            res[i] = maxList.get(i);
        return res;

    }

    public void advantageCountHelper(List<List<Integer>> combinations, List<Integer> combination,
                                     boolean[] visited, int current, int length, int[] A) {
        if (current == length) {
            combinations.add(new ArrayList<>(combination));
            return;
        }
        for (int i = 0; i < length; i++) {
            if (visited[i])
                continue;
            combination.add(A[i]);
            visited[i] = true;
            advantageCountHelper(combinations, combination, visited, current + 1, length, A);
            visited[i] = false;
            combination.remove(current);
        }
    }

    // 870 贪心, 如果没有更大,那就先用最小。否则就找最小的更大.
    public int[] advantageCount(int[] A, int[] B) {
        int length = A.length;
        List<Integer> list = Arrays.stream(A).boxed().collect(Collectors.toList());
        Collections.sort(list);
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            int size = list.size() - 1;
            System.out.println(list.get(size));
            if (list.get(size) <= B[i]) {        //没有更大的,那就把最小的用了
                res[i] = list.get(0);
                list.remove(0);
            }
            else {
                for (int j = 0; j <= size; j++) {
                    int temp = list.get(j);
                    if (temp > B[i]) {
                        res[i] = temp;
                        list.remove(j);
                        break;
                    }
                }
            }
        }
        return res;
    }

    // 5279
    public int subtractProductAndSum(int n) {
        String str = String.valueOf(n);
        int multipy = 1;
        int sum = 0;
        for (char c: str.toCharArray()) {
            multipy *= c - '0';
            sum += c - '0';
        }
        int res = multipy - sum;
        return res;
    }

    // 5280
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> datas = Arrays.stream(groupSizes).boxed().collect(Collectors.toList());
        int length = groupSizes.length;
        boolean[] visited = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (visited[i])
                continue;
            int number = datas.get(i);
            List<Integer> current = new ArrayList<>();
            for (int j = 0; j < number; j++) {
                int index = datas.indexOf(number);
                datas.set(index, -1);
                current.add(index);
                visited[index] = true;
            }
            res.add(new ArrayList<>(current));
        }
        return res;
    }

    // 5281
    public int smallestDivisor(int[] nums, int threshold) {
        int left = 0;
        int right = 1_000_001;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            long sum = 0;
            for (int num: nums)
                sum += (num + mid - 1) / mid;   // 这个加 mid - 1是为了向上取整
            if (sum > threshold)
                left = mid;
            else
                right = mid;
        }
        return right;
    }

    // 872
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        leafSimilarHelper(root1, list1);
        leafSimilarHelper(root2, list2);
        return list1.equals(list2);         // List之间可以直接用equals来判断是否相等
//        int size = list1.size();
//        if (size != list2.size())
//            return false;
//        for (int i = 0; i < size; i++)
//            if (list1.get(i) != list2.get(i))
//                return false;
//        return true;
    }

    public void leafSimilarHelper(TreeNode root, List<Integer> list) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            list.add(root.val);
        else {
            leafSimilarHelper(root.left, list);
            leafSimilarHelper(root.right, list);
        }
    }

    // 873
    // 每次先取数组的前两个数,然后调用库函数Arrays.binarySearch二分查找   134ms
    // 也可以直接构建一个HashSet,然后while条件是 while(set.contains(sum)),效率更高,79ms
    public int lenLongestFibSubseq(int[] A) {
        int max = 2;
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                int tempI = A[i];
                int tempJ = A[j];
                int sum = tempI + tempJ;
                int current = 2;
                while (Arrays.binarySearch(A, sum) >= 0) {
                    tempI = tempJ;
                    tempJ = sum;
                    sum = tempI + tempJ;
                    current++;
                }
                max = max > current ? max : current;
            }
        }
        return max < 3 ? 0 : max;
    }

    // 876
    public ListNode middleNode(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode current = head;        // 不能直接操作head
        while (current != null) {
            list.add(current.val);
            current = current.next;
        }
        int size = list.size() / 2;     // 链表可能会有重复的值
        int i = 1;
        while (i <= size) {
            head = head.next;
            i++;
        }
        return head;
    }

    // 876 double pointers.
    public ListNode middleNode1(ListNode head) {
        ListNode dummy = head;
        while (dummy != null && dummy.next != null) {
            dummy = dummy.next.next;    // fast pointer
            head = head.next;           // slow pointer
        }
        return head;
    }

    // 877
    static int first = 0;
    public boolean stoneGame(int[] piles) {
        int length = piles.length;
        int[][] dp = new int[length][length];
        stoneGameHelper(piles, 0, length - 1,true);
        int sum = 0;
        for (int pile: piles)
            sum += pile;
        if (first > sum / 2)
            return true;
        return false;
    }

    public int stoneGameHelper(int[] piles, int left, int right, boolean flag) {
        if (left == right - 1)
            return Math.max(piles[left], piles[right]);
        if (left == right)
            return piles[right];
        int temp = Math.max(
                stoneGameHelper(piles, left + 1, right, !flag) + piles[left],
                stoneGameHelper(piles, left, right - 1, !flag) + piles[right]);
        if (flag)
            first += temp;
        // if (left == 0 && right == piles.length - 1)
        return 0;
    }

    // 878 主要思路: 先求最大公因子gcd, 再求最小公倍数lcm = A * B / gcd
    // 若一个number是 A和 B的第 x个神奇数字, 那么: x = number / A + number / B - number / lcm
    // 即分别计算number是 A或 B的第 M 个倍数,再减去重复计算了的
    // 且number % A == 0 && number % B == 0
    public int nthMagicalNumber(int N, int A, int B) {
        int gcd = gcd(A, B);
        int lcm = A * B / gcd;
        long left = 2;
        long right = 40_000_000_000_001L;   // 如果刚好结果是4 * 10 ^ 13, 在一个特殊的testcase会死循环
        long middle = 2;
        int mod = 0;
        int temp = (int)Math.pow(10, 9) + 7;
        while (left < right) {
            middle = (left + right) / 2;
            long index = (middle / A + middle / B - middle / lcm);
            if (index > N)
                right = middle;
            else if (index < N)
                left = middle;
            else {
                mod = (int)Math.min(middle % A, middle % B);    // 有余
                break;
            }
        }
        return (int)((middle - mod) % temp);
    }

    // 879      TODO
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int length = profit.length;
        int[][] dp = new int[length][P + 1];    //
        if (group[0] <= G) {
            for (int i = 0; i <= profit[0]; i++)
                dp[0][i] = 1;
        }

        for (int i = 0; i < length - 1; i++) {
            int tmp = P;
            tmp = tmp - profit[i];
            tmp = tmp < P ? 0 : tmp;
            dp[i + 1][P] = dp[i][P] + dp[i][tmp];
            for (int j = 0; j < tmp; j++)
                dp[i + 1][j] = dp[i + 1][P];
        }
        return dp[length - 1][P];
    }

    // 883
    public int projectionArea(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;         // n == m
        int res = 0;
        for (int i = 0; i < m; i++) {
            int yMax = 0;
            for (int j = 0; j < n; j++) {
                yMax = yMax < grid[i][j] ? grid[i][j] : yMax;
            }
            res += yMax;
        }
        for (int j = 0; j < n; j++) {
            int xMax = 0;
            for (int i = 0; i < m; i++) {
                xMax = xMax < grid[i][j] ? grid[i][j] : xMax;
            }
            res += xMax;
        }
        int temp = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 0)
                    temp++;
        res += m * n;
        res -= temp;
        return res;
    }

    // 881
    public int numRescueBoats(int[] people, int limit) {
        int res = 0;
        int right = people.length - 1;
        int left = 0;
        Arrays.sort(people);
        while (left <= right) {
            if (left == right) {
                res++;      // 只剩下最后一个,直接一个走,结束
                break;
            }
            if (people[left] + people[right] > limit) {
                res++;
                right--;        // 先载最重的, 而且最小的也无法一起载,那么就最重的单独走
            }
            else {
                res++;
                right--;        // 最重的与最轻的一起走
                left++;
            }
        }
        return res;
    }

    // 880
    public String decodeAtIndex(String S, int K) {
        long size = 0;
        for (char c: S.toCharArray())
            if (c < 'a')
                size *= (c - '0');
            else
                size++;
        S = new StringBuilder(S).reverse().toString();
        for (char c: S.toCharArray()) {
            K %= size;
            if (K == 0 && c >= 'a')
                return String.valueOf(c);
            if (c < 'a')
                size /= (c - '0');
            else
                size--;
        }
        throw null;
    }

    // 884          用HashSet
    public String[] uncommonFromSentences(String A, String B) {
        String[] as = A.split("\\s+");
        String[] bs = B.split("\\s+");
        Set<String> set = new HashSet<>();
        List<String> res = new ArrayList<>();
        for (String a: as) {
            if (!set.contains(a)) {
                set.add(a);
                res.add(a);
            }
            else
                res.remove(a);
        }
        for (String b: bs) {
            if (!set.contains(b)) {
                set.add(b);
                res.add(b);
            }
            else
                res.remove(b);
        }
        int size = res.size();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++)
            strings[i] = res.get(i);
        return strings;
    }

    // 884      用HashMap
    public String[] uncommonFromSentences1(String A, String B) {
        String[] as = A.split(" ");                     // " "效率比\\s+要快.
        String[] bs = B.split(" ");
        Map<String, Integer> map = new HashMap<>();
        for (String a: as)
            map.put(a, map.getOrDefault(a, 2) - 1);
        for (String b: bs)
            map.put(b, map.getOrDefault(b, 2) - 1);
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet())
            if (entry.getValue() == 1)
                list.add(entry.getKey());
        String[] res = new String[list.size()];
        return list.toArray(res);               // List转数组
    }

    // 885
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        List<int[]> first = new ArrayList<>();
        boolean x = true;    // true for x move, false for y move
        boolean add = true;     // true for add, false for decease
        int current = 1;
        int size = 1;
        int target = R * C;
        first.add(new int[] {r0, c0});
        while (size < target) {
            if (x) {
                if (r0 < 0 || r0 >= R) {
                    c0 += add ? current : -current;
                    x = !x;
                    continue;
                }
                for (int i = 0; i < current; i++) {
                    c0 += add ? 1 : -1;
                    if (c0 >= 0 && c0 < C) {
                        first.add(new int[] {r0, c0});
                        size++;
                    }
                }
                x = !x;
            }
            else {
                if (c0 < 0 || c0 >= C) {
                    r0 += add ? current : -current;
                    x = !x;
                    add = !add;
                    current++;
                    continue;
                }
                for (int i = 0; i < current; i++) {
                    r0 += add ? 1 : -1;
                    if (r0 >= 0 && r0 < R) {
                        first.add(new int[] {r0, c0});
                        size++;
                    }
                }
                x = !x;
                add = !add;
                current++;
            }
        }
        int[][] res = new int[target][2];
        int i = 0;
        for (int[] ints: first) {
            res[i][0] = ints[0];
            res[i++][1] = ints[1];
        }
        return res;
    }

    // 886 TODO
    public boolean possibleBipartition(int N, int[][] dislikes) {
        int[] color = new int[N + 1];   // begin at index 1
        Arrays.sort(dislikes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int tmp = o1[0] - o2[0];
                if (tmp != 0)
                    return tmp;
                return o1[1] - o2[1];
            }
        });
        int countA = 0, countB = 0, begin = 0;
        for (int i = begin; i < dislikes.length; i++) {
            int a = dislikes[i][0];
            int b = dislikes[i][1];
            begin++;
            if (color[a] == 0 && color[b] == 0) {
                color[a] = 1;
                color[b] = 2;
                if (possibleBinpartitionHelper(N, dislikes, i + 1, color))
                    return true;
                else {
                    color[a] = 2;
                    color[b] = 1;
                }
            }
            else if (color[a] != 0 && color[b] == 0)
                color[b] = color[a] == 1 ? 2 : 1;
            else if (color[a] == 0 && color[b] != 0)
                color[a] = color[b] == 1 ? 2 : 1;
            else if (color[a] == color[b])
                return false;
        }
        return true;
    }

    public boolean possibleBinpartitionHelper(int N, int[][] dislikes, int begin, int[] color) {
        for (int i = begin; i < dislikes.length; i++) {
            int a = dislikes[i][0];
            int b = dislikes[i][1];
            System.out.println();
            for (int q = 0; q < color.length; q++)
                System.out.print(color[q] + " , ");
            System.out.println();
            begin++;
            if (color[a] == 0 && color[b] == 0) {
                color[a] = 1;
                color[b] = 2;
                if (possibleBinpartitionHelper(N, dislikes, i + 1, color))
                    return true;
                else {
                    System.out.println("---------------------------");
                    color[a] = 2;
                    color[b] = 1;
                    for (int w = 0; w < color.length; w++)
                        System.out.print(color[w] + " , ");
                }
            }
            else if (color[a] != 0 && color[b] == 0)
                color[b] = color[a] == 1 ? 2 : 1;
            else if (color[a] == 0 && color[b] != 0)
                color[a] = color[b] == 1 ? 2 : 1;
            else if (color[a] == color[b])
                return false;
        }
        return true;
    }

    // 888  暴力法 太慢
    // 假设 sumA = sumB + diff
    // 交换之后: sumA - a + b = sumB - b + a  ==>  diff + 2b = 2a ==> a = b + diff2
    // 先把 A存入HashSet, 然后遍历 B, 只要找到值为 b + diff2, 那么此时满足, 结果为: {b + diff2, b}
    // 通过一个 Set, 使得原本 n ^ 2 的复杂度变成 2n
    public int[] fairCandySwap(int[] A, int[] B) {
        int sumA = 0, sumB = 0;
        for (int a: A)
            sumA += a;
        for (int b: B)
            sumB += b;
        int diff2 = (sumA - sumB) / 2;
        Set<Integer> set = new HashSet<>();
        for (int a: A)
            set.add(a);
        for (int b: B)
            if (set.contains(b + diff2))
                return new int[] {b + diff2, b};
        return null;
    }

    public static String leetCodeReplace(String str) {
        str = str.replace('[', '{');
        str = str.replace(']', '}');
        return str;
    }

    // 890
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        HashMap<Character, List<Integer>> map = new HashMap<>();
        int length = pattern.length();
        for (int i = 0; i < length; i++) {
            Character c = pattern.charAt(i);
            List<Integer> list = map.getOrDefault(c, new ArrayList<>());
            list.add(i);
            map.put(c, list);
        }
        List<String> res = new ArrayList<>();
        for (String word: words)
            if (isIsomorphic(word, map))
                res.add(word);
        return res;
    }

    public boolean isIsomorphic(String s, HashMap<Character, List<Integer>> map) {
        HashSet<Character> set = new HashSet<>();
        for (Map.Entry<Character, List<Integer>> entry: map.entrySet()) {
            List<Integer> list = entry.getValue();
            Character current = s.charAt(list.get(0));
            if (set.contains(current))
                return false;           // exists previously
            set.add(current);
            for (int i = 1; i < list.size(); i++)
                if (s.charAt(list.get(i)) != current)
                    return false;
        }
        return true;
    }

    // 890
    public List<String> findAndReplacePattern1(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        for (String word: words)
            if (isIsomorphic1(word, pattern))
                res.add(word);
        return res;
    }

    // 891 TODO 关键要解决数组的长度最大为20000, 如何存储2 ^ 20000是个大问题。。
    public int sumSubseqWidths(int[] A) {
        Arrays.sort(A);
        int mod = (int)Math.pow(10, 9) + 7;
        long res = 0;
        long pow = 1;
        for (int i = 0; i < A.length - 1; i++) {
            pow = 1;
            for (int j = i + 1; j < A.length; j++) {
                int diff = A[j] - A[i];
                long temp = (long)diff * pow;
                res += (long)diff * pow;
                pow *= 2;
                res %= mod;
            }
        }
        return (int)res;
    }

    // 892
    public int surfaceArea(int[][] grid) {
        int sum = 0, over = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                sum += grid[i][j];
                if (grid[i][j] > 1)
                    over += grid[i][j] - 1;     // 竖着看,每两个中间就有1个重叠
                if (j == grid[0].length - 1)
                    continue;               // 接下来是左右看, j到达最后就不需要继续了,否则超出index了
                over += Math.min(grid[i][j], grid[i][j + 1]);
                over += Math.min(grid[j][i], grid[j + 1][i]);
            }
        }
        return sum * 6 - over * 2;
    }

    // 893
    public int numSpecialEquivGroups(String[] A) {
        int res = 0;
        Set<String> set = new HashSet<>();
        for (String a: A) {
            int length = a.length();
            List<Character> tmp1 = new ArrayList<>();
            List<Character> tmp2 = new ArrayList<>();
            for (int i = 0; i < length; i++)
                if (i % 2 != 0)
                    tmp1.add(a.charAt(i));
                else
                    tmp2.add(a.charAt(i));
            Collections.sort(tmp1);
            Collections.sort(tmp2);
            String str1 = "", str2 = "";
            for (Character c: tmp1)
                str1 += c;
            for (Character c: tmp2)
                str2 += c;
            str1 += str2;
            set.add(str1);
        }
        return set.size();
    }

    // 894, 递归,把总node数分为左,根,右进行递归
    public List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode> res = new ArrayList<>();
        if (N % 2 == 0)
            return res;     // null
        if (N == 1) {
            TreeNode head = new TreeNode(0);    // 1个结点的时候,直接返回
            res.add(head);
            return res;
        }
        for (int i = 1; i < N; i += 2) {
            List<TreeNode> left = allPossibleFBT(i);
            List<TreeNode> right = allPossibleFBT(N - 1 - i);
            for (TreeNode l: left) {
                for (TreeNode r: right) {
                    TreeNode head = new TreeNode(0);
                    head.left = l;
                    head.right = r;
                    res.add(head);
                }
            }
        }
        return res;
    }

    // 896
    public boolean isMonotonic(int[] A) {
        if (A.length <= 2)
            return true;
        boolean add = true;
        for (int i = 0; i < A.length - 1; i++)
            if (A[i] > A[i + 1]) {
                add = false;
                break;
            }
        if (add)
            return true;
        boolean decrease = true;
        for (int i = 0; i < A.length - 1; i++)
            if (A[i] < A[i + 1])
                return false;
        return true;
    }

    // 5126
    public int findSpecialInteger(int[] arr) {
        int length = arr.length;
        int threshold = length / 4 + 1;
        for (int i = 0; i <= length - threshold; i++)
            if (arr[i] == arr[i + threshold - 1])
                return arr[i];
        return -1;
    }

    // 5127
    public int removeCoveredIntervals(int[][] intervals) {
        int m = intervals.length;
        boolean[] delete = new boolean[m];
        int count = 0;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < i; j++) {
                if (delete[j])
                    continue;
                if (intervals[j][0] >= intervals[i][0] && intervals[j][1] <= intervals[i][1]) {
                    delete[j] = true;
                    count++;
                }
                else if (intervals[j][0] <= intervals[i][0] && intervals[j][1] >= intervals[i][1]) {
                    if (delete[i])
                        continue;       //avoid to recount
                    delete[i] = true;
                    count++;
                }
            }
        }
        return m - count;
    }

    // 5129
    public int minFallingPathSum(int[][] arr) {
        int length = arr.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == 0) {
                    dp[i][j] = arr[i][j];
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE;
                for (int j1 = 0; j1 < length; j1++) {
                    if (j1 == j)
                        continue;
                    int temp = arr[i][j] + dp[i - 1][j1];
                    dp[i][j] = dp[i][j] >= temp ? temp : dp[i][j];
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int k = 0; k < length; k++)
            res = res >= dp[length - 1][k] ? dp[length - 1][k] : res;
        return res;
    }

    // 1290
    public int getDecimalValue(ListNode head) {
        int res = 0;
        int tmp = -1;
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int size = list.size();
        for (int i = size - 1; i >= 0; i--) {
            int index = list.get(i);
            if (index == 1) {
                if (tmp == -1) {
                    res += 1;
                }
                else {
                    res += 2 << tmp;
                }
            }
            tmp++;
        }
        return res;
    }

    // 1290
    public int getDecimalValue1(ListNode head) {
        int result = 0;
        ListNode p = head;
        while (p != null) {
            result += p.val;
            result <<= 1;
            p = p.next;
        }
        result >>= 1;
        return result;
    }

    // 1290
    public int getDecimalValue2(ListNode head) {
        int tmp = 0;
        while (null != head) {
            tmp = tmp * 2 + head.val;
            head = head.next;
        }
        return tmp;
    }

    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> list = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        sequentialDigitsGenerator(list, 2, 0);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int current = list.get(i);
            if (current < low)
                continue;
            if (current > high)
                break;
            res.add(current);
        }
        return res;
    }

    public void sequentialDigitsGenerator(List<Integer> list, int digit, int begin) {
        if (digit == 10)        // 10 ^ 9 是11位
            return;
        if (digit + begin == 10) {
            sequentialDigitsGenerator(list, digit + 1, 0);
            return;
        }
        int first = begin + 1;
        int tmp = (int)Math.pow(10, digit - 1);
        int current = 0;
        for (int i = 0; i < digit; i++) {
            current += first++ * tmp;
            tmp /= 10;
        }
        list.add(current);
        sequentialDigitsGenerator(list, digit, begin + 1);
    }

    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;
        int min = Math.min(m, n);
        int left = 0, right = min;
        boolean flag = true;
        while (left <= right) {
            flag = true;
            int mid = (left + right) / 2;
//            int minSum = Integer.MAX_VALUE;
            for (int i = 0; i <= m - mid; i++) {
                for (int j = 0; j <= n - mid; j++) {    // (i, j)是左上角的起点
                    int sum = 0;
                    for (int i1 = 0; i1 < mid; i1++) {
                        for (int j1 = 0; j1 < mid; j1++) {
                            sum += mat[i + i1][j + j1];
                        }
                    }
//                    minSum = minSum < sum ? minSum : sum;
                    if (sum <= threshold) {
                        left = mid + 1;
                        i = Integer.MAX_VALUE - 1;
                        j = Integer.MAX_VALUE - 1;
                        continue;
                    }
                }
            }
//            if (minSum > threshold) {
            right = mid - 1;
                //continue;
            //}
            flag = false;
        }
        return left;
    }


    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> res = new ArrayList<>();
        res.add(Integer.MAX_VALUE);
        bfs(0, 0, k, 0, m, n, grid, res, -1, -1, -1);
        return res.get(0);
    }

    public void bfs(int x, int y, int r, int distance, int m, int n, int[][] grid, List<Integer> res,
                    int px, int py, int pv) {
        if (x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == 2) {
            grid[px][py] = pv;
            return;
        }
        if (grid[x][y] == 1 && r == 0) {
            grid[px][py] = pv;
            return;
        }
        if (x == m - 1 && y == n - 1) {
            int current = res.get(0);
            if (distance < current) {
                res.remove(0);
                res.add(distance);
            }
            grid[px][py] = pv;
            return;
        }
        r = grid[x][y] == 1 ? r - 1 : r;
        distance++;
        grid[x][y] = 2;
        bfs(x - 1, y, r, distance, m, n, grid, res, x, y, grid[x][y]);
        bfs(x + 1, y, r, distance, m, n, grid, res, x, y, grid[x][y]);
        bfs(x, y - 1, r, distance, m, n, grid, res, x, y, grid[x][y]);
        bfs(x, y + 1, r, distance, m, n, grid, res, x, y, grid[x][y]);
    }

    // 665
    public boolean checkPossibility(int[] nums) {
        int length = nums.length;
        boolean flag = true;
        int index = 0;
        for (int i = 0; i < length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                flag = false;
                index = i;
                break;
            }
        }
        if (flag)
            return true;    // 不需要1次转换就已经是非递减数列
        int[] tmp1 = new int[length];
        int[] tmp2 = new int[length];
        System.arraycopy(nums, 0, tmp1, 0, length);
        System.arraycopy(nums, 0, tmp2, 0, length);
        tmp1[index] = tmp1[index + 1];          //把nums[i] = nums[i + 1]
        tmp2[index + 1] = tmp2[index];          //把nums[i + 1] = nums[i]
        boolean flag1 = true, flag2 = true;
        for (int i = 0; i < length - 1; i++) {
            if (tmp1[i] > tmp1[i + 1]) {
                flag1 = false;
                break;
            }
        }
        for (int i = 0; i < length - 1; i++) {
            if (tmp2[i] > tmp2[i + 1]) {
                flag2 = false;
                break;
            }
        }
        return flag1 || flag2;
    }

    // 290
    public boolean wordPattern(String pattern, String str) {
        Map<Character, List<Integer>> map = new HashMap<>();
        String[] strings = str.split(" ");
        int length = pattern.length();
        if (strings.length != length)
            return false;
        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);
            List<Integer> list = map.getOrDefault(c, new ArrayList<>());
            list.add(i);
            map.put(c, new ArrayList<>(list));
        }
        Set<String> set = new HashSet<>();  // used to store the prev values
        for (List<Integer> list: map.values()) {
            String s = strings[list.get(0)];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (!s.equals(strings[list.get(i)]) || set.contains(s))
                    return false;
            }
            set.add(s);
        }
        return true;
    }

    // 383
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        for (char c: ransomNote.toCharArray())
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        for (char c: magazine.toCharArray())
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        for (Map.Entry<Character, Integer> entry: map1.entrySet()) {
            char c = entry.getKey();
            int i = entry.getValue();
            if (!map2.containsKey(c) || map2.get(c) < i)
                return false;
        }
        return true;
    }

    // 367
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        while (left <= right) {
            int mid = (left + right) / 2;
            long tmp = (long)mid * mid;         // long 防止溢出
            if (tmp == num)
                return true;
            if (tmp < num)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }

    // 345
    public String reverseVowels(String s) {
        List<Character> list = new ArrayList<>();
        list.add('a');
        list.add('e');
        list.add('i');
        list.add('o');
        list.add('u');
        list.add('A');
        list.add('E');
        list.add('I');
        list.add('U');
        list.add('O');
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (list.contains(c))
                indexs.add(i);
        }
        StringBuilder sb = new StringBuilder(s);
        int size = indexs.size();
        for (int i = 0; i < size / 2; i++) {
            int firstIndex = indexs.get(i);
            char first = sb.charAt(firstIndex);
            int secondIndex = indexs.get(size - 1 - i);
            char second = sb.charAt(secondIndex);
            sb.setCharAt(firstIndex, second);
            sb.setCharAt(secondIndex, first);
        }
        return sb.toString();
    }

    // 897
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        TreeNode res = new TreeNode(-1);
        inorder(root, list);
        TreeNode current = res;
        for (int i: list) {
            current.right = new TreeNode(i);
            current = current.right;
        }
        return res.right;
    }

    public void inorder(TreeNode root, List<Integer> res) {
        if (root == null)
            return;
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }

    // 908
    public int smallestRangeI(int[] A, int K) {
        if (A.length == 1)
            return 0;
        Arrays.sort(A);                         //nlogn不如 n高效
        int diff = A[A.length - 1] - A[0];
        if (diff <= 2 * K)
            return 0;
        return diff - 2 * K;
    }

    // 908
    public int smallestRangeI1(int[] A, int K) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int a: A) {
            min = min < a ? min : a;            // O(n)
            max = max > a ? max : a;
        }
        int res = max - min - 2 * K;
        return res <= 0 ? 0 : res;
    }

    // 914
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int d: deck) {
            map.put(d, map.getOrDefault(d, 0) + 1);
        }
        int min = Integer.MAX_VALUE;
        for (int tmp: map.values())
            min = min > tmp ? tmp : min;
        if (min <= 1)
            return false;
        int k = 2;
        int k1 = min / 2;
        List<Integer> factors = new ArrayList<>();
        while (k <= k1) {
            if (min % k == 0)
                factors.add(k);
            k++;
        }
        factors.add(min);   // 加上本身
        int size = factors.size();
        boolean flag;
        for (int i = 0; i < size; i++) {
            flag = true;
            int tmp = factors.get(i);
            for (int value : map.values()) {
                if (value % tmp != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                return true;
        }
        return false;
    }

    // 914
    public boolean hasGroupsSizeX1(int[] deck) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int d: deck) {
            map.put(d, map.getOrDefault(d, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int value: map.values())
            list.add(value);
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            int first = list.get(i);
            for (int j = i + 1; j < size; j++) {
                if (gcd(first, list.get(j)) == 1)
                    return false;
            }
        }
        return true;
    }

    // 915
    public int partitionDisjoint(int[] A) {
        List<Integer> orderList = new ArrayList<>();
        for (int a: A)
            orderList.add(a);
        Collections.sort(orderList);
        int res = 0, max = Integer.MIN_VALUE;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            list.add(A[i]);
            max = max > A[i] ? max : A[i];
            res++;
            orderList.remove((Integer)A[i]);        // 这个remove办法太慢了 O(n), n次就是 n ^ 2
            if (max <= orderList.get(0))
                return res;
        }
        return -1;
    }

    // 915
    public int partitionDisjoint1(int[] A) {
        if (A == null || A.length == 0)
            return 0;
        int leftMax = A[0], max = A[0], index = 0;
        for (int i = 1; i < A.length; i++) {
            max = Math.max(max, A[i]);
            if (A[i] < leftMax) {
                leftMax = max;
                index = i;
            }
        }
        return index + 1;
    }

    // 917          也可以用双指针, left , right
    public String reverseOnlyLetters(String S) {
        StringBuilder sb = new StringBuilder(S);
        List<Integer> list = new ArrayList<>();
        int size = sb.length();
        for (int i = 0; i < size; i++) {
            char c = sb.charAt(i);
            if (Character.isLetter(c))
                list.add(i);
        }
        size = list.size();
        for (int i = 0; i < size / 2; i++) {
            int first = list.get(i);
            int second = list.get(size - 1 - i);
            char tmp1 = sb.charAt(first);
            char tmp2 = sb.charAt(second);
            sb.setCharAt(first, tmp2);
            sb.setCharAt(second, tmp1);
        }
        return sb.toString();
    }

    // 922
    public int[] sortArrayByParityII(int[] A) {
        int[] res = new int[A.length];
        int odd = 0, even = 0;
        for (int i = 0; i < A.length; i++)
            if (A[i] % 2 == 0)
                res[even++ * 2] = A[i];
            else
                res[odd++ * 2 + 1] = A[i];
        return res;
    }

    // 921
    public int minAddToMakeValid(String S) {
        int left = 0, right = 0, res = 0;
        for (char c: S.toCharArray()) {
            if (c == '(') {
                if (right > left) {
                    res += right - left;
                    left = right = 0;
                }
                left++;
            }
            else {
                right++;
            }
            if (left == right)
                left = right = 0;
        }
        res += Math.abs(left - right);
        return res;
    }

    public boolean isPossibleDivide(int[] nums, int k) {
        int length = nums.length;
        if (length % k != 0)
            return false;
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        for (int num: nums) {
            list.add(num);
        }
        int current = 0;
        while (current != length) {
            int first = list.get(0);
            for (int i = 0; i < k; i++) {
                if (!list.remove((Integer)first))
                    return false;
                first++;
            }
            current += k;
        }
        return true;
    }

    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        Map<String, Integer> map = new HashMap<>();
        int length = s.length();
        for (int i = minSize; i <= maxSize; i++) {
            for (int j = 0; j <= length - i; j++) {
                String temp = s.substring(j, j + i);
                if (check(temp, maxLetters))
                    map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        int res = 0;
        for (int value: map.values())
            res = res > value ? res : value;
        return res;
    }

    public boolean check(String s, int k) {
        int[] test = new int[26];
        int res = 0;
        for (char c: s.toCharArray()) {
            test[c - 'a']++;
            if (test[c - 'a'] == 1)
                res++;
        }
        return res <= k;
    }

    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int res = 0;
        List<Integer> haveBoxes = new ArrayList<>();
        List<Integer> haveKeys = new ArrayList<>();
        for (int box: initialBoxes)
            haveBoxes.add(box);
        while (true) {
            int currentBox = -1;
            for (int box: haveBoxes) {
                if (status[box] == 1 || haveKeys.contains(box)) {
                    currentBox = box;
                    haveBoxes.remove((Integer)box);
                    break;
                }
            }
            if (currentBox == -1)
                break;      // no available box
            res += candies[currentBox];
            int[] keysInit = keys[currentBox];
            for (int i = 0; i < keysInit.length; i++)
                haveKeys.add(keysInit[i]);
            int[] boxesInit = containedBoxes[currentBox];
            for (int i = 0; i < boxesInit.length; i++)
                haveBoxes.add(boxesInit[i]);
        }
        return res;
    }

    public int threeSumMulti(int[] A, int target) {
        int res = 0;
        int mod = (int)Math.pow(10, 9) + 7;
        for (int i = 0; i < A.length - 2; i++) {
            boolean flag1 = false;
            int second = 0;
            for (int j = i + 1; j < A.length - 1; j++) {
                if (flag1 && A[j] == A[j - 1]) {
                    if (A[i] + A[j - 1] + A[j] == target)
                        second--;
                    res += second;
                    continue;
                }
                second = 0;
                flag1 = false;
                boolean flag = false;
                int tmp = -1;
                for (int k = j + 1; k < A.length; k++) {
                    if (flag && A[k] == tmp) {
                        second++;
                        continue;
                    }
                    if (A[i] + A[j] + A[k] == target) {
                        second++;
                        flag = true;
                        tmp = A[k];
                    }
                }
                res += second;
                flag1 = true;
            }
        }
        return res;
    }

    // 1189
    public int maxNumberOfBalloons(String text) {
        int[] nums = new int[26];
        for (char c: text.toCharArray())
            nums[c - 'a']++;
        nums[11] /= 2;
        nums[14] /= 2;
        int min = Math.min(nums[11], nums[14]);
        min = Math.min(min, nums[0]);
        min = Math.min(min, nums[1]);
        min = Math.min(min, nums[13]);
        return min;
    }

    // 1200
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - 1; i++) {
            int tmp = arr[i + 1] - arr[i];
            min = min < tmp ? min : tmp;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int tmp = arr[i + 1] - arr[i];
            if (tmp == min) {
                List<Integer> temp = new ArrayList<>();
                temp.add(arr[i]);
                temp.add(arr[i + 1]);
                res.add(new ArrayList<>(temp));
            }
        }
        return res;
    }

    // 1137
    public int tribonacci(int n) {
        int[] res = new int[38];
        res[0] = 0;
        res[1] = 1;
        res[2] = 1;
        res[3] = 2;
        for (int i = 4; i <= n; i++)
            res[i] = res[i - 1] + res[i - 2] + res[i - 3];
        return res[n];
    }

//    public int findBestValue(int[] arr, int target) {
//        Arrays.sort(arr);
//        int res = arr[0];
//        int sum = 0;
//        int rest = arr.length;
//        int littleDiff = Integer.MAX_VALUE;
//        int result = 0;
//        for (int i = 0; i < arr.length; i++) {
//            int diff = Math.abs(sum + rest * arr[i] - target);
//            sum += arr[i];
//            if (diff == 0)
//                return arr[i];
//            if (diff < littleDiff) {
//                littleDiff = diff;
//                res = arr[i];
//                result = i;
//            }
//            else
//                break;
//        }
//        if (result == 0)
//            return arr[result];
//        for (int i = arr[result]; i >= arr[result - 1]; i--) {
//            int diff = Math.abs(sum +)
//        }
//        return arr[arr.length - 1];
//    }

    public int findBestValue(int[] arr, int target) {
        Arrays.sort(arr);
        int res = arr[0];
        int sum = 0;
        int rest = arr.length;
        int littleDiff = Integer.MAX_VALUE;
        int begin = target / arr.length;
        for (int i = begin; i <= arr[arr.length - 1]; i++) {
            sum = 0;
            rest = arr.length;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] >= i) {
                    sum += rest * i;
                    break;
                }
                rest--;
                sum += arr[j];
            }
            int diff = Math.abs(sum - target);
            if (diff == 0)
                return i;
            if (diff < littleDiff) {
                littleDiff = diff;
                res = i;
            }
            else if (diff > littleDiff) {
                return res;
            }
        }
        return res;
    }

    public int deepestLeavesSum(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        deepestLeavesSumHelper(root, 0, map);
        int res = 0, max = -1;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int key = entry.getKey();
            if (key < max)
                continue;
            res = entry.getValue();
        }
        return res;
    }

    public void deepestLeavesSumHelper(TreeNode root, int current, Map<Integer, Integer> map) {
        if (root == null)
            return;
        map.put(current, map.getOrDefault(current, 0) + root.val);
        deepestLeavesSumHelper(root.left, current + 1, map);
        deepestLeavesSumHelper(root.right, current + 1, map);
    }

    int max = 0;
    int ways = 0;
    public int[] pathsWithMaxScore(List<String> board) {
        int score = 0;
        pathsWithMaxScoreHelper(board, 0, 0, 0);
        return new int[] {max, ways};
    }

    public void pathsWithMaxScoreHelper(List<String> board, int i, int j, int score) {
        if (score < 0 || i >= board.size() || j >= board.size())
            return;
        char c = board.get(i).charAt(j);
        if (c == 'X')
            return;
        if (c == 'S') {
            if (max == score) {
                ways++;
                return;
            }
            if (max > score)
                return;
            max = score;
            ways = 1;
        }
        boolean flag = false;
        if (c != 'E') {
            flag = true;
            score += Integer.valueOf(c) - '0';
        }
        pathsWithMaxScoreHelper(board, i + 1, j, score);
        pathsWithMaxScoreHelper(board, i, j + 1, score);
    }

    public int[] sumZero(int n) {
        int[] res = new int[n];
        if (n == 1) {
            res[0] = 0;
            return res;
        }
        if (n == 2) {
            res[0] = -1;
            res[1] = 1;
        }
        int  cur = 1;
        for (int i = 0; i < n / 2; i++) {
            res[i] = cur;
            res[i + 1] = -cur;
            cur++;
        }
        if (n % 2 != 0)
            res[n - 1] = 0;
        return res;
    }

    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        while (true) {
            int left = start + arr[start];
            int right = start - arr[start];
            if (check(left, arr.length) && !visited[left]) {
                if (arr[left] == 0)
                    return true;
                visited[left] = true;
                start = left;
                continue;
            }
            if (check(right, arr.length) && !visited[right]) {
                if (arr[right] == 0)
                    return true;
                visited[right] = true;
                start = right;
                continue;
            }
            return false;
        }
    }

    public boolean check (int x, int length) {
        return x >= 0 && x < length;
    }

    public boolean isBadVersion(int n, int k) {
        return n >= k;
    }

    // 278
    public int firstBadVersion(int n, int k) {
        int left = 1, right = n;
        while (left <= right) {
            long mid = ((long)left + right) / 2;        // 要用long, 否则可能溢出
            if (!isBadVersion((int)mid, k))
                left = (int)mid + 1;
            else
                right = (int)mid - 1;
        }
        return right + 1;
    }

    // 299
    public String getHint(String secret, String guess) {
        int[] map1 = new int[10];
        int[] map2 = new int[10];
        int length = secret.length();
        for (char c: secret.toCharArray())
            map1[c - '0']++;
        for (char c: guess.toCharArray())
            map2[c - '0']++;
        int a = 0;
        for (int i = 0; i < length; i++) {
            char c1 = secret.charAt(i);
            char c2 = guess.charAt(i);
            if (c1 == c2)
                a++;
        }
        int b = 0;
        for (int i = 0; i < 10; i++)
            b += Math.min(map1[i], map2[i]);
        b -= a;
        String res = a + "A" + b + "B";
        return res;
    }

    public int guessNumber(int n, int k) {
        int left = 1, right = n, tmp = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int r = guess(mid, k);
            if (r == 0)
                return mid;
            if (r == 1)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return 0;       // not exist
    }

    public int guess(int res, int k) {
        if (res == k)
            return 0;
        if (res > k)
            return -1;
        return 1;
    }

    // 405
    // 【笔记】核心思想，使用位运算，每4位，对应1位16进制数字。
    // 使用0xf(00...01111b)获取num的低4位。
    // >>算数位移，其中正数右移左边补0，负数右移左边补1。
    // 位移运算并不能保证num==0，需要使用32位int保证（对应16进制小于等于8位）。
    public String toHex(int num) {
        if (num == 0)
            return "0";
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 8 && num != 0) {
            sb.append(chars[num & 0xf]);
            num >>= 4;
        }
        return sb.reverse().toString();
    }

    public int[] xorQueries(int[] arr, int[][] queries) {
        int[][] res = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (i == j) {
                    res[i][j] = arr[i];
                    continue;
                }
                res[i][j] = res[i][j - 1] ^ arr[j];
            }
        }
        int[] des = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int j = queries[i][0];
            int k = queries[i][1];
            des[i] = res[j][k];
        }
        return des;
    }

    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        List<String> res = new ArrayList<>();
        int n = watchedVideos.size();
        boolean[] visited = new boolean[n];
        visited[id] = true;
        List<Integer> current = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        while (level > 0) {
            current.clear();
            for (int j = 0; j < ids.size(); j++) {
                int tmp = ids.get(j);
                for (int i = 0; i < friends[tmp].length; i++) {
                    if (!current.contains(friends[tmp][i]) && !visited[friends[tmp][i]]) {
                        current.add(friends[tmp][i]);
                        visited[friends[tmp][i]] = true;
                    }
                }
            }
            ids.clear();
            for (int k = 0; k < current.size(); k++)
                ids.add(current.get(k));
            level--;
        }
        Map<String, Integer> map = new HashMap<>();
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            int tmp = ids.get(i);
            List<String> videos = watchedVideos.get(tmp);
            for (int j = 0; j < videos.size(); j++)
                map.put(videos.get(j), map.getOrDefault(videos.get(j), 0) + 1);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int tmp = o1.getValue() - o2.getValue();
                if (tmp != 0)
                    return tmp;
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        int size1 = list.size();
        for (int q = 0; q < size1; q++)
            res.add(list.get(q).getKey());
        return res;
    }

    public int longestPalindrome22(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);
        boolean flag = false;
        int res = 0;
        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            int i = entry.getValue();
            if (map.size() == 1)
                return i;
            if (i % 2 == 0)
                res += i;
            else if (i % 2 != 0) {
                if (!flag)
                    res += i;
                else
                    res += (i - 1);
                flag = true;
            }
        }
        return res;
    }

    // 434
    public int countSegments(String s) {
        int res = 0;
        boolean flag = false;
        for (char c: s.toCharArray()) {
            boolean f = Character.isWhitespace(c);
            if (f)
                flag = false;
            else {
                if (!flag) {
                    flag = true;
                    res++;
                }
            }
        }
        return res;
    }

    // 459
    public boolean repeatedSubstringPattern(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);
        boolean flag = false;
        boolean even = false;
        for (int value: map.values()) {
            boolean current = value % 2 == 0;
            if (!flag) {
                even = current;
                flag = true;
            }
            if (even != current)
                return false;
        }
        return true;
    }

    class Frequency {
        public int num;
        public int freq;
        public Frequency(int _num, int _freq) {
            num = _num;
            freq = _freq;
        }
    }

    public int[] rearrangeBarcodes(int[] barcodes) {
        int[] res = new int[barcodes.length];
        Map<Integer, Integer> map = new HashMap<>();
        for (int barcode: barcodes)
            map.put(barcode, map.getOrDefault(barcode, 0) + 1);
        PriorityQueue queue = new PriorityQueue(10000, new Comparator<Frequency>() {
            @Override
            public int compare(Frequency f1, Frequency f2) {
                return f1.freq - f2.freq;
            }
        });
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            Frequency f = new Frequency(entry.getKey(), entry.getValue());
            queue.add(f);
        }
        int i = 0;
        while (i < barcodes.length) {
            Frequency f = (Frequency)queue.peek();
            res[i++] = f.num;
            f.freq--;
        }
        return res;
    }

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

    public double angleClock(int hour, int minutes) {
        double tmp = minutes * 0.5;
        double hour1 = (hour * 30 + tmp) % 360;
        double minute1 = minutes * 6;
        double res = Math.abs(hour1 - minute1);
        return res >= 180 ? 360 - res : res;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] s1 = new int[26];
        int[] s2 = new int[26];
        int left = 0, right = p.length(), length = s.length();
        if (length < p.length())
            return res;
        for (int i = 0; i < right; i++) {
            s1[s.charAt(i) - 'a']++;
            s2[p.charAt(i) - 'a']++;
        }
        while (right <= length) {
            if (check(s1, s2))
                res.add(left);
            s1[s.charAt(left++) - 'a']--;
            if (right + 1 > length)
                break;
            s1[s.charAt(right++) - 'a']++;
        }
        return res;
    }

    public boolean check(int[] s1, int[] s2) {
        for (int i = 0; i < 26; i++)
            if (s1[i] != s2[i])
                return false;
        return true;
    }

    public boolean judgeSquareSum1(int c) {
        Set<Integer> set = new HashSet<>();
        int temp = (int)Math.floor(Math.sqrt(c));
        for (int i = 0; i <= temp; i++)
            set.add(i * i);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (set.contains(c - (int)it.next()))
                return true;
        }
        return false;
    }

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null)
            return false;
        if (B.left == null && B.right == null && B.val == A.val)
            return true;
        boolean flag1 = false, flag2 = false;
        if (A.val == B.val)
            flag1 = isSubStructure(A.left, B.left) || isSubStructure(A.right, B.right);
        flag2 = isSubStructure(A.left, B) || isSubStructure(A.right, B);
        return flag1 || flag2;
    }

    public int trap(int[] height) {
        if (height.length == 0)
            return 0;
        int index = 0, max = height[0];
        for (int i = 1; i < height.length; i++) {
            if (height[i] > max) {
                max = height[i];
                index = i;
            }
        }
        int res = 0;
        max = height[0];    // 左边最大
        for (int i = 1; i < index; i++) {
            max = max >= height[i] ? max : height[i];
            res += max - height[i];
        }
        max = height[height.length - 1];    // 右边最大
        for (int i = height.length - 2; i > index; i--) {
            max = max >= height[i] ? max : height[i];
            res += max - height[i];
        }
        return res;
    }

    public int trap1(int[] height) {
        if (height.length == 0)
            return 0;
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(height[0]);
        int res = 0;
        for (int h: height) {
            while (!stack.isEmpty()) {
                int top = stack.peek();
                if (h == top)
                    break;
                else if (h > top) {
                    res += h - top;
                    stack.pop();
                }
                else if (h < top)
                    stack.push(h);
                if (stack.isEmpty()) {
                    stack.push(h);  // 当前成为栈顶
                    break;
                }
            }
        }
        return res;
    }

    //"WWEQ ERQW QWWR WWER QWEQ"        cabwefgewcwaefgcf   cae
    public static void main(String[] args) {
        Test test = new Test();

        System.out.println(test.trap1(new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(0);
        TreeNode t3 = new TreeNode(1);
        TreeNode t4 = new TreeNode(-4);
        TreeNode t5 = new TreeNode(-3);

        TreeNode t6 = new TreeNode(1);
        TreeNode t7 = new TreeNode(-4);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t6.left = t7;
        System.out.println(test.isSubStructure(t1, t6));

        System.out.println(Integer.MAX_VALUE);
        long start = System.currentTimeMillis();
        System.out.println(test.judgeSquareSum1(999999999));
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(test.findAnagrams("abab", "ab"));
        System.out.println(test.angleClock(1, 57));
//        System.out.println(test.repeatedSubstringPattern("aba"));
//        System.out.println(test.longestPalindrome22("abccccdd"));
//        List<List<String>> list1 = new ArrayList<>();
//        list1.add(new ArrayList<>(Arrays.asList(new String[] {"A", "B"})));
//        list1.add(new ArrayList<>(Arrays.asList(new String[] {"C"})));
//        list1.add(new ArrayList<>(Arrays.asList(new String[] {"B", "C"})));
//        list1.add(new ArrayList<>(Arrays.asList(new String[] {"D"})));
//        System.out.println(test.watchedVideosByFriends(
//                list1, new int[][] {{1,2}, {0,3}, {0,3}, {1,2}}, 0 ,2));
//        System.out.println(test.xorQueries(new int[] {15,8,8,8,15}, new int[][] {{2,2},{3,3}}));
//        System.out.println(test.toHex(26));
//        System.out.println(test.guessNumber(2, 2));
//        System.out.println(test.firstBadVersion(2126753390, 1702766719));
//        System.out.println(test.canReach(new int[] {4,2,3,0,3,1,2}, 5));
//        test.sumZero(5);
//        char c = '5';
//        System.out.println(Integer.valueOf(c) - '0');
//        List<String> board = new ArrayList<>();
//        board.add("E23");
//        board.add("2X2");
//        board.add("12S");
//        int[] res = new int[2];
//        res = test.pathsWithMaxScore(board);
//        System.out.println(res[0] + " , " + res[1]);
//        System.out.println(test.findBestValue(new int[]{2,3,5}, 10));

    }

    public void someMainTestCode() {
                /*
        //        System.out.println(test.maxFreq("abcde", 2, 3, 3));
//        System.out.println(test.reverseOnlyLetters("ab-cd"));
//        String S = "aerwefsd";
//        StringBuilder sb = new StringBuilder(S);
//        sb.reverse();
//        System.out.println(sb);
//        System.out.println(test.partitionDisjoint1(new int[] {123,23,34,123,234}));
//        Integer[] temp = new Integer[] {6,5,4,1,2,1,1,2,3,7,8};
//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(temp));
//        System.out.println(list);
//        list.remove((Integer)1);
//        System.out.println(list);
//        System.out.println(test.hasGroupsSizeX1(new int[] {1,1,1,2,2,2,3,3}));
////        System.out.println(test.reverseVowels("leetcode"));
////        System.out.println(test.isPerfectSquare(1165536));
////        System.out.println(test.canConstruct("a", "b"));
////        System.out.println(test.canConstruct("aa", "ab"));
////        System.out.println(test.canConstruct("aa", "aab"));
//        System.out.println(test.wordPattern("ab", "dog dog"));
//        System.out.println(test.shortestPath(new int[][] {{0,0,0},{1,1,0},{0,0,0},{0,1,1},{0,0,0}}, 1));
////        System.out.println(test.maxSideLength(
////                new int[][] {{18,70},{61,1},{25,85},{14,40},{11,96},{97,96},{63,45}}, 123243));
////        System.out.println((1 + 3) / 2);
//        System.out.println(test.maxSideLength(new int[][] {{1,1,3,2,4,3,2},{1,1,3,2,4,3,2},{1,1,3,2,4,3,2}}, 4));
//        System.out.println(test.sequentialDigits(100, 300));
//        System.out.println(2 << 0);
//        System.out.println(test.minFallingPathSum(new int[][] {{1,2,3},{4,5,6},{7,8,9}}));
//        System.out.println(test.sumSubseqWidths(new int[] {
//                123,546,768,2,54,234,657,23,54,67,34,123,567,34,23,546,254,6766,324,54654,324,12423,345345,234,345,
//                2134,345,546,678,879,546,324,65}));
//        test.fairCandySwap(new int[] {2}, new int[] {1, 3});
////        System.out.println(test.possibleBipartition(50,
////                new int[][]{{21,47},{4,41},{2,41},{36,42},{32,45},{26,28},{32,44},{5,41},{29,44},{10,46},{1,6},{7,42},{46,49},{17,46},{32,35},{11,48},{37,48},{37,43},{8,41},{16,22},{41,43},{11,27},{22,44},{22,28},{18,37},{5,11},{18,46},{22,48},{1,17},{2,32},{21,37},{7,22},{23,41},{30,39},{6,41},{10,22},{36,41},{22,25},{1,12},{2,11},{45,46},{2,22},{1,38},{47,50},{11,15},{2,37},{1,43},{30,45},{4,32},{28,37},{1,21},{23,37},{5,37},{29,40},{6,42},{3,11},{40,42},{26,49},{41,50},{13,41},{20,47},{15,26},{47,49},{5,30},{4,42},{10,30},{6,29},{20,42},{4,37},{28,42},{1,16},{8,32},{16,29},{31,47},{15,47},{1,5},{7,37},{14,47},{30,48},{1,10},{26,43},{15,46},{42,45},{18,42},{25,42},{38,41},{32,39},{6,30},{29,33},{34,37},{26,38},{3,22},{18,47},{42,48},{22,49},{26,34},{22,36},{29,36},{11,25},{41,44},{6,46},{13,22},{11,16},{10,37},{42,43},{12,32},{1,48},{26,40},{22,50},{17,26},{4,22},{11,14},{26,39},{7,11},{23,26},{1,20},{32,33},{30,33},{1,25},{2,30},{2,46},{26,45},{47,48},{5,29},{3,37},{22,34},{20,22},{9,47},{1,4},{36,46},{30,49},{1,9},{3,26},{25,41},{14,29},{1,35},{23,42},{21,32},{24,46},{3,32},{9,42},{33,37},{7,30},{29,45},{27,30},{1,7},{33,42},{17,47},{12,47},{19,41},{3,42},{24,26},{20,29},{11,23},{22,40},{9,37},{31,32},{23,46},{11,38},{27,29},{17,37},{23,30},{14,42},{28,30},{29,31},{1,8},{1,36},{42,50},{21,41},{11,18},{39,41},{32,34},{6,37},{30,38},{21,46},{16,37},{22,24},{17,32},{23,29},{3,30},{8,30},{41,48},{1,39},{8,47},{30,44},{9,46},{22,45},{7,26},{35,42},{1,27},{17,30},{20,46},{18,29},{3,29},{4,30},{3,46}}));
////        test.spiralMatrixIII(1, 4, 0, 0);
//        test.uncommonFromSentences("asd","ascx");
//        System.out.println(Long.MAX_VALUE);
//        System.out.println(test.decodeAtIndex("leet2code3", 10));
//        System.out.println(test.numRescueBoats(new int[] {34,65,67,12,45,76,34,12,34,54,
//                65,1,4,6,8,87,45,99,65,34,65,23,51,28,97,54,22,14,68,90,8,65,24,26}, 100));
//        System.out.println(test.profitableSchemes(12322, 3, new int[]{2, 2},
//                new int[] {2, 3}));
//        System.out.println(test.nthMagicalNumber(1000000000, 40000, 40000));
//        System.out.println(test.stoneGame(new int[] {5, 3, 4, 5}));
//        System.out.println(test.subtractProductAndSum(234));
//        int[] ints = test.advantageCount(new int[] {2, 7, 11, 15}, new int[] {1, 10, 4, 11});
//        for (int i: ints)
//            System.out.print(i + " , ");
//        System.out.println();
//        System.out.println(test.reorderedPowerOf2(46));
//        System.out.println(test.matrixScore(new int[][] {{0,0,1,1},{1,0,1,0},{1,1,0,0}}));
//        System.out.println(test.mirrorReflection(60, 24));
//        System.out.println(test.scoreOfParentheses("(()(()))"));
//        TreeNode t1 = new TreeNode(1);
//        TreeNode t2 = new TreeNode(2);
//        TreeNode t3 = new TreeNode(3);
//        TreeNode t4 = new TreeNode(4);
//        TreeNode t5 = new TreeNode(5);
//        TreeNode t6 = new TreeNode(6);
//        TreeNode t7 = new TreeNode(7);
//        TreeNode t8 = new TreeNode(8);
//        t1.left = t2;
//        t1.right = t3;
//        t2.left = t4;
//        t2.right = t5;
//        t3.left = t6;
//        t3.right = t7;
//        t6.right = t8;
//        test.delNodes(t1, new int[] {3, 5, 8});
        test.corpFlightBookings1(new int[][] {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}},5);
        String s123 = "abx.ca.bcs";
        s123 = s123.replaceAll("\\.", "qqq");
        System.out.println(s123);
        System.out.println(test.numWays(430, 148488));
        ArrayList<Integer> list22 = new ArrayList<>(Arrays.asList(4, 6, 8, 2, 7));
        for (Integer integer: list22)
            System.out.println(integer);
        System.out.println(test.tictactoe(new int[][] {{1,2},{2,1},{1,0},{0,0},{0,1},{2,0},{1,1}}));
        System.out.println(test.removeInterval(new int[][] {{-5, -4}, {-3, -2}, {1, 2}, {3, 5}, {8, 9}},
                new int[] {-1, 4}));
        List<Integer> indexs[] = new ArrayList[3];
        List<List<Integer>> index1 = new ArrayList<>();
        indexs[0] = new ArrayList<>();
        indexs[1] = new ArrayList<>();
        for (List<Integer> index: indexs)
            System.out.println(index);      // [], [], null
        index1.add(new ArrayList<>());
        index1.add(new ArrayList<>());
        System.out.println(index1);         // [[], []]
        System.out.println(test.removeComments(strings));
        System.out.println(test.maxSumDivThree(new int[] {3, 6, 5, 1, 8}));
        int[] asd1 = {2,5,87};
        int []asd2 = {5,7,9};
        asd2 = asd1;
        for (int i11: asd2)
            System.out.print(i11 + ", ");
        char c = '*';
        System.out.println((int)c);
        System.out.println(test.largeGroupPositions("abcdddeeeeaabbbcd"));
        System.out.println(test.sumEvenAfterQueries(new int[] {1, 2, 3, 4}, new int[][] {
                {1, 0}, {-3, 1}, {-4, 0}, {2, 3}
        }));
        System.out.println(test.judgeSquareSum(Integer.MAX_VALUE));
        System.out.println(test.isLongPressedName("kikcxmvzi", "kiikcxxmmvvzz"));
        System.out.println(test.canCompleteCircuit(new int[] {3, 3, 4}, new int[] {3, 4, 4}));
        System.out.println(test.reconstructQueue(new int[][] {{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}}));
        System.out.println(test.computeArea(-2, -2, 2, 2, -3, -3, 3, -1));
        System.out.println(test.closedIsland(new int[][] {{1}}));
        System.out.println(test.minRemoveToMakeValid("))(("));
        StringBuilder sb1 = new StringBuilder("safqweffdbfd");
        sb1.deleteCharAt(3);
        String temp = "1s3 456";
        System.out.println(test.shortestCompletingWord(temp, new String[] {"looks", "pest", "stew", "show"}));
        temp = temp.toLowerCase();
        System.out.println(temp);
        System.out.println(test.oddCells(2, 3, new int[][] {{0, 1}, {1, 1}}));
        System.out.println(test.combinationSum3(3, 7));
        StringBuilder sb = new StringBuilder();

        System.out.println(test.isIsomorphic1("aba", "cde"));
        String str = "abcabbcaa";
        char[] ch1 = str.toCharArray();
        for (int i = 0; i < str.length(); i++)
            System.out.println(str.indexOf(ch1[i]));
        test.rotate(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 6);
        int[] dp = IntStream.range(-1, 10).toArray();
        for (int i: dp)
            System.out.print(i + ", ");
        System.out.println(test.partition3("aab"));
        System.out.println(test.findOrder(4, new int[][] {{1,0}, {2,0}, {3,1}, {3,2}}));
        int[] tem =
                new int[] {6,6,6,6,6,6,6};
        Arrays.sort(tem);
        for (int i = 0; i < tem.length; i++)
            System.out.print(tem[i] + ", ");
        System.out.println();
        System.out.println(test.hIndex(tem));

        System.out.println(true || false && false || false);  // ==> t || f || f = t
        System.out.println((true || false) && (false || false));    // ==> t && f = f
        //int[][] res = test.gameOfLife(new int[][] {{0,1,0},{0,0,1},{1,1,1},{0,0,0}});
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(test.isAdditiveNumber("0235813"));
        System.out.println(test.removeDuplicateLetters("bcabc"));
//        System.out.println(test.preimageSizeFZF1(153));
//        System.out.println(test.numberOfSubarrays(new int[] {1, 1, 2, 1, 1}, 3));
//        System.out.println(test.minimumSwap("xx", "yy"));
//        System.out.println(test.transformArray(new int[] {1, 6, 3, 4, 3, 5}));
        int MOD = 1_000_000_007;
        System.out.println(MOD);
        char[][] board = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        test.solveSudoku(board);
        System.out.println(6 / 3 * 3);
        System.out.println(test.findMin(new int[] {}));

        System.out.println(test.restoreIpAddresses("2736786374048"));
        //String str = "aaa";
        //str = String.format("%-5s", str);
        //System.out.println(str);
        System.out.println(test.minWindow("ab", "a"));
        TreeNode t1 = new TreeNode(3);
        TreeNode t2 = new TreeNode(9);
        TreeNode t3 = new TreeNode(20);
        TreeNode t4 = new TreeNode(15);
        TreeNode t5 = new TreeNode(7);
        TreeNode t6 = new TreeNode(4);
        TreeNode t7 = new TreeNode(4);
        t1.left = t2;
        t1.right = t3;
        t3.left = t4;
        t3.right = t5;
        t4.left = t6;
        t4.right = t7;
        System.out.println(test.isBalanced(t1));
        TreeNode res = test.convertBST(t1);
        System.out.println(test.findUnsortedSubarray(new int[] {1,3,2,2,2}));

        System.out.println(test.dayOfTheWeek(1, 1, 1971));
        List<Character> list = new ArrayList<>();
        list.add('A');
        list.add('A');
        list.add('B');
        list.add('B');
        list.add('c');
        list.remove((Character)'A');
        System.out.println(list);
        list.remove((Character)'B');
        System.out.println(list);
        list.remove((Character)'A');
        System.out.println(list);

        List<List<String>> res = test.solveNQueens(1);
        System.out.println(res.size());
        for (List<String> list: res)
            System.out.println(list);
        System.out.println(test.longestCommonSubsequence("cdabbqwe", "acde"));
        long start = System.currentTimeMillis();
        System.out.println(test.findSubsequences(new int[] {5,5,6}));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        ,3,-4,4,7,7,-3,-3
        int[] res = test.dailyTemperatures(new int[] {89,62,70,58,47,47,46,76,100,70});
        for (int r: res)
            System.out.print(r + ", ");
        System.out.println(test.firstUniqChar("asfjdasfkjdsqwoefwed"));
//        System.out.println(test.longestConsecutive(new int[] {9,1,4,7,3,-1,0,5,8,-1,6}));
////        TreeNode node1 = test.makeTreeNode(5);
////        TreeNode node2 = test.makeTreeNode(4);
////        TreeNode node3 = test.makeTreeNode(8);
////        TreeNode node4 = test.makeTreeNode(11);
////        TreeNode node5 = test.makeTreeNode(13);
////        TreeNode node6 = test.makeTreeNode(4);
////        TreeNode node7 = test.makeTreeNode(7);
////        TreeNode node8 = test.makeTreeNode(2);
////        TreeNode node9 = test.makeTreeNode(5);
////        TreeNode node10 = test.makeTreeNode(1);
////        node1.left = node2;
////        node1.right = node3;
////        node2.left = node4;
////        node3.left = node5;
////        node3.right = node6;
////        node4.left = node7;
////        node4.right = node8;
////        node6.left = node9;
////        node6.right = node10;
////        List<List<Integer>> result = test.pathSum(node1, 22);
////        for (List<Integer> list: result)
////            System.out.println(list);
////        test.isPalindrome("A man, a plan, a canal: Panama");
////        TreeNode root = test.makeTreeNode(1);
////        root.left = test.makeTreeNode(2);
////        root.right = test.makeTreeNode(0);
////        System.out.println(test.hasPathSum(root, 1));
////        System.out.println(test.largestNumber(new int[] {0,0,0,0}));
////        System.out.println(test.largestRectangleArea1(new int[] {0,0,0,0,0,0,0,0,2147483647}));
////        System.out.println(null == null);   // true
////        char a = '4', b = '3';
////        System.out.println(a > b);
//
////        System.out.println(test.numDecodings("20"));
        ListNode l1 = test.makeNode(1);
        ListNode l2 = test.makeNode(2);
        ListNode l3 = test.makeNode(3);
        ListNode l4 = test.makeNode(-3);
        ListNode l5 = test.makeNode(4);
        ListNode l6 = test.makeNode(3);
        ListNode l7 = test.makeNode(7);
        ListNode l8 = test.makeNode(8);
//        ListNode l4 = test.makeNode(2);
//        ListNode l5 = test.makeNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        test.removeZeroSumSublists(l1);
//        l5.next = l6;
//        l6.next = l7;
//        l7.next = l8;
//        int[] ints = test.nextLargerNodes1(l1);
//        for (int i: ints)
//            System.out.print(i + ", ");
//         l3.next = l4;
//        l4.next = l5;
//        test.removeElements1(l1, 2);
//        System.out.println(l1 + " , " + l1.next);
//        l2.next = l3;
//        test.reverseList11(l1);
//        System.out.println(test.jump(new int[] {3,4,2,4,5,4,3,2,4,7,6,4,3,2,4,2}));
////        System.out.println(test.dieSimulator(2, new int[] {1, 1, 2, 2, 2, 3}));
////        System.out.println(test.balancedStringSplit("RLLLRLLRRR"));
////        ListNode l1 = test.makeNode(1);
////        ListNode l2 = test.makeNode(1);
////        ListNode l3 = test.makeNode(2);
////        l1.next = l2;
////        l2.next = l3;
////        test.deleteDuplicates(l1);
////        for (int i = 1; i < 100; i++) {
////            double temp = Math.pow(4, i);
////            if (temp > Integer.MAX_VALUE) {
////                System.out.println(i);
////                break;
////            }
////        }
////        test.containsNearbyDuplicate(new int[] {1, 2, 3, 1, 2, 3}, 61);
////        ListNode l1 = test.makeNode(1);
////        ListNode l2 = test.makeNode(2);
////        ListNode l3 = test.makeNode(3);
////        l1.next = l2;
////        l2.next = l3;
////        System.out.println(test.reverseList1(l1));
////        int i = -234;
////        i = Math.abs(i);
////        System.out.println(i);
////        StringBuilder sb = new StringBuilder("wefsdf");
////        sb.reverse();
////        System.out.println(sb);
////        System.out.println(test.majorityElement(new int[] {3, 3, 4}));
//////        ListNode l1 = test.makeNode(4);
//////        ListNode l2 = test.makeNode(1);
//////        ListNode l3 = test.makeNode(8);
//////        ListNode l4 = test.makeNode(4);
//////        ListNode l5 = test.makeNode(5);
//////        l1.next = l2;
//////        l2.next = l3;
//////        l3.next = l4;
//////        l4.next = l5;
//////
//////        ListNode l21 = test.makeNode(5);
//////        ListNode l22 = test.makeNode(0);
//////        ListNode l23 = test.makeNode(1);
//////        l21.next = l22;
//////        l22.next = l23;
//////        l23.next = l3;
//////
//////        ListNode res = test.getIntersectionNode(l1, l21);
//////        System.out.println(res.val);
////
////
//////        test.reverseString(new char[]{});
//////        System.out.println(test.reverseStr("avcdefg", 2));
//////        System.out.println(test.isMatch("b", "ab*b"));
//////        List<Integer> list = test.countSteppingNumbers(0, 0);
//////        for (int i = 0; i < list.size(); i++) {
//////            System.out.print(list.get(i) + " ");
//////            if (i % 25 == 0)
//////                System.out.println();
//////        }
//////        System.out.println();
//////        System.out.println(2 * Math.pow(10, 8));
//////         System.out.println(Integer.MAX_VALUE);
////
//////            int i = longestValidParentheses(")()()(((((()");
//////            System.out.println(i);
////
//////            int k = search(new int[] {15,16,19,20,25,1,3,4,5,7,10,14}, 25);
//////            System.out.print(k);
//////        String str = "aacdefcaaywwkew";    //aacdefcaaywwkew
//////        String result = longestPalindrome(str);
//////        System.out.println(result);
////
//////        String str = "LEETCODEISHIRING";
//////        String result = convert(str, 4);
//////        System.out.println(result);
////
//////        String s = "-23523";
//////        int i = Integer.valueOf(s);
//////        System.out.println(i);
////
//////        boolean f = isPalindrome(100021);
//////        System.out.println(f);
//////        int i = 3;
//////
//////        String str = "sdfdf";
//////        char s = str.charAt(546);
////
//////        int i = romanToInt("MDLXX");
//////        System.out.println(i);
////
//////        int[] ints = {2, 5, 3, 1, 8, 9, 6, 5};
//////        Arrays.sort(ints);
//////        List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
//////        ArrayList<Integer> list1 = new ArrayList<>(list);
//////        System.out.println(list);
////
//////        List<List<Integer>> lists = threeSum(new int[]{-2, 0, 1, 1, 2});
//////        System.out.println(lists);
//////        List<String> stringList = letterCombinations("");
//////        for (String str : stringList)
//////            System.out.println(str);
////
//////        List<List<Integer>> stringList = fourSum(new int[]{1,-2,-5,-4,-3,3,3,5}, -11);
//////        for (List<Integer> integers : stringList)
//////            System.out.println(integers);
//////            ListNode node = new ListNode(1);
//////            node.next = new ListNode(2);
//////            node.next.next = new ListNode(3);
//////            node.next.next.next = new ListNode(4);
//////            node.next.next.next.next = new ListNode(5);
//////            ListNode node1 = removeNthFromEnd(node, 2);
//////
//////            while (node1 != null) {
//////                System.out.print(node.val + " , ");
//////                node = node.next;
//////            }
//////        boolean flag = isValid("{[]}");
//////        System.out.println(flag);
////
//////        String str = "wessdc";
//////        String sbu = str.substring(6, 6);
//////        System.out.println(sbu);
//////        List<String> list = generateParenthesis(4);
//////        for (String str : list)
//////            System.out.println(str);
//////        ListNode node = new ListNode(1);
//////            node.next = new ListNode(2);
//////            node.next.next = new ListNode(3);
//////            node.next.next.next = new ListNode(4);
//////            node.next.next.next.next = new ListNode(5);
//////        node.next.next.next.next.next = new ListNode(6);
//////        node.next.next.next.next.next.next = new ListNode(7);
//////
//////
//////        ListNode res = swapPairs(node);
//////        while (res != null) {
//////            System.out.println(res.val);
//////            res = res.next;
//////        }
//////        int i = removeElement(new int[]{0,1,2,2,3,0,4,2}, 2);
//////        System.out.println(i);
//////        int as = divide(-2147483648, -2147483648);
//////        System.out.println(as);
////
//////        int[] ints = nextPermutation(new int[] {1, 2, 3, 5, 4, 3, 2, 1});
//////        for (int i = 0; i < ints.length; i++)
//////            System.out.print(ints[i] + " , ");
////
//////        int[] ints = new int[]{7,6,5,5,2};
//////        for (int i = 0; i < ints.length / 2; i++) {
//////            int temp = ints[i];
//////            ints[i] = ints[ints.length - 1 - i];
//////            ints[ints.length - 1 - i] = temp;
//////        }
//////        for (int i : ints)
//////            System.out.print(i + " , ");
//////        int[] ints = searchRange(new int[]{}, 0);
//////        System.out.println(ints[0] + " , " + ints[1]);
//////        String str = countAndSay(8);
//////        System.out.println(str);
//////        combinationSum(new int[]{2, 3, 5}, 8);
//////        for (List<Integer> list : listList) {
//////            for (Integer integer : list) {
//////                System.out.print(integer + " , ");
//////            }
//////            System.out.println();
//////        }
////            // 681692778  351251360
////            // 2147483647
////            //System.out.println(Integer.MAX_VALUE);
////
////            //957747794
////            //424238336
//////        int k = findKthNumber(957747794, 424238336);
//////        System.out.println(k);
//////        int[][] items = {{1,91},{1,92},{2,93},{2,97},{1,60},{2,77},{1,65},{1,87},{1,100},{2,100},{2,76}};
//////
//////        int[][] result = highFive(items);
//////
//////        System.out.println(result);
//////        int[] ints = {2, 1, 2, 4, 3};
//////        ArrayList<Integer> list = nextGreaterElement(ints);
//////        Collections.reverse(list);
//////        System.out.println(list);
//////        int[] ints = {1, 4, 3, 6, 8, 22, 13, 17, 16};
//////        int[] ints1 = new int[ints.length * 2];
//////        System.arraycopy(ints, 0, ints1, 0, ints.length);
//////        System.arraycopy(ints, 0, ints1, ints.length, ints.length);
//////        for (int i : ints1)
//////            System.out.print(i + ", ");
////
//////        int[] result = nextGreaterElements(ints);
//////        for (int i : result)
//////            System.out.print(i + ", ");
//////        int i = nextGreaterElement(23792563);
//////        System.out.print(i);
//////        int i = maxProfit(new int[]{4, 5, 2, 4, 3, 3, 1, 2, 5}, 1);
//////        System.out.print(i);
//////        Node node1 = new Node(1, null, null, null);
//////        Node node2 = new Node(2, null, null, null);
//////        Node node3 = new Node(3, null, null, null);
//////        Node node4 = new Node(4, null, null, null);
//////        Node node5 = new Node(5, null, null, null);
//////        Node node6 = new Node(6, null, null, null);
//////        Node node7 = new Node(7, null, null, null);
//////        Node node8 = new Node(8, null, null, null);
//////        Node node9 = new Node(9, null, null, null);
//////        Node node10 = new Node(10, null, null, null);
//////        Node node11 = new Node(11, null, null, null);
//////        Node node12 = new Node(12, null, null, null);
//////        node1.next = node2;
//////        node2.prev = node1;
//////        node2.next = node3;
//////        node3.prev = node2;
//////        node3.next = node4;
//////        node3.child = node7;
//////        node4.prev = node3;
//////        node4.next = node5;
//////        node5.prev = node4;
//////        node5.next = node6;
//////        node6.prev = node5;
//////        node7.prev = node3;
//////        node7.next = node8;
//////        node8.prev = node7;
//////        node8.next = node9;
//////        node8.child = node11;
//////        node11.prev = node8;
//////        node9.prev = node8;
//////        node9.next = node10;
//////        node10.prev = node9;
//////        node11.next = node12;
//////        node12.prev = node11;
//////
//////        getOrder(node1);
//////
//////        System.out.println();
//////        Node result = flatten(node1);
//////        getOrder(result);
//////
////////       ArrayList<Integer> list = test1();
////////       System.out.println(list);
//////        int[] ints = new int[] {1,8,6,2,5,4,8,3,7};   ,879,4234,123,546,33,12,546,78,23,13
//////        int k = maxArea(ints);
//////        System.out.println(k);
//////       long i = 2;
////////            long j = 2;
////////            long k = 3;
////////            long result = ((1 / i)  + j) + k;
////////            System.out.println(result);
////////            System.out.println(Integer.MAX_VALUE);         int[] ints = new int[] {432,123,546,76,12,547,879,4234,123,546,33}; //2783
//////            int k = trap(ints);
//////            System.out.println(k);
//////
//////        boolean  f = robot("URR", new int[][]{{4, 2}}, 3,2);
//////        System.out.println(f);
////
//////            int[][] ints = {{4,2}, {5,1}, {9,0}, {4,3}, {9,3}};
//////            System.out.println(ints.length);
//////            Arrays.sort(ints, new Comparator<int[]>() {
//////                @Override
//////                public int compare(int[] o1, int[] o2) {
//////                    if (o1[0] >= o2[0])
//////                        return 1;
//////                    return -1;
//////                }
//////            });
//////            for (int[] ints1: ints)
//////                System.out.println(ints1[0] + ", " + ints1[1]);
//////
//////            System.out.println(ints[1][1]);
//////            int[] ints = new int[3];
//////            for (int i: ints)
//////                System.out.println(i);
////
//////            int n = 13;
//////            int[][] leadership =  {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
//////                    ,{3,8},{3,9},{8,10},{8,11}};
//////
//////            HashMap<Integer, HashSet<Integer>> leader = new HashMap<>();
//////
//////            for (int i = 1; i <= n; i++)
//////                leader.put(i, new HashSet<>());
//////
//////            for (int i = 0; i < leadership.length; i++) {
//////                HashSet<Integer> list = leader.get(leadership[i][0]);
//////                list.add(leadership[i][1]);
//////            }
//////
//////            for (int i = 1; i <= n; i++) {
//////                if (leader.get(i).size() == 0)
//////                    continue;
//////                leader.get(i).addAll(getSubLeader(leader, leader.get(i)));
//////            }
////
//////            leader.get(1).addAll(getSubLeader(leader, leader.get(1)));
//////
//////            HashSet<Integer> l1 = leader.get(1);
//////            System.out.println("aaa " + l1);
////
//////            HashSet<Integer> set = leader.get(1);
//////            System.out.println(set);
////
//////            for (HashMap.Entry<Integer, HashSet<Integer>> entry: leader.entrySet()) {
//////                System.out.println(entry.getKey());
//////                HashSet<Integer> set = entry.getValue();
//////                System.out.println(set);
//////            }
//////            int n = 13;
//////            int[][] a1 = {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
//////                    ,{3,8},{3,9},{8,10},{8,11}};
//////            int[][] a2 = {{2,2,50},{1,1,111},{1,7,456},{3,5}};
//////            int[] result = bonus(n, a1, a2);
//////            for (int i: result)
//////                System.out.print(i + ", ");
//////            System.out.print((7 / 4) + 1);
//////            int[] answers = new int[] {2,2,2,2};
//////            int count = numRabbits(answers);
//////            System.out.println(count);
//////            HashMap<Integer, Integer> each = new HashMap<>();
//////            for (int i: answers) {
//////                Integer current = each.get(i);
//////                each.put(i, current == null ? 1 : current + 1);
//////            }
//////            for (Map.Entry<Integer, Integer> entry: each.entrySet()) {
//////                System.out.print(entry.getKey() + " : ");
//////                System.out.print(entry.getValue());
//////                System.out.println();
//////            }
//////            int k = totalFruit(new int[] {0,1,2,2});
//////            System.out.println(k);
//////            int k = equalSubstring("szrpjazjjhorzeiduufspm","rgwdrgligareauwihaqroy",55);
//////            System.out.println(k);
//////
//////            int k1 = 'f' - 'b';
//////            System.out.println(k1);
//////            String result = removeDuplicates("yfttttfbbbbnnnnffbgffffgbbbbgssssgthyyyy", 4);
//////            System.out.println(result);
//////            double result = myPow(0, Integer.MIN_VALUE);
//////            System.out.println(result);
//////            char[] chars = {'a', 'b', 'c'};
//////            char[] chars1 = {'c', 'b', 'a'};
//////            Arrays.sort(chars1);
//////            System.out.println(Arrays.equals(chars, chars1));
//////
//////            String str1 = "abc";
//////            String str2 = "abc";
//////            System.out.println(str1.hashCode());
//////            System.out.println(str2.hashCode());
//////
//////            String[] strings = new String[]{"eat","tea","tan","ate","nat","bat"};
//////            groupAnagrams(strings);
//////
//////            HashMap<String, Integer> map = new HashMap<>();
//////            for (String str: strings) {
//////                Integer i = map.get(str);
//////                map.put(str, i == null ? 1 : ++i);
//////            }
//////            System.out.println("----");
//////            for (Map.Entry<String, Integer> entry: map.entrySet())
//////                System.out.println(entry.getKey());
//////            Test test = new Test();
//////            int[] nums = new int[]{1, 2, 3, 4};
//////            List<List<Integer>> permute = test.permute(nums);
//////            for (int i = 0; i < permute.size(); i++) {
//////                System.out.println(permute.get(i));
//////            }
//////            char[][] chars = new char[][] {{'5','3','.','.','7','.','.','.','.'},
//////                    {'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},
//////                    {'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},
//////                    {'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},
//////                    {'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
//////            System.out.println(isValidSudoku(chars));
////
//////            Test test = new Test();
//////            test.levelOrder(null);
//////            TreeNode n3 = test.getter(3);
//////            TreeNode n1 = test.getter(1);
//////            TreeNode n5 = test.getter(5);
//////            TreeNode n0 = test.getter(0);
//////            TreeNode n2 = test.getter(2);
//////            TreeNode n4 = test.getter(4);
//////            TreeNode n6 = test.getter(6);
//////            TreeNode n7 = test.getter(3);
//////            n3.left = n1;
//////            n3.right = n5;
//////            n1.left = n0;
//////            n1.right = n2;
//////            n5.left = n4;
//////            n5.right = n6;
//////            n2.right = n7;
//////            System.out.println(test.isValidBST(n3));
//////
//////            int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//////            int[] candidates = new int[] {10,1,2,7,6,1,5};
//////            List<List<Integer>> listList = test.combinationSum2(candidates, 8);
//////            System.out.println(listList);
//////        System.out.println(test.multiply("765", "4654"));
//////        System.out.println(test.mySqrt(4));
//////        int[] res;
//////        int a = 31;
//////        if (a == 3)
//////            res = new int[10];
//////        else
//////            res = new int[20];
//////        for (int i: res)
//////            System.out.print(i);
//////        int[] res = test.plusOne(new int[] {0});
//////        for (int i: res)
//////            System.out.print(i + " , ");
//////        List<List<Integer>> lists = test.permute1(new int[] {1, 2, 3});
//////        for (List<Integer> list: lists)
//////            System.out.println(list);
//////        System.out.println(test.firstMissingPositive(new int[] {7,8,9,10,13}));
//////        System.out.println(test.getRow(3));
        */
    }
}




