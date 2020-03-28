package algo.datastructure.daily;

import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 17:23 2020/3/27
 */

public class MyRandom {
    public List<List<Integer>> generate() {

        List<List<Integer>> list = new ArrayList<>();
        Random random = new Random();

        for (int j = 0; j < 1000; j++) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < 70; i++) {
                if (tmp.size() == 7)
                    break;
                int cur = random.nextInt(49);
                if (!tmp.contains(cur))
                    tmp.add(cur);
            }
            list.add(tmp);
        }
        return list;
    }

    public List<List<Integer>> generate1() {

        List<List<Integer>> list = new ArrayList<>();
        Random random = new Random();

        for (int j = 0; j < 1000; j++) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < 70; i++) {
                int nextInt = random.nextInt(49);
//                if (!tmp.contains(nextInt))
                    tmp.add(nextInt);
            }
            Collections.shuffle(tmp);
            List<Integer> current = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                current.add(tmp.get(i));
            }

            list.add(current);
        }
        return list;
    }

    public int calcu(int[] nums) {
        int res = 0;
        for (int num: nums)
            res += (20 - num) * (20 - num);
        return (int)Math.sqrt(res);
    }

    public static HashSet<Integer> ranSevenNum() {

        HashSet<Integer> set = new HashSet<Integer>();
        Random ran = new Random();
        while (set.size() < 14) {
            set.add(ran.nextInt(49) + 1);
        }
        List<Integer> list = new ArrayList<>();

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            list.add((Integer) iterator.next());
        }
        Collections.shuffle(list);
        set.clear();
        int index = ran.nextInt(14);
        while (set.size() < 7) {
            set.add(list.get(index));
            index = (index + 1) % 14;
        }
        return set;
    }

    public static void main(String[] args) {
        MyRandom myRandom = new MyRandom();
        List<List<Integer>> list = myRandom.generate();
        for (List<Integer> li: list)
            System.out.println(li);

//        int[] freq = new int[49];
//        for (List<Integer> li : list)
//            freq[li.get(6)]++;
//
//        for (int fre: freq)
//            System.out.print(fre + ",");
    }
}
