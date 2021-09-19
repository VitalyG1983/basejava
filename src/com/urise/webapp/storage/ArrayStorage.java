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
        } else if (update(r) == false) {
            if (size == storage.length) {
                System.out.println("Not enough space in Data base for save new resume");
            } else if (size < storage.length) {
                storage[size] = r;
                System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
                size++;
            }
        } else System.out.println("resume with uuid=" + r.getUuid() + " updated");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) return storage[i];
        }
        System.out.println("Resume with uuid= " + uuid + " is not present in Database");
        return null;
    }

    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int delFlag = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                if (i == size - 1) {
                    storage[i] = null;
                    delFlag = 1;
                    break;
                }
                for (int j = i; j < size; j++) {
                    if ((j + 1) < storage.length) {
                        storage[j] = storage[j + 1];
                    }
                }
                delFlag = 1;
                break;
            }
        }
        if (delFlag == 1) {
            size--;
            System.out.println("Resume with uuid=" + uuid + " deleted");
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

    public boolean update(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == resume.getUuid()) {
                // resume founded, -> load new resume instead old
                storage[i] = resume;
                return true;
            }
        }
        return false;
    }
}