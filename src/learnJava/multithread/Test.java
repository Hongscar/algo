package learnJava.multithread;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 15:50 2020/4/9
 */

public class Test {
    public volatile int inc = 0;
    public boolean stop = false;
    public void incre() {
        inc++;
    }

    public void func() {
        stop = false;
        while (!stop) {
            doSth();
        }
    }

    public void stop() {
        stop = true;
    }

    public void doSth() {
        System.out.print("1");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] ar) {
        final Test test = new Test();
//        for (int i = 0; i < 10; i++) {
//            new Thread() {
//                public void run() {
//                    for (int j = 0; j < 1000; j++)
//                        test.incre();
//                }
//            }.start();
//        }
//        while (Thread.activeCount() > 2) {
//            System.out.println(Thread.currentThread().getName());
//            Thread.yield();
//        }
//
//        System.out.println(test.inc);

        for (int i = 0; i < 10000; i++) {
            System.out.println();
            System.out.println(i);
            new Thread() {
                public void run() {
                    test.func();
                }
            }.start();
            try {
                Thread.sleep(21);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread() {
                public void run() {
                    test.stop();
                }
            }.start();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
