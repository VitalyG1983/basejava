package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static com.urise.webapp.util.GsonLocalDateAdapter.FORMATTER;

public class DataStreamSerializer implements Serialization {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Collection<Map.Entry<ContactType, String>> contCollection = new ArrayList<>(r.getContacts().entrySet());
            dos.writeInt(contCollection.size());
            CustomConsumer<Map.Entry<ContactType, String>> customConsumer = (entry) -> {
                try {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                } catch (IOException e) {
                    throw new StorageException("Error doWrite \"Contacts\" of resume in DataStreamSerializer", null, e);
                }
            };
            writeWithException(contCollection, dos, customConsumer);

          /*  for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }*/
            Collection<Map.Entry<SectionType, AbstractSection>> sectionCollection = new ArrayList<>(r.getSections().entrySet());
            // Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sectionCollection.size());
            //////////////////        SectionType        ////////////////////////////////
            CustomConsumer<Map.Entry<SectionType, AbstractSection>> writeSectionEntry = (entry) -> {
                try {
                    SectionType keyName = entry.getKey();
                    dos.writeUTF(keyName.toString());
                    Object entryValue = entry.getValue();
                    switch (keyName) {
                        case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entryValue).getText());
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            Collection<String> listSection = ((TextListSection) entryValue).getListSection();
                            dos.writeInt(listSection.size());
                            CustomConsumer<String> writeTextListSection = (string) -> {
                                try {
                                    dos.writeUTF(string);
                                } catch (IOException e) {
                                    throw new StorageException("Error doWrite " + keyName + " of resume in DataStreamSerializer", null, e);
                                }
                            };

                            writeWithException(listSection, dos, writeTextListSection);
                          /*  for (String text : listSection)
                                doStream.writeUTF(text);*/
                        }
                        case EXPERIENCE, EDUCATION -> {
                            Collection<Organization> organizations = ((OrganizationsSection) entryValue).getListOrganizations();
                            dos.writeInt(organizations.size());
                            CustomConsumer<Organization> writeOrgSection = (org) -> {
                                try {
                                    Link homePage = org.getHomePage();
                                    String url = homePage.getUrl();
                                    dos.writeUTF(homePage.getName());
                                    dos.writeUTF(url == null ? "null" : url);
                                    Collection<Organization.Experience> listExperience = org.getListExperience();
                                    dos.writeInt(listExperience.size());
                                    CustomConsumer<Organization.Experience> writeExpSection = (exp) -> {
                                        try {
                                            writeDate(dos, exp.getStartDate(), exp.getEndDate());
                                            String title = exp.getTitle();
                                            dos.writeUTF(title == null ? "null" : title);
                                            dos.writeUTF(exp.getDescription() == null ? "null" : exp.getDescription());
                                        } catch (IOException e) {
                                            throw new StorageException("Error doWrite " + keyName + " of resume in DataStreamSerializer", null, e);
                                        }
                                    };
                                    writeWithException(listExperience, dos, writeExpSection);
                                } catch (IOException e) {
                                    throw new StorageException("Error doWrite " + keyName + " of resume in DataStreamSerializer", null, e);
                                }
                            };
                            writeWithException(organizations, dos, writeOrgSection);
                        }
                    }
                } catch (IOException e) {
                    throw new StorageException("Error doWrite SectionType of resume in DataStreamSerializer", null, e);
                }
            };
            writeWithException(sectionCollection, dos, writeSectionEntry);
          /*  for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType keyName = entry.getKey();
                dos.writeUTF(keyName.toString());
                Object entryValue = entry.getValue();
                switch (keyName) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entryValue).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listSection = ((TextListSection) entryValue).getListSection();
                        dos.writeInt(listSection.size());
                        for (String text : listSection)
                            dos.writeUTF(text);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationsSection) entryValue).getListOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization org : organizations) {
                            Link homePage = org.getHomePage();
                            String url = homePage.getUrl();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(url == null ? "null" : url);
                            List<Organization.Experience> listExperience = org.getListExperience();
                            dos.writeInt(listExperience.size());
                            for (Organization.Experience exp : listExperience) {
                                writeDate(dos, exp.getStartDate(), exp.getEndDate());
                                String title = exp.getTitle();
                                dos.writeUTF(title == null ? "null" : title);
                                dos.writeUTF(exp.getDescription() == null ? "null" : exp.getDescription());
                            }
                        }
                    }
                }
            }*/
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
                    case "PERSONAL", "OBJECTIVE" -> sections.put(sectionTittle.equals("PERSONAL") ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> readTextListSection(dis, sections, sectionTittle.equals("ACHIEVEMENT") ?
                            SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                    case "EXPERIENCE", "EDUCATION" -> readOrgSection(dis, sections, sectionTittle.equals("EXPERIENCE") ?
                            SectionType.EXPERIENCE : SectionType.EDUCATION);
                }
            }
            return resume;
        }
    }

    @FunctionalInterface
    public interface CustomConsumer<T> {

        void writeToDos(T t) throws IOException;
    }

    private void writeWithException(Collection entrySet, DataOutputStream dos,
                                    CustomConsumer customConsumer) throws IOException {
        Objects.requireNonNull(customConsumer);
        for (Object entry : entrySet) {
            customConsumer.writeToDos(entry);
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate start, LocalDate end) throws IOException {
        dos.writeUTF(FORMATTER.format(start));
        dos.writeUTF(FORMATTER.format(end));
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.parse(dis.readUTF(), FORMATTER);
    }

    private void readTextListSection(DataInputStream dis,
                                     Map<SectionType, AbstractSection> sections, SectionType secType) throws IOException {
        int size = dis.readInt();
        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> listString = tls.getListSection();
        for (int z = 0; z < size; z++) {
            listString.add(dis.readUTF());
        }
        sections.put(secType, tls);
    }

    private void readOrgSection(DataInputStream dis, Map<SectionType, AbstractSection> sections, SectionType
            secType) throws IOException {
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