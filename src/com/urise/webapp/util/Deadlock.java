package com.urise.webapp.util;

public class Deadlock {
    public final static Object one = new Object(), two = new Object();

    public static void main(String[] s) {
        createThread(one, two);
        createThread(two, one);
    }

    public static String checkOne(Object one) {
        return one == Deadlock.one ? " one" : " two";
    }

    public static void createThread(Object one, Object two) {
        new Thread(() -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " try to block Object" + checkOne(one));
                // Блокировка первого объекта
                synchronized (one) {
                    System.out.println(threadName + " blocked Object" + checkOne(one));
                    Thread.sleep(50);
                    // Блокировка второго объекта
                    synchronized (two) {
                        System.out.println(threadName + " blocked Object" + checkOne(one));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}