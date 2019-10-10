package temp;

import sun.reflect.generics.tree.Tree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
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

    public static void main(String[] args) {
        Test test = new Test();
        test.reverseString(new char[]{});
//        System.out.println(test.reverseStr("avcdefg", 2));
//        System.out.println(test.isMatch("b", "ab*b"));
//        List<Integer> list = test.countSteppingNumbers(0, 0);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.print(list.get(i) + " ");
//            if (i % 25 == 0)
//                System.out.println();
//        }
//        System.out.println();
//        System.out.println(2 * Math.pow(10, 8));
//         System.out.println(Integer.MAX_VALUE);

//        int[] ints = {0,1,2,3,4,5,6,7,8,9,10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98,101,121,123,210,212,232,234,321,323,343,345,432,434,454,456,543,545,565,567,654,656,676,678,765,767,787,789,876,878,898,987,989,1010,1012,1210,1212,1232,1234,2101,2121,2123,2321,2323,2343,2345,3210,3212,3232,3234,3432,3434,3454,3456,4321,4323,4343,4345,4543,4545,4565,4567,5432,5434,5454,5456,5654,5656,5676,5678,6543,6545,6565,6567,6765,6767,6787,6789,7654,7656,7676,7678,7876,7878,7898,8765,8767,8787,8789,8987,8989,9876,9878,9898,10101,10121,10123,12101,12121,12123,12321,12323,12343,12345,21010,21012,21210,21212,21232,21234,23210,23212,23232,23234,23432,23434,23454,23456,32101,32121,32123,32321,32323,32343,32345,34321,34323,34343,34345,34543,34545,34565,34567,43210,43212,43232,43234,43432,43434,43454,43456,45432,45434,45454,45456,45654,45656,45676,45678,54321,54323,54343,54345,54543,54545,54565,54567,56543,56545,56565,56567,56765,56767,56787,56789,65432,65434,65454,65456,65654,65656,65676,65678,67654,67656,67676,67678,67876,67878,67898,76543,76545,76565,76567,76765,76767,76787,76789,78765,78767,78787,78789,78987,78989,87654,87656,87676,87678,87876,87878,87898,89876,89878,89898,98765,98767,98787,98789,98987,98989,101010,101012,101210,101212,101232,101234,121010,121012,121210,121212,121232,121234,123210,123212,123232,123234,123432,123434,123454,123456,210101,210121,210123,212101,212121,212123,212321,212323,212343,212345,232101,232121,232123,232321,232323,232343,232345,234321,234323,234343,234345,234543,234545,234565,234567,321010,321012,321210,321212,321232,321234,323210,323212,323232,323234,323432,323434,323454,323456,343210,343212,343232,343234,343432,343434,343454,343456,345432,345434,345454,345456,345654,345656,345676,345678,432101,432121,432123,432321,432323,432343,432345,434321,434323,434343,434345,434543,434545,434565,434567,454321,454323,454343,454345,454543,454545,454565,454567,456543,456545,456565,456567,456765,456767,456787,456789,543210,543212,543232,543234,543432,543434,543454,543456,545432,545434,545454,545456,545654,545656,545676,545678,565432,565434,565454,565456,565654,565656,565676,565678,567654,567656,567676,567678,567876,567878,567898,654321,654323,654343,654345,654543,654545,654565,654567,656543,656545,656565,656567,656765,656767,656787,656789,676543,676545,676565,676567,676765,676767,676787,676789,678765,678767,678787,678789,678987,678989,765432,765434,765454,765456,765654,765656,765676,765678,767654,767656,767676,767678,767876,767878,767898,787654,787656,787676,787678,787876,787878,787898,789876,789878,789898,876543,876545,876565,876567,876765,876767,876787,876789,878765,878767,878787,878789,878987,878989,898765,898767,898787,898789,898987,898989,987654,987656,987676,987678,987876,987878,987898,989876,989878,989898,1010101,1010121,1010123,1012101,1012121,1012123,1012321,1012323,1012343,1012345,1210101,1210121,1210123,1212101,1212121,1212123,1212321,1212323,1212343,1212345,1232101,1232121,1232123,1232321,1232323,1232343,1232345,1234321,1234323,1234343,1234345,1234543,1234545,1234565,1234567,2101010,2101012,2101210,2101212,2101232,2101234,2121010,2121012,2121210,2121212,2121232,2121234,2123210,2123212,2123232,2123234,2123432,2123434,2123454,2123456,2321010,2321012,2321210,2321212,2321232,2321234,2323210,2323212,2323232,2323234,2323432,2323434,2323454,2323456,2343210,2343212,2343232,2343234,2343432,2343434,2343454,2343456,2345432,2345434,2345454,2345456,2345654,2345656,2345676,2345678,3210101,3210121,3210123,3212101,3212121,3212123,3212321,3212323,3212343,3212345,3232101,3232121,3232123,3232321,3232323,3232343,3232345,3234321,3234323,3234343,3234345,3234543,3234545,3234565,3234567,3432101,3432121,3432123,3432321,3432323,3432343,3432345,3434321,3434323,3434343,3434345,3434543,3434545,3434565,3434567,3454321,3454323,3454343,3454345,3454543,3454545,3454565,3454567,3456543,3456545,3456565,3456567,3456765,3456767,3456787,3456789,4321010,4321012,4321210,4321212,4321232,4321234,4323210,4323212,4323232,4323234,4323432,4323434,4323454,4323456,4343210,4343212,4343232,4343234,4343432,4343434,4343454,4343456,4345432,4345434,4345454,4345456,4345654,4345656,4345676,4345678,4543210,4543212,4543232,4543234,4543432,4543434,4543454,4543456,4545432,4545434,4545454,4545456,4545654,4545656,4545676,4545678,4565432,4565434,4565454,4565456,4565654,4565656,4565676,4565678,4567654,4567656,4567676,4567678,4567876,4567878,4567898,5432101,5432121,5432123,5432321,5432323,5432343,5432345,5434321,5434323,5434343,5434345,5434543,5434545,5434565,5434567,5454321,5454323,5454343,5454345,5454543,5454545,5454565,5454567,5456543,5456545,5456565,5456567,5456765,5456767,5456787,5456789,5654321,5654323,5654343,5654345,5654543,5654545,5654565,5654567,5656543,5656545,5656565,5656567,5656765,5656767,5656787,5656789,5676543,5676545,5676565,5676567,5676765,5676767,5676787,5676789,5678765,5678767,5678787,5678789,5678987,5678989,6543210,6543212,6543232,6543234,6543432,6543434,6543454,6543456,6545432,6545434,6545454,6545456,6545654,6545656,6545676,6545678,6565432,6565434,6565454,6565456,6565654,6565656,6565676,6565678,6567654,6567656,6567676,6567678,6567876,6567878,6567898,6765432,6765434,6765454,6765456,6765654,6765656,6765676,6765678,6767654,6767656,6767676,6767678,6767876,6767878,6767898,6787654,6787656,6787676,6787678,6787876,6787878,6787898,6789876,6789878,6789898,7654321,7654323,7654343,7654345,7654543,7654545,7654565,7654567,7656543,7656545,7656565,7656567,7656765,7656767,7656787,7656789,7676543,7676545,7676565,7676567,7676765,7676767,7676787,7676789,7678765,7678767,7678787,7678789,7678987,7678989,7876543,7876545,7876565,7876567,7876765,7876767,7876787,7876789,7878765,7878767,7878787,7878789,7878987,7878989,7898765,7898767,7898787,7898789,7898987,7898989,8765432,8765434,8765454,8765456,8765654,8765656,8765676,8765678,8767654,8767656,8767676,8767678,8767876,8767878,8767898,8787654,8787656,8787676,8787678,8787876,8787878,8787898,8789876,8789878,8789898,8987654,8987656,8987676,8987678,8987876,8987878,8987898,8989876,8989878,8989898,9876543,9876545,9876565,9876567,9876765,9876767,9876787,9876789,9878765,9878767,9878787,9878789,9878987,9878989,9898765,9898767,9898787,9898789,9898987,9898989,10101010,10101012,10101210,10101212,10101232,10101234,10121010,10121012,10121210,10121212,10121232,10121234,10123210,10123212,10123232,10123234,10123432,10123434,10123454,10123456,12101010,12101012,12101210,12101212,12101232,12101234,12121010,12121012,12121210,12121212,12121232,12121234,12123210,12123212,12123232,12123234,12123432,12123434,12123454,12123456,12321010,12321012,12321210,12321212,12321232,12321234,12323210,12323212,12323232,12323234,12323432,12323434,12323454,12323456,12343210,12343212,12343232,12343234,12343432,12343434,12343454,12343456,12345432,12345434,12345454,12345456,12345654,12345656,12345676,12345678,21010101,21010121,21010123,21012101,21012121,21012123,21012321,21012323,21012343,21012345,21210101,21210121,21210123,21212101,21212121,21212123,21212321,21212323,21212343,21212345,21232101,21232121,21232123,21232321,21232323,21232343,21232345,21234321,21234323,21234343,21234345,21234543,21234545,21234565,21234567,23210101,23210121,23210123,23212101,23212121,23212123,23212321,23212323,23212343,23212345,23232101,23232121,23232123,23232321,23232323,23232343,23232345,23234321,23234323,23234343,23234345,23234543,23234545,23234565,23234567,23432101,23432121,23432123,23432321,23432323,23432343,23432345,23434321,23434323,23434343,23434345,23434543,23434545,23434565,23434567,23454321,23454323,23454343,23454345,23454543,23454545,23454565,23454567,23456543,23456545,23456565,23456567,23456765,23456767,23456787,23456789,32101010,32101012,32101210,32101212,32101232,32101234,32121010,32121012,32121210,32121212,32121232,32121234,32123210,32123212,32123232,32123234,32123432,32123434,32123454,32123456,32321010,32321012,32321210,32321212,32321232,32321234,32323210,32323212,32323232,32323234,32323432,32323434,32323454,32323456,32343210,32343212,32343232,32343234,32343432,32343434,32343454,32343456,32345432,32345434,32345454,32345456,32345654,32345656,32345676,32345678,34321010,34321012,34321210,34321212,34321232,34321234,34323210,34323212,34323232,34323234,34323432,34323434,34323454,34323456,34343210,34343212,34343232,34343234,34343432,34343434,34343454,34343456,34345432,34345434,34345454,34345456,34345654,34345656,34345676,34345678,34543210,34543212,34543232,34543234,34543432,34543434,34543454,34543456,34545432,34545434,34545454,34545456,34545654,34545656,34545676,34545678,34565432,34565434,34565454,34565456,34565654,34565656,34565676,34565678,34567654,34567656,34567676,34567678,34567876,34567878,34567898,43210101,43210121,43210123,43212101,43212121,43212123,43212321,43212323,43212343,43212345,43232101,43232121,43232123,43232321,43232323,43232343,43232345,43234321,43234323,43234343,43234345,43234543,43234545,43234565,43234567,43432101,43432121,43432123,43432321,43432323,43432343,43432345,43434321,43434323,43434343,43434345,43434543,43434545,43434565,43434567,43454321,43454323,43454343,43454345,43454543,43454545,43454565,43454567,43456543,43456545,43456565,43456567,43456765,43456767,43456787,43456789,45432101,45432121,45432123,45432321,45432323,45432343,45432345,45434321,45434323,45434343,45434345,45434543,45434545,45434565,45434567,45454321,45454323,45454343,45454345,45454543,45454545,45454565,45454567,45456543,45456545,45456565,45456567,45456765,45456767,45456787,45456789,45654321,45654323,45654343,45654345,45654543,45654545,45654565,45654567,45656543,45656545,45656565,45656567,45656765,45656767,45656787,45656789,45676543,45676545,45676565,45676567,45676765,45676767,45676787,45676789,45678765,45678767,45678787,45678789,45678987,45678989,54321010,54321012,54321210,54321212,54321232,54321234,54323210,54323212,54323232,54323234,54323432,54323434,54323454,54323456,54343210,54343212,54343232,54343234,54343432,54343434,54343454,54343456,54345432,54345434,54345454,54345456,54345654,54345656,54345676,54345678,54543210,54543212,54543232,54543234,54543432,54543434,54543454,54543456,54545432,54545434,54545454,54545456,54545654,54545656,54545676,54545678,54565432,54565434,54565454,54565456,54565654,54565656,54565676,54565678,54567654,54567656,54567676,54567678,54567876,54567878,54567898,56543210,56543212,56543232,56543234,56543432,56543434,56543454,56543456,56545432,56545434,56545454,56545456,56545654,56545656,56545676,56545678,56565432,56565434,56565454,56565456,56565654,56565656,56565676,56565678,56567654,56567656,56567676,56567678,56567876,56567878,56567898,56765432,56765434,56765454,56765456,56765654,56765656,56765676,56765678,56767654,56767656,56767676,56767678,56767876,56767878,56767898,56787654,56787656,56787676,56787678,56787876,56787878,56787898,56789876,56789878,56789898,65432101,65432121,65432123,65432321,65432323,65432343,65432345,65434321,65434323,65434343,65434345,65434543,65434545,65434565,65434567,65454321,65454323,65454343,65454345,65454543,65454545,65454565,65454567,65456543,65456545,65456565,65456567,65456765,65456767,65456787,65456789,65654321,65654323,65654343,65654345,65654543,65654545,65654565,65654567,65656543,65656545,65656565,65656567,65656765,65656767,65656787,65656789,65676543,65676545,65676565,65676567,65676765,65676767,65676787,65676789,65678765,65678767,65678787,65678789,65678987,65678989,67654321,67654323,67654343,67654345,67654543,67654545,67654565,67654567,67656543,67656545,67656565,67656567,67656765,67656767,67656787,67656789,67676543,67676545,67676565,67676567,67676765,67676767,67676787,67676789,67678765,67678767,67678787,67678789,67678987,67678989,67876543,67876545,67876565,67876567,67876765,67876767,67876787,67876789,67878765,67878767,67878787,67878789,67878987,67878989,67898765,67898767,67898787,67898789,67898987,67898989,76543210,76543212,76543232,76543234,76543432,76543434,76543454,76543456,76545432,76545434,76545454,76545456,76545654,76545656,76545676,76545678,76565432,76565434,76565454,76565456,76565654,76565656,76565676,76565678,76567654,76567656,76567676,76567678,76567876,76567878,76567898,76765432,76765434,76765454,76765456,76765654,76765656,76765676,76765678,76767654,76767656,76767676,76767678,76767876,76767878,76767898,76787654,76787656,76787676,76787678,76787876,76787878,76787898,76789876,76789878,76789898,78765432,78765434,78765454,78765456,78765654,78765656,78765676,78765678,78767654,78767656,78767676,78767678,78767876,78767878,78767898,78787654,78787656,78787676,78787678,78787876,78787878,78787898,78789876,78789878,78789898,78987654,78987656,78987676,78987678,78987876,78987878,78987898,78989876,78989878,78989898,87654321,87654323,87654343,87654345,87654543,87654545,87654565,87654567,87656543,87656545,87656565,87656567,87656765,87656767,87656787,87656789,87676543,87676545,87676565,87676567,87676765,87676767,87676787,87676789,87678765,87678767,87678787,87678789,87678987,87678989,87876543,87876545,87876565,87876567,87876765,87876767,87876787,87876789,87878765,87878767,87878787,87878789,87878987,87878989,87898765,87898767,87898787,87898789,87898987,87898989,89876543,89876545,89876565,89876567,89876765,89876767,89876787,89876789,89878765,89878767,89878787,89878789,89878987,89878989,89898765,89898767,89898787,89898789,89898987,89898989,98765432,98765434,98765454,98765456,98765654,98765656,98765676,98765678,98767654,98767656,98767676,98767678,98767876,98767878,98767898,98787654,98787656,98787676,98787678,98787876,98787878,98787898,98789876,98789878,98789898,98987654,98987656,98987676,98987678,98987876,98987878,98987898,98989876,98989878,98989898,101010101,101010121,101010123,101012101,101012121,101012123,101012321,101012323,101012343,101012345,101210101,101210121,101210123,101212101,101212121,101212123,101212321,101212323,101212343,101212345,101232101,101232121,101232123,101232321,101232323,101232343,101232345,101234321,101234323,101234343,101234345,101234543,101234545,101234565,101234567,121010101,121010121,121010123,121012101,121012121,121012123,121012321,121012323,121012343,121012345,121210101,121210121,121210123,121212101,121212121,121212123,121212321,121212323,121212343,121212345,121232101,121232121,121232123,121232321,121232323,121232343,121232345,121234321,121234323,121234343,121234345,121234543,121234545,121234565,121234567,123210101,123210121,123210123,123212101,123212121,123212123,123212321,123212323,123212343,123212345,123232101,123232121,123232123,123232321,123232323,123232343,123232345,123234321,123234323,123234343,123234345,123234543,123234545,123234565,123234567,123432101,123432121,123432123,123432321,123432323,123432343,123432345,123434321,123434323,123434343,123434345,123434543,123434545,123434565,123434567,123454321,123454323,123454343,123454345,123454543,123454545,123454565,123454567,123456543,123456545,123456565,123456567,123456765,123456767,123456787,123456789,210101010,210101012,210101210,210101212,210101232,210101234,210121010,210121012,210121210,210121212,210121232,210121234,210123210,210123212,210123232,210123234,210123432,210123434,210123454,210123456,212101010,212101012,212101210,212101212,212101232,212101234,212121010,212121012,212121210,212121212,212121232,212121234,212123210,212123212,212123232,212123234,212123432,212123434,212123454,212123456,212321010,212321012,212321210,212321212,212321232,212321234,212323210,212323212,212323232,212323234,212323432,212323434,212323454,212323456,212343210,212343212,212343232,212343234,212343432,212343434,212343454,212343456,212345432,212345434,212345454,212345456,212345654,212345656,212345676,212345678,232101010,232101012,232101210,232101212,232101232,232101234,232121010,232121012,232121210,232121212,232121232,232121234,232123210,232123212,232123232,232123234,232123432,232123434,232123454,232123456,232321010,232321012,232321210,232321212,232321232,232321234,232323210,232323212,232323232,232323234,232323432,232323434,232323454,232323456,232343210,232343212,232343232,232343234,232343432,232343434,232343454,232343456,232345432,232345434,232345454,232345456,232345654,232345656,232345676,232345678,234321010,234321012,234321210,234321212,234321232,234321234,234323210,234323212,234323232,234323234,234323432,234323434,234323454,234323456,234343210,234343212,234343232,234343234,234343432,234343434,234343454,234343456,234345432,234345434,234345454,234345456,234345654,234345656,234345676,234345678,234543210,234543212,234543232,234543234,234543432,234543434,234543454,234543456,234545432,234545434,234545454,234545456,234545654,234545656,234545676,234545678,234565432,234565434,234565454,234565456,234565654,234565656,234565676,234565678,234567654,234567656,234567676,234567678,234567876,234567878,234567898,321010101,321010121,321010123,321012101,321012121,321012123,321012321,321012323,321012343,321012345,321210101,321210121,321210123,321212101,321212121,321212123,321212321,321212323,321212343,321212345,321232101,321232121,321232123,321232321,321232323,321232343,321232345,321234321,321234323,321234343,321234345,321234543,321234545,321234565,321234567,323210101,323210121,323210123,323212101,323212121,323212123,323212321,323212323,323212343,323212345,323232101,323232121,323232123,323232321,323232323,323232343,323232345,323234321,323234323,323234343,323234345,323234543,323234545,323234565,323234567,323432101,323432121,323432123,323432321,323432323,323432343,323432345,323434321,323434323,323434343,323434345,323434543,323434545,323434565,323434567,323454321,323454323,323454343,323454345,323454543,323454545,323454565,323454567,323456543,323456545,323456565,323456567,323456765,323456767,323456787,323456789,343210101,343210121,343210123,343212101,343212121,343212123,343212321,343212323,343212343,343212345,343232101,343232121,343232123,343232321,343232323,343232343,343232345,343234321,343234323,343234343,343234345,343234543,343234545,343234565,343234567,343432101,343432121,343432123,343432321,343432323,343432343,343432345,343434321,343434323,343434343,343434345,343434543,343434545,343434565,343434567,343454321,343454323,343454343,343454345,343454543,343454545,343454565,343454567,343456543,343456545,343456565,343456567,343456765,343456767,343456787,343456789,345432101,345432121,345432123,345432321,345432323,345432343,345432345,345434321,345434323,345434343,345434345,345434543,345434545,345434565,345434567,345454321,345454323,345454343,345454345,345454543,345454545,345454565,345454567,345456543,345456545,345456565,345456567,345456765,345456767,345456787,345456789,345654321,345654323,345654343,345654345,345654543,345654545,345654565,345654567,345656543,345656545,345656565,345656567,345656765,345656767,345656787,345656789,345676543,345676545,345676565,345676567,345676765,345676767,345676787,345676789,345678765,345678767,345678787,345678789,345678987,345678989,432101010,432101012,432101210,432101212,432101232,432101234,432121010,432121012,432121210,432121212,432121232,432121234,432123210,432123212,432123232,432123234,432123432,432123434,432123454,432123456,432321010,432321012,432321210,432321212,432321232,432321234,432323210,432323212,432323232,432323234,432323432,432323434,432323454,432323456,432343210,432343212,432343232,432343234,432343432,432343434,432343454,432343456,432345432,432345434,432345454,432345456,432345654,432345656,432345676,432345678,434321010,434321012,434321210,434321212,434321232,434321234,434323210,434323212,434323232,434323234,434323432,434323434,434323454,434323456,434343210,434343212,434343232,434343234,434343432,434343434,434343454,434343456,434345432,434345434,434345454,434345456,434345654,434345656,434345676,434345678,434543210,434543212,434543232,434543234,434543432,434543434,434543454,434543456,434545432,434545434,434545454,434545456,434545654,434545656,434545676,434545678,434565432,434565434,434565454,434565456,434565654,434565656,434565676,434565678,434567654,434567656,434567676,434567678,434567876,434567878,434567898,454321010,454321012,454321210,454321212,454321232,454321234,454323210,454323212,454323232,454323234,454323432,454323434,454323454,454323456,454343210,454343212,454343232,454343234,454343432,454343434,454343454,454343456,454345432,454345434,454345454,454345456,454345654,454345656,454345676,454345678,454543210,454543212,454543232,454543234,454543432,454543434,454543454,454543456,454545432,454545434,454545454,454545456,454545654,454545656,454545676,454545678,454565432,454565434,454565454,454565456,454565654,454565656,454565676,454565678,454567654,454567656,454567676,454567678,454567876,454567878,454567898,456543210,456543212,456543232,456543234,456543432,456543434,456543454,456543456,456545432,456545434,456545454,456545456,456545654,456545656,456545676,456545678,456565432,456565434,456565454,456565456,456565654,456565656,456565676,456565678,456567654,456567656,456567676,456567678,456567876,456567878,456567898,456765432,456765434,456765454,456765456,456765654,456765656,456765676,456765678,456767654,456767656,456767676,456767678,456767876,456767878,456767898,456787654,456787656,456787676,456787678,456787876,456787878,456787898,456789876,456789878,456789898,543210101,543210121,543210123,543212101,543212121,543212123,543212321,543212323,543212343,543212345,543232101,543232121,543232123,543232321,543232323,543232343,543232345,543234321,543234323,543234343,543234345,543234543,543234545,543234565,543234567,543432101,543432121,543432123,543432321,543432323,543432343,543432345,543434321,543434323,543434343,543434345,543434543,543434545,543434565,543434567,543454321,543454323,543454343,543454345,543454543,543454545,543454565,543454567,543456543,543456545,543456565,543456567,543456765,543456767,543456787,543456789,545432101,545432121,545432123,545432321,545432323,545432343,545432345,545434321,545434323,545434343,545434345,545434543,545434545,545434565,545434567,545454321,545454323,545454343,545454345,545454543,545454545,545454565,545454567,545456543,545456545,545456565,545456567,545456765,545456767,545456787,545456789,545654321,545654323,545654343,545654345,545654543,545654545,545654565,545654567,545656543,545656545,545656565,545656567,545656765,545656767,545656787,545656789,545676543,545676545,545676565,545676567,545676765,545676767,545676787,545676789,545678765,545678767,545678787,545678789,545678987,545678989,565432101,565432121,565432123,565432321,565432323,565432343,565432345,565434321,565434323,565434343,565434345,565434543,565434545,565434565,565434567,565454321,565454323,565454343,565454345,565454543,565454545,565454565,565454567,565456543,565456545,565456565,565456567,565456765,565456767,565456787,565456789,565654321,565654323,565654343,565654345,565654543,565654545,565654565,565654567,565656543,565656545,565656565,565656567,565656765,565656767,565656787,565656789,565676543,565676545,565676565,565676567,565676765,565676767,565676787,565676789,565678765,565678767,565678787,565678789,565678987,565678989,567654321,567654323,567654343,567654345,567654543,567654545,567654565,567654567,567656543,567656545,567656565,567656567,567656765,567656767,567656787,567656789,567676543,567676545,567676565,567676567,567676765,567676767,567676787,567676789,567678765,567678767,567678787,567678789,567678987,567678989,567876543,567876545,567876565,567876567,567876765,567876767,567876787,567876789,567878765,567878767,567878787,567878789,567878987,567878989,567898765,567898767,567898787,567898789,567898987,567898989,654321010,654321012,654321210,654321212,654321232,654321234,654323210,654323212,654323232,654323234,654323432,654323434,654323454,654323456,654343210,654343212,654343232,654343234,654343432,654343434,654343454,654343456,654345432,654345434,654345454,654345456,654345654,654345656,654345676,654345678,654543210,654543212,654543232,654543234,654543432,654543434,654543454,654543456,654545432,654545434,654545454,654545456,654545654,654545656,654545676,654545678,654565432,654565434,654565454,654565456,654565654,654565656,654565676,654565678,654567654,654567656,654567676,654567678,654567876,654567878,654567898,656543210,656543212,656543232,656543234,656543432,656543434,656543454,656543456,656545432,656545434,656545454,656545456,656545654,656545656,656545676,656545678,656565432,656565434,656565454,656565456,656565654,656565656,656565676,656565678,656567654,656567656,656567676,656567678,656567876,656567878,656567898,656765432,656765434,656765454,656765456,656765654,656765656,656765676,656765678,656767654,656767656,656767676,656767678,656767876,656767878,656767898,656787654,656787656,656787676,656787678,656787876,656787878,656787898,656789876,656789878,656789898,676543210,676543212,676543232,676543234,676543432,676543434,676543454,676543456,676545432,676545434,676545454,676545456,676545654,676545656,676545676,676545678,676565432,676565434,676565454,676565456,676565654,676565656,676565676,676565678,676567654,676567656,676567676,676567678,676567876,676567878,676567898,676765432,676765434,676765454,676765456,676765654,676765656,676765676,676765678,676767654,676767656,676767676,676767678,676767876,676767878,676767898,676787654,676787656,676787676,676787678,676787876,676787878,676787898,676789876,676789878,676789898,678765432,678765434,678765454,678765456,678765654,678765656,678765676,678765678,678767654,678767656,678767676,678767678,678767876,678767878,678767898,678787654,678787656,678787676,678787678,678787876,678787878,678787898,678789876,678789878,678789898,678987654,678987656,678987676,678987678,678987876,678987878,678987898,678989876,678989878,678989898,765432101,765432121,765432123,765432321,765432323,765432343,765432345,765434321,765434323,765434343,765434345,765434543,765434545,765434565,765434567,765454321,765454323,765454343,765454345,765454543,765454545,765454565,765454567,765456543,765456545,765456565,765456567,765456765,765456767,765456787,765456789,765654321,765654323,765654343,765654345,765654543,765654545,765654565,765654567,765656543,765656545,765656565,765656567,765656765,765656767,765656787,765656789,765676543,765676545,765676565,765676567,765676765,765676767,765676787,765676789,765678765,765678767,765678787,765678789,765678987,765678989,767654321,767654323,767654343,767654345,767654543,767654545,767654565,767654567,767656543,767656545,767656565,767656567,767656765,767656767,767656787,767656789,767676543,767676545,767676565,767676567,767676765,767676767,767676787,767676789,767678765,767678767,767678787,767678789,767678987,767678989,767876543,767876545,767876565,767876567,767876765,767876767,767876787,767876789,767878765,767878767,767878787,767878789,767878987,767878989,767898765,767898767,767898787,767898789,767898987,767898989,787654321,787654323,787654343,787654345,787654543,787654545,787654565,787654567,787656543,787656545,787656565,787656567,787656765,787656767,787656787,787656789,787676543,787676545,787676565,787676567,787676765,787676767,787676787,787676789,787678765,787678767,787678787,787678789,787678987,787678989,787876543,787876545,787876565,787876567,787876765,787876767,787876787,787876789,787878765,787878767,787878787,787878789,787878987,787878989,787898765,787898767,787898787,787898789,787898987,787898989,789876543,789876545,789876565,789876567,789876765,789876767,789876787,789876789,789878765,789878767,789878787,789878789,789878987,789878989,789898765,789898767,789898787,789898789,789898987,789898989,876543210,876543212,876543232,876543234,876543432,876543434,876543454,876543456,876545432,876545434,876545454,876545456,876545654,876545656,876545676,876545678,876565432,876565434,876565454,876565456,876565654,876565656,876565676,876565678,876567654,876567656,876567676,876567678,876567876,876567878,876567898,876765432,876765434,876765454,876765456,876765654,876765656,876765676,876765678,876767654,876767656,876767676,876767678,876767876,876767878,876767898,876787654,876787656,876787676,876787678,876787876,876787878,876787898,876789876,876789878,876789898,878765432,878765434,878765454,878765456,878765654,878765656,878765676,878765678,878767654,878767656,878767676,878767678,878767876,878767878,878767898,878787654,878787656,878787676,878787678,878787876,878787878,878787898,878789876,878789878,878789898,878987654,878987656,878987676,878987678,878987876,878987878,878987898,878989876,878989878,878989898,898765432,898765434,898765454,898765456,898765654,898765656,898765676,898765678,898767654,898767656,898767676,898767678,898767876,898767878,898767898,898787654,898787656,898787676,898787678,898787876,898787878,898787898,898789876,898789878,898789898,898987654,898987656,898987676,898987678,898987876,898987878,898987898,898989876,898989878,898989898,987654321,987654323,987654343,987654345,987654543,987654545,987654565,987654567,987656543,987656545,987656565,987656567,987656765,987656767,987656787,987656789,987676543,987676545,987676565,987676567,987676765,987676767,987676787,987676789,987678765,987678767,987678787,987678789,987678987,987678989,987876543,987876545,987876565,987876567,987876765,987876767,987876787,987876789,987878765,987878767,987878787,987878789,987878987,987878989,987898765,987898767,987898787,987898789,987898987,987898989,989876543,989876545,989876565,989876567,989876765,989876767,989876787,989876789,989878765,989878767,989878787,989878789,989878987,989878989,989898765,989898767,989898787,989898789,989898987,989898989,1010101010,1010101012,1010101210,1010101212,1010101232,1010101234,1010121010,1010121012,1010121210,1010121212,1010121232,1010121234,1010123210,1010123212,1010123232,1010123234,1010123432,1010123434,1010123454,1010123456,1012101010,1012101012,1012101210,1012101212,1012101232,1012101234,1012121010,1012121012,1012121210,1012121212,1012121232,1012121234,1012123210,1012123212,1012123232,1012123234,1012123432,1012123434,1012123454,1012123456,1012321010,1012321012,1012321210,1012321212,1012321232,1012321234,1012323210,1012323212,1012323232,1012323234,1012323432,1012323434,1012323454,1012323456,1012343210,1012343212,1012343232,1012343234,1012343432,1012343434,1012343454,1012343456,1012345432,1012345434,1012345454,1012345456,1012345654,1012345656,1012345676,1012345678,1210101010,1210101012,1210101210,1210101212,1210101232,1210101234,1210121010,1210121012,1210121210,1210121212,1210121232,1210121234,1210123210,1210123212,1210123232,1210123234,1210123432,1210123434,1210123454,1210123456,1212101010,1212101012,1212101210,1212101212,1212101232,1212101234,1212121010,1212121012,1212121210,1212121212,1212121232,1212121234,1212123210,1212123212,1212123232,1212123234,1212123432,1212123434,1212123454,1212123456,1212321010,1212321012,1212321210,1212321212,1212321232,1212321234,1212323210,1212323212,1212323232,1212323234,1212323432,1212323434,1212323454,1212323456,1212343210,1212343212,1212343232,1212343234,1212343432,1212343434,1212343454,1212343456,1212345432,1212345434,1212345454,1212345456,1212345654,1212345656,1212345676,1212345678,1232101010,1232101012,1232101210,1232101212,1232101232,1232101234,1232121010,1232121012,1232121210,1232121212,1232121232,1232121234,1232123210,1232123212,1232123232,1232123234,1232123432,1232123434,1232123454,1232123456,1232321010,1232321012,1232321210,1232321212,1232321232,1232321234,1232323210,1232323212,1232323232,1232323234,1232323432,1232323434,1232323454,1232323456,1232343210,1232343212,1232343232,1232343234,1232343432,1232343434,1232343454,1232343456,1232345432,1232345434,1232345454,1232345456,1232345654,1232345656,1232345676,1232345678,1234321010,1234321012,1234321210,1234321212,1234321232,1234321234,1234323210,1234323212,1234323232,1234323234,1234323432,1234323434,1234323454,1234323456,1234343210,1234343212,1234343232,1234343234,1234343432,1234343434,1234343454,1234343456,1234345432,1234345434,1234345454,1234345456,1234345654,1234345656,1234345676,1234345678,1234543210,1234543212,1234543232,1234543234,1234543432,1234543434,1234543454,1234543456,1234545432,1234545434,1234545454,1234545456,1234545654,1234545656,1234545676,1234545678,1234565432,1234565434,1234565454,1234565456,1234565654,1234565656,1234565676,1234565678,1234567654,1234567656,1234567676,1234567678,1234567876,1234567878,1234567898};
//        System.out.println(ints.length);
//
//        int[] ints1 = {0,1,2,3,4,5,6,7,8,9,10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98,101,121,123,210,212,232,234,321,323,343,345,432,434,454,456,543,545,565,567,654,656,676,678,765,767,787,789,876,878,898,987,989,1010,1012,1210,1212,1232,1234,2101,2121,2123,2321,2323,2343,2345,3210,3212,3232,3234,3432,3434,3454,3456,4321,4323,4343,4345,4543,4545,4565,4567,5432,5434,5454,5456,5654,5656,5676,5678,6543,6545,6565,6567,6765,6767,6787,6789,7654,7656,7676,7678,7876,7878,7898,8765,8767,8787,8789,8987,8989,9876,9878,9898,10101,10121,10123,12101,12121,12123,12321,12323,12343,12345,21010,21012,21210,21212,21232,21234,23210,23212,23232,23234,23432,23434,23454,23456,32101,32121,32123,32321,32323,32343,32345,34321,34323,34343,34345,34543,34545,34565,34567,43210,43212,43232,43234,43432,43434,43454,43456,45432,45434,45454,45456,45654,45656,45676,45678,54321,54323,54343,54345,54543,54545,54565,54567,56543,56545,56565,56567,56765,56767,56787,56789,65432,65434,65454,65456,65654,65656,65676,65678,67654,67656,67676,67678,67876,67878,67898,76543,76545,76565,76567,76765,76767,76787,76789,78765,78767,78787,78789,78987,78989,87654,87656,87676,87678,87876,87878,87898,89876,89878,89898,98765,98767,98787,98789,98987,98989,101010,101012,101210,101212,101232,101234,121010,121012,121210,121212,121232,121234,123210,123212,123232,123234,123432,123434,123454,123456,210101,210121,210123,212101,212121,212123,212321,212323,212343,212345,232101,232121,232123,232321,232323,232343,232345,234321,234323,234343,234345,234543,234545,234565,234567,321010,321012,321210,321212,321232,321234,323210,323212,323232,323234,323432,323434,323454,323456,343210,343212,343232,343234,343432,343434,343454,343456,345432,345434,345454,345456,345654,345656,345676,345678,432101,432121,432123,432321,432323,432343,432345,434321,434323,434343,434345,434543,434545,434565,434567,454321,454323,454343,454345,454543,454545,454565,454567,456543,456545,456565,456567,456765,456767,456787,456789,543210,543212,543232,543234,543432,543434,543454,543456,545432,545434,545454,545456,545654,545656,545676,545678,565432,565434,565454,565456,565654,565656,565676,565678,567654,567656,567676,567678,567876,567878,567898,654321,654323,654343,654345,654543,654545,654565,654567,656543,656545,656565,656567,656765,656767,656787,656789,676543,676545,676565,676567,676765,676767,676787,676789,678765,678767,678787,678789,678987,678989,765432,765434,765454,765456,765654,765656,765676,765678,767654,767656,767676,767678,767876,767878,767898,787654,787656,787676,787678,787876,787878,787898,789876,789878,789898,876543,876545,876565,876567,876765,876767,876787,876789,878765,878767,878787,878789,878987,878989,898765,898767,898787,898789,898987,898989,987654,987656,987676,987678,987876,987878,987898,989876,989878,989898,1010101,1010121,1010123,1012101,1012121,1012123,1012321,1012323,1012343,1012345,1210101,1210121,1210123,1212101,1212121,1212123,1212321,1212323,1212343,1212345,1232101,1232121,1232123,1232321,1232323,1232343,1232345,1234321,1234323,1234343,1234345,1234543,1234545,1234565,1234567,2101010,2101012,2101210,2101212,2101232,2101234,2121010,2121012,2121210,2121212,2121232,2121234,2123210,2123212,2123232,2123234,2123432,2123434,2123454,2123456,2321010,2321012,2321210,2321212,2321232,2321234,2323210,2323212,2323232,2323234,2323432,2323434,2323454,2323456,2343210,2343212,2343232,2343234,2343432,2343434,2343454,2343456,2345432,2345434,2345454,2345456,2345654,2345656,2345676,2345678,3210101,3210121,3210123,3212101,3212121,3212123,3212321,3212323,3212343,3212345,3232101,3232121,3232123,3232321,3232323,3232343,3232345,3234321,3234323,3234343,3234345,3234543,3234545,3234565,3234567,3432101,3432121,3432123,3432321,3432323,3432343,3432345,3434321,3434323,3434343,3434345,3434543,3434545,3434565,3434567,3454321,3454323,3454343,3454345,3454543,3454545,3454565,3454567,3456543,3456545,3456565,3456567,3456765,3456767,3456787,3456789,4321010,4321012,4321210,4321212,4321232,4321234,4323210,4323212,4323232,4323234,4323432,4323434,4323454,4323456,4343210,4343212,4343232,4343234,4343432,4343434,4343454,4343456,4345432,4345434,4345454,4345456,4345654,4345656,4345676,4345678,4543210,4543212,4543232,4543234,4543432,4543434,4543454,4543456,4545432,4545434,4545454,4545456,4545654,4545656,4545676,4545678,4565432,4565434,4565454,4565456,4565654,4565656,4565676,4565678,4567654,4567656,4567676,4567678,4567876,4567878,4567898,5432101,5432121,5432123,5432321,5432323,5432343,5432345,5434321,5434323,5434343,5434345,5434543,5434545,5434565,5434567,5454321,5454323,5454343,5454345,5454543,5454545,5454565,5454567,5456543,5456545,5456565,5456567,5456765,5456767,5456787,5456789,5654321,5654323,5654343,5654345,5654543,5654545,5654565,5654567,5656543,5656545,5656565,5656567,5656765,5656767,5656787,5656789,5676543,5676545,5676565,5676567,5676765,5676767,5676787,5676789,5678765,5678767,5678787,5678789,5678987,5678989,6543210,6543212,6543232,6543234,6543432,6543434,6543454,6543456,6545432,6545434,6545454,6545456,6545654,6545656,6545676,6545678,6565432,6565434,6565454,6565456,6565654,6565656,6565676,6565678,6567654,6567656,6567676,6567678,6567876,6567878,6567898,6765432,6765434,6765454,6765456,6765654,6765656,6765676,6765678,6767654,6767656,6767676,6767678,6767876,6767878,6767898,6787654,6787656,6787676,6787678,6787876,6787878,6787898,6789876,6789878,6789898,7654321,7654323,7654343,7654345,7654543,7654545,7654565,7654567,7656543,7656545,7656565,7656567,7656765,7656767,7656787,7656789,7676543,7676545,7676565,7676567,7676765,7676767,7676787,7676789,7678765,7678767,7678787,7678789,7678987,7678989,7876543,7876545,7876565,7876567,7876765,7876767,7876787,7876789,7878765,7878767,7878787,7878789,7878987,7878989,7898765,7898767,7898787,7898789,7898987,7898989,8765432,8765434,8765454,8765456,8765654,8765656,8765676,8765678,8767654,8767656,8767676,8767678,8767876,8767878,8767898,8787654,8787656,8787676,8787678,8787876,8787878,8787898,8789876,8789878,8789898,8987654,8987656,8987676,8987678,8987876,8987878,8987898,8989876,8989878,8989898,9876543,9876545,9876565,9876567,9876765,9876767,9876787,9876789,9878765,9878767,9878787,9878789,9878987,9878989,9898765,9898767,9898787,9898789,9898987,9898989,10101010,10101012,10101210,10101212,10101232,10101234,10121010,10121012,10121210,10121212,10121232,10121234,10123210,10123212,10123232,10123234,10123432,10123434,10123454,10123456,12101010,12101012,12101210,12101212,12101232,12101234,12121010,12121012,12121210,12121212,12121232,12121234,12123210,12123212,12123232,12123234,12123432,12123434,12123454,12123456,12321010,12321012,12321210,12321212,12321232,12321234,12323210,12323212,12323232,12323234,12323432,12323434,12323454,12323456,12343210,12343212,12343232,12343234,12343432,12343434,12343454,12343456,12345432,12345434,12345454,12345456,12345654,12345656,12345676,12345678,21010101,21010121,21010123,21012101,21012121,21012123,21012321,21012323,21012343,21012345,21210101,21210121,21210123,21212101,21212121,21212123,21212321,21212323,21212343,21212345,21232101,21232121,21232123,21232321,21232323,21232343,21232345,21234321,21234323,21234343,21234345,21234543,21234545,21234565,21234567,23210101,23210121,23210123,23212101,23212121,23212123,23212321,23212323,23212343,23212345,23232101,23232121,23232123,23232321,23232323,23232343,23232345,23234321,23234323,23234343,23234345,23234543,23234545,23234565,23234567,23432101,23432121,23432123,23432321,23432323,23432343,23432345,23434321,23434323,23434343,23434345,23434543,23434545,23434565,23434567,23454321,23454323,23454343,23454345,23454543,23454545,23454565,23454567,23456543,23456545,23456565,23456567,23456765,23456767,23456787,23456789,32101010,32101012,32101210,32101212,32101232,32101234,32121010,32121012,32121210,32121212,32121232,32121234,32123210,32123212,32123232,32123234,32123432,32123434,32123454,32123456,32321010,32321012,32321210,32321212,32321232,32321234,32323210,32323212,32323232,32323234,32323432,32323434,32323454,32323456,32343210,32343212,32343232,32343234,32343432,32343434,32343454,32343456,32345432,32345434,32345454,32345456,32345654,32345656,32345676,32345678,34321010,34321012,34321210,34321212,34321232,34321234,34323210,34323212,34323232,34323234,34323432,34323434,34323454,34323456,34343210,34343212,34343232,34343234,34343432,34343434,34343454,34343456,34345432,34345434,34345454,34345456,34345654,34345656,34345676,34345678,34543210,34543212,34543232,34543234,34543432,34543434,34543454,34543456,34545432,34545434,34545454,34545456,34545654,34545656,34545676,34545678,34565432,34565434,34565454,34565456,34565654,34565656,34565676,34565678,34567654,34567656,34567676,34567678,34567876,34567878,34567898,43210101,43210121,43210123,43212101,43212121,43212123,43212321,43212323,43212343,43212345,43232101,43232121,43232123,43232321,43232323,43232343,43232345,43234321,43234323,43234343,43234345,43234543,43234545,43234565,43234567,43432101,43432121,43432123,43432321,43432323,43432343,43432345,43434321,43434323,43434343,43434345,43434543,43434545,43434565,43434567,43454321,43454323,43454343,43454345,43454543,43454545,43454565,43454567,43456543,43456545,43456565,43456567,43456765,43456767,43456787,43456789,45432101,45432121,45432123,45432321,45432323,45432343,45432345,45434321,45434323,45434343,45434345,45434543,45434545,45434565,45434567,45454321,45454323,45454343,45454345,45454543,45454545,45454565,45454567,45456543,45456545,45456565,45456567,45456765,45456767,45456787,45456789,45654321,45654323,45654343,45654345,45654543,45654545,45654565,45654567,45656543,45656545,45656565,45656567,45656765,45656767,45656787,45656789,45676543,45676545,45676565,45676567,45676765,45676767,45676787,45676789,45678765,45678767,45678787,45678789,45678987,45678989,54321010,54321012,54321210,54321212,54321232,54321234,54323210,54323212,54323232,54323234,54323432,54323434,54323454,54323456,54343210,54343212,54343232,54343234,54343432,54343434,54343454,54343456,54345432,54345434,54345454,54345456,54345654,54345656,54345676,54345678,54543210,54543212,54543232,54543234,54543432,54543434,54543454,54543456,54545432,54545434,54545454,54545456,54545654,54545656,54545676,54545678,54565432,54565434,54565454,54565456,54565654,54565656,54565676,54565678,54567654,54567656,54567676,54567678,54567876,54567878,54567898,56543210,56543212,56543232,56543234,56543432,56543434,56543454,56543456,56545432,56545434,56545454,56545456,56545654,56545656,56545676,56545678,56565432,56565434,56565454,56565456,56565654,56565656,56565676,56565678,56567654,56567656,56567676,56567678,56567876,56567878,56567898,56765432,56765434,56765454,56765456,56765654,56765656,56765676,56765678,56767654,56767656,56767676,56767678,56767876,56767878,56767898,56787654,56787656,56787676,56787678,56787876,56787878,56787898,56789876,56789878,56789898,65432101,65432121,65432123,65432321,65432323,65432343,65432345,65434321,65434323,65434343,65434345,65434543,65434545,65434565,65434567,65454321,65454323,65454343,65454345,65454543,65454545,65454565,65454567,65456543,65456545,65456565,65456567,65456765,65456767,65456787,65456789,65654321,65654323,65654343,65654345,65654543,65654545,65654565,65654567,65656543,65656545,65656565,65656567,65656765,65656767,65656787,65656789,65676543,65676545,65676565,65676567,65676765,65676767,65676787,65676789,65678765,65678767,65678787,65678789,65678987,65678989,67654321,67654323,67654343,67654345,67654543,67654545,67654565,67654567,67656543,67656545,67656565,67656567,67656765,67656767,67656787,67656789,67676543,67676545,67676565,67676567,67676765,67676767,67676787,67676789,67678765,67678767,67678787,67678789,67678987,67678989,67876543,67876545,67876565,67876567,67876765,67876767,67876787,67876789,67878765,67878767,67878787,67878789,67878987,67878989,67898765,67898767,67898787,67898789,67898987,67898989,76543210,76543212,76543232,76543234,76543432,76543434,76543454,76543456,76545432,76545434,76545454,76545456,76545654,76545656,76545676,76545678,76565432,76565434,76565454,76565456,76565654,76565656,76565676,76565678,76567654,76567656,76567676,76567678,76567876,76567878,76567898,76765432,76765434,76765454,76765456,76765654,76765656,76765676,76765678,76767654,76767656,76767676,76767678,76767876,76767878,76767898,76787654,76787656,76787676,76787678,76787876,76787878,76787898,76789876,76789878,76789898,78765432,78765434,78765454,78765456,78765654,78765656,78765676,78765678,78767654,78767656,78767676,78767678,78767876,78767878,78767898,78787654,78787656,78787676,78787678,78787876,78787878,78787898,78789876,78789878,78789898,78987654,78987656,78987676,78987678,78987876,78987878,78987898,78989876,78989878,78989898,87654321,87654323,87654343,87654345,87654543,87654545,87654565,87654567,87656543,87656545,87656565,87656567,87656765,87656767,87656787,87656789,87676543,87676545,87676565,87676567,87676765,87676767,87676787,87676789,87678765,87678767,87678787,87678789,87678987,87678989,87876543,87876545,87876565,87876567,87876765,87876767,87876787,87876789,87878765,87878767,87878787,87878789,87878987,87878989,87898765,87898767,87898787,87898789,87898987,87898989,89876543,89876545,89876565,89876567,89876765,89876767,89876787,89876789,89878765,89878767,89878787,89878789,89878987,89878989,89898765,89898767,89898787,89898789,89898987,89898989,98765432,98765434,98765454,98765456,98765654,98765656,98765676,98765678,98767654,98767656,98767676,98767678,98767876,98767878,98767898,98787654,98787656,98787676,98787678,98787876,98787878,98787898,98789876,98789878,98789898,98987654,98987656,98987676,98987678,98987876,98987878,98987898,98989876,98989878,98989898,101010101,101010121,101010123,101012101,101012121,101012123,101012321,101012323,101012343,101012345,101210101,101210121,101210123,101212101,101212121,101212123,101212321,101212323,101212343,101212345,101232101,101232121,101232123,101232321,101232323,101232343,101232345,101234321,101234323,101234343,101234345,101234543,101234545,101234565,101234567,121010101,121010121,121010123,121012101,121012121,121012123,121012321,121012323,121012343,121012345,121210101,121210121,121210123,121212101,121212121,121212123,121212321,121212323,121212343,121212345,121232101,121232121,121232123,121232321,121232323,121232343,121232345,121234321,121234323,121234343,121234345,121234543,121234545,121234565,121234567,123210101,123210121,123210123,123212101,123212121,123212123,123212321,123212323,123212343,123212345,123232101,123232121,123232123,123232321,123232323,123232343,123232345,123234321,123234323,123234343,123234345,123234543,123234545,123234565,123234567,123432101,123432121,123432123,123432321,123432323,123432343,123432345,123434321,123434323,123434343,123434345,123434543,123434545,123434565,123434567,123454321,123454323,123454343,123454345,123454543,123454545,123454565,123454567,123456543,123456545,123456565,123456567,123456765,123456767,123456787,123456789,210101010,210101012,210101210,210101212,210101232,210101234,210121010,210121012,210121210,210121212,210121232,210121234,210123210,210123212,210123232,210123234,210123432,210123434,210123454,210123456,212101010,212101012,212101210,212101212,212101232,212101234,212121010,212121012,212121210,212121212,212121232,212121234,212123210,212123212,212123232,212123234,212123432,212123434,212123454,212123456,212321010,212321012,212321210,212321212,212321232,212321234,212323210,212323212,212323232,212323234,212323432,212323434,212323454,212323456,212343210,212343212,212343232,212343234,212343432,212343434,212343454,212343456,212345432,212345434,212345454,212345456,212345654,212345656,212345676,212345678,232101010,232101012,232101210,232101212,232101232,232101234,232121010,232121012,232121210,232121212,232121232,232121234,232123210,232123212,232123232,232123234,232123432,232123434,232123454,232123456,232321010,232321012,232321210,232321212,232321232,232321234,232323210,232323212,232323232,232323234,232323432,232323434,232323454,232323456,232343210,232343212,232343232,232343234,232343432,232343434,232343454,232343456,232345432,232345434,232345454,232345456,232345654,232345656,232345676,232345678,234321010,234321012,234321210,234321212,234321232,234321234,234323210,234323212,234323232,234323234,234323432,234323434,234323454,234323456,234343210,234343212,234343232,234343234,234343432,234343434,234343454,234343456,234345432,234345434,234345454,234345456,234345654,234345656,234345676,234345678,234543210,234543212,234543232,234543234,234543432,234543434,234543454,234543456,234545432,234545434,234545454,234545456,234545654,234545656,234545676,234545678,234565432,234565434,234565454,234565456,234565654,234565656,234565676,234565678,234567654,234567656,234567676,234567678,234567876,234567878,234567898,321010101,321010121,321010123,321012101,321012121,321012123,321012321,321012323,321012343,321012345,321210101,321210121,321210123,321212101,321212121,321212123,321212321,321212323,321212343,321212345,321232101,321232121,321232123,321232321,321232323,321232343,321232345,321234321,321234323,321234343,321234345,321234543,321234545,321234565,321234567,323210101,323210121,323210123,323212101,323212121,323212123,323212321,323212323,323212343,323212345,323232101,323232121,323232123,323232321,323232323,323232343,323232345,323234321,323234323,323234343,323234345,323234543,323234545,323234565,323234567,323432101,323432121,323432123,323432321,323432323,323432343,323432345,323434321,323434323,323434343,323434345,323434543,323434545,323434565,323434567,323454321,323454323,323454343,323454345,323454543,323454545,323454565,323454567,323456543,323456545,323456565,323456567,323456765,323456767,323456787,323456789,343210101,343210121,343210123,343212101,343212121,343212123,343212321,343212323,343212343,343212345,343232101,343232121,343232123,343232321,343232323,343232343,343232345,343234321,343234323,343234343,343234345,343234543,343234545,343234565,343234567,343432101,343432121,343432123,343432321,343432323,343432343,343432345,343434321,343434323,343434343,343434345,343434543,343434545,343434565,343434567,343454321,343454323,343454343,343454345,343454543,343454545,343454565,343454567,343456543,343456545,343456565,343456567,343456765,343456767,343456787,343456789,345432101,345432121,345432123,345432321,345432323,345432343,345432345,345434321,345434323,345434343,345434345,345434543,345434545,345434565,345434567,345454321,345454323,345454343,345454345,345454543,345454545,345454565,345454567,345456543,345456545,345456565,345456567,345456765,345456767,345456787,345456789,345654321,345654323,345654343,345654345,345654543,345654545,345654565,345654567,345656543,345656545,345656565,345656567,345656765,345656767,345656787,345656789,345676543,345676545,345676565,345676567,345676765,345676767,345676787,345676789,345678765,345678767,345678787,345678789,345678987,345678989,432101010,432101012,432101210,432101212,432101232,432101234,432121010,432121012,432121210,432121212,432121232,432121234,432123210,432123212,432123232,432123234,432123432,432123434,432123454,432123456,432321010,432321012,432321210,432321212,432321232,432321234,432323210,432323212,432323232,432323234,432323432,432323434,432323454,432323456,432343210,432343212,432343232,432343234,432343432,432343434,432343454,432343456,432345432,432345434,432345454,432345456,432345654,432345656,432345676,432345678,434321010,434321012,434321210,434321212,434321232,434321234,434323210,434323212,434323232,434323234,434323432,434323434,434323454,434323456,434343210,434343212,434343232,434343234,434343432,434343434,434343454,434343456,434345432,434345434,434345454,434345456,434345654,434345656,434345676,434345678,434543210,434543212,434543232,434543234,434543432,434543434,434543454,434543456,434545432,434545434,434545454,434545456,434545654,434545656,434545676,434545678,434565432,434565434,434565454,434565456,434565654,434565656,434565676,434565678,434567654,434567656,434567676,434567678,434567876,434567878,434567898,454321010,454321012,454321210,454321212,454321232,454321234,454323210,454323212,454323232,454323234,454323432,454323434,454323454,454323456,454343210,454343212,454343232,454343234,454343432,454343434,454343454,454343456,454345432,454345434,454345454,454345456,454345654,454345656,454345676,454345678,454543210,454543212,454543232,454543234,454543432,454543434,454543454,454543456,454545432,454545434,454545454,454545456,454545654,454545656,454545676,454545678,454565432,454565434,454565454,454565456,454565654,454565656,454565676,454565678,454567654,454567656,454567676,454567678,454567876,454567878,454567898,456543210,456543212,456543232,456543234,456543432,456543434,456543454,456543456,456545432,456545434,456545454,456545456,456545654,456545656,456545676,456545678,456565432,456565434,456565454,456565456,456565654,456565656,456565676,456565678,456567654,456567656,456567676,456567678,456567876,456567878,456567898,456765432,456765434,456765454,456765456,456765654,456765656,456765676,456765678,456767654,456767656,456767676,456767678,456767876,456767878,456767898,456787654,456787656,456787676,456787678,456787876,456787878,456787898,456789876,456789878,456789898,543210101,543210121,543210123,543212101,543212121,543212123,543212321,543212323,543212343,543212345,543232101,543232121,543232123,543232321,543232323,543232343,543232345,543234321,543234323,543234343,543234345,543234543,543234545,543234565,543234567,543432101,543432121,543432123,543432321,543432323,543432343,543432345,543434321,543434323,543434343,543434345,543434543,543434545,543434565,543434567,543454321,543454323,543454343,543454345,543454543,543454545,543454565,543454567,543456543,543456545,543456565,543456567,543456765,543456767,543456787,543456789,545432101,545432121,545432123,545432321,545432323,545432343,545432345,545434321,545434323,545434343,545434345,545434543,545434545,545434565,545434567,545454321,545454323,545454343,545454345,545454543,545454545,545454565,545454567,545456543,545456545,545456565,545456567,545456765,545456767,545456787,545456789,545654321,545654323,545654343,545654345,545654543,545654545,545654565,545654567,545656543,545656545,545656565,545656567,545656765,545656767,545656787,545656789,545676543,545676545,545676565,545676567,545676765,545676767,545676787,545676789,545678765,545678767,545678787,545678789,545678987,545678989,565432101,565432121,565432123,565432321,565432323,565432343,565432345,565434321,565434323,565434343,565434345,565434543,565434545,565434565,565434567,565454321,565454323,565454343,565454345,565454543,565454545,565454565,565454567,565456543,565456545,565456565,565456567,565456765,565456767,565456787,565456789,565654321,565654323,565654343,565654345,565654543,565654545,565654565,565654567,565656543,565656545,565656565,565656567,565656765,565656767,565656787,565656789,565676543,565676545,565676565,565676567,565676765,565676767,565676787,565676789,565678765,565678767,565678787,565678789,565678987,565678989,567654321,567654323,567654343,567654345,567654543,567654545,567654565,567654567,567656543,567656545,567656565,567656567,567656765,567656767,567656787,567656789,567676543,567676545,567676565,567676567,567676765,567676767,567676787,567676789,567678765,567678767,567678787,567678789,567678987,567678989,567876543,567876545,567876565,567876567,567876765,567876767,567876787,567876789,567878765,567878767,567878787,567878789,567878987,567878989,567898765,567898767,567898787,567898789,567898987,567898989,654321010,654321012,654321210,654321212,654321232,654321234,654323210,654323212,654323232,654323234,654323432,654323434,654323454,654323456,654343210,654343212,654343232,654343234,654343432,654343434,654343454,654343456,654345432,654345434,654345454,654345456,654345654,654345656,654345676,654345678,654543210,654543212,654543232,654543234,654543432,654543434,654543454,654543456,654545432,654545434,654545454,654545456,654545654,654545656,654545676,654545678,654565432,654565434,654565454,654565456,654565654,654565656,654565676,654565678,654567654,654567656,654567676,654567678,654567876,654567878,654567898,656543210,656543212,656543232,656543234,656543432,656543434,656543454,656543456,656545432,656545434,656545454,656545456,656545654,656545656,656545676,656545678,656565432,656565434,656565454,656565456,656565654,656565656,656565676,656565678,656567654,656567656,656567676,656567678,656567876,656567878,656567898,656765432,656765434,656765454,656765456,656765654,656765656,656765676,656765678,656767654,656767656,656767676,656767678,656767876,656767878,656767898,656787654,656787656,656787676,656787678,656787876,656787878,656787898,656789876,656789878,656789898,676543210,676543212,676543232,676543234,676543432,676543434,676543454,676543456,676545432,676545434,676545454,676545456,676545654,676545656,676545676,676545678,676565432,676565434,676565454,676565456,676565654,676565656,676565676,676565678,676567654,676567656,676567676,676567678,676567876,676567878,676567898,676765432,676765434,676765454,676765456,676765654,676765656,676765676,676765678,676767654,676767656,676767676,676767678,676767876,676767878,676767898,676787654,676787656,676787676,676787678,676787876,676787878,676787898,676789876,676789878,676789898,678765432,678765434,678765454,678765456,678765654,678765656,678765676,678765678,678767654,678767656,678767676,678767678,678767876,678767878,678767898,678787654,678787656,678787676,678787678,678787876,678787878,678787898,678789876,678789878,678789898,678987654,678987656,678987676,678987678,678987876,678987878,678987898,678989876,678989878,678989898,765432101,765432121,765432123,765432321,765432323,765432343,765432345,765434321,765434323,765434343,765434345,765434543,765434545,765434565,765434567,765454321,765454323,765454343,765454345,765454543,765454545,765454565,765454567,765456543,765456545,765456565,765456567,765456765,765456767,765456787,765456789,765654321,765654323,765654343,765654345,765654543,765654545,765654565,765654567,765656543,765656545,765656565,765656567,765656765,765656767,765656787,765656789,765676543,765676545,765676565,765676567,765676765,765676767,765676787,765676789,765678765,765678767,765678787,765678789,765678987,765678989,767654321,767654323,767654343,767654345,767654543,767654545,767654565,767654567,767656543,767656545,767656565,767656567,767656765,767656767,767656787,767656789,767676543,767676545,767676565,767676567,767676765,767676767,767676787,767676789,767678765,767678767,767678787,767678789,767678987,767678989,767876543,767876545,767876565,767876567,767876765,767876767,767876787,767876789,767878765,767878767,767878787,767878789,767878987,767878989,767898765,767898767,767898787,767898789,767898987,767898989,787654321,787654323,787654343,787654345,787654543,787654545,787654565,787654567,787656543,787656545,787656565,787656567,787656765,787656767,787656787,787656789,787676543,787676545,787676565,787676567,787676765,787676767,787676787,787676789,787678765,787678767,787678787,787678789,787678987,787678989,787876543,787876545,787876565,787876567,787876765,787876767,787876787,787876789,787878765,787878767,787878787,787878789,787878987,787878989,787898765,787898767,787898787,787898789,787898987,787898989,789876543,789876545,789876565,789876567,789876765,789876767,789876787,789876789,789878765,789878767,789878787,789878789,789878987,789878989,789898765,789898767,789898787,789898789,789898987,789898989,876543210,876543212,876543232,876543234,876543432,876543434,876543454,876543456,876545432,876545434,876545454,876545456,876545654,876545656,876545676,876545678,876565432,876565434,876565454,876565456,876565654,876565656,876565676,876565678,876567654,876567656,876567676,876567678,876567876,876567878,876567898,876765432,876765434,876765454,876765456,876765654,876765656,876765676,876765678,876767654,876767656,876767676,876767678,876767876,876767878,876767898,876787654,876787656,876787676,876787678,876787876,876787878,876787898,876789876,876789878,876789898,878765432,878765434,878765454,878765456,878765654,878765656,878765676,878765678,878767654,878767656,878767676,878767678,878767876,878767878,878767898,878787654,878787656,878787676,878787678,878787876,878787878,878787898,878789876,878789878,878789898,878987654,878987656,878987676,878987678,878987876,878987878,878987898,878989876,878989878,878989898,898765432,898765434,898765454,898765456,898765654,898765656,898765676,898765678,898767654,898767656,898767676,898767678,898767876,898767878,898767898,898787654,898787656,898787676,898787678,898787876,898787878,898787898,898789876,898789878,898789898,898987654,898987656,898987676,898987678,898987876,898987878,898987898,898989876,898989878,898989898,987654321,987654323,987654343,987654345,987654543,987654545,987654565,987654567,987656543,987656545,987656565,987656567,987656765,987656767,987656787,987656789,987676543,987676545,987676565,987676567,987676765,987676767,987676787,987676789,987678765,987678767,987678787,987678789,987678987,987678989,987876543,987876545,987876565,987876567,987876765,987876767,987876787,987876789,987878765,987878767,987878787,987878789,987878987,987878989,987898765,987898767,987898787,987898789,987898987,987898989,989876543,989876545,989876565,989876567,989876765,989876767,989876787,989876789,989878765,989878767,989878787,989878789,989878987,989878989,989898765,989898767,989898787,989898789,989898987,989898989,1010101010,1010101012,1010101210,1010101212,1010101232,1010101234,1010121010,1010121012,1010121210,1010121212,1010121232,1010121234,1010123210,1010123212,1010123232,1010123234,1010123432,1010123434,1010123454,1010123456,1012101010,1012101012,1012101210,1012101212,1012101232,1012101234,1012121010,1012121012,1012121210,1012121212,1012121232,1012121234,1012123210,1012123212,1012123232,1012123234,1012123432,1012123434,1012123454,1012123456,1012321010,1012321012,1012321210,1012321212,1012321232,1012321234,1012323210,1012323212,1012323232,1012323234,1012323432,1012323434,1012323454,1012323456,1012343210,1012343212,1012343232,1012343234,1012343432,1012343434,1012343454,1012343456,1012345432,1012345434,1012345454,1012345456,1012345654,1012345656,1012345676,1012345678,1210101010,1210101012,1210101210,1210101212,1210101232,1210101234,1210121010,1210121012,1210121210,1210121212,1210121232,1210121234,1210123210,1210123212,1210123232,1210123234,1210123432,1210123434,1210123454,1210123456,1212101010,1212101012,1212101210,1212101212,1212101232,1212101234,1212121010,1212121012,1212121210,1212121212,1212121232,1212121234,1212123210,1212123212,1212123232,1212123234,1212123432,1212123434,1212123454,1212123456,1212321010,1212321012,1212321210,1212321212,1212321232,1212321234,1212323210,1212323212,1212323232,1212323234,1212323432,1212323434,1212323454,1212323456,1212343210,1212343212,1212343232,1212343234,1212343432,1212343434,1212343454,1212343456,1212345432,1212345434,1212345454,1212345456,1212345654,1212345656,1212345676,1212345678,1232101010,1232101012,1232101210,1232101212,1232101232,1232101234,1232121010,1232121012,1232121210,1232121212,1232121232,1232121234,1232123210,1232123212,1232123232,1232123234,1232123432,1232123434,1232123454,1232123456,1232321010,1232321012,1232321210,1232321212,1232321232,1232321234,1232323210,1232323212,1232323232,1232323234,1232323432,1232323434,1232323454,1232323456,1232343210,1232343212,1232343232,1232343234,1232343432,1232343434,1232343454,1232343456,1232345432,1232345434,1232345454,1232345456,1232345654,1232345656,1232345676,1232345678,1234321010,1234321012,1234321210,1234321212,1234321232,1234321234,1234323210,1234323212,1234323232,1234323234,1234323432,1234323434,1234323454,1234323456,1234343210,1234343212,1234343232,1234343234,1234343432,1234343434,1234343454,1234343456,1234345432,1234345434,1234345454,1234345456,1234345654,1234345656,1234345676,1234345678,1234543210,1234543212,1234543232,1234543234,1234543432,1234543434,1234543454,1234543456,1234545432,1234545434,1234545454,1234545456,1234545654,1234545656,1234545676,1234545678,1234565432,1234565434,1234565454,1234565456,1234565654,1234565656,1234565676,1234565678,1234567654,1234567656,1234567676,1234567678,1234567876,1234567878,1234567898};
//        System.out.println(ints1.length);



//            int i = longestValidParentheses(")()()(((((()");
//            System.out.println(i);

//            int k = search(new int[] {15,16,19,20,25,1,3,4,5,7,10,14}, 25);
//            System.out.print(k);
//        String str = "aacdefcaaywwkew";    //aacdefcaaywwkew
//        String result = longestPalindrome(str);
//        System.out.println(result);

//        String str = "LEETCODEISHIRING";
//        String result = convert(str, 4);
//        System.out.println(result);

//        String s = "-23523";
//        int i = Integer.valueOf(s);
//        System.out.println(i);

//        boolean f = isPalindrome(100021);
//        System.out.println(f);
//        int i = 3;
//
//        String str = "sdfdf";
//        char s = str.charAt(546);

//        int i = romanToInt("MDLXX");
//        System.out.println(i);

//        int[] ints = {2, 5, 3, 1, 8, 9, 6, 5};
//        Arrays.sort(ints);
//        List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
//        ArrayList<Integer> list1 = new ArrayList<>(list);
//        System.out.println(list);

//        List<List<Integer>> lists = threeSum(new int[]{-2, 0, 1, 1, 2});
//        System.out.println(lists);
//        List<String> stringList = letterCombinations("");
//        for (String str : stringList)
//            System.out.println(str);

//        List<List<Integer>> stringList = fourSum(new int[]{1,-2,-5,-4,-3,3,3,5}, -11);
//        for (List<Integer> integers : stringList)
//            System.out.println(integers);
//            ListNode node = new ListNode(1);
//            node.next = new ListNode(2);
//            node.next.next = new ListNode(3);
//            node.next.next.next = new ListNode(4);
//            node.next.next.next.next = new ListNode(5);
//            ListNode node1 = removeNthFromEnd(node, 2);
//
//            while (node1 != null) {
//                System.out.print(node.val + " , ");
//                node = node.next;
//            }
//        boolean flag = isValid("{[]}");
//        System.out.println(flag);

//        String str = "wessdc";
//        String sbu = str.substring(6, 6);
//        System.out.println(sbu);
//        List<String> list = generateParenthesis(4);
//        for (String str : list)
//            System.out.println(str);
//        ListNode node = new ListNode(1);
//            node.next = new ListNode(2);
//            node.next.next = new ListNode(3);
//            node.next.next.next = new ListNode(4);
//            node.next.next.next.next = new ListNode(5);
//        node.next.next.next.next.next = new ListNode(6);
//        node.next.next.next.next.next.next = new ListNode(7);
//
//
//        ListNode res = swapPairs(node);
//        while (res != null) {
//            System.out.println(res.val);
//            res = res.next;
//        }
//        int i = removeElement(new int[]{0,1,2,2,3,0,4,2}, 2);
//        System.out.println(i);
//        int as = divide(-2147483648, -2147483648);
//        System.out.println(as);

//        int[] ints = nextPermutation(new int[] {1, 2, 3, 5, 4, 3, 2, 1});
//        for (int i = 0; i < ints.length; i++)
//            System.out.print(ints[i] + " , ");

//        int[] ints = new int[]{7,6,5,5,2};
//        for (int i = 0; i < ints.length / 2; i++) {
//            int temp = ints[i];
//            ints[i] = ints[ints.length - 1 - i];
//            ints[ints.length - 1 - i] = temp;
//        }
//        for (int i : ints)
//            System.out.print(i + " , ");
//        int[] ints = searchRange(new int[]{}, 0);
//        System.out.println(ints[0] + " , " + ints[1]);
//        String str = countAndSay(8);
//        System.out.println(str);
//        combinationSum(new int[]{2, 3, 5}, 8);
//        for (List<Integer> list : listList) {
//            for (Integer integer : list) {
//                System.out.print(integer + " , ");
//            }
//            System.out.println();
//        }
            // 681692778  351251360
            // 2147483647
            //System.out.println(Integer.MAX_VALUE);

            //957747794
            //424238336
//        int k = findKthNumber(957747794, 424238336);
//        System.out.println(k);
//        int[][] items = {{1,91},{1,92},{2,93},{2,97},{1,60},{2,77},{1,65},{1,87},{1,100},{2,100},{2,76}};
//
//        int[][] result = highFive(items);
//
//        System.out.println(result);
//        int[] ints = {2, 1, 2, 4, 3};
//        ArrayList<Integer> list = nextGreaterElement(ints);
//        Collections.reverse(list);
//        System.out.println(list);
//        int[] ints = {1, 4, 3, 6, 8, 22, 13, 17, 16};
//        int[] ints1 = new int[ints.length * 2];
//        System.arraycopy(ints, 0, ints1, 0, ints.length);
//        System.arraycopy(ints, 0, ints1, ints.length, ints.length);
//        for (int i : ints1)
//            System.out.print(i + ", ");

//        int[] result = nextGreaterElements(ints);
//        for (int i : result)
//            System.out.print(i + ", ");
//        int i = nextGreaterElement(23792563);
//        System.out.print(i);
//        int i = maxProfit(new int[]{4, 5, 2, 4, 3, 3, 1, 2, 5}, 1);
//        System.out.print(i);
//        Node node1 = new Node(1, null, null, null);
//        Node node2 = new Node(2, null, null, null);
//        Node node3 = new Node(3, null, null, null);
//        Node node4 = new Node(4, null, null, null);
//        Node node5 = new Node(5, null, null, null);
//        Node node6 = new Node(6, null, null, null);
//        Node node7 = new Node(7, null, null, null);
//        Node node8 = new Node(8, null, null, null);
//        Node node9 = new Node(9, null, null, null);
//        Node node10 = new Node(10, null, null, null);
//        Node node11 = new Node(11, null, null, null);
//        Node node12 = new Node(12, null, null, null);
//        node1.next = node2;
//        node2.prev = node1;
//        node2.next = node3;
//        node3.prev = node2;
//        node3.next = node4;
//        node3.child = node7;
//        node4.prev = node3;
//        node4.next = node5;
//        node5.prev = node4;
//        node5.next = node6;
//        node6.prev = node5;
//        node7.prev = node3;
//        node7.next = node8;
//        node8.prev = node7;
//        node8.next = node9;
//        node8.child = node11;
//        node11.prev = node8;
//        node9.prev = node8;
//        node9.next = node10;
//        node10.prev = node9;
//        node11.next = node12;
//        node12.prev = node11;
//
//        getOrder(node1);
//
//        System.out.println();
//        Node result = flatten(node1);
//        getOrder(result);
//
////       ArrayList<Integer> list = test1();
////       System.out.println(list);
//        int[] ints = new int[] {1,8,6,2,5,4,8,3,7};   ,879,4234,123,546,33,12,546,78,23,13
//        int k = maxArea(ints);
//        System.out.println(k);
//       long i = 2;
////            long j = 2;
////            long k = 3;
////            long result = ((1 / i)  + j) + k;
////            System.out.println(result);
////            System.out.println(Integer.MAX_VALUE);         int[] ints = new int[] {432,123,546,76,12,547,879,4234,123,546,33}; //2783
//            int k = trap(ints);
//            System.out.println(k);
//
//        boolean  f = robot("URR", new int[][]{{4, 2}}, 3,2);
//        System.out.println(f);

//            int[][] ints = {{4,2}, {5,1}, {9,0}, {4,3}, {9,3}};
//            System.out.println(ints.length);
//            Arrays.sort(ints, new Comparator<int[]>() {
//                @Override
//                public int compare(int[] o1, int[] o2) {
//                    if (o1[0] >= o2[0])
//                        return 1;
//                    return -1;
//                }
//            });
//            for (int[] ints1: ints)
//                System.out.println(ints1[0] + ", " + ints1[1]);
//
//            System.out.println(ints[1][1]);
//            int[] ints = new int[3];
//            for (int i: ints)
//                System.out.println(i);

//            int n = 13;
//            int[][] leadership =  {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
//                    ,{3,8},{3,9},{8,10},{8,11}};
//
//            HashMap<Integer, HashSet<Integer>> leader = new HashMap<>();
//
//            for (int i = 1; i <= n; i++)
//                leader.put(i, new HashSet<>());
//
//            for (int i = 0; i < leadership.length; i++) {
//                HashSet<Integer> list = leader.get(leadership[i][0]);
//                list.add(leadership[i][1]);
//            }
//
//            for (int i = 1; i <= n; i++) {
//                if (leader.get(i).size() == 0)
//                    continue;
//                leader.get(i).addAll(getSubLeader(leader, leader.get(i)));
//            }

//            leader.get(1).addAll(getSubLeader(leader, leader.get(1)));
//
//            HashSet<Integer> l1 = leader.get(1);
//            System.out.println("aaa " + l1);

//            HashSet<Integer> set = leader.get(1);
//            System.out.println(set);

//            for (HashMap.Entry<Integer, HashSet<Integer>> entry: leader.entrySet()) {
//                System.out.println(entry.getKey());
//                HashSet<Integer> set = entry.getValue();
//                System.out.println(set);
//            }
//            int n = 13;
//            int[][] a1 = {{1, 2}, {1, 6}, {2, 3}, {2, 5}, {1, 4},{4,12}, {4,13}, {3,7}
//                    ,{3,8},{3,9},{8,10},{8,11}};
//            int[][] a2 = {{2,2,50},{1,1,111},{1,7,456},{3,5}};
//            int[] result = bonus(n, a1, a2);
//            for (int i: result)
//                System.out.print(i + ", ");
//            System.out.print((7 / 4) + 1);
//            int[] answers = new int[] {2,2,2,2};
//            int count = numRabbits(answers);
//            System.out.println(count);
//            HashMap<Integer, Integer> each = new HashMap<>();
//            for (int i: answers) {
//                Integer current = each.get(i);
//                each.put(i, current == null ? 1 : current + 1);
//            }
//            for (Map.Entry<Integer, Integer> entry: each.entrySet()) {
//                System.out.print(entry.getKey() + " : ");
//                System.out.print(entry.getValue());
//                System.out.println();
//            }
//            int k = totalFruit(new int[] {0,1,2,2});
//            System.out.println(k);
//            int k = equalSubstring("szrpjazjjhorzeiduufspm","rgwdrgligareauwihaqroy",55);
//            System.out.println(k);
//
//            int k1 = 'f' - 'b';
//            System.out.println(k1);
//            String result = removeDuplicates("yfttttfbbbbnnnnffbgffffgbbbbgssssgthyyyy", 4);
//            System.out.println(result);
//            double result = myPow(0, Integer.MIN_VALUE);
//            System.out.println(result);
//            char[] chars = {'a', 'b', 'c'};
//            char[] chars1 = {'c', 'b', 'a'};
//            Arrays.sort(chars1);
//            System.out.println(Arrays.equals(chars, chars1));
//
//            String str1 = "abc";
//            String str2 = "abc";
//            System.out.println(str1.hashCode());
//            System.out.println(str2.hashCode());
//
//            String[] strings = new String[]{"eat","tea","tan","ate","nat","bat"};
//            groupAnagrams(strings);
//
//            HashMap<String, Integer> map = new HashMap<>();
//            for (String str: strings) {
//                Integer i = map.get(str);
//                map.put(str, i == null ? 1 : ++i);
//            }
//            System.out.println("----");
//            for (Map.Entry<String, Integer> entry: map.entrySet())
//                System.out.println(entry.getKey());
//            Test test = new Test();
//            int[] nums = new int[]{1, 2, 3, 4};
//            List<List<Integer>> permute = test.permute(nums);
//            for (int i = 0; i < permute.size(); i++) {
//                System.out.println(permute.get(i));
//            }
//            char[][] chars = new char[][] {{'5','3','.','.','7','.','.','.','.'},
//                    {'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},
//                    {'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},
//                    {'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},
//                    {'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
//            System.out.println(isValidSudoku(chars));

//            Test test = new Test();
//            test.levelOrder(null);
//            TreeNode n3 = test.getter(3);
//            TreeNode n1 = test.getter(1);
//            TreeNode n5 = test.getter(5);
//            TreeNode n0 = test.getter(0);
//            TreeNode n2 = test.getter(2);
//            TreeNode n4 = test.getter(4);
//            TreeNode n6 = test.getter(6);
//            TreeNode n7 = test.getter(3);
//            n3.left = n1;
//            n3.right = n5;
//            n1.left = n0;
//            n1.right = n2;
//            n5.left = n4;
//            n5.right = n6;
//            n2.right = n7;
//            System.out.println(test.isValidBST(n3));
//
//            int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//            int[] candidates = new int[] {10,1,2,7,6,1,5};
//            List<List<Integer>> listList = test.combinationSum2(candidates, 8);
//            System.out.println(listList);
//        System.out.println(test.multiply("765", "4654"));
//        System.out.println(test.mySqrt(4));
//        int[] res;
//        int a = 31;
//        if (a == 3)
//            res = new int[10];
//        else
//            res = new int[20];
//        for (int i: res)
//            System.out.print(i);
//        int[] res = test.plusOne(new int[] {0});
//        for (int i: res)
//            System.out.print(i + " , ");
//        List<List<Integer>> lists = test.permute1(new int[] {1, 2, 3});
//        for (List<Integer> list: lists)
//            System.out.println(list);
//        System.out.println(test.firstMissingPositive(new int[] {7,8,9,10,13}));
//        System.out.println(test.getRow(3));

    }
}




