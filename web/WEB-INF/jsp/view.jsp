<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.TextListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>

        <c:forEach var="sectionEntry" items="${resume.sections.entrySet()}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="typeSection" value="<%=sectionEntry.getKey().name()%>"/>

           <c:choose>
             <c:when test="${typeSection == SectionType.PERSONAL || typeSection == SectionType.OBJECTIVE}" >
                  <h4> ${typeSection}&nbsp;</h4>
                  <c:set var="textSection" value="<%=sectionEntry.getValue()%>"/>
                  <jsp:useBean id="textSection" type="com.urise.webapp.model.TextSection"/>
                  <c:set var="text" value="<%=textSection.getText()%>"/>
                  <c:if test="${text != null}">
                          <p>${text}</p>
                  </c:if>
             </c:when>
             <c:when test="${typeSection == SectionType.ACHIEVEMENT || typeSection == SectionType.QUALIFICATIONS}">
                  <h4> ${typeSection}&nbsp;</h4>
                  <c:if test="${sectionEntry.value != null}">
                      <c:set var="textListSection" value="<%=sectionEntry.getValue()%>"/>
                      <jsp:useBean id="textListSection" type="com.urise.webapp.model.TextListSection"/>
                      <c:set var="listText" value="<%=textListSection.getListSection()%>"/>
                      <c:forEach var="text" items="${listText}">
                          <p> - ${text} </p>
                      </c:forEach>
                  </c:if>
             </c:when>
             <c:when test="${typeSection == SectionType.EXPERIENCE || typeSection == SectionType.EDUCATION}">
                 <h4> ${typeSection}</h4>
                 <c:if test="${sectionEntry.value != null}">
                      <c:set var="orgSection" value="<%=sectionEntry.getValue()%>"/>
                      <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationsSection"/>
                      <c:set var="listOrg" value="<%=orgSection.getListOrganizations()%>"/>
                     <table valign="top">
                      <c:forEach var="org" items="${listOrg}">
                          <c:if test="${org.homePage.name != null}">
                              <tr>
                                  <c:choose>
                                  <c:when test="${org.homePage.url != null}">
                                      <td colspan="2"><b><a href="${org.homePage.url}">${org.homePage.name}</a></b></td>
                                  </c:when>
                                  <c:otherwise>
                                      <td colspan="2"><b>${org.homePage.name}</b></td>
                                  </c:otherwise>
                                  </c:choose>
                              </tr>
                          </c:if>
                          <c:forEach var="experience" items="${org.listExperience}">
                                  <tr>
                                      <td style="width:20%" valign="top">${experience.startDate} - ${experience.endDate == null ? "сейчас":experience.endDate}</td>
                                      <c:choose>
                                           <c:when test="${experience.title == null || experience.title == ''}">
                                              <td valign="top"> ${experience.description}</td>
                                  </tr>
                                           </c:when>
                                      <c:otherwise>
                                             <td valign="top"> <b>${experience.title} </b><br>
                                                     ${experience.description}
                                             </td>
                                  </tr>
                                      </c:otherwise>
                                      </c:choose>
                          </c:forEach>
                      </c:forEach>
                     </table>
                </c:if>
             </c:when>
           </c:choose>
        </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>