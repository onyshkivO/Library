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


    <form class="mt-4  d-flex justify-content-between" action="controller" method="get">
        <div class="ms-5 ps-5" style="max-width: 400px">
            <h5>Searching</h5>
            <div class="input-group me-5 pe-5 d-flex">
                <input type="text" class="form-control" name="name" value="${requestScope.name}">
                <select class="form-select" id="inputGroupSelect01" name="search_option">
                    <option value="book_name"
                            <c:if test="${requestScope.search_option == 'book_name'}">selected</c:if>><%-- todo хз, може ця перевірка не дуже равильна--%>
                        Book name
                    </option>
                    <option value="author_name"
                            <c:if test="${requestScope.search_option == 'author_name'}">selected</c:if>>Author name
                    </option>
                </select>
                <input type="hidden" class="form-control" name="action" value="bookPage">
            </div>
        </div>
        <button type="submit" class="btn btn-outline-primary mt-auto">Search</button>

        <div class=" me-5 pe-5 " style="min-width: 360px">
            <h5>Sorting</h5>
            <div class="input-group d-flex">
                <select class="form-select" id="sort_option" name="sort_option" style="min-width: 220px">
                    <option value="book_name" <c:if test="${requestScope.sort_option == 'book_name'}">selected</c:if>>
                        Book name
                    </option>
                    <option value="author_name"
                            <c:if test="${requestScope.sort_option == 'author_name'}">selected</c:if>>Author name
                    </option>
                    <option value="date_of_publication"
                            <c:if test="${requestScope.sort_option == 'date_of_publication'}">selected</c:if>>Date of
                        publication
                    </option>
                    <option value="publication_name"
                            <c:if test="${requestScope.sort_option == 'publication_name'}">selected</c:if>>Publication
                        name
                    </option>
                </select>
                <select class="form-select" id="sort_option_order" name="sort_option_order" style="max-width: 140px">
                    <option value="asc" <c:if test="${requestScope.sort_option_order == 'asc'}">selected</c:if>>
                        Ascending
                    </option>
                    <option value="desc" <c:if test="${requestScope.sort_option_order == 'desc'}">selected</c:if>>
                        Descending
                    </option>
                </select>
            </div>

        </div>



    </form>
    <c:if test="${sessionScope.exist_user == true&&sessionScope.user.role.roleId==3}"><a href="controller?action=AddBookPage" class="btn btn-primary mt-3 ms-auto me-5 d-flex" style="max-width: 100px">Add Book</a></c:if>


    <div class="row d-flex justify-content-center">
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
                                <a href="controller?action=addBook&isbn=${book.isbn}" class="btn btn-primary <c:if test="${sessionScope.user.userStatus.userStatusId==2}">disabled</c:if>" >Add</a>
                            </c:if>
                            <c:if test="${sessionScope.exist_user == true&&sessionScope.user_role==3}">
                                <div class="d-flex justify-content-between">
                                    <a href="#" class="btn btn-primary">Edit</a>
                                    <a href="#" class="btn btn-primary">Delete</a>
                                </div>
                            </c:if>

                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>


    </div>

</div>

<script src="js/window.js"></script>
</body>
</html>