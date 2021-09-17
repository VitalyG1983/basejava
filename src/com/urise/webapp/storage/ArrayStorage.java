package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

   public void clear() {
        // ищем готовые резюме и присваиваем им null
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }
    public void save(Resume r) {
        if (r.getUuid() == null || r.getUuid() == "") {
            System.out.println("Введите правильный uuid, а не " + r.getUuid());
        } else {
            storage[size] = r;
            System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
            size++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) return storage[i];
        }
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
            System.out.println("Резюме с uuid=" + uuid + " удалено");
        }
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
}