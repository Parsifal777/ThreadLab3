package fourZadanie;

public class ThreadGroupDemo implements Runnable{
    @Override
    public void run() {
        System.out.println("\nЗадание 4: Группы потоков и обработка исключений");

        ThreadGroup factorialGroup = new ThreadGroup("FactorialGroup") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("[Группа " + getName() + "] Исключение в потоке '" + t.getName() + "': " + e.getMessage());
            }
        };

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            int number = Integer.parseInt(threadName.substring(threadName.lastIndexOf("-") + 1)) + 5;

            if (threadName.contains("3")) {
                number = -1;
            }

            System.out.println("[" + threadName + "] Вычисляю факториал для " + number);

            try {
                if (number < 0) {
                    throw new IllegalArgumentException("Число не может быть отрицательным: " + number);
                }
                long result = factorial(number);
                System.out.println("[" + threadName + "] Результат: " + result);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка вычисления: " + e.getMessage());
            }
        };

        for (int i = 1; i <= 4; i++) {
            Thread t = new Thread(factorialGroup, task, "Thread-" + i);
            t.start();
        }

        System.out.println("[Main] Активных потоков в группе '" + factorialGroup.getName() + "': " + factorialGroup.activeCount());

        while (factorialGroup.activeCount() > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        System.out.println("Конец задания 4");
    }

    private static long factorial(int n) {
        if (n == 0 || n == 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
        return result;
    }
}
