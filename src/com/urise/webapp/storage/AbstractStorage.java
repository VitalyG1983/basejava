package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    // protected final Logger log= Logger.getLogger(getClass().getTitle());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getStorage();

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted()");
        List<Resume> resumes = getStorage();
        resumes.sort(RESUME_NAME_COMPARATOR);
        return resumes;
    }

    public void save(Resume r) {
        LOG.info("Save" + r);
        if (r.getUuid() == null || r.getUuid().isBlank()) {
            System.out.println("Enter valid uuid, not " + r.getUuid());
        } else {
            SK searchKey = getNotExistedSearchKey(r.getUuid());
            doSave(r, searchKey);
        }
    }

    public void delete(String uuid) {
        LOG.info("Delete" + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("Resume with uuid=" + uuid + " deleted ");
    }

    public void update(Resume resume) {
        LOG.info("Update" + resume);
        SK searchKey = getExistedSearchKey(resume.getUuid());
        // resume founded, -> save new resume instead old
        doUpdate(resume, searchKey);
        System.out.println("Resume with uuid=" + resume.getUuid() + " updated in Database");
    }

    public Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}