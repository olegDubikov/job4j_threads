package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread wget = new Thread(
                () -> {
                    try {
                        for (var i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                    System.out.print("Done");
                }
        );
        wget.start();
        System.out.println("Load");
    }
}
