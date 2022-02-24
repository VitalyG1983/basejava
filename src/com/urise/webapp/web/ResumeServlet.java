package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ResumeServlet extends HttpServlet {
    private static SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        boolean newRes = request.getParameter("newResume").equals("true");
        Resume r;
        if (newRes) {
            r = new Resume(uuid, fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        Map<SectionType, AbstractSection> sections = r.getSections();
        for (SectionType sectionType : SectionType.values()) {
            String sectionValue = request.getParameter(sectionType.toString());
            if (sectionValue != null && sectionValue.trim().length() != 0) {
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sections.put(sectionType.equals(SectionType.PERSONAL) ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(sectionValue.trim()));
                    case ACHIEVEMENT, QUALIFICATIONS -> fillTextListSection(sectionValue.trim(),
                            sections, sectionType.equals(SectionType.ACHIEVEMENT) ? SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                }
            } else {
                sections.remove(sectionType);
            }
            switch (sectionType) {
                case EXPERIENCE, EDUCATION -> readOrgSection(request, r, sections, sectionType.equals(SectionType.EXPERIENCE) ?
                        SectionType.EXPERIENCE : SectionType.EDUCATION);
            }
        }
        if (newRes) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> {
                r = storage.get(uuid);
                request.setAttribute("newResume", false);
            }
            case "newResume" -> {
                r = new Resume();
                request.setAttribute("newResume", true);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void fillTextListSection(String sectionText, Map<SectionType, AbstractSection> sections, SectionType secType) {
        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> listString = tls.getListSection();
        if (sectionText != null) {
            String[] stringArray = sectionText.split("\n");
            listString.addAll(Arrays.asList(stringArray));
            listString.removeIf(String::isBlank);
        }
        sections.put(secType, tls);
    }

    private void readOrgSection(HttpServletRequest request, Resume r, Map<SectionType, AbstractSection> sections, SectionType sectionType) {
        OrganizationsSection section = (OrganizationsSection) sections.get(sectionType);
        String sectionValue = request.getParameter(sectionType.toString());
        List<Organization> organizations;
        if (sectionValue != null && !sectionValue.isBlank()) {
            organizations = section.getListOrganizations();
            String[] orgName = request.getParameterMap().get(sectionType + "orgName");
            String[] urlAddress = request.getParameterMap().get(sectionType + "urlAddress");
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (int i = 0; i < organizations.size(); i++) {
                Organization org = organizations.get(i);
                org.getHomePage().setName(orgName[i].trim());
                org.getHomePage().setUrl(urlAddress[i].trim());
                List<Organization.Experience> expList = org.getListExperience();
                String[] startDate = parameterMap.get(sectionType + Integer.toString(i) + "startDate");
                String[] endDate = parameterMap.get(sectionType + Integer.toString(i) + "endDate");
                String[] expTitle = parameterMap.get(sectionType + Integer.toString(i) + "expTitle");
                String[] expDesc = parameterMap.get(sectionType + Integer.toString(i) + "expDesc");
                for (int z = 0; z < expList.size(); z++) {
                    Organization.Experience exp = expList.get(z);
                    if ((startDate[z].isBlank() || startDate[z] == null) && (endDate[z].isBlank() || endDate[z] == null)
                            && (expTitle[z].isBlank() || expTitle[z] == null) && (expDesc[z].isBlank() || expDesc[z] == null)) {
                        expList.remove(exp);
                    } else {
                        if (!startDate[z].isBlank())
                            exp.startDate = LocalDate.parse(startDate[z]);
                        else exp.startDate = null;
                        if (!endDate[z].isBlank())
                            exp.endDate = LocalDate.parse(endDate[z]);
                        else exp.endDate = null;
                        exp.title = expTitle[z].trim();
                        exp.description = expDesc[z].trim();
                    }
                }
            }
        } else {
            organizations = new ArrayList<>();
            OrganizationsSection newOrgSec = new OrganizationsSection(organizations);
            r.addSection(sectionType, newOrgSec);
        }
        ////////////////////////////////////    New Organization adding    /////////////////////////////////////////////
        String NewOrgName = request.getParameter(sectionType + "NewOrgName").trim();
        if (!NewOrgName.isBlank()) {
            List<Organization.Experience> listExp = new ArrayList<>();
            Organization.Experience experience = addExperience(request, sectionType, "NewExpTitle",
                    "NewExpDesc", "NewStartDate", "NewEndDate");
            listExp.add(experience);
            organizations.add(new Organization(listExp, NewOrgName, request.getParameter(sectionType + "NewUrlAddress")));
        }
        ////////////////////////////////////   New Experience(Position) for Organization adding    /////////////////////////////////
        String newPosition = request.getParameter(sectionType + "newPosition");
        if (newPosition != null && !newPosition.isBlank()) {
            int index = newPosition.indexOf("http");
            String url = null;
            String name;
            if (index >= 0) {
                url = newPosition.substring(index);
                name = newPosition.substring(0, index);
            } else {
                name = newPosition;
            }
            Organization org = section.getOrganization(new Link(name, url));
            Organization.Experience expPos = addExperience(request, sectionType, "expTitlePos",
                    "expDescPos", "startDatePos", "endDatePos");
            org.getListExperience().add(expPos);
        }
    }

    private Organization.Experience addExperience(HttpServletRequest request, SectionType sectionType, String tittle,
                                                  String description, String startDate, String endDate) {
        Organization.Experience experience = new Organization.Experience(
                request.getParameter(sectionType + tittle).trim(),
                request.getParameter(sectionType + description).trim());
        String NewStartDate = request.getParameter(sectionType + startDate);
        if (!NewStartDate.isBlank()) {
            experience.startDate = LocalDate.parse(NewStartDate);
        }
        String NewEndDate = request.getParameter(sectionType + endDate);
        if (!NewEndDate.isBlank()) {
            experience.endDate = LocalDate.parse(NewEndDate);
        }
        return experience;
    }
}