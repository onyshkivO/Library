<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users Orders</title>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="btn-group mx-5 ps-5" role="group" aria-label="First group">
<a class="btn btn-outline-primary" href="user_info.jsp" role="button">My Profile</a>
<a class="btn btn-outline-primary active" href="controller?action=getOrders" role="button">Orders</a>
<a class="btn btn-outline-primary" href="controller?action=getUsersBook" role="button">Users books</a>
</div>
<c:if test="${wronge==true}">
    <h2>Something went wronge</h2>
</c:if>

<c:if test="${bad_date_format==true}">
    <h2>Something went wronge(date)</h2>
</c:if>


<div class="container">
    <c:forEach var="subscription" items="${orders}">
        <div class="row order">
            <div class="card  my-2 col align-self-center " style="max-width: 900px">
                <h5 class="card-header">Order ${subscription.activeBookId}</h5>
                <div class="card-body">
                    <div class=" mb-3 ">
                        <h5 class="card-title">User</h5>
                        <h6 class="card-title">${subscription.user.firstName} ${subscription.user.lastName}</h6>
                    </div>
                    <div class=" mb-3 ">
                        <h5 class="card-title">Book</h5>
                        <h6 class="card-title">isbn: ${subscription.book.isbn}</h6>
                        <h6 class="card-title">name: ${subscription.book.name}</h6>
                    </div>
<%--                    <form id="login-form" class="form" action="controller?action=givebook&id=${subscription.activeBookId}" method="post">--%>
                    <form id="login-form" class="form" action="controller" >
                        <input type="hidden" name="action" value="giveBook">
                        <input type="hidden" name="id" value="${subscription.activeBookId}">
                        <label for="end_date"><h5 class="card-title">End date</h5></label>
                        <div class="input-group mb-3 w-25">

                            <input type="date" id="end_date" name="end_date" class="form-control"
                                   value="${requestScope.date}" min="${requestScope.date}">
                        </div>
                        <label for="fine"><h5 class="card-title">Fine if date go wronge</h5></label>
                        <div class="input-group mb-3 w-25">
                            <span class="input-group-text">UAH</span>
                            <input type="number" class="form-control" id="fine" name="fine" min="0">
                            <span class="input-group-text">.00</span>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Give">
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>

</div>
</body>
</html>
