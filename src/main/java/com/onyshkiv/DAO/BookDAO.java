package com.onyshkiv.DAO;

import com.onyshkiv.DAO.entity.Author;
import com.onyshkiv.DAO.entity.Book;
import com.onyshkiv.DAO.entity.Publication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookDAO implements AbstractDAO<Integer, Book>{
    Connection con;
    private static final Logger logger = LogManager.getLogger(BookDAO.class);
    private static BookDAO instance;

    public static synchronized BookDAO getInstance(){
        if (instance == null){
            instance = new BookDAO();
        }
        return instance;
    }
    private BookDAO()  {
    }
    @Override
    public List<Book> findAll() throws DAOException {
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
        List<Book> books = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement(SQLQuery.BookQuery.SELECT_ALL_BOOKS);
            ResultSet resultSet = statement.executeQuery()){

            outer: while(resultSet.next()){
                Book book = new Book();
                book.setIsbn(resultSet.getInt(1));
                for (Book value : books) {
                    if (value.getIsbn() == book.getIsbn()) {
                        value.getAuthors().add(new Author(resultSet.getInt(7), resultSet.getString(8)));
                        continue outer;
                    }
                }

                book.setName(resultSet.getString(2));
                book.setDateOfPublication(resultSet.getDate(3));
                book.setPublication(new Publication(resultSet.getInt(4),resultSet.getString(10)));
                book.setQuantity(resultSet.getInt(5));
                book.setDetails(resultSet.getString(6));
                book.setAuthors(new HashSet<>());
                book.getAuthors().add(new Author(resultSet.getInt(7),resultSet.getString(8)));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return books;
    }

    @Override
    public Book findEntityById(Integer id) throws DAOException {
        Book result=new Book();
        result.setAuthors(new HashSet<>());
        try(PreparedStatement statement=con.prepareStatement(SQLQuery.BookQuery.SELECT_BOOK_BY_ISBN)) {
            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()) {
                    result.setIsbn(resultSet.getInt(1));
                    result.setName(resultSet.getString(2));
                    result.setDateOfPublication(resultSet.getDate(3));
                    result.setPublication(new Publication(resultSet.getInt(4),resultSet.getString(10)));
                    result.setQuantity(resultSet.getInt(5));
                    result.setDetails(resultSet.getString(6));
                    result.getAuthors().add(new Author(resultSet.getInt(7),resultSet.getString(8)));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return result;
    }

    @Override
    public boolean create(Book model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("insert into book values(?,?,?,?,?,?)")){
            int k=0;
            statement.setInt(++k,model.getIsbn());
            statement.setString(++k,model.getName());
            statement.setDate(++k, new Date(model.getDateOfPublication().getTime()));

            if(isExistPublication(model.getPublication())){
                statement.setInt(++k,  getPublicationId(model.getPublication()));
            }else{
                statement.setInt(++k,  addPublication(model));
            }

            statement.setInt(++k,  model.getQuantity());
            statement.setString(++k,  model.getDetails());

            statement.executeUpdate();
            insertAuthors(model);

        } catch (SQLException e) {
            throw new DAOException(e);
        }

       return false;
    }

    private int addPublication(Book model) throws DAOException {
        int result=-1;
        try(PreparedStatement statement = con.prepareStatement("Insert into publication values(default,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,model.getPublication().getName());
            if (statement.executeUpdate()>0){
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if (resultSet.next()){
                        result = resultSet.getInt(1);
                    }
                }catch(SQLException e ){
                    throw new DAOException();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }
    private void insertAuthors(Book model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("insert into book_has_authors values(?,?)")){
            for (Author a : model.getAuthors()){
                statement.setInt(1, model.getIsbn());
                if (isExist(a)) {
                    statement.setInt(2, getAuthorId(a));
                }
                else{

                    statement.setInt(2, insertAuthor(a));
                }
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private int getAuthorId(Author author) throws DAOException {
        int result=-1 ;
        try (PreparedStatement statement = con.prepareStatement("Select authors_id from authors where name = ?")){
            statement.setString(1,author.getName());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }


    private int getPublicationId(Publication author) throws DAOException {
        int result=-1 ;
        try (PreparedStatement statement = con.prepareStatement("Select publication_id from publication where name = ?")){
            statement.setString(1,author.getName());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    private boolean isExist(Author model) throws DAOException {
        boolean result =false;
        try(PreparedStatement statement = con.prepareStatement("select Count(*) from authors where name = ?")){
            statement.setString(1,model.getName());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                result = resultSet.getInt(1) != 0;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    private boolean isExistPublication(Publication model) throws DAOException {
        boolean result =false;
        try(PreparedStatement statement = con.prepareStatement("select Count(*) from publication where name = ?")){
            statement.setString(1,model.getName());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    result = resultSet.getInt(1) != 0;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }



    private int insertAuthor(Author model) throws DAOException {
        int result =-1;
        try(PreparedStatement statement = con.prepareStatement("insert into authors values(default,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,model.getName());
            if (statement.executeUpdate()>0){
                try(ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        result = resultSet.getInt(1);
                    }
                }catch(SQLException e){
                    throw new DAOException(e);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }



    @Override
    public Book update(Book model) throws DAOException {
        try(PreparedStatement statement =con.prepareStatement( "UPDATE book SET name = ?,date_of_publication = ?, publication_id =?,quantity=?,details=? where isbn=?")){
            deleteBookHasAuthors(model);
            statement.setString(1,model.getName());
            statement.setDate(2,new Date(model.getDateOfPublication().getTime()));
            if (isExistPublication(model.getPublication())){
                statement.setInt(3,getPublicationId(model.getPublication()));
            }
            else{
                statement.setInt(3,  addPublication(model));
            }
            statement.setInt(4,model.getQuantity());
            statement.setString(5,model.getDetails());
            statement.setInt(6,model.getIsbn());
            statement.executeUpdate();
            insertAuthors(model);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getIsbn());
    }

    @Override
    public boolean delete(Book model) throws DAOException {
        try{
            deleteBookHasAuthors(model);
            deleteBook(model);
        } catch (DAOException e) {
            throw e;
        }
        return true;
    }
    private void deleteBookHasAuthors(Book model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM book_has_authors WHERE b_isbn=?")){
            statement.setInt(1,model.getIsbn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    private void deleteBook(Book model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM book WHERE isbn=?")){
            statement.setInt(1,model.getIsbn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    public void setConnection(Connection connection) {
        this.con = connection;
    }

    public static void main(String[] args) throws SQLException {
        Connection a =null;
        try {
            BookDAO bookDAO = new BookDAO();
            a =DataSource.getConnection();
            bookDAO.setConnection(a);

//            Publication publication=new Publication("publication test");
//            Set<Author> authors = new HashSet<>();
//            java.util.Date date = new java.util.Date();
//            authors.add(new Author("author test"));
//            authors.add(new Author("author test2"));
//            Book book = new Book(32,"testBook",date,publication,12,null,authors);
//            bookDAO.create(book);


            Publication publication=new Publication("publication test22");
            Set<Author> authors = new HashSet<>();
            java.util.Date date = new java.util.Date();
            authors.add(new Author("author1"));
            Book book = new Book(32,"testBook",date,publication,3,null,authors);
            bookDAO.create(book);
            //bookDAO.update(book);


            //bookDAO.delete(book);



            a.commit();
        } catch (DAOException e) {
            a.rollback();
            e.printStackTrace();
            System.out.println("something went wrong");
        } finally {
            a.close();
        }

    }
}
