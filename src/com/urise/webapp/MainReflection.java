package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) {
        Resume r = new Resume();
        Class resClass = r.getClass();
        String resName = resClass.getName();
        System.out.println("Имя класса: " + resClass.getName());
        System.out.println("Поля класса: " + Arrays.toString(resClass.getDeclaredFields()));
        System.out.println("Методы класса: ");
        Method[] methods = resClass.getDeclaredMethods();
        for (Method metod : methods) {
            System.out.println(metod);
        }
        System.out.println("Аннотации класса: " + Arrays.toString(resClass.getAnnotations()));
    }
}
