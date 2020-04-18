package learnJava.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 16:33 2020/4/7
 */
class Task implements Runnable {
    String toPrint;
    Object self;    Object prev;
    public Task(String toPrint, Object self, Object prev) {
        this.toPrint = toPrint;
        this.self = self;
        this.prev = prev;
    }

    public void run() {
        int count = 0;
        while (count < 10) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(toPrint);
                    count++;
                    self.notify();
                }
                try {
                    prev.wait(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class PrintABC {
    public static void main(String[] args) throws InterruptedException {
//        Object a = new Object(), b = new Object(), c = new Object();
//        Thread pa = new Thread(new Task("A", a, c));
//        Thread pb = new Thread(new Task("B", b, a));
//        Thread pc = new Thread(new Task("C", c, b));
//        pa.start();
//        Thread.sleep(1000);
//        pb.start();
//        Thread.sleep(1000);
//        pc.start();
        Foo foo = new Foo();
        foo.first(new Runnable() {
            @Override
            public void run() {
                System.out.println("one");
            }
        });
        foo.second(new Runnable() {
            @Override
            public void run() {
                System.out.println("two");
            }
        });
        foo.third(new Runnable() {
            @Override
            public void run() {
                System.out.println("three");
            }
        });
    }
}

class Foo {
    public volatile Object lock1, lock2, lock3;

    public Foo() {
        lock1 = new Object();
        lock2 = new Object();
        lock3 = new Object();
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized(lock1) {
            synchronized (lock2) {
                // printFirst.run() outputs "first". Do not change or remove this line.
                printFirst.run();
                lock2.notify();
            }
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized(lock2) {
            synchronized (lock3) {
                // printSecond.run() outputs "second". Do not change or remove this line.
                printSecond.run();
                lock3.notify();
            }
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized(lock3) {
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
}
