package com.urise.webapp.util;

public class Deadlock {
    public final static Object one = new Object(), two = new Object();

    public static void main(String[] s) {
        createThread(one, two);
        createThread(two, one);
    }

    public static boolean objectOne(Object one) {
        return one == Deadlock.one;
    }

    public static void createThread(Object one, Object two) {
        new Thread(() -> {
            try {
                runThread(one, two);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String printThread(String thread, boolean objectOne, boolean synchronizedFlag) {
        String print = !synchronizedFlag ? (thread + " try to block Object") : (thread + " blocked Object");
        return objectOne ? print + " one" : print + " two";
    }

    public static void runThread(Object one, Object two) throws InterruptedException {
        System.out.println(printThread(Thread.currentThread().getName(), objectOne(one), false));
        // Блокировка первого объекта
        synchronized (one) {
            System.out.println(printThread(Thread.currentThread().getName(), objectOne(one), true));
            Thread.sleep(50);
            // Блокировка второго объекта
            synchronized (two) {
                System.out.println(printThread(Thread.currentThread().getName(), objectOne(one), true));
            }
        }
    }
}