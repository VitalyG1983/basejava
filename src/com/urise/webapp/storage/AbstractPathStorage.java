package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory required non null");
        if (!Files.isDirectory(directory) || Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + "is not directory or is not writable");
        }
        this.directory. = directory;
    }

    protected abstract void doWrite(Resume r, OutputStream file) throws IOException;

    protected abstract Resume doRead(InputStream file) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            file.createNewFile();
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }

    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        //doDelete(file);
        doSave(resume, file);
    }

    @Override
    protected void doDelete(Path file) {
        if (!file.delete()) {
            throw new StorageException("Path delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path read Error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getStorage() {
        Path[] files = directory.listFiles(Path::isFile);
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> resumes = new ArrayList<>(files.length);
        for (Path file : files)
            resumes.add(doGet(file));
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete Error", null);
        }

        @Override
        public int size () {
            Path[] files = directory.listFiles(Path::isFile);
            if (files == null) {
                throw new StorageException("Directory read error", null);
            }
            return files.length;
        }
    }