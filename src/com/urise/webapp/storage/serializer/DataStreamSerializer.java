package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.util.GsonLocalDateAdapter.FORMATTER;

public class DataStreamSerializer implements Serialization {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            //////////////////        SectionType        ////////////////////////////////
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().getTitle());
                switch (entry.getKey().getTitle()) {
                    case "Личные качества", "Позиция" -> dos.writeUTF(((TextSection) entry.getValue()).getText());
                    case "Достижения", "Квалификация" -> {
                        dos.writeInt(((TextListSection) entry.getValue()).getListSection().size());
                        for (String text : ((TextListSection) entry.getValue()).getListSection())
                            dos.writeUTF(text);
                    }
                    case "Опыт работы", "Образование" -> {
                        List<Organization> organizations = ((OrganizationsSection) entry.
                                getValue()).getListOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization org : organizations) {
                            dos.writeUTF(org.getHomePage().getName());
                            if (org.getHomePage().getUrl() == null) {
                                dos.writeUTF("null");
                            } else dos.writeUTF(org.getHomePage().getUrl());
                            dos.writeInt(org.getListExperience().size());
                            for (Organization.Experience exp : org.getListExperience()) {
                                writeDate(dos, exp.getStartDate(), exp.getEndDate());
                                if (exp.getTitle() == null)
                                    dos.writeUTF("null");
                                else dos.writeUTF(exp.getTitle());
                                if (exp.getDescription() == null)
                                    dos.writeUTF("null");
                                else dos.writeUTF(exp.getDescription());
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            Map<SectionType, AbstractSection> sections = resume.getSections();
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String value = dis.readUTF();
                resume.addContact(ContactType.valueOf(value), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                String sectionTittle = dis.readUTF();
                switch (sectionTittle) {
                    case "Личные качества" -> sections.put(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                    case "Позиция" -> sections.put(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case "Достижения" -> readTextListSection(dis, sections, SectionType.ACHIEVEMENT);
                    case "Квалификация" -> readTextListSection(dis, sections, SectionType.QUALIFICATIONS);
                    case "Опыт работы" -> readOrgSection(dis, sections, SectionType.EXPERIENCE);
                    case "Образование" -> readOrgSection(dis, sections, SectionType.EDUCATION);
                }
            }
            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate start, LocalDate end) throws IOException {
        dos.writeUTF(FORMATTER.format(start));
        dos.writeUTF(FORMATTER.format(end));
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.parse(dis.readUTF(), FORMATTER);
    }

    private void readTextListSection(DataInputStream dis, Map<SectionType, AbstractSection> sections, SectionType secType) throws IOException {
        int size = dis.readInt();
        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> listString = tls.getListSection();
        for (int z = 0; z < size; z++) {
            listString.add(dis.readUTF());
        }
        sections.put(secType, tls);
    }

    private void readOrgSection(DataInputStream dis, Map<SectionType, AbstractSection> sections, SectionType secType) throws IOException {
        int size = dis.readInt();
        OrganizationsSection section = new OrganizationsSection(new ArrayList<>());
        for (int z = 0; z < size; z++) {
            String orgName = dis.readUTF();
            String orgUrl = dis.readUTF();
            List<Organization.Experience> expList = new ArrayList<>();
            int sizeListExperience = dis.readInt();
            for (int q = 0; q < sizeListExperience; q++) {
                LocalDate startDate = readDate(dis);
                LocalDate endDate = readDate(dis);
                String title = dis.readUTF();
                String description = dis.readUTF();
                expList.add(new Organization.Experience(startDate, endDate,
                        title.equals("null") ? null : title,
                        description.equals("null") ? null : description));
            }
            Organization org = new Organization(expList, orgName,
                    orgUrl.equals("null") ? null : orgUrl);
            section.getListOrganizations().add(org);
        }
        sections.put(secType, section);
    }
}