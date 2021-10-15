package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Objects;

public class ListStorage extends AbstractStorage{
    protected ArrayList<Resume> storage = new ArrayList<>(STORAGE_LIMIT);

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int searchInd(String uuid) {
        storage.
        storage.indexOf(r);
        return 0;
    }

    @Override
    protected void saveResume(Resume r, int index) {

    }

    @Override
    public void save(Resume r) {
        int index =  storage.indexOf(r);
        if (r.getUuid() == null || Objects.equals(r.getUuid(), "")) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (index>=0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
            System.out.println("storage[" + size + "].uuid= " + r.getUuid());
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int index = searchInd(uuid);
        if (index >= 0) {
            if (index == size - 1) {
                storage[index] = null;
            } else {
                System.arraycopy(storage, index + 1, storage, index, size - index + 1);
                System.out.println("Resume with uuid=" + uuid + " deleted ");
            }
            size--;
        } else throw new NotExistStorageException(uuid);

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }



    @Override
    public void update(Resume resume) {

    }
}