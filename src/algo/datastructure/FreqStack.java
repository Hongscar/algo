package algo.datastructure;

import java.util.LinkedList;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:44 2019/12/13
 */

public class FreqStack {
    private HashMap<Integer, LinkedList<Integer>> map;      // LinkedList用于记录每一个数字的出现的indexs
    private int size;
    private LinkedList<Integer> stack;

    public FreqStack() {
        map = new HashMap<>();
        stack = new LinkedList<>();
        size = 0;
    }

    public void push(int x) {
//        int pre_freq = freq.getOrDefault(x, 0);
//        freq.put(x, pre_freq + 1);
        stack.addLast(x);
        LinkedList<Integer> list = map.getOrDefault(x, new LinkedList<>());
        list.addLast(size);
        map.put(x, new LinkedList<>(list));
        size++;
    }

    public int pop() {
        Integer res;
        // 对HashMap根据Value排序
        List<Map.Entry<Integer, LinkedList<Integer>>> list1 = new ArrayList<>(map.entrySet());
        Collections.sort(list1, new Comparator<Map.Entry<Integer, LinkedList<Integer>>>() {
            @Override
            public int compare(Map.Entry<Integer, LinkedList<Integer>> o1, Map.Entry<Integer, LinkedList<Integer>> o2) {
                LinkedList<Integer> list1 = o1.getValue();
                LinkedList<Integer> list2 = o2.getValue();
                int diff = list2.size() - list1.size();
                if (diff != 0)
                    return diff;
                return list2.getLast() - list1.getLast();
            }
        });
        Map.Entry<Integer, LinkedList<Integer>> entry = list1.get(0);
        LinkedList<Integer> list = entry.getValue();
        int size = list.size();
        int last = stack.getLast();
        LinkedList<Integer> lastList = map.get(last);
        if (lastList.size() != size) {     // 说明是中间的元素freq最高, 无须考虑最接近栈顶这个条件
            res = entry.getKey();
            int lastIndex = list.getLast();
            stack.remove(lastIndex);            // 维护 stack
            list.removeLast();                  // 维护 LinkedList的indexs
            if (list.size() == 0)
                map.remove(res);
            else
                map.put(res, new LinkedList<>(list));
//            int pre_freq = freq.get(res);
//            freq.put(res, pre_freq - 1);
            return res;
        }
        else {
            stack.removeLast();     // 直接去掉 stack的栈顶
            lastList.removeLast();  // 同理
            if (lastList.size() == 0)
                map.remove(last);
            else
                map.put(last, lastList);
//            int pre_freq = freq.get(last);
//            freq.put(last, pre_freq - 1);
            return last;
        }
    }

    public static void main(String[] args) {
//        FreqStack freqStack = new FreqStack();
//        freqStack.push(5);
//        freqStack.push(7);
//        freqStack.push(5);
//        freqStack.push(7);
//        freqStack.push(4);
//        freqStack.push(5);
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());

        FreqStack freqStack = new FreqStack();
//        freqStack.push(4);
//        freqStack.push(0);
//        freqStack.push(9);
//        freqStack.push(3);
//        freqStack.push(4);
//        freqStack.push(2);
//        System.out.println(freqStack.pop());
//        freqStack.push(6);
//        System.out.println(freqStack.pop());
//        freqStack.push(1);
//        System.out.println(freqStack.pop());
//        freqStack.push(1);
//        System.out.println(freqStack.pop());
//        freqStack.push(4);
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
//        System.out.println(freqStack.pop());
        freqStack.push(5);
        freqStack.push(1);
        freqStack.push(2);
        freqStack.push(5);
        freqStack.push(5);
        freqStack.push(5);
        freqStack.push(1);
        freqStack.push(6);
        freqStack.push(1);
        freqStack.push(5);
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());

    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 */
