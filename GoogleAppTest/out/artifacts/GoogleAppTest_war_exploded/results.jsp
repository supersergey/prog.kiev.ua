<%--
  Created by IntelliJ IDEA.
  User: st
  Date: 03.09.2015
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<html>
<head>
    <title>Google Sheets</title>
</head>
<body>
<table class="table table-striped">
  <thead>
  <tr>
    <td><b>Name</b></td>
    <td><b>Phone</b></td>
    <td><b>Payment</b></td>
    <td><b>Comment</b></td>
    <td><b>Course name</b></td>
    <td><b>Start Date</b></td>
    <td><b>Teacher</b></td>
    <td><b>Location</b></td>
  </tr>
  </thead>

  <c:forEach items="${students}"  var="s">
      <tr>
        <td><c:out value="${s.name}"/></td>
        <td><c:out value="${s.phone}"/></td>
        <td><c:out value="${s.payment}"/></td>
        <td><c:out value="${s.comment}"/></td>
        <td><c:out value="${s.courseName}"/></td>
        <td><c:out value="${s.startDate}"/></td>
        <td><c:out value="${s.teacherName}"/></td>
        <td><c:out value="${s.location}"/></td>
      </tr>
    </c:forEach>
  </table>
  <a href="/">Return to the main page.</a>
</body>
</html>
