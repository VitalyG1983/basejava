package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (r.getUuid() == null || r.getUuid() == "") {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (searchInd(r.getUuid()) >= 0) {
            System.out.println("Resume with uuid=" + r.getUuid() + " already exist in Database");
        } else if (size >= storage.length) {
            System.out.println("Not enough space in Database for save new resume");
        } else {
            //if (size < storage.length)
            storage[size] = r;
            System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
            size++;
        }
    }

    protected int searchInd(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) return i;
            }
        }
        return -1;
    }
}