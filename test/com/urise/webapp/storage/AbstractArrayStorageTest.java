package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

class AbstractArrayStorageTest {
    private static Storage storage= new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest() {
        //this(new ArrayStorage());
    }

//    public AbstractArrayStorageTest(Storage storage) {
//        this.storage = storage;
//    }

    @BeforeEach
    public void setUp() throws StorageException {
        storage.clear();
        try {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            storage.save(new Resume(UUID_3));
         //   Assertions.fail("StorageException thrown is too early");
        } catch (Exception e) {
            Class excClass = e.getClass();
            String exName = excClass.getName();
            if (exName.intern() == "StorageException" & storage.size() < 3) {
                Assertions.fail("StorageException thrown is too early");
            }
        }
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test (Exception)
    void save() throws AssertionFailedError {
        Resume resume1 = storage.get(UUID_1);
        Resume resume2 = storage.get(UUID_2);
        Resume resume3 = storage.get(UUID_3);
        Assertions.assertEquals(new Resume(UUID_1), resume1);
        Assertions.assertEquals(new Resume(UUID_2), resume2);
        Assertions.assertEquals(new Resume(UUID_3), resume3);
        Assertions.assertEquals(3, storage.size());
        Assertions.fail("StorageException thrown is too early");
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void update() {
        Resume resumeNew = new Resume(UUID_1);
        storage.update(resumeNew);
        Assertions.assertEquals(resumeNew, storage.get(UUID_1));
    }

    @Test
    void getAll() {
        Resume[] resumeArray = storage.getAll();
        Assertions.assertEquals(2, resumeArray.length);
    }

    @Test
    void get() {
        Resume resume = storage.get(UUID_1);
        Assertions.assertEquals(storage.getAll()[0], resume);
    }

    private void fail(String exception_not_thrown) {
    }

 //   public static void main(String... args) {
//       // AbstractArrayStorageTest abstractArrayStorageTest = new AbstractArrayStorageTest(new ArrayStorage());
//        // try {
//        abstractArrayStorageTest.setUp();
//        abstractArrayStorageTest.size();
//        // } catch (Exception e) {
//        //      e.getMessage();
//        //  }
//        abstractArrayStorageTest.save();
//        abstractArrayStorageTest.delete();
//        abstractArrayStorageTest.update();
//        abstractArrayStorageTest.getAll();
//        abstractArrayStorageTest.get();
//        abstractArrayStorageTest.clear();
//    }
}