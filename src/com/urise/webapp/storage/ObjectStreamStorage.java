package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {

        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return null;
    }
}