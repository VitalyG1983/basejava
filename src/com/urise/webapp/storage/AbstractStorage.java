package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {
    static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract Object searchKey(String uuid);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract void updateResume(Resume resume, Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

   /* protected Object checkKey(String uuid, boolean save) {
        Object searchKey = searchKey(uuid);
        //  boolean MapResumeStorage = getClass() == MapResumeStorage.class;
        if (getClass() == MapResumeStorage.class || getClass() == MapUuidStorage.class) {
            if (save) {
                if (searchKey != null) throw new ExistStorageException(uuid);
            } else if (searchKey == null) throw new NotExistStorageException(uuid);
            return searchKey;
        } else if (save) {
            if ((int) searchKey >= 0) throw new ExistStorageException(uuid);
        } else if ((int) searchKey < 0) throw new NotExistStorageException(uuid);
        return searchKey;
    }*/

    protected abstract Object isExist(String uuid, boolean b);

    public void save(Resume r) {
        if (r.getUuid() == null || r.getUuid().isBlank()) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else saveResume(r, isExist(r.getUuid(), true));
    }

    public void delete(String uuid) {
        Object searchKey = isExist(uuid, false);
        deleteResume(searchKey);
        System.out.println("Resume with uuid=" + uuid + " deleted ");
    }

    public void update(Resume resume) {
        Object searchKey = isExist(resume.getUuid(), false);
        // resume founded, -> save new resume instead old
        updateResume(resume, searchKey);
        System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
    }

    public Resume get(String uuid) {
        Object searchKey = isExist(uuid, false);
        return getResume(searchKey);
    }
}