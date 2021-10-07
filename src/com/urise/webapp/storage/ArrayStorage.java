package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r, int index) {
        storage[size] = r;
        System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
    }

    @Override
    protected int searchInd(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) return i;
        }
        return -1;
    }
}