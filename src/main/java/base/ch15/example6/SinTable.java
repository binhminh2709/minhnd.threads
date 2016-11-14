package base.ch15.example6;

import base.ch15.GuidedLoopHandler;

public class SinTable extends GuidedLoopHandler {
    public float sumValue;
    private float lookupValues[];

    public SinTable() {
        super(0, 360 * 100, 100, 12);
        lookupValues = new float[360 * 100];
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 6 (Two-Stage Reduction Example)");
        System.out.print("Sum: ");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: " + results[0] + ", " + results[1] + ", " + results[2] + ", " + "...");
        System.out.println("Done");
    }

    public void loopDoRange(int start, int end) {
        float sinValue = 0.0f;
        float sumValue = 0.0f;
        for (int i = start; i < end; i++) {
            sinValue = (float) Math.sin((i % 360) * Math.PI / 180.0);
            lookupValues[i] = sinValue * (float) i / 180.0f;
            sumValue += lookupValues[i];
        }
        synchronized (this) {
            this.sumValue += sumValue;
        }
    }

    public float[] getValues() {
        loopProcess();
        System.out.println(sumValue);
        return lookupValues;
    }
}
