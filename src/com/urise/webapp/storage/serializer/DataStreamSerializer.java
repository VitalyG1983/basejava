package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static com.urise.webapp.util.GsonLocalDateAdapter.FORMATTER;

public class DataStreamSerializer implements Serialization {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            Consumer<Map.Entry<ContactType, String>> greetings = entry -> {
                try {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            };//System.out.println("Hello " + x + " !!!");
            writeWithException(contacts.entrySet(),dos,new FunctionalInterface());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            //////////////////        SectionType        ////////////////////////////////
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                String keyName = entry.getKey().name();
                dos.writeUTF(keyName);
                Object entryValue = entry.getValue();
                switch (keyName) {
                    case "PERSONAL", "OBJECTIVE" -> dos.writeUTF(((TextSection) entryValue).getText());
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> {
                        List<String> listSection = ((TextListSection) entryValue).getListSection();
                        dos.writeInt(listSection.size());
                        for (String text : listSection)
                            dos.writeUTF(text);
                    }
                    case "EXPERIENCE", "EDUCATION" -> {
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
                    case "PERSONAL", "OBJECTIVE" -> sections.put(sectionTittle.equals("PERSONAL") ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> readTextListSection(dis, sections, sectionTittle.equals("ACHIEVEMENT") ?
                            SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                    case "EXPERIENCE", "EDUCATION"-> readOrgSection(dis, sections,  sectionTittle.equals("EXPERIENCE") ?
                            SectionType.EXPERIENCE : SectionType.EDUCATION);
                }
            }
            return resume;
        }
    }

    @FunctionalInterface
    public interface forEachCustom<T> {
        void accept(T t);
    }

    @FunctionalInterface
    private void forEachCustom(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }

    private void writeWithException(Collection c,DataOutputStream dos, FunctionalInterface f){

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