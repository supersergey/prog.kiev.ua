<%@ page import="TestServletPackage.TestServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 05.08.2015
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результаты анкетирования</title>
</head>
<body>
<h1>Результаты анкетирования</h1><br>

<h2>Ваше имя: <%=TestServlet.getCurrentSurvey().getName()%></h2>

<h2>Напиток: <%=TestServlet.getCurrentSurvey().getDrinks()%></h2>

<h2>Музыка: <% for (String music : TestServlet.getCurrentSurvey().getMusics()) {
    %>
    <%=music%>
    <%
        }
    %>
    </h2>

<h2>Возраст: <%=TestServlet.getCurrentSurvey().getAges()%>
</h2>

</body>
</html>
