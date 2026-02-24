package secondzadanie;

public class PerformanceMonitor {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getElapsedTimeMs() {
        return (endTime - startTime) / 1_000_000; // Конвертируем в миллисекунды
    }

    public void printResults(String testName, int expectedItems, int actualItems) {
        System.out.println("\n--- " + testName);
        System.out.println("Ожидаемое количество элементов: " + expectedItems);
        System.out.println("Фактическое количество элементов: " + actualItems);
        System.out.println("Потеряно элементов: " + (expectedItems - actualItems));
        System.out.println("Время выполнения: " + getElapsedTimeMs() + " мс");
    }
}
