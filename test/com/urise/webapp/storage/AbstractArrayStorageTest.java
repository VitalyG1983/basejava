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

    private final Resume resume1 = new Resume(UUID_1);
    private final Resume resume2 = new Resume(UUID_2);
    private final Resume resume3 = new Resume(UUID_3);
    private final Resume resume4 = new Resume(UUID_4);

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        Resume actualResume = storage.get(UUID_4);
        Assert.assertEquals(resume4, actualResume);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageExceptionOverFlow() {
        //If we save new resume then STORAGE_LIMIT exceeded, then StorageException will be thrown
        try {
            for (int i = 0; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            if (storage.size() < AbstractArrayStorage.STORAGE_LIMIT) {
                Assert.fail("StorageException catched is too early, Database not full");
            }
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        //If we want to save new resume with already exist UUID, then ExistStorageException will be thrown
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void update() {
        Resume expectedResume = new Resume(UUID_1);
        storage.update(expectedResume);
        Assert.assertEquals(expectedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete("UUID_4");
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
    public void getAll() {
        Resume[] expectedResumes = new Resume[]{resume1, resume2, resume3};
        Resume[] actualResumes = storage.getAll();
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test
    public void get() {
        Resume actualResume = storage.get(UUID_1);
        Assert.assertEquals(resume1, actualResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistResume() {
        storage.get("UUID_4");
    }
}