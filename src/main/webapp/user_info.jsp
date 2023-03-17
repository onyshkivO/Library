<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User info</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Login</h2>
<h3>${sessionScope.user.login}</h3>
<h2>Email</h2>
<h3>${sessionScope.user.email}</h3>
<h2>First name</h2>
<h3>${sessionScope.user.firstName}</h3>
<h2>Last Name</h2>
<h3>${sessionScope.user.lastName}</h3>
<h2>Phone</h2>
<c:if test="${sessionScope.user.phone!=null}">
<h3>${sessionScope.user.phone}</h3>
</c:if>
<c:if test="${sessionScope.user.phone==null||sessionScope.user.phone.isEmpty()}">
    <h3>Ведіть ваш номер, щоб його бачити</h3>
</c:if>
</body>
</html>
