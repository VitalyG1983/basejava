package com.urise.webapp.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    protected static Path STORAGE_DIRR;
    static {
        String os = System.getProperty("os.name");
        if (Objects.equals(os, "windows")) STORAGE_DIRR = Paths.get(".\\src\\com\\urise\\webapp\\storage\\Saved files");
        else STORAGE_DIRR = Paths.get("./src/com/urise/webapp/storage/Saved files");
    }

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIRR));
    }
}