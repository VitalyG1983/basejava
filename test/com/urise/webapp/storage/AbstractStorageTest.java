package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;
import com.urise.webapp.util.ResumeTestData;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.urise.webapp.storage.AbstractStorage.RESUME_NAME_COMPARATOR;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    public static Config config = Config.get();
    protected static File STORAGE_DIR = config.getStorageDir();
    public static SqlStorage SQL_STORAGE = config.getSqlStorage();
    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString(); //"1";
    private static final String UUID_2 = UUID.randomUUID().toString(); //"2";
    private static final String UUID_3 = UUID.randomUUID().toString();//"3";
    private static final String UUID_4 =UUID.randomUUID().toString();//"4";

    private final Resume resume1 = new Resume(UUID_1, "Alex");
          //  ResumeTestData.createResume(UUID_1, "fullName1", true);     //new Resume(UUID_1, "Alex");
    private final Resume resume2 = ResumeTestData.createResume(UUID_2, "Vit", false);//new Resume(UUID_2, "fullName");
    private final Resume resume3 = ResumeTestData.createResume(UUID_3, "John", false);//new Resume(UUID_3, "fullName");
    private final Resume resume4 = ResumeTestData.createResume(UUID_4, "Roma", false);//new Resume(UUID_4, "fullName");

    public AbstractStorageTest(Storage storage) {
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

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        //If we want to save new resume with already exist UUID, then ExistStorageException will be thrown
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume expectedResume = ResumeTestData.createResume(UUID_2, "fullName1", true);//new Resume(UUID_1, "fullName");
        storage.update(expectedResume);
        assertEquals(expectedResume, storage.get(UUID_2));
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
        expectedResumes.sort(RESUME_NAME_COMPARATOR);

        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void get() {
        Resume actualResume = storage.get(UUID_2);
        assertEquals(resume2, actualResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistResume() {
        storage.get(UUID_4);
    }
}