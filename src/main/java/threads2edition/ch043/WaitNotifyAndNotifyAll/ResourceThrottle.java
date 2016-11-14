package threads2edition.ch043.WaitNotifyAndNotifyAll;

public class ResourceThrottle {

    private int resourceCount = 0;
    private int resourceMax = 1;

    public ResourceThrottle(int max) {
        resourceCount = 0;
        resourceMax = max;
    }

    public synchronized void getResource(int numberof) {
        while (true) {
            if ((resourceCount + numberof) <= resourceMax) {
                resourceCount += numberof;
                break;
            }
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void freeResource(int numberof) {
        resourceCount -= numberof;
        notifyAll();
    }
}
