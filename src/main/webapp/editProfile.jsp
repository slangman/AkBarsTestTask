<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: AttenCHUN
  Date: 25.07.2018
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>
<h2>Edit profile</h2>
<c:set var="currentUser" value="${requestScope.get('currentUser')}"/>
<form action="${pageContext.request.contextPath}/editProfile" method="post">
    <label for="email">Email:</label>
    <input type="text" name="email" id="email" placeholder="Email" value="${currentUser.getEmail()}" readonly>
    <h3>Edit password</h3>
    <label for="newPassword">New password</label>
    <input type="password" name="newPassword" id="newPassword" placeholder="New password"><br>
    <label for="repeatNewPassword">Repeat new password</label>
    <input type="password" name="repeatNewPassword" id="repeatNewPassword" placeholder="Repeat new password"><br>
    <label for="fName">First name</label>
    <input type="text" name="fName" id="fName" placeholder="First name" value="${currentUser.getFName()}"><br>
    <label for="mName">Middle name</label>
    <input type="text" name="mName" id="mName" placeholder="Middle name" value="${currentUser.getMName()}"><br>
    <label for="lName">Last name</label>
    <input type="text" name="lName" id="lName" placeholder="Last name" value="${currentUser.getLName()}"><br>
    <%--<label for="mobileNumber">Mobile number</label>
    <input type="tel" id="mobileNumber" name="mobileNumber"
           placeholder="+7 123-456-7890"
           pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"--%>
    <label for="userPicURL">Userpic URL</label>
    <input type="url" name="userPicURL" id="userPicURL" placeholder="Userpic URL"
           value="${currentUser.getUserPicURL()}"><br>
    <button type="submit">Save</button>
</form>
<div>
    Message box<br>
    <c:set var="passwordErrors" value="${requestScope.get('passwordErrors')}"/>
    <c:if test="${passwordErrors!=null}">
        <c:forEach items="${passwordErrors}" var="passwordError">
            ${passwordError}<br>
        </c:forEach>
    </c:if>
    <c:set var="nameErrors" value="${requestScope.get('nameErrors')}"/>
    <c:if test="${nameErrors!=null}">
        <c:forEach items="${nameErrors}" var="nameError">
            ${nameError}<br>
        </c:forEach>
    </c:if>
    <c:set var="userPicErrors" value="${requestScope.get('userPicErrors')}"/>
    <c:if test="${userPicErrors!=null}">
        <c:forEach items="${userPicErrors}" var="userPicError">
            ${userPicError}<br>
        </c:forEach>
    </c:if>
    <c:set var="updateSuccessfull" value="${requestScope.get('updateSuccessfull')}"/>
    <c:if test="${updateSuccessfull!=null}">
        ${updateSuccessfull}
    </c:if>
</div>
</body>
</html>
