package fistzadanie;
import lombok.Getter;

public class SharedCounter {
    @Getter
    private int counter = 0;
    private final Object lock = new Object();

    public void incrementUnsafe() {
        int current = counter;
        try { Thread.sleep(1); } catch (InterruptedException e) {}
        counter = current + 1;
    }

    public void incrementSafe() {
        synchronized (lock) {
            int current = counter;
            try { Thread.sleep(1); } catch (InterruptedException e) {}
            counter = current + 1;
        }
    }

    public void reset() {
        counter = 0;
    }
}
