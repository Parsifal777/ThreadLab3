package secondzadanie;

public class Consumer implements Runnable {
    private final SharedList sharedList;
    private final int itemsToConsume;
    private final boolean useSynchronization;

    public Consumer(SharedList sharedList, int itemsToConsume, boolean useSynchronization) {
        this.sharedList = sharedList;
        this.itemsToConsume = itemsToConsume;
        this.useSynchronization = useSynchronization;
    }

    @Override
    public void run() {
        int consumed = 0;

        while (consumed < itemsToConsume) {
            Integer value = null;

            if (useSynchronization) {
                // Пытаемся получить элемент с синхронизацией
                for (int i = 0; i < sharedList.size(); i++) {
                    value = sharedList.getSafe(i);
                    if (value != null) {
                        consumed++;
                        break;
                    }
                }
            } else {
                // Пытаемся получить элемент без синхронизации
                for (int i = 0; i < sharedList.size(); i++) {
                    value = sharedList.getUnsafe(i);
                    if (value != null) {
                        consumed++;
                        break;
                    }
                }
            }

            if (value == null) {
                Thread.yield();
            }
        }
    }
}
