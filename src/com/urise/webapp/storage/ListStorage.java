package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.ResumeTestData;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
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
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        storage.add(r);
        System.out.println("element(" + (storage.size() - 1) + ").uuid= " + r.getUuid());
    }

    protected void doUpdate(Resume r, Integer index) {
        storage.set(index, r);
    }

    protected void doDelete(Integer index) {
        storage.remove((int) index);
    }

    protected List<Resume> getStorage() {
        return storage;
    }

    @Override
    public Resume doGet(Integer index) {
        return storage.get(index);
    }
}