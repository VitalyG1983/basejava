package com.urise.webapp.storage;

import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super.storage = new MapStorage();
    }

    @Test
    public void storageExceptionOverFlow() {
    }
}