package threads2edition.ch035.NestedLocks;

public class BusyFlag {
    protected Thread busyflag = null;
    protected int busycount = 0;

    public void getBusyFlag() {
        while (tryGetBusyFlag() == false) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public synchronized boolean tryGetBusyFlag() {
        if (busyflag == null) {// (1)
            busyflag = Thread.currentThread();
            busycount = 1;// (2)
            return true;
        }
        if (busyflag == Thread.currentThread()) {// (3)
            busycount++;// (4)
            return true;
        }
        return false;
    }

    public synchronized void freeBusyFlag() {
        if (getBusyFlagOwner() == Thread.currentThread()) {// (5)
            busycount--;// (6)
            if (busycount == 0) {
                busyflag = null;
            }
        }
    }

    public synchronized Thread getBusyFlagOwner() {
        return busyflag;
    }
}
