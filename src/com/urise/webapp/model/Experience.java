package com.urise.webapp.model;

public class Experience {
    private String name;
    private String startDate;
    private String endDate;
    private String text;

    public Experience(String name, String startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}