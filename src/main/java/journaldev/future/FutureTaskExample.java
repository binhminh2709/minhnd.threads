package journaldev.future;

import java.util.concurrent.*;

/**
 * Created by nguye on 11/27/16.
 */
public class FutureTaskExample {


    public static void main(String[] args) {

        CustomCallable callable1 = new CustomCallable(1000);
        CustomCallable callable2 = new CustomCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }

                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }


            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }

    }
}
