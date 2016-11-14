package threads2edition.ch033.PerformSynchronization;

import org.apache.log4j.Logger;

public class BusyFlag {

    private static Logger log = Logger.getLogger(BusyFlag.class);

    protected Thread busyflag = null;

    public void getBusyFlag() {
        while (busyflag != Thread.currentThread()) {
            if (busyflag == null) {
                busyflag = Thread.currentThread();
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public void freeBusyFlag() {
        if (busyflag == Thread.currentThread()) {
            busyflag = null;
        }
    }
}
/**
 * ------------------------------Why Have the BusyFlag Class at
 * All------------------------------ Fixing race conditions using the BusyFlag
 * class seems more like an academic exercise at this moment: why would you then
 * want to use the BusyFlag class in place of the synchronization mechanism ?
 * <p>
 * For all the cases encountered so far, we wouldn't. In other cases, one of the
 * answers lies in the scope of the lock: the synchronization mechanism does not
 * allow us to lock code at certain scopes. We will encounter cases where the
 * scope of the lock cannot be solved by the synchronized mechanism. In
 * addition, the concepts of the BusyFlag class will be useful to implement
 * other mechanisms that we'll be exploring throughout the rest of this book.
 * <p>
 * <p>
 * This BusyFlag class contains two methods. The method getBusyFlag() sits in a
 * loop until it is able to set the busy flag to the current thread. As long as
 * the busy flag is set to another thread, our thread waits for 100
 * milliseconds. As soon as the flag is set to null, our thread sets it to the
 * current thread. The other method, freeBusyFlag(), frees the flag by setting
 * it back to null. This implementation seems to solve the problem simply and
 * elegantly.
 * <p>
 * ---------------------Why do we need to sleep for 100 milliseconds
 * ?--------------------- Because there seems to be no way to detect changes in
 * the flag without a polling loop. However, a polling loop that does not
 * sleep() simply wastes CPU cycles that can be used by other threads. At the
 * other extreme, it takes a minimum of 100 milliseconds to set the busy flag
 * even if no thread is holding the flag in the first place.(13,14) A possible
 * enhancement that addresses this problem may be as simple as making the sleep
 * time a variable, but no matter what we configure the time to be, we will be
 * balancing whether we want to be able to set the flag in a decent amount of
 * time versus the CPU cycles wasted in a polling loop.
 * <p>
 * Boi? vi' duong nhu' khong co cach nao xac dinh (thay doi? in the flag-gia tri
 * ben trong flag) ben ngoai' a polling loop. Tuy nhien, a polling loop khong
 * don gian? sleep() lang~ phi vong' doi CPU, da~ su? dung boi mot other
 * threads. Co the? lam tang, dia chi, van de co the...
 * <p>
 * Dai loai. la khoang thoi gian nho? nhat de? cpu set gia tri cho bien flag.
 * <p>
 * ---------------------Why do we sleep for 100 milliseconds even if the flag is
 * not set ? --------------------- This is actually intentional. There is a race
 * condition between the check to see if the flag is null and setting the flag.
 * If two threads find that the flag is free, they can each set the flag and
 * exit the loop. By calling the sleep() method, we allow the two threads to set
 * busyflag before checking it again in the while loop. This way, only the
 * second thread that sets the flag can exit the loop, and hence exit the
 * getBusyFlag() method.
 */
