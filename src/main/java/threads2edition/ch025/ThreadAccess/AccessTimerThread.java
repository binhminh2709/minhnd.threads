package threads2edition.ch025.ThreadAccess;

import org.apache.log4j.Logger;

import java.awt.*;

public class AccessTimerThread extends Thread {

    private static final Logger _log = Logger.getLogger(AccessTimerThread.class);

    Component comp; // Component that needs repainting
    int timediff; // Time between repaints of the component
    volatile boolean shouldRun; // Set to false to stop thread

    public AccessTimerThread(Component comp, int timediff) {
        _log.info("====isAlive====" + this.isAlive());
        this.comp = comp;
        this.timediff = timediff;
        shouldRun = true;
        _log.info("====isAlive====" + this.isAlive());
    }

    public void run() {
        _log.info("====isAlive====" + this.isAlive());
        while (shouldRun) {
            try {
                comp.repaint();
                Thread.sleep(timediff);
                // or sleep(timediff);
            } catch (Exception e) {
            }
        }
        _log.info("====isAlive====" + this.isAlive());
    }

    /**
     * In this example, the TimerThread class, just like the OurClass class,
     * inherits from the Thread class and overrides the run() method.
     *
     * Its constructor stores the component on which to call the repaint() method
     * and the requested time interval between the calls to the repaint() method
     *
     * What we have not seen so far is the call to the sleep()method:
     *
     * static void sleep (long milliseconds) Puts the currently executing thread
     * to sleep for the specified number of milliseconds. This method is static
     * and may be accessed through the Thread class name.
     *
     * static void sleep (long milliseconds, int nanoseconds) Puts the currently
     * executing thread to sleep for the specified number of milliseconds and
     * nanoseconds. This method is static and may be accessed through the Thread
     * class name.
     * */
}
