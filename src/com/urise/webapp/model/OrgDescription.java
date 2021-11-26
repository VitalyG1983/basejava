package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class OrgDescription {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String description;

    public OrgDescription(YearMonth startDate, YearMonth endDate, String description) {
        Objects.requireNonNull(startDate, "startDate required non null");
       // Objects.requireNonNull(endDate, "endDate required non null");
        Objects.requireNonNull(description, "description required non null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgDescription that = (OrgDescription) o;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        return description.equals(that.description);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgDescription that = (OrgDescription) o;

        if (!startDate.equals(that.startDate)) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }
}