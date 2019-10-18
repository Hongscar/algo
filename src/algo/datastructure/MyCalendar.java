package algo.datastructure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 15:45 2019/10/16
 */

public class MyCalendar {
    private ArrayList<Integer> begins;
    private ArrayList<Integer> ends;
    private int size;

    public MyCalendar() {
        begins = new ArrayList<>();
        ends = new ArrayList<>();
        size = 0;
    }

    public boolean book(int start, int end) {
        for (int i = 0; i < size; i++) {
            if (i == 0 && end <= begins.get(i))
                break;
            else if (i != size - 1 && start >= ends.get(i) && end <= begins.get(i + 1))
                break;
            else if (i == size - 1 && start >= ends.get(i))
                break;
            else if (i == size - 1)
                return false;
        }
        begins.add(start);
        ends.add(end);
        size++;
        Collections.sort(begins);
        Collections.sort(ends);
        return true;
    }



    public static void main(String[] args) {
        MyCalendar myCalendar = new MyCalendar();
        System.out.println(myCalendar.book(69, 70));
        System.out.println(myCalendar.book(3, 4));
        System.out.println(myCalendar.book(39, 40));
        System.out.println(myCalendar.book(35, 36));
        System.out.println(myCalendar.book(3, 4));
        System.out.println(myCalendar.book(55, 56));
    }
}