<%--
  Created by IntelliJ IDEA.
  User: st
  Date: 03.09.2015
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Google Sheets</title>
</head>
<body>
  <table>
    <c:forEach items="${list}"  var="s">
      <tr>
        <td><c:out value="${s.name}"/></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
