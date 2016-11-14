package threads2edition.ch045.ThreadInterruption;

import java.util.Vector;

public class Consumer extends Thread {

    Vector data;

    public Consumer(Vector data) {
        this.data = data;
    }

    @Override
    public void run() {
        Object o;
        while (true) {
            synchronized (data) {
                if (isInterrupted()) {
                    return;
                }
                while (data.size() == 0) {
                    try {
                        data.wait();
                    } catch (InterruptedException ie) {
                        return;
                    }
                }
                o = data.elementAt(0);
                data.removeElementAt(0);
            }
            process(o);
        }
    }

    private void process(Object o) {

    }
}
