package com.urise.webapp.model;

public enum Education {
    COURSERA("Coursera"),
    LUXOFT("Luxoft (Deutsche Bank)"),
    SIEMENSAG("Siemens AG"),
    ALCATEL("Alcatel"),
    SPBNIUITMO("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики"),
    SCHOOL("Заочная физико-техническая школа при МФТИ");

    private String title;

    Education(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}