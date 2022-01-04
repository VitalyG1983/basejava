package com.urise.webapp.storage.serializer;

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
            writeWithException(contCollection, dos,
                    (entry) -> {
                        dos.writeUTF(entry.getKey().name());
                        dos.writeUTF(entry.getValue());
                    });
            Collection<Map.Entry<SectionType, AbstractSection>> sectionCollection = new ArrayList<>(r.getSections().entrySet());
            /////////////////////////        SectionType        ////////////////////////////////
            writeWithException(sectionCollection, dos, (entry) -> {
                SectionType keyName = entry.getKey();
                dos.writeUTF(keyName.name());
                Object entryValue = entry.getValue();
                switch (keyName) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entryValue).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        Collection<String> listSection = ((TextListSection) entryValue).getListSection();
                        writeWithException(listSection, dos, (string) -> dos.writeUTF(string));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        Collection<Organization> organizations = ((OrganizationsSection) entryValue).getListOrganizations();
                        writeWithException(organizations, dos, (org) -> {
                            Link homePage = org.getHomePage();
                            String url = homePage.getUrl();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(url == null ? "null" : url);
                            Collection<Organization.Experience> listExperience = org.getListExperience();
                            writeWithException(listExperience, dos, (exp) -> {
                                writeDate(dos, exp.getStartDate(), exp.getEndDate());
                                String title = exp.getTitle();
                                dos.writeUTF(title == null ? "null" : title);
                                dos.writeUTF(exp.getDescription() == null ? "null" : exp.getDescription());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            Map<SectionType, AbstractSection> sections = resume.getSections();
            readWithException(dis,
                    () -> {
                        String value = dis.readUTF();
                        resume.addContact(ContactType.valueOf(value), dis.readUTF());
                    });
           /* int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String value = dis.readUTF();
                resume.addContact(ContactType.valueOf(value), dis.readUTF());
            }*/
            readWithException(dis,
                    () -> {
                        SectionType sectionType = SectionType.valueOf(dis.readUTF());
                        switch (sectionType) {
                            case PERSONAL, OBJECTIVE -> sections.put(sectionType.equals(SectionType.PERSONAL) ? SectionType.PERSONAL :
                                    SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                            case ACHIEVEMENT, QUALIFICATIONS -> readTextListSection(dis, sections, sectionType.equals(SectionType.ACHIEVEMENT) ?
                                    SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                            case EXPERIENCE, EDUCATION -> readOrgSection(dis, sections, sectionType.equals(SectionType.EXPERIENCE) ?
                                    SectionType.EXPERIENCE : SectionType.EDUCATION);
                        }
                    });
          /*  int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sections.put(sectionType.equals(SectionType.PERSONAL) ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> readTextListSection(dis, sections, sectionType.equals(SectionType.ACHIEVEMENT) ?
                            SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                    case EXPERIENCE, EDUCATION -> readOrgSection(dis, sections, sectionType.equals(SectionType.EXPERIENCE) ?
                            SectionType.EXPERIENCE : SectionType.EDUCATION);
                }
            }*/
            return resume;
        }
    }

    @FunctionalInterface
    public interface CustomWriter<T> {

        void writeToDos(T t) throws IOException;
    }

    @FunctionalInterface
    public interface CustomReader {

        void readFromDis() throws IOException;
    }

    private <T> void writeWithException(Collection<T> entrySet, DataOutputStream dos,
                                        CustomWriter<T> customWriter) throws IOException {
        dos.writeInt(entrySet.size());
        for (T entry : entrySet) {
            customWriter.writeToDos(entry);
        }
    }

    private void readWithException(DataInputStream dis, CustomReader customReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            customReader.readFromDis();
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