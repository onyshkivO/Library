package com.onyshkiv.DAO;

import com.onyshkiv.DAO.impl.AuthorDAO;
import com.onyshkiv.DAO.impl.PublicationDAO;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Publication;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AuthorDAOTest {

    static Connection con;
    static AuthorDAO authorDAO;


    @BeforeAll
    static void setTestMode() {
        try {
            ApplicationResourceBundle.setTestBundle();
            authorDAO = AuthorDAO.getInstance();
            con = DataSource.getConnection();
            authorDAO.setConnection(con);

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

    //test for findAll, must run only one
//    @Test
//    void findAllTest() throws DAOException {
//        List<Author> actual = authorDAO.findAll();
//        List<Author> expected = new ArrayList<>();
//        expected.add(new Author(1,"author1"));
//        expected.add(new Author(2,"author2"));
//        expected.add(new Author(4,"author_for_deleting"));
//        expected.add(new Author(5,"author_for_m2m"));
//        expected.add(new Author(3,"author_for_updating"));
//        assertArrayEquals(expected.toArray(),actual.toArray());
//    }

    @ParameterizedTest
    @CsvSource({
            "1,author1",
            "2, author2"
    })
    void findEntityById(Integer id, String name) throws DAOException {
        Author author = authorDAO.findEntityById(id).get();
        assertAll(
                () -> assertEquals(id,author.getAuthorId()),
                () -> assertEquals(name,author.getName()));
    }

    @Test
    void findEntityById_NoEntries() throws DAOException {
        Author author = authorDAO.findEntityById(12).orElse(null);
        assertNull(author);
    }

    @Test
    void createAuthorWithCorrectData() {
        Author author = new Author("createdAuthor");
        assertDoesNotThrow(() -> authorDAO.create(author));
    }

    @ParameterizedTest
    @CsvSource("0,author1")
    void createAuthorWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(DAOException.class, () -> authorDAO.create(author));
    }

    @ParameterizedTest
    @CsvSource("123,Author_new")
    void createAuthorWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(IllegalArgumentException.class, () -> authorDAO.create(author));
    }

    @ParameterizedTest
    @CsvSource("3,updatedAuthor")
    void updatePublicationWithCorrectData(Integer id, String name) throws DAOException {
        Author author = new Author(id,name);
        assertDoesNotThrow(() -> authorDAO.update(author));
        Author author1=  authorDAO.findEntityById(id).get();
        assertAll(
                () -> assertEquals(id,author1.getAuthorId()),
                ()-> assertEquals(name,author1.getName()));
    }

    @ParameterizedTest
    @CsvSource("0, zero_id")
    void updateUserWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(IllegalArgumentException.class, () -> authorDAO.update(author));
    }

    @ParameterizedTest
    @CsvSource("123,incorrectData")
    void updateUserWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(DAOException.class, () -> authorDAO.update(author));
    }

    @ParameterizedTest
    @CsvSource("4,author_for_deleting")
    void deleteUserWithCorrectData(Integer id, String name) throws DAOException {
        Author author = new Author(id,name);
        assertDoesNotThrow(() -> authorDAO.delete(author));
        Author author1 = authorDAO.findEntityById(id).orElse(null);
        assertNull(author1);
    }

    @ParameterizedTest
    @CsvSource("123, incoorect_publication_id")
    void deleteUserWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(DAOException.class, () -> authorDAO.delete(author));
    }

    @ParameterizedTest
    @CsvSource("0, zero_id")
    void deleteUserWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Author author = new Author(id,name);
        assertThrows(IllegalArgumentException.class, () -> authorDAO.delete(author));
    }

    @Test
    void getAllAuthorByBookISBN_WithData() throws DAOException {
        Set<Author> expected = new HashSet<>();
        expected.add(new Author(1,"author1"));
        expected.add(new Author(2,"author2"));
        Set<Author> actual = authorDAO.getAllAuthorByBookISBN("1");
        assertArrayEquals(expected.toArray(),actual.toArray());
    }

    @Test
    void getAllAuthorByBookISBN_Empty() throws DAOException {
        Set<Author> actual = authorDAO.getAllAuthorByBookISBN("12");
        assertArrayEquals(actual.toArray(),new Object[]{});
    }

    @Test
    void setAuthorBookTableConnection_CorrectData() throws DAOException {
        assertDoesNotThrow(()->authorDAO.setAuthorBookTableConnection("3",5));
        Set<Author> authors = authorDAO.getAllAuthorByBookISBN("3");
        Set<Author> expected = new HashSet<>();
        expected.add(new Author(5, "author_for_m2m"));
        assertArrayEquals(authors.toArray(),expected.toArray());
    }

    @Test
    void setAuthorBookTableConnection_IncorrectData() {
        assertThrows(DAOException.class,()->authorDAO.setAuthorBookTableConnection("12",1));
    }

    @Test
    void removeAuthorBookTableConnection_CorrectData() throws DAOException {
        assertDoesNotThrow(()->authorDAO.removeAuthorBookTableConnection("2"));
        Set<Author> authors = authorDAO.getAllAuthorByBookISBN("2");
        assertArrayEquals(authors.toArray(),new Object[]{});
    }

}