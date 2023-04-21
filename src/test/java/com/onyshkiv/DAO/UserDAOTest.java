package com.onyshkiv.DAO;


import com.onyshkiv.DAO.impl.UserDAO;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    static Connection con;
    static UserDAO userDAO;


    @BeforeAll
    static void setTestMode() {
        try {
            ApplicationResourceBundle.setTestBundle();
            userDAO = UserDAO.getInstance();
            con = DataSource.getConnection();
            userDAO.setConnection(con);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void closeConnection() {
        try {
            ScriptRunner scriptRunner = new ScriptRunner(con);
            scriptRunner.runScript(new BufferedReader(new FileReader("E:\\Final_project_EPAM\\Library\\src\\test\\resources\\library_final_project_test_script.sql")));
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    //test for findAll, must run only one
//    @Test
//    void findAllTest() throws DAOException {
//        List<User> users = userDAO.findAll();
//        List<User> actual = new ArrayList<>();
//        actual.add(new User("user1", "das", PasswordHashGenerator.hash("password"), new Role("reader"), new UserStatus("active"), "Ostap", "Patso", null));
//        actual.add(new User("userfordeleting", "delete@gmail.com", PasswordHashGenerator.hash("password"), new Role(1), new UserStatus(2), "name", "secondname", null));
//        actual.add(new User("userLib21", "das", PasswordHashGenerator.hash("password"), new Role("librarian"), new UserStatus("active"), "lib", "miy", null));
//        actual.add(new User("userLibr1", "das", PasswordHashGenerator.hash("password"), new Role("librarian"), new UserStatus("active"), "lib", "miy", null));
//        actual.add(new User("userLibr2", "das", PasswordHashGenerator.hash("password"), new Role("librarian"), new UserStatus("active"), "lib", "nemiy", null));
//        assertArrayEquals(users.toArray(), actual.toArray());
//    }

    @ParameterizedTest
    @CsvSource({
            "user1,asd, reader, active, Ostap, Patso, null",
            "userLib21, asd,librarian, active, lib, miy, null",
            "userLibr1, asd, librarian, active, lib, miy, null",
            "userLibr2, asd, librarian, active, lib, nemiy, null"
    })
    void findEntityById(String login, String email, String role, String status,
                        String firstName, String lastName, String phone) throws DAOException {

        User user = userDAO.findEntityById(login).get();
        assertAll(
                () -> assertEquals(user.getLogin(), login),
                () -> assertEquals(user.getUserStatus(), new UserStatus(status)),
                () -> assertEquals(user.getEmail(), email),
                () -> assertEquals(user.getRole(), new Role(role)),
                () -> assertEquals(user.getFirstName(), firstName),
                () -> assertEquals(user.getLastName(), lastName),
                phone.equals("null") ? () -> assertNull(user.getPhone()) : () -> assertEquals(user.getPhone(), phone));
    }

    @Test
    void findEntityByIdNoEntries() throws DAOException {
        User user = userDAO.findEntityById("incorrectlogin").orElse(null);
        assertNull(user);
    }

    @ParameterizedTest
    @CsvSource(
            "testUser,asd@gmail.com,password, reader, active, Ostap, Patso")
    void createUserWithCorrectData(String login, String email, String password, String role, String status,
                                   String firstName, String lastName) throws DAOException {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertDoesNotThrow(() -> userDAO.create(user));
        User user1 = userDAO.findEntityById(login).get();
        assertAll(
                () -> assertEquals(user1.getLogin(), login),
                () -> assertEquals(user1.getUserStatus(), new UserStatus(status)),
                () -> assertEquals(user1.getEmail(), email),
                () -> assertEquals(user1.getRole(), new Role(role)),
                () -> assertEquals(user1.getFirstName(), firstName),
                () -> assertEquals(user1.getLastName(), lastName),
                () -> assertNull(user1.getPhone()));
    }


    @ParameterizedTest
    @CsvSource(
            "user1,asd@gmail.com,password, reader, active, Ostap, Patso")
    void createUserWithIncorrectData(String login, String email, String password, String role, String status,
                                     String firstName, String lastName) {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertThrows(DAOException.class, () -> userDAO.create(user));
    }

    @ParameterizedTest
    @CsvSource(
            "userforupdating,updated@gmail.com,password, reader, blocked, Ostap, Patso")
    void updateUserWithCorrectData(String login, String email, String password, String role, String status,
                                   String firstName, String lastName) {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertDoesNotThrow(() -> userDAO.update(user));
        assertAll(
                () -> assertEquals(user.getLogin(), login),
                () -> assertEquals(user.getUserStatus(), new UserStatus(status)),
                () -> assertEquals(user.getEmail(), email),
                () -> assertEquals(user.getRole(), new Role(role)),
                () -> assertEquals(user.getFirstName(), firstName),
                () -> assertEquals(user.getLastName(), lastName),
                () -> assertNull(user.getPhone()));
    }
//to do null user login
    @ParameterizedTest
    @CsvSource(
            "incorrectLogin,asd@gmail.com,password, reader, blocked, Ostap, Patso")
    void updateUserWithIncorrectData(String login, String email, String password, String role, String status,
                                     String firstName, String lastName) {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertThrows(DAOException.class, () -> userDAO.update(user));


    }
    @ParameterizedTest
    @CsvSource(
            "userfordeleting,delete@gmail.com,password, reader, active, name, secondname")
    void deleteUserWithCorrectData(String login, String email, String password, String role, String status,
                                   String firstName, String lastName) throws DAOException {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertDoesNotThrow(() -> userDAO.delete(user));
        User user2 = userDAO.findEntityById(login).orElse(null);
        assertNull(user2);
    }

    @ParameterizedTest
    @CsvSource(
            "incorrectLogin,asd@gmail.com,password, reader, active, Ostap, Patso")
    void deleteUserWithIncorrectData(String login, String email, String password, String role, String status,
                                     String firstName, String lastName) {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertThrows(DAOException.class, () -> userDAO.delete(user));
    }


    @ParameterizedTest
    @CsvSource("user1,asd@gmail.com,newpassword, reader, active, Ostap, Patso")
    void changePasswordWithCorrectData(String login, String email, String password, String role, String status,
                                       String firstName, String lastName) throws DAOException {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertDoesNotThrow(() -> userDAO.changePassword(password,login));
    }

    @ParameterizedTest
    @CsvSource("incorrectLogin,asd@gmail.com,newpassword, reader, active, Ostap, Patso")
    void changePasswordWithIncorrectData(String login, String email, String password, String role, String status,
                                         String firstName, String lastName) {
        User user = new User(login, email, password, new Role(role), new UserStatus(status), firstName, lastName, null);
        assertThrows(DAOException.class, () -> userDAO.changePassword(password,login));
    }
}