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
            System.out.println("Not enough space in Data base for save new resume");
        } else {
            //if (size < storage.length)
            storage[size] = r;
            System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
            size++;
        }
    }

    public Resume get(String uuid) {
        int i = searchInd(uuid);
        if (i >= 0) return storage[i];
        else {
            System.out.println("Resume with uuid= " + uuid + " is not present in Database");
            return null;
        }
    }

    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int i = searchInd(uuid);
        if (i >= 0) {
            if (i == size - 1) {
                storage[i] = null;
            } else {
                System.arraycopy(storage, i + 1, storage, i, size - i + 1);
                size--;
                System.out.println("Resume with uuid=" + uuid + " deleted");
            }
        } else System.out.println("Resume with uuid=" + uuid + " is not present in Database");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        // создаем копию массива резюме без null
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int i = searchInd(resume.getUuid());
        if (i >= 0) {
            // resume founded, -> load new resume instead old
            storage[i] = resume;
            System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
        }
    }

    public int searchInd(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid() == uuid) return i;
            }
        }
        return -1;
    }
}