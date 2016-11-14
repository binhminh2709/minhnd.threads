package threads2edition.ch025.ThreadAccess;

public class CurrentThreadRead extends Thread {
    AsyncReadSocket asr;

    public CurrentThreadRead(AsyncReadSocket asr) {
        super("ReaderThread");
        this.asr = asr;
    }

    public static void main(String args[]) {
        AsyncReadSocket asr = new AsyncReadSocket("myhost", 6001);
        asr.start();
        new CurrentThreadRead(asr).start();
    }

    public void run() {
        // Do some other processing, and allow asr to read data.
        System.out.println("Data is " + asr.getResult());
    }
}
/**
 * Note that there is a very subtle thing going on here. The getName() method is
 * a method of the Thread class, and we might have called it directly in our
 * code. That would return the name of the AsyncReadSocket thread itself.
 * Instead, what we're after is the name of the thread that has called the
 * getResult() method, which is probably not the AsyncReadSocket thread.
 * Typically, we'd use the AsyncReadSocket class like this
 * <p>
 * There are three threads of interest to us in this example: the thread that
 * the virtual machine started for us that is executing the main() method, the
 * asr thread, and the TestRead thread. Since the TestRead thread is executing
 * the getResult() method, it will actually receive the data, as its name begins
 * with "Reader." If another thread in this example were to call the getResult()
 * method, it would receive merely an empty string.
 * <p>
 * This can be a common source of confusion: methods in subclasses of the thread
 * class may be executed by the thread object itself, or they may - like the
 * get-Result() method in this example - be executed by another thread object.
 * Don't assume that the code in a thread object is only being executed by the
 * specific thread that the object represents.
 */
