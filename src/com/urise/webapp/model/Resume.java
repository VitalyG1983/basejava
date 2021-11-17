package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {
    private final String uuid;
    private final String fullName;
    private final EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final EnumMap<SectionType, ResumeText> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
//        ResumeText resumeText=new ResumeText();
//        contacts.put(ContactType.TEL,resumeText.tel);
//        sections.put(SectionType.ACHIEVEMENT,resumeText.sectionText.get(0));
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    public class ResumeText {
        //        private String tel;
//        private String Skype;
//        private String mail;
//        private String linkedLn;
//        private String gitHub;
//        private String stackOverflow;
        private String text;
        private final List<String> sectionText = new ArrayList<>();
    }

}