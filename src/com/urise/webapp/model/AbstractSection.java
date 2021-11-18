package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class AbstractSection {
    private final EnumMap<JobName, TextListSection> experienceSection = new EnumMap<>(JobName.class);
    private final EnumMap<Education, TextSection> educationSection = new EnumMap<>(Education.class);
}