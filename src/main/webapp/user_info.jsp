<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User info</title>
    <link href="style.css" rel="stylesheet">
</head>

<body>
<%@ include file="header.jsp" %>
<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">
    <a class="btn btn-outline-primary active" href="user_info.jsp" role="button">My Profile</a>
    <c:if test="${sessionScope.user_role == 2}">
        <a class="btn btn-outline-primary" href="controller?action=getOrders" role="button">Orders</a>
        <a class="btn btn-outline-primary" href="controller?action=getUsersBook" role="button">Users books</a>
    </c:if>
    <c:if test="${sessionScope.user_role == 1}">
    <a class="btn btn-outline-primary" href="controller?action=userBooks" role="button">My Books</a>
    </c:if>
    <c:if test="${sessionScope.user_role == 3}">
        <a class="btn btn-outline-primary" href="controller?action=getLibrarians" role="button">Librarians</a>
        <a class="btn btn-outline-primary" href="#" role="button">Users</a>
    </c:if>


</div>
<div class="container-fluid mt-3 mx-5 ps-5"  style="width: 500px;">
    <p class="fs-4 fw-semibold">Login:</p>
    <p class="fs-5">${sessionScope.user.login}</p>
    <p class="fs-4 fw-semibold">Email:</p>
    <p class="fs-5">${sessionScope.user.email}</p>
    <p class="fs-4 fw-semibold">First name:</p>
    <p class="fs-5">${sessionScope.user.firstName}</p>
    <p class="fs-4 fw-semibold">Last Name:</p>
    <p class="fs-5">${sessionScope.user.lastName}</p>
    <p class="fs-4 fw-semibold">Phone:</p>

    <c:if test="${sessionScope.user.phone!=null}">
        <p class="fs-5">${sessionScope.user.phone}</p>
    </c:if>
    <c:if test="${sessionScope.user.phone==null||sessionScope.user.phone.isEmpty()}">
    <p class="fs-5">Ведіть ваш номер, щоб його бачити</p>
    </c:if>
    <div class="d-grid gap-2 col-6 mt-3">
    <a class="btn btn-outline-primary" href="edit_profile.jsp" role="button">Edit profile</a>
    </div>
</div>
</body>
</html>
