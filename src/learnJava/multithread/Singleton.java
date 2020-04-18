package learnJava.multithread;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 14:43 2020/4/10
 */

public class Singleton {
    public static Singleton singleton;
    private Singleton() {

    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized(Singleton.class) {
                if (singleton == null) {
                    // double check是必须的,否则Thread1如果正在等待锁,
                    // 然后Thread2生成了,之后Thread1会再次创建
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
