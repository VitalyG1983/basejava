package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

public class Organization {
    private final Link homePage;
    private final List<Experience> experience;

    public Organization(List<Experience> experience, String name, String url) {
        //   Objects.requireNonNull(title, "OrganizationsSection title required non null");
        //  Objects.requireNonNull(experience, "List Experience required non null");
        // Objects.requireNonNull(name, "name required non null");
        // Objects.requireNonNull(url, "url required non null");
        this.experience = experience;
        this.homePage = new Link(name, url);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", title='" + '\'' +
                ", experience=" + experience +
                '}';
    }


    public List<Experience> getOrganizations() {
        return experience;
    }

    public Link getHomePage() {
        return homePage;
    }

    public static class Experience {
        private final String title;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;

        public Experience(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Experience(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate required non null");
            // Objects.requireNonNull(endDate, "endDate required non null");
            //Objects.requireNonNull(description, "description required non null");
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Experience that = (Experience) o;

            if (title != null ? !title.equals(that.title) : that.title != null) return false;
            if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
            if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
            return description != null ? description.equals(that.description) : that.description == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Experience{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }
    }
}