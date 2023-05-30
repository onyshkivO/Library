<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div id="registration">
    <h3 class="text-center text-white pt-5">Login form</h3>
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">

                <div id="login-box" class="col-md-12">

                    <form id="login-form" class="form" action="controller?action=changePassword" method="post">
                        <h3 class="text-center text-info text-black">Change password:</h3>
                        <c:if test="${bad_old_password == true}">
                            <p class="text-danger lh-2 ms-1">Incorrect old password</p>
                        </c:if>
                        <c:if test="${incorrect_password == true}">
                            <p class="text-danger lh-2 ms-1">Incorrect new password</p>
                        </c:if>
                        <div class="form-group">
                            <label for="passwordOld" class="text-info text-black">Old password:</label><br>
                            <input type="password" name="password_old" id="passwordOld" class="form-control"
                                   value="${old}" required>
                        </div>
                        <div class="form-group">
                            <label for="passwordNew" class="text-info text-black">new password:</label><br>
                            <input type="password" name="password_new" id="passwordNew" class="form-control"
                                   value="${newPass}" required>
                        </div>
                        <div class="d-grid gap-3 col-6 mt-3 mx-auto">
                            <input type="submit" name="submit" class="btn btn-outline-primary" value="Submit">
                            <a class="btn btn-outline-primary" href="edit_profile.jsp" role="button">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
