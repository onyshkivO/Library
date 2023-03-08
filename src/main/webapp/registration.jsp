 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<form action="sign/in" method="post">
    <label for="login">Login</label><br>
    <input type="text" name="login" /><br>
    <br>
    <label for="email">Email</label><br>
    <input type="email" name="email" /><br>
    <br>
    <label for="password">Password</label><br>
    <input type="password" name="password" /><br>
    <br>
    <label for="firstName">First Name</label><br>
    <input type="text" name="firstName" /><br>
    <br>
    <label for="lastName">Last Name</label><br>
    <input type="text" name="lastName" /><br>
    <br>
    <label for="phone">Phone</label><br>
    <input type="text" name="phone" /><br>
    <br>
    <input type="submit" value="Go!" />
</form>

</body>
</html>