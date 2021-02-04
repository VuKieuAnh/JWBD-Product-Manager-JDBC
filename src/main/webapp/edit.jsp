<%--
  Created by IntelliJ IDEA.
  User: vuanh
  Date: 2/4/21
  Time: 09:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Edit Product</h1>
<form method="post">
    <input type="text" name="name" value="${p.getName()}"/>
    <input type="text" name="description"value="${p.getDescription()}"/>
    <button type="submit">Edit</button>
</form>
</body>
</html>
