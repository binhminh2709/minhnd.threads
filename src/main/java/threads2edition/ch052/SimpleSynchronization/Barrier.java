package threads2edition.ch052.SimpleSynchronization;

public class Barrier {
    private int threads2Wait4;
    private InterruptedException iexeption;

    public Barrier(int nThreads) {
        threads2Wait4 = nThreads;
    }

    public synchronized int waitForRest() throws InterruptedException {
        int threadNum = --threads2Wait4;
        if (iexeption != null)
            throw iexeption;
        if (threads2Wait4 <= 0) {
            notifyAll();
            return threadNum;
        }
        while (threads2Wait4 > 0) {
            if (iexeption != null)
                throw iexeption;
            try {
                wait();
            } catch (InterruptedException ex) {
                iexeption = ex;
                notifyAll();
            }
        }
        return threadNum;
    }

    public synchronized void freeAll() {
        iexeption = new InterruptedException("Barrier Released by freeAll");
        notifyAll();
    }
}
