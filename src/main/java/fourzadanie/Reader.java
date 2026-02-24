package fourzadanie;
import lombok.Getter;
import java.util.Random;

public class Reader implements Runnable {
    @Getter
    private final SharedDictionary dictionary;
    private final int id;
    private final int readsToPerform;
    private final boolean useSynchronization;
    private final int maxKey;
    private int successfulReads = 0;
    private int failedReads = 0;
    private final Random random = new Random();

    public Reader(int id, SharedDictionary dictionary, int readsToPerform,
                  boolean useSynchronization, int maxKey) {
        this.id = id;
        this.dictionary = dictionary;
        this.readsToPerform = readsToPerform;
        this.useSynchronization = useSynchronization;
        this.maxKey = maxKey;
    }

    @Override
    public void run() {
        for (int i = 0; i < readsToPerform; i++) {
            Integer key = random.nextInt(maxKey);

            String value;
            if (useSynchronization) {
                value = dictionary.getSafe(key);
            } else {
                value = dictionary.getUnsafe(key);
            }

            if (value != null) {
                successfulReads++;
            } else {
                failedReads++;
            }

            Thread.yield();
        }

        System.out.println("Читатель " + id + " завершил работу. Успешно: " + successfulReads +
                ", Неудачно: " + failedReads);
    }
}
