package threads2edition.ch023.LifeCycle;

import org.apache.log4j.Logger;
import threads2edition.util.IOUtil;
import threads2edition.util.NameImage;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class LifeCycleAnimate extends Applet {

    private static final Logger _log = Logger.getLogger(LifeCycleAnimate.class);
    LifeCycleTimer timer;
    private int count, lastcount;
    private Image pictures[];
    private long startTime;

    // Method of applet
    @Override
    public void init() {
        _log.info("====INIT===");
        lastcount = 10;
        count = 0;
        pictures = new Image[10];
        MediaTracker tracker = new MediaTracker(this);
        for (int a = 0; a < lastcount; a++) {
            // pictures[a] = getImage(getCodeBase(), new Integer(a).toString() +
            // ".jpeg");
            try {
                pictures[a] = ImageIO.read(IOUtil.getInputStream(NameImage.sImge[a]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tracker.addImage(pictures[a], 0);
        }
        tracker.checkAll(true);
    }

    // Method of applet
    @Override
    public void start() {
        _log.info("====START===");
        timer = new LifeCycleTimer(this, 1000);
        timer.start();
        startTime = System.currentTimeMillis();
        _log.info("====" + startTime);
    }

    // Method of applet
    @Override
    public void stop() {
        _log.info("====STOP===");
        timer.shouldRun = false;
        while (timer.isAlive()) {
            try {
                Thread.sleep(100);
                _log.info("====isAlive====" + timer.isAlive());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        _log.info("====isAlive===" + timer.isAlive());
        _log.info("====TOTAL TIME===" + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void paint(Graphics g) {
        _log.info("====PAINT====" + timer.isAlive());
        g.drawImage(pictures[count++], 0, 0, null);
        if (count == lastcount) {
            count = 0;
        }
    }

    /**
     * Just because a thread has been started does not mean itis actually running,
     * nor that it is able to run - the thread may be blocked, waiting for I/O, or
     * it may still be in the transitional period of the start() method. For this
     * reason, the isAlive()method is more useful in detecting whether a thread
     * has stopped running. For example, let's examine the stop()method of this
     * applet. Just like the earlier versions, we have a TimerThread object that
     * is started and stopped when the applet is started and stopped. In this
     * newer version, the applet's stop()method does more than just stop the
     * TimerThread: it also checks to make sure the thread actually has stopped.
     *
     * In this example, we don't gain anything by making sure the timer thread has
     * actually stopped. But if for some reason we need to deal with common data
     * that is being accessed by two threads, and it is critical to make sure the
     * other thread is stopped, we can simply loop and check to make sure the
     * thread is no longer alive before continuing.
     *
     * There is another circumstance in which a thread can be considered no longer
     * alive: if the stop() method is called, the thread will be considered no
     * longer alive a short time later. This is really the same case: the
     * isAlive()method can be used to determine if the run()method has completed,
     * whether normally or as a result of the stop()method having been called.
     */
}
