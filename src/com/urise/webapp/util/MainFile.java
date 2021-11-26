package com.urise.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list)
                System.out.println(name);
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


class HW_8_2 {

    public static void print(File[] list, String s) {
        s = s + "  ";
        for (File f : list) {
            if (f.isFile())
                System.out.println(s + f.getName());
        }
        for (File f : list) {
            if (f.isDirectory()) {
                System.out.println(s + f.getName());
                File[] list0 = f.listFiles();
                print(list0, s);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = ".\\.";
        File file = new File(filePath);
        System.out.println("The directory: "+file.getCanonicalPath());
        System.out.println("Contains files: ");
        File[] list = file.listFiles();
        String s = " ";
        HW_8_2.print(list, s);
    }
}