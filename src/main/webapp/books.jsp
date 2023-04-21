<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link href="style.css" rel="stylesheet">


</head>
<%--<body data-active="<%= request.getAttribute("isbn") %>">--%>
<body>
<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <c:choose>
            <c:when test="${books.isEmpty()==true}">
                <h2 class="my-4 d-flex justify-content-center">There are no available books</h2>
            </c:when>
            <c:otherwise>
                <h2 class="my-4 d-flex justify-content-center">Available books</h2>
                <c:forEach var="book" items="${books}">
                    <div class="card col-lg-4 mx-2 my-1" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title mb-3">${book.name}</h5>
                            <div class="d-flex justify-content-between " style="max-width: 280px">
                                <h6>Authors</h6>
                                <h6>Publication</h6>
                            </div>
                            <div class="d-flex justify-content-between " style="max-width: 280px; ">
                                <div style="max-width: 125px; ">
                                    <p>
                                        <c:forEach var="author" items="${book.authors}">
                                            ${author.name},
                                        </c:forEach>
                                    </p>
                                </div>
                                <div style="max-width: 125px; " class="ms-1 ">
                                    <p>${book.publication.name}</p>
                                </div>
                            </div>
                            <p class="card-text">${book.details}</p>
                            <c:if test="${sessionScope.exist_user == true&&sessionScope.user_role==1}">
                                <a href="controller?action=addBook&isbn=${book.isbn}" class="btn btn-primary">Add</a>
                            </c:if>

                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>



    </div>

</div>

<%--<c:forEach var="book" items="${books}">--%>
<%--    <div class="container bg-light my-4 h-25">${book.name}--%>
<%--        <a href="controller?action=addBook&isbn=${book.isbn}">--%>
<%--            <button class="button" role="button">add</button>--%>
<%--        </a>--%>
<%--        <br>--%>
<%--    </div>--%>
<%--</c:forEach>--%>
<script src="js/window.js"></script>
</body>
</html>
