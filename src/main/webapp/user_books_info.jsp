<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>user books</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%--<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">--%>
<%--    <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">My Profile</a>--%>
<%--    <a class="btn btn-outline-primary active" href="controller?action=userBooks" role="button">My Books</a>--%>
<%--</div>--%>
<div class="container my-4">
    <c:choose>
        <c:when test="${requestScope.user_books.isEmpty()==true}">
            <h3 class="text-center"   > You don`t have active books</h3>
        </c:when>
        <c:otherwise>
            <h3> Your books</h3>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Book</th>
                    <th scope="col">Date of return</th>
                    <th scope="col">fine</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="active_book" items="${requestScope.user_books}">
                    <tr  <c:if test="${active_book.subscriptionStatus.subscriptionStatusID==3}"> style="background-color: #fca2a2" </c:if>  "   >
                            <%--                <th scope="row">1</th>--%>
                        <td>${active_book.book.name}</td>
                        <td>${active_book.endDate}</td>
                        <c:choose>
                            <c:when test="${active_book.subscriptionStatus.subscriptionStatusID==3}">
                                <td>${active_book.fine}</td>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>


                    </tr>
                    <%--            <p class="fs-4 fw-semibold">Book name: ${active_book.book.name} Date of return: ${active_book.endDate}--%>
                    <%--                <c:if test="${active_book.subscriptionStatus.subscriptionStatusID==3}"> You do not return book in certain period? so you need to pay a fine :${active_book.fine}</c:if> </p>--%>
                </c:forEach>


                </tbody>
            </table>
        </c:otherwise>
    </c:choose>



</div>


</body>
</html>
