<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>

<div id="signin">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center ">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form mt-5" action="controller?action=signin" method="post">
                        <h3 class="text-center text-info  text-black">Login</h3>
                        <c:if test="${incorrect_user == true}">
                        <p class="text-danger lh-2 ms-1">Incorrect user login</p>
                        </c:if>


                        <c:if test="${password_does_not_match == true}">
                            <p class="text-danger lh-2 ms-1">Wronge password</p>
                        </c:if>


                        <div class="form-group ">
                            <label for="login" class="text-info text-black" >Login:</label><br>
                            <input type="text" name="login" id="login" class="form-control" value="${login}" required>
                            <c:if test="${incorrect_login == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect login</p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info text-black">Password:</label><br>
                            <input type="password" name="password" id="password" class="form-control" required>
                            <c:if test="${incorrect_password == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect password, password should be at list 4 character</p>
                            </c:if>
                        </div>
                        <div class="form-group">
                           <input type="submit" name="submit" class="btn btn-outline-primary" value="submit">

                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
