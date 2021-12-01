package com.urise.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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


class Recursion {

    public static void print(File file, String s) {
        s = s + "  ";
        File[] list = file.listFiles();
        if (list != null) {
            Arrays.sort(list, (o1, o2) -> {
                if (o1.isFile() & o2.isDirectory())
                    return -1;
                else if (o2.isFile() & o1.isDirectory())
                    return 1;
                else return 0;
            });
            for (File f : list) {
                if (f.isFile())
                    System.out.println(s + f.getName());
                else if (f.isDirectory()) {
                    System.out.println(s + f.getName());
                    print(f, s);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String filePath;
        String os = System.getProperty("os.name");
        if (Objects.equals(os, "windows")) filePath = ".\\.";
        else filePath = "./.";
        File file = new File(filePath);
        System.out.println("The directory: " + file.getCanonicalPath());
        System.out.println("Contains files: ");
        String s = " ";
        Recursion.print(file, s);
    }
}