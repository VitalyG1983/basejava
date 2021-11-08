package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "1";
    private static final String UUID_2 = "2";
    private static final String UUID_3 = "3";
    private static final String UUID_4 = "4";

    private final Resume resume1 = new Resume(UUID_1, "fullName");
    private final Resume resume2 = new Resume(UUID_2, "fullName");
    private final Resume resume3 = new Resume(UUID_3, "fullName");
    private final Resume resume4 = new Resume(UUID_4, "fullName");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

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
        assertEquals(resume4, actualResume);
        assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageExceptionOverFlow() {
        //If we save new resume then STORAGE_LIMIT exceeded, then StorageException will be thrown
        try {
            clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("StorageException catched is too early, Database not full");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        //If we want to save new resume with already exist UUID, then ExistStorageException will be thrown
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume expectedResume = new Resume(UUID_1, "fullName");
        storage.update(expectedResume);
        Assert.assertSame(expectedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete("UUID_4");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = new ArrayList<>();
        expectedResumes.add(resume1);
        expectedResumes.add(resume2);
        expectedResumes.add(resume3);

        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void get() {
        Resume actualResume = storage.get(UUID_1);
        assertEquals(resume1, actualResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistResume() {
        storage.get(UUID_4);
    }
}