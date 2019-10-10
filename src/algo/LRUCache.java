package algo;
import java.util.*;
/**
 * @Author: Seth
 * @Description: using LinkedHashMap
 * @Date: Created in 11:09 2019/10/10
 */

// 除了HashMap,对于LinkedHashMap跟TreeMap的掌握还不够
class LRUCache extends LinkedHashMap<Integer, Integer>{ // 此处不应该用继承，改用组合会更好
    private int capacity;

    // 关键是构造函数上的accessOrder参数，如果是true的话，会按访问顺序来重新排序，跟get没啥关系
    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
