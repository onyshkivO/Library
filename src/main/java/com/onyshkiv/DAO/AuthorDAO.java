package com.onyshkiv.DAO;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorDAO implements AbstractDAO<Integer, Author> {
    Connection con;
    private static final Logger logger = LogManager.getLogger(AuthorDAO.class);
    private static AuthorDAO instance;

    public static synchronized AuthorDAO getInstance() {
        if (instance == null) {
            instance = new AuthorDAO();
        }
        return instance;
    }

    private AuthorDAO() {
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public List<Author> findAll() throws DAOException {
        List<Author> authors = new ArrayList<>();
        try (
                PreparedStatement statement = con.prepareStatement(SQLQuery.AuthorQuery.SELECT_ALL_AUTHORS);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                authors.add(map(resultSet));
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return authors;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Author findEntityById(Integer id) throws DAOException {
        Author author = null;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.AuthorQuery.SELECT_AUTHOR_BY_ID, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                author = map(resultSet);
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return author;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean create(Author model) throws DAOException {
        if (model.getAuthorId() != 0) {
            throw new IllegalArgumentException("Author is already created, the author ID is not 0.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.AuthorQuery.INSERT_AUTHOR, true, model.getName())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating author failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    model.setAuthorId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating author failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Author update(Author model) throws DAOException {
        if (model.getAuthorId() == 0) {
            throw new IllegalArgumentException("Author is not created yet, the author id is 0.");
        }
        Object[] values = {
                model.getName(),
                model.getAuthorId()
        };
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.AuthorQuery.UPDATE_AUTHOR, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating author failed, no rows affected.");
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return findEntityById(model.getAuthorId());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean delete(Author model) throws DAOException {
        if (model.getAuthorId() == 0) {
            throw new IllegalArgumentException("Author is not created yet, the author id is 0.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.AuthorQuery.DELETE_AUTHOR, false, model.getAuthorId())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting author failed, no rows affected.");
            } else {
                model.setAuthorId(0);
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return true;
    }

    public Set<Author> getAllAuthorByBookISBN(Integer isbn) throws DAOException {
        Set<Author> result = new HashSet<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.AuthorQuery.M2M_BOOKS_AUTHORS, false, isbn);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                result.add(findEntityById(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return result;

    }

    private static Author map(ResultSet resultSet) throws SQLException {
        Author author = new Author(resultSet.getInt(1), resultSet.getString(2));
        return author;
    }

    public void setConnection(Connection connection) {
        this.con = connection;
    }
}
