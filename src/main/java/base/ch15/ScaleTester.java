package base.ch15;

public interface ScaleTester {
    public void init(int nRows, int nCols, int nThreads);

    public float[][] doCalc();
}
