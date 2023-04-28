package com.onyshkiv.DAO.impl;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.DAO.AbstractDAO;
import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.SQLQuery;
import com.onyshkiv.util.password.PasswordHashGenerator;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDAO extends AbstractDAO<String, User> {
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

    public List<User> findLibrarians() throws DAOException {
        List<User> users = new ArrayList<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_ALL_LIBRARIANS, false);
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
    public List<User> findUserByRole(Integer roleId) throws DAOException {
        List<User> users = new ArrayList<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_USERS_BY_ROLE, false,roleId);
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


    //+++++++++++++++++++++++++++++++++++++
    @Override
    public Optional<User> findEntityById(String login) throws DAOException {
        User user = null;
        Optional<User> optionalUser;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_USER_BY_LOGIN, false, login);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
            optionalUser = Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return optionalUser;
    }
    public Optional<String> findUserPasswordById(String login) throws DAOException {
        Optional<String> result;
        String password =null;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.SELECT_PASSWORD_BY_LOGIN, false, login);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                 password = resultSet.getString("password");

            }
            result = Optional.ofNullable(password);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void create(User model) throws DAOException {
        Object[] values = {
                model.getLogin(),
                model.getEmail(),
                PasswordHashGenerator.hash(model.getPassword()),
                model.getRole().getRoleId(),
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
            throw new DAOException("Can't get user from database because of: " + e.getMessage(), e);
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void update(User model) throws DAOException {
        if (model.getLogin() == null) {
            throw new IllegalArgumentException("User is not created yet, the user login is null.");
        }

        Object[] values = {
                model.getEmail(),
                model.getRole().getRoleId(),
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
    }

    //+++++++++++++++++++++++++++++++++
    @Override
    public void delete(User model) throws DAOException {
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
    }

    //+++++++++++++++++++++++++++++++++
//    public void changePassword(User user) throws DAOException {
//        if (user.getLogin() == null)
//            throw new IllegalArgumentException("User is not created yet, the user login is null.");
//
//        Object[] values = {
//                user.getPassword(),
//                user.getLogin()
//        };
//
//        try (
//                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.CHANGE_PASSWORD, false, values)
//        ) {
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows == 0) {
//                throw new DAOException("Changing password failed, no rows affected.");
//            }
//        } catch (SQLException e) {
//            //log
//            throw new DAOException(e);
//        }
//
//    }



    public void changePassword(String password,String login) throws DAOException {
        if (login == null)
            throw new IllegalArgumentException("User is not created yet, the user login is null.");


        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.CHANGE_PASSWORD, false,password, login)
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
    public void changeLogin(String newLogin,String oldLogin) throws DAOException {

        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.CHANGE_LOGIN, false, newLogin,oldLogin)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("problem there");
                throw new DAOException("Changing password failed, no rows affected.");
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }

    }

//    public Set<User> getAllUsersByActiveBookId(Integer activeBookId) throws DAOException {
//        Set<User> users = new HashSet<>();
//        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_USERS_ACTIVE_BOOK_ID, false, activeBookId);
//             ResultSet resultSet = statement.executeQuery()
//        ) {
//            while (resultSet.next()) {
//                users.add(findEntityById(resultSet.getString(1)));
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//        return users;
//    }
//
//    public void setActiveBookUserConnection(Integer activeBookID, String login) throws DAOException {
//        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_INSERT_BOOK_AND_AUTHOR, false, activeBookID, login)) {
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows == 0) {
//                throw new DAOException("Creating m2m_active_book_users failed, no rows affected.");
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//    }
//
//    public void removeActiveBookUserConnection(Integer activeBookID) throws DAOException {
//        try (PreparedStatement statement = prepareStatement(con, SQLQuery.UserQuery.M2M_REMOVE_BOOK_AND_AUTHOR, false, activeBookID)) {
//            int affectedRows = statement.executeUpdate();
////            if (affectedRows == 0) {
////                throw new DAOException("Deleting m2m_active_book_users failed, no rows affected.");
////            }
//        } catch (SQLException e) {
//            //log
//            throw new DAOException(e);
//        }
//
//    }

    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setRole(new Role(resultSet.getInt("role_id")));
        user.setUserStatus(new UserStatus(resultSet.getInt("status_id")));
        user.setPhone(resultSet.getString("phone"));
        return user;
    }


}
