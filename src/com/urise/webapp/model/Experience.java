package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Experience {
    private String title;
    private final List<OrgDescription> orgDescription;
    private final Link homePage;

    public Experience(String title, List<OrgDescription> orgDescription, String name, String url) {
        Objects.requireNonNull(title, "Organization title required non null");
        Objects.requireNonNull(orgDescription, "List OrgDescription required non null");
       // Objects.requireNonNull(name, "name required non null");
       // Objects.requireNonNull(url, "url required non null");
        this.title = title;
        this.orgDescription = orgDescription;
        this.homePage = new Link(name, url);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "title='" + title + '\'' +
                ", organizations=" + orgDescription +
                ", homePage=" + homePage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        if (!title.equals(that.title)) return false;
        if (!orgDescription.equals(that.orgDescription)) return false;
        return homePage.equals(that.homePage);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + orgDescription.hashCode();
        result = 31 * result + homePage.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public List<OrgDescription> getOrganizations() {
        return orgDescription;
    }

    public Link getHomePage() {
        return homePage;
    }
}