package learnJava.multithread.testLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 11:45 2020/4/18
 */
class Account {
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();
    private int balance;
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void deposit(int i) {
        lock.lock();
        try {
            balance += i;
            System.out.println(Thread.currentThread().getName() + " deposit " +
                    i + ",now the balance is : " + balance );
            condition.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
    public void withdraw(int i) {
        lock.lock();
        try {
            while (balance < i) {
                System.out.println(Thread.currentThread().getName() + " waiting for deposit");
                condition.await();
            }
            balance -= i;
            System.out.println(Thread.currentThread().getName() + " withdraw " +
                    i + ",now t he balance is : " + balance);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}


public class TestBank {
    public static Account account = new Account();

    static class DepositTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    account.deposit((int)(Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    static class WithdrawTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                account.withdraw((int)(Math.random() * 10) + 1);
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new DepositTask());
        Thread thread2 = new Thread(new WithdrawTask());
        thread1.start();
        thread2.start();
    }
}
