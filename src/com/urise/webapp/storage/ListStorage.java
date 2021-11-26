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
        return storage.indexOf(ResumeTestData.createResume(uuid, "fullName"));
    }

    @Override
    protected void saveResume(Resume r, Integer index) {
        storage.add(r);
        System.out.println("element(" + (storage.size() - 1) + ").uuid= " + r.getUuid());
    }

    protected void updateResume(Resume r, Integer index) {
        storage.set(index, r);
    }

    protected void deleteResume(Integer index) {
        storage.remove((int) index);
    }

    protected List<Resume> getStorage() {
        return storage;
    }

    @Override
    public Resume getResume(Integer index) {
        return storage.get(index);
    }
}