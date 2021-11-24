package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {
    private final List<String> ListSection;

    public TextListSection(List<String> listSection) {
        Objects.requireNonNull(listSection, "listSection required non null");
        ListSection = listSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextListSection that = (TextListSection) o;
        return ListSection.equals(that.ListSection);
    }

    @Override
    public int hashCode() {
        return ListSection.hashCode();
    }

    public List<String> getListSection() {
        return ListSection;
    }
}