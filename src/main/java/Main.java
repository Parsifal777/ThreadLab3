public class Main {
    public static void main(String[] args) {

        firstzadanie.DaemonThreadsDemo task1 = new firstzadanie.DaemonThreadsDemo();
        task1.run();

        secondZadanie.UncaughtExceptionDemo task2 = new secondZadanie.UncaughtExceptionDemo();
        task2.run();

        thirdZadanie.ThreadLocalDemo task3 = new thirdZadanie.ThreadLocalDemo();
        task3.run();

        fourZadanie.ThreadGroupDemo task4 = new fourZadanie.ThreadGroupDemo();
        task4.run();

        System.out.println("\nВсе задания выполнены. Программа завершена");
    }
}
