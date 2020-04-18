import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 19:36 2019/10/9
 */

public class algo {

    public boolean checkOverlap(int radius, int x_center, int y_center, int x1, int y1, int x2, int y2) {
        int minX = x_center - radius, maxX = x_center + radius, minY = y_center - radius, maxY = y_center + radius;
        double minx = Math.min(x1, x2);
        double maxx = Math.max(x1, x2);
        double miny = Math.min(y1, y2);
        double maxy = Math.max(y1, y2);
        double tempx1 = Math.sqrt(radius * radius - (y1 - y_center) * (y1 - y_center)) + x_center;
        if (tempx1 >= minX && tempx1 <= maxX && tempx1 >= minx && tempx1 <= maxx)
            return true;
        double tempx2 = Math.sqrt(radius * radius - (y2 - y_center) * (y2 - y_center)) + x_center;
        if (tempx2 > minX && tempx2 <= maxX && tempx2 >= minx && tempx2 <= maxx)
            return true;
        double tempy1 = Math.sqrt(radius * radius - (x1 - x_center) * (x1 - x_center)) + y_center;
        if (tempy1 >= minY && tempy1 <= maxY && tempy1 >= miny && tempy1 <= maxy)
            return true;
        double tempy2 = Math.sqrt(radius * radius - (x2 - x_center) * (x2 - x_center)) + y_center;
        if (tempy2 >= minY && tempy2 <= maxY && tempy2 >= miny && tempy2 <= maxy)
            return true;

        if (minx < minX && maxx > maxX && miny < minY && maxy > maxY)
            return true;
        if (minx > minX && maxx < maxX && miny > minY && maxy < maxY)
            return true;
        return false;
    }

    public int numSteps(String s) {
        int res = 0;
        List<Character> list = new ArrayList<>();
        for (char c: s.toCharArray())
            list.add(c);
        Collections.reverse(list);
        list.add('0');
        int size = list.size();
        while (true) {
            for (int i = 0; i < size; i++) {
                if (i == size - 2 && list.get(size - 1) == '0' && list.get(i) == '1' || i == size - 1)
                    return res;
                if (list.get(i) == '0')
                    res++;
                else {
                    list.set(i, '0');
                    for (int j = i + 1; j < size; j++) {
                        if (list.get(j) == '0') {
                            list.set(j, '1');
                            break;
                        }
                        else
                            list.set(j, '0');
                    }
                    res++;
                    i--;
                }
            }
        }
    }

    public String longestDiverseString(int a, int b, int c) {
        String res = "d";
        int prev = 0;
        while (prev != res.length()) {
            prev = res.length();
            if (a > b && a > c && res.charAt(res.length() - 1) != 'a' && a > 1) {
                if (a > b + c) {
                    res += "aa";
                    a -= 2;
                }
                else {
                    res += "a";
                    a--;
                }
            }
            if (b > a && b > c && res.charAt(res.length() - 1) != 'b' && b > 1) {
                if (b > a + c) {
                    res += "bb";
                    b -= 2;
                }
                else {
                    res += "b";
                    b--;
                }
            }
            if (c > a && c > b && res.charAt(res.length() - 1) != 'c' && c > 1) {
                if (c > a + b) {
                    res += "cc";
                    c -= 2;
                }
                else {
                    res += "c";
                    c--;
                }
            }
            else {
                if (res.charAt(res.length() - 1) != 'a' && a > 0) {
                    res += "a";
                    a--;
                }
                else if (res.charAt(res.length() - 1) != 'b' && b > 0) {
                    res += "b";
                    b--;
                }
                else if (res.charAt(res.length() - 1) != 'c' && c > 0) {
                    res += "c";
                    c--;
                }
            }
        }
        return res.substring(1);
    }

//    public static void f1(double d) {
//        System.out.println(d);
//    }
    public static void main(String[] args) {
        System.out.println(new algo().longestDiverseString(1, 1, 7));
        System.out.println(new algo().numSteps("1101"));
        System.out.println(new algo().checkOverlap(4, 102, 50, 0, 0, 100, 100));
//        char c = '阿';
//        f1(c);
//        short s1 = 1;
//        byte b1 = 126;
//        //s1 = s1 + 1;    // error, int赋值给short
//        s1 += 1;          // ok
//        //s1 = s1 + b1;   // error
//        s1 += b1;         // ok
//        System.out.println(s1);     // 128
//        //s1 = s1 << 2;   // error
//        s1 >>= 2;         // ok
//        System.out.println(s1);     // 32
//        System.out.println(s1 >> 2);    // 不赋值就可以, 8
//        // b1 = 128;         // 超出byte的范围
//        b1 += 1000;
//        System.out.println(b1); // b1 + 2之后超出,从-128重新开始
//        //998 / 256 = 3 ...... 230   230 - 128 = 102
//        String a = "a";
//        a += 1;
//        System.out.println(a);
//        A:
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 200; j++) {
//                System.out.println(1);
//                break A;
//            }
//        }
    }
}
