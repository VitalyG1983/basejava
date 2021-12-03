package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationsSection extends AbstractSection implements Serializable {
    private final List<Organization> organizations;

    public OrganizationsSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations required non null");
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationsSection that = (OrganizationsSection) o;

        return organizations != null ? organizations.equals(that.organizations) : that.organizations == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    public List<Organization> getExperience() {
        return organizations;
    }
}