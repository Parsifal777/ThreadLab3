package secondzadanie;

public class Main2 {
    private static final int PRODUCER_ITEMS = 1000;
    private static final int CONSUMER_ITEMS = 1000;

    public static void main(String[] args) throws InterruptedException {
        SharedList sharedList = new SharedList();
        PerformanceMonitor monitor = new PerformanceMonitor();

        // Тест 1: Без синхронизации
        System.out.println("ТЕСТ 1: БЕЗ СИНХРОНИЗАЦИИ");
        testWithoutSynchronization(sharedList, monitor);

        sharedList.clear();

        // Тест 2: С синхронизацией
        System.out.println("\nТЕСТ 2: С СИНХРОНИЗАЦИЕЙ (SOLUTION)");
        testWithSynchronization(sharedList, monitor);
    }

    private static void testWithoutSynchronization(SharedList sharedList, PerformanceMonitor monitor)
            throws InterruptedException {

        monitor.start();

        Thread producer = new Thread(new Producer(sharedList, PRODUCER_ITEMS, false));
        Thread consumer = new Thread(new Consumer(sharedList, CONSUMER_ITEMS, false));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        monitor.stop();

        int totalItems = sharedList.size();
        monitor.printResults("Без синхронизации", PRODUCER_ITEMS, totalItems);

        if (totalItems < PRODUCER_ITEMS) {
            System.out.println("ОБНАРУЖЕНА ПРОБЛЕМА: Не все элементы были добавлены!");
            System.out.println("Это race condition - потоки мешают друг другу");
        }
    }

    private static void testWithSynchronization(SharedList sharedList, PerformanceMonitor monitor)
            throws InterruptedException {

        monitor.start();

        Thread producer = new Thread(new Producer(sharedList, PRODUCER_ITEMS, true));
        Thread consumer = new Thread(new Consumer(sharedList, CONSUMER_ITEMS, true));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        monitor.stop();

        int totalItems = sharedList.size();
        monitor.printResults("С синхронизацией", PRODUCER_ITEMS, totalItems);

        if (totalItems == PRODUCER_ITEMS) {
            System.out.println("ВСЕ ЭЛЕМЕНТЫ ДОБАВЛЕНЫ КОРРЕКТНО");
        } else {
            System.out.println("ПРОБЛЕМА: Не все элементы добавлены!");
        }
    }
}
