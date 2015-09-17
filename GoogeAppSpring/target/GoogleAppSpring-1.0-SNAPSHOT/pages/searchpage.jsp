<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<c:set var="title" scope="request" value="${\"Дипломный проект Сергея Толокунского\"}"/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title><c:out value="${title}"/></title>

    <!-- Bootstrap core CSS -->
    <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="jumbotron-narrow.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <div class="header clearfix">
        <h3 class="text-muted"><a href="/"><c:out value="${title}"/></a></h3>
    </div>


    <div class="jumbotron">
        <%--<img src="images/keng.png" alt="Greetings from Australia!" hspace="5" vspace="5"--%>
             <%--style="height: 160px; top: -20px; float: right; display: inline; position: relative">--%>
            <img src="../../images/koalabears.png" alt="Greetings from Australia!" hspace="0" vspace="0" style="width: 248px; height: 373px; top: -105px; right: -160px; float: right; display: inline; position: relative">

        <h1>Поиск видео</h1>


        <form action="/search" method="get">
            <div id="custom-search-input" style="width: 60%">
                <h2>Введите свой мобильный:</h2>

                <div class="input-group col-md-12">
                    <input type="text" class="  search-query form-control" name="phoneNumber"
                           placeholder="050-234-05-06"/>
                                <span class="input-group-btn">
                                    <button class="btn btn-danger" type="submit">
                                        <span class=" glyphicon glyphicon-search"></span>
                                    </button>
                                </span>

                </div>

            </div>
        </form>
    </div>

    <div>
        <c:if test="${searchResult == 'dataIsFound'}">
            <c:set var="showSendButton" scope="request" value="no"/>
            <table class="table table-striped">
                <thead>
                <tr>
                    <td><b>Название курса</b></td>
                    <td><b>Ссылка на видео</b></td>

                </tr>
                </thead>
                <c:forEach var="entry" items="${studentsList}">
                    <tr>
                        <td><c:out value="${entry.courseName}"/></td>
                        <td>
                            <c:if test="${entry.url == ''}">
                                Для получения видео оплатите курс полностью.
                            </c:if>
                            <c:if test="${entry.url != ''}">
                                <a href="${entry.url}" target="_blank">Видео</a></td>
                                <c:set var="showSendButton" scope="request" value="yes"/>
                            </c:if>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${showSendButton == 'yes'}">
            <div style="display: block; width: 50%">
                <form action="/sendMail" method="post">
                    <div class="input-group">
                        <input type="text" name="emailAddress" class="form-control" placeholder="mr.beans@prog.kiev.ua" style="min-width: 150px;">
                        <input type="hidden" name="mailId" value="${mailId}">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="submit">Получить ссылки на емейл</button>
                        </span>
                    </div>
                </form>
            </div>
            </c:if>
        </c:if>
        <c:if test="${searchResult == 'userNotFound'}">
            <h3>Студент с таким номером телефона на найден!</h3>
        </c:if>
    </div>
    <footer class="footer">
        <p>(c) <a href="https://www.linkedin.com/profile/view?id=AAMAAABUdNMBu3nOOQHFz1f3-pv_qto3WgZP-Hs&trk=hp-identity-name" target="_blank">Sergey Tolokunsky</a> 2015 // спасибо <a href="http://prog.kiev.ua" target="_blank">prog.kiev.ua</a></p>
        <p><a href="pages/about.html">About this project</a>.</p>
    </footer>


</div>
<!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>