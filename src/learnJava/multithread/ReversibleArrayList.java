package learnJava.multithread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:28 2020/4/23
 */

public class ReversibleArrayList<E> extends ArrayList<E> {
    public ReversibleArrayList(Collection<E> c) {
        super(c);
    }

    public Iterable<E> pre_it() {   // 反向迭代器
        return new Iterable<E>() {
            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    int current = size() - 1;   // 从尾部开始迭代
                    @Override
                    public boolean hasNext() {
                        return current >= 0;
                    }
                    @Override
                    public E next() {
                        return get(current--);
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        ReversibleArrayList<Integer> reverseList =
                new ReversibleArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        for (int i: reverseList)
            System.out.print(i + ", ");  // 1, 2, 3, 4, 5   默认是顺序迭代器
        System.out.println();
        for (int i: reverseList.pre_it())   // 显式调用自定义迭代器
            System.out.print(i + ", ");   // 5, 4, 3, 2, 1
    }
}






















