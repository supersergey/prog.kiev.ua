<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Sergey Tolokunsky Graduation Project</title>

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
        <h3 class="text-muted">Sergey Tolokunsky Graduation Project</h3>
    </div>

    <div class="jumbotron">
        <h1>Welcome</h1>


        <form action="/search" method="get">
            <div class="form-group">
                <label for="phoneNumber">Student's phone number</label>
                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="phone">
            </div>
            <a href="/">Update search base if you have new data in spreadsheets (takes time).</a>
            <p><input type="submit"></p>
        </form>

    </div>

    <c:if test="${dataIsReady eq true}">
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
    </c:if>

    <footer class="footer">
        <p>(c) Sergey Tolokunsky 2015 // thanks to prog.kiev.ua</p>
    </footer>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
