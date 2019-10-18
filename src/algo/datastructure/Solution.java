package algo.datastructure;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 17:02 2019/10/17
 */

class Solution {
    private int[] nums;
    private int[] former;

    public Solution(int[] nums) {
        this.nums = new int[nums.length];
        this.former = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            this.nums[i] = nums[i];
            this.former[i] = nums[i];
        }
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        for (int i = 0; i < former.length; i++)
            nums[i] = former[i];
        return nums;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int length = nums.length;
        int rand;
        boolean[] visited = new boolean[length];
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            do {
                rand = (int)(Math.random() * length);
                if (!visited[rand]) {
                    visited[rand] = true;
                    break;
                }
            } while (true);
            result[i] = nums[rand];
        }
        nums = result;
        return nums;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < nums.length; i++)
            str += nums[i] + ", ";
        return str;
    }

    public static void main(String[] args) {
        int[] nums = {1,5,9,2,13,65,23,87};
        Solution solution = new Solution(nums);
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.reset();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
        solution.reset();
        System.out.println(solution);
        solution.shuffle();
        System.out.println(solution);
    }
}
