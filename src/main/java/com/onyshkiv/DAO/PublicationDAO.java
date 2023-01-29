package com.onyshkiv.DAO;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.entity.Publication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicationDAO implements AbstractDAO<Integer, Publication> {

    Connection con;
    private static final Logger logger = LogManager.getLogger(PublicationDAO.class);
    private static PublicationDAO instance;

    public static synchronized PublicationDAO getInstance() {
        if (instance == null) {
            instance = new PublicationDAO();
        }
        return instance;
    }

    private PublicationDAO() {
    }

    //+++++++++++++++++++++++
    @Override
    public List<Publication> findAll() throws DAOException {
        List<Publication> publications = new ArrayList<>();
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.SELECT_ALL_PUBLICATIONS, false);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                publications.add(map(resultSet));
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return publications;
    }

    @Override
    public Publication findEntityById(Integer id) throws DAOException {
        Publication publication = null;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.SELECT_PUBLICATION_BY_ID, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                publication = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return publication;
    }

    //+++++++++++++++++++++++++++++++++++
    @Override
    public boolean create(Publication model) throws DAOException {
        if (model.getPublicationId() != 0) {
            throw new IllegalArgumentException("Publication is already created, the publication ID is not 0.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.INSERT_PUBLICATION, true, model.getName())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                //log
                throw new DAOException("Creating publication failed, no rows affected.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    model.setPublicationId(resultSet.getInt(1));
                } else {
                    //log
                    throw new DAOException("Creating publication failed, no generated key obtained.");
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Publication update(Publication model) throws DAOException {
        if (model.getPublicationId() == 0) {
            throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.UPDATE_PUBLICATION, false, model.getName())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating publication failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getPublicationId());
    }

    //+++++++++++++++++++++++++++++++++++
    @Override
    public boolean delete(Publication model) throws DAOException {
        if (model.getPublicationId() == 0) {
            throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
        }

        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.DELETE_PUBLICATION, false, model.getPublicationId())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting publication failed, no rows affected.");
            } else {
                model.setPublicationId(0);
            }
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return true;
    }

    public boolean contains(Integer id) throws DAOException {
        if (id == 0) {
            throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
        }
        try (PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.IS_CONTAINS_PUBLICATION, false, id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next() && resultSet.getInt(1) == 1) return true;
            return false;
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }

    }

    private static Publication map(ResultSet resultSet) throws SQLException {
        return new Publication(resultSet.getInt(1), resultSet.getString(2));
    }


    public void setConnection(Connection connection) {
        this.con = connection;
    }
}
