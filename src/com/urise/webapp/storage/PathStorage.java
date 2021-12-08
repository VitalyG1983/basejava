package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serialization serialization;

    protected PathStorage(String dir, Serialization ser) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory required non null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + "is not directory or is not writable");
        }
        this.serialization = ser;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        Path file;
        try {
            file = Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("I/O doSave Error", path.getFileName().toString(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path doUpdate error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path doDelete error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path doGet Error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected List<Resume> getStorage() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).//forEach(path -> resumes.add(doGet(path)));
            return resumes;
        } catch (IOException e) {
            throw new StorageException("Path getStorage() Error", e.toString(), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    doDelete(path);
                }
            });
        } catch (IOException e) {
            throw new StorageException("Path delete Error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).filter(path -> Files.isRegularFile(Path.of(path.toString(), ""))).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", e.toString());
        }
    }
}