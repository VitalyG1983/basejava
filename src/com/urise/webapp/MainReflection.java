package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume r = new Resume("reflection UUID");
        Class resClass = r.getClass();
        Method toString = resClass.getDeclaredMethod("toString", null);
        String s = (String) toString.invoke(r, null);
        System.out.println("Metod 'toString' via reflection= " + s);
    }
}