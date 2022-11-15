package com.onyshkiv.DAO;

import com.onyshkiv.DAO.entity.Author;
import com.onyshkiv.DAO.entity.Book;
import com.onyshkiv.DAO.entity.Entity;
import com.onyshkiv.DAO.entity.Publication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookDAO implements AbstractDAO<Integer, Book>{
    Connection con;

    private static final Logger logger = LogManager.getLogger(BookDAO.class);
    {
        try {
            con = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static BookDAO instance;

    public static synchronized BookDAO getInstance(){
        if (instance == null){
            instance = new BookDAO();
        }
        return instance;
    }

    private BookDAO()  {
    }

    @Override
    public List<Book> findAll() throws DAOException {
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
        List<Book> books = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement(SQLQuery.BookQuery.SELECT_ALL_BOOKS);
            ResultSet resultSet = statement.executeQuery()){
            outer: while(resultSet.next()){
                Book book = new Book();
                book.setIsbn(resultSet.getInt(1));
                for (Book value : books) {
                    if (value.getIsbn() == book.getIsbn()) {
                        value.getAuthors().add(new Author(resultSet.getInt(7), resultSet.getString(8)));
                        continue outer;
                    }
                }
                book.setName(resultSet.getString(2));
                book.setDateOfPublication(resultSet.getDate(3));
                book.setPublication(new Publication(resultSet.getInt(4),resultSet.getString(10)));
                book.setQuantity(resultSet.getInt(5));
                book.setDetails(resultSet.getString(6));
                book.setAuthors(new HashSet<>());
                book.getAuthors().add(new Author(resultSet.getInt(7),resultSet.getString(8)));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return books;
    }

    @Override
    public Book findEntityById(Integer id) throws DAOException {
        return null;
    }

    @Override
    public boolean create(Book model) throws DAOException {
        return false;
    }


    @Override
    public Book update(Book model) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(Book model) throws DAOException {
        return false;
    }

    public static void main(String[] args) {
        try {
            BookDAO bookDAO = new BookDAO();
            //bookDAO.con=DataSource.getConnection();
            List<Book> books = bookDAO.findAll();
            System.out.println(books.toString());
        } catch (DAOException e) {
            System.out.println("something went wrong");
        }

    }
}
