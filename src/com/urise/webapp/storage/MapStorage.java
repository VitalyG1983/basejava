package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int searchInd(String uuid) {
        boolean isKey = storage.containsKey(uuid);
        if (isKey) return 0;
        else return -1;
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}