package com.urise.webapp.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainFile {
    public static void HW_8_2_outputFiles() throws IOException {
        String filePath = ".\\.";
        File file = new File(filePath);
        List<File> listDirectories = new ArrayList<>();
        System.out.println("The directory:");
        System.out.println(file.getCanonicalPath());
        System.out.println("Contains files: ");
        File[] list = file.listFiles();
        for (File f : list) {
            if (f.isFile())
                System.out.println(f);
            else listDirectories.add(f);
        }

       // File file = new File("path");
        File[] subdirs = file.listFiles(f -> f.isFile());


    }

    public static void main(String[] args) throws IOException {
        MainFile.HW_8_2_outputFiles();
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