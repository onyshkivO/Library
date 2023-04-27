
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="header.jsp"%>
<div id="registration">
  <h3 class="text-center text-white pt-5">Login form</h3>
  <div class="container">
    <div id="login-row" class="row justify-content-center align-items-center">
      <div id="login-column" class="col-md-6">
        <div id="login-box" class="col-md-12">
          <form id="login-form" class="form" action="controller?action=editProfile" method="post">
            <h3 class="text-center text-info text-black">Edit profile:</h3>
            <div class="form-group">
              <label for="login" class="text-info text-black">Login:</label><br>
              <input type="text" name="login" id="login" class="form-control" required value="${sessionScope.user.login}">
            </div>
            <div class="form-group">
              <label for="email" class="text-info text-black">E-mail:</label><br>
              <input type="email" name="email" id="email" class="form-control" required  value="${sessionScope.user.email}">
            </div>
             <div class="form-group">
              <label for="first_name" class="text-info text-black">First name:</label><br>
              <input type="text" name="first_name" id="first_name" class="form-control" value="${sessionScope.user.firstName}" required>
            </div>
            <div class="form-group">
              <label for="last_name" class="text-info text-black">Last name:</label><br>
              <input type="text" name="last_name" id="last_name" class="form-control" value="${sessionScope.user.lastName}" required>
            </div>
            <div class="form-group">
              <label for="phone" class="text-info text-black">Phone:</label><br>
              <input type="tel" name="phone" id="phone" class="form-control" value="${sessionScope.user.phone}">
            </div>
            <div class="d-grid gap-3 col-6 mt-3 mx-auto">
              <input type="submit" name="submit" class="btn btn-outline-primary" value="Submit">
              <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">Cancel</a>
              <a class="btn btn-outline-primary" href="change_password.jsp" role="button">Change password</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
