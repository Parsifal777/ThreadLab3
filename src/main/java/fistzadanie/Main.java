package fistzadanie;

public class Main {
    private static final int ITERATIONS = 100;

    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();

        System.out.println("Демонстрация race condition (без синхронизации)");

        // Создаем потоки
        Thread t1 = new Thread(new UnsafeIncrementThread(sharedCounter, ITERATIONS));
        Thread t2 = new Thread(new UnsafeIncrementThread(sharedCounter, ITERATIONS));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int expected = 2 * ITERATIONS;
        System.out.println("Ожидаемое значение: " + expected);
        System.out.println("Фактическое значение: " + sharedCounter.getCounter());
        System.out.println("Потеряно обновлений: " + (expected - sharedCounter.getCounter()));

        sharedCounter.reset();

        System.out.println("\nРешение с синхронизацией");

        // Создаем потоки
        Thread t3 = new Thread(new SafeIncrementThread(sharedCounter, ITERATIONS));
        Thread t4 = new Thread(new SafeIncrementThread(sharedCounter, ITERATIONS));

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Ожидаемое значение: " + expected);
        System.out.println("Фактическое значение: " + sharedCounter.getCounter());
        System.out.println("Потеряно обновлений: " + (expected - sharedCounter.getCounter()));
    }
}
