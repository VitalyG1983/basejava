package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

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
    protected void saveResume(Resume r, int index) {
        storage.add(r);
        System.out.println("element(" + (storage.size() - 1) + ").uuid= " + r.getUuid());
    }

    protected void updateResume(Resume r, int index) {
        storage.set(index, r);
    }

    protected void deleteResume(Object index) {
          storage.remove((int)index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public Resume getResume(Object index) {
        return storage.get((int)index);
    }
}