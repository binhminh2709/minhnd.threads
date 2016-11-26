package journaldev.safe;

/**
 * Created by binhminh on 11/26/2016.
 */
public class HashMapProcessor implements Runnable {

    private String[] strArr = null;

    public HashMapProcessor(String[] m) {
        this.strArr = m;
    }

    public String[] getMap() {
        return strArr;
    }

    @Override
    public void run() {
        processArr(Thread.currentThread().getName());
    }

    private void processArr(String name) {
        for (int i = 0; i < strArr.length; i++) {
            //process data and append thread name
            processSomething(i);
            addThreadName(i, name);
        }
    }

    private void processSomething(int index) {
        // processing some job
        try {
            Thread.sleep(index * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private void addThreadName(int i, String name) {
//        strArr[i] = strArr[i] + ":" + name;
//    }

    /**
     * The String array values are corrupted because shared data and no synchronization.
     * Here is how we can change addThreadName() method to make our program thread safe.
     * */
    private Object lock = new Object();
    private void addThreadName(int i, String name) {
        synchronized(lock){
            strArr[i] = strArr[i] +":"+name;
        }
    }


}
