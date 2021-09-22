package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = searchInd(uuid);
        if (index >= 0) return storage[index];
        System.out.println("Resume with uuid= " + uuid + " is not present in Database");
        return null;
    }

    protected abstract int searchInd(String uuid);
}