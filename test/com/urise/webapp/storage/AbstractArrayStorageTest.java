package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

class AbstractArrayStorageTest {
    private static Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

//    public AbstractArrayStorageTest() {
//        //this(new ArrayStorage());
//    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    void save() {
        Resume resume1 = storage.get(UUID_1);
        Resume resume2 = storage.get(UUID_2);
        Resume resume3 = storage.get(UUID_3);
        Assert.assertEquals(UUID_1, resume1.getUuid().intern());
        Assert.assertEquals(UUID_2, resume2.getUuid().intern());
        Assert.assertEquals(UUID_3, resume3.getUuid().intern());
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    void update() {
        Resume resumeNew = new Resume(UUID_1);
        storage.update(resumeNew);
        Assert.assertEquals(resumeNew, storage.get(UUID_1));
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    public static void main(String... args) {
        AbstractArrayStorageTest abstractArrayStorageTest = new AbstractArrayStorageTest(new ArrayStorage());
        try {
            abstractArrayStorageTest.setUp();
            abstractArrayStorageTest.size();
        } catch (Exception e) {
            e.getMessage();
        }
        abstractArrayStorageTest.save();
        abstractArrayStorageTest.delete();
        abstractArrayStorageTest.update();
        abstractArrayStorageTest.clear();

    }
}