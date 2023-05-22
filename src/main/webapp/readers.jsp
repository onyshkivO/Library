<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Readers</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%--<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">--%>
<%--    <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">My Profile</a>--%>
<%--    <a class="btn btn-outline-primary" href="controller?action=getLibrarians" role="button">Librarians</a>--%>
<%--    <a class="btn btn-outline-primary active" href=""controller?action=getReaders" role="button">Users</a>--%>
<%--</div>--%>

<div class="container">
    <div class="row d-flex justify-content-center">
        <c:forEach var="user" items="${users}">
        <div class=" card col-lg-5 mx-3 my-3">
            <h5 class="card-header"><a href="controller?action=userInfo&login=${user.login}">${user.login}</a></h5>
            <c class="card-body">
                <div class=" mb-3 ">
                    <h5 class="card-title">Full name</h5>
                    <h6 class="card-title">${user.firstName} ${user.lastName}</h6>
                </div>
                <div class=" mb-3 ">
                    <h5 class="card-title">Email</h5>
                    <h6 class="card-title">${user.email}</h6>
                </div>
                <div class=" mb-3 ">
                    <h5 class="card-title">Phone</h5>
                    <h6 class="card-title">${user.phone}</h6>
                </div>
                <c:choose>
                <c:when test="${user.userStatus.userStatusId == 1}">
                <a href="controller?action=changeUserStatus&login=${user.login}" class="btn btn-primary">Block</a>
                </c:when>
                <c:otherwise>
                <a href="controller?action=changeUserStatus&login=${user.login}" class="btn btn-primary">Unblock</a>
                </c:otherwise>
                </c:choose>
        </div>
        </c:forEach>
    </div>

</div>

</div>
</body>
</html>
