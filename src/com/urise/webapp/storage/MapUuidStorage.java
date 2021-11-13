package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    protected boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    protected String searchKey(String uuid) {
        return (storage.containsKey(uuid)) ? uuid : null;
    }

    @Override
    protected void saveResume(Resume r, String searchKey) {
        storage.put(r.getUuid(), r);
        System.out.println("Resume with Key= " + r.getUuid() + " is mapped");
    }

    @Override
    protected void updateResume(Resume resume, String searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume getResume(String key) {
        return storage.get(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    protected List<Resume> getStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.values().size();
    }
}