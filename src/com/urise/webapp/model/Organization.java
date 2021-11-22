package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private final List<Experience> org = new ArrayList<>();

    public List<Experience> getOrg() {
        return org;
    }
}
