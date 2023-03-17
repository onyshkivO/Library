<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>

<c:if test="${isWrong ==true}">
    <div>
        <h3 style="color: red"> Something wend wronge</h3>
    </div>
</c:if>

<div id="registration">
    <h3 class="text-center text-white pt-5">Login form</h3>
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="controller?action=signup" method="post">
                        <h3 class="text-center text-info">Registration:</h3>
                        <div class="form-group">
                            <label for="login" class="text-info">Login:</label><br>
                            <input type="text" name="login" id="login" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="email" class="text-info">E-mail:</label><br>
                            <input type="email" name="email" id="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="first_name" class="text-info">First name:</label><br>
                            <input type="text" name="first_name" id="first_name" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="last_name" class="text-info">Last name:</label><br>
                            <input type="text" name="last_name" id="last_name" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="text-info">Phone:</label><br>
                            <input type="tel" name="phone" id="phone" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info">Password:</label><br>
                            <input type="password" name="password" id="password" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <!--                            <label for="remember-me" class="text-info"><span>Remember me</span> <span><input id="remember-me" name="remember-me" type="checkbox"></span></label><br>-->
                            <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                        </div>
                        <!--                        <div id="register-link" class="text-right">-->
                        <!--                            <a href="#" class="text-info">Register here</a>-->
                        <!--                        </div>-->
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>



