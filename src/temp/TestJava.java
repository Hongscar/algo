package temp;

import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 11:16 2019/9/15
 */

//class A {
//     void f() {
//        System.out.println("A function in Father class");
//    }
//}
//
//class B extends A {
//    protected void f() {
//        System.out.println("A function in son class");
//
//    }
//}
//class Car {
//    private int batch;
//    public Car(int batch) {
//        this.batch = batch;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Car) {
//            Car c = (Car) obj;
//            return batch == c.batch;
//        }
//        return false;
//    }
//}
//
//class BigCar extends Car {
//    int count;
//    public BigCar(int batch, int count) {
//        super(batch);
//        this.count = count;
//    }
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof BigCar) {
//            BigCar bc = (BigCar) obj;
//            return super.equals(bc) && count == bc.count;
//        }
//        return super.equals(obj);
//    }
//    public static void main(String[] args) {
//        Car c = new Car(1);
//        BigCar bc = new BigCar(1, 20);
//        BigCar bc2 = new BigCar(1, 22);
//        System.out.println(bc.equals(c));
//        System.out.println(c.equals(bc2));
//        System.out.println(bc.equals(bc2));
//    }
//}

class Key{
    private String k;
    public Key(String key){
        this.k=key;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key){
            Key key=(Key)obj;
            return k.equals(key.k);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return k.hashCode();    // String的hashCode实现
    }
}

/**
 * 值
 * @author zejian
 *
 */
class Value{
    private int v;
    public volatile long j = 12;

    public Value(int v){
        this.v=v;
    }

    @Override
    public String toString() {
        return "类Value的值－－>"+v;
    }
}




public class TestJava {
    public static void main(String[] args) {
//        A obj1 = new A();
//        obj1.f();
//        A obj2 = new B();
//        obj2.f();
//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 99));
//        Iterator it1 = list.iterator();
//
//         list.set(0, 123);       // java.util.ConcurrentModificationException
//
//        Iterator it2 = list.iterator();
//        it2.remove();
//
//        while (it2.hasNext())
//            System.out.print(it2.next() + " , ");
//
////        list.add(1);
////        list.remove(3);
//
//
//        System.out.println();
//        while (it1.hasNext())
//            System.out.print(it1.next() + " , ");
//        Map<Key,Value> map2 = new HashMap<Key,Value>();
//
//        String s1 = new String("key");
//        String s2 = new String("key");
//        Value value = new Value(2);
//        Key k1 = new Key("A");
//        Key k2 = new Key("A");
//        map2.put(k1, value);
//        System.out.println("k1.equals(k2):"+k1.equals(k2));
//        System.out.println("map2.get(k1):"+map2.get(k1));   // 2
//        System.out.println("map2.get(k2):"+map2.get(k2));   // null

//        ArrayList list = new ArrayList();
//        list.add(123);
//        list.add("asd");
//        System.out.print(list);

    }
}
