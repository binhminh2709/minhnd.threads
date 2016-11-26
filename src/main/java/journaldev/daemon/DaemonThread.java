package journaldev.daemon;

/**
 * Created by nguye on 11/26/16.
 */
public class DaemonThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            processSomething();
        }
    }

    private void processSomething() {
        try {
            System.out.println("Processing daemon thread");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
