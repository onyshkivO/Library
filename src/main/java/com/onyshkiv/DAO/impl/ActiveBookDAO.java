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
import java.util.Optional;

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
                activeBook.setBook(bookDAO.findEntityById(activeBook.getBook().getIsbn()).orElse(null));

                UserDAO userDAO = UserDAO.getInstance();
                userDAO.setConnection(con);

                activeBook.setUser(userDAO.findEntityById(activeBook.getUser().getLogin()).orElse(null));
                activeBooks.add(activeBook);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBooks;
    }

    //++++++++++++++++++++++++
    @Override
    public Optional<ActiveBook> findEntityById(Integer id) throws DAOException {
        ActiveBook activeBook = null;
        Optional<ActiveBook> activeBookOptional;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.SELECT_ACTIVE_BOOK_BY_ID, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                activeBook = map(resultSet);

                BookDAO bookDAO = BookDAO.getInstance();
                bookDAO.setConnection(con);
                activeBook.setBook(bookDAO.findEntityById(activeBook.getBook().getIsbn()).orElse(null));

                UserDAO userDAO = UserDAO.getInstance();
                userDAO.setConnection(con);
                activeBook.setUser(userDAO.findEntityById(activeBook.getUser().getLogin()).orElse(null));
            }
            activeBookOptional = Optional.ofNullable(activeBook);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBookOptional;
    }

    //+++++++++++++++
    @Override
    public void create(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() != 0) {
            throw new IllegalArgumentException("ActiveBook is already created, the activeBook ID is not 0.");
        }
        if (model.getUser() == null || model.getUser().getLogin() == null || model.getUser().getLogin().isBlank()) {
            throw new IllegalArgumentException("Cannot create new active book relation without user");
        }
        Object[] values = {
                model.getBook().getIsbn(),
                model.getUser().getLogin(),
                model.getWayOfUsing().getWayOfUsingId(),
                model.getSubscriptionStatus().getPublicationStatusId(),
                toSqlDate(model.getStartDate()),
                toSqlDate(model.getEndDate()),
                model.getQuantity(),
                model.getFine()

        };

        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.INSERT_ACTIVE_BOOK, true, values)) {
            // ?????? можливо краще просто в сервісі викликати в bookDao findbyid
//            BookDAO bookDAO = BookDAO.getInstance();
//            bookDAO.setConnection(con);
//            if (!bookDAO.chechIsHaveAvaliableBook(model.getBook().getIsbn()))
//                throw new DAOException("Not avaliable book.");
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating activeBook failed, no rows affected.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    model.setActiveBookId(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public void update(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() == 0) {
            throw new IllegalArgumentException("ActiveBook is not created yet, the ActiveBook ID is 0.");
        }

        if (model.getUser() == null || model.getUser().getLogin() == null || model.getUser().getLogin().isBlank()) {
            throw new IllegalArgumentException("Cannot update  active book relation without users");
        }
        Object[] values = {
                model.getBook().getIsbn(),
                model.getUser().getLogin(),
                model.getWayOfUsing().getWayOfUsingId(),
                model.getSubscriptionStatus().getPublicationStatusId(),
                toSqlDate(model.getStartDate()),
                toSqlDate(model.getEndDate()),
                model.getQuantity(),
                model.getFine(),
                model.getActiveBookId()

        };
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.UPDATE_ACTIVE_BOOK, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating active_book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public void delete(ActiveBook model) throws DAOException {
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.ActiveBookQuery.DELETE_ACTIVE_BOOK, false, model.getActiveBookId())) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting active_book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    private ActiveBook map(ResultSet resultSet) throws SQLException {
        ActiveBook result = new ActiveBook();
        result.setActiveBookId(resultSet.getInt("active_book_id"));
        Book book = new Book();
        book.setIsbn(resultSet.getInt("book_isbn"));
        result.setBook(book);
        User user = new User();
        user.setLogin(resultSet.getString("user_login"));
        result.setUser(user);
        result.setWayOfUsing(new WayOfUsing(resultSet.getInt("way_of_using_id")));
        result.setSubscriptionStatus(new SubscriptionStatus(resultSet.getInt("subscription_status_id")));
        result.setStartDate(resultSet.getDate("start_date"));
        result.setEndDate(resultSet.getDate("end_date"));
        result.setQuantity(resultSet.getInt("quantity"));
        result.setFine(resultSet.getDouble("fine"));

        return result;
    }


}
