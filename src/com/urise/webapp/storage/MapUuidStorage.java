package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object searchKey(String uuid) {
        return (storage.containsKey(uuid)) ? uuid : null;
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
        System.out.println("Resume with Key= " + r.getUuid() + " is mapped");
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get((String) key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        resumes.sort(RESUME_NAME_COMPARATOR);
        return resumes;
        //return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.values().size();
    }
}