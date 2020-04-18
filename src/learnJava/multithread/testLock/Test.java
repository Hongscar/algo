package learnJava.multithread.testLock;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 22:04 2020/4/16
 */

public class Test {

    public static List<Integer> list = new ArrayList<>();
    private static Lock lock = new ReentrantLock();
    public static void insert(Thread thread) {
        lock.lock();
        System.out.println(thread.getName() + " caught the lock");
        try {
            for (int i = 0; i < 5; i++)
                list.add(i);
            Thread.sleep(new Random().nextInt(1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(thread.getName() + " release the lock");
        }
    }

    private static ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public static void get(Thread thread) {
        rwlock.readLock().lock();
        try {
            for (int i = 0; i < 10; i++)
                System.out.println(thread.getName() + " is reading:" + list.get(i));
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        Thread  t1 = new Thread("Thread1") {
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        };
        Thread t2 = new Thread("Thread2") {
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        };
        t1.start();
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread r1 = new Thread("readThread1") {
            @Override
            public void run() {
                get(Thread.currentThread());
            }
        };
        Thread r2 = new Thread("readThread2") {
            @Override
            public void run() {
                get(Thread.currentThread());
            }
        };
        r1.start();
        r2.start();
    }
}
