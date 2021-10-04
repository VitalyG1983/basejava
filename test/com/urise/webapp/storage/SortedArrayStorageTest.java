package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

   // public SortedArrayStorageTest(Storage storage) {
    //    super(storage);
    //}
}