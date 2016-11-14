package threads2edition.ch025.ThreadAccess;

public class AsyncReadSocket extends Thread {

    private StringBuffer result;

    public AsyncReadSocket(String host, int port) {
        // Open a socket to the given host.
    }

    @Override
    public void run() {
        // Read data from a socket into the result string buffer.
    }

    // Get the string already read from the socket so far. Only allows "Reader"
    // threads to execute this method.
    public String getResult() {
        String reader = Thread.currentThread().getName();
        if (reader.startsWith("Reader")) {
            String retval = result.toString();
            result = new StringBuffer();
            return retval;
        } else {
            return "";
        }
    }
}
/**
 * ================The Current Thread ================static Thread
 * currentThread() Gets the Thread object that represents the current thread of
 * execution. The method is static and may be called through the Thread class
 * name.
 * <p>
 * This is a static method of the Thread class, and it simply returns a Thread
 * object that represents the current thread; the current thread is the thread
 * that called the currentThread() method. The object returned is the same
 * Thread object first created for the current thread.
 * <p>
 * But why is this method important? The Thread object for the current thread
 * may not be saved anywhere, and even if it is, it may not be accessible to the
 * called method. For example, let's look at a class that performs socket I/O
 * and stores the data it reads into an internal buffer. We'll show the full
 * implementation of this class in the next chapter, but for now, we're
 * interested only in its interface:
 * <p>
 * Nhung tai sao do la method quan trong, Doi tuong Thread cho current Thread co
 * the ko lu'u' tru~ o? moi noi, va even if it is, no co the ko dat duoc de
 * called method.
 * <p>
 * To retrieve the data that has been read by this class, you must call the
 * getResult() method, but we've coded the getResult() method such that only
 * reader threads are allowed actually to retrieve the stored data. For our
 * example, we are assuming that reader threads are threads whose names start
 * with "Reader." This name could have been assigned by the setName() method
 * earlier or when the threads are constructed. To obtain a name, we need simply
 * to call the getName() method. However, since we do not have the Thread object
 * reference of the caller, we must call the currentThread() method to obtain
 * the reference. In this case, we are using the name of the thread, but we
 * could just as easily have used the thread reference for other purposes. Other
 * uses of the thread reference could be priority control or thread groups;
 * these and other services are described in upcoming chapters.
 */
