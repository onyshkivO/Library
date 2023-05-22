<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Librarians</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%--<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">--%>
<%--    <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">My Profile</a>--%>
<%--    <a class="btn btn-outline-primary active" href="controller?action=getLibrarians" role="button">Librarians</a>--%>
<%--    <a class="btn btn-outline-primary" href="controller?action=getReaders" role="button">Users</a>--%>
<%--</div>--%>

<div class="container">
    <div class="d-grid gap-2">
        <a href="register_librarian.jsp" class="btn btn-primary mt-3 ms-auto me-5 d-flex " >Add Librarian</a>
    </div>

    <div class="row d-flex justify-content-center">
        <c:forEach var="user" items="${users}">
        <div class=" card col-lg-5 mx-3 my-3">
            <h5 class="card-header">${user.login}</h5>
            <div class="card-body">
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
                <a href="controller?action=deleteUser&login=${user.login}" class="btn btn-primary">Delete</a>
            </div>
        </div>
        </c:forEach>
    </div>

</div>
</body>
</html>
