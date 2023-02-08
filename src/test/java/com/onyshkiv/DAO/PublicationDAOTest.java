package com.onyshkiv.DAO;

import com.onyshkiv.DAO.ApplicationResourceBundle;
import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.DataSource;
import com.onyshkiv.DAO.impl.PublicationDAO;
import com.onyshkiv.DAO.impl.UserDAO;
import com.onyshkiv.PasswordHashGenerator;
import com.onyshkiv.entity.Publication;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class PublicationDAOTest {

    static Connection con;
    static PublicationDAO publicationDAO;


    @BeforeAll
    static void setTestMode() {
        try {
            ApplicationResourceBundle.setTestBundle();
            publicationDAO = PublicationDAO.getInstance();
            con = DataSource.getConnection();
            publicationDAO.setConnection(con);

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
//        List<Publication> actual = publicationDAO.findAll();
//        List<Publication> expected = new ArrayList<>();
//        expected.add(new Publication(1,"publication1"));
//        expected.add(new Publication(2,"publication2"));
//        expected.add(new Publication(4,"publication_for_deleting"));
//        expected.add(new Publication(3,"publication_for_updating"));
//        assertArrayEquals(expected.toArray(),actual.toArray());
//    }

    @ParameterizedTest
    @CsvSource({
            "1,publication1",
            "2, publication2"
    })
    void findEntityById(Integer id, String name) throws DAOException {
        Publication publication = publicationDAO.findEntityById(id);
        assertAll(
                () -> assertEquals(id,publication.getPublicationId()),
                () -> assertEquals(name,publication.getName()));
    }

    @Test
    void findEntityById_NoEntries() throws DAOException {
        Publication publication = publicationDAO.findEntityById(12);
        assertNull(publication);
    }

    @Test
    void createPublicationWithCorrectData() {
        Publication publication = new Publication("createdPublication");
        assertDoesNotThrow(() -> publicationDAO.create(publication));
    }


    @ParameterizedTest
    @CsvSource("0,publication1")
    void createPublicationWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(DAOException.class, () -> publicationDAO.create(publication));
    }

    @ParameterizedTest

    @CsvSource("123,publication_new")
    void createPublicationWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(IllegalArgumentException.class, () -> publicationDAO.create(publication));
    }

    @ParameterizedTest
    @CsvSource("3,updatedPublication")
    void updatePublicationWithCorrectData(Integer id, String name) throws DAOException {
        Publication publication = new Publication(id,name);
        assertDoesNotThrow(() -> publicationDAO.update(publication));
        Publication updated=  publicationDAO.findEntityById(id);
        assertAll(
                () -> assertEquals(id,updated.getPublicationId()),
                ()-> assertEquals(name,updated.getName()));
    }

    @ParameterizedTest
    @CsvSource("0, zero_id")
    void updateUserWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(IllegalArgumentException.class, () -> publicationDAO.update(publication));
    }

    @ParameterizedTest
    @CsvSource("123,incorrectData")
    void updateUserWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(DAOException.class, () -> publicationDAO.update(publication));
    }
    @ParameterizedTest
    @CsvSource("4,publication_for_deleting")
    void deleteUserWithCorrectData(Integer id, String name) throws DAOException {
        Publication publication = new Publication(id,name);
        assertDoesNotThrow(() -> publicationDAO.delete(publication));
        Publication publication2 = publicationDAO.findEntityById(id);
        assertNull(publication2);
    }

    @ParameterizedTest
    @CsvSource("123, incoorect_publication_id")
    void deleteUserWithIncorrectData_DAOExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(DAOException.class, () -> publicationDAO.delete(publication));
    }

    @ParameterizedTest
    @CsvSource("0, zero_id")
    void deleteUserWithIncorrectData_IllegalArgumentExceptionThrows(Integer id, String name) {
        Publication publication = new Publication(id,name);
        assertThrows(IllegalArgumentException.class, () -> publicationDAO.delete(publication));
    }

    @ParameterizedTest
    @CsvSource("1, publication1")
    void containsWithCorrectData_TrueAnswer(Integer id, String name) throws DAOException {
        Publication publication = new Publication(id,name  );
        assertDoesNotThrow(()->publicationDAO.contains(publication.getPublicationId()));
        assertTrue(publicationDAO.contains(publication.getPublicationId()));
    }

    @ParameterizedTest
    @CsvSource("12, unreal_publication")
    void containsWithCorrectData_FalseAnswer(Integer id, String name) throws DAOException {
        Publication publication = new Publication(id,name  );
        assertDoesNotThrow(()->publicationDAO.contains(publication.getPublicationId()));
        assertFalse(publicationDAO.contains(publication.getPublicationId()));
    }

    @Test
    void containsWithIncorrectData() {
        Publication publication = new Publication("newPublication");
        assertThrows(IllegalArgumentException.class, ()->publicationDAO.contains(publication.getPublicationId()));
    }
}