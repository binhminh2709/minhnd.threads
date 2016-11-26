package journaldev.daemon;

/**
 * Created by nguye on 11/26/16.
 */
public class JavaDaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread dt = new Thread(new DaemonThread(), "dt");
//        dt.setDaemon(true);
        dt.start();
        //continue program
        Thread.sleep(30000);
        System.out.println("Finishing program");
    }

    /**
     * When we execute this program, JVM creates first user thread with main() function and then a daemon thread.
     * When main function is finished, the program terminates and daemon thread is also shut down by JVM
     * */
}
