package com.urise.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    static Storage storage;

    public ArrayStorageTest() {
        this.storage = new ArrayStorage();
        super.storage = storage;
    }

    @Test
    public void testSaveResume() {
    }

    @Test
    public void testSearchInd() {
    }
}