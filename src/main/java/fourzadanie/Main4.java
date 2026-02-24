package fourzadanie;

import java.util.ArrayList;
import java.util.List;

public class Main4 {
    private static final int NUMBER_OF_WRITERS = 3;
    private static final int NUMBER_OF_READERS = 5;
    private static final int ENTRIES_PER_WRITER = 100;
    private static final int READS_PER_READER = 200;
    private static final int MAX_KEY = NUMBER_OF_WRITERS * 1000;

    public static void main(String[] args) throws InterruptedException {
        // Тест 1: Без синхронизации
        System.out.println("ТЕСТ 1: БЕЗ СИНХРОНИЗАЦИИ");
        testWithoutSynchronization();

        // Тест 2: С синхронизацией
        System.out.println("\nТЕСТ 2: С СИНХРОНИЗАЦИЕЙ");
        testWithSynchronization();

        // Тест 3: Сравнение производительности
        System.out.println("\nТЕСТ 3: СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ");
        comparePerformance();
    }

    private static void testWithoutSynchronization() throws InterruptedException {
        SharedDictionary dictionary = new SharedDictionary();
        List<Thread> threads = new ArrayList<>();
        List<Writer> writers = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUMBER_OF_WRITERS; i++) {
            Writer writer = new Writer(i, dictionary, ENTRIES_PER_WRITER, false);
            writers.add(writer);
            Thread t = new Thread(writer, "Writer-" + i);
            threads.add(t);
            t.start();
        }

        for (int i = 0; i < NUMBER_OF_READERS; i++) {
            Reader reader = new Reader(i, dictionary, READS_PER_READER, false, MAX_KEY);
            readers.add(reader);
            Thread t = new Thread(reader, "Reader-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        analyzeResults("БЕЗ СИНХРОНИЗАЦИИ", dictionary, writers, readers, duration);
    }

    private static void testWithSynchronization() throws InterruptedException {
        SharedDictionary dictionary = new SharedDictionary();
        List<Thread> threads = new ArrayList<>();
        List<Writer> writers = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUMBER_OF_WRITERS; i++) {
            Writer writer = new Writer(i, dictionary, ENTRIES_PER_WRITER, true);
            writers.add(writer);
            Thread t = new Thread(writer, "Writer-" + i);
            threads.add(t);
            t.start();
        }

        for (int i = 0; i < NUMBER_OF_READERS; i++) {
            Reader reader = new Reader(i, dictionary, READS_PER_READER, true, MAX_KEY);
            readers.add(reader);
            Thread t = new Thread(reader, "Reader-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        analyzeResults("С СИНХРОНИЗАЦИЕЙ", dictionary, writers, readers, duration);
    }

    private static void comparePerformance() throws InterruptedException {
        SharedDictionary dict1 = new SharedDictionary();
        List<Thread> threads1 = new ArrayList<>();

        long start1 = System.currentTimeMillis();

        for (int i = 0; i < 2; i++) {
            Writer writer = new Writer(i, dict1, 500, false);
            Thread t = new Thread(writer);
            threads1.add(t);
            t.start();
        }

        for (int i = 0; i < 3; i++) {
            Reader reader = new Reader(i, dict1, 1000, false, 2000);
            Thread t = new Thread(reader);
            threads1.add(t);
            t.start();
        }

        for (Thread t : threads1) {
            t.join();
        }

        long end1 = System.currentTimeMillis();
        long duration1 = end1 - start1;

        SharedDictionary dict2 = new SharedDictionary();
        List<Thread> threads2 = new ArrayList<>();

        long start2 = System.currentTimeMillis();

        for (int i = 0; i < 2; i++) {
            Writer writer = new Writer(i, dict2, 500, true);
            Thread t = new Thread(writer);
            threads2.add(t);
            t.start();
        }

        for (int i = 0; i < 3; i++) {
            Reader reader = new Reader(i, dict2, 1000, true, 2000);
            Thread t = new Thread(reader);
            threads2.add(t);
            t.start();
        }

        for (Thread t : threads2) {
            t.join();
        }

        long end2 = System.currentTimeMillis();
        long duration2 = end2 - start2;

        System.out.println("\nСРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ");
        System.out.println("Без синхронизации: " + duration1 + " мс");
        System.out.println("С синхронизацией: " + duration2 + " мс");
        System.out.println("Синхронизация медленнее в " +
                String.format("%.2f", (double)duration2 / duration1) + " раз");

        System.out.println("\nРазмер словаря без синхронизации: " + dict1.size());
        System.out.println("Размер словаря с синхронизацией: " + dict2.size());

        if (dict1.size() < 1000) {
            System.out.println("ПРОБЛЕМА: Данные потеряны без синхронизации!");
        }
        if (dict2.size() == 1000) {
            System.out.println("С синхронизацией все данные сохранены");
        }
    }

    private static void analyzeResults(String testName, SharedDictionary dictionary,
                                       List<Writer> writers, List<Reader> readers, long duration) {
        System.out.println("\nРЕЗУЛЬТАТЫ ТЕСТА: " + testName + " ---");

        int expectedEntries = NUMBER_OF_WRITERS * ENTRIES_PER_WRITER;
        int actualEntries = dictionary.size();

        System.out.println("Ожидаемое количество записей: " + expectedEntries);
        System.out.println("Фактическое количество записей: " + actualEntries);
        System.out.println("Потеряно записей: " + (expectedEntries - actualEntries));

        System.out.println("Время выполнения: " + duration + " мс");

        if (actualEntries < expectedEntries) {
            System.out.println("ПРОБЛЕМА ОБНАРУЖЕНА: Потеря данных при записи!");
            System.out.println("Это race condition - конфликты при параллельной записи");
        } else {
            System.out.println("Данные сохранены полностью");
        }
    }
}
