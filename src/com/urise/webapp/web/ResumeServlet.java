package com.urise.webapp.web;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.Config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private static Config config = Config.get();
    private static SqlStorage SQL_STORAGE = config.getSqlStorage();

    public ResumeServlet() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume r = SQL_STORAGE.get(uuid);
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, AbstractSection> sections = r.getSections();
            response.getWriter().write("<p style=\"font-size:110%;\"><b> Uuid</b>=" + uuid + ";<b>   FullName</b>=" +
                    r.getFullName() + "</p>");
            for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
                writer.println("<p><b>" + entry.getKey() + ": </b>" + entry.getValue() + "</p>");
            }
            for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                writer.println("<h4>" + entry.getKey().getTitle() + "</h4>");
                TextSection textSection = entry.getValue() instanceof TextSection ? ((TextSection) entry.getValue()) : null;
                TextListSection textListSection = entry.getValue() instanceof TextListSection ?
                        ((TextListSection) entry.getValue()) : null;
                OrganizationsSection organizations = entry.getValue() instanceof OrganizationsSection ?
                        ((OrganizationsSection) entry.getValue()) : null;
                if (textSection != null) {
                    writer.println("<p>" + textSection.getText() + "</p>");
                } else if (textListSection != null) {
                    for (String text : textListSection.getListSection())
                        response.getWriter().write("<p>- " + text + "</p>");
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
        } else {
            List<Resume> list = SQL_STORAGE.getAllSorted();
            response.getWriter().write("<style>table, th, td {border: 1px solid black; </style>" +
                    "<table style=width:30%>" +
                    "<caption style=\"font-size:120%;\">Resumes in DataBase</caption>" +
                    "<tr>" +
                    "  <th>Uuid</th>" +
                    "  <th>FullName</th>" +
                    "</tr>" +
                    "<tr>");
            for (Resume r : list) {
                response.getWriter().write(
                        "<tr>" +
                                "    <th>" + r.getUuid() + "</th>" +
                                "    <th>" + r.getFullName() + "</th>" +
                                "</tr>");
            }
        }
    }
}