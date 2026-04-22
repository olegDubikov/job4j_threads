package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        Thread progress = new Thread(
                () -> {
                    var process = new char[]{'-', '\\', '|', '/'};
                    while (!Thread.currentThread().isInterrupted()) {
                        for (char c : process) {
                            System.out.print("\r load: " + c);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                }
        );
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
        System.out.println();
        System.out.print("...Complete...");
    }

    public static void main(String[] args) {
        ConsoleProgress consoleProgress = new ConsoleProgress();
        consoleProgress.run();
    }
}
