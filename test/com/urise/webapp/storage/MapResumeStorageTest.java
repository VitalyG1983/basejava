package com.urise.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractArrayStorageTest {

    public MapResumeStorageTest() {
        super.storage = new MapResumeStorage();
    }

    @Ignore
    @Test
    public void storageExceptionOverFlow() {
    }
}