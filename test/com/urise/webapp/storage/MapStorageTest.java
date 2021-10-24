package com.urise.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super.storage = new MapStorage();
    }

    @Ignore
    @Test
    public void storageExceptionOverFlow() {
    }
}