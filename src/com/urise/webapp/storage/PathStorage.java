package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.Serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("I/O doSave Error", path.getFileName().toString(), e);
        }
        doUpdate(r, path);
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
        return listOfDirectory().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        listOfDirectory().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) listOfDirectory().filter(path -> Files.isRegularFile(Path.of(path.toString(), ""))).count();
    }

    public Stream<Path> listOfDirectory() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e.toString());
        }
    }
}