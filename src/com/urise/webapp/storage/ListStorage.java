package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object searchKey(String uuid) {
        return storage.indexOf(new Resume(uuid, null));
    }

    @Override
    protected void saveResume(Resume r, Object index) {
        storage.add(r);
        System.out.println("element(" + (storage.size() - 1) + ").uuid= " + r.getUuid());
    }

    protected void updateResume(Resume r, Object index) {
        storage.set((int) index, r);
    }

    protected void deleteResume(Object index) {
        storage.remove((int) index);
    }

    protected List<Resume> getStorage() {
        return storage;
    }

    @Override
    public Resume getResume(Object index) {
        return storage.get((int) index);
    }
}