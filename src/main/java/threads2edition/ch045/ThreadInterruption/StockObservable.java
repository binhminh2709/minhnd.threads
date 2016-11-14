package threads2edition.ch045.ThreadInterruption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Observable;

public class StockObservable extends Observable {
    String lastTick;

    void setTick(String s) {
        lastTick = s;
        setChanged();
        notifyObservers();
    }

    public class StockHandler extends Thread {
        private BufferedReader br;
        private InputStream is;
        private Socket sock;
        private StockObservable stock;
        private volatile boolean done = false;
        private Object lock = new Object();

        public StockHandler(StockObservable o, String host, int port) throws IOException, UnknownHostException {
            sock = new Socket(host, port);
            is = sock.getInputStream();
            stock = o;
        }

        @Override
        public void run() {
            br = new BufferedReader(new InputStreamReader(is));
            Thread t = new StockHandlerThread();
            t.start();
            synchronized (lock) {
                while (!done) {
                    try {
                        lock.wait(Integer.MAX_VALUE);
                    } catch (InterruptedException ie) {
                        done = true;
                        try {
                            t.interrupt();
                            is.close();
                            sock.close();
                        } catch (IOException ioe) {
                        }
                    }
                }
            }
        }

        class StockHandlerThread extends Thread {
            @Override
            public void run() {
                String s;
                try {
                    while ((s = br.readLine()) != null)
                        stock.setTick(s);
                } catch (IOException ioe) {
                }
                done = true;
                synchronized (lock) {
                    lock.notify();
                }
            }
        }
    }
}
