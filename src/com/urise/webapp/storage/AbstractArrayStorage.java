package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    final Resume[] storage = new Resume[STORAGE_LIMIT];
    static final int STORAGE_LIMIT = 10_000;
    int size;

    protected abstract void saveByIndex(Resume r, int index);

    public int size() {
        return size;
    }

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveNewResume(Resume r, int index) {
        if (size >= AbstractArrayStorage.STORAGE_LIMIT) {
            throw new StorageException("Not enough space in Database for save new resume ", r.getUuid());
        }
        saveByIndex(r, index);
        System.out.println("storage[" + size + "].uuid= " + r.getUuid());
        size++;
    }

    protected void deleteByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    public Resume[] getAll() {
        // Create copy of resumes without 'null'
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void updateByIndex(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public Resume getByIndex(int index) {
        return storage[index];
    }
}