package com.urise.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    static Storage storage = new ArrayStorage();

    public ArrayStorageTest() {
        super(storage);
//        this.storage = new ArrayStorage();
//        super.storage = storage;
    }

    //super(storage);
    @Test
    public void testSaveResume() {
    }

    @Test
    public void testSearchInd() {
    }
}