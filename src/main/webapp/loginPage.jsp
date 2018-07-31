<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div>
    <c:set var="signUpSuccess" value="${requestScope.get('signUpSuccess')}"/>
    <c:if test="${signUpSuccess!=null}">
        ${signUpSuccess}
    </c:if>
</div>
</body>
</html>
