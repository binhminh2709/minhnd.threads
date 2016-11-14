package threads2edition.ch022.RunnableInterface;

public class OurClass implements Runnable {

    /**
     * The Java language deals with this lack of multiple inheritance by using the
     * mechanism known as interfaces The Runnable interface contains only one
     * method: the run() method. The Thread class actually implements the Runnable
     * interface, when you inherit from the Thread class, your subclass also
     * implements the Runnable interface. However, in this case we want to
     * implement the Runnable interface without actually inheriting from the
     * Thread class. This is achieved by simply substituting the phrase
     * "implements Runnable" for the phrase "extends Thread"; no other changes are
     * necessary in
     * <p>
     * Step 1 of our thread creation process:
     */

    @Override
    public void run() {
        for (int I = 0; I < 100; I++) {
            System.out.println("Hello, from another thread");
        }
    }

}
