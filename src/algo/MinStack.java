package algo;

import java.util.*;


/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 13:12 2019/10/10
 */

public class MinStack {
    /** initialize your data structure here. */
    public LinkedList<Integer> list;
    public LinkedList<Integer> sortedList;

    public MinStack() {
        list = new LinkedList<>();
        sortedList = new LinkedList<>();
        // int minElement = Integer.MAX_VALUE;
    }

    public void push1(int x) {
        list.addFirst(x);
        if (sortedList.isEmpty())
            sortedList.addFirst(x);
        else if (sortedList.getFirst() < x)
            sortedList.addFirst(sortedList.getFirst());
        else
            sortedList.addFirst(x);
        // for example, list add [3, 7], sortedList is [3, 3], when list remove 7,sortedList will remove 3
        // then sortedList peek is always the min element;
        // if list add [7, 3], sortedList is [7, 3]
        // we can optimise it laterly (it will add some duplicate elements, but it's much more clearly!
    }

    // improve push1
    public void push(int x) {
        list.addFirst(x);
        if (sortedList.isEmpty())
            sortedList.addFirst(x);
        else if (sortedList.getFirst() >= x)
            sortedList.addFirst(x);     // only insert element when the new element is smaller.
    }

    public void pop() {
        if (list.size() == 0)
            return;
        int x = list.removeFirst();
        if (x == sortedList.getFirst())
            sortedList.removeFirst();   // only delete element in sortedList when the smallest is popped.
    }

    public void pop1() {
        if (list.size() == 0)
            return;
        list.removeFirst();
        sortedList.removeFirst();   // remove the same time
    }

    public int top() {
        return list.getFirst();
    }

    public int getMin() {
        return sortedList.getFirst();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
