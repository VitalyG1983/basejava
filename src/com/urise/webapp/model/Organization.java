package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private final List<Experience> jobEducationSection = new ArrayList<>();

    public List<Experience> getJobEducationSection() {
        return jobEducationSection;
    }
}
