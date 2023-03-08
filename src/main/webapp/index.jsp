 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<h1>Hello there</h1>
<form action="sign/in" method="post">
    <input type="text" name="login" /><br>
    <input type="submit" value="Go!" /><br>
</form>
<a href="registration.jsp">Register</a>
    <c:if test="${not empty user}">
        <br/>
        ${sessionScope.user}
    </c:if>

</body>
</html>