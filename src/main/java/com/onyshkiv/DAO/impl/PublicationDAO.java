package com.onyshkiv.DAO.impl;

import static com.onyshkiv.DAO.DAOUtil.*;

import com.onyshkiv.DAO.AbstractDAO;
import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.SQLQuery;
import com.onyshkiv.entity.Publication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublicationDAO extends AbstractDAO<Integer, Publication> {

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
    public Optional<Publication> findEntityById(Integer id) throws DAOException {
        Publication publication = null;
        Optional<Publication> optionalPublication;
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.SELECT_PUBLICATION_BY_ID, false, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                publication = map(resultSet);
            }
            optionalPublication = Optional.ofNullable(publication);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return optionalPublication;
    }

    //+++++++++++++++++++++++++++++++++++
    @Override
    public void create(Publication model) throws DAOException {
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
    }

    //++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void update(Publication model) throws DAOException {
        if (model.getPublicationId() == 0) {
            throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
        }
        try (
                PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.UPDATE_PUBLICATION, false, model.getName(),model.getPublicationId())
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating publication failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    //+++++++++++++++++++++++++++++++++++
    //to do remove
//    if (model.getPublicationId() == 0) {
//        throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
//    }
    @Override
    public void delete(Publication model) throws DAOException {
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
    }

//    public boolean contains(Integer id) throws DAOException {
//        if (id == 0) {
//            throw new IllegalArgumentException("Publication is not created yet, the publication ID is 0.");
//        }
//        try (PreparedStatement statement = prepareStatement(con, SQLQuery.PublicationQuery.IS_CONTAINS_PUBLICATION, false, id);
//             ResultSet resultSet = statement.executeQuery()
//        ) {
//            return resultSet.next() && resultSet.getInt(1) == 1;
//        } catch (SQLException e) {
//            //log
//            throw new DAOException(e);
//        }
//
//    }

    private static Publication map(ResultSet resultSet) throws SQLException {
        return new Publication(resultSet.getInt(1), resultSet.getString(2));
    }


}
