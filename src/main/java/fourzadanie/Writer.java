package fourzadanie;
import lombok.Getter;

public class Writer implements Runnable {
    @Getter
    private final SharedDictionary dictionary;
    private final int id;
    private final int entriesToWrite;
    private final boolean useSynchronization;
    private int successfulWrites = 0;

    public Writer(int id, SharedDictionary dictionary, int entriesToWrite, boolean useSynchronization) {
        this.id = id;
        this.dictionary = dictionary;
        this.entriesToWrite = entriesToWrite;
        this.useSynchronization = useSynchronization;
    }

    @Override
    public void run() {
        for (int i = 0; i < entriesToWrite; i++) {
            Integer key = id * 1000 + i;
            String value = "Значение от писателя " + id + " - " + i;

            if (useSynchronization) {
                dictionary.putSafe(key, value);
            } else {
                dictionary.putUnsafe(key, value);
            }

            successfulWrites++;

            Thread.yield();
        }

        System.out.println("Писатель " + id + " завершил работу. Записано: " + successfulWrites);
    }
}
