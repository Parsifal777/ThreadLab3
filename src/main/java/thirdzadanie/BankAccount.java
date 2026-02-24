package thirdzadanie;

import lombok.Getter;

public class BankAccount {
    @Getter
    private double balance;
    private final Object lock = new Object();
    private int transactionCount = 0;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Пополнение счета с синхронизацией
    public void deposit(double amount, String clientName) {
        synchronized (lock) {
            System.out.printf("[%s] Попытка пополнить счет на %.2f. Текущий баланс: %.2f%n",
                    clientName, amount, balance);

            balance += amount;
            transactionCount++;

            System.out.printf("[%s] Пополнение успешно. Новый баланс: %.2f%n",
                    clientName, balance);
        }
    }

    // Снятие со счета с проверкой достаточности средств
    public boolean withdraw(double amount, String clientName) {
        synchronized (lock) {
            System.out.printf("[%s] Попытка снять %.2f. Текущий баланс: %.2f%n",
                    clientName, amount, balance);

            if (amount <= balance) {
                balance -= amount;
                transactionCount++;
                System.out.printf("[%s] Снятие успешно. Новый баланс: %.2f%n",
                        clientName, balance);
                return true;
            } else {
                System.out.printf("[%s] ОТКАЗ: Недостаточно средств. Баланс: %.2f, Запрос: %.2f%n",
                        clientName, balance, amount);
                return false;
            }
        }
    }

    public boolean transferTo(BankAccount targetAccount, double amount, String clientName) {
        synchronized (this) {
            synchronized (targetAccount) {
                System.out.printf("[%s] Попытка перевести %.2f с текущего счета на другой%n",
                        clientName, amount);

                if (amount <= balance) {
                    this.balance -= amount;
                    targetAccount.balance += amount;
                    transactionCount++;
                    System.out.printf("[%s] Перевод успешен. Баланс отправителя: %.2f%n",
                            clientName, this.balance);
                    return true;
                } else {
                    System.out.printf("[%s] ОТКАЗ: Недостаточно средств для перевода%n", clientName);
                    return false;
                }
            }
        }
    }

    public int getTransactionCount() {
        synchronized (lock) {
            return transactionCount;
        }
    }

    public void printBalance() {
        synchronized (lock) {
            System.out.println("=== ТЕКУЩИЙ БАЛАНС: " + balance + " ===");
        }
    }
}
