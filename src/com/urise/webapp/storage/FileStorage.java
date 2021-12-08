package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serialization serialization;

    protected FileStorage(File directory, Serialization ser) {
        Objects.requireNonNull(directory, "directory required non null");
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        if (!directory.canRead() || !directory.canWrite())
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        this.directory = directory;
        this.serialization = ser;

    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("I/O createNewFile() Error", file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("I/O doWrite Error", file.getName(), e);
        }
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
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
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
        File[] files = directoryListFiles();
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file : files)
            resumes.add(doGet(file));
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = directoryListFiles();
        for (File file : files)
            doDelete(file);
    }

    @Override
    public int size() {
        File[] files = directoryListFiles();
        return files.length;
    }

    public File[] directoryListFiles() {
        File[] files = directory.listFiles(File::isFile);
        if (files == null) {
            throw new StorageException("Directory read listFiles() error", null);
        }
        return files;
    }
}