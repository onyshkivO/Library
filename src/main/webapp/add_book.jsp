<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<%@ include file="header.jsp" %>

<div id="add_book">
    <div class="container mt-5">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="controller?action=createBook" method="post">
                        <input type="hidden" name="role" id="role" class="form-control" value="reader">
                        <h3 class="text-center text-info text-black">Add Book:</h3>
                        <div class="form-group">
                            <label for="isbn" class="text-info text-black">Book isbn:</label><br>
                            <input type="text" name="isbn" id="isbn" class="form-control" value="${isbn}" required>
                            <%--todo якщо щось не так, то виводити це користувачу--%>

                            <%--                            <c:if test="${incorrect_login == true}">--%>
                            <%--                                <p class="text-danger lh-1 ms-1" >Incorrect login</p>--%>
                            <%--                            </c:if>--%>

                            <%--                            <c:if test="${already_exist_login == true}">--%>
                            <%--                                <p class="text-danger lh-1 ms-1">User login already exist</p>--%>
                            <%--                            </c:if>--%>

                        </div>
                        <div class="form-group">
                            <label for="name" class="text-info text-black">Book name:</label><br>
                            <input type="text" name="name" id="name" class="form-control" value="${name}" required>
                        </div>
                        <div class="form-group">


                            <label for="publication" class="text-info text-black">Publication:</label><br>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="exist"
                                       value="option1" checked>
                                <label class="form-check-label" for="exist">Exist publication</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="new"
                                       value="option2">
                                <label class="form-check-label" for="new">New publication</label>
                            </div>


                            <select id="publication" name="publication" class="form-select exist">
                                <c:forEach var="publication" items="${publications}">
                                    <option value="${publication.publicationId}" selected>${publication.name}</option>
                                </c:forEach>
                            </select>

                            <div class="new" style="display: none">

                                <input type="text" name="publication_name" id="publication_name" class="form-control"
                                       placeholder="Publication name">
                            </div>


                        </div>
                        <div class="form-group" id="authors-container">
                            <label for="authors" class="text-info text-black">Authors:</label><br>
                            <div class="author">
                                <select id="authors" name="authors" class="form-select" style="max-width: 300px"
                                        multiple>
                                    <c:forEach var="author" items="${authors}">
                                        <option value="${author.authorId}">${author.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="quantity" class="text-info text-black">Quantity:</label><br>
                            <input type="number" name="quantity" id="quantity" class="form-control" min="0"
                                   value="${quantity} " required>
                        </div>

                        <div class="form-group">
                            <label for="year_of_publication" class="text-info text-black">Year of
                                publication:</label><br>
                            <input type="date" name="year_of_publication" id="year_of_publication"
                                   class="form-control" min="1945-01-01" max="2099-01-01"
                                   value="${year_of_publication} " required>
                        </div>

                        <div class="form-group">
                            <label for="details" class="text-info text-black">Book descriptions:</label><br>
                            <textarea id="details" name="details" rows="4" cols="79"></textarea>
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
<script src="js/radioButt.js"></script>
</body>
</html>
