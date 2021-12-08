package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamPath;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamPath()));
    }
}