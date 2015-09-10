<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 10.09.2015
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
  <c:out value="${dataIsReady}" />
  <c:if test="${dataIsReady == 1}">
    Value 1;
  </c:if>
  <c:if test="${dataIsReady == 2}">
    Value 2;
  </c:if>
</body>
</html>
