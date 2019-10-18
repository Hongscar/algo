package temp;

import com.sun.deploy.util.ArrayUtil;
import sun.reflect.generics.tree.Tree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Seth
 * @Description:
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


    //TODO
    public static int search(int[] nums, int target) {
        int len = nums.length;
        int mid = len / 2;
        int left = 0;
        int right = len - 1;
        while (left <= right && left >= 0 && right >= 0) {
            mid = (left + right) / 2;
            if (nums[left] == target)
                return left;
            if (nums[right] == target)
                return right;
            if (nums[mid] == target)
                return mid;
            if (left == right)
                return nums[left] == target ? left : -1;
            if (nums[mid] > nums[left]) {
                if (target < nums[left])
                    left = mid + 1;
                else if (target < nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            } else {
                if (nums[mid] < target) {
                    int mmid;
                    if (target < nums[right])
                        mmid = (mid + right) / 2;
                    else
                        mmid = (mid + left) / 2;
                    if (nums[mmid] == target)
                        return mmid;
                    else if (nums[mmid] > target)
                        right = mmid - 1;
                    else
                        left = mmid + 1;
                } else
                    right = mid - 1;
            }
        }
        return -1;
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

    public static int trap(int[] height) {
        int length = height.length;
        if (length <= 2)
            return 0;

        int sum = 0;
        int nextBigger;
        boolean flag = false;
        //  int final_index = 0;
        //    int rightMost = 0;

//        if (height[length - 1] > height[length - 2]) {
//            for (int i = length - 3; i >= 0; i--) {
//                if (height[i] > height[length - 1]) {
//                    rightMost = i;
//                    break;
//                }
//            }
//        }

        for (int i = 0; i < height.length; ) {

            nextBigger = -1;
            for (int j = i + 1; j < length; j++) {
                if (height[j] >= height[i]) {
                    nextBigger = j;
                    break;
                }
            }

            if (nextBigger == -1) {
                i++;
                flag = true;
                continue;
            }

            for (int j = i; j < nextBigger; j++) {
                if (!flag)
                    sum += height[i] - height[j];
                else
                    sum += height[nextBigger] - height[j];
            }

            flag = false;

            i = nextBigger;
        }

        return sum;
    }

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

    public class TreeNode {
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

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int i = 0, j = 0, min = grid[0][0], minI = 0, minJ = 0;
        while (i <= m && j <= n) {

        }
        return 1;
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

    // TODO
    public String minWindow(String s, String t) {
        int left = 0;
        int right = s.length() - 1;
        String result = " ";
        for (int i = left; i <= right; i++) {
            String sub = s.substring(left, right + 1);
            for (char c: t.toCharArray())
                if (sub.indexOf(String.valueOf(c)) == -1)
                    return result;
            result = sub;
        }

        return s.contains(t) ? t : " ";     // t.length == 1 ?
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

    public static void main(String[] args) {
        Test test = new Test();
//        System.out.println(test.longestCommonSubsequence("cdabbqwe", "acde"));
        long start = System.currentTimeMillis();
        System.out.println(test.findSubsequences(new int[] {5,5,6}));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        //,3,-4,4,7,7,-3,-3
//        int[] res = test.dailyTemperatures(new int[] {89,62,70,58,47,47,46,76,100,70});
//        for (int r: res)
//            System.out.print(r + ", ");
//        System.out.println(test.firstUniqChar("asfjdasfkjdsqwoefwed"));
////        System.out.println(test.longestConsecutive(new int[] {9,1,4,7,3,-1,0,5,8,-1,6}));
//////        TreeNode node1 = test.makeTreeNode(5);
//////        TreeNode node2 = test.makeTreeNode(4);
//////        TreeNode node3 = test.makeTreeNode(8);
//////        TreeNode node4 = test.makeTreeNode(11);
//////        TreeNode node5 = test.makeTreeNode(13);
//////        TreeNode node6 = test.makeTreeNode(4);
//////        TreeNode node7 = test.makeTreeNode(7);
//////        TreeNode node8 = test.makeTreeNode(2);
//////        TreeNode node9 = test.makeTreeNode(5);
//////        TreeNode node10 = test.makeTreeNode(1);
//////        node1.left = node2;
//////        node1.right = node3;
//////        node2.left = node4;
//////        node3.left = node5;
//////        node3.right = node6;
//////        node4.left = node7;
//////        node4.right = node8;
//////        node6.left = node9;
//////        node6.right = node10;
//////        List<List<Integer>> result = test.pathSum(node1, 22);
//////        for (List<Integer> list: result)
//////            System.out.println(list);
//////        test.isPalindrome("A man, a plan, a canal: Panama");
//////        TreeNode root = test.makeTreeNode(1);
//////        root.left = test.makeTreeNode(2);
//////        root.right = test.makeTreeNode(0);
//////        System.out.println(test.hasPathSum(root, 1));
//////        System.out.println(test.largestNumber(new int[] {0,0,0,0}));
//////        System.out.println(test.largestRectangleArea1(new int[] {0,0,0,0,0,0,0,0,2147483647}));
//////        System.out.println(null == null);   // true
//////        char a = '4', b = '3';
//////        System.out.println(a > b);
////
//////        System.out.println(test.numDecodings("20"));
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
        //l5.next = l6;
//        test.removeZeroSumSublists(l1);
////        l5.next = l6;
////        l6.next = l7;
////        l7.next = l8;
////        int[] ints = test.nextLargerNodes1(l1);
////        for (int i: ints)
////            System.out.print(i + ", ");
////         l3.next = l4;
////        l4.next = l5;
////        test.removeElements1(l1, 2);
////        System.out.println(l1 + " , " + l1.next);
////        l2.next = l3;
////        test.reverseList11(l1);
////        System.out.println(test.jump(new int[] {3,4,2,4,5,4,3,2,4,7,6,4,3,2,4,2}));
//////        System.out.println(test.dieSimulator(2, new int[] {1, 1, 2, 2, 2, 3}));
//////        System.out.println(test.balancedStringSplit("RLLLRLLRRR"));
//////        ListNode l1 = test.makeNode(1);
//////        ListNode l2 = test.makeNode(1);
//////        ListNode l3 = test.makeNode(2);
//////        l1.next = l2;
//////        l2.next = l3;
//////        test.deleteDuplicates(l1);
//////        for (int i = 1; i < 100; i++) {
//////            double temp = Math.pow(4, i);
//////            if (temp > Integer.MAX_VALUE) {
//////                System.out.println(i);
//////                break;
//////            }
//////        }
//////        test.containsNearbyDuplicate(new int[] {1, 2, 3, 1, 2, 3}, 61);
//////        ListNode l1 = test.makeNode(1);
//////        ListNode l2 = test.makeNode(2);
//////        ListNode l3 = test.makeNode(3);
//////        l1.next = l2;
//////        l2.next = l3;
//////        System.out.println(test.reverseList1(l1));
//////        int i = -234;
//////        i = Math.abs(i);
//////        System.out.println(i);
//////        StringBuilder sb = new StringBuilder("wefsdf");
//////        sb.reverse();
//////        System.out.println(sb);
//////        System.out.println(test.majorityElement(new int[] {3, 3, 4}));
////////        ListNode l1 = test.makeNode(4);
////////        ListNode l2 = test.makeNode(1);
////////        ListNode l3 = test.makeNode(8);
////////        ListNode l4 = test.makeNode(4);
////////        ListNode l5 = test.makeNode(5);
////////        l1.next = l2;
////////        l2.next = l3;
////////        l3.next = l4;
////////        l4.next = l5;
////////
////////        ListNode l21 = test.makeNode(5);
////////        ListNode l22 = test.makeNode(0);
////////        ListNode l23 = test.makeNode(1);
////////        l21.next = l22;
////////        l22.next = l23;
////////        l23.next = l3;
////////
////////        ListNode res = test.getIntersectionNode(l1, l21);
////////        System.out.println(res.val);
//////
//////
////////        test.reverseString(new char[]{});
////////        System.out.println(test.reverseStr("avcdefg", 2));
////////        System.out.println(test.isMatch("b", "ab*b"));
////////        List<Integer> list = test.countSteppingNumbers(0, 0);
////////        for (int i = 0; i < list.size(); i++) {
////////            System.out.print(list.get(i) + " ");
////////            if (i % 25 == 0)
////////                System.out.println();
////////        }
////////        System.out.println();
////////        System.out.println(2 * Math.pow(10, 8));
////////         System.out.println(Integer.MAX_VALUE);
//////
////////            int i = longestValidParentheses(")()()(((((()");
////////            System.out.println(i);
//////
////////            int k = search(new int[] {15,16,19,20,25,1,3,4,5,7,10,14}, 25);
////////            System.out.print(k);
////////        String str = "aacdefcaaywwkew";    //aacdefcaaywwkew
////////        String result = longestPalindrome(str);
////////        System.out.println(result);
//////
////////        String str = "LEETCODEISHIRING";
////////        String result = convert(str, 4);
////////        System.out.println(result);
//////
////////        String s = "-23523";
////////        int i = Integer.valueOf(s);
////////        System.out.println(i);
//////
////////        boolean f = isPalindrome(100021);
////////        System.out.println(f);
////////        int i = 3;
////////
////////        String str = "sdfdf";
////////        char s = str.charAt(546);
//////
////////        int i = romanToInt("MDLXX");
////////        System.out.println(i);
//////
////////        int[] ints = {2, 5, 3, 1, 8, 9, 6, 5};
////////        Arrays.sort(ints);
////////        List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
////////        ArrayList<Integer> list1 = new ArrayList<>(list);
////////        System.out.println(list);
//////
////////        List<List<Integer>> lists = threeSum(new int[]{-2, 0, 1, 1, 2});
////////        System.out.println(lists);
////////        List<String> stringList = letterCombinations("");
////////        for (String str : stringList)
////////            System.out.println(str);
//////
////////        List<List<Integer>> stringList = fourSum(new int[]{1,-2,-5,-4,-3,3,3,5}, -11);
////////        for (List<Integer> integers : stringList)
////////            System.out.println(integers);
////////            ListNode node = new ListNode(1);
////////            node.next = new ListNode(2);
////////            node.next.next = new ListNode(3);
////////            node.next.next.next = new ListNode(4);
////////            node.next.next.next.next = new ListNode(5);
////////            ListNode node1 = removeNthFromEnd(node, 2);
////////
////////            while (node1 != null) {
////////                System.out.print(node.val + " , ");
////////                node = node.next;
////////            }
////////        boolean flag = isValid("{[]}");
////////        System.out.println(flag);
//////
////////        String str = "wessdc";
////////        String sbu = str.substring(6, 6);
////////        System.out.println(sbu);
////////        List<String> list = generateParenthesis(4);
////////        for (String str : list)
////////            System.out.println(str);
////////        ListNode node = new ListNode(1);
////////            node.next = new ListNode(2);
////////            node.next.next = new ListNode(3);
////////            node.next.next.next = new ListNode(4);
////////            node.next.next.next.next = new ListNode(5);
////////        node.next.next.next.next.next = new ListNode(6);
////////        node.next.next.next.next.next.next = new ListNode(7);
////////
////////
////////        ListNode res = swapPairs(node);
////////        while (res != null) {
////////            System.out.println(res.val);
////////            res = res.next;
////////        }
////////        int i = removeElement(new int[]{0,1,2,2,3,0,4,2}, 2);
////////        System.out.println(i);
////////        int as = divide(-2147483648, -2147483648);
////////        System.out.println(as);
//////
////////        int[] ints = nextPermutation(new int[] {1, 2, 3, 5, 4, 3, 2, 1});
////////        for (int i = 0; i < ints.length; i++)
////////            System.out.print(ints[i] + " , ");
//////
////////        int[] ints = new int[]{7,6,5,5,2};
////////        for (int i = 0; i < ints.length / 2; i++) {
////////            int temp = ints[i];
////////            ints[i] = ints[ints.length - 1 - i];
////////            ints[ints.length - 1 - i] = temp;
////////        }
////////        for (int i : ints)
////////            System.out.print(i + " , ");
////////        int[] ints = searchRange(new int[]{}, 0);
////////        System.out.println(ints[0] + " , " + ints[1]);
////////        String str = countAndSay(8);
////////        System.out.println(str);
////////        combinationSum(new int[]{2, 3, 5}, 8);
////////        for (List<Integer> list : listList) {
////////            for (Integer integer : list) {
////////                System.out.print(integer + " , ");
////////            }
////////            System.out.println();
////////        }
//////            // 681692778  351251360
//////            // 2147483647
//////            //System.out.println(Integer.MAX_VALUE);
//////
//////            //957747794
//////            //424238336
////////        int k = findKthNumber(957747794, 424238336);
////////        System.out.println(k);
////////        int[][] items = {{1,91},{1,92},{2,93},{2,97},{1,60},{2,77},{1,65},{1,87},{1,100},{2,100},{2,76}};
////////
////////        int[][] result = highFive(items);
////////
////////        System.out.println(result);
////////        int[] ints = {2, 1, 2, 4, 3};
////////        ArrayList<Integer> list = nextGreaterElement(ints);
////////        Collections.reverse(list);
////////        System.out.println(list);
////////        int[] ints = {1, 4, 3, 6, 8, 22, 13, 17, 16};
////////        int[] ints1 = new int[ints.length * 2];
////////        System.arraycopy(ints, 0, ints1, 0, ints.length);
////////        System.arraycopy(ints, 0, ints1, ints.length, ints.length);
////////        for (int i : ints1)
////////            System.out.print(i + ", ");
//////
////////        int[] result = nextGreaterElements(ints);
////////        for (int i : result)
////////            System.out.print(i + ", ");
////////        int i = nextGreaterElement(23792563);
////////        System.out.print(i);
////////        int i = maxProfit(new int[]{4, 5, 2, 4, 3, 3, 1, 2, 5}, 1);
////////        System.out.print(i);
////////        Node node1 = new Node(1, null, null, null);
////////        Node node2 = new Node(2, null, null, null);
////////        Node node3 = new Node(3, null, null, null);
////////        Node node4 = new Node(4, null, null, null);
////////        Node node5 = new Node(5, null, null, null);
////////        Node node6 = new Node(6, null, null, null);
////////        Node node7 = new Node(7, null, null, null);
////////        Node node8 = new Node(8, null, null, null);
////////        Node node9 = new Node(9, null, null, null);
////////        Node node10 = new Node(10, null, null, null);
////////        Node node11 = new Node(11, null, null, null);
////////        Node node12 = new Node(12, null, null, null);
////////        node1.next = node2;
////////        node2.prev = node1;
////////        node2.next = node3;
////////        node3.prev = node2;
////////        node3.next = node4;
////////        node3.child = node7;
////////        node4.prev = node3;
////////        node4.next = node5;
////////        node5.prev = node4;
////////        node5.next = node6;
////////        node6.prev = node5;
////////        node7.prev = node3;
////////        node7.next = node8;
////////        node8.prev = node7;
////////        node8.next = node9;
////////        node8.child = node11;
////////        node11.prev = node8;
////////        node9.prev = node8;
////////        node9.next = node10;
////////        node10.prev = node9;
////////        node11.next = node12;
////////        node12.prev = node11;
////////
////////        getOrder(node1);
////////
////////        System.out.println();
////////        Node result = flatten(node1);
////////        getOrder(result);
////////
//////////       ArrayList<Integer> list = test1();
//////////       System.out.println(list);
////////        int[] ints = new int[] {1,8,6,2,5,4,8,3,7};   ,879,4234,123,546,33,12,546,78,23,13
////////        int k = maxArea(ints);
////////        System.out.println(k);
////////       long i = 2;
//////////            long j = 2;
//////////            long k = 3;
//////////            long result = ((1 / i)  + j) + k;
//////////            System.out.println(result);
//////////            System.out.println(Integer.MAX_VALUE);         int[] ints = new int[] {432,123,546,76,12,547,879,4234,123,546,33}; //2783
////////            int k = trap(ints);
////////            System.out.println(k);
////////
////////        boolean  f = robot("URR", new int[][]{{4, 2}}, 3,2);
////////        System.out.println(f);
//////
////////            int[][] ints = {{4,2}, {5,1}, {9,0}, {4,3}, {9,3}};
////////            System.out.println(ints.length);
////////            Arrays.sort(ints, new Comparator<int[]>() {
////////                @Override
////////                public int compare(int[] o1, int[] o2) {
////////                    if (o1[0] >= o2[0])
////////                        return 1;
////////                    return -1;
////////                }
////////            });
////////            for (int[] ints1: ints)
////////                System.out.println(ints1[0] + ", " + ints1[1]);
////////
////////            System.out.println(ints[1][1]);
////////            int[] ints = new int[3];
////////            for (int i: ints)
////////                System.out.println(i);
//////
////////            int n = 13;
////////            int[][] leadership =  {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
////////                    ,{3,8},{3,9},{8,10},{8,11}};
////////
////////            HashMap<Integer, HashSet<Integer>> leader = new HashMap<>();
////////
////////            for (int i = 1; i <= n; i++)
////////                leader.put(i, new HashSet<>());
////////
////////            for (int i = 0; i < leadership.length; i++) {
////////                HashSet<Integer> list = leader.get(leadership[i][0]);
////////                list.add(leadership[i][1]);
////////            }
////////
////////            for (int i = 1; i <= n; i++) {
////////                if (leader.get(i).size() == 0)
////////                    continue;
////////                leader.get(i).addAll(getSubLeader(leader, leader.get(i)));
////////            }
//////
////////            leader.get(1).addAll(getSubLeader(leader, leader.get(1)));
////////
////////            HashSet<Integer> l1 = leader.get(1);
////////            System.out.println("aaa " + l1);
//////
////////            HashSet<Integer> set = leader.get(1);
////////            System.out.println(set);
//////
////////            for (HashMap.Entry<Integer, HashSet<Integer>> entry: leader.entrySet()) {
////////                System.out.println(entry.getKey());
////////                HashSet<Integer> set = entry.getValue();
////////                System.out.println(set);
////////            }
////////            int n = 13;
////////            int[][] a1 = {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
////////                    ,{3,8},{3,9},{8,10},{8,11}};
////////            int[][] a2 = {{2,2,50},{1,1,111},{1,7,456},{3,5}};
////////            int[] result = bonus(n, a1, a2);
////////            for (int i: result)
////////                System.out.print(i + ", ");
////////            System.out.print((7 / 4) + 1);
////////            int[] answers = new int[] {2,2,2,2};
////////            int count = numRabbits(answers);
////////            System.out.println(count);
////////            HashMap<Integer, Integer> each = new HashMap<>();
////////            for (int i: answers) {
////////                Integer current = each.get(i);
////////                each.put(i, current == null ? 1 : current + 1);
////////            }
////////            for (Map.Entry<Integer, Integer> entry: each.entrySet()) {
////////                System.out.print(entry.getKey() + " : ");
////////                System.out.print(entry.getValue());
////////                System.out.println();
////////            }
////////            int k = totalFruit(new int[] {0,1,2,2});
////////            System.out.println(k);
////////            int k = equalSubstring("szrpjazjjhorzeiduufspm","rgwdrgligareauwihaqroy",55);
////////            System.out.println(k);
////////
////////            int k1 = 'f' - 'b';
////////            System.out.println(k1);
////////            String result = removeDuplicates("yfttttfbbbbnnnnffbgffffgbbbbgssssgthyyyy", 4);
////////            System.out.println(result);
////////            double result = myPow(0, Integer.MIN_VALUE);
////////            System.out.println(result);
////////            char[] chars = {'a', 'b', 'c'};
////////            char[] chars1 = {'c', 'b', 'a'};
////////            Arrays.sort(chars1);
////////            System.out.println(Arrays.equals(chars, chars1));
////////
////////            String str1 = "abc";
////////            String str2 = "abc";
////////            System.out.println(str1.hashCode());
////////            System.out.println(str2.hashCode());
////////
////////            String[] strings = new String[]{"eat","tea","tan","ate","nat","bat"};
////////            groupAnagrams(strings);
////////
////////            HashMap<String, Integer> map = new HashMap<>();
////////            for (String str: strings) {
////////                Integer i = map.get(str);
////////                map.put(str, i == null ? 1 : ++i);
////////            }
////////            System.out.println("----");
////////            for (Map.Entry<String, Integer> entry: map.entrySet())
////////                System.out.println(entry.getKey());
////////            Test test = new Test();
////////            int[] nums = new int[]{1, 2, 3, 4};
////////            List<List<Integer>> permute = test.permute(nums);
////////            for (int i = 0; i < permute.size(); i++) {
////////                System.out.println(permute.get(i));
////////            }
////////            char[][] chars = new char[][] {{'5','3','.','.','7','.','.','.','.'},
////////                    {'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},
////////                    {'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},
////////                    {'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},
////////                    {'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
////////            System.out.println(isValidSudoku(chars));
//////
////////            Test test = new Test();
////////            test.levelOrder(null);
////////            TreeNode n3 = test.getter(3);
////////            TreeNode n1 = test.getter(1);
////////            TreeNode n5 = test.getter(5);
////////            TreeNode n0 = test.getter(0);
////////            TreeNode n2 = test.getter(2);
////////            TreeNode n4 = test.getter(4);
////////            TreeNode n6 = test.getter(6);
////////            TreeNode n7 = test.getter(3);
////////            n3.left = n1;
////////            n3.right = n5;
////////            n1.left = n0;
////////            n1.right = n2;
////////            n5.left = n4;
////////            n5.right = n6;
////////            n2.right = n7;
////////            System.out.println(test.isValidBST(n3));
////////
////////            int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
////////            int[] candidates = new int[] {10,1,2,7,6,1,5};
////////            List<List<Integer>> listList = test.combinationSum2(candidates, 8);
////////            System.out.println(listList);
////////        System.out.println(test.multiply("765", "4654"));
////////        System.out.println(test.mySqrt(4));
////////        int[] res;
////////        int a = 31;
////////        if (a == 3)
////////            res = new int[10];
////////        else
////////            res = new int[20];
////////        for (int i: res)
////////            System.out.print(i);
////////        int[] res = test.plusOne(new int[] {0});
////////        for (int i: res)
////////            System.out.print(i + " , ");
////////        List<List<Integer>> lists = test.permute1(new int[] {1, 2, 3});
////////        for (List<Integer> list: lists)
////////            System.out.println(list);
////////        System.out.println(test.firstMissingPositive(new int[] {7,8,9,10,13}));
////////        System.out.println(test.getRow(3));

    }
}




