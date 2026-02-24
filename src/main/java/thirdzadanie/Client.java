package thirdzadanie;

import java.util.Random;

public class Client implements Runnable {
    private final String name;
    private final BankAccount account;
    private final int operations;
    private final Random random = new Random();
    private int successfulWithdrawals = 0;
    private int failedWithdrawals = 0;
    private double totalDeposited = 0;
    private double totalWithdrawn = 0;

    public Client(String name, BankAccount account, int operations) {
        this.name = name;
        this.account = account;
        this.operations = operations;
    }

    @Override
    public void run() {
        for (int i = 0; i < operations; i++) {
            // Случайно выбираем операцию: 0 - пополнение, 1 - снятие
            int operationType = random.nextInt(2);
            double amount = 10 + random.nextInt(90);

            if (operationType == 0) {
                account.deposit(amount, name);
                totalDeposited += amount;
            } else {
                boolean success = account.withdraw(amount, name);
                if (success) {
                    successfulWithdrawals++;
                    totalWithdrawn += amount;
                } else {
                    failedWithdrawals++;
                }
            }

            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printStatistics() {
        System.out.println("\nСТАТИСТИКА КЛИЕНТА " + name );
        System.out.println("Всего операций: " + operations);
        System.out.println("Успешных снятий: " + successfulWithdrawals);
        System.out.println("Отказанных снятий: " + failedWithdrawals);
        System.out.println("Всего пополнено: " + totalDeposited);
        System.out.println("Всего снято: " + totalWithdrawn);
        System.out.println("Чистый вклад: " + (totalDeposited - totalWithdrawn));
    }
}
