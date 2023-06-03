<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

    <link href="style.css" rel="stylesheet">

</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">

    <form class="mt-4  d-flex justify-content-between" action="controller" method="get">
        <input type="hidden" class="form-control" name="action" value="bookPage">
        <input type="hidden" class="form-control" name="page" value="1">
        <div class="ms-5 ps-5" style="max-width: 400px">
            <h5>Searching</h5>
            <div class="input-group me-5 pe-5 d-flex">
                <input type="text" class="form-control" name="name" value="${requestScope.name}">
                <select class="form-select" id="inputGroupSelect01" name="search_option">
                    <option value="book_name"
                            <c:if test="${requestScope.search_option == 'book_name'}">selected</c:if>>
                        Book name
                    </option>
                    <option value="author_name"
                            <c:if test="${requestScope.search_option == 'author_name'}">selected</c:if>>Author name
                    </option>
                </select>

            </div>
        </div>
        <button type="submit" class="btn btn-outline-primary mt-auto">Search</button>

        <div class=" me-5 pe-5 " style="min-width: 360px">
            <h5>Sorting</h5>
            <div class="input-group d-flex">
                <select class="form-select" id="sort_option" name="sort_option" style="min-width: 220px">
                    <option value="b.name" <c:if test="${requestScope.sort_option.equals('b.name')}">selected</c:if>>
                        Book name
                    </option>
                    <option value="authors" <c:if test="${requestScope.sort_option == 'authors'}">selected</c:if>>
                        Author name
                    </option>
                    <option value="date_of_publication"
                            <c:if test="${requestScope.sort_option == 'date_of_publication'}">selected</c:if>>
                        Date of publication
                    </option>
                    <option value="p.name"
                            <c:if test="${requestScope.sort_option == 'p.name'}">selected</c:if>>
                        Publication name
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
    <c:if test="${sessionScope.exist_user == true&&sessionScope.user.role.roleId==3}"><a
            href="controller?action=AddBookPage" class="btn btn-primary mt-3 ms-auto me-5 d-flex"
            style="max-width: 100px">Add Book</a></c:if>


    <div class="row d-flex justify-content-center">
        <c:choose>
            <c:when test="${books.isEmpty()==true}">
                <h2 class="my-4 d-flex justify-content-center">There are no available books</h2>
            </c:when>
            <c:otherwise>
                <h2 class="my-4 d-flex justify-content-center">Available books</h2>
                <c:forEach var="book" items="${books}">

                    <div class="card my-2"
                         style=" max-width: 1100px; <c:if test="${book.quantity==0}">background-color: #d9d9d9 </c:if>">
                        <h4 class="card-header">${book.name}</h4>
                        <div class="card-body row">
                            <div class="col-3 align-self-center">
                                <h6 class="card-title">isbn: ${book.isbn}</h6>
                                <h6 class="card-text">Authors: <c:forEach var="author" items="${book.authors}"
                                                                          varStatus="status">
                                    ${author.name}<c:if test="${!status.last}">, </c:if>
                                </c:forEach></h6>
                                <h6 class="card-text">publications : ${book.publication.name}</h6>
                                <h6 class="card-text">Date of Publication : ${book.dateOfPublication}</h6>
                            </div>
                            <div class="col-8 align-self-center">
                                <c:if test="${book.details!=null && !book.details.isBlank() }">
                                    <h5 class="card-title">Details</h5>
                                    <p class="card-text">${book.details}</p></c:if>

                            </div>
                            <div class="col-1 align-self-center">
                                <c:if test="${sessionScope.exist_user == true&&sessionScope.user_role==1}">
                                    <form action="controller?action=addBook" method="post">
                                        <input type="hidden" name="isbn" value="${book.isbn}">
                                        <input type="submit" class="btn btn-primary mb-1"
                                               <c:if test="${sessionScope.user.userStatus.userStatusId==2}">disabled</c:if>
                                               value="Order">
                                    </form>
                                </c:if>
                                <c:if test="${sessionScope.exist_user == true&&sessionScope.user_role==3}">
                                    <a href="controller?action=editBookPage&isbn=${book.isbn}"
                                       class="btn btn-primary mb-1 ">Edit</a><br>
                                    <form action="controller?action=deleteBook" method="post">
                                        <input type="hidden" name="isbn" value="${book.isbn}">
                                        <input type="submit" class="btn btn-primary mb-1 " value="Delete">
                                    </form>
                                </c:if>
                            </div>


                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
    <c:if test="${books.isEmpty()!=true}">
        <nav aria-label="..." class="ms-5 ps-5">
            <ul class="pagination">
                <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                    <a class="page-link"
                       href="controller?action=bookPage&page=${page - 1}&search_option=${search_option}&sort_option=${sort_option}&sort_option_order=${sort_option_order}&name=${name}"
                       tabindex="-1">Previous</a>
                </li>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${page eq i}">
                            <li class="page-item active">
                        <span class="page-link">
                                ${i}
                        </span>
                            </li>

                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=bookPage&page=${i}&search_option=${search_option}&sort_option=${sort_option}&sort_option_order=${sort_option_order}&name=${name}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <li class="page-item <c:if test="${page == noOfPages}">disabled</c:if> ">
                    <a class="page-link"
                       href="controller?action=bookPage&page=${page + 1}&search_option=${search_option}&sort_option=${sort_option}&sort_option_order=${sort_option_order}&name=${name}">Next</a>
                </li>
            </ul>
        </nav>
    </c:if>


</div>

<script charset="UTF-8" src="js/window.js"></script>
</body>
</html>
