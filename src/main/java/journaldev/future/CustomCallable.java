package journaldev.future;

import java.util.concurrent.Callable;

/**
 * Created by nguye on 11/27/16.
 */
public class CustomCallable implements Callable<String> {

    private long waitTime;

    public CustomCallable(int timeInMillis) {
        this.waitTime = timeInMillis;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
}
