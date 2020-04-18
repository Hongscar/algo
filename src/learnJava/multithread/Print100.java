package learnJava.multithread;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 2:13 2020/4/11
 */

class Print11 implements Runnable {
    static Integer i = 1;
    Object lock1;
    Object lock2;
    public Print11(Object l1, Object l2) {
        lock1 = l1;
        lock2 = l2;
    }

    @Override
    public void run() {
        while (i < 100) {
            synchronized (lock1) {
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " " + i++);
                    lock2.notify();
                }
                try {
                    lock1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class Print100 {

    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Thread t1 = new Thread(new Print11(obj1, obj2));
        Thread t2 = new Thread(new Print11(obj2, obj1));
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();


    }
}
