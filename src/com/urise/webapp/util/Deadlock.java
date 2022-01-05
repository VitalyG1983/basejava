package com.urise.webapp.util;

public class Deadlock {

    public final static Object one = new Object(), two = new Object();
    static boolean oneLocked, twoLocked;

    public static void runThread(Object one, Object two) {

        // Блокировка первого объекта
        synchronized (one) {
            if (one == Deadlock.one) oneLocked = true;
            else twoLocked = true;
            while (!(oneLocked & twoLocked)) {
            }
            // Блокировка второго объекта
            synchronized (two) {
                twoLocked = true;
                System.out.println("Success!");
            }
        }
    }

    public static void main(String[] s) {

        Thread t1 = new Thread(() -> runThread(one, two));
        Thread t2 = new Thread(() -> runThread(two, one));

        t1.start();
        t2.start();
    }
}