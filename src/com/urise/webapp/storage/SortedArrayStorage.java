package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int index = searchInd(r.getUuid());
        if (r.getUuid() == null || r.getUuid() == "") {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (index >= 0) {
            System.out.println("Resume with uuid=" + r.getUuid() + " already exist in Database");
        } else if (size >= storage.length) {
            System.out.println("Not enough space in Database for save new resume");
            //if (size < storage.length)
        } else {
            if (index < 0) index = -(index + 1);
            System.arraycopy(storage, index, storage, index + 1, size - index + 1);
            storage[index] = r;
            System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
            size++;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    protected int searchInd(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}