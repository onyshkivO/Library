package com.onyshkiv.DAO;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.Publication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements AbstractDAO<Integer, Book> {
    private Connection con;
    private static final Logger logger = LogManager.getLogger(BookDAO.class);
    private static BookDAO instance;

    public static synchronized BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    private BookDAO() {
    }

    //++++++++++++++++++++++
    @Override
    public List<Book> findAll() throws DAOException {
        List<Book> books = new ArrayList<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.FIND_ALL_BOOKS, false);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Book result = map(resultSet);

                PublicationDAO publicationDAO = PublicationDAO.getInstance();
                publicationDAO.setConnection(con);
                result.setPublication(publicationDAO.findEntityById(result.getPublication().getPublicationId()));

                AuthorDAO authorDAO = AuthorDAO.getInstance();
                authorDAO.setConnection(con);
                result.setAuthors(authorDAO.getAllAuthorByBookISBN(result.getIsbn()));
                books.add(result);
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return books;
    }

    //++++++++++++++++++++++
    @Override
    public Book findEntityById(Integer id) throws DAOException {
        Book result = new Book();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.FIND_BOOK_BY_ISBN, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                result = map(resultSet);

                PublicationDAO publicationDAO = PublicationDAO.getInstance();
                publicationDAO.setConnection(con);
                result.setPublication(publicationDAO.findEntityById(result.getPublication().getPublicationId()));

                AuthorDAO authorDAO = AuthorDAO.getInstance();
                authorDAO.setConnection(con);
                result.setAuthors(authorDAO.getAllAuthorByBookISBN(id));
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return result;
    }

    //++++++++++++++++++++++
    @Override
    public boolean create(Book model) throws DAOException {

        Object[] values = {
                model.getIsbn(),
                model.getName(),
                toSqlDate(model.getDateOfPublication()),
                model.getPublication().getPublicationId(),
                model.getQuantity(),
                model.getDetails()
        };
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.INSERT_BOOK, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating book failed, no rows affected.");
            }
            AuthorDAO authorDAO = AuthorDAO.getInstance();
            authorDAO.setConnection(con);
            for (Author a : model.getAuthors()) {
                authorDAO.setAuthorBookTableConnection(model.getIsbn(), a.getAuthorId());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return true;
    }

    //++++++++++++++++++++++
    @Override
    public Book update(Book model) throws DAOException {
        if (model.getIsbn() == 0) {
            throw new IllegalArgumentException("Book is not created yet, the Book isbn is 0.");
        }

        Object[] values = {
                model.getName(),
                toSqlDate(model.getDateOfPublication()),
                model.getPublication().getPublicationId(),
                model.getQuantity(),
                model.getDetails(),
                model.getIsbn()
        };

        try (PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.UPDATE_BOOK, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating book failed, no rows affected.");
            }

            AuthorDAO authorDAO = AuthorDAO.getInstance();
            authorDAO.setConnection(con);
            authorDAO.removeAuthorBookTableConnection(model.getIsbn());
            for (Author a : model.getAuthors()) {
                authorDAO.setAuthorBookTableConnection(model.getIsbn(), a.getAuthorId());
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getIsbn());
    }

    //++++++++++++++++++++++
    @Override
    public boolean delete(Book model) throws DAOException {
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.DELETE_BOOK, false, model.getIsbn())) {
            AuthorDAO authorDAO = AuthorDAO.getInstance();
            authorDAO.setConnection(con);
            authorDAO.removeAuthorBookTableConnection(model.getIsbn());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting book failed, no rows affected.");
            } else {
                model.setIsbn(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    public boolean chechIsHaveAvaliableBook(Integer isbn) throws DAOException {
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.BookQuery.IS_AVALIABLE_BOOK, false, isbn);
             ResultSet resultSet = statement.executeQuery()
        ) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }


    private static Book map(ResultSet resultSet) throws SQLException {
        Book result = new Book();

        Publication publication = new Publication();
        result.setIsbn(resultSet.getInt(1));
        result.setName(resultSet.getString(2));
        publication.setPublicationId(resultSet.getInt(4));


        result.setDateOfPublication(resultSet.getDate(3));
        result.setPublication(publication);
        result.setQuantity(resultSet.getInt(5));
        result.setDetails(resultSet.getString(6));

        return result;
    }


    public void setConnection(Connection connection) {
        this.con = connection;
    }
}
