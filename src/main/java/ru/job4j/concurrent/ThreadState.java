package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {
        });
        Thread second = new Thread(() -> {
        });
        first.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println("name: " + first.getName() + " state: " + first.getState());
        }
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println("name: " + second.getName() + " state: " + second.getState());
        }
        System.out.println(first.getName() + " " + first.getState() + "\n" + second.getName() + " " + second.getState());
        System.out.println("The job is done!");
    }
}
