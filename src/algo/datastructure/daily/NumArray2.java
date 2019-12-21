package algo.datastructure.daily;

/**
 * @Author: Seth
 * @Description:    Leetcode 307        这题一定要用一维数组, 否则内存会超出限制.
 * @Date: Created in 19:08 2019/12/17
 */

class NumArray2 {
    private int[] res;
    private int length;
    private int[] nums;

    public NumArray2(int[] nums) {
        length = nums.length;
        res = new int[length + 1];
        for (int i = 1; i <= length; i++)
            res[i] = res[i - 1] + nums[i - 1];
        this.nums = nums;
    }

    public int sumRange(int i, int j) {
        return res[j + 1] - res[i];
    }

    public void update(int i, int val) {
        int diff = val - nums[i];
        nums[i] = val;
        for (int j = i + 1; j <= length; j++)
            res[j] += diff;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */