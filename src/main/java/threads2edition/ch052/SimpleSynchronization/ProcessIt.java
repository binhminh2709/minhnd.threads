package threads2edition.ch052.SimpleSynchronization;

public class ProcessIt implements Runnable {

    String is[];
    Barrier bpStart, bp1, bp2, bpEnd;

    public ProcessIt(String sources[]) {
        is = sources;
        bpStart = new Barrier(sources.length);
        bp1 = new Barrier(sources.length);
        bp2 = new Barrier(sources.length);
        bpEnd = new Barrier(sources.length);
        for (int i = 0; i < is.length; i++) {
            (new Thread(this)).start();
        }
    }

    static public void main(String args[]) {
        System.out.println(args.length);
        ProcessIt pi = new ProcessIt(args);
        pi.run();
    }

    @Override
    public void run() {
        // try {
        // int i = bpStart.waitForRest();
        // System.out.println(i);
        // doPhaseOne(is[i]);
        // bp1.waitForRest();
        // doPhaseTwo(is[i]);
        // bp2.waitForRest();
        // doPhaseThree(is[i]);
        // bpEnd.waitForRest();
        // } catch (InterruptedException ex) {
        // ex.printStackTrace();
        // }

        try {
            int i = bpStart.waitForRest();
            doPhaseOne(is[i]);
            if (bp1.waitForRest() == 0) {
                doPhaseTwoSetup();
            }
            bp1.waitForRest();
            doPhaseTwo(is[i]);
            if (bp2.waitForRest() == 0) {
                doPhaseThreeSetup();
            }
            bp2.waitForRest();
            doPhaseThree(is[i]);
            bpEnd.waitForRest();
        } catch (InterruptedException ex) {
        }
    }

    private void doPhaseThreeSetup() {

    }

    private void doPhaseTwoSetup() {

    }

    public void doPhaseOne(String ps) {
    }

    public void doPhaseTwo(String ps) {
    }

    public void doPhaseThree(String ps) {
    }
}
