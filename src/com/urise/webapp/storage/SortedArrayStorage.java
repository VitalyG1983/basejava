package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r, int index) {
        //int index = searchInd(r.getUuid());
        if (index < 0) index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index + 1);
        storage[index] = r;
    }

    @Override
    protected int searchInd(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}