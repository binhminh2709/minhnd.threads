package threads2edition.ch042.WaitAndNotify;

public class ExampleBlockLock {
    private StringBuffer sb = new StringBuffer();

    public void getLock() {
        doSomething(sb);
        synchronized (sb) {
            try {
                sb.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void freeLock() {
        doSomethingElse(sb);
        synchronized (sb) {
            sb.notify();
        }
    }

    private void doSomething(StringBuffer sb) {

    }

    private void doSomethingElse(StringBuffer sb) {

    }
}
