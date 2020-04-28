package learnJava.multithread.testPool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:32 2020/4/12
 */

public class TestExecutors {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    static int i = 1;

    public static void testnewFixedPool() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void testNewCachedPool() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running!");
            });
        }
    }

    public static void testNewSinglePool() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++)
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running!");
            });
    }

    public static void testNewSchedulePool() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
//        pool.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Current time: " + System.currentTimeMillis());
//                System.out.println(Thread.currentThread().getName() + " is running");
//            }
//        }, 1, 3, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("Current time: " + System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, 2, TimeUnit.SECONDS);
    }

    public static void testThreadLocal() {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(66);
        System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
        //threadLocal.remove();
        new Thread("Thread#1") {
            @Override
            public void run() {
                threadLocal.set(22);
                System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
                threadLocal.remove();
            }
        }.start();
//        new Thread("Thread#2") {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
////                local.set(343);
////                System.out.println(Thread.currentThread().getName() + " : " + local.get());
//            }
//        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
//        System.out.println(Thread.currentThread().getName() + " : " + local.get());
    }

    public static void testException() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future future = pool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                Object object = null;
                System.out.println("result##:" + object.toString());
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                System.err.println("Catch a Interrupted exception!");
            } catch (Exception e) {
                System.err.println("Catch an exception!");
            }
        }
        System.out.println(1);
    }

    public static void testException2() {
        ExecutorService pool = Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler((t1, e) -> {
                System.out.println(t1.getName() + " is throwing exception: " + e);
            });
            return t;
        });
        pool.execute(() -> {
            Object object = null;
            System.out.println("result##:" + object.toString());
        });
    }

    public static void testException3() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        Thread task = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Object object = null;
                System.out.println("result##:" + object.toString());
            }
        };
//        task.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println(t.getName() + " is throwing exception: " + e);
//            }
//        });
        task.start();
    }

    class ExtendedExecutor extends ThreadPoolExecutor {
        public ExtendedExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        // ...
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future<?>) {
                try {
                    Object result = ((Future<?>) r).get();
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }
            if (t != null)
                System.out.println(t);
        }
    }

    public static void doSth() {
        for (int i = 0; i < 15; i++) {
            System.out.print(i + " , ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ConcurrentHashMap<String, FutureTask<Connection>> connectionPool = new ConcurrentHashMap<>();

    public Connection getConnection(String key) throws Exception {
        FutureTask<Connection> connectionTask = connectionPool.get(key);
        if (connectionTask != null) {
            return connectionTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    // TODO Auto-generated method stub
                    return createConnection();
                }
            };
            FutureTask<Connection> newTask = new FutureTask<>(callable);
            connectionTask = connectionPool.putIfAbsent(key, newTask);
            if (connectionTask == null) {
                connectionTask = newTask;
                connectionTask.run();
            }
            return connectionTask.get();
        }
    }

    //创建Connection
    private Connection createConnection() {
        return null;
    }

    public static void testException11() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future future = pool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                Object obj = null;
                System.out.println("result##:" + obj.toString());
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                System.err.println("Catch an interrupted exception!");
            } catch (ExecutionException e) {
                System.err.println("Catch an execution exception!");
            }
        }
    }

    public static void main(String[] args) {
        testException2();
       // testNewSchedulePool();
//        Random random = new Random();
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        List<Future<Integer>> futureList = new ArrayList<>();
//        int res = 0;
//        for (int i = 0; i < 10; i++) {
//            Callable<Integer> callable = new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    int res = 0;
//                    for (int i = 1; i <= 10; i++)
//                        res += random.nextInt(10);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return res;
//                }
//            };
//            Future<Integer> future = pool.submit(callable);
//            futureList.add(future);
//        }
//        //doSth();    // FutureTask线程submit时, 主线程执行其他操作
//        for (Future<Integer> future : futureList) {
//            try {
//                System.out.println(future);
//                System.out.println(future.get());   // 阻塞future线程
//                System.out.println("----");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        pool.shutdown();
    }
}
