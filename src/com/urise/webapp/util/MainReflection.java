package com.urise.webapp.util;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume r = new Resume("reflection UUID", "fullName");
        Class<? extends Resume> resClass = r.getClass();
        Method toString = resClass.getDeclaredMethod("toString");
        String s = (String) toString.invoke(r);
        System.out.println("Metod 'toString' via reflection= " + s);
    }
}