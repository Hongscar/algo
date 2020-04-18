package learnJava.multithread.testLock;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 2:34 2020/4/17
 */

public class TestSync {
    public void method1() {
        synchronized (this) {
            System.out.println("Method 1 start");
        }
    }

    public synchronized void method2() {
        System.out.println("Method 2 start");
    }

    public void method3() {
        System.out.println("Method 3 start");
    }

    public static void main(String[] args) {
        TestSync testSync = new TestSync();
        testSync.method1();
        testSync.method2();
    }
}
