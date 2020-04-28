package learnJava.multithread;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 10:59 2020/4/7
 */
class TinyTask implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread() + " 子线程开始执行");
        for (int i = 0; i < 2; i++) {
            System.out.println("**********" + " i");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread() + " 子线程结束执行");
    }
}

class Task1 implements Runnable {
    public String name;
    public Task1(String name) {
        this.name = name;
    }
    public void run() {
        Thread thread = new Thread(new TinyTask());
        System.out.println(Thread.currentThread().getName() + " 线程开始执行！");
        thread.start();
        for (int i = 0; i < 3; i++) {
            System.out.println("任务:" + name + " print " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 线程结束执行！");
    }
}

public class TestTask1 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " 线程开始执行！");
        Thread task1 = new Thread(new Task1("1"));
        Thread task2 = new Thread(new Task1("2"));
        task1.start();
        try {
            task1.join();
            task2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task2.start();
        System.out.println(Thread.currentThread().getName() + " 线程结束执行！");
        //task1.start();
    }
}
