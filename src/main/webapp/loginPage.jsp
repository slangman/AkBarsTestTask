<%--
  Created by IntelliJ IDEA.
  User: AttenCHUN
  Date: 25.07.2018
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Sing In</h2>
<form action="/j_spring_security_check" method="post">
    <input type="text" placeholder="Email" name="j_username">
    <input type="password" placeholder="Password" name="j_password">
    <button type="submit">Log in</button>
</form>
<a href="${pageContext.request.contextPath}/signup">Sign Up</a>
</body>
</html>
