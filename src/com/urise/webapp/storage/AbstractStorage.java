package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {

    protected abstract int searchInd(String uuid);

    protected abstract void saveNewResume(Resume r, int index);

    protected abstract void updateByIndex(Resume resume, int index);

    protected abstract void deleteByIndex(int index);

    protected abstract Resume getByIndex(int index);

    public void save(Resume r) {
        int index = searchInd(r.getUuid());
        if (r.getUuid() == null || Objects.equals(r.getUuid(), "")) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
            // } else if (storage.size()>= AbstractArrayStorage.STORAGE_LIMIT) {
            // throw new StorageException("Not enough space in Database for save new resume ", r.getUuid());
        } else {
            saveNewResume(r, index);
//            System.out.println("storage[" + storage.size + "].uuid= " + r.getUuid());
//            storage.size++;
        }
    }

    public void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int index = searchInd(uuid);
        if (index >= 0) {
                deleteByIndex(index);
                System.out.println("Resume with uuid=" + uuid + " deleted ");
            } else throw new NotExistStorageException(uuid);
    }

    public void update(Resume resume) {
        int index = searchInd(resume.getUuid());
        if (index >= 0) {
            // resume founded, -> save new resume instead old
            updateByIndex(resume, index);
            System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
        } else throw new NotExistStorageException(resume.getUuid());
    }

    public Resume get(String uuid) {
        int index = searchInd(uuid);
        if (index >= 0) return getByIndex(index);
         throw new NotExistStorageException(uuid);
    }
}