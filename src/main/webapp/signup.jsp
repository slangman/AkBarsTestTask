<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<div>
    Note: Your password must be from 6 to 20 characters containing both letters and numbers.
</div>
<div>
    <h2>Sign Up</h2>
    <form action="${pageContext.request.contextPath}/signup" method="post">
        <input type="text" name="email" placeholder="Email"><br>
        <input type="password" name="password" placeholder="Password"><br>
        <input type="password" name="repeatPassword" placeholder="Repeat password">
        <button type="submit">OK</button>
    </form>
</div>
<div>
    Message box<br>
    <c:set var="passwordErrorMessage" value="${requestScope.get('passwordError')}"/>
    <c:if test="${passwordErrorMessage!=null}">${passwordErrorMessage}</c:if>
    <c:set var="emailErrorMessage" value="${requestScope.get('emailError')}"/>
    <c:if test="${emailErrorMessage!=null}">${emailErrorMessage}</c:if>
</div>
</body>
</html>
