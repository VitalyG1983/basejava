package com.urise.webapp.model;

public enum JobName {
    JAVAONLINEPROJECTS("Java Online Projects"),
    WRIKE("Wrike"),
    RITCENTER("RIT Center"),
    LUXOFT("Luxoft (Deutsche Bank)"),
    YOTA("Yota"),
    ENKATA("Enkata"),
    SIEMENSAG("Siemens AG"),
    ALCATEL("Alcatel");
    private String title;

    JobName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}