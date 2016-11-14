package threads2edition.ch063.SchedulingWithThreadPriorities;

import java.applet.Applet;
import java.awt.*;

public class Fractal extends Applet implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 5286139864387904105L;
    static int nSections = 10;
    Thread calcThread;
    boolean sectionsToCalculate;

    @Override
    public void start() {
        Thread current = Thread.currentThread();
        calcThread = new Thread(this);
        calcThread.setPriority(current.getPriority() - 1);
        calcThread.start();
    }

    @Override
    public void stop() {
        sectionsToCalculate = false;
    }

    @Override
    public void run() {
        for (int i = 0; i < nSections && sectionsToCalculate; i++) {
            doCalc(i);
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        // Paint the calculated sections.
    }

    void doCalc(int i) {
        // Calculate section i of the fractal.
    }
}
