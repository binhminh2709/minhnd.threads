package threads2edition.ch034.SynchronizedBlock;

public class SynchroizedBlock {

    protected Thread busyflag = null;

    public void getBusyFlag() {
        while (true) {
            synchronized (this) {
                if (busyflag == null) {
                    busyflag = Thread.currentThread();
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}
