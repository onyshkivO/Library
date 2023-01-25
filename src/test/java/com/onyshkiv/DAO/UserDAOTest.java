package com.onyshkiv.DAO;

import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void findAll() {
        Connection con = null;
        try {
            con = DataSource.getConnection();
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.setConnection(con);
            List<User> users = userDAO.findAll();
            System.out.println(users);
            assertArrayEquals(users.toArray(),new Object[]{new User("user1","das","dsa",new Role("reader"),new UserStatus("active"),"Ostap","Patso",null)});
        } catch (SQLException | DAOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void findAllUsersByActiveBook() {
    }

    @Test
    void findEntityById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void changePassword() {
    }
}