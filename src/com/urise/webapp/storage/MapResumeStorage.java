package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// class MapResumeStorage with searchKey = value of the Entry
public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object searchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
        System.out.println("Resume with Key= " + r.getUuid() + " is mapped");
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        Resume res = (Resume) searchKey;
        storage.replace(res.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        Resume res = (Resume) searchKey;
        storage.remove(res.getUuid(), res);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
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
        return storage.size();
    }
}