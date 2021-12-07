package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> implements Serialization {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory required non null");
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        if (!directory.canRead() || !directory.canWrite())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        //doDelete(file);
        doSave(resume, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read Error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getStorage() {
        File[] files = directory.listFiles(File::isFile);
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file : files)
            resumes.add(doGet(file));
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles(File::isFile);
        if (files != null) {
            for (File file : files)
                doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles(File::isFile);
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        return files.length;
    }
}