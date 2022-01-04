package com.urise.webapp.util;

public class Deadlock {
    // Два объекта-ресурса
    public final static Object one = new Object(), two = new Object();

    public static void main(String s[]) {

        // Создаем два потока, которые будут
        // конкурировать за доступ к объектам
        // one и two
        Thread t1 = new Thread() {
            public void run() {
                // Блокировка первого объекта
                synchronized (one) {
                    Thread.yield();
                    // Блокировка второго объекта
                    synchronized (two) {
                        System.out.println("Success!");
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                // Блокировка второго объекта
                synchronized (two) {
                    Thread.yield();
                    // Блокировка первого объекта
                    synchronized (one) {
                        System.out.println("Success!");
                    }
                }
            }
        };

        // Запускаем потоки
        t1.start();
        t2.start();
    }
}