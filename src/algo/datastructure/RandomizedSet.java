package algo.datastructure;

import java.util.*;


/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 18:58 2019/10/17
 */

public class RandomizedSet {
    HashMap<Integer, Integer> map;
    List<Integer> list;
    int size;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
        size = 0;
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val))
            return false;
        map.put(val, size);
        list.add(size++, val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    // 关键在于,单独一个 map,不能做到 insert, remove的时间复杂度均为1.
    // 而且getRandom,需要从数组里get,否则map删除了一个前面的元素,这时候rand如果roll到前面的数就不存在数据
    // 使用数组,当remove一个元素的时候,把数组的最后一个元素放到被remove的index处(而最后一个list元素不需要delete
    // 因为size已经减1,之后仅靠rand(size),并不会访问到那个元素,只需等待新的元素直接替换(数组的add方法是add(index, val))
    // 同时,调整了数组的最后一个元素,还要调整Map的最后一个元素的val(也就是对应数组的index)
    public boolean remove(int val) {
        if (!map.containsKey(val))
            return false;
        int index = map.get(val);
        int lastVal = list.get(size - 1);
        list.set(index, lastVal);
        map.put(lastVal, index);
        map.remove(val);
        size--;
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        return list.get(random.nextInt(size));
    }

    public static void main(String[] args) {
        RandomizedSet set = new RandomizedSet();
        System.out.println(set.insert(1));
        System.out.println(set.remove(2));
        System.out.println(set.insert(2));
        System.out.println(set.getRandom());
        System.out.println(set.remove(1));
        System.out.println(set.insert(2));
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
    }
}
