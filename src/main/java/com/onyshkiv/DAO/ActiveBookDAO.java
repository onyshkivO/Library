package com.onyshkiv.DAO;

import com.onyshkiv.DAO.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActiveBookDAO implements AbstractDAO<Integer, ActiveBook> {

    Connection con;
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


    @Override
    public List<ActiveBook> findAll() throws DAOException {
        List<ActiveBook> activeBooks = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.ActiveBookQuery.SELECT_ALL_ACTIVE_BOOKS);
             ResultSet resultSet = statement.executeQuery()) {

            BookDAO bookDAO = BookDAO.getInstance();
            UserDAO userDAO = UserDAO.getInstance();
            bookDAO.setConnection(con);
            userDAO.setConnection(con);

            while (resultSet.next()) {
                ActiveBook activeBook = new ActiveBook(resultSet.getInt(1));
                activeBook.setWayOfUsing(new WayOfUsing(resultSet.getString(3)));
                activeBook.setSubscriptionStatus(new SubscriptionStatus(resultSet.getString(4)));
                activeBook.setStartDate(resultSet.getDate(5));
                activeBook.setEndDate(resultSet.getDate(6));
                activeBook.setFine(resultSet.getDouble(7));
                activeBook.setBook(bookDAO.findEntityById(resultSet.getInt(2)));
                activeBook.setUsers(userDAO.findAllUsersByActiveBook(resultSet.getInt(1)));
                activeBooks.add(activeBook);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBooks;
    }

    @Override
    public ActiveBook findEntityById(Integer id) throws DAOException {
        ActiveBook activeBook = new ActiveBook(id);
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.ActiveBookQuery.SELECT_ACTIVE_BOOK_BY_ID);) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                BookDAO bookDAO = BookDAO.getInstance();
                UserDAO userDAO = UserDAO.getInstance();
                bookDAO.setConnection(con);
                userDAO.setConnection(con);
                if (resultSet.next()) {
                    activeBook.setWayOfUsing(new WayOfUsing(resultSet.getString(3)));
                    activeBook.setSubscriptionStatus(new SubscriptionStatus(resultSet.getString(4)));
                    activeBook.setStartDate(resultSet.getDate(5));
                    activeBook.setEndDate(resultSet.getDate(6));
                    activeBook.setFine(resultSet.getDouble(7));
                    activeBook.setBook(bookDAO.findEntityById(resultSet.getInt(2)));
                    activeBook.setUsers(userDAO.findAllUsersByActiveBook(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return activeBook;
    }

    @Override
    public boolean create(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() != 0) throw new DAOException();
        if (!chechIsHaveAvaliableBook(model.getBook())) throw new DAOException("Not avaliable book");
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.ActiveBookQuery.INSERT_ACTIVE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getBook().getIsbn());
            statement.setInt(2, model.getWayOfUsing().getWayOfUsingId());
            statement.setInt(3, model.getSubscriptionStatus().getPublicationStatusId());
            statement.setDate(4, new Date(model.getStartDate().getTime()));
            statement.setDate(5, new Date(model.getEndDate().getTime()));
            if (model.getFine() == null) {
                statement.setNull(6, Types.DOUBLE);
            } else {
                statement.setDouble(6, model.getFine());
            }
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    insertUsers(resultSet.getInt(1), model.getUsers());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void insertUsers(int activeBookId, Set<User> users) {
        try (PreparedStatement statement = con.prepareStatement("Insert into active_book_has_user values(?,?)")) {
            statement.setInt(1, activeBookId);
            for (User u : users) {
                statement.setString(2, u.getLogin());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean chechIsHaveAvaliableBook(Book book) throws DAOException {
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.setConnection(con);
        try (PreparedStatement statement = con.prepareStatement("SELECT name FROM book where quantity>0 and isbn=?")) {
            statement.setInt(1, book.getIsbn());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ActiveBook update(ActiveBook model) throws DAOException {
        if (model.getActiveBookId() == 0) {
            throw new IllegalArgumentException("ActiveBook is not created yet, the ActiveBook ID is 0.");
        }
        try(PreparedStatement statement = con.prepareStatement(SQLQuery.ActiveBookQuery.UPDATE_ACTIVE_BOOK)){
            int k =0;
            statement.setInt(++k,model.getBook().getIsbn());
            statement.setInt(++k,model.getWayOfUsing().getWayOfUsingId());
            statement.setInt(++k,model.getSubscriptionStatus().getPublicationStatusId());
            statement.setDate(++k,new Date(model.getStartDate().getTime()));
            statement.setDate(++k,new Date(model.getEndDate().getTime()));
            if (model.getFine() == null) {
                statement.setNull(++k, Types.DOUBLE);
            } else {
                statement.setDouble(++k, model.getFine());
            }
            statement.setInt(++k,model.getActiveBookId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating active_book failed, no rows affected.");
            }
            deleteUsers(model.getActiveBookId());
            insertUsers(model.getActiveBookId(),model.getUsers());
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getActiveBookId());
    }

    private void deleteUsers(int activeBookId) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("Delete from active_book_has_user where active_book_id = ?")){
            statement.setInt(1,activeBookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    @Override
    public boolean delete(ActiveBook model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement(SQLQuery.ActiveBookQuery.DELETE_ACTIVE_BOOK)) {
            statement.setInt(1,model.getActiveBookId());
            deleteUsers(model.getActiveBookId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting active_book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    public void setConnection(Connection connection) {
        this.con = connection;
    }

    public static void main(String[] args) {

        Connection a = null;
        try {
            BookDAO bookDAO = BookDAO.getInstance();
            ActiveBookDAO activeBookDAO = new ActiveBookDAO();
            UserDAO userDAO = UserDAO.getInstance();
            a = DataSource.getConnection();
            activeBookDAO.setConnection(a);
            bookDAO.setConnection(a);
            userDAO.setConnection(a);


//        List<ActiveBook> list = activeBookDAO.findAll();
//        list.forEach(System.out::println);

            Book book = bookDAO.findEntityById(2);
            Set<User> users = new HashSet<>();
            users.add(userDAO.findEntityById("user1"));
            users.add(userDAO.findEntityById("userLibr"));

            ActiveBook activeBook = new ActiveBook(3,book, new WayOfUsing("reading room"), new SubscriptionStatus("fined"), new java.util.Date(), new java.util.Date(), 18.25, users);
            activeBookDAO.delete(activeBook);

            a.commit();
        } catch (DAOException e) {
            try {
                a.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                a.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
