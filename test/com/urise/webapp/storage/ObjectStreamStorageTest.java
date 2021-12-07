package com.urise.webapp.storage;

import java.io.File;
import java.util.Objects;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    protected static File STORAGE_DIR;
    static {
        String os = System.getProperty("os.name");
        if (Objects.equals(os, "windows")) STORAGE_DIR = new File(".\\src\\com\\urise\\webapp\\storage\\Saved files");
        else STORAGE_DIR = new File("./src/com/urise/webapp/storage/Saved files");
    }

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}