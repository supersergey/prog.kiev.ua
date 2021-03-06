<%@ page import="Anketa.TestServlet" %>
<%@ page import="Anketa.SurveyStatistics" %>
<%@ page import="java.util.Map" %>
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
    <title>Вы указали такие данные:</title>
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

<hr width="100%">

<h1>Статистика анкетирования</h1>
<h2>Всего опрошено участников: </h2><%=SurveyStatistics.getInstance().getNumberOfEntries()%>
<h2>Статистика имен:</h2>
<%for(Map.Entry<String, Integer> e : SurveyStatistics.getInstance().getNamesStatistics().entrySet()) {%>
Имя: <%=e.getKey()%>, количество: <%=e.getValue()%><br>
<%}%>
<h2>Статистика напитков:</h2>
<%for(Map.Entry<String, Integer> e : SurveyStatistics.getInstance().getDrinksStatistics().entrySet()) {%>
Напиток: <%=e.getKey()%>, количество: <%=e.getValue()%><br>
<%}%>
<h2>Статистика музыки:</h2>
<%for(Map.Entry<String, Integer> e : SurveyStatistics.getInstance().getMusicsStatistics().entrySet()) {%>
Музыка: <%=e.getKey()%>, количество: <%=e.getValue()%><br>
<%}%>
<h2>Статистика возрастов:</h2>
<%for(Map.Entry<String, Integer> e : SurveyStatistics.getInstance().getAgesStatistics().entrySet()) {%>
Возраст: <%=e.getKey()%>, количество: <%=e.getValue()%><br>
<%}%>

<br>
<br>
<a href="form.html">Вернуться к форме</a>

</body>
</html>
