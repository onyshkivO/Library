package com.onyshkiv.DAO;

import com.onyshkiv.DAO.impl.AuthorDAO;
import com.onyshkiv.DAO.impl.BookDAO;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.Publication;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    static Connection con;
    static BookDAO bookDAO;


    @BeforeAll
    static void setTestMode() {
        try {
            ApplicationResourceBundle.setTestBundle();
            bookDAO = BookDAO.getInstance();
            con = DataSource.getConnection();
            bookDAO.setConnection(con);

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
//    void findAllTest() throws DAOException, ParseException {
//        List<Book> actual = bookDAO.findAll();
//        List<Book> expected = new ArrayList<>();
//
//        Set<Author> author1=new HashSet<>();
//        author1.add(new Author(1,"author1"));
//        author1.add(new Author(2,"author2"));
//
//        Set<Author> author2=new HashSet<>();
//        author1.add(new Author(2,"author2"));
//
//        expected.add(new Book(1,"book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,author1));
//        expected.add(new Book(2,"book2",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),2,null,author2));
//        expected.add(new Book(3,"book_for_m2m",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>()));
//        expected.add(new Book(4,"book_for_updating",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>()));
//        expected.add(new Book(5,"book_for_deleting",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>()));
//        expected.add(new Book(6,"book_for_deleting",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),0,null,new HashSet<>()));
//
//        assertArrayEquals(expected.toArray(),actual.toArray());
//    }


    @ParameterizedTest
    @MethodSource("findEntityData")
    void findEntityById(Book expected) throws DAOException {
        Book actual = bookDAO.findEntityById(expected.getIsbn()).get();
        assertAll(
                () -> assertEquals(expected.getIsbn(),actual.getIsbn()),
                () -> assertEquals(expected.getName(),actual.getName()),
                () -> assertEquals(expected.getDateOfPublication(),actual.getDateOfPublication()),
                () -> assertEquals(expected.getPublication(),actual.getPublication()),
                () -> assertEquals(expected.getQuantity(),actual.getQuantity()),
                () -> assertNull(actual.getDetails()),
                () -> assertEquals(expected.getAuthors(),actual.getAuthors()));
    }



    private static Stream<Arguments> findEntityData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("1", "book1",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,null,authors1);

        Set<Author> authors2 = new HashSet<>();
        authors2.add(new Author(2,"author2"));
        Book book2 = new Book("2","book2",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),2,null,authors2);


        Book book3 = new Book("3","book_for_m2m", new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>());
        return Stream.of(
                Arguments.of(book1),
                Arguments.of(book2),
                Arguments.of(book3));
    }

    @Test
    void findEntityById_NoEntries() throws DAOException {
        Book book = bookDAO.findEntityById("13").orElse(null);
        assertNull(book);
    }



    @ParameterizedTest
    @MethodSource("createdBookData")
    void createBookWithCorrectData(Book book) throws DAOException {
        assertDoesNotThrow(() -> bookDAO.create(book));
        Book created = bookDAO.findEntityById(book.getIsbn()).get();
        assertAll(
                () -> assertEquals(created.getIsbn(),book.getIsbn()),
                () -> assertEquals(created.getName(),book.getName()),
                () -> assertEquals(created.getDateOfPublication(),book.getDateOfPublication()),
                () -> assertEquals(created.getPublication(),book.getPublication()),
                () -> assertEquals(created.getQuantity(),book.getQuantity()),
                () -> assertEquals(created.getDetails(),book.getDetails()));
    }

    private static Stream<Arguments> createdBookData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("12", "createdBook",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),12,"something",authors1);
        return Stream.of(
                Arguments.of(book1));
    }

    @Test
    void createBookWithIncorrectData_DAOExceptionThrows() throws ParseException {
        Book book = new Book("1","bookIncorrect",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),12,"something",new HashSet<>());
        assertThrows(DAOException.class, () -> bookDAO.create(book));
    }


    @ParameterizedTest
    @MethodSource("updatedBookData")
    void updateBookWithCorrectData(Book book) throws DAOException {
        assertDoesNotThrow(() -> bookDAO.update(book));
        Book book1=  bookDAO.findEntityById(book.getIsbn()).get();
        assertAll(
                () -> assertEquals(book1.getIsbn(),book.getIsbn()),
                () -> assertEquals(book1.getName(),book.getName()),
                () -> assertEquals(book1.getDateOfPublication(),book.getDateOfPublication()),
                () -> assertEquals(book1.getPublication(),book.getPublication()),
                () -> assertEquals(book1.getQuantity(),book.getQuantity()),
                () -> assertEquals(book1.getDetails(),book.getDetails()),
                () -> assertEquals(book1.getDetails(),book.getDetails()));
    }
    private static Stream<Arguments> updatedBookData() throws ParseException {
        Set<Author> authors1 = new HashSet<>();
        authors1.add(new Author(1,"author1"));
        authors1.add(new Author(2,"author2"));
        Book book1 = new Book("4","book_for_updating",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),1,"Something",authors1);
        return Stream.of(
                Arguments.of(book1));
    }

    @Test
    void updateUserWithIncorrectData_IllegalArgumentExceptionThrows() throws ParseException {
        Book book = new Book(null,"bookIncorrect",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),12,"something",new HashSet<>());
        assertThrows(IllegalArgumentException.class, () -> bookDAO.update(book));
    }

    @Test
    void updateBookWithIncorrectData_DAOExceptionThrows() throws ParseException {
        Book book = new Book("1341","bookIncorrect",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(1,"publication1"),12,"something",new HashSet<>());
        assertThrows(DAOException.class, () -> bookDAO.update(book));
    }

    @ParameterizedTest
    @MethodSource("deletedBookData")
    void deleteUserWithCorrectData(Book book) throws DAOException {
        assertDoesNotThrow(() -> bookDAO.delete(book));
        Book book1 = bookDAO.findEntityById(book.getIsbn()).orElse(null);
        assertNull(book1);
    }

    private static Stream<Arguments> deletedBookData() throws ParseException {
        Book book1 = new Book("5","book_for_deleting",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>());
        return Stream.of(
                Arguments.of(book1));
    }

    @Test
    void deleteUserWithIncorrectData_DAOExceptionThrows() throws ParseException {
        Book book = new Book("11231","incorrectDAta",new SimpleDateFormat( "dd.MM.yyyy" ).parse( "11.11.2011" ),new Publication(2,"publication2"),1,null,new HashSet<>());
        assertThrows(DAOException.class, () -> bookDAO.delete(book));
    }

  //to do no row affected то коли немає такого запису


}