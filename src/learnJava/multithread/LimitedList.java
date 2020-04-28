package learnJava.multithread;

import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 15:32 2020/4/8
 */
class AddTask implements Runnable {
    private LimitedList list;
    public AddTask(LimitedList list) {
        this.list = list;
    }

    public void run() {
        while (true) {
            try {
                list.add((int)(Math.random() * 10 + 1));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DeleteTask implements Runnable {
    private LimitedList list;
    public DeleteTask(LimitedList list) {
        this.list = list;
    }

    public void run() {
        while (true) {
            try {
                list.delete();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class LimitedList {
    private List<Integer> list;
    public static int MAX = 5;

    public LimitedList() {
        list = new ArrayList<>();
    }

    public List<Integer> getList() {
        return list;
    }

    synchronized public void add(int element) throws InterruptedException {
        if (list.size() >= MAX)
            wait();
        list.add(element);
        notify();
        System.out.println(Thread.currentThread() + " After add, List is: " + list);
    }

    synchronized public void delete() throws InterruptedException {
        if (list.size() == 0)
            wait();
        list.remove(0);
        notify();
        System.out.println(Thread.currentThread() + " After delete, List is: " + list);
    }

    public static void main(String[] args) throws InterruptedException {
        LimitedList limitedList = new LimitedList();
        Thread thread1 = new Thread(new AddTask(limitedList));
        thread1.start();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new DeleteTask(limitedList));
            thread.start();
        }
    }
}
