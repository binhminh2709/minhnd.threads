package threads2edition.ch044.WaitSleep;

public class WaitExample {
    // Ex1
    public synchronized void processLoopEx1() {
        processOne();
        try {
            wait(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        processTwo();
    }

    // Ex2
    public void processLoopEx2() {
        synchronized (this) {
            processOne();
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (this) {
            processTwo();
        }
    }

    // Ex3
    public synchronized void processLoopEx3() {
        processOne();
        for (int i = 0; i < 50; i++) {
            processTwo();
            try {
                wait(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processTwo() {

    }

    private void processOne() {

    }
}
