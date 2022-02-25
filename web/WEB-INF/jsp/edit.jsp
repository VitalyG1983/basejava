<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>--%>
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

    label {
        vertical-align: top;
    }

    input {
        margin-left: 50px;
    }

    h2, h3 {
        margin: 50px 0 15px;
    }

</style>--%>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form name="editForm" method="post" action="resume"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="newResume" value="${newResume}">
        <h3>Имя:<input type="text" name="fullName" size=50 value="${resume.fullName}" required
                       pattern="[\s]{0,}[А-Яа-яa-zA-Z0-9]{1,}[А-Яа-яa-zA-Z0-9\s]{0,}"
                       title="'Имя' не должно быть пустым и содержать специальных символов"
                       style="margin-left: 200px">
        </h3>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl <%--style="margin-left: 30px"--%>>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr/>
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
                    <input type="hidden" name="${sectionType}" value="${orgSection}">
                    <h3>${sectionTittle}</h3>
                    <c:if test="${orgSection != null}">
                        <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationsSection"/>
                        <c:set var="listOrg" value="<%=orgSection.getListOrganizations()%>"/>
                        <c:forEach var="org" items="${listOrg}" varStatus="counter">
                            <p></p>
                            <label><b>Имя организации</b>
                                <input type="text" name="${sectionType}orgName" size="80" value="${org.homePage.name}">
                            </label><br>
                            <label>URL адрес
                                <input style="margin-left: 55px" type="url" name="${sectionType}urlAddress" size="30"
                                       value="${org.homePage.url}">
                            </label>
                            <div class="edit\experience">
                                <c:forEach var="experience" items="${org.listExperience}" varStatus="expCounter">
                                    <dl>
                                        <dt>Дата начала</dt>
                                        <dd><input type="date" name="${sectionType}${counter.index}startDate" size="30"
                                                   value="${experience.startDate}"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Дата конца</dt>
                                        <dd><input type="date" name="${sectionType}${counter.index}endDate" size="30"
                                                   value="${experience.endDate}"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}expTitle" size="50"
                                                   value="${experience.title}"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание</dt>
                                        <dd><textarea rows="5" cols="65"
                                                      name="${sectionType}${counter.index}expDesc">${experience.description}</textarea>
                                        </dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                        <br>
                        <div style="margin-left: 150px">
                        <c:if test="${sectionType == SectionType.EXPERIENCE}">
                            <h4>Добавление описания(позиции) для места работы</h4>
                        </c:if>
                        <c:if test="${sectionType == SectionType.EDUCATION}">
                            <h4>Добавление описания(позиции) для места учебы</h4>
                        </c:if>
                        <label>Выберите организацию</label>
                        <select style="margin-left: 32px" name="${sectionType}newPosition">
                            <option></option>
                            <c:forEach var="org" items="${listOrg}" varStatus="orgCounter">
                                <option value="${org.homePage.name}${org.homePage.url}">${org.homePage.name}</option>
                            </c:forEach>
                        </select>
                        <dl>
                            <dt>Дата начала</dt>
                            <dd><input type="date" name="${sectionType}startDatePos" size="30"></dd>
                        </dl>
                        <dl>
                            <dt>Дата конца</dt>
                            <dd><input type="date" name="${sectionType}endDatePos" size="30"></dd>
                        </dl>
                        <dl>
                            <dt>Должность</dt>
                            <dd><input type="text" name="${sectionType}expTitlePos" size="50"></dd>
                        </dl>
                        <dl>
                            <dt>Описание</dt>
                            <dd><textarea rows="5" cols="65" name="${sectionType}expDescPos"></textarea></dd>
                        </dl>
                    </c:if>
                    <c:if test="${sectionType == SectionType.EXPERIENCE}">
                        <h4>Добавить новое место работы</h4>
                    </c:if>
                    <c:if test="${sectionType == SectionType.EDUCATION}">
                        <h4>Добавить новое место учебы</h4>
                    </c:if>
                    <dl>
                        <dt>Имя организации</dt>
                        <dd><input type="text" name="${sectionType}NewOrgName" size="80"></dd>
                    </dl>
                    <dl>
                        <dt>URL адрес</dt>
                        <dd><input type="url" name="${sectionType}NewUrlAddress" size="30"></dd>
                    </dl>
                    <dl>
                        <dt>Дата начала</dt>
                        <dd><input type="date" name="${sectionType}NewStartDate" size="30"></dd>
                    </dl>
                    <dl>
                        <dt>Дата конца</dt>
                        <dd><input type="date" name="${sectionType}NewEndDate" size="30"></dd>
                    </dl>
                    <dl>
                        <dt>Должность</dt>
                        <dd><input type="text" name="${sectionType}NewExpTitle" size="50"></dd>
                    </dl>
                    <dl>
                        <dt>Описание</dt>
                        <dd><textarea rows="5" cols="65" name="${sectionType}NewExpDesc"></textarea></dd>
                    </dl>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
        <p><br></p>
        <button style="margin-left: 200px" type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back();return false">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>