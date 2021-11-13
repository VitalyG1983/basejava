package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size;

    protected abstract void saveToArray(Resume r, int index);

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume r, Object index) {
        if (size >= AbstractArrayStorage.STORAGE_LIMIT) {
            throw new StorageException("Not enough space in Database for save new resume ", r.getUuid());
        }
        saveToArray(r, (int) index);
        System.out.println("storage[" + size + "].uuid= " + r.getUuid());
        size++;
    }

    protected void deleteResume(Object index) {
        System.arraycopy(storage, (int) index + 1, storage, (int) index, size - (int) index);
        size--;
    }

    protected List<Resume> getStorage() {
        // Create copy of resumes without 'null'
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void updateResume(Resume r, Object index) {
        storage[(int) index] = r;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }
}