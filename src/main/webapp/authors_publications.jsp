<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors and publications</title>
</head>
<body>
<%@ include file="header.jsp" %>

<%--<div class="container"><a class="btn btn-outline-primary active mt-3 " href="controller?action=AddBookPage"  role="button">Go back</a></div>--%>
<%--<div class="container"><a class="btn btn-outline-primary active mt-3 " onclick="goBack()"  role="button">Go back</a></div>--%>
<div class="container"><a class="btn btn-outline-primary active mt-3 " href="controller?action=AddBookPage"  role="button">Add book page</a></div>
<div class="container"><a class="btn btn-outline-primary active mt-3 " href="controller?action=bookPage&page=1"  role="button">Books page</a></div>
<div class="container text-center mt-4">
    <div class="row">

        <div class="col  d-flex ">
            <div class="container ms-0  border border-dark-subtle rounded-3">
                <h2>Authors</h2>
                <hr>
                <div class="card-body">
                    <div class=" mb-3 ">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="author" items="${authors}">
                                <tr>
                                    <td class="w-75">${author.name}</td>
                                    <td><a class="btn btn-outline-primary active" href="#" data-bs-toggle="modal"
                                          data-bs-target="#author${author.authorId}" role="button">Rename</a></td>


                                </tr>
                            </c:forEach>


                            </tbody>
                        </table>
                        <form action="controller?action=createAuthor" method="post" class="d-flex">

                            <div class="col-4">
                                <label for="author_name" class="text-info text-black">Author name:</label>
                            </div>
                            <div class="col-5">
                                <input type="text" name="author_name" id="author_name"
                                       class="form-control border border-primary" required>
                            </div>
                            <div class="col">
                                <input type="submit" name="submit" class="btn btn-outline-primary" value="Add">
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="container ms-0  border border-dark-subtle rounded-3">
                <h2>Publications</h2>
                <hr>
                <div class="card-body">
                    <div class=" mb-3 ">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="publication" items="${publications}">
                                <tr>
                                    <td class="w-75">${publication.name}</td>
                                    <td><a class="btn btn-outline-primary active" href="#" data-bs-toggle="modal"
                                           data-bs-target="#publication${publication.publicationId}" role="button">Rename</a></td>
                                </tr>
                            </c:forEach>


                            </tbody>
                        </table>
                        <form action="controller?action=createPublication" method="post" class="d-flex">

                            <div class="col-4">
                                <label for="publication_name" class="text-info text-black">Publication name:</label>
                            </div>
                            <div class="col-5">
                                <input type="text" name="publication_name" id="publication_name"
                                       class="form-control border border-primary" required>
                            </div>
                            <div class="col">
                                <input type="submit" name="submit" class="btn btn-outline-primary" value="Add">
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <c:forEach var="author" items="${authors}">
    <div class="modal fade" id="author${author.authorId}" tabindex="-1"
         aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel1">Rename Author</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="controller?action=renameAuthor" method="post">
                    <div class="modal-body">
                        <input type="text" name="new_author_name" class="form-control border border-primary"
                               value="${author.name}" required >
                        <input type="hidden" name="author_id" class="form-control border border-primary"
                               value="${author.authorId}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-primary" value="Save changes">
                    </div>
                </form>
            </div>
        </div>
    </div>
    </c:forEach>
    <c:forEach var="publication" items="${publications}">
    <div class="modal fade" id="publication${publication.publicationId}" tabindex="-1"
         aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Rename publication</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="controller?action=renamePublication" method="post">
                    <div class="modal-body">
                        <input type="text" name="new_publication_name" class="form-control border border-primary"
                               value="${publication.name}" required>
                        <input type="hidden" name="publication_id" class="form-control border border-primary"
                               value="${publication.publicationId}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-primary" value="Save changes">
                    </div>
                </form>
            </div>
        </div>
    </div>
    </c:forEach>

    <script>
        function goBack() {
            window.history.back(3);
        }
    </script>
</body>
</html>
