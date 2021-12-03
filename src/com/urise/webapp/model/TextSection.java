package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class TextSection extends AbstractSection implements Serializable {
    private String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "text required non null");
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}