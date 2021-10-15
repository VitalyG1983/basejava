package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void deleteByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index + 1);
    }

    public Resume[] getAll() {
        // Create copy of resumes without 'null'
        return Arrays.copyOf(storage, size);
    }

    @Override
    public Resume getByIndex(int index) {
        return storage[index];
    }
}