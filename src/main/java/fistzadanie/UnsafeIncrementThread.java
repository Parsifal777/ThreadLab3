package fistzadanie;

public class UnsafeIncrementThread implements Runnable {
    private final SharedCounter sharedCounter;
    private final int iterations;

    public UnsafeIncrementThread(SharedCounter sharedCounter, int iterations) {
        this.sharedCounter = sharedCounter;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            sharedCounter.incrementUnsafe();
        }
    }
}
