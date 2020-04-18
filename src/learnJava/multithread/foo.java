package learnJava.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 17:20 2020/4/7
 */
class Task2 implements Runnable {
    String str;
    public Task2(String str) {
        this.str = str;
    }

    public void run() {
        System.out.print(str);
    }
}

public class foo {
    Lock lockA, lockB, lockC;

    public foo() {
        lockA = new ReentrantLock();
        lockB = new ReentrantLock();
        lockC = new ReentrantLock();
//        lockA.unlock();
        lockB.lock();
        lockC.lock();
        Thread t1 = new Thread(new Task2("one"));
        Thread t2 = new Thread(new Task2("two"));
        Thread t3 = new Thread(new Task2("three"));
        t1.start();
        t2.start();
        t3.start();
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized(lockA) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            lockB.unlock();
            lockA.unlock();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized(lockB) {
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            lockC.unlock();
            lockB.unlock();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized(lockC) {
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            lockC.unlock();
        }
    }

    public static void main(String[] args) {
        foo f = new foo();
    }
}