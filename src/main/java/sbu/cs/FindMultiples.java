package sbu.cs;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FindMultiples
{
    public static int sum = 0;

    public static class MultipleChecker implements Runnable{
        int number;
        Lock lock;

        public MultipleChecker(int number, Lock lock){
            this.number = number;
            this.lock = lock;
        }

        @Override
        public void run(){
            if (number % 3 == 0 || number % 5 == 0 || number % 7 == 0){
                lock.lock();
                sum += number;
                lock.unlock();
            }
        }
    }

    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public int getSum(int n) {
        sum = 0; // only for get correct answers in unit tests.program works without this line of code correctly.
        Lock lock = new ReentrantLock();

        ExecutorService executors = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= n; i++){
            MultipleChecker mc = new MultipleChecker(i, lock);
            executors.execute(mc);
        }

        executors.shutdown();
        try {
            executors.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }

        return sum;
    }

    public static void main(String[] args) {
    }
}
