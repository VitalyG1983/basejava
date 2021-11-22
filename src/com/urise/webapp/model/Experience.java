package com.urise.webapp.model;

import java.time.YearMonth;

public class Experience {
    private String name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String text;

    public Experience(String name, YearMonth startDate, YearMonth endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}