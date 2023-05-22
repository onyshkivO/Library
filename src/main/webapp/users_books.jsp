<%--
  Created by IntelliJ IDEA.
  User: prost
  Date: 4/11/2023
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

    <style>
        <%@include file="style.css" %>
    </style>

</head>
<body>
<%@ include file="header.jsp" %>
<%--<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">--%>
<%--    <a class="btn btn-outline-primary" href="user_profile.jsp" role="button">My Profile</a>--%>
<%--    <a class="btn btn-outline-primary" href="controller?action=getOrders" role="button">Orders</a>--%>
<%--    <a class="btn btn-outline-primary active" href="controller?action=getUsersBook" role="button">Users books</a>--%>
<%--</div>--%>
<div class="container">


    <c:forEach var="subscription" items="${users_books.keySet()}">
        <div class="row order">
            <div class="card  my-2 col align-self-center"
                 style="max-width: 900px; padding-right: 0px; padding-left: 0px">
                <a class="btn btn-light text-start" data-bs-toggle="collapse" href="#${subscription.login}"
                   role="button"
                   aria-expanded="false" aria-controls="collapseExample">
                    <h5>${subscription.firstName} ${subscription.lastName}</h5>
                </a>
                <div class="card-body collapse" id="${subscription.login}">
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
                            <c:forEach var="active_book" items="${users_books.get(subscription)}">
                                <tr>
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
                                    <td>  <a class="btn btn-outline-primary active" href="controller?action=giveBookBack&active_book_id=${active_book.activeBookId}" role="button">Return</a></td>


                                </tr>
                            </c:forEach>


                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </c:forEach>


</div>
</body>
</html>


