package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractArrayStorageTest {
     private Storage storage = new ArrayStorage();
   // private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    //public AbstractArrayStorageTest() {
    //}

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
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
    void save()  {
        try {
            Resume resume1 = storage.get(UUID_1);
            Resume resume2 = storage.get(UUID_2);
            Resume resume3 = storage.get(UUID_3);
            Assert.assertEquals(UUID_1, resume1.getUuid());
            Assert.assertEquals(UUID_2, resume2.getUuid());
            Assert.assertEquals(UUID_3, resume3.getUuid());
        } catch (StorageException e) {
            e.getMessage();
        }
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }
}