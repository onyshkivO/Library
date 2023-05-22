<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
    <link href="header2.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid " class="mx-auto" style="width: 1750px;">

        <a class="navbar-brand" href="/Library">Library</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/Library">Home</a>
                </li>
                <c:if test="${sessionScope.user_role!=null }">
                <li class="nav-item">
                    <a class="nav-link" href="controller?action=bookPage&page=1">Books</a>
                </li>
                </c:if>
                <c:if test="${sessionScope.user_role == 2}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=getOrders" role="button">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=getUsersBook" role="button">Users books</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user_role == 1}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=userBooks" role="button">My Books</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user_role == 3}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=getLibrarians" role="button">Librarians</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=getReaders" role="button">Users</a>
                    </li>
                </c:if>

            </ul>


            <c:choose>
                <c:when test="${sessionScope.exist_user == true}">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item me-3">
                            <a class="nav-link"
                               href="user_profile.jsp">Hello ${sessionScope.user.firstName} ${sessionScope.user.lastName}</a>
                        </li>
                        <li class="mar"><a class="btn btn-primary" href="controller?action=signout" role="button">Sign
                            Out</a>
                        </li>
                    </ul>
                </c:when>

                <c:otherwise>
                    <ul class="navbar-nav ms-auto">
                        <li><a class="btn btn-primary me-3" href="registration.jsp" role="button">Sign Up</a></li>
                        <li><a class="btn btn-primary" href="login.jsp" role="button">Sign In</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div>

    </div>
</nav>
</body>
</html>