package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size;

    protected abstract void saveToArray(Resume r, int index);

    public int size() {
        return size;
    }

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume r, int index) {
        if (size >= AbstractArrayStorage.STORAGE_LIMIT) {
            throw new StorageException("Not enough space in Database for save new resume ", r.getUuid());
        }
        saveToArray(r, index);
        System.out.println("storage[" + size + "].uuid= " + r.getUuid());
        size++;
    }

    protected void deleteResume(String uuid) {
        int index = searchInd(uuid);
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    public Resume[] getAll() {
        // Create copy of resumes without 'null'
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public Resume getResume(String uuid) {
        int index = searchInd(uuid);
        return storage[index];
    }
}