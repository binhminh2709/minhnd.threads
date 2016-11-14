package threads2edition.ch021.ClassThread;

public class OurClassThread extends Thread {

    @Override
    public void run() {
        for (int I = 0; I < 100; I++) {
            System.out.println("Hello");
        }
    }

    /**
     * Thread() Constructs a thread object using default values for all options
     *
     * void run() The method that the newly created thread will execute.
     * Developers should override this method with the code they want the new
     * thread to run; we'll show the default implementation of the run() method a
     * little further on, but it is essentially an empty method.
     *
     * void start() Creates a new thread and executes the run() method defined in
     * this thread class.
     *
     * To review, creating another thread of control is a two-step process.
     *
     * First, we must create the code that executes in the new thread by
     * overriding the run() method in our subclass. Then we create the actual
     * subclassed object using its constructor (which calls the default
     * constructor of the Thread class in this case) and begin execution of its
     * run() method by calling the start() method of the subclass
     *
     * run() Versus main(). Versus /'və:səs/ In essence, the run() method may be
     * thought of as the main() method of the newly formed thread: a new thread
     * begins execution with the run() method in the same way a program begins
     * execution with the main() method
     *
     * While the main() method receives its arguments from the argv parameter
     * (which is typically set from the command line), the newly created thread
     * must receive its arguments programmatically from the originating thread.
     * Hence, parameters can be passed in via the constructor, static instance
     * variables, or any other technique designed by the developer.
     * */
}
