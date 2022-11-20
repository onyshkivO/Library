package com.onyshkiv.DAO;

import com.onyshkiv.DAO.entity.Publication;
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

    public static synchronized PublicationDAO getInstance(){
        if (instance == null){
            instance = new PublicationDAO();
        }
        return instance;
    }
    private PublicationDAO()  {
    }


    @Override
    public List<Publication> findAll() throws DAOException {
        List<Publication> publications = new ArrayList<>();
        try(PreparedStatement statement =con.prepareStatement("SELECT * FROM publication");
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publications.add(new Publication(resultSet.getInt(1), resultSet.getString(2)));
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
        try(PreparedStatement statement =con.prepareStatement("SELECT * FROM publication WHERE publication_id = ? ")){
            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    publication = new Publication(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return publication;
    }

    @Override
    public boolean create(Publication model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO publication VALUES (DEFAULT,?)")){
            statement.setString(1,model.getName());
           statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Publication update(Publication model) throws DAOException{
        try(PreparedStatement statement = con.prepareStatement("UPDATE publication SET name =? WHERE publication_id=?")){
            statement.setString(1,model.getName());
            statement.setInt(2,model.getPublicationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return findEntityById(model.getPublicationId());
    }

    @Override
    public boolean delete(Publication model) throws DAOException {
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM publication WHERE publication_id = ?")){
            statement.setInt(1,model.getPublicationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            //log
            throw new DAOException(e);
        }
        return true;
    }
    public void setConnection(Connection connection) {
        this.con = connection;
    }

    public static void main(String[] args) {
        Connection a =null;
        try {
            PublicationDAO  publicationDAO= new PublicationDAO();
            a =DataSource.getConnection();
            publicationDAO.setConnection(a);
            Publication publication = new Publication(19,"publ test upd");
            publicationDAO.delete(publication);
            a.commit();
    } catch (SQLException | DAOException e) {
            try {
                a.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }finally {
            try {
                a.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
