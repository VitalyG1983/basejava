package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class Experience {
    private String title;
    private List<YearMonth> startDate;
    private YearMonth endDate;
    private String description;
    private final Link homePage;

    public Experience(List<Experience> organizations, String title, YearMonth startDate, YearMonth endDate, String name, String url, String description) {
        Objects.requireNonNull(startDate, "startDate required non null");
        Objects.requireNonNull(endDate, "endDate required non null");
        Objects.requireNonNull(title, "title required non null");
        if (IsExist(organizations, title, name, url))
            this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.homePage = new Link(name, url);
        this.description = description;
    }

    public boolean IsExist(List<Experience> organizations, String title, String name, String url) {
        for (Experience org : organizations) {
            if (org.getTitle().equals(title) && org.homePage.getName().equals(name) && org.homePage.getUrl().equals(url))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", homePage=" + homePage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        if (!title.equals(that.title)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return homePage.equals(that.homePage);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + homePage.hashCode();
        return result;
    }


    public String getTitle() {
        return title;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}