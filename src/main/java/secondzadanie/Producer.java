package secondzadanie;

public class Producer implements Runnable {
    private final SharedList sharedList;
    private final int itemsToProduce;
    private final boolean useSynchronization;

    public Producer(SharedList sharedList, int itemsToProduce, boolean useSynchronization) {
        this.sharedList = sharedList;
        this.itemsToProduce = itemsToProduce;
        this.useSynchronization = useSynchronization;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            if (useSynchronization) {
                sharedList.addSafe(i);
            } else {
                sharedList.addUnsafe(i);
            }
            Thread.yield();
        }
    }
}
