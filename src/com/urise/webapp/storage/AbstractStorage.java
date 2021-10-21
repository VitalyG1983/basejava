package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int searchInd(String uuid);

    protected abstract void saveResume(Resume r, int index);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void deleteResume(String uuid);

    protected abstract Resume getResume(String uuid);

    public void save(Resume r) {
        int index = searchInd(r.getUuid());
        if (r.getUuid() == null || r.getUuid().isBlank()) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(r, index);
        }
    }

    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int index = searchInd(uuid);
        if (index >= 0) {
            deleteResume(uuid);
            System.out.println("Resume with uuid=" + uuid + " deleted ");
        } else throw new NotExistStorageException(uuid);
    }

    public void update(Resume resume) {
        int index = searchInd(resume.getUuid());
        if (index >= 0) {
            // resume founded, -> save new resume instead old
            updateResume(resume, index);
            System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
        } else throw new NotExistStorageException(resume.getUuid());
    }

    public Resume get(String uuid) {
        int index = searchInd(uuid);
        if (index >= 0) return getResume(uuid);
        // throw new NotExistStorageException(uuid);
        return null;
    }
}