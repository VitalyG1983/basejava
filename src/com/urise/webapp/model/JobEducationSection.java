package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class JobEducationSection extends AbstractSection {
    private final List<JobEducationTextSection> JobEducationSection = new ArrayList<>();

    public List<JobEducationTextSection> getJobEducationSection() {
        return JobEducationSection;
    }
}
