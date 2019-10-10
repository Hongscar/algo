package algo;

import java.util.*;

/**
 * @Author: Seth
 * @Description:    bad time out solution
 * @Date: Created in 9:50 2019/10/10
 */

public class Bad_LRUCache {
    public Map<Integer, int[]> map;
    public List<Integer> lru;
    public int capacity;
    public int current;

    public Bad_LRUCache(int capacity) {
        map = new HashMap<>();
        lru = new LinkedList<>();
        this.capacity = capacity;
        this.current = 1;
    }

    public int get(int key) {
        int[] ints = map.get(key);
        if (ints != null) {
            current++;
            map.put(key, new int[] { ints[0], current});
            return ints[0];
        }
        return -1;
    }

    public void put(int key, int value) {
        int[] ints = map.get(key);
        if (ints != null && ints[0] == value)
            return;

        // map是根据key排序的，即使传入Comparator也是。
        if (map.size() >= capacity && ints == null) {
            List<Map.Entry<Integer, int[]>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, int[]>>() {
                @Override
                public int compare(Map.Entry<Integer, int[]> o1, Map.Entry<Integer, int[]> o2) {
                    return  o1.getValue()[1] - o2.getValue()[1];
                }
            });
            map.remove(list.get(0).getKey());
        }
        current++;
        map.put(key, new int[] { value, current});
    }

    public static void main(String[] args) {
        Bad_LRUCache cache = new Bad_LRUCache(2);
        System.out.println(cache.get(2));
        cache.put(2, 6);
        System.out.println(cache.get(1));
        cache.put(1, 5);
        cache.put(1, 2);
//        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
//        System.out.println(cache.get(3));
//        System.out.println(cache.get(4));
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
