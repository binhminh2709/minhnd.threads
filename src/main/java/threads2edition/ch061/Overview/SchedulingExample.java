package threads2edition.ch061.Overview;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SchedulingExample implements Runnable {

    static String host;
    static int port;

    public static void main(String args[]) throws UnknownHostException, IOException {
        SchedulingExample scheduling = new SchedulingExample();
        Thread calcThread = new Thread(scheduling);
        calcThread.setPriority(4);
        calcThread.start();

        AsyncReadSocket reader = new AsyncReadSocket(new Socket(host, port));
        reader.setPriority(6);
        reader.start();

        doDefault();
    }

    private static void doDefault() {

    }

    @Override
    public void run() {
        doCalc();
    }

    private void doCalc() {

    }
}
