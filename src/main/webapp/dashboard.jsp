<%--
  Created by IntelliJ IDEA.
  User: AttenCHUN
  Date: 25.07.2018
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Welcome to dashboard</h2>
Signed in as ${sessionScope.get('entered_email')}
<a href="${pageContext.request.contextPath}/profile">Edit profile</a>
</body>
</html>
