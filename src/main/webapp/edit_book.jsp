<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit book</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="header.jsp"%>
<div id="edit_book">
    <div class="container mt-">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <div class="d-flex justify-content-between my-4">
                        <a href="controller?action=bookPage" class="btn btn-primary " >Go back</a>
                        <a href="controller?action=AuthAndPub" class="btn btn-primary " >Manage authors and publications</a>
                    </div>
                    <form id="login-form" class="form" action="controller?action=editBook" method="post">

                        <input type="hidden" name="role" id="role" class="form-control" value="reader">
                        <h3 class="text-center text-info text-black">Edit Book:</h3>
                        <div class="form-group">
                            <label class="text-info text-black">Book isbn:</label><br>
                            <h5>${book.isbn}</h5>
                            <input type="hidden" name="isbn" id="isbn" class="form-control" value="${book.isbn}">
                        </div>
                        <div class="form-group">
                            <label for="name" class="text-info text-black">Book name:</label><br>
                            <input type="text" name="name" id="name" class="form-control" value="${book.name}" required>
                        </div>
                        <div class="form-group">


                            <label for="publication" class="text-info text-black">Publication:</label><br>
                            <select id="publication" name="publication" class="form-select exist" required>
                                <c:forEach var="publication" items="${publications}">
                                    <option value="${publication.publicationId}"
                                            <c:if test="${book.publication.publicationId==publication.publicationId}">selected </c:if> >${publication.name}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="form-group" id="authors-container">
                            <label for="authors" class="text-info text-black">Authors:</label><br>
                            <div class="author">
                                <select id="authors" name="authors" class="form-select"
                                        multiple required>
                                    <c:forEach var="author" items="${authors}">
                                        <option value="${author.authorId}" <c:if
                                                test="${book.authors.contains(author)}"> selected</c:if>>${author.name}

                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="quantity" class="text-info text-black">Quantity:</label><br>
                            <input type="number" name="quantity" id="quantity" class="form-control" min="0"
                                   value="${book.quantity}" required>
                        </div>

                        <div class="form-group">
                            <label for="year_of_publication" class="text-info text-black">Year of
                                publication:</label><br>
                            <input type="date" name="year_of_publication" id="year_of_publication"
                                   class="form-control" min="1945-01-01" max="${requestScope.date}"
                                   value="${book.dateOfPublication}" required>
                        </div>
                        <div class="form-group">
                            <label for="details" class="text-info text-black">Book descriptions:</label><br>
                            <textarea id="details" name="details" rows="4" cols="79">${book.details}</textarea>
                        </div>


                        <div class="form-group">
                            <input type="submit" name="submit" class="btn btn-outline-primary" value="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script></script>
</body>
</html>
