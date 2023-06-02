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
<div class="container">
    <c:if test="${orders.size()==0}"><h2 class="d-flex justify-content-center mt-5">There are not new orders</h2></c:if>
    <c:forEach var="subscription" items="${orders}">
        <div class="row order">
            <div class="card  my-2 col align-self-center " style="max-width: 900px">
                <h5 class="card-header">Order ${subscription.activeBookId}</h5>
                <div class="card-body">
                    <div class=" mb-3 ">
                        <h5 class="card-title">User</h5>
                        <h6 class="card-title"><a
                                href="controller?action=userInfo&login=${subscription.user.login}">${subscription.user.firstName} ${subscription.user.lastName}</a>
                        </h6>
                    </div>
                    <div class=" mb-3 ">
                        <h5 class="card-title">Book</h5>
                        <h6 class="card-title">isbn: ${subscription.book.isbn}</h6>
                        <h6 class="card-title">name: ${subscription.book.name}</h6>
                    </div>
                    <form id="login-form" class="form" action="controller" method="post">
                        <input type="hidden" name="action" value="giveBook">
                        <input type="hidden" name="id" value="${subscription.activeBookId}">
                        <label for="end_date"><h5 class="card-title">End date</h5></label>
                        <div class="input-group mb-3 w-25">

                            <input type="date" id="end_date" name="end_date" class="form-control"
                                   value="${requestScope.date}" min="${requestScope.date}" required>
                        </div>
                        <label for="fine"><h5 class="card-title">Possible fine</h5></label>
                        <div class="input-group mb-3 w-25">
                            <span class="input-group-text">UAH</span>
                            <input type="number" class="form-control" id="fine" name="fine" min="0" required>
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
