package com.urise.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest {

    public ListStorageTest() {
        super.setStorage(new ListStorage());
    }

    @Ignore
    @Test
    public void storageExceptionOverFlow() {
    }
}