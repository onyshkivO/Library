<html>
<head>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="px-4 py-5 my-5 text-center">
    <img class="d-block mx-auto mb-4" src="img/images.png" alt="">
    <h1 class="display-5 fw-bold text-body-emphasis">Welcome to our Library!</h1>
    <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">Discover, read, and enjoy books from our wide range of collection</p>
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">

            <c:choose>
                <c:when test="${sessionScope.user_role!=null}">
                    <a class="btn btn-primary btn-lg px-4 gap-3" href="controller?action=bookPage&page=1">Books</a>
                </c:when>
                <c:otherwise>

                    <a class="btn btn-primary btn-lg px-4 gap-3" href="registration.jsp" role="button">Sign Up</a>
                    <a class="btn btn-primary btn-lg px-4 gap-3" href="login.jsp" role="button">Sign In</a>

                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>