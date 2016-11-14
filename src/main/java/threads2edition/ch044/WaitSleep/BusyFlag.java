package threads2edition.ch044.WaitSleep;

public class BusyFlag {

    protected Thread busyflag = null;
    protected int busycount = 0;

    public synchronized void getBusyFlag() {
        while (tryGetBusyFlag() == false) {
            try {
                //TODO BusyFlag NOTE
                wait(100);
                doSomethingElse();
                //Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    private void doSomethingElse() {

    }

    public synchronized boolean tryGetBusyFlag() {
        if (busyflag == null) {
            busyflag = Thread.currentThread();
            busycount = 1;
            return true;
        }
        if (busyflag == Thread.currentThread()) {
            busycount++;
            return true;
        }
        return false;
    }

    public synchronized void freeBusyFlag() {
        if (getBusyFlagOwner() == Thread.currentThread()) {
            busycount--;
            if (busycount == 0) {
                busyflag = null;
                notify();
            }
        }
    }

    public synchronized Thread getBusyFlagOwner() {
        return busyflag;
    }
}
