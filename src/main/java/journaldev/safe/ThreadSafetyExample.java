package journaldev.safe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by binhminh on 11/26/16.
 */
public class ThreadSafetyExample {

    public static void main(String[] args) throws InterruptedException {
        ProcessingThread pt = new ProcessingThread();
        Thread t1 = new Thread(pt, "t1");
        t1.start();
        Thread t2 = new Thread(pt, "t2");
        t2.start();
        //wait for threads to finish processing
        t1.join();
        t2.join();
        System.out.println("Processing count=" + pt.getCount());

        AtomicLong atomicLong = new AtomicLong();
        AtomicInteger atomicInteger = new AtomicInteger();
//        java.util.concurrent.locks
    }
}
