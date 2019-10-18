package algo.book;

import java.util.ArrayList;

/**
 * @Author: Seth
 * @Description: Introduction to The Design and Analysis of Algorithms
 * @Date: Created in 19:47 2019/10/10
 */

public class Chapter1 {

    // page 4, Euclid's algorithm
    public int gcd(int m, int n) {
        int r = m % n;
        while (r != 0) {
            r = m % n;
            m = n;
            n = r;
        }
        return m;   // n is the last n
    }

    // page 5, Sieve of Eratosthenes
    // get all the Prime numbers which is less than n
    public ArrayList<Integer> sieve(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        int[] A = new int[n];           // use n space complex
        int j = 0;
        for (int p = 2; p < n; p++)
            A[p] = p;
        // p < floor(n ^ 0.5) , when p is bigger than it,p * p is bigger than n
        for (int p = 2; p * p < n; p++) {
            if (A[p] != 0)
              j = p * p;
            // why begin at p * p? Obviously p is not less than 2
            // it means p * (1 ~ p-1) must have already removed.
            while (j < n) {     // remove the non-prime number
                A[j] = 0;
                j = j + p;      // begin at p * p, increment is p.
            }
        }
        for (int i = 2; i < n; i++)     // all the non-prime number have been set zero
            if (A[i] != 0)
                result.add(A[i]);
        return result;
    }

    public static void main(String[] args) {
        Chapter1 ch1 = new Chapter1();
        ArrayList<Integer> list = ch1.sieve(100000);
        System.out.println(list.size());
        System.out.println(list.size());
       // System.out.println(ch1.gcd(80, 24));
    }

}
