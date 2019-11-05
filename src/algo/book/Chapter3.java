package algo.book;

import java.util.*;

/**
 * @Author: Seth
 * @Description:    brute force
 * @Date: Created in 13:36 2019/10/24
 */

public class Chapter3 {

    // 顺序查找
    public int sequentialSearch(ArrayList<Integer> ints, int key) {
        ints.add(key);
        int size = ints.size();
        int i = 0;
        while (ints.get(i) != key)
            i++;
        if (i == size - 1)
            return -1;          // at the end,means not exist
        return i;
    }

    // 蛮力字符串匹配
    public int bruteForceStringMatch(char[] text, char[] pattern) {
        int text_len = text.length;
        int pattern_len = pattern.length;
        for (int i = 0; i < text_len - pattern_len; i++)
            for (int j = 0; j < pattern_len; j++) {
                if (text[i + j] != pattern[j])
                    break;
                if (j == pattern_len - 1)
                    return i;
            }
        return -1;
    }

    // 最近点对
    public double bruteForceClosestPoints(int[][] p) {
        double d = Integer.MAX_VALUE;
        for (int i = 0; i < p.length - 1; i++)
            for (int j = 0; j < p.length; j++)
                d = Math.min(d, Math.pow(p[i][0] - p[j][0], 2) + Math.pow(p[i][1] - p[j][1], 2));
        // 无须开平方根,只要一个数越大,开平方的结果也越大
        return d;
    }

    public static void main(String[] args) {
        Chapter3 ch3 = new Chapter3();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(6,4,2,5,7,9,5,5));
        System.out.println(ch3.sequentialSearch(list, 14));
        System.out.println(ch3.bruteForceStringMatch(new char[]{'n','o','b','o','d','y','_','n','o','t','i','c'},
                new char[] {'n','o','b','b'}));
    }
}
