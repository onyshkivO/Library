<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User info</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
    <link href="style.css" rel="stylesheet">
</head>

<body>
<%@ include file="header.jsp" %>
<%--<div class="btn-group ms-5 ps-5" role="group" aria-label="First group">--%>
<%--    <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">My Profile</a>--%>
<%--    <c:if test="${sessionScope.user_role == 2}">--%>
<%--        <a class="btn btn-outline-primary" href="controller?action=getOrders" role="button">Orders</a>--%>
<%--        <a class="btn btn-outline-primary" href="controller?action=getUsersBook" role="button">Users books</a>--%>
<%--    </c:if>--%>
<%--    <c:if test="${sessionScope.user_role == 3}">--%>
<%--        <a class="btn btn-outline-primary" href="controller?action=getLibrarians" role="button">Librarians</a>--%>
<%--        <a class="btn btn-outline-primary" href="controller?action=getReaders" role="button">Users</a>--%>
<%--    </c:if>--%>


<%--</div>--%>

<div class=" d-flex justify-content-center" >
    <div class=" mt-3 ms-5 me-0 ps-5" style="width: 500px;">
        <c:if test="${user.userStatus.userStatusId==2}"><h2 class="text-danger"> This account is blocked!</h2></c:if>
        <p class="fs-4 fw-semibold">Login:</p>
        <p class="fs-5">${requestScope.user.login}</p>
        <p class="fs-4 fw-semibold">Email:</p>
        <p class="fs-5">${requestScope.user.email}</p>
        <p class="fs-4 fw-semibold">First name:</p>
        <p class="fs-5">${requestScope.user.firstName}</p>
        <p class="fs-4 fw-semibold">Last Name:</p>
        <p class="fs-5">${requestScope.user.lastName}</p>


        <c:if test="${requestScope.user.phone!=null}">
            <p class="fs-4 fw-semibold">Phone:</p>
            <p class="fs-5">${requestScope.user.phone}</p>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${user_books.isEmpty() != true}">
            <div class="  ms-0 mt-5 bg-secondary-subtle border border-dark-subtle rounded-3 " style="width: 807px"   >
                <h2 class="text-center mt-2 mb-3 ">User's books</h2>
                <hr>
                <div class="card-body">
                    <div class=" mb-3 ">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Book</th>
                                <th scope="col">Date of return</th>
                                <th scope="col">fine</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="active_book" items="${user_books}">
                                <tr  <c:if test="${active_book.subscriptionStatus.subscriptionStatusID==3}"> style="background-color: #fca2a2" </c:if>  "   >
                                <td class="w-50">${active_book.book.name}</td>
                                <td class="w-25">${active_book.endDate}</td>
                                <c:choose>
                                    <c:when test="${active_book.subscriptionStatus.subscriptionStatusID==3}">
                                        <td class="w-25">${active_book.fine}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="w-25"></td>
                                    </c:otherwise>
                                </c:choose>


                                </tr>
                            </c:forEach>


                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <h2 class="mx-5 mt-3 ">User doesn't have active book now</h2>
        </c:otherwise>
    </c:choose>


</div>

</body>
</html>

