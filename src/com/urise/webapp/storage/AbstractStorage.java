package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int searchKey(String uuid);

    protected abstract void saveResume(Resume r, int searchKey);

    protected abstract void updateResume(Resume resume, int searchKey);

    protected abstract void deleteResume(Object o);

    protected abstract Resume getResume(Object o);

    protected int checkKey(String uuid, boolean save) {
        int searchKey = searchKey(uuid);
        if (save) {
            if (searchKey >= 0) throw new ExistStorageException(uuid);
        } else if (searchKey < 0) ;//throw new NotExistStorageException(uuid);
        return searchKey;
    }

    public void save(Resume r) {
        if (r.getUuid() == null || r.getUuid().isBlank()) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else saveResume(r, checkKey(r.getUuid(), true));
    }

    public void delete(String uuid) {
        int searchKey = checkKey(uuid, false);
        if (getClass() == MapStorage.class)
            deleteResume(uuid);
        else deleteResume(searchKey);
        System.out.println("Resume with uuid=" + uuid + " deleted ");
    }

    public void update(Resume resume) {
        int searchKey = checkKey(resume.getUuid(), false);
        // resume founded, -> save new resume instead old
        updateResume(resume, searchKey);
        System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
    }

    public Resume get(String uuid) {
        int searchKey = checkKey(uuid, false);
        if (getClass() == MapStorage.class)
            return getResume(uuid);
        else return getResume(searchKey);
    }
}