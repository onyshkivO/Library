<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<%@ include file="header.jsp" %>

<div id="registration">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="controller?action=signup" method="post">
                        <input type="hidden" name="role" id="role" class="form-control" value="reader">
                        <h3 class="text-center text-info text-black">Registration:</h3>
                        <div class="form-group">
                            <label for="login" class="text-info text-black">Login:</label><br>
                            <input type="text" name="login" id="login" class="form-control" value="${login}" required>

                            <c:if test="${incorrect_login == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect login</p>
                            </c:if>

                            <c:if test="${already_exist_login == true}">
                                <p class="text-danger lh-1 ms-1">User login already exist</p>
                            </c:if>

                        </div>
                        <div class="form-group">
                            <label for="email" class="text-info text-black">E-mail:</label><br>
                            <input type="email" name="email" id="email" class="form-control" value="${email}" required>

                            <c:if test="${incorrect_Email == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect Email</p>
                            </c:if>

                        </div>
                        <div class="form-group">
                            <label for="first_name" class="text-info text-black">First name:</label><br>
                            <input type="text" name="first_name" id="first_name" class="form-control"
                                   value="${first_name}" required>
                            <c:if test="${incorrect_firstName == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect lirst Name</p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="last_name" class="text-info text-black">Last name:</label><br>
                            <input type="text" name="last_name" id="last_name" class="form-control" value="${last_name}"
                                   required>
                            <c:if test="${incorrect_lastName == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect last name</p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="text-info text-black">Phone:</label><br>
                            <input type="tel" name="phone" id="phone" class="form-control" value="${phone}">
                            <c:if test="${incorrect_phone == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect phone</p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info text-black">Password:</label><br>
                            <input type="password" name="password" id="password" class="form-control"
                                   value="${password}" required>
                            <c:if test="${incorrect_password == true}">
                                <p class="text-danger lh-1 ms-1">Incorrect password, password should be at list 6
                                    character</p>
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



