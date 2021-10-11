package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract int searchInd(String uuid);

    protected abstract void saveResume(Resume r, int index);

    public int size() {
        return size;
    }

    public void clear() {
        // fill 'null' instead real resumes
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = searchInd(r.getUuid());
        if (r.getUuid() == null || Objects.equals(r.getUuid(), "")) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (size >= storage.length) {
            throw new StorageException("Not enough space in Database for save new resume ", r.getUuid());
        } else {
            saveResume(r, index);
            System.out.println("storage[" + size + "].uuid= " + storage[size].getUuid());
            size++;
        }
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
        } else throw new NotExistStorageException(uuid);
    }

    public void update(Resume resume) {
        int index = searchInd(resume.getUuid());
        if (index >= 0) {
            // resume founded, -> save new resume instead old
            storage[index] = resume;
            System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
        } else throw new NotExistStorageException(resume.getUuid());
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        // Create copy of resumes without 'null'
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int index = searchInd(uuid);
        if (index >= 0) return storage[index];
        throw new NotExistStorageException(uuid);
    }
}