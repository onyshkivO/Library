package com.onyshkiv.DAO.impl;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.DAO.AbstractDAO;
import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.SQLQuery;
import com.onyshkiv.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiveBookDAO extends AbstractDAO<Integer, ActiveBook> {

    private static final Logger logger = LogManager.getLogger(ActiveBookDAO.class);
    private static ActiveBookDAO instance;

    public static synchronized ActiveBookDAO getInstance() {
        if (instance == null) {
            instance = new ActiveBookDAO();
        }
        return instance;
    }

    private ActiveBookDAO() {
    }

    //+++++++++++++++++++
    @Override
    public List<ActiveBook> findAll() throws DAOException {
        List<ActiveBook> activeBooks = new ArrayList<>();
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.SELECT_ALL_ACTIVE_BOOKS, false);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                ActiveBook activeBook = map(resultSet);

                BookDAO bookDAO = BookDAO.getInstance();
                bookDAO.setConnection(con);
                activeBook.setBook(bookDAO.findEntityById(activeBook.getBook().getIsbn()));

                UserDAO userDAO = UserDAO.getInstance();
                userDAO.setConnection(con);
                activeBook.setUsers(userDAO.getAllUsersByActiveBookId(activeBook.getActiveBookId()));
                activeBooks.add(activeBook);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBooks;
    }

    //++++++++++++++++++++++++
    @Override
    public ActiveBook findEntityById(Integer id) throws DAOException {
        ActiveBook activeBook = null;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.SELECT_ACTIVE_BOOK_BY_ID, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                activeBook = map(resultSet);

                BookDAO bookDAO = BookDAO.getInstance();
                bookDAO.setConnection(con);
                activeBook.setBook(bookDAO.findEntityById(activeBook.getBook().getIsbn()));

                UserDAO userDAO = UserDAO.getInstance();
                userDAO.setConnection(con);
                activeBook.setUsers(userDAO.getAllUsersByActiveBookId(activeBook.getActiveBookId()));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBook;
    }

    //+++++++++++++++
    @Override
    public boolean create(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() != 0) {
            throw new IllegalArgumentException("ActiveBook is already created, the activeBook ID is not 0.");
        }
        Object[] values = {
                model.getBook().getIsbn(),
                model.getWayOfUsing().getWayOfUsingId(),
                model.getSubscriptionStatus().getPublicationStatusId(),
                toSqlDate(model.getStartDate()),
                toSqlDate(model.getEndDate()),
                model.getFine()

        };

        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.INSERT_ACTIVE_BOOK, true, values)) {
            BookDAO bookDAO = BookDAO.getInstance();
            bookDAO.setConnection(con);
            if (!bookDAO.chechIsHaveAvaliableBook(model.getBook().getIsbn()))
                throw new DAOException("Not avaliable book.");
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating activeBook failed, no rows affected.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    model.setActiveBookId(resultSet.getInt(1));
                    UserDAO userDAO = UserDAO.getInstance();
                    userDAO.setConnection(con);
                    for (User u : model.getUsers()) {
                        userDAO.setActiveBookUserConnection(model.getActiveBookId(), u.getLogin());
                    }

                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return true;
    }


    @Override
    public ActiveBook update(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() == 0) {
            throw new IllegalArgumentException("ActiveBook is not created yet, the ActiveBook ID is 0.");
        }
        Object[] values = {
                model.getBook().getIsbn(),
                model.getWayOfUsing().getWayOfUsingId(),
                model.getSubscriptionStatus().getPublicationStatusId(),
                toSqlDate(model.getStartDate()),
                toSqlDate(model.getEndDate()),
                model.getFine(),
                model.getActiveBookId()

        };
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.UPDATE_ACTIVE_BOOK, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating active_book failed, no rows affected.");
            }
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.setConnection(con);
            userDAO.removeActiveBookUserConnection(model.getActiveBookId());
            for (User u : model.getUsers())
                userDAO.setActiveBookUserConnection(model.getActiveBookId(), u.getLogin());
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getActiveBookId());
    }


    @Override
    public boolean delete(ActiveBook model) throws DAOException {
        try(PreparedStatement statement = prepareStatement(con,SQLQuery.ActiveBookQuery.DELETE_ACTIVE_BOOK,false,model.getActiveBookId())){
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting active_book failed, no rows affected.");
            }
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.setConnection(con);
            userDAO.removeActiveBookUserConnection(model.getActiveBookId());
        }catch (SQLException e){
            throw new DAOException(e);
        }

        return true;
    }

    private ActiveBook map(ResultSet resultSet) throws SQLException {
        ActiveBook result = new ActiveBook();
        result.setActiveBookId(resultSet.getInt("active_book_id"));

        Book book = new Book();
        book.setIsbn(resultSet.getInt("book_isbn"));
        result.setBook(book);
        result.setWayOfUsing(new WayOfUsing(resultSet.getInt("way_of_using_id")));
        result.setSubscriptionStatus(new SubscriptionStatus(resultSet.getInt("subscription_status_id")));
        result.setStartDate(resultSet.getDate("start_date"));
        result.setEndDate(resultSet.getDate("end_date"));
        result.setFine(resultSet.getDouble("fine"));

        return result;
    }



}
