package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.util.EnumMap;
import java.util.List;

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

        TextListSection achievmentList = new TextListSection();
        achievmentList.getTextListSection().add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n" +
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n" +
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n" +
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n" +
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n" +
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        sections.put(SectionType.ACHIEVEMENT, achievmentList);

        TextListSection qualificationsList = new TextListSection();
        qualificationsList.getTextListSection().add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n" +
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce\n" +
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                "MySQL, SQLite, MS SQL, HSQLDB\n" +
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,\n" +
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,\n" +
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).\n" +
                "Python: Django.\n" +
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js\n" +
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka\n" +
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\n" +
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,\n" +
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.\n" +
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования\n" +
                "Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATIONS, qualificationsList);

//////////////////////////////--------JobSection------//////////////////////////////////////////////
        JobEducationSection jobSection = new JobEducationSection();
        JobEducationTextSection jobText0 = new JobEducationTextSection("Java Online Projects", "10/2013 - Сейчас");
        jobText0.setJobEducationText("Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobSection.getJobEducationSection().add(jobText0);
        JobEducationTextSection jobText1 = new JobEducationTextSection("Wrike", "10/2014 - 01/2016");
        jobText1.setJobEducationText("Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        jobSection.getJobEducationSection().add(jobText1);
        JobEducationTextSection jobText2 = new JobEducationTextSection("RIT Center", "04/2012 - 10/2014");
        jobText2.setJobEducationText("Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobSection.getJobEducationSection().add(jobText2);
        JobEducationTextSection jobText3 = new JobEducationTextSection("Luxoft (Deutsche Bank)", "12/2010 - 04/2012");
        jobText3.setJobEducationText("Ведущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobSection.getJobEducationSection().add(jobText3);
        JobEducationTextSection jobText4 = new JobEducationTextSection("Yota", "06/2008 - 12/2010");
        jobText4.setJobEducationText("Ведущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobSection.getJobEducationSection().add(jobText4);
        JobEducationTextSection jobText5 = new JobEducationTextSection("Enkata", "03/2007 - 06/2008");
        jobText5.setJobEducationText("Разработчик ПО\n" +
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        jobSection.getJobEducationSection().add(jobText5);
        JobEducationTextSection jobText6 = new JobEducationTextSection("Siemens AG", "01/2005 - 02/2007");
        jobText6.setJobEducationText("Разработчик ПО\n" +
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        jobSection.getJobEducationSection().add(jobText6);
        JobEducationTextSection jobText7 = new JobEducationTextSection("Alcatel", "09/1997 - 01/2005");
        jobText7.setJobEducationText("Инженер по аппаратному и программному тестированию\n" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        jobSection.getJobEducationSection().add(jobText7);
        sections.put(SectionType.EXPERIENCE, jobSection);

//////////////////////////////--------EducationSection------//////////////////////////////////////////////
        JobEducationSection educationSection = new JobEducationSection();
        JobEducationTextSection educationText0 = new JobEducationTextSection("Coursera", "03/2013 - 05/2013");
        educationText0.setJobEducationText("Functional Programming Principles in Scala\" by Martin Odersky");
        educationSection.getJobEducationSection().add(educationText0);
        JobEducationTextSection educationText1 = new JobEducationTextSection("Luxoft", "03/2011 - 04/2011");
        educationText1.setJobEducationText("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");
        educationSection.getJobEducationSection().add(educationText1);
        JobEducationTextSection educationText2 = new JobEducationTextSection("Siemens AG", "01/2005 - 04/2005");
        educationText2.setJobEducationText("3 месяца обучения мобильным IN сетям (Берлин)");
        educationSection.getJobEducationSection().add(educationText2);
        JobEducationTextSection educationText3 = new JobEducationTextSection("Alcatel", "09/1997 - 03/1998");
        educationText3.setJobEducationText("6 месяцев обучения цифровым телефонным сетям (Москва)");
        educationSection.getJobEducationSection().add(educationText3);
        JobEducationTextSection educationText4 = new JobEducationTextSection("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1993 - 07/1996\t" + "\n09/1987 - 07/1993");
        educationText4.setJobEducationText("Аспирантура (программист С, С++)" + "\nИнженер (программист Fortran, C)");
        educationSection.getJobEducationSection().add(educationText4);
        JobEducationTextSection educationText5 = new JobEducationTextSection("Заочная физико-техническая школа при МФТИ", "09/1984 - 06/1987");
        educationText5.setJobEducationText("Закончил с отличием");
        educationSection.getJobEducationSection().add(educationText5);
        sections.put(SectionType.EDUCATION, educationSection);
    }

    public static void main(String[] args) {
        ResumeTestData test = new ResumeTestData();
        Resume res = new Resume("Григорий Кислин");
        EnumMap<ContactType, String> contacts = res.getContacts();
        EnumMap<SectionType, AbstractSection> sections = res.getSections();
        test.fillContacts(contacts);
        test.fillSections(sections);
        System.out.println(res.getFullName() + "\n");
        for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry);
        }
        if (sections.containsKey(SectionType.PERSONAL))
            System.out.println("\n" + SectionType.PERSONAL.getTitle());
        System.out.println(((TextSection) sections.get(SectionType.PERSONAL)).getTextSection());
        if (sections.containsKey(SectionType.OBJECTIVE))
            System.out.println("\n" + SectionType.OBJECTIVE.getTitle());
        System.out.println(((TextSection) sections.get(SectionType.OBJECTIVE)).getTextSection());
        if (sections.containsKey(SectionType.ACHIEVEMENT))
            System.out.println("\n" + SectionType.ACHIEVEMENT.getTitle());
        System.out.println(((TextListSection) sections.get(SectionType.ACHIEVEMENT)).getTextListSection());
        if (sections.containsKey(SectionType.QUALIFICATIONS))
            System.out.println("\n" + SectionType.QUALIFICATIONS.getTitle());
        System.out.println(((TextListSection) sections.get(SectionType.QUALIFICATIONS)).getTextListSection());
        if (sections.containsKey(SectionType.EXPERIENCE))
            System.out.println("\n" + SectionType.EXPERIENCE.getTitle());
        List<JobEducationTextSection> jobSection = ((JobEducationSection) sections.get(SectionType.EXPERIENCE)).getJobEducationSection();
        for (JobEducationTextSection job : jobSection) {
            System.out.println(job.getJobEducationName());
            System.out.println(job.getJobEducationDate());
            System.out.println(job.getJobEducationText());
        }
        if (sections.containsKey(SectionType.EDUCATION))
            System.out.println("\n" + SectionType.EDUCATION.getTitle());
        List<JobEducationTextSection> educationSection = ((JobEducationSection) sections.get(SectionType.EDUCATION)).getJobEducationSection();
        for (JobEducationTextSection education : educationSection) {
            System.out.println(education.getJobEducationName());
            System.out.println(education.getJobEducationDate());
            System.out.println(education.getJobEducationText());
        }
    }
}