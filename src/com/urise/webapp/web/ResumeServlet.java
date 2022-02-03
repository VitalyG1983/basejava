package com.urise.webapp.web;

import com.urise.webapp.model.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static com.urise.webapp.storage.SqlStorage.SQL_STORAGE;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {

            Resume r = SQL_STORAGE.get(uuid);
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, AbstractSection> sections = r.getSections();
            response.getWriter().write(r.getFullName() + "\n");
            for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
                response.getWriter().write(String.valueOf(entry));
            }
            for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                response.getWriter().write("\n" + entry.getKey().getTitle());
                TextSection textSection = entry.getValue() instanceof TextSection ? ((TextSection) entry.getValue()) : null;
                TextListSection textListSection = entry.getValue() instanceof TextListSection ?
                        ((TextListSection) entry.getValue()) : null;
                OrganizationsSection organizations = entry.getValue() instanceof OrganizationsSection ?
                        ((OrganizationsSection) entry.getValue()) : null;
                if (textSection != null)
                    response.getWriter().write(textSection.getText());
                else if (textListSection != null) {
                    for (String text : textListSection.getListSection())
                        response.getWriter().write("- " + text);
                } else if (organizations != null) {
                    for (Organization organization : organizations.getListOrganizations()) {
                        if (organization.getHomePage().getName() == null) {
                        } else response.getWriter().write(organization.getHomePage().getName());
                        for (Organization.Experience org : organization.getListExperience()) {
                            response.getWriter().write(org.getStartDate().toString());
                            if (org.getEndDate() != null)
                                response.getWriter().write(" - " + org.getEndDate());
                            else response.getWriter().write(" - Сейчас");
                            if (org.getTitle() == null) {
                            } else response.getWriter().write(org.getTitle());
                            if (org.getDescription() == null) {
                            } else response.getWriter().write("  " + org.getDescription());
                        }
                    }
                }
            }
        }
    }
}


