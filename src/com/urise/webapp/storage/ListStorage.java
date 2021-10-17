package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected final ArrayList<Resume> storage = new ArrayList<>();

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
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveNewResume(Resume r, int index) {
        if (index < 0) storage.add(r);
        System.out.println("element(" + (storage.size() - 1) + ").uuid= " + r.getUuid());
    }

    protected void updateByIndex(Resume r, int index) {
        storage.set(index, r);
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