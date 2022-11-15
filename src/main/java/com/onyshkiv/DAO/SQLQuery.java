package com.onyshkiv.DAO;

public abstract class SQLQuery {
    static class BookQuery{
        public static final String SELECT_ALL_BOOKS ="select book.*,authors.*,publication.* " +
                "from book, authors, book_has_authors,publication " +
                "where book.isbn=book_has_authors.b_isbn and  book_has_authors.a_id = authors.authors_id and publication.publication_id=book.publication_id";
    }
}
