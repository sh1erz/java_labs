<%@ page import="controller.command.CommandList" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Doctor Main</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<h2>Hello, ${userDoctor.name}</h2>
<p>Specialisation ${userDoctor.doctorSpecialisationName.name()}</p>
<h2>Select patient</h2>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="<%=CommandList.CHOOSE_PATIENT%>">
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Birth</th>
            <th>Diagnosis</th>
            <th>Choose</th>
        </tr>
        </thead>
        <tbody>
        <form action="/controller" method="post">
            <c:forEach var="p" items="${patients}">
                <tr>
                        <%--                <form action="/controller" method="post">--%>
                    <td>${p.name}</td>
                    <td>${p.birth}</td>
                    <td>${p.diagnosis}</td>
                    <td><input type="radio" name="patient" value="${p.id}"/></td>
                </tr>
            </c:forEach>
            <input type="hidden" name="command" value="<%=CommandList.CHOOSE_PATIENT%>"/>
            <input type="submit"/>
        </form>
        </tbody>
    </table>
</form>
</body>
</html>