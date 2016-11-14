package base.ch12.example5;

import base.ch12.InterruptibleReader;

import java.io.InputStream;
import java.net.Socket;

public class InterruptibleClient extends InterruptibleReader {

    public InterruptibleClient(InputStream is) {
        super(is);
    }

    public static void main(String[] args) throws Exception {
        Socket s = new Socket(args[0], Integer.parseInt(args[1]));
        InputStream is = s.getInputStream();
        InterruptibleClient c = new InterruptibleClient(is);
        c.start();
        System.out.println("Main thread sleeping");
        Thread.sleep(10000);
        System.out.println("Main thread woke up");
        c.interrupt();
        System.out.println("Main thread called interrupt");
    }

    public void processData(byte[] b, int n) {
        System.out.println("Got data " + new String(b, 0, n));
    }
}
