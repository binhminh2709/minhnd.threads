package journaldev.callable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by nguye on 11/27/16.
 */
public class MyCallable implements Callable<String> {


    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }

    public static void main(String args[]) {
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //create a futures to hold the Future object associated with Callable
        List<Future<String>> futures = new ArrayList<Future<String>>();

        //Create MyCallable instance
        Callable<String> callable = new MyCallable();

        for (int i = 0; i < 100; i++) {
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);

            //add Future to the futures, we can get return value using Future
            futures.add(future);
        }

        for (Future<String> future : futures) {
            try {

                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed

                System.out.println(new Date() + "::" + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

        //shut down the executor service now
        executor.shutdown();
    }
}
