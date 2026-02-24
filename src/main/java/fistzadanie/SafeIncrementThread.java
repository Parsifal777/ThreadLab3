package fistzadanie;

public class SafeIncrementThread implements Runnable {
    private final SharedCounter sharedCounter;
    private final int iterations;

    public SafeIncrementThread(SharedCounter sharedCounter, int iterations) {
        this.sharedCounter = sharedCounter;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            sharedCounter.incrementSafe();
        }
    }
}
