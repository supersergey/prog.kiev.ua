<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/SpringMVC_war_exploded/add"
          method="post">
        <div class="form-group"><h3>New adv.</h3></div>
        <div class="form-group"><input type="text" class="form-control" name="name" placeholder="Name"></div>
        <div class="form-group"><input type="text" class="form-control" name="shortDesc"
                                       placeholder="Short description"></div>
        <div class="form-group"><input type="text" class="form-control" name="longDesc" placeholder="Long description">
        </div>
        <div class="form-group"><input type="text" class="form-control" name="phone" placeholder="Phone"></div>
        <div class="form-group"><input type="text" class="form-control" name="price" placeholder="Price"></div>
        <div class="form-group">Photo: <input type="file" name="photo"></div>
        <div class="form-group"><input type="submit" class="btn btn-primary" value="Add"></div>
    </form>
</div>

<div class="container">
    <form role="form" class="form-horizontal" action="/SpringMVC_war_exploded/addXML" method="post"
          enctype="multipart/form-data">
        <div class="form-group">Select XML to upload:</div>
        <div class="form-group"><input type="file" name="xmlFile" id="xmlFile"></div>
        <div class="form-group"><input type="submit" value="Upload XML" name="submit" class="btn btn-primary"></div>
    </form>

</div>
</body>
</html>