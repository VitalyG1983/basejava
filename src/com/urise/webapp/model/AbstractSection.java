package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class AbstractSection {
//    private String text;
//    private List<String> sectionText;
private final EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

}
