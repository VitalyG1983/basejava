package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "1";
    private static final String UUID_2 = "2";
    private static final String UUID_3 = "3";
    private static final String UUID_4 = "4";
    private static final String UUID_5 = "5";
    //  private static final String UUID_6 = "6";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void storageExceptionOverFlow() {
        try {
            //If we save new resume then STORAGE_LIMIT exceeded, then StorageException will be thrown
            for (int i = 3; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            if (storage.size() >= AbstractArrayStorage.STORAGE_LIMIT) {
                System.out.println(e.getMessage());
                System.out.println("Test storageExceptionOverFlow() succesfully catched StorageException. DataBase overflow occured.");
                throw new StorageException("", "");
            }
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        try {
            //If we want to save new resume with already exist UUID, then ExistStorageException will be thrown
            storage.save(new Resume(UUID_3));
        } catch (ExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test saveExistResume() succesfully catched ExistStorageException. Resume already exist DataBase");
            throw new ExistStorageException(UUID_3);
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        try {
            Resume resumeNew = new Resume(UUID_4);
            storage.update(resumeNew);
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test updateNotExistResume() succesfully catched NotExistStorageException. Resume not exist in DataBase");
            throw new NotExistStorageException("");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistResume() {
        try {
            Resume resume = storage.get(UUID_4);
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test getNotExistResume() succesfully catched NotExistStorageException. Resume not exist in DataBase");
            throw new NotExistStorageException("");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        try {
            storage.delete("Not_exist_UUID");
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test deleteNotExistResume() succesfully catched NotExistStorageException. Resume not exist in DataBase");
        }
    }

    @Test(expected = StorageException.class)
    public void storageExceptionThrow() {
        try {
            // If we want to test fail then STORAGE_LIMIT not exceeded
            throw new StorageException("Test StorageException then STORAGE_LIMIT not exceeded", "");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Test storageExceptionThrow() succesfully catched StorageException. DataBase STORAGE_LIMIT not exceeded");
            throw new StorageException("", "");
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
        Resume resume4 = new Resume(UUID_4);
        storage.save(resume4);
        Resume r = storage.get(UUID_4);
        Assert.assertEquals(resume4, r);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void update() {
        Resume resumeNew = new Resume(UUID_1);
        storage.update(resumeNew);
        Assert.assertEquals(resumeNew, storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{storage.get(UUID_1), storage.get(UUID_2), storage.get(UUID_3)};
        Resume[] resumeStorage = storage.getAll();
        Assert.assertArrayEquals(resumes, resumeStorage);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(storage.getAll()[0], resume);
    }
}