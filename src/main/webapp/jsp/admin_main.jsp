<%@ page import="controller.command.CommandList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Main</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<input type="hidden" name="command" value="view">
<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Birth</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${patients}">
        <tr>
            <td>${p.name}</td>
            <td>${p.birth}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h2>Add patient</h2>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="<%=CommandList.ADD_PATIENT%>">
    <p>Name:</p>
    <label>
        <input type="text" name="name">
    </label><br>
    <p>Birth:</p>
    <label>
        <input type="date" name="birth">
    </label><br>
    <p>Doctor:</p>
    <label>
        <select name="doctor">
            <c:forEach items="${doctors}" var="doctor">
                <option value="${doctor.id}">${doctor.name} ${doctor.doctorSpecialisationName}</option>
            </c:forEach>
        </select>
    </label><br>
    <input type="submit" name="button">
</form>

<h2>Add doctor</h2>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="<%=CommandList.ADD_DOCTOR%>">
    <p>Name:</p>
    <label>
        <input type="text" name="name">
    </label><br>
    <p>Login:</p>
    <label>
        <input type="text" name="login">
    </label><br>
    <p>Password:</p>
    <label>
        <input type="password" name="password">
    </label><br>
    <p>Specialisation:</p>
    <label>
        <select name="specialisation">
            <c:forEach items="${specialisations}" var="spec">
                <option value="${spec.name()}">${spec.name()}</option>
            </c:forEach>
        </select>
    </label><br>
    <input type="submit" name="button">
</form>
</body>
</html>
