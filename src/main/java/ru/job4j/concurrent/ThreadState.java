package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {
        });
        Thread second = new Thread(() -> {
        });
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("name: " + first.getName() + " state: " + first.getState());
            System.out.println("name: " + second.getName() + " state: " + second.getState());
        }
        System.out.println(first.getName() + " " + first.getState()
                + System.lineSeparator() + second.getName() + " " + second.getState());
        System.out.println("The job is done!");
    }
}
