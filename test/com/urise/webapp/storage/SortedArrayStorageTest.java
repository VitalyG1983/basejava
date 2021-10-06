package com.urise.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    static Storage storage;

    public SortedArrayStorageTest() {
        this.storage = new SortedArrayStorage();
        super.storage = storage;
    }

    @Test
    public void saveResume() {
    }

    @Test
    public void searchInd() {
    }
}