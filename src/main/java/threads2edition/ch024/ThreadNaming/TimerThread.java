package threads2edition.ch024.ThreadNaming;

import org.apache.log4j.Logger;
import threads2edition.util.IOUtil;
import threads2edition.util.NameImage;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class TimerThread extends Applet implements Runnable {

    int count, timediff; // Time between repaints of the component
    volatile boolean shouldRun; // Set to false to stop thread
    Thread thread;
    private Logger _log = Logger.getLogger(TimerThread.class);
    private Image pictures[];
    private int lastcount;

    public TimerThread() {
        _log.info("====INIT===");
        // Thread
        timediff = 1000;

        // Applet
        lastcount = 10;
        pictures = new Image[10];
        MediaTracker tracker = new MediaTracker(this);
        for (int a = 0; a < lastcount; a++) {
            try {
                pictures[a] = ImageIO.read(IOUtil.getInputStream(NameImage.sImge[a]));
            } catch (IOException e) {
                _log.error(e);
            }
            tracker.addImage(pictures[a], 0);
        }
        tracker.checkAll(true);
        shouldRun = true;
        setName("TimerThread(" + timediff + " milliseconds)");
    }

    @Override
    public void start() {
        _log.info("====START===");
        thread = new Thread(this, "TimerThread(" + timediff + " milliseconds)");
        thread.start();
    }

    @Override
    public void stop() {
        _log.info("====STOP===");
        shouldRun = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            _log.error(e);
        }
    }

    @Override
    public void run() {
        _log.info("====RUN===name Thread====" + thread.getName());
        while (shouldRun) {
            try {
                this.repaint();
                Thread.sleep(timediff);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(pictures[count++], 0, 0, null);
        _log.info("====PAINT===count===" + count);
        if (count == lastcount) {
            count = 0;
        }
    }
}
/**
 * ===========void setName(String name) Assigns a name to the Thread instance.
 * <p>
 * ===========String getName() Gets the name of the Thread instance.
 * <p>
 * The Thread class provides a method that allows us to attach a name to the
 * thread object and a method that allows us to retrieve the name. The system
 * does not use this string for any specific purpose, though the name is printed
 * out by the default implementation of the toString() method of the thread. The
 * developer who assigns the name is free to use this string for any purpose
 * desired. For example, let's assign a name to our TimerThread class.
 * <p>
 * ===========Uses for a Thread Name? Using the thread name to store information
 * is not too beneficial. We could just as easily have added an instance
 * variable to the Thread class (if we're threading by inheritance) or to the
 * Runnable type class (if we're threading by interfaces) and achieved the same
 * results. The best use of this name is probably for debugging. With an
 * assigned name, the debugger and the toString() method display thread
 * information in terms of a "logical" name instead of a number.
 * <p>
 * By default, if no name is assigned, the Thread class chooses a unique name.
 * This name is generally "Thread-" followed by a unique number.
 * <p>
 * ===========The naming support is also available as a constructor of the
 * Thread class: ===========Thread(String name). Constructs a thread object with
 * a name that is already assigned. This constructor is used when threading by
 * inheritance. ===========Thread(Runnable target, String name) Constructs a
 * thread object that is associated with the given Runnable object and is
 * created with a name that is already assigned. This constructor is used when
 * threading by interfaces.
 */
