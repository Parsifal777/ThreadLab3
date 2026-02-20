package firstzadanie;

import java.io.File;
import java.io.IOException;

public class DaemonThreadsDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("\nЗадание 1: Потоки-демоны");

        Thread daemonThread = new Thread(() -> {
            try {
                int count = 0;
                while (true) {
                    File tempFile = File.createTempFile("demo", ".tmp");
                    System.out.println("[Демон] Создан временный файл: " + tempFile.getName());

                    if (tempFile.delete()) {
                        System.out.println("[Демон] Файл удален.");
                    }

                    Thread.sleep(5000);
                }
            } catch (InterruptedException | IOException e) {
                System.out.println("[Демон] Прерван или ошибка ввода-вывода.");
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        Thread userThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("[Обычный] Сообщение #" + i);
                    Thread.sleep(2000);
                }
                System.out.println("[Обычный] Завершил работу.");
            } catch (InterruptedException e) {
                System.out.println("[Обычный] Прерван.");
            }
        });

        userThread.start();

        try {
            userThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Main] Основной поток завершен. Демоны должны прекратить работу автоматически.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        System.out.println("Конец задания 1");
    }
}
