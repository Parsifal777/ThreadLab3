package thirdZadanie;

public class ThreadLocalDemo implements Runnable{

    public void run() {
        System.out.println("\nЗадание 3: Локальные переменные потока (ThreadLocal)");

        ThreadLocal<Integer>[] threadLocals = new ThreadLocal[2];
        threadLocals[0] = new ThreadLocal<>();
        threadLocals[1] = new ThreadLocal<>();

        Thread thread1 = new Thread(() -> {
            threadLocals[0].set(10);
            threadLocals[1].set(100);

            int val = threadLocals[0].get();
            val += 5;
            System.out.println("[Поток 1] Результат операции (10+5) = " + val);

            int val2 = threadLocals[1].get();
            val2 *= 2;
            System.out.println("[Поток 1] Результат операции (100*2) = " + val2);
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            threadLocals[0].set(20);
            threadLocals[1].set(200);

            int val = threadLocals[0].get();
            val += 10;
            System.out.println("[Поток 2] Результат операции (20+10) = " + val);

            int val2 = threadLocals[1].get();
            val2 *= 3;
            System.out.println("[Поток 2] Результат операции (200*3) = " + val2);
        }, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Конец задания 3");
    }
}
