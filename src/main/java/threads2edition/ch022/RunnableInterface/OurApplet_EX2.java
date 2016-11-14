package threads2edition.ch022.RunnableInterface;

import org.apache.log4j.Logger;

import java.applet.Applet;

public class OurApplet_EX2 extends Applet implements Runnable {

    private static final Logger _log = Logger.getLogger(OurApplet_EX2.class);

    // method of Applet
    @Override
    public void init() {
        Thread t = new Thread(this);
        t.start();
    }

    // method of thread
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            _log.info("=========minhnd=======" + i);
        }
    }
}
