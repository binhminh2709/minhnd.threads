package base.ch12;

import java.io.IOException;
import java.io.InputStream;

public abstract class InterruptibleReader extends Thread {
    private Object lock = new Object();
    private InputStream is;
    private boolean done;
    private int buflen;

    public InterruptibleReader(InputStream is) {
        this(is, 512);
    }

    public InterruptibleReader(InputStream is, int len) {
        this.is = is;
        buflen = len;
    }

    protected void processData(byte[] b, int n) {
    }

    public void run() {
        ReaderClass rc = new ReaderClass();
        synchronized (lock) {
            rc.start();
            while (!done) {
                try {
                    lock.wait();
                } catch (InterruptedException ie) {
                    done = true;
                    rc.interrupt();
                    try {
                        is.close();
                    } catch (IOException ioe) {
                    }
                }
            }
        }
    }

    class ReaderClass extends Thread {
        public void run() {
            byte[] b = new byte[buflen];
            while (!done) {
                try {
                    int n = is.read(b, 0, buflen);
                    processData(b, n);
                } catch (IOException ioe) {
                    done = true;
                }
            }
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
