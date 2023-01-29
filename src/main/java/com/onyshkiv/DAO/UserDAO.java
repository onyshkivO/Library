package com.onyshkiv.DAO;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_ALL_USERS, false);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return users;
    }

//    //+++++++++++++++++++++++++++++++++++++++++++++
//    public Set<User> findAllUsersByActiveBook(int activeBookId) throws DAOException {
//        Set<User> users = new HashSet<>();
//
//        try (
//                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_ALL_USERS_BY_ACTIVE_BOOK, false, activeBookId);
//                ResultSet resultSet = statement.executeQuery()
//        ) {
//            while (resultSet.next()) {
//                users.add(map(resultSet));
//            }
//        } catch (SQLException e) {
//            //log
//            throw new DAOException(e);
//        }
//        return users;
//    }

    //+++++++++++++++++++++++++++++++++++++
    @Override
    public User findEntityById(String login) throws DAOException {
        User user = null;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_USER_BY_LOGIN, false, login);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean create(User model) throws DAOException {
        if (model.getLogin() != null) {
            throw new IllegalArgumentException("User is already created, the user login is not null.");
        }
        Object[] values = {
                model.getLogin(),
                model.getEmail(),
                model.getPassword(),
                model.getRole().getRole_id(),
                model.getUserStatus().getUserStatusId(),
                model.getFirstName(),
                model.getLastName(),
                model.getPhone()
        };

        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.INSERT_USER, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return true;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public User update(User model) throws DAOException {
        if (model.getLogin() == null) {
            throw new IllegalArgumentException("User is not created yet, the user login is null.");
        }

        Object[] values = {
                model.getEmail(),
                model.getRole().getRole_id(),
                model.getUserStatus().getUserStatusId(),
                model.getFirstName(),
                model.getLastName(),
                model.getPhone(),
                model.getLogin()
        };
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.UPDATE_USER, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return findEntityById(model.getLogin());
    }

    //+++++++++++++++++++++++++++++++++
    @Override
    public boolean delete(User model) throws DAOException {
        if (model.getLogin() == null) {
            throw new IllegalArgumentException("User is not created yet, the user login is null.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.DELETE_USER, false, model.getLogin())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else model.setLogin(null);
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return true;
    }

    //+++++++++++++++++++++++++++++++++
    public void changePassword(User user) throws DAOException {
        if (user.getLogin() == null)
            throw new IllegalArgumentException("User is not created yet, the user login is null.");

        Object[] values = {
                user.getPassword(),
                user.getLogin()
        };

        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.CHANGE_PASSWORD, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing password failed, no rows affected.");
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }

    }

    public Set<User> getAllUsersByActiveBookId(Integer activeBookId) throws DAOException {
        Set<User> users = new HashSet<>();
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_USERS_ACTIVE_BOOK_ID, false, activeBookId);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(findEntityById(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    public void setActiveBookUserConnection(Integer activeBookID, String login) throws DAOException {
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_INSERT_BOOK_AND_AUTHOR, false, activeBookID, login)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating m2m_active_book_users failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void removeActiveBookUserConnection(Integer activeBookID) throws DAOException {
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_REMOVE_BOOK_AND_AUTHOR, false, activeBookID)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting m2m_active_book_users failed, no rows affected.");
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }

    }

    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setRole(new Role(resultSet.getString("role")));
        user.setUserStatus(new UserStatus(resultSet.getString("status")));
        user.setPhone(resultSet.getString("phone"));
        return user;
    }

    public void setConnection(Connection connection) {
        this.con = connection;
    }


}
