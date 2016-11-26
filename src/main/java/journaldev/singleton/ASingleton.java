package journaldev.singleton;

/**
 * Created by nguye on 11/26/16.
 */
public class ASingleton {

    private static ASingleton instance = null;
    private static Object mutex = new Object();

    private ASingleton() {
    }

    public static ASingleton getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) instance = new ASingleton();
            }
        }
        return instance;
    }
}
