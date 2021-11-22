package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class TextListSection extends AbstractSection {
    private final List<String> ListSection = new ArrayList<>();

    public List<String> getListSection() {
        return ListSection;
    }
}