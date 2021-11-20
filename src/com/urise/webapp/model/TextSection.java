package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private String textSection;

    public TextSection(String textSection) {
        this.textSection = textSection;
    }

    public String getTextSection() {
        return textSection;
    }

    public void setTextSection(String textSection) {
        this.textSection = textSection;
    }
}