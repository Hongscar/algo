package learnJava.multithread;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 15:01 2020/4/15
 */

public class TestClassLock {
    public TestClassLock() {
        System.out.println(Thread.currentThread().getName() + " is creating object");
    }

    public static void p() throws InterruptedException {
        synchronized (TestClassLock.class) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " is calling a function");
                Thread.sleep(500);
            }
        }
    }

    public static void p2() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " is calling another function");
            Thread.sleep(500);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            TestClassLock obj = new TestClassLock();
        });
        Thread t2 = new Thread(() -> {
            try {
                //TestClassLock.p();
                TestClassLock obj = new TestClassLock();
                obj.p();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread t3 = new Thread(() -> {
//            TestClassLock obj = new TestClassLock();
//            obj.p2();
            try {
                TestClassLock.p2();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        t2.start();
        Thread.sleep(500);
        t3.start();
        t1.start();
    }
}
