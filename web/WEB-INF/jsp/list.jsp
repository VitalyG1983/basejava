<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.urise.webapp.model.ContactType" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<p></p>
<label style="margin-left: 640px" id="New resume"> Добавить новое резюме
    <br><button style="margin-left: 665px" type="button" OnClick="location.href='resume?action=newResume'">Создать резюме</button>
</label>
<p></p>
<section>
    <table style="margin-left: 440px" border="1" cellpadding="8" cellspacing="0">
        <caption><h3>Список резюме</h3></caption>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.MAIL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<p><br></p>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>