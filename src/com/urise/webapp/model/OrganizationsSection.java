package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationsSection extends AbstractSection {
    private final List<Organization> organizations;

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations required non null");
        this.organizations = organizations;
    }

 /*   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationsSection that = (OrganizationsSection) o;
        return organizations.equals(that.organizations);
    }*/

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