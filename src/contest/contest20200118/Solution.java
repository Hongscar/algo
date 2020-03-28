package contest.contest20200118;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 2:24 2020/1/19
 */

class Solution {
    public String q() {
        String str = "";
        str += "class Solution {\n\tpublic String q() {\n\t\tString str = \"\";\n\t\tstr += \"class Solution {\\n\\tpublic String q() {\\n\\t\\tString str = \\\"\\\";\\n\\t\\t\";\n\t\treturn str.substring(0, 136) + str.substring(59, 136) + str.substring(59, 136) + str.substring(136);\n\t}\n}";
        return str.substring(0, 136) + str.substring(59, 136) + str.substring(59, 136) + str.substring(136);
    }

    public boolean checkPossibility(int[] nums) {
        if (nums.length <= 2)
            return true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] <= nums[i + 1])
                continue;
            int temp = nums[i];
            nums[i] = nums[i + 1];
            boolean flag = helper (nums, i);
            if (flag)
                return true;
            nums[i] = temp;
            nums[i + 1] = temp;
            return helper(nums, i);
        }
        return true;
    }

    public boolean helper (int[] nums, int index) {
        if (nums.length - index <= 2)
            return true;
        if (index > 0 && nums[index] < nums[index - 1])
            return false;       // 防止改变前面出现的错误
        for (int i = index; i < nums.length - 1; i++) {
            if (nums[i] <= nums[i + 1])
                continue;
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.checkPossibility(new int[] {3, 4, 2, 3}));
//        String str = solution.q();
//        System.out.println(str);        // 0 - 136
        //System.out.println(str.substring(0, 136));
       // System.out.println(str.substring(0, 136) + str.substring(59, 136) + str.substring(136, 155));
    }
}