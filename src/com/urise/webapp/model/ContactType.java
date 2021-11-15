package com.urise.webapp.model;

public enum ContactType {
    TEL("Тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDLN("LINKEDLN"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}