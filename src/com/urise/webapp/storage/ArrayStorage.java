package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

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

    public Resume get(String uuid) {
        int index = searchInd(uuid);
        if (index >= 0) return storage[index];
        System.out.println("Resume with uuid= " + uuid + " is not present in Database");
        return null;
    }

    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int index = searchInd(uuid);
        if (index >= 0) {
            if (index == size - 1) {
                storage[index] = null;
            } else {
                System.arraycopy(storage, index + 1, storage, index, size - index + 1);
                size--;
                System.out.println("Resume with uuid=" + uuid + " deleted");
            }
        } else System.out.println("Resume with uuid=" + uuid + " is not present in Database");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        // Create copy of resumes without 'null'
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = searchInd(resume.getUuid());
        if (index >= 0) {
            // resume founded, -> save new resume instead old
            storage[index] = resume;
            System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
        } else System.out.println("Resume with uuid=" + resume.getUuid() + " not founded in Database");
    }

    private int searchInd(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid() == uuid) return i;
            }
        }
        return -1;
    }
}