package threads2edition.ch022.RunnableInterface;

import java.applet.Applet;

public class OurApplet_EX1 extends Applet {

    private static final long serialVersionUID = 5374063800925526698L;

    public void init() {
        Runnable oc = new OurClass();
        Thread thread = new Thread(oc);
        thread.start();
    }

    /**
     * We create this object by passing our runnable OurClass object reference to
     * the constructor of the thread using a new constructor of the Thread class.
     *
     * Thread(Runnable target). Constructs a new thread object associated with the
     * given Runnable object.
     *
     * The new Thread object's start() method is called to begin execution of the
     * new thread of control.
     *
     * The reason we need to pass the runnable object to the thread object's
     * constructor is that the thread must have some way to get to the run()
     * method we want the thread to execute. Since we are no longer overriding the
     * run()method of the Thread class, the default run()method of the Thread
     * class is executed; this default run() method looks like this:
     *
     * public void run() { if (target != null) { target.run(); } }
     *
     * Here, target is the runnable object we passed to the thread's constructor
     */
}
