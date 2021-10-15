package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static int STORAGE_LIMIT = 10_000;
    protected int size;

    protected abstract int searchInd(String uuid);

    protected abstract void saveResume(Resume r, int index);


}


