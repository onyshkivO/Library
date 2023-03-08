 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Good</title>
</head>
<body>
<h1>This is good page</h1>
 <c:if test="${not empty user}">
        <br/>
        ${sessionScope.user}
    </c:if>
</body>
</html>