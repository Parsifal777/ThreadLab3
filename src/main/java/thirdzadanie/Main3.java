package thirdzadanie;

import java.util.ArrayList;
import java.util.List;

public class Main3 {
    private static final int NUMBER_OF_CLIENTS = 5;
    private static final int OPERATIONS_PER_CLIENT = 20;
    private static final int NUMBER_OF_TRANSFERS = 3;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("БАНКОВСКАЯ СИСТЕМА С СИНХРОНИЗАЦИЕЙ\n");

        BankAccount mainAccount = new BankAccount(1000);

        BankAccount secondAccount = new BankAccount(500);
        BankAccount thirdAccount = new BankAccount(300);

        List<Thread> threads = new ArrayList<>();
        List<Client> clients = new ArrayList<>();

        System.out.println("ЗАПУСК КЛИЕНТОВ");
        for (int i = 1; i <= NUMBER_OF_CLIENTS; i++) {
            Client client = new Client("Клиент-" + i, mainAccount, OPERATIONS_PER_CLIENT);
            clients.add(client);
            Thread t = new Thread(client);
            threads.add(t);
            t.start();
        }

        System.out.println("\nЗАПУСК ПЕРЕВОДОВ МЕЖДУ СЧЕТАМИ");

        TransferTask transfer1 = new TransferTask("Переводчик-1", mainAccount, secondAccount, NUMBER_OF_TRANSFERS);
        TransferTask transfer2 = new TransferTask("Переводчик-2", secondAccount, thirdAccount, NUMBER_OF_TRANSFERS);
        TransferTask transfer3 = new TransferTask("Переводчик-3", thirdAccount, mainAccount, NUMBER_OF_TRANSFERS);

        Thread t1 = new Thread(transfer1);
        Thread t2 = new Thread(transfer2);
        Thread t3 = new Thread(transfer3);

        threads.add(t1);
        threads.add(t2);
        threads.add(t3);

        t1.start();
        t2.start();
        t3.start();

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nИТОГОВЫЙ РЕЗУЛЬТАТ");
        System.out.println("Баланс основного счета: " + mainAccount.getBalance());
        System.out.println("Баланс второго счета: " + secondAccount.getBalance());
        System.out.println("Баланс третьего счета: " + thirdAccount.getBalance());
        System.out.println("Всего транзакций по основному счету: " + mainAccount.getTransactionCount());

        System.out.println("\nСТАТИСТИКА ПЕРЕВОДОВ");
        transfer1.printStatistics();
        transfer2.printStatistics();
        transfer3.printStatistics();

        demonstrateProtection();
    }

    private static void demonstrateProtection() {
        System.out.println("\nДЕМОНСТРАЦИЯ ЗАЩИТЫ ОТ ПОВТОРНЫХ СНЯТИЙ");

        BankAccount testAccount = new BankAccount(100);
        System.out.println("Создан счет с балансом 100");
        System.out.println("Запускаем 2 потока, каждый пытается снять по 80 трижды");
        System.out.println("Ожидаемый результат: только одно снятие должно быть успешным\n");

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                testAccount.withdraw(80, "Поток-1");
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                testAccount.withdraw(80, "Поток-2");
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nИтоговый баланс после попыток снятия: " + testAccount.getBalance());
        System.out.println("(Баланс не может стать отрицательным благодаря synchronized)");

        if (testAccount.getBalance() >= 0) {
            System.out.println("ЗАЩИТА РАБОТАЕТ: баланс не стал отрицательным");
        } else {
            System.out.println("ПРОБЛЕМА: баланс стал отрицательным!");
        }
    }
}
