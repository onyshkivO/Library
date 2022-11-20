package com.onyshkiv.DAO;

import com.onyshkiv.DAO.entity.Role;
import com.onyshkiv.DAO.entity.User;
import com.onyshkiv.DAO.entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAO implements AbstractDAO<String, User> {
    Connection con;
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private static UserDAO instance;

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() {
    }


    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(new Role(resultSet.getString(4)));
                user.setUserStatus(new UserStatus(resultSet.getString(5)));
                user.setFirstName(resultSet.getString(6));
                user.setLastName(resultSet.getString(7));
                user.setPhone(resultSet.getString(8));
                users.add(user);
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return users;
    }

    public Set<User> findAllUsersByActiveBook(int activeBookId) throws DAOException {
        Set<User> users = new HashSet<>();
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_USERS_BY_ACTIVE_BOOK)) {
            statement.setInt(1, activeBookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setLogin(resultSet.getString(1));
                    user.setEmail(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(new Role(resultSet.getString(4)));
                    user.setUserStatus(new UserStatus(resultSet.getString(5)));
                    user.setFirstName(resultSet.getString(6));
                    user.setLastName(resultSet.getString(7));
                    user.setPhone(resultSet.getString(8));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public User findEntityById(String login) throws DAOException {
        User user = null;
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    int k = 0;
                    user.setLogin(resultSet.getString(++k));
                    user.setEmail(resultSet.getString(++k));
                    user.setPassword(resultSet.getString(++k));
                    user.setRole(new Role(resultSet.getString(++k)));
                    user.setRole(new Role(resultSet.getString(++k)));
                    user.setFirstName(resultSet.getString(++k));
                    user.setLastName(resultSet.getString(++k));
                    user.setPhone(resultSet.getString(++k));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public boolean create(User model) throws DAOException {
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.INSERT_USER)) {
            int k = 0;
            statement.setString(++k, model.getLogin());
            statement.setString(++k, model.getEmail());
            statement.setString(++k, model.getPassword());
            statement.setInt(++k, model.getRole().getRole_id());
            statement.setInt(++k, model.getUserStatus().getUserStatusId());
            statement.setString(++k, model.getFirstName());
            statement.setString(++k, model.getLastName());
            statement.setString(++k, model.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public User update(User model) throws DAOException {
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.UPDATE_USER)) {
            int k = 0;
            statement.setString(++k, model.getEmail());
            statement.setString(++k, model.getPassword());
            statement.setInt(++k, model.getRole().getRole_id());
            statement.setInt(++k, model.getUserStatus().getUserStatusId());
            statement.setString(++k, model.getFirstName());
            statement.setString(++k, model.getLastName());
            statement.setString(++k, model.getPhone());
            statement.setString(++k, model.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getLogin());
    }

    @Override
    public boolean delete(User model) throws DAOException {
        try (PreparedStatement statement = con.prepareStatement(SQLQuery.UserQuery.DELETE_USER)) {
            statement.setString(1, model.getLogin());
            if (statement.executeUpdate() == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else model.setLogin(null);
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
            UserDAO userDAO = new UserDAO();
            a = DataSource.getConnection();
            userDAO.setConnection(a);
            User user = new User("login test", "tesm", "test", new Role("librarian"), new UserStatus("blocked"), "test", "test", "0988744456");
            userDAO.delete(user);
            a.commit();
        } catch (SQLException | DAOException e) {
            try {
                a.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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
