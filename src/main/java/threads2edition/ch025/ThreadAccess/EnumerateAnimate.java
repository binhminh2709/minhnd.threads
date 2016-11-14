package threads2edition.ch025.ThreadAccess;

import org.apache.log4j.Logger;

import java.applet.Applet;

public class EnumerateAnimate extends Applet {

    private static final long serialVersionUID = 6949182449075041271L;

    private static final Logger _log = Logger.getLogger(EnumerateAnimate.class);

    @Override
    public void init() {
        printThreads();
    }

    // Instance variables and methods not shown
    public void printThreads() {
        Thread ta[] = new Thread[Thread.activeCount()];

        int enumerate = Thread.enumerate(ta);
        _log.info("enumerate===" + enumerate);
        for (int i = 0; i < enumerate; i++) {
            _log.info("Thread " + i + " is " + ta[i].getName());
        }
    }
}
/**
 * =============static int enumerate(Thread threadArray[]) Gets all the thread
 * objects of the program and stores the result into the thread array. The value
 * returned is the number of thread objects stored into the array. The method is
 * static and may be called through the Thread class name.
 * <p>
 * =============static int activeCount() Returns the number of threads in the
 * program. The method is static and may be called through the Thread class name
 * <p>
 * This list is retrieved with the enumerate() method. The developer simply
 * needs to create a Thread array and pass it as a parameter. The enumerate()
 * method stores the thread references into the array and returns the number of
 * thread objects stored; this number is the size of the array parameter or the
 * number of threads in the program, whichever is smaller. ============= In
 * order to size the array for the enumerate() method, we need to determine the
 * number of threads in the program. The activeCount() method can determine the
 * number of threads and size the thread array accordingly. For example, we
 * could add a support method to our Animate applet that prints all the threads
 * in the applet
 * <p>
 * =====================When Is a Thread Active?===================== When is a
 * thread active? At first glance, this seems to be a simple question. Using the
 * isAlive() method, a thread is considered alive during the period between the
 * call to the start() method and a short time period after the stop() method is
 * called. We might consider a thread active if it is alive.
 * <p>
 * However, if the definition of an active thread is a thread whose thread
 * reference appears in the active count returned by the activeCount() method,
 * we would have a different definition of active. A thread reference first
 * appears in the thread array returned by the enumerate() method, and is
 * counted by the activeCount() method, when the thread object is first
 * constructed and not when the thread is started.
 * <p>
 * The thread is removed from the thread array either when the thread is stopped
 * or when the run() method has completed. This means that if a thread object is
 * constructed but is not started, the thread object will not be removed from
 * the enumeration list, even if the original reference to the object is lost.
 * <p>
 * Note that we've been careful in this section to say
 * "all the threads in the program" rather than "all the threads in the virtual
 * machine." That's because at the level of the Thread class, the enumerate()
 * method shows us only the threads that our program has created, plus
 * (possibly) the main and GUI threads of an application or applet that the
 * virtual machine has created for us. It will not show us other threads of the
 * virtual machine (e.g., the garbage collection thread), and in an applet, it
 * will not show us other threads in other applets. We'll see how to examine all
 * these other threads in Chapter 10.
 */
