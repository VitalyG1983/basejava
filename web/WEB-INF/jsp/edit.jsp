<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="newResume" type="java.lang.Boolean" scope="request"/>
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
        <input type="hidden" name="newResume" value="${newResume}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required
                       pattern="[\s]{0,}[А-Яа-яa-zA-Z0-9]{1,}[А-Яа-яa-zA-Z0-9\s]{0,}"
                       title="'Имя' не должно быть пустым и содержать специальных символов"></dd>
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
            <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
            <c:set var="sectionTittle" value="<%=sectionType.getTitle()%>"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                    <h3>${sectionTittle}</h3>
                    <c:set var="textSection" value="${resume.sections.get(sectionType)}"/>
                    <c:choose>
                        <c:when test="${textSection != null}">
                            <jsp:useBean id="textSection" type="com.urise.webapp.model.TextSection"/>
                            <c:set var="text" value="<%=textSection.getText()%>"/>
                            <textarea rows="5" cols="65" name="${sectionType}">${text}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea rows="5" cols="65" name="${sectionType}"></textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <h3>${sectionTittle}</h3>
                    <c:set var="textListSection" value="${resume.sections.get(sectionType)}"/>
                    <c:choose>
                        <c:when test="${textListSection != null}">
                            <jsp:useBean id="textListSection" type="com.urise.webapp.model.TextListSection"/>
                            <c:set var="joinedTextList"
                                   value='<%=String.join("\n", textListSection.getListSection())%>'/>
                            <textarea rows="20" cols="65" name="${sectionType}">${joinedTextList}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea rows="5" cols="65" name="${sectionType}"></textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <%-------------------------------------  EXPERIENCE, EDUCATION--------------------------------------------------%>
                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                    <c:set var="orgSection" value="${resume.sections.get(sectionType)}"/>
                    <c:if test="${orgSection != null}">
                        <h3>${sectionTittle}</h3>
                        <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationsSection"/>
                     <%--   <jsp:useBean id="orgName" type="java.lang.String"/>--%>
                        <c:set var="listOrg" value="<%=orgSection.getListOrganizations()%>"/>
                        <c:forEach var="org" items="${listOrg}" varStatus="counter">
                            <input type="hidden" name="${sectionType}" value="${counter.count}">
                            <%--   <c:if test="${org.homePage.name != null}">--%>
                            <%--   <c:choose>--%>
                            <%--  <c:when test="${org.homePage.url != null}">--%>
                            <p> ${counter.index}</p>
                            <p> ${counter.count}</p>
                            <dl>
                                <dt><b>Имя организации</b></dt>
                                <dd><input type="text" name="${sectionType}orgName" size="80" value="${org.homePage.name}"></dd>
                            </dl>
                            <label>URL адрес
                                <input type="text" name="${sectionType}urlAddress" size="30" value="${org.homePage.url}">
                            </label>
                            <c:forEach var="experience" items="${org.listExperience}" varStatus="expCounter">
                                <label><br>Дата начала
                                    <input type="text" name="${sectionType}startDate" size="30" value="${experience.startDate}">
                                </label><br>
                                <label>Дата конца
                                    <input type="text" name="${sectionType}endDate" size="30" value="${experience.endDate}">
                                </label><br>
                                <label>Должность
                                    <input type="text" name="${sectionType}expTitle" size="50" value="${experience.title}">
                                </label>
                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea rows="5" cols="65" name="${sectionType}expDesc">"${experience.description}"</textarea></dd>
                                </dl>
                            </c:forEach>
                        </c:forEach>
                    </c:if>
                    <br>
                    <c:if test="${sectionType == SectionType.EXPERIENCE}">
                        <h3>Добавить новое место работы</h3>
                    </c:if>
                    <c:if test="${sectionType == SectionType.EDUCATION}">
                        <h3>Добавить новое место учебы</h3>
                    </c:if>
                    <label>Имя организации
                        <input type="text" name="NewOrgName${sectionType}" size="80">
                    </label><br>
                    <label>URL адрес
                        <input type="text" name="NewUrlAddress${sectionType}" size="30">
                    </label><br>
                    <label>Дата начала
                        <input type="text" name="NewStartDate${sectionType}" size="30">
                    </label><br>
                    <label>Дата конца
                        <input type="text" name="NewEndDate${sectionType}" size="30">
                    </label><br>
                    <label>Должность
                        <input type="text" name="NewExpTitle${sectionType}" size="50">
                    </label>
                    <dl>
                        <dt>Описание</dt>
                        <dd><textarea rows="5" cols="65" name="NewExpDesc${sectionType}"></textarea></dd>
                    </dl>

                </c:when>
            </c:choose>
        </c:forEach>

        <%--   <input type="text" name="section" size=30 value="1"><br/>
           <input type="text" name="section" size=30 value="2"><br/>
           <input type="text" name="section" size=30 value="3"><br/>
           <hr>--%>
        <p><br></p>
        <button type="submit" onsubmit="return checkFields(this);">Сохранить</button>
        <button type="reset" onclick="window.history.back();return false">Отменить</button>
        <script type="text/javascript">
            function checkFields(editForm) {
                if (editForm.fullName.value === "") {
                    alert("Пожалуйста, введите правильное 'Имя'");
                    return false;
                }
                return true;
            }
        </script>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>