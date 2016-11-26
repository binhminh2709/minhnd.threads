package journaldev.safe;

import java.util.Arrays;

/**
 * Created by binhminh on 11/26/2016.
 */
public class SyncronizedMethodExample {
    public static void main(String[] args) throws InterruptedException {
        String[] arr = {"1", "2", "3", "4", "5", "6"};
        HashMapProcessor hmp = new HashMapProcessor(arr);
        Thread t1 = new Thread(hmp, "t1");
        Thread t2 = new Thread(hmp, "t2");
        Thread t3 = new Thread(hmp, "t3");
        long start = System.currentTimeMillis();
        //start all the threads
        t1.start();
        t2.start();
        t3.start();
        //wait for threads to finish
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Time taken= " + (System.currentTimeMillis() - start));
        //check the shared variable value now
        System.out.println(Arrays.asList(hmp.getMap()));
    }
}
