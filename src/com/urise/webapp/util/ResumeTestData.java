package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName, Boolean fillCont2) {
        Resume resume = new Resume(uuid, fullName);
        Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, AbstractSection> sections = resume.getSections();
        if (fillCont2)
            fillContacts2((EnumMap<ContactType, String>) contacts);
        else fillContacts((EnumMap<ContactType, String>) contacts);
        //   fillSections((EnumMap<SectionType, AbstractSection>) sections);
        return resume;
    }

    static void fillContacts(EnumMap<ContactType, String> contacts) {
        contacts.put(ContactType.TEL, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDLN, "Профиль LinkedIn");
        contacts.put(ContactType.GITHUB, "Профиль GitHub");
        contacts.put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
    }

    static void fillContacts2(EnumMap<ContactType, String> contacts) {
        contacts.put(ContactType.TEL, "");
        contacts.put(ContactType.SKYPE, "");
        contacts.put(ContactType.MAIL, "");
        contacts.put(ContactType.LINKEDLN, "");
        contacts.put(ContactType.GITHUB, "");
        contacts.put(ContactType.STACKOVERFLOW, "");
    }

    static void fillSections(EnumMap<SectionType, AbstractSection> sections) {
        TextSection personal = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры."));

        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> achievSection = tls.getListSection();
        achievSection.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievSection.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievSection.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция " +
                "с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция " +
                "CIFS/SMB java сервера.");
        achievSection.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring," +
                " Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievSection.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга " +
                "системы по JMX (Jython/ Django).");
        achievSection.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat," +
                " Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        sections.put(SectionType.ACHIEVEMENT, tls);

        TextListSection tlsQ = new TextListSection(new ArrayList<>());
        List<String> qualSection = tlsQ.getListSection();
        qualSection.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualSection.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualSection.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualSection.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualSection.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualSection.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualSection.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, " +
                "Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualSection.add("Python: Django.");
        qualSection.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualSection.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualSection.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM," +
                " XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, " +
                "OAuth2, JWT.");
        qualSection.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualSection.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, " +
                "iReport, OpenCmis, Bonita, pgBouncer.");
        qualSection.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                "шаблонов, UML, функционального программирования");
        qualSection.add("Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATIONS, tlsQ);

//////////////////////////////--------JobSection------//////////////////////////////////////////////
        OrganizationsSection jobSection = new OrganizationsSection(new ArrayList<>());
        List<Organization.Experience> jobDescList1 = new ArrayList<>();
        jobDescList1.add(new Organization.Experience(2013, Month.OCTOBER, "  Автор проекта",
                "                       Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization jobText1 = new Organization(jobDescList1, "Java Online Projects", "https://java_online_projects.ru");
        jobSection.getListOrganizations().add(jobText1);

        List<Organization.Experience> jobDescList2 = new ArrayList<>();
        jobDescList2.add(new Organization.Experience(2014, Month.OCTOBER, 2016, Month.JANUARY,
                "  Старший разработчик (backend)",
                "                       Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                        "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Organization jobText2 = new Organization(jobDescList2, "Wrike", "https://wrike.ru");
        jobSection.getListOrganizations().add(jobText2);

        List<Organization.Experience> jobDescList3 = new ArrayList<>();
        jobDescList3.add(new Organization.Experience(2012, Month.APRIL, 2014, Month.OCTOBER,
                "  Java архитектор", "                       Организация процесса разработки системы " +
                "ERP для разных окружений: релизная " +
                "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                " конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части " +
                "системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security," +
                " Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting " +
                "via ssh tunnels, PL/Python"));
        Organization jobText3 = new Organization(jobDescList3, "RIT Center", "https://ritcenter.ru");
        jobSection.getListOrganizations().add(jobText3);

        List<Organization.Experience> jobDescList4 = new ArrayList<>();
        jobDescList4.add(new Organization.Experience(2010, Month.DECEMBER, 2012, Month.APRIL,
                "  Ведущий специалист",
                "                       Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring," +
                        " Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация " +
                        "RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Organization jobText4 = new Organization(jobDescList4, "Luxoft (Deutsche Bank)", "https://luxoft.ru");
        jobSection.getListOrganizations().add(jobText4);

        List<Organization.Experience> jobDescList5 = new ArrayList<>();
        jobDescList5.add(new Organization.Experience(2008, Month.JUNE, 2010, Month.DECEMBER,
                "  Ведущий специалист",
                "                       Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента " +
                        "(Python/ Jython, Django, ExtJS)"));
        Organization jobText5 = new Organization(jobDescList5, "Yota", "https://yota.ru");
        jobSection.getListOrganizations().add(jobText5);

        List<Organization.Experience> jobDescList6 = new ArrayList<>();
        jobDescList6.add(new Organization.Experience(2007, Month.MARCH, 2008, Month.JUNE,
                "  Разработчик ПО",
                "                       Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, " +
                        "Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        Organization jobText6 = new Organization(jobDescList6, "Enkata", "https://enkata.ru");
        jobSection.getListOrganizations().add(jobText6);

        List<Organization.Experience> jobDescList7 = new ArrayList<>();
        jobDescList7.add(new Organization.Experience(2005, Month.JANUARY, 2007, Month.FEBRUARY,
                "  Разработчик ПО",
                "                       Разработка информационной модели, проектирование интерфейсов, реализация и " +
                        "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        Organization jobText7 = new Organization(jobDescList7, "Siemens AG", null);
        jobSection.getListOrganizations().add(jobText7);

        List<Organization.Experience> jobDescList8 = new ArrayList<>();
        jobDescList8.add(new Organization.Experience(1997, Month.SEPTEMBER, 2005, Month.JANUARY,
                "  Инженер по аппаратному и программному тестированию",
                "                       Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel " +
                        "1000 S12 (CHILL, ASM)."));
        Organization jobText8 = new Organization(jobDescList8, "Alcatel", null);
        jobSection.getListOrganizations().add(jobText8);

        sections.put(SectionType.EXPERIENCE, jobSection);

//////////////////////////////--------EducationSection------//////////////////////////////////////////////
        OrganizationsSection eduSection = new OrganizationsSection(new ArrayList<>());
        List<Organization.Experience> eduDescList1 = new ArrayList<>();
        eduDescList1.add(new Organization.Experience(2013, Month.MARCH, 2013, Month.MAY, "",
                "  Functional Programming Principles in Scala\" by Martin Odersky"));
        Organization eduText1 = new Organization(eduDescList1, "Coursera", "https://coursera.ru");
        eduSection.getListOrganizations().add(eduText1);

        List<Organization.Experience> eduDescList2 = new ArrayList<>();
        eduDescList2.add(new Organization.Experience(2011, Month.MARCH, 2011, Month.APRIL, "",
                "  Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        Organization eduText2 = new Organization(eduDescList2, "Luxoft", "https://luxoft.ru");
        eduSection.getListOrganizations().add(eduText2);

        List<Organization.Experience> eduDescList3 = new ArrayList<>();
        eduDescList3.add(new Organization.Experience(2005, Month.JANUARY, 2005, Month.APRIL, "",
                "  3 месяца обучения мобильным IN сетям (Берлин)"));
        Organization eduText3 = new Organization(eduDescList3, "Siemens AG", "https://siemens.ru");
        eduSection.getListOrganizations().add(eduText3);

        List<Organization.Experience> eduDescList4 = new ArrayList<>();
        eduDescList4.add(new Organization.Experience(1997, Month.SEPTEMBER, 1998, Month.MARCH, "",
                "  6 месяцев обучения цифровым телефонным сетям (Москва)"));
        Organization eduText4 = new Organization(eduDescList4, "Alcatel", "https://alcatel.ru");
        eduSection.getListOrganizations().add(eduText4);

        List<Organization.Experience> eduDescList5 = new ArrayList<>();
        eduDescList5.add(new Organization.Experience(1993, Month.SEPTEMBER, 1996, Month.JULY, "",
                "  Аспирантура (программист С, С++)"));
        eduDescList5.add(new Organization.Experience(1987, Month.SEPTEMBER, 1993, Month.JULY, null,
                "  Инженер (программист Fortran, C)"));
        Organization eduText5 = new Organization(eduDescList5, "Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики", null);
        eduSection.getListOrganizations().add(eduText5);

        List<Organization.Experience> eduDescList7 = new ArrayList<>();
        eduDescList7.add(new Organization.Experience(1984, Month.SEPTEMBER, 1987, Month.JUNE, null,
                null));
        Organization eduText7 = new Organization(eduDescList7, "Заочная физико-техническая школа при МФТИ", null);
        eduSection.getListOrganizations().add(eduText7);
        sections.put(SectionType.EDUCATION, eduSection);
    }

    public static void main(String[] args) {
        Resume res = new Resume("Григорий Кислин");
        Map<ContactType, String> contacts = res.getContacts();
        Map<SectionType, AbstractSection> sections = res.getSections();
        fillContacts((EnumMap<ContactType, String>) contacts);
        fillSections((EnumMap<SectionType, AbstractSection>) sections);
        System.out.println(res.getFullName() + "\n");
        for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry);
        }
        for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            System.out.println("\n" + entry.getKey().getTitle());
            TextSection textSection = entry.getValue() instanceof TextSection ? ((TextSection) entry.getValue()) : null;
            TextListSection textListSection = entry.getValue() instanceof TextListSection ?
                    ((TextListSection) entry.getValue()) : null;
            OrganizationsSection organizations = entry.getValue() instanceof OrganizationsSection ?
                    ((OrganizationsSection) entry.getValue()) : null;
            if (textSection != null)
                System.out.println(textSection.getText());
            else if (textListSection != null) {
                for (String text : textListSection.getListSection())
                    System.out.println("- " + text);
            } else if (organizations != null) {
                for (Organization organization : organizations.getListOrganizations()) {
                    if (organization.getHomePage().getName() == null) {
                    } else System.out.println(organization.getHomePage().getName());
                    for (Organization.Experience org : organization.getListExperience()) {
                        System.out.print(org.getStartDate());
                        if (org.getEndDate() != null)
                            System.out.print(" - " + org.getEndDate());
                        else System.out.print(" - Сейчас");
                        if (org.getTitle() == null) {
                        } else System.out.println(org.getTitle());
                        if (org.getDescription() == null) {
                        } else System.out.println("  " + org.getDescription());
                    }
                }
            }
        }
    }
}