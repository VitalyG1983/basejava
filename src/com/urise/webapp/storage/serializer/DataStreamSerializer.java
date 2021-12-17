package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.util.DateUtil.NOW;
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
            //////////////         ////////////////////////////////
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().getTitle());
                TextSection textSection = entry.getValue() instanceof TextSection ? ((TextSection) entry.getValue()) : null;
                TextListSection textListSection = entry.getValue() instanceof TextListSection ? ((TextListSection) entry.getValue()) : null;
                OrganizationsSection organizations = entry.getValue() instanceof OrganizationsSection ? ((OrganizationsSection) entry.getValue()) : null;
                if (textSection != null)
                    dos.writeUTF(textSection.getText());
                else if (textListSection != null) {
                    dos.writeInt(textListSection.getListSection().size());
                    for (String text : textListSection.getListSection())
                        dos.writeUTF(text);
                } else if (organizations != null) {
                    dos.writeInt(organizations.getListOrganizations().size());
                    for (Organization organization : organizations.getListOrganizations()) {
                        if (organization.getHomePage().getName() == null) {
                        } else dos.writeUTF(organization.getHomePage().getName());
                        dos.writeInt(organization.getListExperience().size());
                        for (Organization.Experience org : organization.getListExperience()) {
                            dos.writeUTF(FORMATTER.format(org.getStartDate()));
                            if (org.getEndDate() != null)
                                dos.writeUTF(FORMATTER.format(org.getEndDate()));
                            else dos.writeUTF(FORMATTER.format(NOW));
                            if (org.getTitle() == null) {
                            } else dos.writeUTF(org.getTitle());
                            if (org.getDescription() == null) {
                            } else dos.writeUTF(org.getDescription());
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
                resume.addContact(ContactType.valueOf(dis.readUTF()), value);
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                String sectionTittle = dis.readUTF();
                if (sectionTittle.equals("Личные качества")) {
                    sections.put(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                }
                if (sectionTittle.equals("Позиция")) {
                    sections.put(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                }
                if (sectionTittle.equals("Достижения")) {
                    size = dis.readInt();
                    TextListSection tls = new TextListSection(new ArrayList<>());
                    List<String> achievSection = tls.getListSection();
                    for (i = 0; i < size; i++) {
                        achievSection.add(dis.readUTF());
                    }
                    sections.put(SectionType.ACHIEVEMENT, tls);
                }
                if (sectionTittle.equals("Квалификация")) {
                    size = dis.readInt();
                    TextListSection tlsQ = new TextListSection(new ArrayList<>());
                    List<String> qualSection = tlsQ.getListSection();
                    for (i = 0; i < size; i++) {
                        qualSection.add(dis.readUTF());
                    }
                    sections.put(SectionType.QUALIFICATIONS, tlsQ);
                }
                if (sectionTittle.equals("Опыт работы")) {
                    size = dis.readInt();
                    OrganizationsSection jobSection = new OrganizationsSection(new ArrayList<>());
                    for (i = 0; i < size; i++) {
                        String organizationName = dis.readUTF();
                        List<Organization.Experience> jobDescList1 = new ArrayList<>();
                        int sizeListExperience = dis.readInt();
                        for (i = 0; i < sizeListExperience; i++) {
                            jobDescList1.add(new Organization.Experience(LocalDate.parse(dis.readUTF(), FORMATTER), LocalDate.parse(dis.readUTF(), FORMATTER), dis.readUTF(), dis.readUTF()));
                        }
                        Organization jobText1 = new Organization(jobDescList1, organizationName, null);
                        jobSection.getListOrganizations().add(jobText1);
                    }
                    sections.put(SectionType.EXPERIENCE, jobSection);
                }
            }

            return resume;
        }

    }
}