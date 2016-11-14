package base.ch09.example1;

import base.ch09.Task;

public class ThreadTest {

    public static void main(String[] args) {
        //int nThreads = Integer.parseInt(args[0]);
        int nThreads = 10;
        //long n = Long.parseLong(args[1]);
        long n = 10;
        Thread t[] = new Thread[nThreads];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Task(n, "Task " + i));
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {
            }
        }
    }
}
