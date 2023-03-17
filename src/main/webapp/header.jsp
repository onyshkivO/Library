<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link href="header.css" rel="stylesheet">
</head>
<body>
<header class="site-header">
    <div class="site-identity">
        <h1><a href="#">Site Name</a></h1>
    </div>
    <nav class="site-navigation">
        <ul class="nav">
            <li><a href="#">About</a></li>
            <li><a href="#">News</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </nav>
    <c:if test="${sessionScope.exist_user == true} ">
        <ul class="nav2">
            <li><h3>Hello ${sessionScope.user.firstName} ${sessionScope.user.lastName} </h3></li>
            <li><a href="#" ><button  class="button" role="button">Sign Out</button></a></li>
        </ul>
    </c:if>


    <ul class="nav2">
        <li><a href="registration.jsp" > <button  class="button" role="button"  >Sign Up</button></a></li>
        <li><a href="login.jsp" ><button  class="button" role="button">Sign In</button></a></li>
    </ul>


</header>
</body>
</html>