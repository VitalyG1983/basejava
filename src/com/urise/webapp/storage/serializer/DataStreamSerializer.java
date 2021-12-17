package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.urise.webapp.util.DateUtil.NOW;

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
                            dos.writeUTF(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(org.getStartDate()));
                            if (org.getEndDate() != null)
                                dos.writeUTF(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(org.getEndDate()));
                            else dos.writeUTF(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(NOW));
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String value = dis.readUTF();
                resume.addContact(ContactType.valueOf(dis.readUTF()), value);
            }
            return resume;
        }

    }
}