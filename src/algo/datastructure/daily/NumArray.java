package algo.datastructure.daily;

/**
 * @Author: Seth
 * @Description:    Leetcode 303
 * @Date: Created in 19:07 2019/12/17
 */

class NumArray {            // 改进方案, 无须二维数组, 一维数组即可. res[i][j] = improve_res[j] - improve_pres[i]
//    private int[][] res;
//    private int length;
//
//    public NumArray(int[] nums) {
//        length = nums.length;
//        res = new int[length][length];
//        for (int i = 0; i < length; i++)
//            res[i][i] = nums[i];
//        for (int i = 0; i < length - 1; i++)
//            for (int j = i + 1; j < length; j++)
//                res[i][j] = res[i][j - 1] + nums[j];
//    }
//
//    public int sumRange(int i, int j) {
//        return res[i][j];
//    }

    private int[] res;
    private int length;

    public NumArray(int[] nums) {
        length = nums.length;
        res = new int[length + 1];
        for (int i = 1; i <= length; i++)
            res[i] = res[i - 1] + nums[i - 1];
    }

    public int sumRange(int i, int j) {
        return res[j + 1] - res[i];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */