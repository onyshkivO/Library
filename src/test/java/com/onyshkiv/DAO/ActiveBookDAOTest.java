package com.onyshkiv.DAO;

import com.onyshkiv.DAO.impl.ActiveBookDAO;
import com.onyshkiv.util.password.PasswordHashGenerator;
import com.onyshkiv.entity.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ActiveBookDAOTest {

    static Connection con;
    static ActiveBookDAO activeBookDAO;


    @BeforeAll
    static void setTestMode() {
        try {
            ApplicationResourceBundle.setTestBundle();
            activeBookDAO = ActiveBookDAO.getInstance();
            con = DataSource.getConnection();
            activeBookDAO.setConnection(con);

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
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() {
    }

    @ParameterizedTest
    @MethodSource("findEntityData")
    void findEntityById(ActiveBook expected) throws DAOException {
        ActiveBook actual = activeBookDAO.findEntityById(expected.getActiveBookId()).get();
        assertAll(
                () -> assertEquals(expected.getActiveBookId(),actual.getActiveBookId()),
                () -> assertEquals(expected.getBook(),actual.getBook()),
                () -> assertEquals(expected.getUser(),actual.getUser()),
                () -> assertEquals(expected.getSubscriptionStatus(),actual.getSubscriptionStatus()),
                () -> assertEquals(expected.getStartDate(),actual.getStartDate()),
                () -> assertEquals(expected.getEndDate(),actual.getEndDate()),
                () -> assertNull(expected.getFine()));
    }



    private static Stream<Arguments> findEntityData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("user1","asd", PasswordHashGenerator.hash("password"),new Role("reader"),new UserStatus( "active"), "Ostap", "Patso", null);
        ActiveBook activeBook1 = new ActiveBook(1,book1,user1,new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        return Stream.of(
                Arguments.of(activeBook1));
    }

    @Test
    void findEntityById_NoEntries() throws DAOException {
        ActiveBook activeBook = activeBookDAO.findEntityById(13).orElse(null);
        assertNull(activeBook);
    }
// to do як показував міщеряков як з null в int
    @ParameterizedTest
    @MethodSource("createdActiveBookData")
    void createBookWithCorrectData(ActiveBook activeBook) throws DAOException {
        assertDoesNotThrow(() -> activeBookDAO.create(activeBook));
        ActiveBook created = activeBookDAO.findEntityById(activeBook.getActiveBookId()).get();
        assertAll(
                () -> assertEquals(created.getActiveBookId(),activeBook.getActiveBookId()),
                () -> assertEquals(created.getBook(),activeBook.getBook()),
                () -> assertEquals(created.getUser(),activeBook.getUser()),
                () -> assertEquals(created.getSubscriptionStatus(),activeBook.getSubscriptionStatus()),
                () -> assertEquals(created.getStartDate(),activeBook.getStartDate()),
                () -> assertEquals(created.getEndDate(),activeBook.getEndDate()),
                () -> assertEquals(created.getFine(),0.0));
    }

    private static Stream<Arguments> createdActiveBookData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("user1","asd", PasswordHashGenerator.hash("password"),new Role("reader"),new UserStatus( "active"), "Ostap", "Patso", null);
        ActiveBook activeBook1 = new ActiveBook(book1,user1,new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        return Stream.of(
                Arguments.of(activeBook1));
    }

    @ParameterizedTest
    @MethodSource("createdActiveBookData_Incorrect")
    void createBookWithIncorrectData_DAOExceptionThrows(ActiveBook activeBook) {
        assertThrows(IllegalArgumentException.class, () -> activeBookDAO.create(activeBook));
    }

    private static Stream<Arguments> createdActiveBookData_Incorrect() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("user1","asd", PasswordHashGenerator.hash("password"),new Role("reader"),new UserStatus( "active"), "Ostap", "Patso", null);
        ActiveBook activeBook1 = new ActiveBook(1,book1,user1,new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        ActiveBook activeBook2 = new ActiveBook(book1,new User(),new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        ActiveBook activeBook3 = new ActiveBook(book1,null,new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        ActiveBook activeBook4 = new ActiveBook(312321,book1,null,new SubscriptionStatus(1),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),null);
        return Stream.of(
                Arguments.of(activeBook1),
                Arguments.of(activeBook2),
                Arguments.of(activeBook3),
                Arguments.of(activeBook4));
    }

    @ParameterizedTest
    @MethodSource("updatedActiveBookData")
    void updateBookWithCorrectData(ActiveBook activeBook) throws DAOException {
        assertDoesNotThrow(() -> activeBookDAO.update(activeBook));
        ActiveBook actual=  activeBookDAO.findEntityById(activeBook.getActiveBookId()).get();
        assertAll(
                () -> assertEquals(actual.getActiveBookId(),activeBook.getActiveBookId()),
                () -> assertEquals(actual.getBook(),activeBook.getBook()),
                () -> assertEquals(actual.getUser(),activeBook.getUser()),
                () -> assertEquals(actual.getSubscriptionStatus(),activeBook.getSubscriptionStatus()),
                () -> assertEquals(actual.getStartDate(),activeBook.getStartDate()),
                () -> assertEquals(actual.getEndDate(),activeBook.getEndDate()),
                () -> assertEquals(actual.getFine(),activeBook.getFine()));
    }

    private static Stream<Arguments> updatedActiveBookData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("userLibr2","asd", PasswordHashGenerator.hash("password"),new Role(1),new UserStatus( 1), "lib", "miy", null);
        ActiveBook activeBook1 = new ActiveBook(3,book1,user1,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        return Stream.of(
                Arguments.of(activeBook1));
    }

    @ParameterizedTest
    @MethodSource("updatedActiveBookData_IncorrectData")
    void updateUserWithIncorrectData_IllegalArgumentExceptionThrows(ActiveBook activeBook)  {
        assertThrows(IllegalArgumentException.class, () -> activeBookDAO.update(activeBook));
    }


    private static Stream<Arguments> updatedActiveBookData_IncorrectData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("userLibr2","asd", PasswordHashGenerator.hash("password"),new Role(1),new UserStatus( 1), "lib", "miy", null);
        ActiveBook activeBook1 = new ActiveBook(3,book1,null,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        ActiveBook activeBook2 = new ActiveBook(3,book1,new User(),new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        ActiveBook activeBook3 = new ActiveBook(book1,user1,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        return Stream.of(
                Arguments.of(activeBook1),
                Arguments.of(activeBook2),
                Arguments.of(activeBook3));
    }

    @ParameterizedTest
    @MethodSource("updatedActiveBookData_IncorrectDataDao")
    void updateBookWithIncorrectData_DAOExceptionThrows(ActiveBook activeBook) {
        assertThrows(DAOException.class, () -> activeBookDAO.update(activeBook));
    }


    private static Stream<Arguments> updatedActiveBookData_IncorrectDataDao() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("userLibr2","asd", PasswordHashGenerator.hash("password"),new Role(1),new UserStatus( 1), "lib", "miy", null);
        ActiveBook activeBook1 = new ActiveBook(123,book1,user1,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        return Stream.of(
                Arguments.of(activeBook1));
    }

    @ParameterizedTest
    @MethodSource("deletedActiveBookData")
    void deleteUserWithCorrectData(ActiveBook activeBook) throws DAOException {
        assertDoesNotThrow(() -> activeBookDAO.delete(activeBook));
        ActiveBook activeBook1 = activeBookDAO.findEntityById(activeBook.getActiveBookId()).orElse(null);
        assertNull(activeBook1);
    }

    private static Stream<Arguments> deletedActiveBookData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("userLibr2","asd", PasswordHashGenerator.hash("password"),new Role(1),new UserStatus( 1), "lib", "miy", null);
        ActiveBook activeBook1 = new ActiveBook(4,book1,user1,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        return Stream.of(
                Arguments.of(activeBook1));
    }

    @ParameterizedTest
    @MethodSource("deletedActiveBookData_IncorrectData")
    void deleteUserWithIncorrectData_DAOExceptionThrows(ActiveBook activeBook) throws ParseException {
        assertThrows(DAOException.class, () -> activeBookDAO.delete(activeBook));
    }

    private static Stream<Arguments> deletedActiveBookData_IncorrectData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);
        User user1 = new User("userLibr2","asd", PasswordHashGenerator.hash("password"),new Role(1),new UserStatus( 1), "lib", "miy", null);
        ActiveBook activeBook1 = new ActiveBook(312,book1,user1,new SubscriptionStatus(3),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.11.2022" ),new SimpleDateFormat( "dd.MM.yyyy" ).parse( "15.12.2022" ),50.5);
        return Stream.of(
                Arguments.of(activeBook1));
    }
}