package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final String title;
    private final List<Experience> experience;

    public Organization(String title, List<Experience> experience, String name, String url) {
     //   Objects.requireNonNull(title, "OrganizationsSection title required non null");
        Objects.requireNonNull(experience, "List Experience required non null");
       // Objects.requireNonNull(name, "name required non null");
       // Objects.requireNonNull(url, "url required non null");
        this.title = title;
        this.experience = experience;
        this.homePage = new Link(name, url);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", title='" + title + '\'' +
                ", experience=" + experience +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!title.equals(that.title)) return false;
        if (!experience.equals(that.experience)) return false;
        return homePage.equals(that.homePage);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + experience.hashCode();
        result = 31 * result + homePage.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public List<Experience> getOrganizations() {
        return experience;
    }

    public Link getHomePage() {
        return homePage;
    }
}