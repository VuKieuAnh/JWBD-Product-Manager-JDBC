<%--
  Created by IntelliJ IDEA.
  User: vuanh
  Date: 2/4/21
  Time: 08:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Danh sach sp</h1>
<table>
    <tr>
        <td>name</td>
        <td>description</td>
    </tr>
    <c:forEach items="${list}" var="p">
        <tr>
            <td>${p.getName()}</td>
            <td>${p.getDescription()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
