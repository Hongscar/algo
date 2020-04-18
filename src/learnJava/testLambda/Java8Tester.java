package learnJava.testLambda;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:56 2020/4/16
 */

public class Java8Tester {
    public static void main(String args[]) {
        int num = 1;
        Converter<Integer, String> s = (param2, param) -> System.out.println(param + num);
        s.convert(2, "2");  // 输出结果为 3
        //num = 3;
    }

    public interface Converter<T1, T2> {
        void convert(int i2, String i);
    }
}
