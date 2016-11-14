package threads2edition.ch033.PerformSynchronization;

public class PerformSynchronization {
    /**
     * Why do we need a new keyword to solve a race condition? Could we reengineer
     * our algorithms so that race conditions do not exist?
     *
     * Let's see if we can reengineer the AsyncReadSocket class not to have a race
     * condition by using trial and error (obviouslynot the best programming
     * technique, but one that is useful for our purposes) We'll conclude that it
     * is impossible to solve a race condition without direct support from the
     * virtual machine, because everything that we might try requires two
     * operations: testing and setting variable. Without some process in the
     * virtual machine to ensure that nothing happens to the variable after it is
     * tested and before it is set, a race condition can occur. But the
     * investigation into a possible way to avoid the race condition will provide
     * us with an important tool for our later use—the BusyFlag class.
     *
     * At first glance, the easiest way to make sure that the two threads do not
     * try to change the result variable, or any buffer at the same time, is to
     * use the concept of a busy flag: if a thread needs to access the result
     * variable, it must set the flag to busy. If the flag is already busy, the
     * thread must wait until the flag is free, at which point it must set the
     * flag to busy. The thread is then free to access the buffer without fear of
     * it being accessed by the other thread. Once the task is completed, the
     * thread must set the flag to not busy.
     *
     *
     * =========================Why Have the BusyFlag Class at All?=========================
     */
}
