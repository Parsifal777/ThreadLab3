package secondZadanie;

import java.util.Random;

public class UncaughtExceptionDemo implements Runnable{
    @Override
    public void run() {
        System.out.println("\nЗадание 2: Обработчики неконтролируемых исключений");

        ThreadGroup group = new ThreadGroup("Group2") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("[Групповой обработчик] Поймано исключение в потоке '" + t.getName() + "': " + e.getMessage());
            }
        };

        Thread threadWithInternalHandler = new Thread(group, () -> {
            try {
                Random rand = new Random();
                if (rand.nextBoolean()) {
                    throw new RuntimeException("Случайное исключение ВНУТРИ потока (обработано локально)");
                }
                System.out.println("[Внутренний] Поток выполнился без исключений.");
            } catch (RuntimeException e) {
                System.out.println("[Внутренний] Исключение перехвачено внутри потока: " + e.getMessage());
            }
        }, "Thread-Internal");

        Thread threadWithGroupHandler = new Thread(group, () -> {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                throw new RuntimeException("Случайное исключение в потоке (обработано группой)");
            }
            System.out.println("[Групповой] Поток выполнился без исключений.");
        }, "Thread-GroupHandled");

        threadWithInternalHandler.start();
        threadWithGroupHandler.start();

        try {
            threadWithInternalHandler.join();
            threadWithGroupHandler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Конец задания 2");
    }
}
