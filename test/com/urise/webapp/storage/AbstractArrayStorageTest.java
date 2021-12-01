package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void storageExceptionOverFlow() {
        //If we save new resume then STORAGE_LIMIT exceeded, then StorageException will be thrown
        try {
            clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Test"));
            }
        } catch (StorageException e) {
            fail("StorageException catched is too early, Database not full");
        }
        storage.save(new Resume("Test"));
    }
}