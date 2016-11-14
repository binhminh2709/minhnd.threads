package threads2edition.ch038.SynchronizedStaticMethod;

public class MyStatic {
    public synchronized static void staticMethod(MyStatic obj) {
        // Class lock acquired
        obj.nonStaticMethod();
        synchronized (obj) {
            // Class and object locks acquired
        }
    }

    public synchronized void nonStaticMethod() {
        // Object lock acquired
    }
}

/**
 * If a synchronized static method has access to an object reference,
 * can it call synchronized methods of that object or use the object to lock a synchronized block ?
 * Yes: in this case the program first acquires the class lock when it calls the synchronized static method and
 * then acquires the object lock of the particular object
 */
