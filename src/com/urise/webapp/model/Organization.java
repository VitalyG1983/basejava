package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization extends AbstractSection {
    private final List<Experience> org;

    public Organization(List<Experience> organizations) {
        Objects.requireNonNull(organizations, "organizations required non null");
        this.org = organizations;
    }

    public List<Experience> getOrg() {
        return org;
    }
}