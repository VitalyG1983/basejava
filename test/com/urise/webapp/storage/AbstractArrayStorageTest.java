package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    static Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest() {
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void overFlowAndFail() {
        try {
            //If we save new resume then STORAGE_LIMIT exceeded, then StorageException will be thrown
            storage.save(new Resume(UUID_4));
            // If we want to test fail then STORAGE_LIMIT not exceeded
            if (storage.size() <= AbstractArrayStorage.STORAGE_LIMIT)
                Assert.fail("StorageException thrown is too early, DataBase not full");
            //If we want to save new resume with already exist UUID, then ExistStorageException will be thrown
            storage.save(new Resume(UUID_4));
        } catch (ExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test overFlowAndFail() succesfully catched ExistStorageException. Resume already exist DataBase");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test overFlowAndFail() succesfully catched StorageException. DataBase overflow occured.");
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() throws StorageException {
        Resume resume1 = storage.get(UUID_1);
        Resume resume2 = storage.get(UUID_2);
        Resume resume3 = storage.get(UUID_3);
        Assert.assertEquals(new Resume(UUID_1), resume1);
        Assert.assertEquals(new Resume(UUID_2), resume2);
        Assert.assertEquals(new Resume(UUID_3), resume3);
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void delete() {
        try {
            storage.delete(UUID_2);
            // Testing size after resume was removed
            Assert.assertEquals(2, storage.size());
            //If we want to delete the resume which are not exist, then NotExistStorageException will be thrown
            storage.delete("Not_exist_UUID");
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test overFlowAndFail() succesfully catched NotExistStorageException. Resume not exist in  DataBase");
        }
    }

    @Test
    public void update() {
        Resume resumeNew = new Resume(UUID_1);
        storage.update(resumeNew);
        Assert.assertEquals(resumeNew, storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] resumeArray = storage.getAll();
        Assert.assertEquals(storage.size(), resumeArray.length);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(storage.getAll()[0], resume);
    }
}