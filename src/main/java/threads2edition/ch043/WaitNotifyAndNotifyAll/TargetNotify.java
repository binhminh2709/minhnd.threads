package threads2edition.ch043.WaitNotifyAndNotifyAll;

public class TargetNotify {
    private Object targets[] = null;

    public TargetNotify(int numberOfTargets) {
        targets = new Object[numberOfTargets];
        for (int i = 0; i < numberOfTargets; i++) {
            targets[i] = new Object();
        }
    }

    public void wait(int targetNumber) {
        synchronized (targets[targetNumber]) {
            try {
                targets[targetNumber].wait();
            } catch (Exception e) {
            }
        }
    }

    public void notify(int targetNumber) {
        synchronized (targets[targetNumber]) {
            targets[targetNumber].notify();
        }
    }
}
