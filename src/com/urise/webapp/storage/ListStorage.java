package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected final ArrayList<Resume> storage = new ArrayList<>(STORAGE_LIMIT);

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    protected int searchInd(String uuid) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return storage.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(Resume r, int index) {
        if (index < 0) storage.add(r);
        else storage.set(index, r);
    }

    protected void deleteByIndex(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public Resume getByIndex(int index) {
        return storage.get(index);
    }
}