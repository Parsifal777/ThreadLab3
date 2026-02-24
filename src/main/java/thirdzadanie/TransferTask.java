package thirdzadanie;

import java.util.Random;

public class TransferTask implements Runnable {
    private final String clientName;
    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final int transfers;
    private final Random random = new Random();
    private int successfulTransfers = 0;
    private int failedTransfers = 0;

    public TransferTask(String clientName, BankAccount fromAccount, BankAccount toAccount, int transfers) {
        this.clientName = clientName;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transfers = transfers;
    }

    @Override
    public void run() {
        for (int i = 0; i < transfers; i++) {
            double amount = 5 + random.nextInt(45);

            boolean success = fromAccount.transferTo(toAccount, amount, clientName);

            if (success) {
                successfulTransfers++;
            } else {
                failedTransfers++;
            }

            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printStatistics() {
        System.out.println("Успешных переводов: " + successfulTransfers);
        System.out.println("Отказанных переводов: " + failedTransfers);
    }
}
