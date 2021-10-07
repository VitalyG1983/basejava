package com.urise.webapp.storage;

import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    static Storage storage = new ArrayStorage();

    public ArrayStorageTest() {
        super.storage = storage;
    }

    @Test
    public void testSaveResume() {
    }

    @Test
    public void testSearchInd() {
    }
}