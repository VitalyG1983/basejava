package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    void fillContacts(EnumMap<ContactType, String> contacts) {
        contacts.put(ContactType.TEL, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDLN, "Профиль LinkedIn");
        contacts.put(ContactType.GITHUB, "Профиль GitHub");
        contacts.put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
    }

    void fillSections(EnumMap<SectionType, AbstractSection> sections) {
        TextSection personal = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        TextListSection tls = new TextListSection();
        List<String> achievSection = tls.getListSection();
        achievSection.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievSection.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievSection.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievSection.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievSection.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievSection.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        sections.put(SectionType.ACHIEVEMENT, tls);

        TextListSection tlsQ = new TextListSection();
        List<String> qualSection = tlsQ.getListSection();
        qualSection.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualSection.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualSection.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualSection.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualSection.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualSection.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualSection.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualSection.add("Python: Django.");
        qualSection.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualSection.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualSection.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualSection.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualSection.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualSection.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualSection.add("Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATIONS, tlsQ);

//////////////////////////////--------JobSection------//////////////////////////////////////////////
        Organization jobSection = new Organization();
        Experience jobText0 = new Experience("Java Online Projects", YearMonth.of(2013, 10), null);
        jobText0.setText("Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobSection.getJobEducationSection().add(jobText0);
        Experience jobText1 = new Experience("Wrike", YearMonth.of(2014, 10), YearMonth.of(2016, 1));
        jobText1.setText("Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        jobSection.getJobEducationSection().add(jobText1);
        Experience jobText2 = new Experience("RIT Center", YearMonth.of(2012, 4), YearMonth.of(2014, 10));
        jobText2.setText("Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobSection.getJobEducationSection().add(jobText2);
        Experience jobText3 = new Experience("Luxoft (Deutsche Bank)", YearMonth.of(2010, 12), YearMonth.of(2012, 4));
        jobText3.setText("Ведущий специалист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        jobSection.getJobEducationSection().add(jobText3);
        Experience jobText4 = new Experience("Yota", YearMonth.of(2008, 6), YearMonth.of(2010, 12));
        jobText4.setText("Ведущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobSection.getJobEducationSection().add(jobText4);
        Experience jobText5 = new Experience("Enkata", YearMonth.of(2007, 3), YearMonth.of(2008, 6));
        jobText5.setText("Разработчик ПО\n" +
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        jobSection.getJobEducationSection().add(jobText5);
        Experience jobText6 = new Experience("Siemens AG", YearMonth.of(2005, 1), YearMonth.of(2007, 2));
        jobText6.setText("Разработчик ПО\n" +
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        jobSection.getJobEducationSection().add(jobText6);
        Experience jobText7 = new Experience("Alcatel", YearMonth.of(1997, 9), YearMonth.of(2005, 1));
        jobText7.setText("Инженер по аппаратному и программному тестированию\n" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        jobSection.getJobEducationSection().add(jobText7);
        sections.put(SectionType.EXPERIENCE, jobSection);

//////////////////////////////--------EducationSection------//////////////////////////////////////////////
        Organization educationSection = new Organization();
        Experience educationText0 = new Experience("Coursera", YearMonth.of(2013, 3), YearMonth.of(2013, 5));
        educationText0.setText("Functional Programming Principles in Scala\" by Martin Odersky");
        educationSection.getJobEducationSection().add(educationText0);
        Experience educationText1 = new Experience("Luxoft", YearMonth.of(2011, 3), YearMonth.of(2011, 4));
        educationText1.setText("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");
        educationSection.getJobEducationSection().add(educationText1);
        Experience educationText2 = new Experience("Siemens AG", YearMonth.of(2005, 1), YearMonth.of(2005, 4));
        educationText2.setText("3 месяца обучения мобильным IN сетям (Берлин)");
        educationSection.getJobEducationSection().add(educationText2);
        Experience educationText3 = new Experience("Alcatel", YearMonth.of(1997, 9), YearMonth.of(1998, 3));
        educationText3.setText("6 месяцев обучения цифровым телефонным сетям (Москва)");
        educationSection.getJobEducationSection().add(educationText3);
        Experience educationText4 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", YearMonth.of(1993, 9), YearMonth.of(1996, 7));
        educationText4.setText("Аспирантура (программист С, С++)" + "\nИнженер (программист Fortran, C)");
        educationSection.getJobEducationSection().add(educationText4);
        Experience educationText5 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", YearMonth.of(1987, 9), YearMonth.of(1993, 7));
        educationText5.setText("Аспирантура (программист С, С++)" + "\nИнженер (программист Fortran, C)");
        educationSection.getJobEducationSection().add(educationText5);
        Experience educationText6 = new Experience("Заочная физико-техническая школа при МФТИ", YearMonth.of(1984, 9), YearMonth.of(1987, 6));
        educationText6.setText("Закончил с отличием");
        educationSection.getJobEducationSection().add(educationText6);
        sections.put(SectionType.EDUCATION, educationSection);
    }

    public static void main(String[] args) {
        ResumeTestData test = new ResumeTestData();
        Resume res = new Resume("Григорий Кислин");
        Map<ContactType, String> contacts = res.getContacts();
        Map<SectionType, AbstractSection> sections = res.getSections();
        test.fillContacts((EnumMap<ContactType, String>) contacts);
        test.fillSections((EnumMap<SectionType, AbstractSection>) sections);
        System.out.println(res.getFullName() + "\n");
        for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry);
        }
        if (sections.containsKey(SectionType.PERSONAL)) {
            System.out.println("\n" + SectionType.PERSONAL.getTitle());
            System.out.println(((TextSection) sections.get(SectionType.PERSONAL)).getText());
        }
        if (sections.containsKey(SectionType.OBJECTIVE)) {
            System.out.println("\n" + SectionType.OBJECTIVE.getTitle());
            System.out.println(((TextSection) sections.get(SectionType.OBJECTIVE)).getText());
        }
        if (sections.containsKey(SectionType.ACHIEVEMENT)) {
            System.out.println("\n" + SectionType.ACHIEVEMENT.getTitle());
            List<String> tls = ((TextListSection) sections.get(SectionType.ACHIEVEMENT)).getListSection();
            for (String text : tls)
                System.out.println("- " + text);
        }
        if (sections.containsKey(SectionType.QUALIFICATIONS)) {
            System.out.println("\n" + SectionType.QUALIFICATIONS.getTitle());
            List<String> tls = ((TextListSection) sections.get(SectionType.QUALIFICATIONS)).getListSection();
            for (String text : tls)
                System.out.println("- " + text);
        }
        if (sections.containsKey(SectionType.EXPERIENCE)) {
            System.out.println("\n" + SectionType.EXPERIENCE.getTitle());
            List<Experience> jobSection = ((Organization) sections.get(SectionType.EXPERIENCE)).getJobEducationSection();
            for (Experience job : jobSection) {
                System.out.println(job.getName());
                System.out.println(job.getStartDate());
                System.out.println(job.getText());
            }
        }
        if (sections.containsKey(SectionType.EDUCATION)) {
            System.out.println("\n" + SectionType.EDUCATION.getTitle());
            List<Experience> educationSection = ((Organization) sections.get(SectionType.EDUCATION)).getJobEducationSection();
            for (Experience education : educationSection) {
                System.out.println(education.getName());
                System.out.println(education.getStartDate());
                System.out.println(education.getText());
            }
        }
    }
}