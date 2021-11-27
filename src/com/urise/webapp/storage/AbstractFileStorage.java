package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory required non null");
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        if (!directory.canRead() || !directory.canWrite())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        this.directory = directory;
    }

    protected abstract void doWright(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            file.createNewFile();
            doWright(r, file);
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }

    }

    @Override
    protected void updateResume(Resume resume, File file) {
        deleteResume(file);
        saveResume(resume, file);
    }

    @Override
    protected void deleteResume(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("Delete file Error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getStorage() {
        File[] files = directory.listFiles(File::isFile);
        List<Resume> resumes = new ArrayList<>();
        if (files != null) {
            for (File file : files)
                resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles(File::isFile);
        if (files != null) {
            for (File file : files)
                deleteResume(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles(File::isFile);
        return files.length;
    }
}