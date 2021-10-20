package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int searchInd(String uuid) {
        Resume r = storage.get(uuid);
        if (r == null) return -1;
        else return 0;
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Resume resume, int index) {

    }

    @Override
    protected void deleteResume(int index) {

    }

    @Override
    protected Resume getResume(int index) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
