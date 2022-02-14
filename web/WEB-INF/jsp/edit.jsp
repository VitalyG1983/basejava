<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<%--<style>
    table {
        border-spacing: 2px 10px;
    }
</style>--%>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form name="editForm" method="post" action="resume" onsubmit="return checkFields(this);"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" required size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <%-------------------------------------  Секции--------------------------------------------------%>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <%--  <c:forEach var="sectionEntry" items="${resume.sections.entrySet()}">--%>


            <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
            <%--  <jsp:useBean id="sectionEntry"
                       type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>--%>
            <%-- <c:set var="typeSection" value="<%=sectionEntry.getKey().name()%>"/>--%>
            <c:set var="sectionTittle" value="<%=sectionType.getTitle()%>"/>

            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}" >
                    <h3>${sectionTittle}</h3>
                    <c:set var="textSection" value="${resume.sections.get(sectionType)}"/>
                    <c:choose>
                        <c:when test="${textSection != null}">
                            <jsp:useBean id="textSection" type="com.urise.webapp.model.TextSection"/>
                            <c:set var="text" value="<%=textSection.getText()%>"/>
                            <textarea rows="5" cols="65" name="textSection">${text} </textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea rows="5" cols="65" name="textSection"> </textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <h3>${sectionTittle}</h3>
                    <c:set var="textListSection" value="${resume.sections.get(sectionType)}"/>
                    <c:choose>
                        <c:when test="${textListSection != null}">
                            <jsp:useBean id="textListSection" type="com.urise.webapp.model.TextListSection"/>
                            <c:set var="joinedTextList" value='<%=String.join("\n", textListSection.getListSection())%>'/>
                            <textarea rows="20" cols="65" name="tls">${joinedTextList} </textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea rows="5" cols="65" name="textSection"> </textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <%-------------------------------------  EXPERIENCE, EDUCATION--------------------------------------------------%>
                <%-- <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                     <h3>${sectionTittle}</h3>
                     <c:if test="${sectionEntry.value != null}">
                         <c:set var="orgSection" value="<%=sectionEntry.getValue()%>"/>
                         <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationsSection"/>
                         <c:set var="listOrg" value="<%=orgSection.getListOrganizations()%>"/>
                         <table>
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
                                     <td style="width:180px" valign="top">${experience.startDate} - ${experience.endDate == null ? "сейчас":experience.endDate}</td>
                                     <c:choose>
                                         <c:when test="${experience.title == null || experience.title == ''}">
                                             <td valign="top"> ${experience.description}
                                             </td>
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
                                 <tr> <td colspan="2"> <p><br></p> </td>
                                 </tr>
                             </c:forEach>
                         </table>
                     </c:if>
                 </c:when>--%>
            </c:choose>
        </c:forEach>

        <input type="text" name="section" size=30 value="1"><br/>
        <input type="text" name="section" size=30 value="2"><br/>
        <input type="text" name="section" size=30 value="3"><br/>
        <hr>
        <button type="submit" onsubmit="return checkFields(this);" onclick="checkFields(this)">Сохранить </button>
        <button onclick="window.history.back()">Отменить</button>
        <script type="text/javascript">
            function checkFields(editForm) {
                if (editForm.fullName.value === "") {
                    alert("Пожалуйста, введите правильное 'Имя'");
                    return false;
                }
                return true ;
            }
        </script>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>