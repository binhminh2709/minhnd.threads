package threads2edition.ch025.ThreadAccess;

import java.applet.Applet;

public class MoreStartingStoppingJoining extends Applet {

    AccessTimerThread t;

    public void start() {
        if (t == null)
            t = new AccessTimerThread(this, 500);
        t.start();
    }

    public void stop() {
        t.shouldRun = false;
        try {
            t.join();
        } catch (InterruptedException e) {
        }
        // t = null;
    }
}

/**
 * Two ways a thread can be stopped - c1: t.join() - c2: t = null;
 */
