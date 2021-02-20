<%--
  Created by IntelliJ IDEA.
  User: vuanh
  Date: 2/20/21
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <input name="name"/>
    <input name="description"/>
    <select name="categories" multiple>
        <c:forEach var="c" items="${categories}">
            <option value="${c.getId()}">${c.getName()}</option>
        </c:forEach>
    </select>
    <button type="submit">Create</button>
</form>
</body>
</html>
