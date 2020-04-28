package algo.datastructure;

import java.util.LinkedList;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 16:37 2020/4/20
 */

public class MaxQueue {
    private LinkedList<Integer> queue;
    private LinkedList<Integer> deque;
    private int size;

    public MaxQueue() {
        queue = new LinkedList<>();
        deque = new LinkedList<>();
        size = 0;
    }

    public int max_value() {
        if (size == 0)
            return -1;
        return deque.getFirst();
    }

    public void push_back(int value) {
        queue.addFirst(value);
        while (deque.size() != 0 && deque.getLast() <= value)
            deque.removeLast();
        deque.addLast(value);
        size++;
    }

    public int pop_front() {
        if (size == 0)
            return -1;
        int tmp = queue.removeFirst();
        if (tmp == max_value())
            deque.removeFirst();
        size--;
        return tmp;
    }

    public static void main(String[] args) {
        MaxQueue que = new MaxQueue();
        que.push_back(1);
        que.push_back(2);
        System.out.println(que.max_value());
        char c = '2';
        System.out.println(c);
    }
}
